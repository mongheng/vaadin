package com.emh.uiprovider;

import com.emh.MainUI;
import com.emh.mobile.MobileUI;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class DefineUIProvider extends UIProvider {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
	
		if (event.getRequest().getHeader("user-agent").contains("Mobile")) {
			return MobileUI.class;
		}
		else {
			return MainUI.class;
		}
	}

}
