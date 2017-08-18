package com.emh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.CarParking;
import com.emh.model.Customer;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CarParkingView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private Customer customer;
	private List<CarParking> currectCarParkingCustomers;
	private ListDataProvider<CarParking> dataProvider;

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

	public CarParkingView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}

	private void init() {
		formLayout = new FormLayout();
		hLayout = new HorizontalLayout();
		grid = new Grid<>();

		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());

		cboCustomer = new ComboBox<>("Please Select Customer :");
		carTypeField = new TextField("Car Type :");
		plantNumberField = new TextField("Plant Number :");
		amountField = new TextField("Amount :");

		ckbFree = new CheckBox("Free");
		ckbFree.setValue(true);
		ckbFree.addValueChangeListener(valueChange -> {
			if (!ckbFree.getValue()) {
				amountField.clear();
				amountField.setEnabled(false);
			} else {
				amountField.setEnabled(true);
			}
		});

		btnSave = new Button("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);

		btnNew = new Button("New");
		btnNew.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnNew.addClickListener(clickEvent -> {
			clear();
		});

		setValueCustomer();

		hLayout.addComponents(btnSave, btnNew);
		hLayout.setComponentAlignment(btnSave, Alignment.MIDDLE_CENTER);
		formLayout.addComponents(cboCustomer, carTypeField, plantNumberField, amountField, ckbFree, hLayout);
		formLayout.setSpacing(false);
		
		initColumnGrid();
		grid.setSizeFull();
		addComponents(formLayout, grid);
		setCaption("Car Parking");
		setSizeFull();
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

		Column<CarParking, Float> columnAmount = grid.addColumn(CarParking::getAmount);
		columnAmount.setCaption("Amount");
	}

	private void clear() {
		carTypeField.clear();
		plantNumberField.clear();
		amountField.clear();
		ckbFree.setValue(false);
	}
}
