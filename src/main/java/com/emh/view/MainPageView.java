package com.emh.view;

import org.springframework.context.ApplicationContext;

import com.emh.repository.business.ClassBusiness;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class MainPageView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	
	private HorizontalLayout headerLayout;
	private HorizontalLayout innerHeaderLayout;
	private VerticalLayout menuLayout;
	
	public MainPageView(ApplicationContext applicationContext) {
		
		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
