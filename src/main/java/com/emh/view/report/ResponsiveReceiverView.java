package com.emh.view.report;

import com.emh.model.User;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class ResponsiveReceiverView extends CssLayout {

	private static final long serialVersionUID = 1L;

	private VerticalLayout mainLayout;
	private VerticalLayout topLayout;
	private VerticalLayout bottomLayout;
	private HorizontalLayout topFormHLayout;
	private HorizontalLayout topButtonHLayout;
	private FormLayout formLayout;
	
	private DateField startDate;
	private DateField endDate;
	private ComboBox<User> cboEmployee;
	private Button btnSearch;
	
	public ResponsiveReceiverView() {
		init();
	}
	
	private void init() {
		mainLayout = new VerticalLayout();
		topLayout = new VerticalLayout();
		bottomLayout = new VerticalLayout();
		topFormHLayout = new HorizontalLayout();
		topButtonHLayout = new HorizontalLayout();
		formLayout = new FormLayout();
		
		startDate = new DateField("Start Date");
		startDate.setTextFieldEnabled(true);
		startDate.setDateFormat("dd/MM/yyyy");
		
		endDate = new DateField("End Date");
		endDate.setTextFieldEnabled(true);
		endDate.setDateFormat("dd/MM/yyyy");
		
		cboEmployee = new ComboBox<>("Empolyee");
		
		btnSearch = new Button("Search");
		
		topFormHLayout.addComponents(startDate, endDate, cboEmployee);
		topButtonHLayout.addComponent(btnSearch);
		formLayout.addComponents(topFormHLayout, topButtonHLayout);
		formLayout.addStyleName("caption");
		
		topLayout.addComponent(formLayout);
		topLayout.setSizeFull();
		topLayout.setSpacing(false);
		topLayout.setMargin(false);
		
		mainLayout.addComponents(topLayout, bottomLayout);
		mainLayout.setExpandRatio(topLayout, 1);
		mainLayout.setExpandRatio(bottomLayout, 2);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);
		
		addComponent(mainLayout);
		setSizeFull();
		setCaption("Receive Payment");
	}
}
