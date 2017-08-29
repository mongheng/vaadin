package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.CarParking;
import com.emh.model.Customer;
import com.emh.model.ParkingCashFlow;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;

public class CarParkingContractView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private Customer customer;

	public CarParkingContractView(ApplicationContext applicationContext, Customer customer) {
		this.applicationContext = applicationContext;
		this.customer = customer;
		init();
	}

	private void init() {
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		List<CarParking> carParkings = classBusiness.selectListEntity(CarParking.class, Customer.class, "customerID",
				customer.getCustomerID().toString());
		carParkings.forEach(carParking -> {
			String cellCaption = carParking.getCarType() + " - " + carParking.getPlantNumber();
			Grid<ParkingCashFlow> grid = new Grid<>();
			ListDataProvider<ParkingCashFlow> dataProvider = new ListDataProvider<>(new ArrayList<>());
			if (carParking.isActivated()) {

				List<ParkingCashFlow> parkingCashFlows = classBusiness.selectListEntity(ParkingCashFlow.class,
						CarParking.class, "carparkingID", carParking.getCarparkingID());

				dataProvider = parkingCashFlows.size() > 0 ? new ListDataProvider<>(parkingCashFlows)
						: new ListDataProvider<>(new ArrayList<>());

				grid.setDataProvider(dataProvider);
				initColumnGrid(grid, cellCaption);
			}
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

		grid.setSizeFull();

		HeaderRow row = grid.prependHeaderRow();
		HeaderCell cell = row.join(row.getCell("0"), row.getCell("1"), row.getCell("2"), row.getCell("3"),
				row.getCell("4"));
		cell.setText(cellCaption);
		addComponent(grid);
	}
}
