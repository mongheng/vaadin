package com.emh.view.report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.HistoryPayment;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.ReportUtil;
import com.emh.util.Utility;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ResponsiveReportView extends CssLayout {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	private User user;
	private User userSession;
	private VerticalLayout mainLayout;
	private VerticalLayout topLayout;
	private VerticalLayout centerLayout;
	private HorizontalLayout topFormHLayout;
	private HorizontalLayout topButtonHLayout;
	private FormLayout formLayout;

	private DateField startDate;
	private DateField endDate;
	private ComboBox<User> cboEmployee;
	private Button btnSearch;
	private Button btnClear;

	private Button btnExtra;
	private FileDownloader fileDownloader;

	private List<HistoryPayment> historyPayments;

	private ListDataProvider<HistoryPayment> dataProvider;
	private Grid<HistoryPayment> grid;

	public ResponsiveReportView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.classBusiness = (ClassBusiness) this.applicationContext.getBean(ClassBusiness.class.getSimpleName());
		init();
	}

	private void init() {
		userSession = (User) UI.getCurrent().getSession().getAttribute(User.class);
		mainLayout = new VerticalLayout();
		topLayout = new VerticalLayout();
		centerLayout = new VerticalLayout();
		topFormHLayout = new HorizontalLayout();
		topButtonHLayout = new HorizontalLayout();
		formLayout = new FormLayout();

		initTopLayout();

		initGrid();

		mainLayout.addComponents(topLayout, centerLayout);
		mainLayout.setExpandRatio(topLayout, 0.5f);
		mainLayout.setExpandRatio(centerLayout, 1.5f);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);

		addComponent(mainLayout);
		setSizeFull();
		setCaption("Report");

		if (historyPayments.size() > 0) {
			ExportFile();
		}
	}

	private void initTopLayout() {
		startDate = new DateField("Start Date");
		startDate.setTextFieldEnabled(true);
		startDate.setDateFormat("dd/MM/yyyy");

		endDate = new DateField("End Date");
		endDate.setTextFieldEnabled(true);
		endDate.setDateFormat("dd/MM/yyyy");

		cboEmployee = new ComboBox<>("Empolyee");
		cboEmployee.setItems(Utility.getEmployee(applicationContext));
		cboEmployee.setItemCaptionGenerator(User::getUsername);
		cboEmployee.addValueChangeListener(valueChange -> {
			user = valueChange.getValue();
		});

		btnSearch = new Button("Search");
		btnSearch.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSearch.addClickListener(clickEvent -> {
			String HQL = null;
			if (startDate.getValue() != null && endDate.getValue() != null && user != null) {
				HQL = "FROM HistoryPayment WHERE RECEIVE_DATE >= '" + startDate.getValue() + "' and RECEIVE_DATE <= '"
						+ endDate.getValue() + "' and USER_ID = '" + user.getUserid() + "'";
			} else if (startDate.getValue() != null && endDate.getValue() != null) {
				HQL = "FROM HistoryPayment WHERE RECEIVE_DATE >= '" + startDate.getValue() + "' and RECEIVE_DATE <= '"
						+ endDate.getValue() + "'";
			} else if (startDate.getValue() == null && endDate.getValue() == null && user != null) {
				HQL = "FROM HistoryPayment WHERE USER_ID = '" + user.getUserid() + "'";
			}
			if (HQL != null) {
				search(HQL);
				if (historyPayments.size() > 0) {
					ExportFile();
				}
			}
		});

		btnClear = new Button("Clear");
		btnClear.addClickListener(clickEvent -> {
			clear();
		});

		btnExtra = new Button("Export");
		btnExtra.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		topFormHLayout.addComponents(startDate, endDate, cboEmployee);
		topButtonHLayout.addComponents(btnSearch, btnClear, btnExtra);
		formLayout.addComponents(topFormHLayout, topButtonHLayout);
		formLayout.addStyleName("caption");

		topLayout.addComponent(formLayout);
		topLayout.setSizeFull();
		topLayout.setSpacing(false);
		topLayout.setMargin(false);
	}

	private void initGrid() {
		grid = new Grid<>();
		String HQL = "FROM HistoryPayment WHERE RECEIVE_DATE = '" + LocalDate.now() + "'";
		historyPayments = classBusiness.selectListEntityByHQL(HistoryPayment.class, HQL);
		dataProvider = historyPayments.size() > 0 ? new ListDataProvider<>(historyPayments)
				: new ListDataProvider<>(new ArrayList<>());

		grid.setDataProvider(dataProvider);

		grid.addColumn(HistoryPayment::getCustomerName).setCaption("Customer Name");
		grid.addColumn(HistoryPayment::getInstallmentNumber).setCaption("Installement Num");
		grid.addColumn(HistoryPayment::getAmount).setCaption("Amount");
		grid.addColumn(HistoryPayment::getFloorNumber).setCaption("Floor Num");
		grid.addColumn(HistoryPayment::getUnitNumber).setCaption("Unit Num");
		grid.addColumn(HistoryPayment::getCarType).setCaption("Vehicle");
		grid.addColumn(HistoryPayment::getPlantNumber).setCaption("Plant Number");

		grid.setSizeFull();

		centerLayout.addComponent(grid);
		centerLayout.setSizeFull();
		centerLayout.setSpacing(false);
	}

	private void search(String HQL) {
		historyPayments = classBusiness.selectListEntityByHQL(HistoryPayment.class, HQL);
		dataProvider = historyPayments.size() > 0 ? new ListDataProvider<>(historyPayments)
				: new ListDataProvider<>(new ArrayList<>());
		grid.setDataProvider(dataProvider);
	}

	private void ExportFile() {
		String path = "c:/dailyReport/" + userSession.getUsername() + "/dailyReport-" + LocalDate.now();
		ReportUtil.createReportPDF(historyPayments, userSession, path, ReportUtil.PDF);
		ReportUtil.StreamResourceData streamSource = new ReportUtil.StreamResourceData(path, ReportUtil.PDF);
		// ReportUtil.createReportExcel(applicationContext, userSession, path,
		// ReportUtil.XSL);
		// ReportUtil.StreamResourceData streamSource = new
		// ReportUtil.StreamResourceData(path, ReportUtil.XSL);
		StreamResource sr = new StreamResource(streamSource,
				"report-" + LocalDate.now() + "-" + System.currentTimeMillis() + ReportUtil.PDF);
		fileDownloader = new FileDownloader(sr);
		fileDownloader.extend(btnExtra);

		// We can use this way to download file.
		/*
		 * FileResource fr = new FileResource(new File(path + ReportUtil.PDF));
		 * Page.getCurrent().open(fr, null, false);
		 */
	}

	private void clear() {
		startDate.clear();
		endDate.clear();
		cboEmployee.clear();
		dataProvider = new ListDataProvider<>(new ArrayList<>());
		grid.setDataProvider(dataProvider);
	}
}
