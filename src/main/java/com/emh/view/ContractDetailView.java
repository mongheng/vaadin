package com.emh.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;

import com.emh.model.CashFlow;
import com.emh.model.Contract;
import com.emh.model.Customer;
import com.emh.model.User;
import com.emh.model.mock.MockCashFlow;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.ReportUtil;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ContractDetailView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private Contract contract;
	private Customer customer;
	private User user;
	
	private HorizontalLayout hLayout;
	
	private TextField customerNameField;
	private TextField floorField;
	private TextField unitField;
	
	private Button btnExport;
	
	private FileDownloader fileDownloader;

	private ListDataProvider<CashFlow> cashflowDataProvider;
	private Grid<CashFlow> grid;

	public ContractDetailView(ApplicationContext applicationContext, Contract contract) {
		super();
		this.applicationContext = applicationContext;
		this.contract = contract;

		init();
	}

	private void init() {
		user = (User) UI.getCurrent().getSession().getAttribute(User.class);
		hLayout = new HorizontalLayout();
		grid = new Grid<>();
		
		customer = contract.getCustomer();
		
		customerNameField = new TextField("Customer Name");
		customerNameField.setValue(customer.getCustomerName());
		customerNameField.setReadOnly(true);
		customerNameField.addStyleName("textnoncenter");
		
		floorField = new TextField("Floor Number");
		floorField.setValue(customer.getUnit().getFloor().getFloorNumber().toString());
		floorField.setReadOnly(true);
		floorField.addStyleName("textnoncenter");

		unitField = new TextField("Unit Number");
		unitField.setValue(customer.getUnit().getUnitNumber().toString());
		unitField.setReadOnly(true);
		unitField.addStyleName("textnoncenter");
		
		btnExport = new Button("Export");
		btnExport.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		hLayout.addComponents(customerNameField, floorField, unitField, btnExport);
		hLayout.setComponentAlignment(btnExport, Alignment.BOTTOM_LEFT);
		
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

		addComponents(hLayout, grid);
		setExpandRatio(hLayout, 0.1f);
		setExpandRatio(grid, 0.9f);
		setSizeFull();
		setCaption("Contract");
		
		ExportFile(cashflows);

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
	
	private void ExportFile(List<CashFlow> cashFlows) {
		String info = customer.getCustomerName() + "- F:" + customer.getUnit().getFloor().getFloorNumber() + "- R/U:" + customer.getUnit().getUnitNumber();
		String path = "c:/dailyReport/" + user.getUsername() + "/dailyReport";
		
		List<MockCashFlow> mockCashFlows = cashFlows.stream().map(cashflow -> {
			MockCashFlow mcf = new MockCashFlow();
			mcf.setAmount(cashflow.getAmount());
			mcf.setCashflowID(cashflow.getCashflowID());
			mcf.setContract(cashflow.getContract());
			mcf.setEndDate(cashflow.getEndDate().toString());
			mcf.setInstallmentNumber(cashflow.getInstallmentNumber());
			mcf.setStartDate(cashflow.getStartDate().toString());
			mcf.setStatu(cashflow.getStatu());
			return mcf;
		}).collect(Collectors.toList());
		
		ReportUtil.createCashFlowReportPDF(mockCashFlows, user , info, path, ReportUtil.PDF); 
		ReportUtil.StreamResourceData streamSource = new ReportUtil.StreamResourceData(path, ReportUtil.PDF);
		StreamResource sr = new StreamResource(streamSource, "Cashflow-" + customer.getCustomerName() + ReportUtil.PDF);
		fileDownloader = new FileDownloader(sr);
		fileDownloader.extend(btnExport);
	}
}
