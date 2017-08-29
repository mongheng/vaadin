package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.CarParking;
import com.emh.model.Contract;
import com.emh.model.Customer;
import com.emh.model.ParkingCashFlow;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CarParkingView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private Customer customer;
	private CarParking carParking;
	private List<CarParking> currectCarParkingCustomers;
	private ListDataProvider<CarParking> dataProvider;
	private Binder<CarParking> binder;

	private FormLayout formLayout;
	private HorizontalLayout hLayout;
	private Grid<CarParking> grid;
	private ComboBox<Customer> cboCustomer;
	private TextField carTypeField;
	private TextField plantNumberField;
	private TextField amountField;
	private CheckBox ckbFree;
	private Button btnSave;
	private Button btnNew;
	private Button btnActivated;

	public CarParkingView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}

	private void init() {
		formLayout = new FormLayout();
		hLayout = new HorizontalLayout();
		grid = new Grid<>();

		binder = new Binder<>();
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());

		cboCustomer = new ComboBox<>("Please Select Customer :");
		binder.bind(cboCustomer, CarParking::getCustomer, CarParking::setCustomer);

		carTypeField = new TextField("Car Type :");
		binder.forField(carTypeField).withValidator(carType -> carType != null, "Please input the car type.")
				.bind(CarParking::getCarType, CarParking::setCarType);

		plantNumberField = new TextField("Plant Number :");
		binder.forField(plantNumberField)
				.withValidator(plantNumber -> plantNumber != null, "Please input the Plant Number.")
				.bind(CarParking::getPlantNumber, CarParking::setPlantNumber);

		amountField = new TextField("Amount :");
		binder.forField(amountField).withConverter(new StringToFloatConverter("Please input number."))
				.bind(CarParking::getAmount, CarParking::setAmount);

		ckbFree = new CheckBox("Free");
		ckbFree.setValue(true);
		amountField.setEnabled(false);
		binder.bind(ckbFree, CarParking::isFree, CarParking::setFree);
		ckbFree.addValueChangeListener(valueChange -> {
			if (!ckbFree.getValue()) {
				amountField.clear();
				amountField.setEnabled(true);
			} else {
				amountField.setEnabled(false);
			}
		});

		btnSave = new Button("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.addClickListener(clickEvent -> {

			if (carParking == null) {
				CarParking carParking = new CarParking();
				binder.writeBeanIfValid(carParking);
				classBusiness.createEntity(carParking);
				dataProvider.getItems().add(carParking);
				grid.setDataProvider(dataProvider);
				Notification.show("The data save have been successfully.");
				this.carParking = carParking;
			} else {
				binder.writeBeanIfValid(carParking);
				classBusiness.updateEntity(carParking);
				String HQL = "FROM CarParking WHERE CUSTOMER_ID = '" + customer.getCustomerID() + "'";
				List<CarParking> carParkings = classBusiness.selectListEntityByHQL(CarParking.class, HQL);
				dataProvider = new ListDataProvider<CarParking>(carParkings);
				grid.setDataProvider(dataProvider);
				Notification.show("The data update have been successfully.");
			}
		});

		btnNew = new Button("New");
		btnNew.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnNew.addClickListener(clickEvent -> {
			clear();
		});

		btnActivated = new Button("Activated");
		btnActivated.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnActivated.setEnabled(false);
		btnActivated.addClickListener(new ActivatedClickEvent());

		setValueCustomer();

		hLayout.addComponents(btnSave, btnNew, btnActivated);
		hLayout.setComponentAlignment(btnSave, Alignment.MIDDLE_CENTER);
		formLayout.addComponents(cboCustomer, carTypeField, plantNumberField, amountField, ckbFree, hLayout);

		initColumnGrid();
		grid.setWidth("100%");
		grid.setHeight("220px");
		addComponents(formLayout, grid);
		setComponentAlignment(grid, Alignment.BOTTOM_LEFT);
		setCaption("Car Parking");
		setSizeFull();
		setExpandRatio(formLayout, 7);
		setSpacing(false);
	}

	private void setValueCustomer() {
		List<Customer> customers = classBusiness.selectAllEntity(Customer.class);
		if (customers.size() > 0) {
			cboCustomer.setItems(customers);
			cboCustomer.setItemCaptionGenerator(Customer::getCustomerName);
		} else {
			cboCustomer.setItems(new ArrayList<>());
		}

		cboCustomer.addValueChangeListener(valueChange -> {
			customer = valueChange.getValue();
			String HQL = "FROM CarParking WHERE CUSTOMER_ID = '" + customer.getCustomerID() + "'";
			currectCarParkingCustomers = classBusiness.selectListEntityByHQL(CarParking.class, HQL);

			dataProvider = currectCarParkingCustomers.size() > 0
					? new ListDataProvider<CarParking>(currectCarParkingCustomers)
					: new ListDataProvider<CarParking>(new ArrayList<CarParking>());

			grid.setDataProvider(dataProvider);

			if (currectCarParkingCustomers.size() > 0) {
				ckbFree.setValue(false);
				amountField.setEnabled(true);
				btnActivated.setEnabled(true);
			} else {
				ckbFree.setValue(true);
				amountField.setEnabled(false);
				btnActivated.setEnabled(false);
			}
		});
	}

	private void initColumnGrid() {

		Column<CarParking, String> columnCustomerName = grid
				.addColumn(carParking -> carParking.getCustomer().getCustomerName());
		columnCustomerName.setCaption("Customer Name");

		Column<CarParking, String> columnCarType = grid.addColumn(carParking -> carParking.getCarType());
		columnCarType.setCaption("Car Type");

		Column<CarParking, String> columnPlantNumber = grid.addColumn(carParking -> carParking.getPlantNumber());
		columnPlantNumber.setCaption("Plant Number");

		Column<CarParking, String> columnAmount = grid.addColumn(carParking -> {
			return carParking.getAmount() == null ? "free" : carParking.getAmount().toString();
		});
		columnAmount.setCaption("Amount");

		grid.addItemClickListener(itemClick -> {
			carParking = itemClick.getItem();
			if (carParking.getAmount() == null) {
				carParking.setAmount(0f);
			}
			if(carParking.isActivated()) {
				btnActivated.setVisible(false);
			} else if (carParking.isFree()) {
				btnActivated.setVisible(false);
			} else {
				btnActivated.setVisible(true);
			}
			
			binder.readBean(carParking);
		});
	}

	private void clear() {
		carTypeField.clear();
		plantNumberField.clear();
		amountField.clear();
		ckbFree.setValue(false);
		btnActivated.setVisible(true);
		this.carParking = null;
	}

	private final class ActivatedClickEvent implements ClickListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {
			String HQL = "FROM Contract WHERE CUSTOMER_ID = '" + customer.getCustomerID() + "'";
			Contract contract = (Contract) classBusiness.selectEntityByHQL(HQL);
			int term = contract.getTerm();
			LocalDate tempEndDate = null;

			for (int i = 1; i <= term; i++) {
				ParkingCashFlow parkingCashFlow = new ParkingCashFlow();
				if (i == 1) {
					parkingCashFlow.setStartDate(contract.getStartDate());
					tempEndDate = contract.getStartDate().plusMonths(i);
					parkingCashFlow.setEndDate(tempEndDate);
					parkingCashFlow.setInstallmentNumber(i);
					parkingCashFlow.setAmount(carParking.getAmount());
					parkingCashFlow.setCarparking(carParking);
					classBusiness.createEntity(parkingCashFlow);
				} else {
					parkingCashFlow.setStartDate(tempEndDate);
					tempEndDate = tempEndDate.plusMonths(1);
					parkingCashFlow.setEndDate(tempEndDate);
					parkingCashFlow.setInstallmentNumber(i);
					parkingCashFlow.setAmount(carParking.getAmount());
					parkingCashFlow.setCarparking(carParking);;
					classBusiness.createEntity(parkingCashFlow);
				}
				carParking.setActivated(true);
				classBusiness.updateEntity(carParking);
				customer.setParkStatu(true);
				classBusiness.updateEntity(customer);
				Notification.show("The Car Parking start successfully.");
				btnActivated.setVisible(false);
			}
		}

	}
}
