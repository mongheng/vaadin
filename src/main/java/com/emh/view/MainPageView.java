package com.emh.view;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.User;
import com.emh.view.tab.TabContract;
import com.emh.view.tab.TabCustomer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainPageView extends AbsoluteLayout implements View {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private User user;

	private VerticalLayout headerLayout;
	private HorizontalLayout innerHeaderLayout;
	private HorizontalLayout menuHeaderLayout;
	private VerticalLayout menuLayout;
	private HorizontalSplitPanel bodyLayout;
	private VerticalLayout contentLayout;
	private MenuBar menuBar;

	public MainPageView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void enter(ViewChangeEvent event) {

		init();
	}

	private void init() {
		headerLayout = new VerticalLayout();
		innerHeaderLayout = new HorizontalLayout();
		menuHeaderLayout = new HorizontalLayout();
		menuLayout = new VerticalLayout();
		bodyLayout = new HorizontalSplitPanel();
		contentLayout = new VerticalLayout();
		
		menuBar = new MenuBar();

		user = (User) UI.getCurrent().getSession().getAttribute(User.class);
		
		addMenuBarToHeader("Dashboard",new String[]{});
		addMenuBarToHeader("Contract",new String[]{});
		addMenuBarToHeader("Extends Contract",new String[]{});
		addMenuBarToHeader("Customer", new String[]{"Customer","Car Parking"});
		addMenuBarToHeader("User", "UserListView");
		addHeaderComponent();
		addMenuAndContentComponent("UserListView", UserListView.class.getSimpleName());
		addMenuAndContentComponent("Customer", TabCustomer.class.getSimpleName());
		
		contentLayout.setSizeFull();
		contentLayout.addComponent(new DashboardView(applicationContext));
		
		bodyLayout.setFirstComponent(menuLayout);
		bodyLayout.setSecondComponent(contentLayout);
		bodyLayout.setSizeFull();
		bodyLayout.setLocked(true);
		bodyLayout.setSplitPosition(13);
		
		addComponent(headerLayout);
		addComponent(bodyLayout,"top:70px;bottom:10px");
		setSizeFull();

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

		headerLayout.setHeight(2, Unit.CM);
	}

	private Component getViewComponent(String viewCaption) {
		if (viewCaption.equals(UserListView.class.getSimpleName())) {
			return new UserListView(applicationContext);
		} else if (viewCaption.equals(TabCustomer.class.getSimpleName())) {
			TabCustomer tabCustomer = new TabCustomer(applicationContext);
			return tabCustomer;
		} else if (viewCaption.equalsIgnoreCase("Dashboard")) {
			return new DashboardView(applicationContext);
		} else if (viewCaption.equalsIgnoreCase("Contract")) {
			return new TabContract(applicationContext);
		} else if (viewCaption.equalsIgnoreCase("Extends Contract")) {
			return new ExtendContractView(applicationContext);
		} else if (viewCaption.equalsIgnoreCase("Customer")) {
			return new TabCustomer(applicationContext);
		} else if (viewCaption.equalsIgnoreCase("Car Parking")) {
			return new TabCustomer(applicationContext);
		} else {
			return null;
		}
	}

	private void addMenuAndContentComponent(String buttonCaption, String viewComponent) {
		Button menuTitle = new Button();
		menuTitle.setCaption(buttonCaption);
		menuTitle.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		menuTitle.setWidth("100%");
		menuLayout.addComponent(menuTitle);
		menuLayout.setComponentAlignment(menuTitle, Alignment.TOP_CENTER);
		
		menuTitle.addClickListener(event -> {
			contentLayout.removeAllComponents();
			contentLayout.addComponent(getViewComponent(viewComponent));
			contentLayout.setSizeFull();
			contentLayout.setMargin(false);
		});
	}
	
	private void addMenuBarToHeader(String topLevelMenuCatpion, String... subMenuCaptions) {
		MenuItem menuItem;
		if (subMenuCaptions.length == 0) {
			menuItem = menuBar.addItem(topLevelMenuCatpion, new MenuCommand());
			menuItem.setStyleName(ValoTheme.MENU_ROOT);
		} else {
			menuItem = menuBar.addItem(topLevelMenuCatpion, null);
			menuItem.setStyleName(ValoTheme.MENU_ROOT);
			for (String menuItemCaption : subMenuCaptions) {
				MenuItem subMenuItem = menuItem.addItem(menuItemCaption, new MenuCommand());
				subMenuItem.setIcon(VaadinIcons.GROUP);;
			}
		}
		menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		menuHeaderLayout.addComponent(menuBar);
		menuHeaderLayout.setSizeUndefined();
		menuHeaderLayout.setComponentAlignment(menuBar, Alignment.TOP_CENTER);
		
		headerLayout.addComponent(menuBar);
		headerLayout.setComponentAlignment(menuBar, Alignment.TOP_CENTER);
	}
	
	private class MenuCommand implements MenuBar.Command {

		private static final long serialVersionUID = 1L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
			
			String command = selectedItem.getText();
			contentLayout.removeAllComponents();
			contentLayout.addComponent(getViewComponent(command));
			contentLayout.setMargin(false);
			contentLayout.setSizeFull();
		}
	}
}
