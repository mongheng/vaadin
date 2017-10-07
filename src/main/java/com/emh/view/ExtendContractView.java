package com.emh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.CarParking;
import com.emh.model.Customer;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ExtendContractView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;

	private Grid<Customer> grid;
	private ListDataProvider<Customer> customerDataProvider;

	private Grid<CarParking> parkingGrid;
	private ListDataProvider<CarParking> parkingDataProvider;

	public ExtendContractView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}

	private void init() {
		grid = new Grid<>();
		customerDataProvider = new ListDataProvider<>(new ArrayList<>());

		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		List<Customer> customers = (List<Customer>) classBusiness.selectAllEntity(Customer.class);

		if (customers.size() > 0) {
			customers.forEach(customer -> {
				if (customer.isClose()) {
					customerDataProvider.getItems().add(customer);
				}
			});
		}

		grid.setDataProvider(customerDataProvider);

		grid.addColumn(customer -> customer.getCustomerName()).setCaption("Customer Name");
		grid.addColumn(customer -> customer.getGender()).setCaption("Marital Statu");
		grid.addColumn(customer -> customer.getPhoneNumber()).setCaption("Phone Number");
		grid.addColumn(customer -> customer.getUnit().getFloor().getFloorNumber().toString())
				.setCaption("Floor Number");
		grid.addColumn(customer -> customer.getUnit().getUnitNumber().toString()).setCaption("Unit Number");

		grid.setSizeFull();

		initParkingGrid();
		
		addComponents(grid, parkingGrid);
		setSizeFull();

		grid.addItemClickListener(itemClick -> {
			UI.getCurrent().addWindow(new ExtendContractForm(applicationContext, itemClick.getItem()));
		});
	}

	private void initParkingGrid() {
		parkingGrid = new Grid<>();
		parkingDataProvider = new ListDataProvider<>(new ArrayList<>());

		classBusiness = applicationContext.getBean(ClassBusiness.class);
		List<CarParking> carParkings = classBusiness.selectAllEntity(CarParking.class);

		if (carParkings.size() > 0) {
			carParkings.forEach(carParking -> {
				if (carParking.isClose()) {
					parkingDataProvider.getItems().add(carParking);
				}
			});
		}
		parkingGrid.setDataProvider(parkingDataProvider);

		parkingGrid.addColumn(carParking -> {
			return carParking.getCustomer().getCustomerName();
		}).setCaption("Customer Name");
		
		parkingGrid.addColumn(CarParking::getCarType).setCaption("Vehicle Name");
		parkingGrid.addColumn(CarParking::getPlantNumber).setCaption("Plant Number");
		parkingGrid.addColumn(carParking -> {
			return carParking.getCustomer().getUnit().getFloor().getFloorNumber();
		}).setCaption("Floor Number");
		
		parkingGrid.addColumn(carParking -> {
			return carParking.getCustomer().getUnit().getUnitNumber();
		}).setCaption("Unit Number");
		
		parkingGrid.setSizeFull();
		
		parkingGrid.addItemClickListener(itemClick -> {
			UI.getCurrent().addWindow(new ExtendParkingContractForm(applicationContext, itemClick.getItem()));
		});
	}
}
