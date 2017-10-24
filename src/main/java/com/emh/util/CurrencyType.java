package com.emh.util;

import net.sf.dynamicreports.report.builder.datatype.FloatType;

public class CurrencyType extends FloatType {

	private static final long serialVersionUID = 1L;

	@Override
	public String getPattern() {
		return "$ #,###.00";
	}
}
