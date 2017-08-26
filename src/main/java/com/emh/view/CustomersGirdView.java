package com.emh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Customer;
import com.emh.repository.business.ClassBusiness;
import com.emh.view.tab.TabCustomer;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;

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
		customerDataProvider = new ListDataProvider<>(new ArrayList<>());
		List<Customer> customers = (List<Customer>) classBusiness.selectAllEntity(Customer.class);
		grid = new Grid<>();
		
		if (customers.size() > 0) {
			customers.forEach(customer -> {
				if (!customer.isClose()) {
					customerDataProvider.getItems().add(customer);
				}
			});
		}
		
		grid.setDataProvider(customerDataProvider);
		
		Column<Customer, String> columnName = grid.addColumn(customer -> customer.getCustomerName());
		columnName.setCaption("Customer Name");
		columnName.setId("0");
		
		Column<Customer, String> columnGender = grid.addColumn(customer -> customer.getGender());
		columnGender.setCaption("Marital Status");
		columnGender.setId("1");
		
		Column<Customer, String> columnPhone = grid.addColumn(customer -> customer.getPhoneNumber());
		columnPhone.setCaption("Phone Number");
		columnPhone.setId("2");
		
		Column<Customer, Integer> columnFloor = grid.addColumn(customer -> customer.getUnit().getFloor().getFloorNumber());
		columnFloor.setCaption("Floor Number");
		columnFloor.setId("3");
		
		Column<Customer, Integer> columnUnitNumber = grid.addColumn(customer -> customer.getUnit().getUnitNumber());
		columnUnitNumber.setCaption("Unit Number");
		columnUnitNumber.setId("4");
		
		setFilterGrid(customerDataProvider);
		grid.setSizeFull();
		grid.setSortOrder(GridSortOrder.asc(columnName));
		addComponent(grid);
		setSizeFull();
		
		grid.addSelectionListener(listener -> {
			grid.getSelectedItems().forEach(customer -> {
				source = customer;
			});
			tabCustomer.removeTab(tabCustomer.getTab(1));
			tabCustomer.addTab(new CustomerFormView(applicationContext, source), "Customer", null, 1);
			tabCustomer.setSelectedTab(1);
		});
	}
	
	private void setFilterGrid(ListDataProvider<Customer> customerDataProvider) {
		HeaderRow headerRow = grid.prependHeaderRow();
		
		for (Column<Customer, ?> column : grid.getColumns()) {
			HeaderCell headerCell = headerRow.getCell(column);
			headerCell.setComponent(createTextFieldFilter(customerDataProvider, column));
		}
	}
	
	private TextField createTextFieldFilter(ListDataProvider<Customer> customerDataProvider, Column<Customer, ?> column) {
		TextField filterField = new TextField();
		filterField.setHeight("26px");
		filterField.setWidth("100%");

		filterField.addValueChangeListener(change -> {
			String filterText = change.getValue();
			List<Customer> newFilterCustomer = new ArrayList<>();
			List<Customer> filterCustomer = (List<Customer>) customerDataProvider.getItems();
			filterCustomer.forEach(customerfilter -> {

				switch (column.getId()) {
				case "0":
					if (customerfilter.getCustomerName().contains(filterText)) {
						newFilterCustomer.add(customerfilter);
					}
					break;
				case "1":
					if (customerfilter.getGender().contains(filterText)) {
						newFilterCustomer.add(customerfilter);
					}
					break;
				case "2":
					if (customerfilter.getPhoneNumber().contains(filterText)) {
						newFilterCustomer.add(customerfilter);
					}
					break;
				case "3":
					if (customerfilter.getUnit().getFloor().getFloorNumber().toString().contains(filterText)) {
						newFilterCustomer.add(customerfilter);
					}
					break;
				case "4":
					if (customerfilter.getUnit().getUnitNumber().toString().contains(filterText)) {
						newFilterCustomer.add(customerfilter);
					}
					break;
				default:
					break;
				}

			});
			grid.setDataProvider(new ListDataProvider<>(newFilterCustomer));
		});
		return filterField;
	}
}
