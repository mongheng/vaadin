package com.emh.view;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.emh.model.CarParking;
import com.emh.model.CashFlow;
import com.emh.model.Contract;
import com.emh.model.Customer;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ExtendContractForm extends Window {

	private static final long serialVersionUID = 1L;
	
	private ApplicationContext applicationContext;
	private ClassBusiness classBusiness;
	
	private FormLayout formLayout;
	private HorizontalLayout hLayout;
	private TextField termField;
	private DateField startDate;
	private DateField endDate;
	private ComboBox<CarParking> cboCarParking;
	private Contract contract;
	private Customer customer;
	private List<CarParking> carParkings;
	private CashFlow cashFlow;
	private Binder<Contract> binder;

	public ExtendContractForm(ApplicationContext applicationContext, Customer customer) {
		this.applicationContext = applicationContext;
		this.customer = customer;
		init();
	}
	
	private void init() {
		classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		contract = (Contract) classBusiness.selectEntityByHQL("From Contract WHERE CUSTOMER_ID = '" + customer.getCustomerID() + "'");
		carParkings = classBusiness.selectListEntityByHQL(CarParking.class, "From CarParking WHERE CUSTOMER_ID = '" + customer.getCustomerID() + "' and ACTIVATE = true");
		
		formLayout = new FormLayout();
		hLayout = new HorizontalLayout();
		binder = new Binder<>();
		
		termField = new TextField("Term :");
		binder.forField(termField).withConverter(new StringToIntegerConverter("Please input Number."))
			.bind(Contract::getTerm, Contract::setTerm);
		termField.addValueChangeListener(valueChange -> {
			if (!termField.getValue().isEmpty() ) {
				endDate.setValue(startDate.getValue().plusMonths(Integer.parseInt(termField.getValue())));
			}
		});
		
		startDate = new DateField("Start End :");
		binder.bind(startDate, Contract::getStartDate, Contract::setStartDate);
		startDate.addValueChangeListener(valueChange -> {
			if (!termField.getValue().isEmpty() ) {
				endDate.setValue(startDate.getValue().plusMonths(Integer.parseInt(termField.getValue())));
			}
		});
		
		endDate = new DateField("End Date :");
		binder.bind(endDate, Contract::getEndDate, Contract::setEndDate);
		
		cboCarParking = new ComboBox<>("Vehicle :");
		cboCarParking.setItems(carParkings);
		cboCarParking.setItemCaptionGenerator(carParking -> {
			if (carParkings.size() > 0) {
				return carParking.getCarType() + " - " + carParking.getPlantNumber();
			}
			return null;
		});
		
		Button btnExtend = new Button("Extend");
		btnExtend.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnExtend.addClickListener(clickEvent -> {
			ConfirmDialog.show(UI.getCurrent(), "Confirmation", "Are you sure " + customer.getCustomerName() + "wants to extend this contract?", "Yes", "No", new ConfirmDialog.Listener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						cashFlow = (CashFlow) classBusiness.selectEntityByHQL("From CashFlow WHERE CONTRACT_ID = '" + contract.getContractID() + "'");
						int term = contract.getTerm();
						int newTerm = Integer.parseInt(termField.getValue());
						LocalDate tempEndDate = null;
						for (int i = 1; i <= newTerm; i++) {
							CashFlow newCashFlow = new CashFlow();
							if (i == 1) {
								newCashFlow.setStartDate(startDate.getValue());
								tempEndDate = startDate.getValue().plusMonths(i);
								newCashFlow.setEndDate(tempEndDate);
								newCashFlow.setAmount(cashFlow.getAmount());
								newCashFlow.setInstallmentNumber(term + i);
								newCashFlow.setContract(contract);
							} else {
								newCashFlow.setStartDate(tempEndDate);
								tempEndDate = tempEndDate.plusMonths(1);
								newCashFlow.setEndDate(tempEndDate);
								newCashFlow.setAmount(cashFlow.getAmount());
								newCashFlow.setInstallmentNumber(term + i);
								newCashFlow.setContract(contract);
							}
							classBusiness.createEntity(newCashFlow);
						}
						com.emh.model.Unit unit = customer.getUnit();
						unit.setStatu(true);
						classBusiness.updateEntity(unit);
						customer.setClose(false);
						classBusiness.updateEntity(customer);
						contract.setEndDate(endDate.getValue());
						contract.setTerm(term + newTerm);
						classBusiness.updateEntity(contract);
						Notification.show(customer.getCustomerName() + " have been Extended Contract.");
						close();
					}
				}
			});
		});
		
		Button btnCancel = new Button("Cancel");
		btnCancel.addClickListener(listener -> {
			close();
		});
		
		hLayout.addComponents(btnExtend, btnCancel);
		hLayout.setComponentAlignment(btnExtend, Alignment.TOP_CENTER);
		hLayout.setComponentAlignment(btnCancel, Alignment.TOP_CENTER);
		
		formLayout.addComponents(termField, cboCarParking, startDate, endDate, hLayout);
		formLayout.setSizeFull();
		formLayout.setComponentAlignment(termField, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(startDate, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(endDate, Alignment.TOP_CENTER);
		
		setContent(formLayout);
		setCaption("Extend Form");
		center();
		setWidth("400px");
		setHeight("350px");
		
		if (contract != null) {
			startDate.setValue(LocalDate.now());
		}	
	}
}
