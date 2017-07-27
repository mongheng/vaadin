package com.emh.UIProvider;

import com.emh.MainUI;
import com.emh.mobile.MobileUI;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.server.SpringUIProvider;
import com.vaadin.ui.UI;

public class DefineSpringUIProvider extends SpringUIProvider {

	private static final long serialVersionUID = 1L;
	
	public DefineSpringUIProvider(VaadinSession vaadinSession) {
		super(vaadinSession);
		
	}

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent uiClassSelectionEvent) {
		if (uiClassSelectionEvent.getRequest().getHeader("user-agent").contains("Mobile")) {
			return MobileUI.class;
		}
		else {
			return MainUI.class;
		}
	}

/*	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		if (event.getRequest().getHeader("user-agent").contains("Mobile")) {
			return MobileUI.class;
		}
		else {
			return MainUI.class;
		}
	}*/
	
	
	
}
