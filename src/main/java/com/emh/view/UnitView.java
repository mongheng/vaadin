package com.emh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.Floor;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.Utility;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UnitView extends Window {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private com.emh.model.Unit unit;
	private Binder<com.emh.model.Unit> binder;

	private ListDataProvider<com.emh.model.Unit> unitDataProvider;

	private AbsoluteLayout absoluteLayout;

	private Label unitNumberLabel;
	private TextField unitNumberField;
	private Label floorLabel;
	private ComboBox<Floor> cbFloor;
	private Button btnComboFloor;
	private Button btnSave;
	private Button btnCancel;
	private Label unitNumberLabelFilter;
	private TextField unitNumberFieldFilter;

	private Grid<com.emh.model.Unit> grid;

	public UnitView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
		init();
	}

	private void init() {
		setCaption("Add New Unit/Room");

		binder = new Binder<>();

		grid = new Grid<>();

		absoluteLayout = new AbsoluteLayout();
		unitNumberLabelFilter = new Label();
		unitNumberLabelFilter.setValue("FilterUnit :");

		unitNumberFieldFilter = new TextField();
		unitNumberFieldFilter.setWidth("90px");
		unitNumberFieldFilter.setHeight("30px");
		unitNumberFieldFilter.addValueChangeListener(valueChangeEvent -> {
			String filterText = valueChangeEvent.getValue();
			List<com.emh.model.Unit> newUnit = new ArrayList<>();
			List<com.emh.model.Unit> units = (List<com.emh.model.Unit>) unitDataProvider.getItems();

			units.forEach(unit -> {
				if (unit.getUnitNumber().toString().contains(filterText)) {
					newUnit.add(unit);
				}
			});
			grid.setDataProvider(new ListDataProvider<>(newUnit));
		});

		unitNumberLabel = new Label();
		unitNumberLabel.setValue("Unit/Room Number :");

		unitNumberField = new TextField();
		cbFloor = new ComboBox<>();
		btnComboFloor = new Button();
		btnSave = new Button();
		btnCancel = new Button();

		// unitNumberField.setRequiredIndicatorVisible(true);
		binder.forField(unitNumberField)
				.withConverter(new StringToIntegerConverter("This field is not a Number. Please input Number."))
				.withValidator(unitNumber -> unitNumber > 0, "Number must be greater than zaro (0)")
				.bind(com.emh.model.Unit::getUnitNumber, com.emh.model.Unit::setUnitNumber);

		floorLabel = new Label();
		floorLabel.setValue("Select Floor :");

		// cbFloor.setRequiredIndicatorVisible(true);
		cbFloor.setItems(Utility.getFloor(applicationContext));
		cbFloor.setItemCaptionGenerator(floor -> floor.getFloorNumber().toString());
		binder.bind(cbFloor, com.emh.model.Unit::getFloor, com.emh.model.Unit::setFloor);

		btnComboFloor.setIcon(VaadinIcons.PLUS);
		btnComboFloor.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnComboFloor.setWidth("35px");
		btnComboFloor.setHeight("33px");
		btnComboFloor.addClickListener(clickEvent -> {
			UI.getCurrent().addWindow(new FloorView(applicationContext));
		});

		btnSave.setCaption("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.addClickListener(new SaveClickListener());

		btnCancel.setCaption("Cancel");
		btnCancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnCancel.addClickListener(eventClick -> {
			this.close();
		});

		grid.setHeight("190px");

		initGrid();

		absoluteLayout.addComponent(unitNumberLabel, "top:20.0px;left:30.0px;");
		absoluteLayout.addComponent(unitNumberField, "top:15.0px;left:183.0px;");
		absoluteLayout.addComponent(floorLabel, "top:75.0px;left:82.0px;");
		absoluteLayout.addComponent(cbFloor, "top:66.0px;left:183.0px;");
		absoluteLayout.addComponent(btnComboFloor, "top:66.0px;left:369.0px;");
		absoluteLayout.addComponent(btnSave, "top:121.0px;left:183.0px;");
		absoluteLayout.addComponent(btnCancel, "top:121.0px;left:287.0px;");
		absoluteLayout.addComponent(unitNumberLabelFilter, "top:194.0px;left:355.0px;");
		absoluteLayout.addComponent(unitNumberFieldFilter, "top:189.0px;left:439.5px;");
		absoluteLayout.addComponent(grid, "top:220.0px;left:30.0px;");

		setContent(absoluteLayout);
		center();
		setWidth("560px");
		setHeight("455px");
	}

	private void initGrid() {
		List<com.emh.model.Unit> units = classBusiness.selectAllEntity(com.emh.model.Unit.class);

		if (units.size() > 0) {
			unitDataProvider = new ListDataProvider<>(units);
		} else {
			unitDataProvider = new ListDataProvider<>(new ArrayList<>());
		}

		grid.setDataProvider(unitDataProvider);

		Column<com.emh.model.Unit, Integer> columnUnitNumber = grid.addColumn(com.emh.model.Unit::getUnitNumber);
		columnUnitNumber.setCaption("Unit/Room Number");

		Column<com.emh.model.Unit, Integer> columnFloorNumber = grid
				.addColumn(unit -> unit.getFloor().getFloorNumber());
		columnFloorNumber.setCaption("Floor Number");

		grid.addItemClickListener(itemClick -> {
			unit = itemClick.getItem();
			binder.readBean(unit);
		});
	}

	private final class SaveClickListener implements ClickListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {

			try {
				if (unit == null) {
					boolean valid = binder.writeBeanIfValid(unit);
					if (valid) {
						unitDataProvider.getItems().add(unit);
						grid.setDataProvider(unitDataProvider);
						classBusiness.createEntity(unit);

						Notification.show("The Unit save successfully.");

						unitNumberField.clear();
						cbFloor.clear();
						unitNumberField.focus();
					} else {
						Notification.show("The Unit save failure. Please input again.", Type.ERROR_MESSAGE);
						unitNumberField.focus();
					}
				} else {
					ConfirmDialog.show(UI.getCurrent(), "Confirmation", "Are you sure you want to update this unit?",
							"Yes", "No", new ConfirmDialog.Listener() {

								private static final long serialVersionUID = 1L;

								@Override
								public void onClose(ConfirmDialog dialog) {
									boolean valid = binder.writeBeanIfValid(unit);
									if (valid) {
										classBusiness.updateEntity(unit);
										unitDataProvider.refreshItem(unit);
										grid.setDataProvider(unitDataProvider);
										Notification.show("The Unit update successfully.");
										unitNumberField.clear();
										cbFloor.clear();
										unitNumberField.focus();
									} else {
										Notification.show("The Unit update failure. Please Check again.", Type.ERROR_MESSAGE);
										unitNumberField.focus();
									}
								}
							});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
