package com.emh.view.tab;

import org.springframework.context.ApplicationContext;

import com.emh.view.CustomerFormView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabCustomer extends TabSheet {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	
	public TabCustomer(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}
	
	private void init() {
		VerticalLayout tab1 = new VerticalLayout();
		tab1.addComponent(new Label("Test1"));
		tab1.setMargin(false);
		tab1.setCaption("Customers");
		tab1.setIcon(VaadinIcons.AIRPLANE);
		
		addTab(tab1, 0);
		addTab(new CustomerFormView(applicationContext), "Customer", null, 1);
		
		
		setSizeFull();
		
	}
}
