package com.emh.view;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
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

		user = (User) UI.getCurrent().getSession().getAttribute(User.class);
		addHeaderComponent();
		addMenuAndContentComponent("User List View", UserListView.class.getSimpleName());
		
		bodyLayout.setFirstComponent(menuLayout);
		bodyLayout.setSecondComponent(contentLayout);
		bodyLayout.setSizeFull();
		bodyLayout.setSplitPosition(15);
		
		addComponent(headerLayout);
		addComponent(bodyLayout);
		setComponentAlignment(headerLayout, Alignment.TOP_RIGHT);
		addStyleName("v-verticallayout-borderBottom");
		setSizeFull();
		setExpandRatio(bodyLayout, 1);

	}

	private void addHeaderComponent() {
		Label lblUserName = new Label();
		lblUserName.setIcon(VaadinIcons.USER);
		lblUserName.addStyleName(ValoTheme.LABEL_COLORED);
		lblUserName.addStyleName(ValoTheme.LABEL_BOLD);
		lblUserName.setSizeUndefined();

		if (user != null) {
			lblUserName.setCaption(user.getUsername());
		}

		Button btnSignout = new Button();
		btnSignout.setCaption("Sign out");
		btnSignout.setIcon(VaadinIcons.SIGN_OUT);
		btnSignout.addStyleName(ValoTheme.BUTTON_SMALL);
		btnSignout.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		btnSignout.setSizeUndefined();
		btnSignout.setHeight("85%");
		btnSignout.addClickListener(event -> {
			ConfirmDialog.show(UI.getCurrent(), "Confirmation", "Are you sure you want to Sign out?", "Yes", "No",
					dialog -> {
						if (dialog.isConfirmed()) {
							contentLayout.removeAllComponents();
							menuLayout.removeAllComponents();
							this.removeAllComponents();
							getUI().getNavigator().navigateTo(LoginView.class.getSimpleName());
						}
					});
		});

		innerHeaderLayout.addComponents(lblUserName, btnSignout);
		// innerHeaderLayout.setExpandRatio(btnSignout, 1);
		innerHeaderLayout.setSpacing(true);
		innerHeaderLayout.setSizeUndefined();
		innerHeaderLayout.setComponentAlignment(btnSignout, Alignment.TOP_RIGHT);
		headerLayout.setSizeFull();
		headerLayout.addComponent(innerHeaderLayout);
		headerLayout.setMargin(new MarginInfo(false, true, false, false));
		headerLayout.setComponentAlignment(innerHeaderLayout, Alignment.TOP_RIGHT);

		//headerLayout.addStyleName("v-verticallayout-borderBottom");
		headerLayout.setHeight(2, Unit.CM);
	}

	private Component getViewComponent(String viewCaption) {
		if (viewCaption.equals(UserListView.class.getSimpleName())) {
			return new UserListView(applicationContext);
		}else {
			return null;
		}
	}

	private void addMenuAndContentComponent(String buttonCaption, String viewComponent) {
		Button menuTitle = new Button();
		menuTitle.setCaption(buttonCaption);
		menuTitle.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		menuTitle.setWidth("100%");
		menuLayout.addComponent(menuTitle);
		
		menuTitle.addClickListener(event -> {
			contentLayout.removeAllComponents();
			contentLayout.addComponent(getViewComponent(viewComponent));
			contentLayout.setSizeFull();
			contentLayout.setStyleName("padding-bottom: 80px;");
		});
	}
}
