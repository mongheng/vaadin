package com.emh.view.tab;

import org.springframework.context.ApplicationContext;

import com.emh.view.ContractListView;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;

public class TabContract extends TabSheet {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationContext applicationContext;

	public TabContract(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		initTab();
	}
	
	private void initTab() {
		addTab(new ContractListView(applicationContext, this), 0).setId("0");
		setSizeFull();
		
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
