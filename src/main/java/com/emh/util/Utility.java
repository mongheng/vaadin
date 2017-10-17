package com.emh.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.context.ApplicationContext;

import com.emh.model.Floor;
import com.emh.model.HistoryPayment;
import com.emh.model.Role;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Utility {

	public static final String STAFF_PATH = "C:\\staff\\";
	public static final String STAFF_SUBPATH = "image\\";
	public static final String CUSTOMER_PATH = "C:\\customer\\";
	public static final String CUSTOMER_SUBPATH = "image\\";

	public static String encryptionPassword(String password) {
		try {
			if (password != null) {
				int i = 1;
				char[] cPassword = password.toCharArray();
				StringBuilder s = new StringBuilder();
				for (char c : cPassword) {
					int value = (((int) c + 2) * 3) + i;
					s.append(String.valueOf(value));
					s.append("-");
					i++;
				}

				return s.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String decryptionPassword(String password) {
		try {
			int i = 1;
			String[] splitPassword = password.split("-");
			StringBuilder decryption = new StringBuilder();
			for (String values : splitPassword) {
				int value = ((Integer.valueOf(values) - i) / 3) - 2;
				decryption.append((char) value);
				i++;
			}
			return decryption.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean checkFolder(String path) {
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
			return true;
		}
		return false;
	}

	public static byte[] resizeImage(File imageFile) {
		try {
			BufferedImage originalImage = ImageIO.read(imageFile);
			originalImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, 50, 50);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "png", baos);
			baos.flush();
			baos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static OutputStream writeImage(File imageFile, String newPath, String newFileName) {
		try {
			String basePath = newPath + newFileName;
			byte[] bs = resizeImage(imageFile);
			FileOutputStream fileOutputStream = new FileOutputStream(basePath + ".png");
			fileOutputStream.write(bs);
			fileOutputStream.close();
			return fileOutputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readImage(File file) {
		try {
			if (file.isDirectory() && file.exists()) {
				if (file.listFiles().length > 0) {
					BufferedImage originalImage = ImageIO.read(file.listFiles()[0]);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(originalImage, "png", baos);
					baos.flush();
					baos.close();
					return baos.toByteArray();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteDirectory(File file) {
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length > 0) {
				for (File f : files) {
					if (f.isFile()) {
						f.delete();
					} else if (f.isDirectory()) {
						deleteDirectory(f);
					}
				}
			}
		}
		file.delete();
	}

	public static List<Role> getRoles(ApplicationContext applicationContext) {
		try {
			ClassBusiness classBusiness = (ClassBusiness) applicationContext
					.getBean(ClassBusiness.class.getSimpleName());
			if (classBusiness != null) {
				List<Role> roles = (List<Role>) classBusiness.selectAllEntity(Role.class);
				if (roles.size() > 0) {
					return roles;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

	public static List<Floor> getFloor(ApplicationContext applicationContext) {
		try {
			ClassBusiness classBusiness = (ClassBusiness) applicationContext
					.getBean(ClassBusiness.class.getSimpleName());
			if (classBusiness != null) {
				List<Floor> floors = (List<Floor>) classBusiness.selectAllEntity(Floor.class);
				if (floors.size() > 0) {
					return floors;
				}
			}
		} catch (Exception ex) {
			Notification.show(ex.getMessage(), Type.HUMANIZED_MESSAGE);
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

	public static List<User> getEmployee(ApplicationContext applicationContext) {
		try {
			ClassBusiness classBusiness = (ClassBusiness) applicationContext
					.getBean(ClassBusiness.class.getSimpleName());
			if (classBusiness != null) {
				List<User> users = (List<User>) classBusiness.selectAllEntity(User.class);
				if (users.size() > 0) {
					return users;
				}
			}
		} catch (Exception ex) {
			Notification.show(ex.getMessage(), Type.HUMANIZED_MESSAGE);
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

	private static JRDataSource createDataSource(ApplicationContext applicationContext) {
		ClassBusiness classBusiness = (ClassBusiness) applicationContext.getBean(ClassBusiness.class.getSimpleName());
		List<HistoryPayment> historyPayments = classBusiness.selectAllEntity(HistoryPayment.class);
		return new JRBeanCollectionDataSource(historyPayments);
	}

	public static void createReport(ApplicationContext applicationContext) {
		
		InputStream is = titleImageReport();
		
		JasperReportBuilder reportBuilder = DynamicReports.report();

		SubtotalBuilders sbt = DynamicReports.sbt;

		StyleBuilders styleBuilders = DynamicReports.stl;
		StyleBuilder boldStyle = styleBuilders.style().bold();
		StyleBuilder boldCenteredStyle = styleBuilders.style(boldStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder titleStyle = styleBuilders.style(boldCenteredStyle)
				.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE).setFontSize(15);
		StyleBuilder columnTitleStyle = styleBuilders.style(
				boldCenteredStyle);

		TextColumnBuilder<String> customerName = Columns.column("Customer Name", "customerName",
				DataTypes.stringType());
		TextColumnBuilder<Float> amount = Columns.column("Amount", "amount", DataTypes.floatType());
		TextColumnBuilder<Integer> installmentNumber = Columns.column("Installment Number", "installmentNumber",
				DataTypes.integerType());
		TextColumnBuilder<Integer> floorNumber = Columns.column("Floor", "floorNumber", DataTypes.integerType());
		TextColumnBuilder<Integer> unitNumber = Columns.column("Unit", "unitNumber", DataTypes.integerType());

		reportBuilder.columns(customerName, installmentNumber, floorNumber, unitNumber, amount)
				.subtotalsAtSummary(sbt.sum(amount).setLabel("Total Amount")).setTextStyle(boldCenteredStyle)
				.setColumnStyle(
						columnTitleStyle)
				.highlightDetailEvenRows().highlightDetailOddRows()
				.title(Components.horizontalList()
						.add(Components.image(is)
								.setFixedDimension(80, 80),
								Components.text("Dynamic Report").setStyle(titleStyle)
										.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
								Components.text("Getting Started").setStyle(titleStyle)
										.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).newRow()
								.add(Components.filler().setStyle(styleBuilders.style().setTopBorder(styleBuilders.pen2Point())).setFixedHeight(10)))
				.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))
				.setDataSource(createDataSource(applicationContext));

		try {
			File file = new File("C:\\dialyReport");
			if (!file.exists()) {
				file.mkdirs();
			}
			reportBuilder.toPdf(new FileOutputStream("c:/dialyReport/dailyReport.pdf")).show(false);

			/*
			 * ProcessBuilder processBuilder = new ProcessBuilder(
			 * "C:/Program Files (x86)/Foxit Software/Foxit PhantomPDF/FoxitPhantomPDF.exe"
			 * , "c:/dialyReport/dailyReport.pdf"); Process process =
			 * processBuilder.start(); process.waitFor();
			 */
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
}
