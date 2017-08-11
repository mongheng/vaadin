package com.emh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.CashFlow;
import com.emh.model.Contract;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ContractDetailView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private Contract contract;

	private ListDataProvider<CashFlow> cashflowDataProvider;
	private Grid<CashFlow> grid;

	public ContractDetailView(ApplicationContext applicationContext, Contract contract) {
		super();
		this.applicationContext = applicationContext;
		this.contract = contract;

		init();
	}

	private void init() {
		grid = new Grid<>();
		cashflowDataProvider = new ListDataProvider<>(new ArrayList<>());
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		String HQL = "FROM CashFlow WHERE CONTRACT_ID = '" + contract.getContractID() + "'";
		List<CashFlow> cashflows = classBusiness.selectListEntityByHQL(CashFlow.class, HQL);

		if (cashflows.size() > 0) {
			cashflowDataProvider = new ListDataProvider<>(cashflows);
		}

		grid.setDataProvider(cashflowDataProvider);
		grid.setSizeFull();

		initColumnGrid();

		addComponent(grid);
		setSizeFull();
		setCaption("Contract");

		grid.addItemClickListener(itemClick -> {
			CashFlow cashFlow = (CashFlow) itemClick.getItem();
			int installment = cashFlow.getInstallmentNumber();
			if (!cashFlow.getStatu()) {
				if (installment == 1) {
					UI.getCurrent().addWindow(new PaymentForm(applicationContext, cashFlow));
				} else {
					installment = installment - 1;
					String hql = "FROM CashFlow WHERE INSTALLMENT_NUMBER = " + installment + " and CONTRACT_ID = '"
							+ contract.getContractID() + "'";
					CashFlow cflow = (CashFlow) classBusiness.selectEntityByHQL(hql);
					if (cflow.getStatu()) {
						UI.getCurrent().addWindow(new PaymentForm(applicationContext, cashFlow));
					} else {
						Notification.show("Please paid by order.", Type.WARNING_MESSAGE);
					}
				}
			} else {
				Notification.show("This month already paid.", Type.WARNING_MESSAGE);
			}
		});
	}

	private void initColumnGrid() {

		grid.addColumn(cashflow -> cashflow.getInstallmentNumber()).setCaption("Installment Number");
		grid.addColumn(cashflow -> cashflow.getAmount()).setCaption("Amount");
		grid.addColumn(cashflow -> cashflow.getStartDate()).setCaption("Start Date");
		grid.addColumn(cashflow -> cashflow.getEndDate()).setCaption("End Date");
		grid.addColumn(cashflow -> {
			if (cashflow.getStatu()) {
				return "X";
			} else {
				return "";
			}
		}).setCaption("Statu");
	}
}
