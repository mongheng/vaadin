package com.emh.view;

import org.springframework.context.ApplicationContext;

import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LoginView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private HorizontalLayout hLayout;
	private Panel loginPanel;
	private FormLayout formlogin;
	private TextField name;
	private PasswordField password;
	private Button btnLogin;
	private Button btnSignup;

	private boolean isStatu;
	
	public LoginView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;

		loginPanel = new Panel("Login Form");
		formlogin = new FormLayout();
		hLayout = new HorizontalLayout();

		classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());

		name = new TextField();
		name.setCaption("Username :");
		name.setIcon(VaadinIcons.USER);
		name.setRequiredIndicatorVisible(true);
		name.focus();
		name.addBlurListener(new BlurListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void blur(BlurEvent event) {
				User user = new User();
				user.setUsername(name.getValue());

				user = classBusiness.selectUserByUsernameAndPassword(user);

				isStatu = user != null ? true : false;
			}
		});

		password = new PasswordField();
		password.setCaption("Password :");
		password.setIcon(VaadinIcons.PASSWORD);
		password.setRequiredIndicatorVisible(true);
		password.addShortcutListener(new ShortcutListener("KeyEnter", ShortcutAction.KeyCode.ENTER, null) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				login();
			}
		});

		btnLogin = new Button("Login");
		btnLogin.setIcon(VaadinIcons.SIGN_IN);
		btnLogin.setClickShortcut(KeyCode.ENTER);
		btnLogin.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnLogin.addClickListener(e -> {
			login();
		});

		btnSignup = new Button("Signup");
		btnSignup.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSignup.addClickListener(e -> {
			// getUI().getNavigator().navigateTo("signupview/params=10");
			//UI.getCurrent().addWindow(new SignupView(applicationContext));
			UI.getCurrent().addWindow(new FloorView(applicationContext));
		});

		hLayout.addComponents(btnLogin, btnSignup);
		hLayout.setSpacing(true);

		formlogin.addComponents(name, password, hLayout);
		formlogin.setMargin(true);

		loginPanel.setWidth("400px");
		loginPanel.addStyleName("v-loginPanel");
		loginPanel.setContent(formlogin);

		addComponents(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		setHeight("95%");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	private void login() {
		User user = new User();
		user.setUsername(name.getValue());
		user.setPassword(password.getValue());

		User nUser = classBusiness.selectUserByUsernameAndPassword(user);

		if (isStatu) {
			if (nUser != null) {
				UI.getCurrent().getSession().setAttribute(User.class, nUser);
				getUI().getNavigator().navigateTo(MainPageView.class.getSimpleName());
				name.clear();
				password.clear();
			} else {
				Notification.show("Incorrect password. Please type password again.", Type.HUMANIZED_MESSAGE);
				password.clear();
				password.focus();
			}
		} else {
			Notification.show("You have not account yet. Please ask your admin or manager", Type.HUMANIZED_MESSAGE);
			name.clear();
			password.clear();
			name.focus();
		}
	}

}
