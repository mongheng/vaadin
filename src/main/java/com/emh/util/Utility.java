package com.emh.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.context.ApplicationContext;

import com.emh.model.Floor;
import com.emh.model.Role;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

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
					if(f.isFile()) {
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
}
