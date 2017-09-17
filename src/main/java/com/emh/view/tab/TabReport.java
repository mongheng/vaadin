package com.emh.view.tab;

import com.emh.view.report.ResponsiveReceiverView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

public class TabReport extends TabSheet {

	private static final long serialVersionUID = 1L;
	
	public TabReport() {
		initTab();
	}
	
	private void initTab() {
		addTab(new ResponsiveReceiverView(), 0).setId("0");
		setSizeFull();
		addStyleName(ValoTheme.TABSHEET_FRAMED);
	}
}
