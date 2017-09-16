package com.emh.view;

import java.util.Arrays;
import java.util.List;

import com.emh.model.Customer;
import com.emh.model.Floor;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ResponsiveCustomerFormView extends CssLayout {

	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout;
	private VerticalLayout topLayout;
	private VerticalLayout bottomLayout;

	// Top Customer Layout
	private VerticalLayout topCustomerTitleLayout;
	private VerticalLayout topCustomerContentLayout;
	private HorizontalLayout topCustomerHLayout;
	private VerticalLayout topCustomerContentLeftLayout;
	private VerticalLayout topCustomerContentCenterLayout;
	private VerticalLayout topCustomerContentRightLayout;
	private FormLayout topCustomerLeftFormLayout;
	private FormLayout topCustomerCenterFormLayout;
	private FormLayout topCustomerRightFormLayout;

	// Bottom Unit/Room/Payment Detail Layout
	private VerticalLayout topDetailTitleLayout;
	private VerticalLayout topDetailContentLayout;
	private HorizontalLayout topDetailHLayout;
	private VerticalLayout topDetailContentLeftLayout;
	private VerticalLayout topDetailContentCenterLayout;
	private VerticalLayout topDetailContentRightLayout;
	private FormLayout topDetailLeftFormLayout;
	private FormLayout topDetailCenterFormLayout;
	private FormLayout topDetailRightFormLayout;

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

	private Label lblCustomerTitle;
	private Label lblUnitPaymentTitle;

	private Grid<Customer> grid;

	/*private Binder<Customer> binderCustomer;
	private Binder<com.emh.model.Unit> binderUnit;
	private Binder<Contract> binderContract;*/

	public ResponsiveCustomerFormView() {
		init();
	}

	private void init() {
		mainLayout = new VerticalLayout();
		topLayout = new VerticalLayout();
		bottomLayout = new VerticalLayout();

		// Top Customer Layout.
		topCustomerTitleLayout = new VerticalLayout();
		topCustomerContentLayout = new VerticalLayout();
		topCustomerHLayout = new HorizontalLayout();
		topCustomerContentLeftLayout = new VerticalLayout();
		topCustomerContentCenterLayout = new VerticalLayout();
		topCustomerContentRightLayout = new VerticalLayout();
		topCustomerLeftFormLayout = new FormLayout();
		topCustomerCenterFormLayout = new FormLayout();
		topCustomerRightFormLayout = new FormLayout();

		// Bottom Unit/Room/Payment Detail Layout
		topDetailTitleLayout = new VerticalLayout();
		topDetailContentLayout = new VerticalLayout();
		topDetailHLayout = new HorizontalLayout();
		topDetailContentLeftLayout = new VerticalLayout();
		topDetailContentCenterLayout = new VerticalLayout();
		topDetailContentRightLayout = new VerticalLayout();
		topDetailLeftFormLayout = new FormLayout();
		topDetailCenterFormLayout = new FormLayout();
		topDetailRightFormLayout = new FormLayout();

		lblCustomerTitle = new Label("Customer Details");
		lblCustomerTitle.addStyleName("customerstyle");
		lblUnitPaymentTitle = new Label("Unit/Room and Payment Details");
		lblUnitPaymentTitle.addStyleName("customerstyle");

		topCustomerTitleLayout.addComponent(lblCustomerTitle);
		topCustomerTitleLayout.setSpacing(false);
		topCustomerTitleLayout.setMargin(false);
		topCustomerTitleLayout.setSizeFull();

		topDetailTitleLayout.addComponent(lblUnitPaymentTitle);
		topDetailTitleLayout.setSpacing(false);
		topDetailTitleLayout.setMargin(false);
		topDetailTitleLayout.setSizeFull();

		// set Customer component in layout.
		initComponents();
		// Top Left
		topCustomerLeftFormLayout.addComponents(customerNameField, jobField);
		topCustomerLeftFormLayout.setSizeFull();
		topCustomerLeftFormLayout.setMargin(false);
		topCustomerContentLeftLayout.addComponent(topCustomerLeftFormLayout);
		topCustomerContentLeftLayout.setSizeFull();
		topCustomerContentLeftLayout.setSpacing(false);
		topCustomerContentLeftLayout.setMargin(false);

		// Top Center
		topCustomerCenterFormLayout.addComponents(cboGender, phoneField);
		topCustomerCenterFormLayout.setSizeFull();
		topCustomerCenterFormLayout.setMargin(false);
		topCustomerContentCenterLayout.addComponent(topCustomerCenterFormLayout);
		topCustomerContentCenterLayout.setSpacing(false);
		topCustomerContentCenterLayout.setMargin(false);

		// Top Right
		topCustomerRightFormLayout.addComponents(dobDateField, addressTextArea);
		topCustomerRightFormLayout.setSizeFull();
		topCustomerRightFormLayout.setMargin(false);
		topCustomerContentRightLayout.addComponent(topCustomerRightFormLayout);
		topCustomerContentRightLayout.setSpacing(false);
		topCustomerContentRightLayout.setMargin(false);

		topCustomerHLayout.addComponents(topCustomerContentLeftLayout, topCustomerContentCenterLayout,
				topCustomerContentRightLayout);
		topCustomerHLayout.setSizeFull();
		topCustomerHLayout.setSpacing(false);
		topCustomerHLayout.setMargin(false);

		topCustomerContentLayout.addComponent(topCustomerHLayout);
		topCustomerContentLayout.setSpacing(false);
		topCustomerContentLayout.setMargin(false);
		topCustomerContentLayout.setSizeFull();

		//Call Bottom Unit/Room/Payment Detail Layout
		initDetailComponent();

		topLayout.addComponents(topCustomerTitleLayout, topCustomerContentLayout, topDetailTitleLayout,
				topDetailContentLayout);
		topLayout.setExpandRatio(topCustomerTitleLayout, 0.3f);
		topLayout.setExpandRatio(topCustomerContentLayout, 1.5f);
		topLayout.setExpandRatio(topDetailTitleLayout, 0.3f);
		topLayout.setExpandRatio(topDetailContentLayout, 2.5f);
		topLayout.setSpacing(false);
		topLayout.setMargin(false);
		topLayout.setSizeFull();

		bottomLayout.addStyleName("menulayout");
		bottomLayout.setSpacing(false);
		bottomLayout.setMargin(false);
		bottomLayout.setSizeFull();

		mainLayout.addComponents(topLayout, bottomLayout);
		mainLayout.setExpandRatio(topLayout, 3);
		mainLayout.setExpandRatio(bottomLayout, 1);
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);
		mainLayout.setSizeFull();

		addComponent(mainLayout);
		setSizeFull();
	}

	private void initComponents() {

		/*binderCustomer = new Binder<>();
		binderUnit = new Binder<>();
		binderContract = new Binder<>();*/

		lblCustomerTitle = new Label("Customer Details");
		lblCustomerTitle.addStyleName("customerstyle");
		lblUnitPaymentTitle = new Label("Unit/Room and Payment Details");
		lblUnitPaymentTitle.addStyleName("customerstyle");

		customerNameField = new TextField("Customer Name :");

		jobField = new TextField("Job :");

		phoneField = new TextField("Phone :");

		paymentField = new TextField();

		termField = new TextField();

		addressTextArea = new TextArea("Address :");
		addressTextArea.setWidth("-1px");
		addressTextArea.setHeight("53px");

		cboGender = new ComboBox<>("Marital Status :");

		dobDateField = new DateField("Date Of Birth :");
		cboFloor = new ComboBox<>("Floor Number :");
		cboUnit = new ComboBox<>("Unit Number :");
		startDateField = new DateField("Start Date :");
		endDateField = new DateField("End Date :");
		
		grid = new Grid<>();
		grid.setWidth("1110px");
		grid.setHeight("150px");

		List<String> genders = Arrays.asList("Male", "Female");
		cboGender.setItems(genders);
	}

	private void initDetailComponent() {
		// Top Detail Left
		topDetailLeftFormLayout.addComponents(cboFloor, termField);
		topDetailLeftFormLayout.setSizeFull();
		topDetailLeftFormLayout.setMargin(false);
		topDetailContentLeftLayout.addComponent(topDetailLeftFormLayout);
		topDetailContentLeftLayout.setSizeFull();
		topDetailContentLeftLayout.setSpacing(false);
		topDetailContentLeftLayout.setMargin(false);

		// Top Detail Center
		topDetailCenterFormLayout.addComponents(cboUnit, startDateField);
		topDetailCenterFormLayout.setSizeFull();
		topDetailCenterFormLayout.setMargin(false);
		topDetailContentCenterLayout.addComponent(topDetailCenterFormLayout);
		topDetailContentCenterLayout.setSpacing(false);
		topDetailContentCenterLayout.setMargin(false);

		// Top Detail Right
		topDetailRightFormLayout.addComponents(paymentField, endDateField);
		topDetailRightFormLayout.setSizeFull();
		topDetailRightFormLayout.setMargin(false);
		topDetailContentRightLayout.addComponent(topDetailRightFormLayout);
		topDetailContentRightLayout.setSpacing(false);
		topDetailContentRightLayout.setMargin(false);

		topDetailHLayout.addComponents(topDetailContentLeftLayout, topDetailContentCenterLayout,
				topDetailContentRightLayout);
		topDetailHLayout.setSizeFull();
		topDetailHLayout.setSpacing(false);
		topDetailHLayout.setMargin(false);

		topDetailContentLayout.addComponent(topDetailHLayout);
		topDetailContentLayout.setSpacing(false);
		topDetailContentLayout.setMargin(false);
		topDetailContentLayout.setSizeFull();
	}
}
