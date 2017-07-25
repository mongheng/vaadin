package com.emh.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emh.repository.business.ClassBusiness;

public class ConfigApplicationContext {

	private static ApplicationContext applicationContext =  new ClassPathXmlApplicationContext("applicationContext.xml");
	public static ClassBusiness classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
}
