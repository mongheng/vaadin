package com.emh.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.Role;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.Utility;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Label;
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
	private ClassBusiness classBusiness;
	private Grid<User> grid;
	private TextField tfUserName;
	private ComboBox<Role> cbRole;
	private ListDataProvider<User> userDataProvider;

	public UserListView(ClassBusiness classBusiness) {
		this.classBusiness = classBusiness;
		init();
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	private void init() {

		List<User> users = getUsers();
		if (users != null) {
			userDataProvider = new ListDataProvider<>(users);
		} else {
			userDataProvider = new ListDataProvider<>(new ArrayList<>());
		}
		grid = new Grid<>();

		grid.setDataProvider(userDataProvider);
		tfUserName = new TextField();

		Column<User, String> columnUserName = grid.addColumn(User::getUsername);
		columnUserName.setCaption("UserName");
		columnUserName.setId("0");
		// columnUserName.setWidth(220);
		columnUserName.setEditorComponent(tfUserName, User::setUsername).setExpandRatio(1);

		Column<User, String> columnTelephon = grid.addColumn(User::getTelephone);
		columnTelephon.setCaption("Telephone");
		columnTelephon.setId("1");
		// columnTelephon.setWidth(165);
		columnTelephon.setEditorComponent(new TextField(), User::setTelephone);

		Column<User, String> columnEmail = grid.addColumn(User::getEmail);
		columnEmail.setCaption("Email");
		columnEmail.setId("2");
		// columnEmail.setWidth(290);
		columnEmail.setEditorComponent(new TextField(), User::setEmail);

		cbRole = new ComboBox<>();
		cbRole.setItems(Utility.getRoles(classBusiness));
		cbRole.setItemCaptionGenerator(Role::getRoleName);
		Binder<User> userBinder = grid.getEditor().getBinder();
		Binding<User, Role> userBinding = userBinder.bind(cbRole, User::getRole, User::setRole);
		Column<User, String> columnRole = grid.addColumn(user -> user.getRole().getRoleName());
		columnRole.setCaption("Role");
		columnRole.setId("3");
		// columnRole.setWidth(165);
		columnRole.setEditorBinding(userBinding);
		// columnRole.setEditorComponent(cbRole,
		// User::setRole::setRoleName).setExpandRatio(2);

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
		columnButton.setId("4");
		columnButton.setCaption("Delete Action");

		Column<User, String> columnUpdate = grid.addColumn(user -> "Update", new ButtonRenderer<>(clickEvent -> {
			User user = clickEvent.getItem();
			if (user != null) {
				getUI();

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
		columnUpdate.setId("5");
		columnUpdate.setCaption("Update Action");

		setFilterGrid(userDataProvider);
		// grid.setSelectionMode(SelectionMode.MULTI);
		grid.setStyleName("v-grid");
		grid.sort(columnUserName, SortDirection.ASCENDING);
		grid.getEditor().setEnabled(true);
		grid.addSelectionListener(new GridSelectionListener());

		Label title = new Label("User Information");
		title.addStyleName(ValoTheme.LABEL_H3);

		grid.setSizeFull();
		grid.setSortOrder(GridSortOrder.asc(columnUserName));

		addComponent(grid);
		setSizeFull();
	}

	private List<User> getUsers() {
		try {
			if (classBusiness != null) {
				List<User> users = classBusiness.selectAllEntity(User.class);
				if (users.size() > 0) {
					return users;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<>();
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

		HeaderRow row = grid.prependHeaderRow(); // Adds a new row at the top of
													// the header section.
		HeaderRow filterRow = grid.appendHeaderRow(); // Adds a new row at the
														// bottom of the header
														// section.
		// FooterRow footerRow = grid.prependFooterRow();

		for (Column<User, ?> column : grid.getColumns()) {
			if (!column.getCaption().equals("Delete Action") && !column.getCaption().equals("Update Action")) {
				/*
				 * HeaderCell topCell = filterRowTop.getCell(column);
				 * topCell.setText(column.getCaption());
				 */
				HeaderCell filterCell = filterRow.getCell(column);
				filterCell.setComponent(createTextFieldFilter(users, column));

				/*
				 * FooterCell footerCell = footerRow.getCell(column); if
				 * (column.getId().equals("2")) { footerCell.setText("Email"); }
				 */
			}
		}
		HeaderCell cell = row.join(row.getCell("0"), row.getCell("1"), row.getCell("2"), row.getCell("3"),
				row.getCell("4"), row.getCell("5"));
		cell.setText("User Information");
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
