package com.emh.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Customer;
import com.emh.model.Floor;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class CustomerFormView extends AbsoluteLayout implements View {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;

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
	private Button btnCancel;

	private Grid<Customer> grid;

	public CustomerFormView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	private void init() {

		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		initComponents();
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
		addComponent(btnCancel, "top:320.0px;left:585.0px;");

		addComponent(grid, "top:360.0px;left:16.0px;");
		setSizeFull();

	}

	private void initComponents() {

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
		jobField = new TextField();
		phoneField = new TextField();
		paymentField = new TextField();
		termField = new TextField();

		addressTextArea = new TextArea();
		addressTextArea.setWidth("-1px");
		addressTextArea.setHeight("73px");

		cboGender = new ComboBox<>();
		
		cboFloor = new ComboBox<>();
		cboFloor.setWidth("151px");
		
		cboUnit = new ComboBox<>();
		cboUnit.setWidth("151px");
		dobDateField = new DateField();
		startDateField = new DateField();
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
		btnCancel = new Button("Cancel");
		btnCancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		grid = new Grid<>();
		grid.setWidth("1110px");
		grid.setHeight("150px");

		List<String> genders = Arrays.asList("Male", "Female");
		cboGender.setItems(genders);

		cboFloor.setItems(classBusiness.selectAllEntity(Floor.class));
		cboFloor.setItemCaptionGenerator(floor -> floor.getFloorNumber().toString());
		cboFloor.addValueChangeListener(valueChangeEvent -> {
			Floor floor = valueChangeEvent.getValue();
			cboUnit.clear();
			if (floor != null) {
				List<com.emh.model.Unit> newUnits = new ArrayList<>();
				List<com.emh.model.Unit> units = (List<com.emh.model.Unit>) classBusiness
						.selectAllEntity(com.emh.model.Unit.class);
				if (units.size() > 0) {
					units.forEach(unit -> {
						if (unit.getFloor().getFloorID().equals(floor.getFloorID())) {
							newUnits.add(unit);
						}
					});
				}
				cboUnit.setItems(newUnits);
				cboUnit.setItemCaptionGenerator(unit -> unit.getUnitNumber().toString());
			} else {
				cboUnit.setItems(new ArrayList<>());
			}
		});
	}
}
