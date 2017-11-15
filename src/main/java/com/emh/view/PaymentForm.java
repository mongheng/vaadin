package com.emh.view;

import java.time.LocalDate;

import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.CashFlow;
import com.emh.model.Customer;
import com.emh.model.Payment;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.ReportUtil;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PaymentForm extends Window {

	private static final long serialVersionUID = 1L;

	private ClassBusiness classBusiness;
	private User user;
	private CashFlow cashFlow;
	private VerticalLayout vLayout;
	private HorizontalLayout hLayout;
	private FormLayout formLayout;

	private Binder<Payment> binder;

	public PaymentForm() {
		
	}
	
	public PaymentForm(ClassBusiness classBusiness, CashFlow cashFlow) {
		super();
		this.classBusiness = classBusiness;
		this.cashFlow = cashFlow;

		init();
	}

	private void init() {
		vLayout = new VerticalLayout();
		hLayout = new HorizontalLayout();
		formLayout = new FormLayout();

		binder = new Binder<>();

		user = (User) UI.getCurrent().getSession().getAttribute(User.class);
		
		TextField customerNameField = new TextField();
		customerNameField.setCaption("Customer Name : ");
		customerNameField.setValue(cashFlow.getContract().getCustomer().getCustomerName());
		customerNameField.setEnabled(false);
		binder.bind(customerNameField, Payment::getCustomerName, Payment::setCustomerName);

		TextField installmentField = new TextField();
		installmentField.setCaption("Installment Number : ");
		installmentField.setValue(cashFlow.getInstallmentNumber().toString());
		installmentField.setEnabled(false);
		binder.forField(installmentField).withConverter(new StringToIntegerConverter("Please input Number."))
				.bind(Payment::getInstallmentNumber, Payment::setInstallmentNumber);

		TextField floorNumberField = new TextField();
		floorNumberField.setCaption("Floor Number : ");
		floorNumberField
				.setValue(cashFlow.getContract().getCustomer().getUnit().getFloor().getFloorNumber().toString());
		floorNumberField.setEnabled(false);
		binder.forField(floorNumberField).withConverter(new StringToIntegerConverter("Please input Number."))
				.bind(Payment::getFloorNumber, Payment::setFloorNumber);

		TextField unitNumberField = new TextField();
		unitNumberField.setCaption("Unit Number : ");
		unitNumberField.setValue(cashFlow.getContract().getCustomer().getUnit().getUnitNumber().toString());
		unitNumberField.setEnabled(false);
		binder.forField(unitNumberField).withConverter(new StringToIntegerConverter("Please input number"))
				.bind(Payment::getUnitNumber, Payment::setUnitNumber);

		TextField amountField = new TextField();
		amountField.setCaption("Amount : ");
		amountField.setValue(cashFlow.getAmount().toString());
		amountField.setEnabled(false);
		binder.forField(amountField).withConverter(new StringToFloatConverter("Please input number"))
				.bind(Payment::getAmount, Payment::setAmount);

		Button btnPay = new Button();
		btnPay.setCaption("Paid");
		btnPay.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnPay.addClickListener(clickEvent -> {
			ConfirmDialog.show(UI.getCurrent(), "Confrimation",
					"Are you sure you want to pay " + cashFlow.getContract().getCustomer().getCustomerName(), "Yes",
					"No", new ConfirmDialog.Listener() {
				
						private static final long serialVersionUID = 1L;

						@Override
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								Payment payment = new Payment();
								String message = " have been paid.";
								try {
									binder.writeBean(payment);
									payment.setPaymentID(cashFlow.getCashflowID());
									payment.setCustomerName(cashFlow.getContract().getCustomer().getCustomerName());
									payment.setAmount(cashFlow.getAmount());
									payment.setFloorNumber(
											cashFlow.getContract().getCustomer().getUnit().getFloor().getFloorNumber());
									payment.setUnitNumber(cashFlow.getContract().getCustomer().getUnit().getUnitNumber());
									payment.setInstallmentNumber(cashFlow.getInstallmentNumber());
									payment.setStartDate(cashFlow.getStartDate().toString());
									payment.setEndDate(cashFlow.getEndDate().toString());
									payment.setPaymentDate(LocalDate.now());
									payment.setUser(user);
									cashFlow.setStatu(true);
									classBusiness.updateEntity(cashFlow);
									classBusiness.createEntity(payment);
									if (cashFlow.getInstallmentNumber().equals(cashFlow.getContract().getTerm())) {
										Customer closeCustomer = cashFlow.getContract().getCustomer();
										com.emh.model.Unit openUnit = closeCustomer.getUnit();
										openUnit.setStatu(false);
										classBusiness.updateEntity(openUnit);
										closeCustomer.setClose(true);
										classBusiness.updateEntity(closeCustomer);
										message = message + " This contract is end of these month.";
									}
									close();
									Notification.show(cashFlow.getContract().getCustomer().getCustomerName() + message);
									ReportUtil.createInvoiceReportPDF(payment, user);;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					});
		});

		Button btnCancel = new Button();
		btnCancel.setCaption("Cancel");
		btnCancel.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnCancel.addClickListener(clickEvent -> {
			this.close();
		});

		hLayout.addComponents(btnPay, btnCancel);
		hLayout.setComponentAlignment(btnCancel, Alignment.TOP_CENTER);
		hLayout.setComponentAlignment(btnPay, Alignment.TOP_CENTER);

		formLayout.addComponents(customerNameField, installmentField, floorNumberField, unitNumberField, amountField,
				hLayout);

		vLayout.addComponent(formLayout);
		vLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		setContent(vLayout);
		setModal(true);
		setCaption("Payment From");
		center();
		setWidth("450px");
		setHeight("455px");

	}
}
