package com.emh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Customer;
import com.emh.repository.business.ClassBusiness;
import com.emh.view.tab.TabCustomer;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.VerticalLayout;

public class CustomersGirdView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private TabCustomer tabCustomer;
	private Customer source;
	
	private Grid<Customer> grid;
	private ListDataProvider<Customer> customerDataProvider;
	
	public CustomersGirdView(ApplicationContext applicationContext, TabCustomer tabCustomer) {
		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
		this.tabCustomer = tabCustomer;
		initGrid();
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	private void initGrid() {
		List<Customer> customers = (List<Customer>) classBusiness.selectAllEntity(Customer.class);
		grid = new Grid<>();
		
		if (customers.size() > 0) {
			customerDataProvider = new ListDataProvider<>(customers);
		} else {
			customerDataProvider = new ListDataProvider<>(new ArrayList<>());
		}
		grid.setDataProvider(customerDataProvider);
		
		Column<Customer, String> columnName = grid.addColumn(customer -> customer.getCustomerName());
		columnName.setCaption("Customer Name");
		
		Column<Customer, String> columnGender = grid.addColumn(customer -> customer.getGender());
		columnGender.setCaption("Marital Status");
		
		Column<Customer, String> columnPhone = grid.addColumn(customer -> customer.getPhoneNumber());
		columnPhone.setCaption("Phone Number");
		
		Column<Customer, Integer> columnFloor = grid.addColumn(customer -> customer.getUnit().getFloor().getFloorNumber());
		columnFloor.setCaption("Floor Number");

		Column<Customer, Integer> columnUnitNumber = grid.addColumn(customer -> customer.getUnit().getUnitNumber());
		columnUnitNumber.setCaption("Unit Number");
		
		addComponent(grid);
		
		grid.addSelectionListener(listener -> {
			grid.getSelectedItems().forEach(customer -> {
				source = customer;
			});
			tabCustomer.removeTab(tabCustomer.getTab(1));
			tabCustomer.addTab(new CustomerFormView(applicationContext, source), "Customer", null, 1);
			tabCustomer.setSelectedTab(1);
		});
	}
}
