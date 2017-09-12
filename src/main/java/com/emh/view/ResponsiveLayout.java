package com.emh.view;

import org.springframework.context.ApplicationContext;

import com.emh.model.User;
import com.emh.view.tab.TabContract;
import com.emh.view.tab.TabCustomer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ResponsiveLayout extends CssLayout implements View {

	private static final long serialVersionUID = 1L;
	
	private ApplicationContext applicationContext;
	private User user;
	private HorizontalSplitPanel hsLayout;
	private VerticalLayout menuLayout;
	private VerticalLayout contentLayout;
	private VerticalLayout menuTitleLayout;
	private VerticalLayout menuProfileLayout;
	private VerticalLayout menuContentLayout;
	private FormLayout formLayout;
	
	private Label titleContent;
	private Image image;
	private MenuBar menuBar;
	private Button btnDashboard;
	private Button btnCustomer;
	private Button btnContract;
	private Button btnExtendContract;
	private Button btnCarParking;
	private Button btnUserView;
	
	@Override
	public void enter(ViewChangeEvent event) {	
		init();
	}
	
	public ResponsiveLayout(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	private void init() {
		user = (User) UI.getCurrent().getSession().getAttribute(User.class);
		hsLayout = new HorizontalSplitPanel();
		menuLayout = new VerticalLayout();
		contentLayout = new VerticalLayout();
		menuTitleLayout = new VerticalLayout();
		menuProfileLayout = new VerticalLayout();
		menuContentLayout = new VerticalLayout();
		formLayout = new FormLayout();
		
		titleContent = new Label("Vaadin Application");
		titleContent.addStyleName("menutitle");
		titleContent.setDescription("This is Title Content in Menu Layout.");
		
		ThemeResource resource = new ThemeResource("images/android.png");
		image = new Image("profile pic", resource);
		menuBar = new MenuBar();
		
		menuTitleLayout.addComponent(titleContent);
		menuTitleLayout.setComponentAlignment(titleContent, Alignment.TOP_CENTER);
		menuTitleLayout.setSpacing(false);
		menuTitleLayout.setMargin(false);
		menuTitleLayout.setSizeFull();
		menuTitleLayout.addStyleName("menulayout");
		
		menuProfileLayout.addComponent(image);
		addMenuBarToHeader("sok", "1", new String[] {"Edit Profile", "Preferences", "Sign Out"});
		menuProfileLayout.setComponentAlignment(image, Alignment.TOP_CENTER);
		menuProfileLayout.setSpacing(false);
		menuProfileLayout.setMargin(false);
		menuProfileLayout.setSizeFull();
		menuProfileLayout.addStyleName("menulayout");
		menuProfileLayout.setResponsive(true);
		
		//processButton();
		addMenuAndContentComponent("Dashboard", DashboardView.class.getSimpleName(), VaadinIcons.DASHBOARD);
		addMenuAndContentComponent("Customer", TabCustomer.class.getSimpleName(), VaadinIcons.MALE);
		addMenuAndContentComponent("Contract", TabContract.class.getSimpleName(), VaadinIcons.ADOBE_FLASH);
		addMenuAndContentComponent("Extend Contract", ExtendContractView.class.getSimpleName(), VaadinIcons.ALARM);
		addMenuAndContentComponent("Users", UserListView.class.getSimpleName(), VaadinIcons.GROUP);
		menuContentLayout.setSpacing(false);
		menuContentLayout.setMargin(false);
		menuContentLayout.setSizeFull();
		menuContentLayout.addStyleName("menulayout");
		menuContentLayout.setResponsive(true);
		
		menuLayout.addComponents(menuTitleLayout, menuProfileLayout, menuContentLayout);
		menuLayout.setSizeFull();
		menuLayout.setSpacing(false);
		menuLayout.setMargin(false);
		menuLayout.setExpandRatio(menuTitleLayout, 0.3f);
		menuLayout.setExpandRatio(menuProfileLayout, 1.3f);
		menuLayout.setExpandRatio(menuContentLayout, 4.4f);
		menuLayout.setComponentAlignment(menuContentLayout, Alignment.TOP_CENTER);
		menuLayout.setComponentAlignment(menuProfileLayout, Alignment.TOP_CENTER);
		menuLayout.setResponsive(true);
		
		hsLayout.setFirstComponent(menuLayout);
		hsLayout.setSecondComponent(contentLayout);
		hsLayout.setSplitPosition(18);
		hsLayout.setSizeFull();
		hsLayout.setResponsive(true);
		
		addComponent(hsLayout);
		setSizeFull();
		setResponsive(true);
	}
	
	private void addMenuBarToHeader(String topLevelMenuCatpion, String indexSparator, String... subMenuCaptions) {
		MenuItem menuItem;
		Integer i = 0;
		if (subMenuCaptions.length == 0) {
			menuItem = menuBar.addItem(topLevelMenuCatpion, new MenuCommand());
			menuItem.setStyleName(ValoTheme.MENU_ROOT);
		} else {
			menuItem = menuBar.addItem(topLevelMenuCatpion, null);
			menuItem.setStyleName(ValoTheme.MENU_ROOT);
			for (String menuItemCaption : subMenuCaptions) {
				MenuItem subMenuItem = menuItem.addItem(menuItemCaption, new MenuCommand());
				subMenuItem.setIcon(VaadinIcons.GROUP);
				if (indexSparator != null) {
					if (i.toString().equals(indexSparator)) {
						menuItem.addSeparator();
					}
				}
				i++;
			}
		}
		menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		menuBar.addStyleName("menucaptioncolor");
		menuProfileLayout.addComponent(menuBar);
		menuProfileLayout.setSizeUndefined();
		menuProfileLayout.setComponentAlignment(menuBar, Alignment.BOTTOM_CENTER);
	}
	
	private class MenuCommand implements MenuBar.Command {

		private static final long serialVersionUID = 1L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
			
			//String command = selectedItem.getText();		
		}
	}
	
	private void processButton() {
		btnDashboard = new Button("Dashboard");
		btnDashboard.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnDashboard.addStyleName("textstyle");
		btnDashboard.addStyleName("textstylehover");
		btnDashboard.addStyleName("icon");
		btnDashboard.addStyleName("iconhover");
		btnDashboard.addStyleName("focus");
		btnDashboard.addStyleName("iconfocus");
		btnDashboard.addStyleName("hideborder");
		btnDashboard.setIcon(VaadinIcons.DASHBOARD);
		
		btnCustomer = new Button("Customer");
		btnCustomer.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnCustomer.addStyleName("textstyle");
		btnCustomer.addStyleName("textstylehover");
		btnCustomer.addStyleName("icon");
		btnCustomer.addStyleName("iconhover");
		btnCustomer.addStyleName("focus");
		btnCustomer.addStyleName("iconfocus");
		btnCustomer.setIcon(VaadinIcons.GROUP);
		
		btnContract = new Button("Contract");
		btnContract.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnContract.addStyleName("textstyle");
		btnContract.addStyleName("textstylehover");
		btnContract.addStyleName("icon");
		btnContract.addStyleName("iconhover");
		btnContract.addStyleName("focus");
		btnContract.addStyleName("iconfocus");
		btnContract.setIcon(VaadinIcons.ADOBE_FLASH);
		
		btnExtendContract = new Button("Extend Contract");
		btnExtendContract.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnExtendContract.addStyleName("textstyle");
		btnExtendContract.addStyleName("textstylehover");
		btnExtendContract.addStyleName("icon");
		btnExtendContract.addStyleName("iconhover");
		btnExtendContract.addStyleName("focus");
		btnExtendContract.addStyleName("iconfocus");
		btnExtendContract.setIcon(VaadinIcons.ALARM);
		
		btnCarParking = new Button("Car Parking");
		btnCarParking.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnCarParking.addStyleName("textstyle");
		btnCarParking.addStyleName("textstylehover");
		btnCarParking.addStyleName("icon");
		btnCarParking.addStyleName("iconhover");
		btnCarParking.addStyleName("focus");
		btnCarParking.addStyleName("iconfocus");
		btnCarParking.setIcon(VaadinIcons.CAR);
		
		btnUserView = new Button("Users");
		btnUserView.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnUserView.addStyleName("textstyle");
		btnUserView.addStyleName("textstylehover");
		btnUserView.addStyleName("icon");
		btnUserView.addStyleName("iconhover");
		btnUserView.addStyleName("focus");
		btnUserView.addStyleName("iconfocus");
		btnUserView.setIcon(VaadinIcons.GROUP);
		
		formLayout.addComponents(btnDashboard, btnCustomer, btnContract, btnExtendContract, btnCarParking, btnUserView);
		formLayout.setSizeFull();
		menuContentLayout.addComponent(formLayout);
		menuContentLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
	}
	
	private void addMenuAndContentComponent(String buttonCaption, String viewComponent, VaadinIcons vaadinIcons) {
		Button menuTitle = new Button();
		menuTitle.setCaption(buttonCaption);
		menuTitle.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		menuTitle.addStyleName("textstyle");
		menuTitle.addStyleName("textstylehover");
		menuTitle.addStyleName("icon");
		menuTitle.addStyleName("iconhover");
		menuTitle.addStyleName("focus");
		menuTitle.addStyleName("iconfocus");
		menuTitle.setIcon(vaadinIcons);
		formLayout.addComponents(menuTitle);
		formLayout.setSizeFull();
		menuContentLayout.addComponent(formLayout);
		menuContentLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		
		menuTitle.addClickListener(event -> {
			contentLayout.removeAllComponents();
			contentLayout.addComponent(getViewComponent(viewComponent));
			contentLayout.setSizeFull();
			contentLayout.setMargin(false);
			contentLayout.setSpacing(false);
		});
	}
	
	private Component getViewComponent(String viewCaption) {
		if (viewCaption.equals(UserListView.class.getSimpleName())) {
			return new UserListView(applicationContext);
		} else if (viewCaption.equals(TabCustomer.class.getSimpleName())) {
			TabCustomer tabCustomer = new TabCustomer(applicationContext);
			return tabCustomer;
		} else if (viewCaption.equalsIgnoreCase(DashboardView.class.getSimpleName())) {
			return new DashboardView(applicationContext);
		} else if (viewCaption.equalsIgnoreCase(TabContract.class.getSimpleName())) {
			return new TabContract(applicationContext);
		} else if (viewCaption.equalsIgnoreCase(ExtendContractView.class.getSimpleName())) {
			return new ExtendContractView(applicationContext);
		} else if (viewCaption.equalsIgnoreCase(TabCustomer.class.getSimpleName())) {
			return new TabCustomer(applicationContext);
		} else if (viewCaption.equalsIgnoreCase("Car Parking")) {
			return new TabCustomer(applicationContext);
		} else {
			return null;
		}
	}
}
