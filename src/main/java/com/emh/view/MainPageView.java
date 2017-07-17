package com.emh.view;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainPageView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private User user;

	private HorizontalLayout headerLayout;
	private HorizontalLayout innerHeaderLayout;
	private VerticalLayout menuLayout;
	private HorizontalSplitPanel bodyLayout;
	private VerticalLayout contentLayout;

	public MainPageView(ApplicationContext applicationContext) {

		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
	}

	@Override
	public void enter(ViewChangeEvent event) {

		init();
	}

	private void init() {
		headerLayout = new HorizontalLayout();
		innerHeaderLayout = new HorizontalLayout();
		menuLayout = new VerticalLayout();
		bodyLayout = new HorizontalSplitPanel();
		contentLayout = new VerticalLayout();

		//user = (User) VaadinSession.getCurrent().getAttribute(User.class);
		user = (User) UI.getCurrent().getSession().getAttribute(User.class);
		addHeaderComponent();

		bodyLayout.setSplitPosition(15);
		addComponent(headerLayout);
		addComponent(bodyLayout);
		setComponentAlignment(headerLayout, Alignment.MIDDLE_RIGHT);

	}

	private void addHeaderComponent() {
		Label lblUserName = new Label();
		lblUserName.setIcon(VaadinIcons.USER);
		lblUserName.addStyleName(ValoTheme.LABEL_BOLD);
		lblUserName.addStyleName(ValoTheme.LABEL_H4);

		if (user != null) {
			lblUserName.setCaption(user.getUsername());
		}

		Button btnSignout = new Button();
		btnSignout.setCaption("Sign out");
		btnSignout.addStyleName(ValoTheme.BUTTON_SMALL);
		btnSignout.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnSignout.addClickListener(event->{
			ConfirmDialog.show(UI.getCurrent(), "Confirmation","Are you sure you want to Sign out?","Yes","No", dialog -> {
				if (dialog.isConfirmed()) {
					getUI().getNavigator().navigateTo("loginview");
				}
			});
		});

		innerHeaderLayout.addComponents(lblUserName, btnSignout);
		innerHeaderLayout.setSpacing(true);
		innerHeaderLayout.setSizeUndefined();
		headerLayout.addComponent(innerHeaderLayout);
		headerLayout.setComponentAlignment(innerHeaderLayout, Alignment.MIDDLE_RIGHT);
	}
}
