package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Customer;
import com.emh.model.Floor;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class CustomerFormView extends AbsoluteLayout implements View {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;

	private Customer customer;
	private Customer tempCustomer;

	private ListDataProvider<Customer> customerDataProvider;

	private Label lblCustomerTitle;
	private Label lblUnitPaymentTitle;
	private Label lblCustomerName;
	private Label lblGender;
	private Label lblDOB;
	private Label lblJob;
	private Label lblAddress;
	private Label lblPhone;
	private Label lblFloor;
	private Label lblUnit;
	private Label lblPayment;
	private Label lblTerm;
	private Label lblStartDate;
	private Label lblEndDate;

	private TextField customerNameField;
	private TextField jobField;
	private TextField phoneField;
	private TextField paymentField;
	private TextField termField;

	private TextArea addressTextArea;

	private ComboBox<String> cboGender;
	private ComboBox<Floor> cboFloor;
	private ComboBox<com.emh.model.Unit> cboUnit;

	private DateField dobDateField;
	private DateField startDateField;
	private DateField endDateField;

	private Button btnAddFloor;
	private Button btnAddUnit;
	private Button btnSave;
	private Button btnClear;

	private Grid<Customer> grid;

	private Binder<Customer> binderCustomer;
	private Binder<com.emh.model.Unit> binderUnit;

	public CustomerFormView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}

	public CustomerFormView(ApplicationContext applicationContext, Customer customer) {
		this.applicationContext = applicationContext;
		this.customer = customer;
		init();
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	private void init() {

		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		initComponents();
		initGrid();
		addComponent(lblCustomerTitle, "top:14.0px;left:0.0px;");
		addComponent(lblUnitPaymentTitle, "top:175.0px;left:0.0px;");
		addComponent(lblCustomerName, "top:69.0px;left:16.0px;");
		addComponent(customerNameField, "top:61.0px;left:178.0px;");
		addComponent(lblGender, "top:69.0px;left:390.0px;");
		addComponent(cboGender, "top:61.0px;left:540.0px;");
		addComponent(lblDOB, "top:69.0px;left:756.0px;");
		addComponent(dobDateField, "top:61.0px;left:940.0px;");

		addComponent(lblJob, "top:117.0px;left:126.0px;");
		addComponent(jobField, "top:114.0px;left:178.0px;");
		addComponent(lblAddress, "top:117.0px;left:765.0px;");
		addComponent(addressTextArea, "top:109.0px;left:940.0px;");
		addComponent(lblPhone, "top:117.0px;left:400.0px;");
		addComponent(phoneField, "top:114.0px;left:540.0px;");

		addComponent(lblFloor, "top:223.0px;left:34.0px;");
		addComponent(cboFloor, "top:216.0px;left:178.0px;");
		addComponent(btnAddFloor, "top:216.0px;left:330.0px;");
		addComponent(lblUnit, "top:223.0px;left:393.0px;");
		addComponent(cboUnit, "top:216.0px;left:540.0px;");
		addComponent(btnAddUnit, "top:216.0px;left:692.0px;");

		addComponent(lblPayment, "top:223.0px;left:756.0px;");
		addComponent(paymentField, "top:216.0px;left:940.0px;");
		addComponent(lblTerm, "top:269.0px;left:105.0px;");
		addComponent(termField, "top:269.0px;left:178.0px;");
		addComponent(lblStartDate, "top:269.0px;left:393.0px;");
		addComponent(startDateField, "top:269.0px;left:540.0px;");
		addComponent(lblEndDate, "top:269.0px;left:765.0px;");
		addComponent(endDateField, "top:269.0px;left:940.0px;");
		addComponent(btnSave, "top:320.0px;left:485.0px;");
		addComponent(btnClear, "top:320.0px;left:585.0px;");

		addComponent(grid, "top:360.0px;left:16.0px;");
		setSizeFull();

		if (customer != null) {
			binderCustomer.readBean(customer);
			binderUnit.readBean(customer.getUnit());
			cboUnit.setItemCaptionGenerator(unit -> unit.getUnitNumber().toString());
			cboFloor.setItemCaptionGenerator(floor -> floor.getFloorNumber().toString());

			customerDataProvider.getItems().clear();
			customerDataProvider.getItems().add(tempCustomer);
			grid.setDataProvider(customerDataProvider);
		} else {
			clearValueComponent();
		}
	}

	private void initComponents() {

		customerDataProvider = new ListDataProvider<>(new ArrayList<>());
		binderCustomer = new Binder<>();
		binderUnit = new Binder<>();

		lblCustomerTitle = new Label("Customer Details");
		lblCustomerTitle.addStyleName("name");
		lblUnitPaymentTitle = new Label("Unit/Room and Payment Details");
		lblUnitPaymentTitle.addStyleName("name");

		lblCustomerName = new Label("Customer Name :");
		lblGender = new Label("Marital Status :");
		lblDOB = new Label("Date Of Birth :");
		lblJob = new Label("Job :");
		lblAddress = new Label("Address :");
		lblPhone = new Label("Phone :");
		lblFloor = new Label("Floor Number :");
		lblUnit = new Label("Unit Number :");
		lblPayment = new Label("Payment Amount :");
		lblTerm = new Label("Term :");
		lblStartDate = new Label("Start Date :");
		lblEndDate = new Label("End Date :");

		customerNameField = new TextField();
		binderCustomer.forField(customerNameField)
				.withValidator(name -> name.length() > 0, "Customer Name can not empty. Please fill it.")
				.bind(Customer::getCustomerName, Customer::setCustomerName);

		jobField = new TextField();
		binderCustomer.bind(jobField, Customer::getJob, Customer::setJob);

		phoneField = new TextField();
		binderCustomer.bind(phoneField, Customer::getPhoneNumber, Customer::setPhoneNumber);

		paymentField = new TextField();
		termField = new TextField();
		termField.addValueChangeListener(new TermFieldValueChangeListener());

		addressTextArea = new TextArea();
		addressTextArea.setWidth("-1px");
		addressTextArea.setHeight("73px");
		binderCustomer.bind(addressTextArea, Customer::getAddress, Customer::setAddress);

		cboGender = new ComboBox<>();
		binderCustomer.forField(cboGender).withValidator(gender -> gender.length() > 0, "Please select marital status.")
				.bind(Customer::getGender, Customer::setGender);

		cboFloor = new ComboBox<>();
		cboFloor.setWidth("151px");
		// binderCustomer.forField(cboFloor).bind(customer ->
		// customer.getUnit().getFloor(), (customer, formValue )->
		// customer.getUnit().setFloor(formValue));
		binderUnit.bind(cboFloor, com.emh.model.Unit::getFloor, com.emh.model.Unit::setFloor);

		cboUnit = new ComboBox<>();
		cboUnit.setWidth("151px");
		binderCustomer.bind(cboUnit, Customer::getUnit, Customer::setUnit);

		dobDateField = new DateField();
		binderCustomer.bind(dobDateField, Customer::getDob, Customer::setDob);

		startDateField = new DateField();
		startDateField.setValue(LocalDate.now());

		endDateField = new DateField();

		btnAddFloor = new Button();
		btnAddFloor.setWidth("33px");
		btnAddFloor.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnAddFloor.setIcon(VaadinIcons.PLUS);
		btnAddFloor.addClickListener(clickEvent -> {
			UI.getCurrent().addWindow(new FloorView(applicationContext));
		});

		btnAddUnit = new Button();
		btnAddUnit.setWidth("33px");
		btnAddUnit.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnAddUnit.setIcon(VaadinIcons.PLUS);
		btnAddUnit.addClickListener(clickEvent -> {
			UI.getCurrent().addWindow(new UnitView(applicationContext));
		});

		btnSave = new Button("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.addClickListener(new SaveClickEvent());

		btnClear = new Button("New");
		btnClear.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnClear.addClickListener(clickEvent -> {
			clearValueComponent();
		});

		grid = new Grid<>();
		grid.setWidth("1110px");
		grid.setHeight("150px");

		List<String> genders = Arrays.asList("Male", "Female");
		cboGender.setItems(genders);

		cboFloor.setItems(classBusiness.selectAllEntity(Floor.class));
		cboFloor.setItemCaptionGenerator(floor -> floor.getFloorNumber().toString());
		cboFloor.addValueChangeListener(valueChangeEvent -> {
			Floor floor = valueChangeEvent.getValue();
			if (customer == null) {
				cboUnit.clear();
				if (floor != null) {
					List<com.emh.model.Unit> newUnits = new ArrayList<>();
					List<com.emh.model.Unit> units = (List<com.emh.model.Unit>) classBusiness
							.selectAllEntity(com.emh.model.Unit.class);
					if (units.size() > 0) {
						units.forEach(unit -> {
							if (unit.getFloor().getFloorID().equals(floor.getFloorID())) {
								if (!unit.isStatu()) {
									newUnits.add(unit);
								}
							}
						});
					}
					cboUnit.setItems(newUnits);
					cboUnit.setItemCaptionGenerator(unit -> unit.getUnitNumber().toString());
				} else {
					cboUnit.setItems(new ArrayList<>());
				}
				customer = tempCustomer;
			}
		});

		cboFloor.addSelectionListener(selectionEvent -> {
			tempCustomer = customer;
			customer = null;
		});
	}

	private final class SaveClickEvent implements ClickListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {

			try {
				if (tempCustomer == null) {
					Customer customer = new Customer();
					binderCustomer.writeBean(customer);
					
					customer.getUnit().setStatu(true);
					classBusiness.createEntity(customer);
					classBusiness.updateEntity(customer.getUnit());
					customerDataProvider.getItems().add(customer);
					grid.setDataProvider(customerDataProvider);
					customerDataProvider = new ListDataProvider<>(new ArrayList<>());
					Notification.show("The Customer save successfully.", Type.HUMANIZED_MESSAGE);
				} else {
					customer = tempCustomer;
					
					com.emh.model.Unit tempUnit = tempCustomer.getUnit();
					binderCustomer.writeBean(customer);
					
					com.emh.model.Unit unit = customer.getUnit();
	
					if (!unit.getUnitNumber().equals(tempUnit.getUnitNumber())) {
						unit.setStatu(true);
						classBusiness.updateEntity(customer.getUnit());
						tempUnit.setStatu(false);
						classBusiness.updateEntity(tempUnit);
					}
					classBusiness.updateEntity(customer);
					customerDataProvider.getItems().clear();
					customerDataProvider.getItems().add(customer);
					grid.setDataProvider(customerDataProvider);
					Notification.show("The Customer update successfully.", Type.HUMANIZED_MESSAGE);
					tempCustomer = customer;
				}
			} catch (Exception e) {
				Notification.show(e.getMessage(), Type.HUMANIZED_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	private final class TermFieldValueChangeListener implements ValueChangeListener<String> {

		private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			String term = event.getValue();
			if (!term.isEmpty()) {
				int month = Integer.parseInt(event.getValue());
				LocalDate start = startDateField.getValue().plusMonths(month);
				endDateField.setValue(start);
			} else {
				endDateField.setValue(LocalDate.now());
			}
		}
	}

	private void initGrid() {

		Column<Customer, String> columnCustomername = grid.addColumn(Customer::getCustomerName);
		columnCustomername.setCaption("Customer Name");

		Column<Customer, String> columnGender = grid.addColumn(Customer::getGender);
		columnGender.setCaption("Marital Status");

		Column<Customer, LocalDate> columnDOB = grid.addColumn(Customer::getDob);
		columnDOB.setCaption("Date Of Birth");

		Column<Customer, String> columnPhone = grid.addColumn(Customer::getPhoneNumber);
		columnPhone.setCaption("Phone");

		Column<Customer, Integer> columnFloorNumber = grid
				.addColumn(customer -> customer.getUnit().getFloor().getFloorNumber());
		columnFloorNumber.setCaption("Floor Number");

		Column<Customer, Integer> columnUnitNumber = grid.addColumn(customer -> customer.getUnit().getUnitNumber());
		columnUnitNumber.setCaption("Unit Number");
	}
	
	private void clearValueComponent() {
		customerNameField.clear();
		jobField.clear();
		addressTextArea.clear();
		phoneField.clear();
		cboGender.clear();
		dobDateField.clear();
		cboFloor.clear();
		cboUnit.clear();
		paymentField.clear();
		termField.clear();
		startDateField.clear();
		endDateField.clear();
		
		customerDataProvider = new ListDataProvider<>(new ArrayList<>());
		grid.setDataProvider(customerDataProvider);
		customer = null;
		tempCustomer = null;
		startDateField.setValue(LocalDate.now());
	}
}
