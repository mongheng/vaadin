package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import com.emh.model.CashFlow;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DashboardView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	
	private ListDataProvider<CashFlow> cashflowDataProvider;
	private Grid<CashFlow> grid;

	public DashboardView(ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
		init();
	}
	
	private void init() {
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		
		grid = new Grid<>();
		
		List<CashFlow> cashflows = classBusiness.selectListEntityByHQL(CashFlow.class,"from CashFlow where STATU = false and (STARTDATE <= '" + LocalDate.now() + "' and ENDDATE >= '" + LocalDate.now() + "')");
		if (cashflows.size() > 0) {
			cashflowDataProvider = new ListDataProvider<>(cashflows);
		} else {
			cashflowDataProvider = new ListDataProvider<>(new ArrayList<>());
		}
		
		grid.setDataProvider(cashflowDataProvider);
		grid.setSizeFull();
		initColumnGrid();
		
		addComponent(grid);
		
		grid.addContextClickListener(clickEvent -> {
			grid.getSelectedItems().forEach(cashflow -> {
				UI.getCurrent().addWindow(new PaymentForm(applicationContext, cashflow));
			});;
			
		});
	}
	
	private void initColumnGrid() {
		
		Column<CashFlow, String> columnCustomerName = grid.addColumn(cashflow -> cashflow.getContract().getCustomer().getCustomerName());
		columnCustomerName.setCaption("Customer Name");
		
		Column<CashFlow, Integer> columnFloor = grid.addColumn(cashflow -> cashflow.getContract().getCustomer().getUnit().getFloor().getFloorNumber());
		columnFloor.setCaption("Floor Number");
		
		Column<CashFlow, Integer> columnUnit = grid.addColumn(cashflow -> cashflow.getContract().getCustomer().getUnit().getUnitNumber());
		columnUnit.setCaption("Unit Number");
		
		Column<CashFlow, Integer> columnInstallment = grid.addColumn(cashflow -> cashflow.getInstallmentNumber());
		columnInstallment.setCaption("Installment Number");
		
		Column<CashFlow, Float> columnAmount = grid.addColumn(cashflow -> cashflow.getAmount());
		columnAmount.setCaption("Amount");
		
		Column<CashFlow, LocalDate> columnStartDate = grid.addColumn(cashflow -> cashflow.getStartDate());
		columnStartDate.setCaption("Start Date");
		
		Column<CashFlow, LocalDate> columnEndDate = grid.addColumn(cashflow -> cashflow.getEndDate());
		columnEndDate.setCaption("End Date");
	}
}
