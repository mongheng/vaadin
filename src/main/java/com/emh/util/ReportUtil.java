package com.emh.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.HistoryPayment;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinService;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.ExporterBuilders;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilders;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportUtil {

	public static final String PDF = ".pdf";
	public static final String XSL = ".xls";

	private synchronized static JRDataSource createDataSource(ApplicationContext applicationContext) {
		ClassBusiness classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		List<HistoryPayment> historyPayments = classBusiness.selectAllEntity(HistoryPayment.class);
		return new JRBeanCollectionDataSource(historyPayments);
	}

	public static void createReportPDF(ApplicationContext applicationContext, User user, String fileType) {

		InputStream is = titleImageReport();

		JasperReportBuilder reportBuilder = DynamicReports.report();

		SubtotalBuilders sbt = DynamicReports.sbt;

		StyleBuilders styleBuilders = DynamicReports.stl;
		StyleBuilder boldStyle = styleBuilders.style().bold();
		StyleBuilder boldCenteredStyle = styleBuilders.style(boldStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder titleStyle = styleBuilders.style(boldCenteredStyle)
				.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setFontSize(15);
		StyleBuilder columnTitleStyle = styleBuilders.style(boldCenteredStyle);

		TextColumnBuilder<String> customerName = Columns.column("Customer Name", "customerName",
				DataTypes.stringType());
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", DataTypes.floatType());
		TextColumnBuilder<Integer> installmentNumber = Columns.column("Installment Number", "installmentNumber",
				DataTypes.integerType());
		TextColumnBuilder<Integer> floorNumber = Columns.column("Floor", "floorNumber", DataTypes.integerType());
		TextColumnBuilder<Integer> unitNumber = Columns.column("Unit", "unitNumber", DataTypes.integerType());

		reportBuilder.columns(customerName, installmentNumber, floorNumber, unitNumber, amount)
				.subtotalsAtSummary(sbt.sum(amount).setLabel("Total Amount")).setTextStyle(boldCenteredStyle)
				.setColumnStyle(columnTitleStyle).highlightDetailEvenRows().highlightDetailOddRows()
				.title(Components.horizontalList()
						.add(Components.image(is).setFixedDimension(80, 80),
								Components.text("Dynamic Report").setStyle(titleStyle)
										.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
								Components.text("Getting Started").setStyle(titleStyle)
										.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
						.newRow()
						.add(Components.filler().setStyle(styleBuilders.style().setTopBorder(styleBuilders.pen2Point()))
								.setFixedHeight(10)))
				.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))
				.setDataSource(createDataSource(applicationContext));

		try {
			File file = new File("C:\\dailyReport\\" + user.getUsername());
			if (!file.exists()) {
				file.mkdirs();
			}
			if (fileType.equals(PDF)) {
				reportBuilder.toPdf(
						new FileOutputStream("c:/dailyReport/" + user.getUsername() + "/dailyReport" + fileType));
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
		private User user;
		private String fileType;

		public StreamResourceData(User user, String fileType) {
			this.user = user;
			this.fileType = fileType;
		}

		@Override
		public InputStream getStream() {
			File file = new File("c:/dailyReport/" + user.getUsername() + "/dailyReport" + fileType);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ByteArrayInputStream(baos.toByteArray());
		}
	}

	public static void createReportExcel(ApplicationContext applicationContext, User user, String fileType) {

		JasperReportBuilder reportBuilder = DynamicReports.report();
		ExporterBuilders export = DynamicReports.export;
		SubtotalBuilders subtotal = DynamicReports.sbt;
		
		File file = new File("C:\\dailyReport\\" + user.getUsername());
		if (!file.exists()) {
			file.mkdirs();
		}
		
		JasperXlsExporterBuilder jasperXlsExporterBuilder = export
				.xlsExporter("c:/dailyReport/" + user.getUsername() + "/dailyReport" + fileType).setDetectCellType(true)
				.setIgnorePageMargins(true).setWhitePageBackground(false).setRemoveEmptySpaceBetweenColumns(true);
		
		TextColumnBuilder<String> customerName = Columns.column("Customer Name", "customerName",
				DataTypes.stringType());
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", DataTypes.floatType());
		TextColumnBuilder<Integer> installmentNumber = Columns.column("Installment Number", "installmentNumber",
				DataTypes.integerType());
		TextColumnBuilder<Integer> floorNumber = Columns.column("Floor", "floorNumber", DataTypes.integerType());
		TextColumnBuilder<Integer> unitNumber = Columns.column("Unit", "unitNumber", DataTypes.integerType());
		
		try {
			reportBuilder
			  .addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "2")
			  .ignorePageWidth()
			  .ignorePagination()
			  .columns(customerName, installmentNumber, floorNumber, unitNumber, amount)
			  .subtotalsAtSummary(subtotal.sum(amount))
			  .setDataSource(createDataSource(applicationContext))
			  .toXls(jasperXlsExporterBuilder);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
