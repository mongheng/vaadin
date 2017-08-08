package com.emh.view;

import org.springframework.context.ApplicationContext;

import com.emh.model.CashFlow;
import com.vaadin.ui.Window;

public class PaymentForm extends Window {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private CashFlow cashFlow;

	public PaymentForm(ApplicationContext applicationContext, CashFlow cashFlow) {
		super();
		this.applicationContext = applicationContext;
		this.cashFlow = cashFlow;
		
		init();
	}

	private void init() {
		setCaption("Payment From");
		center();
		setWidth("560px");
		setHeight("455px");

		System.out.println(
				cashFlow.getContract().getCustomer().getCustomerName() + ", " + cashFlow.getInstallmentNumber() + ","
						+ cashFlow.getAmount() + "," + cashFlow.getContract().getCustomer().getUnit().getUnitNumber()
						+ ", " + cashFlow.getContract().getCustomer().getUnit().getFloor().getFloorNumber());
	}
}
