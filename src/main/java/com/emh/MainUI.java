package com.emh;

import org.apache.log4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.emh.repository.business.ClassBusiness;
import com.emh.view.LoginView;
import com.emh.view.MainPageView;
import com.emh.view.ResponsiveLayout;
import com.emh.view.UserListView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Scope("prototype")
@SpringUI
@SpringViewDisplay
@PreserveOnRefresh
@Push
public class MainUI extends UI {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(MainUI.class.getName());
	
	@Autowired
	private ClassBusiness classBusiness;
	
	static {
		SLF4JBridgeHandler.install();
	}
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		logger.info("Loading Main Form");
		
		getPage().setTitle("Vaadin Project");
	
		Navigator navigator = new Navigator(this, this);

        navigator.addView(LoginView.class.getSimpleName(), new LoginView(classBusiness));
        navigator.addView(MainPageView.class.getSimpleName(), new MainPageView(classBusiness));
        navigator.addView(UserListView.class.getSimpleName(), new UserListView(classBusiness));
        navigator.addView(ResponsiveLayout.class.getSimpleName(), new ResponsiveLayout(classBusiness));
        
        navigator.navigateTo(LoginView.class.getSimpleName());
    }

/*    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 1L;
    }*/
}
