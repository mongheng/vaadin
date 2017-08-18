package com.emh.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Custom extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	CustomLayout content = new CustomLayout("mylayout");
	Panel p = new Panel();
	
	public Custom() {

		init();
	}
	
	private void init() {
		content.setSizeUndefined();
		p.setCaption("Form Login");
		p.setContent(content);
		p.setSizeUndefined();
		
		addComponent(p);
		setSizeFull();
		setComponentAlignment(p, Alignment.MIDDLE_CENTER);

		// No captions for fields is they are provided in the template
		content.addComponent(new TextField(), "username");
		content.addComponent(new TextField(), "password");
		content.addComponent(new Button("Login"), "okbutton");
	}
}
