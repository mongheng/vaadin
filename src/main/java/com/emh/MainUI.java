package com.emh;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emh.view.LoginView;
import com.emh.view.MainPageView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MainUI extends UI {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		getPage().setTitle("Vaadin Project");
		
		Navigator navigator = new Navigator(this, this);
		
        navigator.addView("loginview", new LoginView(applicationContext));
        navigator.addView("mainpageview", new MainPageView(applicationContext));
        
        navigator.navigateTo("loginview");
    }

/*    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 1L;
    }*/
}
