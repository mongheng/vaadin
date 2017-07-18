package com.emh.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.Role;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.Utility;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class UserListView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;
	private Grid<User> grid;
	private TextField tfUserName;
	private ComboBox<Role> cbRole;
	private ListDataProvider<User> userDataProvider;

	public UserListView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		init();
	}

	@Override
	public void enter(ViewChangeEvent event) {		

	}
	
	private void init() {
		List<User> users = getUsers();
		userDataProvider = new ListDataProvider<>(users);
		grid = new Grid<>();

		grid.setDataProvider(userDataProvider);
		tfUserName = new TextField();

		Column<User, String> columnUserName = grid.addColumn(User::getUsername);
		columnUserName.setCaption("UserName");
		columnUserName.setId("0");
		columnUserName.setWidth(170);
		columnUserName.setEditorComponent(tfUserName, User::setUsername).setExpandRatio(1);

		Column<User, String> columnTelephon = grid.addColumn(User::getTelephone);
		columnTelephon.setCaption("Telephone");
		columnTelephon.setId("1");
		columnTelephon.setWidth(170);

		Column<User, String> columnEmail = grid.addColumn(User::getEmail);
		columnEmail.setCaption("Email");
		columnEmail.setId("2");
		columnEmail.setWidth(170);

		cbRole = new ComboBox<>();
		cbRole.setItems(Utility.getRoles(applicationContext));
		cbRole.setItemCaptionGenerator(Role::getRoleName);
		Binder<User> userBinder = grid.getEditor().getBinder();
		Binding<User, Role> userBinding = userBinder.bind(cbRole, User::getRole, User::setRole);
		Column<User, String> columnRole = grid.addColumn(user->user.getRole().getRoleName());
		columnRole.setCaption("Role");
		columnRole.setId("3");
		columnRole.setWidth(170);
		columnRole.setEditorBinding(userBinding);
		//columnRole.setEditorComponent(cbRole, User::setRole::setRoleName).setExpandRatio(2);

		Column<User, String> columnButton = grid.addColumn(user -> "Delete", new ButtonRenderer<>(clickEvent -> {

			User user = clickEvent.getItem();
			getUI();
			ConfirmDialog.show(UI.getCurrent(), "Confirm Information",
					"Are you sure you want to delete " + user.getUsername(), "Yes", "No", new ConfirmDialog.Listener() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								userDataProvider.getItems().remove(user);
								grid.setDataProvider(userDataProvider);
								Notification.show("Your Item have been deleted.", Type.HUMANIZED_MESSAGE);
							} else {
								dialog.close();
							}
						}
					});

		}));
		columnButton.setCaption("Delete Action");

		Column<User, String> columnUpdate = grid.addColumn(user -> "Update", new ButtonRenderer<>(clickEvent -> {
			User user = clickEvent.getItem();
			ClassBusiness classBusiness;
			if (user != null) {
				getUI();

				classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
				ConfirmDialog.show(UI.getCurrent(), "Confirmation", "Are you sure you want to update.", "Yes", "No",
						new ConfirmDialog.Listener() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onClose(ConfirmDialog dialog) {
								if (dialog.isConfirmed()) {
									classBusiness.updateEntity(user);
									Notification.show("Your have been updated.", Type.HUMANIZED_MESSAGE);
								} else {
									dialog.close();
								}

							}
						});
			} else {
				Notification.show("Please select the item if you want to update.", Type.HUMANIZED_MESSAGE);
			}
		}));
		columnUpdate.setCaption("Update Action");

		setFilterGrid(userDataProvider);
		// grid.setSelectionMode(SelectionMode.MULTI);
		grid.setStyleName("v-grid");
		grid.sort(columnUserName, SortDirection.ASCENDING);
		grid.getEditor().setEnabled(true);
		grid.addSelectionListener(new GridSelectionListener());

		// HeaderRow groupingHeader = grid.prependHeaderRow();

		/*
		 * HeaderCell personInformationCell =
		 * groupingHeader.join(groupingHeader.getCell("UserName"),
		 * groupingHeader.getCell("Telephone"),
		 * groupingHeader.getCell("Email")); personInformationCell.setText(
		 * "Person Information"); HeaderCell accessRoleCell =
		 * groupingHeader.join(groupingHeader.getCell("Role"));
		 * accessRoleCell.setText("Access Role");
		 */
		
		Label title = new Label("User Information.");
		title.addStyleName(ValoTheme.LABEL_H1);

		addComponents(title,grid);
		grid.setWidth("950px");
		grid.setHeight("300px");
		setMargin(new MarginInfo(true, false, false, false));
		setComponentAlignment(grid, Alignment.TOP_CENTER);
		setComponentAlignment(title, Alignment.TOP_CENTER);
	}

	private List<User> getUsers() {
		try {
			ClassBusiness classBusiness = (ClassBusiness) applicationContext
					.getBean(ClassBusiness.class.getSimpleName());
			if (classBusiness != null) {
				List<User> users = classBusiness.selectAllEntity(User.class);
				if (users.size() > 0) {
					return users;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private final class GridSelectionListener implements SelectionListener<User> {

		private static final long serialVersionUID = 1L;

		@Override
		public void selectionChange(SelectionEvent<User> event) {

			Set<User> users = grid.getSelectedItems();

			users.forEach(user -> {
				System.out.format("%s,%s,%s,%s,%s", user.getUsername(), user.getPassword(), user.getTelephone(),
						user.getEmail(), user.getRole().getRoleName());
				System.out.println();
			});
		}
	}

	private void setFilterGrid(ListDataProvider<User> users) {

		HeaderRow filterRow = grid.prependHeaderRow();

		for (Column<User, ?> column : grid.getColumns()) {
			if (!column.getCaption().equals("Delete Action") && !column.getCaption().equals("Update Action")) {
				HeaderCell filterCell = filterRow.getCell(column);
				filterCell.setComponent(createTextFieldFilter(users, column));
			}

		}
	}

	private TextField createTextFieldFilter(ListDataProvider<User> users, Column<User, ?> column) {
		TextField filterField = new TextField();
		filterField.setHeight("26px");
		filterField.setWidth("100%");

		filterField.addValueChangeListener(change -> {
			String filterText = change.getValue();
			List<User> newFilterUser = new ArrayList<>();
			List<User> filterUser = (List<User>) users.getItems();
			filterUser.forEach(userfilter -> {

				switch (column.getId()) {
				case "0":
					if (userfilter.getUsername().contains(filterText)) {
						newFilterUser.add(userfilter);
					}
					break;
				case "1":
					if (userfilter.getTelephone().contains(filterText)) {
						newFilterUser.add(userfilter);
					}
					break;
				case "2":
					if (userfilter.getEmail().contains(filterText)) {
						newFilterUser.add(userfilter);
					}
					break;
				case "3":
					if (userfilter.getRole().getRoleName().contains(filterText)) {
						newFilterUser.add(userfilter);
					}
					break;
				default:
					break;
				}

			});
			grid.setDataProvider(new ListDataProvider<>(newFilterUser));
		});
		return filterField;
	}
}
