package com.emh.view.report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.HistoryPayment;
import com.emh.model.Payment;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.Utility;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.themes.ValoTheme;

public class ResponsiveReceiverView extends CssLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private User user;
	private VerticalLayout mainLayout;
	private VerticalLayout topLayout;
	private VerticalLayout centerLayout;
	private VerticalLayout bottomLayout;
	private HorizontalLayout topFormHLayout;
	private HorizontalLayout topButtonHLayout;
	private HorizontalLayout centerHLayout;
	private FormLayout formLayout;
	private GridLayout bottomGridLayout;

	private DateField startDate;
	private DateField endDate;
	private ComboBox<User> cboEmployee;
	private Button btnSearch;

	private Label equalSign;
	private Label plusSign;
	private TextField totalRoomAmountField;
	private TextField totalCarParkingAmountField;
	private TextField totalAmountField;
	private Button btnRecevier;

	private ListDataProvider<Payment> dataProvider;
	private Grid<Payment> roomGrid;
	private Grid<Payment> carParkingGrid;
	private Set<Payment> roomsPayment;
	private Set<Payment> carsParkingPayment;

	private Float totalAmount = 0f;
	private Float totalAmountRoom = 0f;
	private Float totalAmountCarParking = 0f;

	public ResponsiveReceiverView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
		init();
	}

	private void init() {
		mainLayout = new VerticalLayout();
		topLayout = new VerticalLayout();
		centerLayout = new VerticalLayout();
		bottomLayout = new VerticalLayout();
		topFormHLayout = new HorizontalLayout();
		topButtonHLayout = new HorizontalLayout();
		formLayout = new FormLayout();
		centerHLayout = new HorizontalLayout();
		bottomGridLayout = new GridLayout(6, 1);

		roomsPayment = new HashSet<>();
		carsParkingPayment = new HashSet<>();

		// Top Layout.
		initTopLayout();
		// Center Layout.
		initCenterLayout();
		// Bottom Layout.
		initBottomLayout();

		mainLayout.addComponents(topLayout, centerLayout, bottomLayout);
		mainLayout.setExpandRatio(topLayout, 1);
		mainLayout.setExpandRatio(centerLayout, 2.5f);
		mainLayout.setExpandRatio(bottomLayout, 0.5f);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);

		addComponent(mainLayout);
		setSizeFull();
		setCaption("Receive Payment");
	}

	private void initTopLayout() {
		startDate = new DateField("Start Date");
		startDate.setTextFieldEnabled(true);
		startDate.setDateFormat("dd/MM/yyyy");

		endDate = new DateField("End Date");
		endDate.setTextFieldEnabled(true);
		endDate.setDateFormat("dd/MM/yyyy");

		cboEmployee = new ComboBox<>("Empolyee");
		cboEmployee.setItems(Utility.getEmployee(applicationContext));
		cboEmployee.setItemCaptionGenerator(User::getUsername);
		cboEmployee.addValueChangeListener(valueChange -> {
			user = valueChange.getValue();
		});

		btnSearch = new Button("Search");
		btnSearch.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSearch.addClickListener(clickEvent -> {
			String HQL = null;
			if (startDate.getValue() != null && endDate.getValue() != null && user != null) {
				HQL = "FROM Payment WHERE PAYMENT_DATE >= '" + startDate.getValue() + "' and PAYMENT_DATE <= '"
						+ endDate.getValue() + "' and USER_ID = '" + user.getUserid() + "'";
			} else if (startDate.getValue() != null && endDate.getValue() != null) {
				HQL = "FROM Payment WHERE PAYMENT_DATE >= '" + startDate.getValue() + "' and PAYMENT_DATE <= '"
						+ endDate.getValue() + "'";
			} else if (startDate.getValue() == null && endDate.getValue() == null && user != null) {
				HQL = "FROM Payment WHERE USER_ID = '" + user.getUserid() + "'";
			}
			if (HQL != null) {
				search(HQL);
			}
		});

		topFormHLayout.addComponents(startDate, endDate, cboEmployee);
		topButtonHLayout.addComponents(btnSearch);
		formLayout.addComponents(topFormHLayout, topButtonHLayout);
		formLayout.addStyleName("caption");

		topLayout.addComponent(formLayout);
		topLayout.setSizeFull();
		topLayout.setSpacing(false);
		topLayout.setMargin(false);
	}

	private void initCenterLayout() {
		roomGrid = new Grid<>();
		carParkingGrid = new Grid<>();

		loadGrid();

		centerHLayout.addComponents(roomGrid, carParkingGrid);
		centerHLayout.setSizeFull();
		centerHLayout.setMargin(false);

		centerLayout.addComponent(centerHLayout);
		centerLayout.setSizeFull();
		centerLayout.setSpacing(false);
	}

	private void loadGrid() {
		List<Payment> paymentsRoom = new ArrayList<>();
		List<Payment> paymentsCarParking = new ArrayList<>();
		String HQL = "FROM Payment WHERE PAYMENT_DATE = '" + LocalDate.now() + "'";
		List<Payment> payments = classBusiness.selectListEntityByHQL(Payment.class, HQL);
		payments.forEach(payment -> {
			if (payment.getCarType() != null && payment.getPlantNumber() != null) {
				paymentsCarParking.add(payment);
			} else {
				paymentsRoom.add(payment);
			}
		});

		loadRoomGrid(paymentsRoom);
		loadCarParkingGrid(paymentsCarParking);
	}

	private void loadRoomGrid(List<Payment> paymentsRoom) {
		try {
			dataProvider = new ListDataProvider<>(new ArrayList<>());
			if (paymentsRoom.size() > 0) {
				dataProvider = new ListDataProvider<>(paymentsRoom);
			}
			roomGrid.setDataProvider(dataProvider);

			roomGrid.addColumn(payment -> payment.getCustomerName()).setCaption("Customer Name");
			roomGrid.addColumn(payment -> payment.getAmount()).setCaption("Amount");
			roomGrid.addColumn(payment -> payment.getInstallmentNumber()).setCaption("Installment Number");
			roomGrid.addColumn(payment -> payment.getFloorNumber()).setCaption("Floor Number");
			roomGrid.addColumn(payment -> payment.getUnitNumber()).setCaption("Unit/Room Number");
			roomGrid.addColumn(payment -> payment.getPaymentDate()).setCaption("Payment Date");
			roomGrid.setSizeFull();
			MultiSelectionModel<Payment> multiSelectionModel = (MultiSelectionModel<Payment>) roomGrid
					.setSelectionMode(SelectionMode.MULTI);

			multiSelectionModel.addMultiSelectionListener(selectItem -> {
				Set<Payment> payments = selectItem.getAddedSelection();
				roomsPayment = selectItem.getNewSelection();
				Set<Payment> removeSelectPayments = selectItem.getRemovedSelection();
				if (payments.size() > 0) {
					payments.forEach(payment -> {
						totalAmountRoom = totalAmountRoom + payment.getAmount();
					});
				}

				if (removeSelectPayments.size() > 0) {
					removeSelectPayments.forEach(payment -> {
						totalAmountRoom = totalAmountRoom - payment.getAmount();
					});
				}
				totalAmount = totalAmountRoom + totalAmountCarParking;
				totalAmountField.setValue(totalAmount.toString());
				totalRoomAmountField.setValue(totalAmountRoom.toString());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadCarParkingGrid(List<Payment> paymentsCarParking) {
		try {
			dataProvider = new ListDataProvider<>(new ArrayList<>());
			if (paymentsCarParking.size() > 0) {
				dataProvider = new ListDataProvider<>(paymentsCarParking);
			}
			carParkingGrid.setDataProvider(dataProvider);

			carParkingGrid.addColumn(payment -> payment.getCustomerName()).setCaption("Customer Name");
			carParkingGrid.addColumn(payment -> payment.getAmount()).setCaption("Amount");
			carParkingGrid.addColumn(payment -> payment.getInstallmentNumber()).setCaption("Installment Number");
			carParkingGrid.addColumn(payment -> payment.getCarType()).setCaption("CarType");
			carParkingGrid.addColumn(payment -> payment.getPlantNumber()).setCaption("PlantNumber");
			carParkingGrid.addColumn(payment -> payment.getFloorNumber()).setCaption("Floor Number");
			carParkingGrid.addColumn(payment -> payment.getUnitNumber()).setCaption("Unit/Room Number");
			carParkingGrid.addColumn(payment -> payment.getPaymentDate()).setCaption("Payment Date");
			carParkingGrid.setSizeFull();
			carParkingGrid.setSelectionMode(SelectionMode.MULTI);

			MultiSelectionModel<Payment> multiSelectionModel = (MultiSelectionModel<Payment>) carParkingGrid
					.setSelectionMode(SelectionMode.MULTI);

			multiSelectionModel.addMultiSelectionListener(selectItem -> {
				Set<Payment> payments = selectItem.getAddedSelection();
				carsParkingPayment = selectItem.getNewSelection();
				Set<Payment> removeSelectPayments = selectItem.getRemovedSelection();
				if (payments.size() > 0) {
					payments.forEach(payment -> {
						totalAmountCarParking = totalAmountCarParking + payment.getAmount();
					});
				}

				if (removeSelectPayments.size() > 0) {
					removeSelectPayments.forEach(payment -> {
						totalAmountCarParking = totalAmountCarParking - payment.getAmount();
					});
				}
				totalAmount = totalAmountRoom + totalAmountCarParking;
				totalAmountField.setValue(totalAmount.toString());
				totalCarParkingAmountField.setValue(totalAmountCarParking.toString());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initBottomLayout() {
		equalSign = new Label("=");
		equalSign.addStyleName("plusequal");
		plusSign = new Label("+");
		plusSign.addStyleName("plusequal");

		totalAmountField = new TextField("Grand Total :");
		totalAmountField.setReadOnly(true);
		totalAmountField.addStyleName("textalig");

		totalCarParkingAmountField = new TextField("Vehicle Total :");
		totalCarParkingAmountField.setReadOnly(true);
		totalCarParkingAmountField.addStyleName("textalig");

		totalRoomAmountField = new TextField("Room Total :");
		totalRoomAmountField.setReadOnly(true);
		totalRoomAmountField.addStyleName("textalig");

		btnRecevier = new Button("Receiver");
		btnRecevier.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnRecevier.addClickListener(clickEvent -> {
			ConfirmDialog.show(UI.getCurrent(), "Confrimation", "Are you sure you want to receive? ", "Yes", "No",
					new ConfirmDialog.Listener() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								String totalamount = totalAmountField.getValue();
								if (totalamount != "") {
									Float checkValue = Float.valueOf(totalamount);
									if (!checkValue.equals(0.0f)) {

										if (roomsPayment.size() > 0) {
											roomsPayment.forEach(payment -> {
												HistoryPayment hPayment = new HistoryPayment();
												hPayment.setHistoryID(payment.getPaymentID());
												hPayment.setAmount(payment.getAmount());
												hPayment.setCustomerName(payment.getCustomerName());
												hPayment.setFloorNumber(payment.getFloorNumber());
												hPayment.setInstallmentNumber(payment.getInstallmentNumber());
												hPayment.setPaymentDate(payment.getPaymentDate());
												hPayment.setReceiveDate(LocalDate.now());
												hPayment.setUnitNumber(payment.getUnitNumber());
												hPayment.setUser(payment.getUser());

												classBusiness.createEntity(hPayment);
												classBusiness.deleteEntity(payment);
											});
										}

										if (carsParkingPayment.size() > 0) {
											carsParkingPayment.forEach(payment -> {
												HistoryPayment hPayment = new HistoryPayment();
												hPayment.setHistoryID(payment.getPaymentID());
												hPayment.setAmount(payment.getAmount());
												hPayment.setCustomerName(payment.getCustomerName());
												hPayment.setFloorNumber(payment.getFloorNumber());
												hPayment.setInstallmentNumber(payment.getInstallmentNumber());
												hPayment.setPaymentDate(payment.getPaymentDate());
												hPayment.setReceiveDate(LocalDate.now());
												hPayment.setUnitNumber(payment.getUnitNumber());
												hPayment.setUser(payment.getUser());
												hPayment.setCarType(payment.getCarType());
												hPayment.setPlantNumber(payment.getPlantNumber());

												classBusiness.createEntity(hPayment);
												classBusiness.deleteEntity(payment);
											});
										}

										loadGrid();
										cboEmployee.clear();
										startDate.clear();
										endDate.clear();
									} else {
										Notification.show("You can not receive. Please check your data again.",
												Type.ERROR_MESSAGE);
									}
								} else {
									Notification.show("You can not receive. Please check your data again.",
											Type.ERROR_MESSAGE);
								}
							} else {
								dialog.close();
							}
						}
					});
		});

		bottomGridLayout.setWidth("800px");
		bottomGridLayout.addComponent(totalRoomAmountField, 0, 0);
		bottomGridLayout.addComponent(plusSign, 1, 0);
		bottomGridLayout.setComponentAlignment(plusSign, Alignment.BOTTOM_CENTER);
		bottomGridLayout.addComponent(totalCarParkingAmountField, 2, 0);
		bottomGridLayout.addComponent(equalSign, 3, 0);
		bottomGridLayout.setComponentAlignment(equalSign, Alignment.BOTTOM_CENTER);
		bottomGridLayout.addComponent(totalAmountField, 4, 0);
		bottomGridLayout.addComponent(btnRecevier, 5, 0);
		bottomGridLayout.setComponentAlignment(btnRecevier, Alignment.BOTTOM_CENTER);

		bottomGridLayout.setColumnExpandRatio(1, 0.1f);
		bottomGridLayout.setColumnExpandRatio(3, 0.1f);
		bottomGridLayout.setColumnExpandRatio(4, 0.1f);

		bottomLayout.addComponent(bottomGridLayout);
		bottomLayout.setSizeFull();
		bottomLayout.setComponentAlignment(bottomGridLayout, Alignment.TOP_CENTER);
		bottomLayout.setSpacing(false);
		bottomLayout.setMargin(false);

	}

	private void search(String HQL) {
		List<Payment> paymentsRoom = new ArrayList<>();
		List<Payment> paymentsCarParking = new ArrayList<>();
		List<Payment> payments = classBusiness.selectListEntityByHQL(Payment.class, HQL);
		payments.forEach(payment -> {
			if (payment.getCarType() != null && payment.getPlantNumber() != null) {
				paymentsCarParking.add(payment);
			} else {
				paymentsRoom.add(payment);
			}
		});

		loadRoomGrid(paymentsRoom);
		loadCarParkingGrid(paymentsCarParking);
	}
}
