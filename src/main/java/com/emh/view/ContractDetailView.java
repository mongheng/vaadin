package com.emh.view;

import com.emh.model.Contract;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ContractDetailView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public ContractDetailView(Contract contract) {
		addComponent(new Label(contract.getContractID()));
		setCaption("Contract");
		setSizeFull();
	}
}
