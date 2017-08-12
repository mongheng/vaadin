package com.emh.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Contract;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.Column;

public class ContractListView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;

	private TabSheet tabSheet;
	private Grid<Contract> grid;
	private ListDataProvider<Contract> contractDataProvider;

	public ContractListView(ApplicationContext applicationContext, TabSheet tabSheet) {
		this.applicationContext = applicationContext;
		this.tabSheet = tabSheet;
		init();
	}

	private void init() {
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		grid = new Grid<>();
		List<Contract> contracts = (List<Contract>) classBusiness.selectAllEntity(Contract.class);
		contractDataProvider = new ListDataProvider<>(new ArrayList<>());
		if (contracts.size() > 0) {
			contracts.forEach(contract -> {
				if (contract.isActive()) {
					if (!contract.getCustomer().isClose()) {
						contractDataProvider.getItems().add(contract);
					}
				}
			});
		}

		grid.setDataProvider(contractDataProvider);
		grid.setSizeFull();
		initGridColumn();

		setSizeFull();
		addComponent(grid);
		setCaption("Contracts");

		grid.addItemClickListener(listener -> {
			Contract contract = listener.getItem();

			if (contract != null) {
				tabSheet.addTab(new ContractDetailView(applicationContext, contract), 1).setId("1");

				tabSheet.setSelectedTab(1);
			}
		});
	}

	private void initGridColumn() {

		Column<Contract, String> columnCustomerName = grid
				.addColumn(contract -> contract.getCustomer().getCustomerName());
		columnCustomerName.setCaption("Customer Name");
		columnCustomerName.setId("0");

		Column<Contract, String> columnPhone = grid.addColumn(contract -> contract.getCustomer().getPhoneNumber());
		columnPhone.setCaption("Phone");
		columnPhone.setId("1");

		Column<Contract, LocalDate> columnDate = grid.addColumn(contract -> contract.getCustomer().getDob());
		columnDate.setCaption("Date of Birth");
		columnDate.setId("2");

		Column<Contract, Integer> columnFloor = grid
				.addColumn(contract -> contract.getCustomer().getUnit().getFloor().getFloorNumber());
		columnFloor.setCaption("Floor Number");
		columnFloor.setId("3");
	}
}
