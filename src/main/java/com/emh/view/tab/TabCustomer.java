package com.emh.view.tab;

import org.springframework.context.ApplicationContext;

import com.emh.view.CustomerFormView;
import com.emh.view.CustomersGirdView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabCustomer extends TabSheet {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private CustomersGirdView customersGirdView;
	
	public TabCustomer(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}
	
	private void init() {
		VerticalLayout tab1 = new VerticalLayout();
		customersGirdView = new CustomersGirdView(applicationContext, this);
		tab1.addComponent(customersGirdView);
		//tab1.setMargin(false);
		tab1.setCaption("Customers");
		tab1.setIcon(VaadinIcons.AIRPLANE);
		
		addTab(tab1, 0);
		addTab(new CustomerFormView(applicationContext), "Customer", null, 1);
		
		setSizeFull();
		
		this.addSelectedTabChangeListener(selectedTab -> {
			TabSheet tabsheet = selectedTab.getTabSheet();
			Tab tab = tabsheet.getTab(0);
			if (tab.getCaption().equals("Customers")) {
				replaceComponent(customersGirdView, new CustomersGirdView(applicationContext, this));
			}
		});
	}
}
