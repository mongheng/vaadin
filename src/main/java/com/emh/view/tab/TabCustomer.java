package com.emh.view.tab;

import com.emh.repository.business.ClassBusiness;
import com.emh.view.CarParkingView;
import com.emh.view.CustomerFormView;
import com.emh.view.CustomersGirdView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TabCustomer extends TabSheet {

	private static final long serialVersionUID = 1L;

	private ClassBusiness classBusiness;
	private CustomersGirdView customersGirdView;
	
	public TabCustomer(ClassBusiness classBusiness) {
		this.classBusiness = classBusiness;
		init();
	}
	
	private void init() {
		VerticalLayout tab1 = new VerticalLayout();
		customersGirdView = new CustomersGirdView(classBusiness, this);
		tab1.addComponent(customersGirdView);
		//tab1.setMargin(false);
		tab1.setSizeFull();
		tab1.setCaption("Customers");
		tab1.setIcon(VaadinIcons.AIRPLANE);
		
		addTab(tab1, 0);
		addTab(new CustomerFormView(classBusiness), "Customer", null, 1);
		//addTab(new ResponsiveCustomerFormView(), "Customer", null, 1);
		
		addTab(new CarParkingView(classBusiness), 2);
		
		setSizeFull();
		addStyleName(ValoTheme.TABSHEET_FRAMED);
		
		this.addSelectedTabChangeListener(selectedTab -> {
			TabSheet tabsheet = selectedTab.getTabSheet();
			Layout layout = (Layout) tabsheet.getSelectedTab();
			
			if (layout instanceof CustomerFormView) {
				System.out.println("CustomerFormView caption is : " + tabsheet.getTab((CustomerFormView) layout).getCaption());
			} else if (layout instanceof CustomersGirdView) {
				layout.removeAllComponents();
				layout.addComponent(new CustomersGirdView(classBusiness, this));
				System.out.println("CustomerGridView caption is : " + tabsheet.getTab((VerticalLayout) layout).getCaption());
			}
		});
	}
}
