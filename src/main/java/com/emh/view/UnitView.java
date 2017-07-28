package com.emh.view;

import org.springframework.context.ApplicationContext;

import com.emh.model.Floor;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.Utility;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UnitView extends Window {

	private static final long serialVersionUID = 1L;
	
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private Binder<com.emh.model.Unit> binder;
	
	private VerticalLayout vLayout;
	private FormLayout formLayout;
	private HorizontalLayout hFloorLayout;
	private HorizontalLayout hButtonLayout;
	
	private TextField unitNumberField;
	private ComboBox<Floor> cbFloor;
	private Button btnComboFloor;
	private Button btnSave;
	private Button btnCancel;
	
	public UnitView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
		init();
	}
	
	private void init() {
		setCaption("Add New Unit/Room");
		
		binder = new Binder<>();
		
		vLayout = new VerticalLayout();
		formLayout = new FormLayout();
		hFloorLayout = new HorizontalLayout();
		hButtonLayout = new HorizontalLayout();
		
		unitNumberField = new TextField();
		cbFloor = new ComboBox<>();
		btnComboFloor = new Button();
		btnSave = new Button();
		btnCancel = new Button();
		
		unitNumberField.setCaption("Unit/Room Number :");
		unitNumberField.setRequiredIndicatorVisible(true);
		binder.forField(unitNumberField)
			.withConverter(new StringToIntegerConverter("This field is not a Number. Please input Number."))
			.withValidator(unitNumber -> unitNumber > 0, "Number must be greater than zaro (0)")
			.bind(com.emh.model.Unit::getUnitNumber, com.emh.model.Unit::setUnitNumber);
		
		cbFloor.setCaption("Select Floor");
		cbFloor.setRequiredIndicatorVisible(true);
		cbFloor.setItems(Utility.getFloor(applicationContext));
		cbFloor.setItemCaptionGenerator(floor -> floor.getFloorNumber().toString());
		binder.bind(cbFloor, com.emh.model.Unit::getFloor, com.emh.model.Unit::setFloor);
		
		btnComboFloor.setIcon(VaadinIcons.PLUS);
		btnComboFloor.addClickListener(clickEvent -> {
			UI.getCurrent().getNavigator().navigateTo(FloorView.class.getSimpleName());
		});
		
		FormLayout formLayoutFloor = new FormLayout();
		formLayoutFloor.addComponent(cbFloor);
		hFloorLayout.addComponents(formLayoutFloor, btnComboFloor);
		hFloorLayout.setSpacing(false);
		
		btnSave.setCaption("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		
		btnCancel.setCaption("Cancel");
		btnCancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		hButtonLayout.addComponents(btnSave, btnCancel);
		
		formLayout.addComponents(unitNumberField, hFloorLayout, hButtonLayout);
		
		vLayout.addComponent(formLayout);
		vLayout.setSizeFull();
		
		setContent(vLayout);
		center();
		setWidth("400px");
		setHeight("400px");
	}
}
