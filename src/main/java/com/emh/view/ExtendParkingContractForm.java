package com.emh.view;

import java.time.LocalDate;

import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.CarParking;
import com.emh.model.Contract;
import com.emh.model.Customer;
import com.emh.model.ParkingCashFlow;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ExtendParkingContractForm extends Window {

	private static final long serialVersionUID = 1L;

	private ClassBusiness classBusiness;
	private CarParking carParking;
	private Customer customer;
	private Contract contract;
	private ParkingCashFlow parkingCashFlow;

	private VerticalLayout vlayout;
	private FormLayout formLayout;
	private HorizontalLayout horizontalLayout;
	private TextField nameField;
	private TextField vehicleField;
	private TextField plantNumberField;
	private TextField termField;
	private DateField startDate;
	private DateField endDate;
	private Button btnExtend;
	private Button btnCancel;

	public ExtendParkingContractForm(ClassBusiness classBusiness, CarParking carParking) {
		this.classBusiness = classBusiness;
		this.carParking = carParking;
		init();
	}

	private void init() {
		customer = carParking.getCustomer();

		vlayout = new VerticalLayout();
		formLayout = new FormLayout();
		horizontalLayout = new HorizontalLayout();

		nameField = new TextField("Customer Name :");
		nameField.setValue(customer.getCustomerName());
		nameField.setEnabled(false);

		vehicleField = new TextField("Vehicle Name :");
		vehicleField.setValue(carParking.getCarType());
		vehicleField.setEnabled(false);

		plantNumberField = new TextField("Plant Number :");
		plantNumberField.setValue(carParking.getPlantNumber());
		plantNumberField.setEnabled(false);

		termField = new TextField("New Term :");
		termField.addValueChangeListener(valueChange -> {
			if (!termField.getValue().isEmpty()) {
				endDate.setValue(startDate.getValue().plusMonths(Integer.parseInt(termField.getValue())));
			}
		});

		startDate = new DateField("Start End :");
		startDate.setValue(LocalDate.now());
		startDate.addValueChangeListener(valueChange -> {
			if (!termField.getValue().isEmpty()) {
				endDate.setValue(startDate.getValue().plusMonths(Integer.parseInt(termField.getValue())));
			}
		});

		endDate = new DateField("End Date :");

		btnExtend = new Button("Extend");
		btnExtend.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnExtend.addClickListener(clickEvent -> {
			ConfirmDialog.show(UI.getCurrent(), "Confirmation",
					"Are you sure " + customer.getCustomerName() + "wants to extend this car parking contract?", "Yes",
					"No", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								if (!customer.isClose()) {
									parkingCashFlow = (ParkingCashFlow) classBusiness
											.selectEntityByHQL("From ParkingCashFlow WHERE CARPARKING_ID = '"
													+ carParking.getCarparkingID() + "'");
									contract = (Contract) classBusiness.selectEntityByHQL(
											"From Contract WHERE CUSTOMER_ID = '" + customer.getCustomerID() + "'");
									int term = contract.getTerm();
									int newTerm = Integer.parseInt(termField.getValue());
									int startTerm = term - newTerm;
									if (startTerm > 0) {
										LocalDate tempEndDate = null;
										for (int i = 1; i <= newTerm; i++) {
											ParkingCashFlow newCashFlow = new ParkingCashFlow();
											if (i == 1) {
												newCashFlow.setStartDate(startDate.getValue());
												tempEndDate = startDate.getValue().plusMonths(i);
												newCashFlow.setEndDate(tempEndDate);
												newCashFlow.setAmount(parkingCashFlow.getAmount());
												newCashFlow.setInstallmentNumber(startTerm + i);
												newCashFlow.setCarparking(carParking);
											} else {
												newCashFlow.setStartDate(tempEndDate);
												tempEndDate = tempEndDate.plusMonths(1);
												newCashFlow.setEndDate(tempEndDate);
												newCashFlow.setAmount(parkingCashFlow.getAmount());
												newCashFlow.setInstallmentNumber(startTerm + i);
												newCashFlow.setCarparking(carParking);
											}
											classBusiness.createEntity(newCashFlow);
										}
										carParking.setClose(false);
										classBusiness.updateEntity(carParking);
										Notification.show(customer.getCustomerName() + ", " + carParking.getCarType() + ", " + carParking.getPlantNumber() + " have been Extended Contract.");
										close();
									} else {
										Notification.show("The new term can not bigger than old term (" + newTerm + " > " + term + "). Please input again.");
										termField.focus();
									}
								} else {
									Notification.show(customer.getCustomerName()
											+ " have not been yet Extended Contract. Please Extended Contract before Extending Car Parking");
								}
							}
						}
					});
		});

		btnCancel = new Button("Cancel");
		btnCancel.addClickListener(listener -> {
			close();
		});

		horizontalLayout.addComponents(btnExtend, btnCancel);
		horizontalLayout.setComponentAlignment(btnExtend, Alignment.TOP_CENTER);
		horizontalLayout.setComponentAlignment(btnCancel, Alignment.TOP_CENTER);

		formLayout.addComponents(nameField, vehicleField, plantNumberField, termField, startDate, endDate,
				horizontalLayout);
		formLayout.setSizeFull();
		formLayout.setComponentAlignment(termField, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(vehicleField, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(plantNumberField, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(startDate, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(endDate, Alignment.TOP_CENTER);

		vlayout.addComponent(formLayout);
		vlayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);

		setContent(vlayout);
		setWidth(380, Unit.PIXELS);
		setHeight(428, Unit.PIXELS);
		setCaption("Extend Car Parking Form");
		setModal(true);
		setResizable(false);
		center();
	}
}
