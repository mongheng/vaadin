package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.CarParking;
import com.emh.model.Customer;
import com.emh.model.ParkingCashFlow;
import com.emh.model.Payment;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Page;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;

public class CarParkingContractView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private Customer customer;
	private Grid<ParkingCashFlow> grid;
	ListDataProvider<ParkingCashFlow> dataProvider;

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
			grid = new Grid<>();
			dataProvider = new ListDataProvider<>(new ArrayList<>());
			if (carParking.isActivated()) {

				List<ParkingCashFlow> parkingCashFlows = classBusiness.selectListEntity(ParkingCashFlow.class,
						CarParking.class, "carparkingID", carParking.getCarparkingID());

				dataProvider = parkingCashFlows.size() > 0 ? new ListDataProvider<>(parkingCashFlows)
						: new ListDataProvider<>(new ArrayList<>());

				grid.setDataProvider(dataProvider);
				initColumnGrid(grid, cellCaption);
			}

			grid.addItemClickListener(itemClick -> {
				
				
			});
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
		
		Column<ParkingCashFlow, String> columnButtonPaid = grid.addColumn(btnPaid -> "Paid", new ButtonRenderer<>(clickEvent -> {
			ParkingCashFlow parkingCashFlow = clickEvent.getItem();
			int installmentNumber = parkingCashFlow.getInstallmentNumber();
			if (!parkingCashFlow.isStatu()) {
				if (installmentNumber == 1) {
					payment(parkingCashFlow);
				} else {
					installmentNumber = installmentNumber - 1;
					String HQL = "FROM ParkingCashFlow WHERE INSTALLMENT_NUMBER = " + installmentNumber
							+ " and CARPARKING_ID = '" + parkingCashFlow.getCarparking().getCarparkingID() + "'";
					ParkingCashFlow pcf = (ParkingCashFlow) classBusiness.selectEntityByHQL(HQL);
					if (pcf.isStatu()) {
						payment(parkingCashFlow);
					} else {
						Notification.show("Please paid by order.", Type.WARNING_MESSAGE);
					}
				}
			} else {
				Notification.show("This month already paid.", Type.WARNING_MESSAGE);
			}
		}));
		columnButtonPaid.setCaption("Action Pay");
		columnButtonPaid.setId("5");
		grid.setSizeFull();

		HeaderRow row = grid.prependHeaderRow();
		HeaderCell cell = row.join(row.getCell("0"), row.getCell("1"), row.getCell("2"), row.getCell("3"),
				row.getCell("4"), row.getCell("5"));
		cell.setText(cellCaption);
		addComponent(grid);
	}
	
	private void payment(ParkingCashFlow parkingCashFlow) {
		ConfirmDialog.show(UI.getCurrent(), "Confirmation",
				"Are you sure you want to pay "
						+ parkingCashFlow.getCarparking().getCustomer().getCustomerName()
						+ ", Installment Number :" + parkingCashFlow.getInstallmentNumber() + ", CarType :"
						+ parkingCashFlow.getCarparking().getCarType() + ", PlantNumber :"
						+ parkingCashFlow.getCarparking().getPlantNumber(),
				"Yes", "No", new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							Payment payment = new Payment();
							Customer customer = parkingCashFlow.getCarparking().getCustomer();
							payment.setPaymentID(parkingCashFlow.getCashflowID());
							payment.setInstallmentNumber(parkingCashFlow.getInstallmentNumber());
							payment.setCustomerName(customer.getCustomerName());
							payment.setAmount(parkingCashFlow.getAmount());
							payment.setFloorNumber(customer.getUnit().getFloor().getFloorNumber());
							payment.setUnitNumber(customer.getUnit().getUnitNumber());
							payment.setCarType(parkingCashFlow.getCarparking().getCarType());
							payment.setPlantNumber(parkingCashFlow.getCarparking().getPlantNumber());
							payment.setPaymentDate(LocalDate.now());
							classBusiness.createEntity(payment);

							parkingCashFlow.setStatu(true);
							classBusiness.updateEntity(parkingCashFlow);
							Notification.show(parkingCashFlow.getCarparking().getCustomer()
									.getCustomerName() + ", Installment Number :"
									+ parkingCashFlow.getInstallmentNumber() + ", CarType :"
									+ parkingCashFlow.getCarparking().getCarType() + ", PlantNumber :"
									+ parkingCashFlow.getCarparking().getPlantNumber() + "has been paid.");
							
							Page.getCurrent().reload();
						} else {
							dialog.close();
						}
					}
				});
	}
}
