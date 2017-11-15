package com.emh.view.tab;

import com.emh.repository.business.ClassBusiness;
import com.emh.view.ContractListView;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

public class TabContract extends TabSheet {

	private static final long serialVersionUID = 1L;

	private ClassBusiness classBusiness;

	public TabContract(ClassBusiness classBusiness) {
		this.classBusiness = classBusiness;
		initTab();
	}

	private void initTab() {
		addTab(new ContractListView(classBusiness, this), 0).setId("0");
		setSizeFull();
		addStyleName(ValoTheme.TABSHEET_FRAMED);

		this.addSelectedTabChangeListener(selectedTab -> {
			TabSheet tabSheet = selectedTab.getTabSheet();
			Layout tabLayout = (Layout) tabSheet.getSelectedTab();
			Tab tab = tabSheet.getTab(tabLayout);
			if (tab.getId().equals("0")) {
				removeTab(tabSheet.getTab(1));
			}
		});
	}
}
