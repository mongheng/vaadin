package com.emh.mobile;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class MobileUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest request) {
		WebBrowser webBrowser = getPage().getWebBrowser();
		String screenSize = "" + webBrowser.getScreenWidth() + "X" + webBrowser.getScreenHeight();
		
		setContent(new Label("Using a touch enabled device with screen size"+ screenSize));
		
		Notification.show("Mobile WebBrower.", Type.HUMANIZED_MESSAGE);
	}

}
