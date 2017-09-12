package com.emh.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Floor;
import com.emh.model.Role;
import com.emh.repository.business.ClassBusiness;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class Utility {

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
}
