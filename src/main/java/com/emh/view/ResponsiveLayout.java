package com.emh.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.User;
import com.emh.view.tab.TabContract;
import com.emh.view.tab.TabCustomer;
import com.emh.view.tab.TabReport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
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
		
		if (user.getImage() == null) {
			ThemeResource resource = new ThemeResource("images/person.png");
			image = new Image("", resource);
		} else {
			StreamResource resource = new StreamResource(new ImageResource(), user.getUsername());
			image = new Image("", resource);
		}
		menuBar = new MenuBar();
		
		menuTitleLayout.addComponent(titleContent);
		menuTitleLayout.setComponentAlignment(titleContent, Alignment.TOP_CENTER);
		menuTitleLayout.setSpacing(false);
		menuTitleLayout.setMargin(false);
		menuTitleLayout.setSizeFull();
		menuTitleLayout.addStyleName("menulayout");
		
		menuProfileLayout.addComponent(image);
		addMenuBarToHeader(user.getUsername(), "1", new String[] {"Edit Profile", "Preferences", "Sign Out"});
		menuProfileLayout.setComponentAlignment(image, Alignment.TOP_CENTER);
		menuProfileLayout.setExpandRatio(image, 0.7f);
		menuProfileLayout.setExpandRatio(menuBar, 1.3f);
		menuProfileLayout.setSpacing(false);
		menuProfileLayout.setMargin(false);
		menuProfileLayout.setSizeFull();
		menuProfileLayout.addStyleName("menulayout");
		menuProfileLayout.setResponsive(true);
		
		addMenuAndContentComponent("Dashboard", DashboardView.class.getSimpleName(), VaadinIcons.DASHBOARD);
		addMenuAndContentComponent("Customer", TabCustomer.class.getSimpleName(), VaadinIcons.MALE);
		addMenuAndContentComponent("Contract", TabContract.class.getSimpleName(), VaadinIcons.ADOBE_FLASH);
		addMenuAndContentComponent("Extend Contract", ExtendContractView.class.getSimpleName(), VaadinIcons.ALARM);
		addMenuAndContentComponent("Users", UserListView.class.getSimpleName(), VaadinIcons.GROUP);
		addMenuAndContentComponent("Report", TabReport.class.getSimpleName(), VaadinIcons.BOOK);
		formLayout.setSpacing(false);
		formLayout.setMargin(false);
		formLayout.setSizeFull();
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
		menuLayout.setExpandRatio(menuProfileLayout, 1.1f);
		menuLayout.setExpandRatio(menuContentLayout, 4.6f);
		menuLayout.setComponentAlignment(menuContentLayout, Alignment.TOP_CENTER);
		menuLayout.setComponentAlignment(menuProfileLayout, Alignment.TOP_CENTER);
		menuLayout.setResponsive(true);
		
		contentLayout.addComponent(new DashboardView(applicationContext));
		contentLayout.setSizeFull();
		contentLayout.setSpacing(false);
		contentLayout.setMargin(false);
		
		hsLayout.setFirstComponent(menuLayout);
		hsLayout.setSecondComponent(contentLayout);
		hsLayout.setSplitPosition(16);
		hsLayout.setLocked(true);
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
			menuItem = menuBar.addItem(topLevelMenuCatpion, new MenuCommand(this));
			menuItem.setStyleName(ValoTheme.MENU_ROOT);
		} else {
			menuItem = menuBar.addItem(topLevelMenuCatpion, null);
			menuItem.setStyleName(ValoTheme.MENU_ROOT);
			for (String menuItemCaption : subMenuCaptions) {
				MenuItem subMenuItem = menuItem.addItem(menuItemCaption, new MenuCommand(this));
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
		menuBar.addStyleName("menufoucs");
		menuProfileLayout.addComponent(menuBar);
		menuProfileLayout.setSizeUndefined();
		menuProfileLayout.setComponentAlignment(menuBar, Alignment.BOTTOM_CENTER);
	}
	
	private class MenuCommand implements MenuBar.Command {

		private static final long serialVersionUID = 1L;
		private ResponsiveLayout responsiveLayout;
		
		public MenuCommand(ResponsiveLayout responsiveLayout) {
			this.responsiveLayout = responsiveLayout;
		}
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
			
			String command = selectedItem.getText();
			System.out.println(command);
			if (command.equalsIgnoreCase("Sign Out")) {
				ConfirmDialog.show(UI.getCurrent(), "Confirmation", "Are you sure you want to Sign out?", "Yes", "No",
						dialog -> {
							if (dialog.isConfirmed()) {
								contentLayout.removeAllComponents();
								menuLayout.removeAllComponents();
								responsiveLayout.removeAllComponents();
								getUI().getNavigator().navigateTo(LoginView.class.getSimpleName());
							}
						});
			}
		}
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
		menuTitle.addStyleName("hideborder");
		menuTitle.setIcon(vaadinIcons);
		formLayout.addComponents(menuTitle);
		formLayout.setComponentAlignment(menuTitle, Alignment.TOP_CENTER);
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
		} else if (viewCaption.equalsIgnoreCase(TabReport.class.getSimpleName())) {
			return new TabReport(applicationContext);
		} else {
			return null;
		}
	}
	
	private final class ImageResource implements StreamSource {

		private static final long serialVersionUID = 1L;

		@Override
		public InputStream getStream() {
			return new ByteArrayInputStream(user.getImage());
		}
	}
	
	/*private void addTreeAndContentComponent(Set<String> mainTrees, Map<String, Set<String>> subTrees) {
		Tree<String> tree = new Tree<>();
		TreeData<String> treeData = new TreeData<>();

		mainTrees.forEach(mainTree -> {
			treeData.addItems(null, mainTrees);
			subTrees.forEach((captionMainTree, captionSubTree) -> {
				treeData.addItems(captionMainTree, captionSubTree);
			});
		});

		tree.setDataProvider(new TreeDataProvider<>(treeData));

		tree.addStyleName(ValoTheme.TREETABLE_BORDERLESS);
		tree.addStyleName("focus");
		tree.setItemIconGenerator(iconGenerator -> {
			if (iconGenerator.equals("Desktops")) {
				return VaadinIcons.DESKTOP;
			} else if (iconGenerator.equals("Setting")) {
				return VaadinIcons.COGS;
			}
			return null;
		});
		formLayout.addComponent(tree);
		formLayout.setComponentAlignment(tree, Alignment.TOP_CENTER);

		menuContentLayout.addComponent(formLayout);
		menuContentLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);

		tree.addItemClickListener(itemClick -> {
			String caption = itemClick.getItem();
			if (caption.equals("Setting")) {
				tree.expand("Setting");
				tree.collapse("Desktops");
			} else if (caption.equals("Desktops")) {
				tree.expand("Desktops");
				tree.collapse("Setting");
			} else {
				contentLayout.removeAllComponents();
				contentLayout.addComponent(getViewComponent(caption));
				contentLayout.setSizeFull();
				contentLayout.setMargin(false);
				contentLayout.setSpacing(false);
			}
		});
	}*/
}
