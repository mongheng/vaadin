package com.emh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Floor;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class FloorView extends Window {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private VerticalLayout vLayout;
	private FormLayout formLayout;
	private HorizontalLayout hLayout;

	private TextField fNumberField;
	private TextField totalNumberField;
	private Button btnSave;
	private Button btnCancel;
	private Grid<Floor> grid;
	private Binder<Floor> binder;

	private ListDataProvider<Floor> floorDataProvider;
	private Floor updateFloor;

	public FloorView(ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
		init();
	}

	private void init() {
		vLayout = new VerticalLayout();
		formLayout = new FormLayout();
		hLayout = new HorizontalLayout();

		grid = new Grid<>();
		binder = new Binder<>();

		fNumberField = new TextField();
		fNumberField.setCaption("Floor Number :");
		fNumberField.setRequiredIndicatorVisible(true);
		binder.forField(fNumberField)
				.withConverter(new StringToIntegerConverter("This field is not a Number. Please input Number."))
				.withValidator(floorNumber -> floorNumber > 0, "Number must be greater than zaro (0)")
				.bind(Floor::getFloorNumber, Floor::setFloorNumber);

		totalNumberField = new TextField();
		totalNumberField.setCaption("Total Unit/Room :");
		binder.forField(totalNumberField)
				.withConverter(new StringToIntegerConverter("This field is not a Number. Please input Number."))
				.bind(Floor::getTotalFloor, Floor::setTotalFloor);

		btnSave = new Button();
		btnSave.setCaption("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.addClickListener(new SaveClickAction());

		btnCancel = new Button();
		btnCancel.setCaption("Clear");
		btnCancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnCancel.addClickListener(event -> {
			fNumberField.clear();
			fNumberField.setEnabled(true);
			totalNumberField.clear();
			fNumberField.focus();
			btnSave.setEnabled(true);
		});

		hLayout.addComponents(btnSave, btnCancel);
		hLayout.setComponentAlignment(btnSave, Alignment.MIDDLE_CENTER);
		hLayout.setSpacing(true);

		formLayout.addComponents(fNumberField, totalNumberField, hLayout);
		formLayout.setComponentAlignment(fNumberField, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(totalNumberField, Alignment.TOP_CENTER);

		initGrid();

		vLayout.addComponents(formLayout, grid);
		vLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		vLayout.setSpacing(true);

		setCaption("Add New Floor");
		setContent(vLayout);
		center();
		setModal(true);
		// setResizable(false);

		setWidth("555px");
		setHeight("450px");

	}

	private void initGrid() {
		List<Floor> floors = classBusiness.selectAllEntity(Floor.class);

		if (floors.size() > 0) {
			floorDataProvider = new ListDataProvider<>(floors);
		} else {
			floorDataProvider = new ListDataProvider<>(new ArrayList<>());
		}

		grid.setDataProvider(floorDataProvider);

		Column<Floor, Integer> columnFloorNumber = grid.addColumn(Floor::getFloorNumber);
		columnFloorNumber.setCaption("Floor Number");
		columnFloorNumber.setId("0");
		columnFloorNumber.setWidth(130);
		// columnFloorNumber.setEditorComponent(new
		// TextField(),Floor::setFloorNumber);

		Column<Floor, Integer> columnTotalUnit = grid.addColumn(Floor::getTotalFloor);
		columnTotalUnit.setCaption("Total Unit");
		columnTotalUnit.setId("1");
		columnTotalUnit.setWidth(130);

		Column<Floor, String> columnDelete = grid.addColumn(floor -> "Delete", new ButtonRenderer<>(event -> {
			Floor floor = event.getItem();
			List<com.emh.model.Unit> units = classBusiness.selectListEntity(com.emh.model.Unit.class, Floor.class,
					"floorID", floor.getFloorID());
			if (units.size() > 0) {
				Notification.show("This floor can not deleted becuase it relates to other unit.", Type.ERROR_MESSAGE);
			} else {
				if (floor != null) {
					classBusiness.deleteEntity(floor);

					Notification.show("The Floor is deleted Succussfully.");
					floorDataProvider.getItems().remove(floor);
					grid.setDataProvider(floorDataProvider);
				}
			}
			btnSave.setEnabled(true);
		}));
		columnDelete.setCaption("Delete Action");
		columnDelete.setWidth(130);

		Column<Floor, String> columnUpdate = grid.addColumn(floor -> "Update", new ButtonRenderer<>(event -> {
			try {
				if (updateFloor != null) {
					classBusiness.updateEntity(updateFloor);

					Notification.show("The data update successfully.", Type.HUMANIZED_MESSAGE);

					List<Floor> listFloor = classBusiness.selectAllEntity(Floor.class);
					floorDataProvider.getItems().clear();
					floorDataProvider = new ListDataProvider<>(listFloor);
					grid.setDataProvider(floorDataProvider);

					fNumberField.clear();
					totalNumberField.clear();
					updateFloor = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			btnSave.setEnabled(true);
		}));
		columnUpdate.setCaption("Update Action");
		columnUpdate.setWidth(130);

		grid.setSizeFull();

		grid.addItemClickListener(itemClick -> {
			updateFloor = itemClick.getItem();
			List<com.emh.model.Unit> units = classBusiness.selectListEntity(com.emh.model.Unit.class, Floor.class,
					"floorID", updateFloor.getFloorID());
			if (units.size() > 0) {
				fNumberField.setEnabled(false);
			} else {
				fNumberField.setEnabled(true);
			}
			binder.readBean(updateFloor);
			btnSave.setEnabled(false);
		});
	}

	private final class SaveClickAction implements ClickListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {

			try {
				Floor floor = new Floor();
				binder.writeBean(floor);
				floorDataProvider.getItems().add(floor);
				grid.setDataProvider(floorDataProvider);
				classBusiness.createEntity(floor);
				Notification.show("The Floor save successfully.", Type.HUMANIZED_MESSAGE);

				fNumberField.clear();
				totalNumberField.clear();
				fNumberField.focus();
			} catch (Exception e) {
				Notification.show(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
