package com.emh.view;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Floor;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ResponsiveAvariableUnit extends CssLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;

	private Panel panel;
	private VerticalLayout verticalLayout;
	private HorizontalLayout hLayout;

	public ResponsiveAvariableUnit(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
		init();
	}

	private void init() {
		panel = new Panel();
		verticalLayout = new VerticalLayout();

		List<Floor> floors = classBusiness.selectAllEntity(Floor.class);
		if (floors.size() > 0) {
			floors.forEach(floor -> {
				hLayout = new HorizontalLayout();
				List<com.emh.model.Unit> units = classBusiness.selectListEntity(com.emh.model.Unit.class, Floor.class,
						"floorID", floor.getFloorID());
				units.forEach(unit -> {
					String info = "Floor:" + floor.getFloorNumber() + "\n" + "Unit:" + unit.getUnitNumber();
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
				verticalLayout.addComponent(hLayout);
			});
			verticalLayout.setSizeFull();
			verticalLayout.setSpacing(false);
			panel.setContent(verticalLayout);
			
		}
		addComponent(panel);
	}
}
