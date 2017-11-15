package com.emh.view;

import java.util.List;

import com.emh.model.Floor;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ResponsiveAvariableUnit extends CssLayout {

	private static final long serialVersionUID = 1L;

	private ClassBusiness classBusiness;

	private HorizontalLayout innerHLayout;
	private Panel mainPanel;
	private VerticalLayout verticalLayout;
	private HorizontalLayout hLayout;

	private int index = 1;
	private int size = 1;
	private boolean statu;

	public ResponsiveAvariableUnit(ClassBusiness classBusiness) {
		this.classBusiness = classBusiness;
		init();
	}

	private void init() {
		innerHLayout = new HorizontalLayout();
		mainPanel = new Panel();
		verticalLayout = new VerticalLayout();

		List<Floor> floors = classBusiness.selectAllEntity(Floor.class);

		if (floors.size() > 0) {
			floors.forEach(floor -> {
				Panel innerPanel = new Panel("Floor Number : " + floor.getFloorNumber());
				hLayout = new HorizontalLayout();
				List<com.emh.model.Unit> units = classBusiness.selectListEntity(com.emh.model.Unit.class, Floor.class,
						"floorID", floor.getFloorID());
				if (units.size() > 0) {
					units.forEach(unit -> {
						String info = "Unit:" + unit.getUnitNumber();
						Button btnUnit = new Button(info);
						btnUnit.setId(unit.getUnitNumber().toString());
						if (unit.isStatu()) {
							btnUnit.addStyleName(ValoTheme.BUTTON_DANGER);
						} else {
							btnUnit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
						}
						btnUnit.addClickListener(clickEvent -> {
							System.out.println(clickEvent.getButton().getStyleName());
						});
						hLayout.addComponent(btnUnit);
					});
					innerPanel.setContent(hLayout);
					innerPanel.setWidth(552, Unit.PIXELS);
					innerPanel.setHeight(88, Unit.PIXELS);
					innerPanel.addStyleNames("scrollable","buttoncaptionsize");
					if (index == 1) {
						innerHLayout.addComponent(innerPanel);
						index++;
						if(floors.size() == size) {
							innerHLayout.addComponent(innerPanel);
							verticalLayout.addComponent(innerHLayout);
							statu = false;
						}
						statu = true;
						size++;
					} else if (index == 2) {
						innerHLayout.addComponent(innerPanel);
						verticalLayout.addComponent(innerHLayout);
						innerHLayout = new HorizontalLayout();
						index = 1;
						if(floors.size() == size) {
							innerHLayout.addComponent(innerPanel);
							verticalLayout.addComponent(innerHLayout);
						}
						size++;
						statu = false;
					}
				}
			});
			if (statu) {
				verticalLayout.addComponent(innerHLayout);
			}
			//verticalLayout.setSizeFull();
			verticalLayout.addStyleName("paneltitle");
			mainPanel.setContent(verticalLayout);
			mainPanel.setIcon(VaadinIcons.BUILDING_O);
			mainPanel.setWidth(1145, Unit.PIXELS);
			mainPanel.setHeight(660, Unit.PIXELS);
			mainPanel.setStyleName("scrollable");
			mainPanel.setCaption("Floor/Unit Information");
		}
		addComponent(mainPanel);
	}
}
