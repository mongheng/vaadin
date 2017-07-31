package com.emh.view.tab;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabCustomer extends TabSheet {

	private static final long serialVersionUID = 1L;

	public TabCustomer() {
		super();
		init();
	}
	
	private void init() {
		VerticalLayout tab1 = new VerticalLayout();
		tab1.addComponent(new Label("Test1"));
		tab1.setMargin(false);
		tab1.setCaption("Tab1");
		
		VerticalLayout tab2 = new VerticalLayout();
		tab2.addComponent(new Label("Test2"));
		tab2.setCaption("Tab2");
		
		addTab(tab1, 0);
		addTab(tab2, 1);
		
	}
}
