package com.emh.controllermvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping(value = "/items", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = "http://localhost:4200")
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

	@GetMapping(value = "/item", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = "http://localhost:4200")
	public User getUser(@RequestParam("id") String userid) {

		User user = classBusiness.selectEntity(User.class, userid);
		try {
			if (user != null)
				return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(value = "/item/{userid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = "http://localhost:4200")
	public User getUserByPath(@PathVariable String userid) {

		User user = classBusiness.selectEntity(User.class, userid);
		try {
			if (user != null)
				return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/save", headers = { "Accept=application/json" })
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean saveItem(@RequestBody User user) {
		if (user != null) {
			// classBusiness.createEntity(user);
			System.out.println(user.getUsername());
			return true;
		}
		return false;
	}

	@PostMapping(value = "/saves", headers = { "Accept=application/json" })
	public boolean saveItems(@RequestBody List<User> users) {
		if (users.size() > 0) {
			return users.stream()/*
									 * .filter(user ->
									 * user.getRole().getRoleID().equals(
									 * "c24266d6-28ee-4a3d-859a-2cb514c46115"))
									 */
					.anyMatch(user -> user.getRole().getRoleID().equals("c24266d6-28ee-4a3d-859a-2cb514c46115")
							&& user.getUsername().equals("mongheng"));
		}
		return false;
	}

	@DeleteMapping(value = "delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean deleteUser(@PathVariable String id) {
		try {
			User user = classBusiness.selectEntity(User.class, id);
			if (user != null) {
				classBusiness.deleteEntity(user);
				System.out.println(user.getUserid() + ", " + user.getUsername());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
