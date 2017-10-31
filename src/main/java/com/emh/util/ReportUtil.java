package com.emh.util;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.HistoryPayment;
import com.emh.model.Payment;
import com.emh.model.User;
import com.emh.model.mock.MockCashFlow;
import com.emh.model.mock.MockParkingCashFlow;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinService;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.ExporterBuilders;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.BooleanColumnBuilder;
import net.sf.dynamicreports.report.builder.column.ColumnBuilders;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilders;
import net.sf.dynamicreports.report.constant.BooleanComponentType;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportUtil {

	public static final String PDF = ".pdf";
	public static final String XSL = ".xls";
	private static final String REPORT_PATH = "C:\\dailyReport\\";

	private static ComponentBuilder<?, ?> HeaderStyle(StyleBuilder titleStyle, StyleBuilders styleBuilders,
			String title1, String title2) {
		InputStream is = titleImageReport();
		return Components.horizontalList().add(Components.image(is).setFixedDimension(80, 80),
				Components.text(title1).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
				Components.text(title2).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
				.newRow().add(Components.filler()
						.setStyle(styleBuilders.style().setTopBorder(styleBuilders.pen2Point())).setFixedHeight(10));
	}

	private static JRDataSource createDataSource(List<?> items) {

		return new JRBeanCollectionDataSource(items);
	}

	public synchronized static void createReportPDF(List<HistoryPayment> historyPayments, User user, String path,
			String fileType) {

		JasperReportBuilder reportBuilder = DynamicReports.report();

		SubtotalBuilders sbt = DynamicReports.sbt;

		StyleBuilders styleBuilders = DynamicReports.stl;
		StyleBuilder boldStyle = styleBuilders.style().bold();
		StyleBuilder boldCenteredStyle = styleBuilders.style(boldStyle).setFontName("FreeUniversal")
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder titleStyle = styleBuilders.style(boldCenteredStyle)
				.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setFontSize(15);
		StyleBuilder columnTitleStyle = styleBuilders.style(boldCenteredStyle);

		TextColumnBuilder<String> customerName = Columns.column("Customer Name", "customerName",
				DataTypes.stringType());
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", new CurrencyType());
		TextColumnBuilder<Integer> installmentNumber = Columns.column("Installment Number", "installmentNumber",
				DataTypes.integerType());
		TextColumnBuilder<Integer> floorNumber = Columns.column("Floor", "floorNumber", DataTypes.integerType());
		TextColumnBuilder<Integer> unitNumber = Columns.column("Unit", "unitNumber", DataTypes.integerType());
		TextColumnBuilder<String> carType = Columns.column("Vehicle", "carType", DataTypes.stringType());
		TextColumnBuilder<String> plantNumber = Columns.column("PlantNumber", "plantNumber", DataTypes.stringType());

		reportBuilder.columns(customerName, installmentNumber, floorNumber, unitNumber, carType, plantNumber, amount)
				.subtotalsAtSummary(sbt.sum(amount).setLabel("Total Amount")).setTextStyle(boldCenteredStyle)
				.setColumnStyle(columnTitleStyle).highlightDetailEvenRows().highlightDetailOddRows()
				.title(HeaderStyle(titleStyle, styleBuilders, "Dynamic Report", "Getting Started"))
				.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))
				.setDataSource(createDataSource(historyPayments));

		try {
			File file = new File(REPORT_PATH + user.getUsername());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (fileType.equals(PDF)) {
				FileOutputStream fos = new FileOutputStream(path + fileType);
				reportBuilder.toPdf(fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static InputStream titleImageReport() {
		String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource fileResource = new FileResource(new File(path + "/WEB-INF/images/report.png"));
		File fileImage = fileResource.getSourceFile();
		InputStream is = null;
		try {
			is = new FileInputStream(fileImage);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return is;
	}

	public static class StreamResourceData implements StreamSource {

		private static final long serialVersionUID = 1L;
		private String fileType;
		private String path;

		public StreamResourceData(String path, String fileType) {
			this.path = path;
			this.fileType = fileType;
		}

		@Override
		public InputStream getStream() {
			File file = new File(path + fileType);
			ByteArrayOutputStream baos = null;
			InputStream ios = null;
			try {
				byte[] bs = new byte[4096];
				baos = new ByteArrayOutputStream();
				ios = new FileInputStream(file);
				int read = 0;
				while ((read = ios.read(bs)) != -1) {
					baos.write(bs, 0, read);
				}
				ios.close();
				baos.flush();
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ByteArrayInputStream(baos.toByteArray());
		}
	}

	public synchronized static void createReportExcel(ApplicationContext applicationContext, User user, String path,
			String fileType) {

		ClassBusiness classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		List<HistoryPayment> historyPayments = classBusiness.selectAllEntity(HistoryPayment.class);

		JasperReportBuilder reportBuilder = DynamicReports.report();
		ExporterBuilders export = DynamicReports.export;
		SubtotalBuilders subtotal = DynamicReports.sbt;

		StyleBuilders styleBuilders = DynamicReports.stl;
		StyleBuilder boldStyle = styleBuilders.style().bold();
		StyleBuilder boldCenteredStyle = styleBuilders.style(boldStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

		File file = new File(REPORT_PATH + user.getUsername());
		if (!file.exists()) {
			file.mkdirs();
		}

		JasperXlsExporterBuilder jasperXlsExporterBuilder = export.xlsExporter(path + fileType).setDetectCellType(true)
				.setIgnorePageMargins(true).setWhitePageBackground(false).setRemoveEmptySpaceBetweenColumns(true);

		TextColumnBuilder<String> customerName = Columns.column("Customer Name", "customerName", DataTypes.stringType())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", new CurrencyType())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		TextColumnBuilder<Integer> installmentNumber = Columns
				.column("Installment Number", "installmentNumber", DataTypes.integerType())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		TextColumnBuilder<Integer> floorNumber = Columns.column("Floor", "floorNumber", DataTypes.integerType())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		TextColumnBuilder<Integer> unitNumber = Columns.column("Unit", "unitNumber", DataTypes.integerType())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		TextColumnBuilder<String> carType = Columns.column("Vehicle", "carType", DataTypes.stringType())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		TextColumnBuilder<String> plantNumber = Columns.column("PlantNumber", "plantNumber", DataTypes.stringType())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

		try {
			reportBuilder.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "2").ignorePageWidth().ignorePagination()
					.columns(customerName, installmentNumber, floorNumber, unitNumber, carType, plantNumber, amount)
					.setColumnTitleStyle(boldCenteredStyle)
					.subtotalsAtSummary(subtotal.sum(amount).setStyle(boldCenteredStyle))
					.setDataSource(createDataSource(historyPayments)).toXls(jasperXlsExporterBuilder);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void createCashFlowReportPDF(List<MockCashFlow> cashFlows, User user, String info,
			String path, String fileType) {
		JasperReportBuilder reportBuilder = DynamicReports.report();

		ColumnBuilders columnBuilders = DynamicReports.col;

		StyleBuilders styleBuilders = DynamicReports.stl;
		StyleBuilder boldStyle = styleBuilders.style().bold();
		StyleBuilder boldCenteredStyle = styleBuilders.style(boldStyle).setFontName("FreeUniversal")
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder titleStyle = styleBuilders.style(boldCenteredStyle)
				.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setFontSize(15);
		StyleBuilder columnTitleStyle = styleBuilders.style(boldCenteredStyle).setForegroundColor(Color.BLUE);

		TextColumnBuilder<Integer> rowNumberColumn = columnBuilders.reportRowNumberColumn("No.").setFixedColumns(2)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		TextColumnBuilder<String> startDate = Columns.column("Start Date", "startDate", DataTypes.stringType());
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", new CurrencyType());
		TextColumnBuilder<Integer> installmentNumber = Columns.column("Installment Number", "installmentNumber",
				DataTypes.integerType());
		TextColumnBuilder<String> endDate = Columns.column("End Date", "endDate", DataTypes.stringType());
		BooleanColumnBuilder paid = Columns.booleanColumn("Paid", "statu")
				.setComponentType(BooleanComponentType.IMAGE_STYLE_3);

		reportBuilder.columns(rowNumberColumn, installmentNumber, amount, startDate, endDate, paid)
				.setColumnTitleStyle(columnTitleStyle).setColumnStyle(boldCenteredStyle).highlightDetailEvenRows()
				.highlightDetailOddRows().title(HeaderStyle(titleStyle, styleBuilders, "CashFlow", info))
				.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))
				.setDataSource(createDataSource(cashFlows));

		try {
			File file = new File(REPORT_PATH + user.getUsername());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (fileType.equals(PDF)) {
				FileOutputStream fos = new FileOutputStream(path + fileType);
				reportBuilder.toPdf(fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void createParkingCashFlowReportPDF(List<MockParkingCashFlow> parkingCashFlows,
			User user, String info, String path, String fileType) {
		JasperReportBuilder reportBuilder = DynamicReports.report();

		StyleBuilders styleBuilders = DynamicReports.stl;
		StyleBuilder boldStyle = styleBuilders.style().bold();
		StyleBuilder boldCenteredStyle = styleBuilders.style(boldStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder titleStyle = styleBuilders.style(boldCenteredStyle)
				.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setFontSize(15);
		StyleBuilder columnTitleStyle = styleBuilders.style(boldCenteredStyle).setForegroundColor(Color.BLUE);

		TextColumnBuilder<String> startDate = Columns.column("Start Date", "startDate", DataTypes.stringType());
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", new CurrencyType());
		TextColumnBuilder<Integer> installmentNumber = Columns.column("Installment Number", "installmentNumber",
				DataTypes.integerType());
		TextColumnBuilder<String> endDate = Columns.column("End Date", "endDate", DataTypes.stringType());

		reportBuilder.columns(installmentNumber, amount, startDate, endDate).setColumnTitleStyle(columnTitleStyle)
				.setColumnStyle(boldCenteredStyle).highlightDetailEvenRows().highlightDetailOddRows()
				.title(HeaderStyle(titleStyle, styleBuilders, "CashFlow", info))
				.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))
				.setDataSource(createDataSource(parkingCashFlows));

		try {
			File file = new File(REPORT_PATH + user.getUsername());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (fileType.equals(PDF)) {
				FileOutputStream fos = new FileOutputStream(path + fileType);
				reportBuilder.toPdf(fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized static void exportInvoiceFile(Payment payment, User user, String info, String path,
			String fileType) {

		List<Payment> payments = Arrays.asList(payment);

		JasperReportBuilder reportBuilder = DynamicReports.report();

		StyleBuilders styleBuilders = DynamicReports.stl;
		StyleBuilder boldStyle = styleBuilders.style().bold();
		StyleBuilder boldCenteredStyle = styleBuilders.style(boldStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder titleStyle = styleBuilders.style(boldCenteredStyle)
				.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setFontSize(15);
		StyleBuilder columnTitleStyle = styleBuilders.style(boldCenteredStyle).setForegroundColor(Color.BLUE);

		TextColumnBuilder<String> startDate = Columns.column("Start Date", "startDate", DataTypes.stringType());
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", new CurrencyType());
		TextColumnBuilder<Integer> installmentNumber = Columns.column("Installment Number", "installmentNumber",
				DataTypes.integerType());
		TextColumnBuilder<String> endDate = Columns.column("End Date", "endDate", DataTypes.stringType());
		TextColumnBuilder<String> vehicle = Columns.column("Vehicle", "carType", DataTypes.stringType());
		TextColumnBuilder<String> plantNum = Columns.column("Plant Num", "plantNumber", DataTypes.stringType());

		reportBuilder.columns(installmentNumber, startDate, endDate, vehicle, plantNum, amount)
				.setColumnTitleStyle(columnTitleStyle).setColumnStyle(boldCenteredStyle).highlightDetailEvenRows()
				.highlightDetailOddRows().title(HeaderStyle(titleStyle, styleBuilders, "Invoice", info))
				.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle),
						Components.text("Issue Date:" + LocalDate.now().toString())
								.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
				.setDataSource(createDataSource(payments));

		try {
			File file = new File(REPORT_PATH + user.getUsername());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (fileType.equals(PDF)) {
				FileOutputStream fos = new FileOutputStream(path + fileType);
				reportBuilder.toPdf(fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void createInvoiceReportPDF(Payment payment, User user) {
		String path = "c:/dailyReport/" + user.getUsername() + "/InvoiceReport-" + payment.getCustomerName();
		String info = payment.getCustomerName() + "- F:" + payment.getFloorNumber() + "- R/U:"
				+ payment.getUnitNumber();
		if (payment.getCarType() != null) {
			path = path + "-" + payment.getCarType() + "-" + payment.getPlantNumber() + "-" + LocalDate.now();
		} else {
			path = path + "-" + payment.getInstallmentNumber() + "-" + LocalDate.now();
		}
		exportInvoiceFile(payment, user, info, path, PDF);
		FileResource fr = new FileResource(new File(path + PDF));
		Page.getCurrent().open(fr, null, false);
	}
}
