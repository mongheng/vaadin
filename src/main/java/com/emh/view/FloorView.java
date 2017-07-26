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
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class FloorView extends Window {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private VerticalLayout vLayout;
	private FormLayout formLayout;
	private HorizontalLayout hLayout;
	
	private Grid<Floor> grid;
	private Binder<Floor> binder;
	
	private ListDataProvider<Floor> floorDataProvider;

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
		
		TextField fNumberField = new TextField();
		fNumberField.setCaption("Floor Number :");
		fNumberField.setRequiredIndicatorVisible(true);
		binder.forField(fNumberField)
			.withConverter(new StringToIntegerConverter("This field is not a Number. Please input Number."))
			.withValidator(floorNumber -> floorNumber > 0, "Number must be greater than zaro (0)")
			.bind(Floor::getFloorNumber, Floor::setFloorNumber);
		
		TextField totalNumberField = new TextField();
		totalNumberField.setCaption("Total Unit/Room :");
		binder.forField(totalNumberField)
			.withConverter(new StringToIntegerConverter("This field is not a Number. Please input Number."))
			.bind(Floor::getTotalFloor, Floor::setTotalFloor);
		
		Button btnSave = new Button();
		btnSave.setCaption("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		
		Button btnCancel = new Button();
		btnCancel.setCaption("Cancel");
		btnCancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnCancel.addClickListener(event -> {
			this.close();
		});
		
		hLayout.addComponents(btnSave, btnCancel);
		hLayout.setComponentAlignment(btnSave, Alignment.MIDDLE_CENTER);
		hLayout.setSpacing(true);
		
		formLayout.addComponents(fNumberField, totalNumberField,hLayout);
		formLayout.setComponentAlignment(fNumberField, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(totalNumberField, Alignment.TOP_CENTER);
		
		vLayout.addComponents(formLayout);
		vLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		
		setCaption("Add New Floor");
		setContent(vLayout);
		center();
		setModal(true);
		//setResizable(false);
		
		setWidth("450px");
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
		//columnFloorNumber.setEditorComponent(new TextField(), Floor::setFloorNumber);
		
		
	}
}
