package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Contract;
import com.emh.model.Customer;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;

public class ContractListView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;

	private TabSheet tabSheet;
	private Grid<Contract> grid;
	private Grid<Customer> parkingGrid;
	private ListDataProvider<Contract> contractDataProvider;
	private ListDataProvider<Customer> parkDataProvider;

	public ContractListView(ApplicationContext applicationContext, TabSheet tabSheet) {
		this.applicationContext = applicationContext;
		this.tabSheet = tabSheet;
		init();
	}

	private void init() {
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		grid = new Grid<>();
		parkingGrid = new Grid<>();
		List<Contract> contracts = (List<Contract>) classBusiness.selectAllEntity(Contract.class);
		contractDataProvider = new ListDataProvider<>(new ArrayList<>());
		if (contracts.size() > 0) {
			contracts.forEach(contract -> {
				if (contract.isActive()) {
					if (!contract.getCustomer().isClose()) {
						contractDataProvider.getItems().add(contract);
					}
				}
			});
		}

		List<Customer> customers = (List<Customer>) classBusiness.selectAllEntity(Customer.class);
		parkDataProvider = new ListDataProvider<>(new ArrayList<>());
		if (customers.size() > 0) {
			customers.forEach(customer -> {
				if (customer.isParkStatu()) {
					if (!customer.isClose()) {
						parkDataProvider.getItems().add(customer);
					}
				}
			});
		}

		grid.setDataProvider(contractDataProvider);
		grid.setSizeFull();
		grid.addStyleName("v-tabsheet-tabitem");
		initGridColumn();

		parkingGrid.setDataProvider(parkDataProvider);
		parkingGrid.setSizeFull();
		initParkGridColumn();

		setSizeFull();
		addComponents(grid, parkingGrid);
		setCaption("Contracts");

		grid.addItemClickListener(listener -> {
			Contract contract = listener.getItem();

			if (contract != null) {
				tabSheet.addTab(new ContractDetailView(applicationContext, contract), 1).setId("1");
				tabSheet.setSelectedTab(1);
			}
		});
		
		parkingGrid.addItemClickListener(itemClick -> {
			Customer customer = itemClick.getItem();
			if (customer != null) {
				tabSheet.addTab(new CarParkingContractView(applicationContext, customer, contracts), 1).setId("1");
				tabSheet.setSelectedTab(1);
			}
		});
	}

	private void initGridColumn() {

		Column<Contract, String> columnCustomerName = grid
				.addColumn(contract -> contract.getCustomer().getCustomerName());
		columnCustomerName.setCaption("Customer Name");
		columnCustomerName.setId("0");

		Column<Contract, String> columnPhone = grid.addColumn(contract -> contract.getCustomer().getPhoneNumber());
		columnPhone.setCaption("Phone");
		columnPhone.setId("1");

		Column<Contract, LocalDate> columnDate = grid.addColumn(contract -> contract.getCustomer().getDob());
		columnDate.setCaption("Date of Birth");
		columnDate.setId("2");

		Column<Contract, Integer> columnFloor = grid
				.addColumn(contract -> contract.getCustomer().getUnit().getFloor().getFloorNumber());
		columnFloor.setCaption("Floor Number");
		columnFloor.setId("3");

		grid.sort(columnCustomerName);
		
		HeaderRow row = grid.prependHeaderRow();
		HeaderCell cell = row.join(row.getCell("0"), row.getCell("1"), row.getCell("2"),row.getCell("3"));
		cell.setText("Unit/Room Monthly Fee");
	}

	private void initParkGridColumn() {
		Column<Customer, String> columnCustomerName = parkingGrid
				.addColumn(customer -> customer.getCustomerName());
		columnCustomerName.setCaption("Customer Name");
		columnCustomerName.setId("0");

		Column<Customer, String> columnPhone = parkingGrid.addColumn(customer -> customer.getPhoneNumber());
		columnPhone.setCaption("Phone");
		columnPhone.setId("1");

		Column<Customer, LocalDate> columnDate = parkingGrid.addColumn(customer -> customer.getDob());
		columnDate.setCaption("Date of Birth");
		columnDate.setId("2");

		Column<Customer, Integer> columnFloor = parkingGrid
				.addColumn(customer -> customer.getUnit().getFloor().getFloorNumber());
		columnFloor.setCaption("Floor Number");
		columnFloor.setId("3");

		parkingGrid.sort(columnCustomerName);
		
		HeaderRow row = parkingGrid.prependHeaderRow();
		HeaderCell cell = row.join(row.getCell("0"), row.getCell("1"), row.getCell("2"),row.getCell("3"));
		cell.setText("Car Parking Monthly Fee");
	}
}
