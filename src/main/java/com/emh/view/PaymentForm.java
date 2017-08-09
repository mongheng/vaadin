package com.emh.view;

import org.springframework.context.ApplicationContext;

import com.emh.model.CashFlow;
import com.emh.model.Payment;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PaymentForm extends Window {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private CashFlow cashFlow;
	private VerticalLayout vLayout;
	private HorizontalLayout hLayout;
	private FormLayout formLayout;
	
	private boolean pay;
	
	private Binder<Payment> binder;

	public PaymentForm(ApplicationContext applicationContext, CashFlow cashFlow) {
		super();
		this.applicationContext = applicationContext;
		this.cashFlow = cashFlow;

		init();
	}

	private void init() {
		vLayout = new VerticalLayout();
		hLayout = new HorizontalLayout();
		formLayout = new FormLayout();
		
		binder = new Binder<>();
		
		TextField customerNameField = new TextField();
		customerNameField.setCaption("Customer Name : ");
		customerNameField.setValue(cashFlow.getContract().getCustomer().getCustomerName());
		customerNameField.setEnabled(false);
		
		TextField installmentField = new TextField();
		installmentField.setCaption("Installment Number : ");
		installmentField.setValue(cashFlow.getInstallmentNumber().toString());
		installmentField.setEnabled(false);
		binder.forField(installmentField).withConverter(new StringToIntegerConverter("Please input Number."))
			.bind(Payment::getInstallmentNumber, Payment::setInstallmentNumber);
		
		TextField floorNumberField = new TextField();
		floorNumberField.setCaption("Floor Number : ");
		floorNumberField.setValue(cashFlow.getContract().getCustomer().getUnit().getFloor().getFloorNumber().toString());
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
		btnPay.setCaption("Paied");
		btnPay.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnPay.addClickListener(clickEvent -> {
			classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
			Payment payment = new Payment();
			try {
				binder.writeBean(payment);
				pay = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		
		formLayout.addComponents(customerNameField, installmentField, floorNumberField, unitNumberField, amountField, hLayout);
		
		vLayout.addComponent(formLayout);
		vLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		setContent(vLayout);
		setModal(true);
		setCaption("Payment From");
		center();
		setWidth("450px");
		setHeight("455px");

	}

	public boolean isPay() {
		return pay;
	}

	public void setPay(boolean pay) {
		pay = pay;
	}
}
