package com.emh.controllermvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;

@RestController
public class RestAPI {

	@Autowired
	ClassBusiness classBusiness;

	@RequestMapping(value = "/logins/{username}/{password}", method = RequestMethod.PUT, headers = {
			"Accept=application/json" })
	public @ResponseBody boolean userLogins(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		boolean statu = false;
		User currentUser = null;
		try {

			User tempUser = new User();
			tempUser.setUsername(username);
			
			tempUser.setPassword(password);

			currentUser = classBusiness.selectUserByUsernameAndPassword(tempUser);

			statu = currentUser == null ? false : true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return statu;
	}

	@GetMapping(value = "/items", headers = { "Accept=application/json" })
	public <T> List<T> getItems() {
		try {

			@SuppressWarnings("unchecked")
			List<T> users = (List<T>) classBusiness.selectAllEntity(User.class);
			if (users != null)
				return users;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
