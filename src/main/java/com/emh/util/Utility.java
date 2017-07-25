package com.emh.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Role;
import com.emh.repository.business.ClassBusiness;

public class Utility {

	public static String encryptionPassword(String password) {
		try {
			if (password != null) {
				char[] cPassword = password.toCharArray();
				StringBuilder s = new StringBuilder();
				for (char c : cPassword) {
					int value = ((int)c + 2) *3;
					s.append(String.valueOf(value));
					s.append("-");
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
			String[] splitPassword = password.split("-");
			StringBuilder decryption = new StringBuilder();
			for (String values : splitPassword) {
				int value = (Integer.valueOf(values) / 3) - 2;
				decryption.append((char)value);
			}
			return decryption.toString();
		}catch(Exception e) {
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
}
