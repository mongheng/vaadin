package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.CarParking;
import com.emh.model.Contract;
import com.emh.model.Customer;
import com.emh.model.ParkingCashFlow;
import com.emh.model.Payment;
import com.emh.model.User;
import com.emh.model.mock.MockParkingCashFlow;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.ReportUtil;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class CarParkingContractView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private User user;
	private Customer customer;
	
	private HorizontalLayout hLayout;
	
	private TextField customerNameField;
	private TextField floorField;
	private TextField unitField;
	
	private Button btnExport;
	
	private FileDownloader fileDownloader;
	
	private List<Contract> contracts;
	private Grid<ParkingCashFlow> grid;
	ListDataProvider<ParkingCashFlow> dataProvider;
	
	String message = " has been paid.";

	public CarParkingContractView(ApplicationContext applicationContext, Customer customer, List<Contract> contracts) {
		this.applicationContext = applicationContext;
		this.customer = customer;
		this.contracts = contracts;
		init();
	}

	private void init() {
		hLayout = new HorizontalLayout();
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		user = (User) UI.getCurrent().getSession().getAttribute(User.class);
		
		customerNameField = new TextField("Customer Name");
		customerNameField.setValue(customer.getCustomerName());
		customerNameField.setReadOnly(true);
		customerNameField.addStyleName("textnoncenter");
		
		floorField = new TextField("Floor Number");
		floorField.setValue(customer.getUnit().getFloor().getFloorNumber().toString());
		floorField.setReadOnly(true);
		floorField.addStyleName("textnoncenter");

		unitField = new TextField("Unit Number");
		unitField.setValue(customer.getUnit().getUnitNumber().toString());
		unitField.setReadOnly(true);
		unitField.addStyleName("textnoncenter");
		
		btnExport = new Button("Export");
		btnExport.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		hLayout.addComponents(customerNameField, floorField, unitField, btnExport);
		hLayout.setComponentAlignment(btnExport, Alignment.BOTTOM_LEFT);
		addComponent(hLayout);
		setExpandRatio(hLayout, 0.25f);
		
		List<CarParking> carParkings = classBusiness.selectListEntity(CarParking.class, Customer.class, "customerID",
				customer.getCustomerID().toString());
		carParkings.forEach(carParking -> {
			String cellCaption = carParking.getCarType() + " - " + carParking.getPlantNumber();
			grid = new Grid<>();
			dataProvider = new ListDataProvider<>(new ArrayList<>());
			if (carParking.isActivated()) {

				List<ParkingCashFlow> parkingCashFlows = classBusiness.selectListEntity(ParkingCashFlow.class,
						CarParking.class, "carparkingID", carParking.getCarparkingID());

				dataProvider = parkingCashFlows.size() > 0 ? new ListDataProvider<>(parkingCashFlows)
						: new ListDataProvider<>(new ArrayList<>());

				grid.setDataProvider(dataProvider);
				initColumnGrid(grid, cellCaption);
				
				ExportFile(parkingCashFlows, cellCaption);
			}

			grid.addItemClickListener(itemClick -> {
				
				
			});
		});
		setSizeFull();
		setCaption("Parking Contract");
	}

	private void initColumnGrid(Grid<ParkingCashFlow> grid, String cellCaption) {

		Column<ParkingCashFlow, Integer> columnInstallment = grid
				.addColumn(parkingCashFlow -> parkingCashFlow.getInstallmentNumber());
		columnInstallment.setCaption("Installment Number");
		columnInstallment.setId("0");

		Column<ParkingCashFlow, Float> columnAmount = grid.addColumn(parkingCashFlow -> parkingCashFlow.getAmount());
		columnAmount.setCaption("Amount");
		columnAmount.setId("1");

		Column<ParkingCashFlow, LocalDate> columnStartDate = grid
				.addColumn(parkingCashFlow -> parkingCashFlow.getStartDate());
		columnStartDate.setCaption("Start Date");
		columnStartDate.setId("2");

		Column<ParkingCashFlow, LocalDate> columnEndDate = grid
				.addColumn(parkingCashFlow -> parkingCashFlow.getEndDate());
		columnEndDate.setCaption("End Date");
		columnEndDate.setId("3");

		Column<ParkingCashFlow, String> columnPaid = grid.addColumn(parkingCashFlow -> {
			if (parkingCashFlow.isStatu()) {
				return "X";
			} else {
				return "";
			}
		});
		columnPaid.setCaption("Paid");
		columnPaid.setId("4");
		
		Column<ParkingCashFlow, String> columnButtonPaid = grid.addColumn(btnPaid -> "Paid", new ButtonRenderer<>(clickEvent -> {
			ParkingCashFlow parkingCashFlow = clickEvent.getItem();
			int installmentNumber = parkingCashFlow.getInstallmentNumber();
			if (!parkingCashFlow.isStatu()) {
				if (installmentNumber == 1) {
					payment(parkingCashFlow, false);
				} else {
					String HQL_CHECKVALUE = "From ParkingCashFlow WHERE CARPARKING_ID = '" + parkingCashFlow.getCarparking().getCarparkingID() + "'";
					List<ParkingCashFlow> parkingCashFlows = classBusiness.selectListEntityByHQL(ParkingCashFlow.class, HQL_CHECKVALUE);
					boolean checkValue = installmentNumber == parkingCashFlows.size() ? true: false;
					installmentNumber = installmentNumber - 1;
					String HQL = "FROM ParkingCashFlow WHERE INSTALLMENT_NUMBER = " + installmentNumber
							+ " and CARPARKING_ID = '" + parkingCashFlow.getCarparking().getCarparkingID() + "'";
					ParkingCashFlow pcf = (ParkingCashFlow) classBusiness.selectEntityByHQL(HQL);
					if (pcf.isStatu()) {
						payment(parkingCashFlow, checkValue);
					} else {
						Notification.show("Please paid by order.", Type.WARNING_MESSAGE);
					}
				}
			} else {
				Notification.show("This month already paid.", Type.WARNING_MESSAGE);
			}
		}));
		columnButtonPaid.setCaption("Action Pay");
		columnButtonPaid.setId("5");
		grid.setSizeFull();

		HeaderRow row = grid.prependHeaderRow();
		HeaderCell cell = row.join(row.getCell("0"), row.getCell("1"), row.getCell("2"), row.getCell("3"),
				row.getCell("4"), row.getCell("5"));
		cell.setText(cellCaption);
		addComponent(grid);
		setExpandRatio(grid, 0.75f);
	}
	
	private void payment(ParkingCashFlow parkingCashFlow, boolean checkValue) {
		ConfirmDialog.show(UI.getCurrent(), "Confirmation",
				"Are you sure you want to pay "
						+ parkingCashFlow.getCarparking().getCustomer().getCustomerName()
						+ ", Installment Number :" + parkingCashFlow.getInstallmentNumber() + ", CarType :"
						+ parkingCashFlow.getCarparking().getCarType() + ", PlantNumber :"
						+ parkingCashFlow.getCarparking().getPlantNumber(),
				"Yes", "No", new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							Payment payment = new Payment();
							Customer customer = parkingCashFlow.getCarparking().getCustomer();
							payment.setPaymentID(parkingCashFlow.getCashflowID());
							payment.setInstallmentNumber(parkingCashFlow.getInstallmentNumber());
							payment.setCustomerName(customer.getCustomerName());
							payment.setAmount(parkingCashFlow.getAmount());
							payment.setFloorNumber(customer.getUnit().getFloor().getFloorNumber());
							payment.setUnitNumber(customer.getUnit().getUnitNumber());
							payment.setCarType(parkingCashFlow.getCarparking().getCarType());
							payment.setPlantNumber(parkingCashFlow.getCarparking().getPlantNumber());
							payment.setStartDate(parkingCashFlow.getStartDate().toString());
							payment.setEndDate(parkingCashFlow.getEndDate().toString());
							payment.setPaymentDate(LocalDate.now());
							payment.setUser(user);
							classBusiness.createEntity(payment);

							parkingCashFlow.setStatu(true);
							classBusiness.updateEntity(parkingCashFlow);
							
							contracts.forEach(contract -> {
								if (contract.getCustomer().getCustomerID().equals(customer.getCustomerID())) {
									if (contract.getTerm().equals(parkingCashFlow.getInstallmentNumber()) || checkValue) {
										String HQL = "FROM CarParking WHERE CARPAKING_ID = '" + parkingCashFlow.getCarparking().getCarparkingID() + "'";
										CarParking carParking = (CarParking) classBusiness.selectEntityByHQL(HQL);
										carParking.setClose(true);
										classBusiness.updateEntity(carParking);
										message = message + " This car contract is end of these month.";
										return;
									}
								}
							});
							
							Notification.show(parkingCashFlow.getCarparking().getCustomer()
									.getCustomerName() + ", Installment Number :"
									+ parkingCashFlow.getInstallmentNumber() + ", CarType :"
									+ parkingCashFlow.getCarparking().getCarType() + ", PlantNumber :"
									+ parkingCashFlow.getCarparking().getPlantNumber() + message);
							ReportUtil.createInvoiceReportPDF(payment, user);
							//Page.getCurrent().reload();
						} else {
							dialog.close();
						}
					}
				});
	}
	
	private void ExportFile(List<ParkingCashFlow> parkingCashFlows, String vehicle) {
		String info = customer.getCustomerName() + "-" + vehicle;
		String path = "c:/dailyReport/" + user.getUsername() + "/" + info;
		
		List<MockParkingCashFlow> mockParkingCashFlows = parkingCashFlows.stream().map(parkingcashflow -> {
			MockParkingCashFlow mcf = new MockParkingCashFlow();
			mcf.setAmount(parkingcashflow.getAmount());
			mcf.setCashflowID(parkingcashflow.getCashflowID());
			mcf.setCarparking(parkingcashflow.getCarparking());
			mcf.setEndDate(parkingcashflow.getEndDate().toString());
			mcf.setInstallmentNumber(parkingcashflow.getInstallmentNumber());
			mcf.setStartDate(parkingcashflow.getStartDate().toString());
			mcf.setStatu(parkingcashflow.isStatu());
			return mcf;
		}).collect(Collectors.toList());
		
		ReportUtil.createParkingCashFlowReportPDF(mockParkingCashFlows, user , info, path, ReportUtil.PDF); 
		ReportUtil.StreamResourceData streamSource = new ReportUtil.StreamResourceData(path, ReportUtil.PDF);
		StreamResource sr = new StreamResource(streamSource, "ParkingCashflow-" + info + ReportUtil.PDF);
		fileDownloader = new FileDownloader(sr);
		fileDownloader.extend(btnExport);
	}
}
