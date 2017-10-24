package com.emh.view.tab;

import org.springframework.context.ApplicationContext;

import com.emh.view.report.ResponsiveReceiverView;
import com.emh.view.report.ResponsiveReportView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

public class TabReport extends TabSheet {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	
	public TabReport(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		initTab();
	}
	
	private void initTab() {
		addTab(new ResponsiveReceiverView(applicationContext), 0).setId("0");
		addTab(new ResponsiveReportView(applicationContext),1).setId("1");
		setSizeFull();
		addStyleName(ValoTheme.TABSHEET_FRAMED);
	}
}
