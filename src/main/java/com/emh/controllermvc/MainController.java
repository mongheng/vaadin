package com.emh.controllermvc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;

@Controller
public class MainController {
	
	final static Logger logger = Logger.getLogger(MainController.class);
	
	@Autowired
	ClassBusiness classBusiness;
	
	@RequestMapping(name = "/index", method = RequestMethod.GET)
	public String loadPage(SitePreference sitePreference, Device device) {
		
		if (sitePreference == SitePreference.MOBILE || device.isMobile()) {
			return "index";
		} else if (sitePreference == SitePreference.TABLET || device.isTablet()) {
			return "index";
		} else {
			return "index";
		}
	}
	
	@RequestMapping(value="/login/{username}/{password}", method=RequestMethod.PUT, headers={"Accept=application/json"})
	public @ResponseBody User userLogin(@PathVariable("username") String username, @PathVariable("password") String password) {
		
		User currentUser = null;
		try {
			
			User tempUser = new User();
			tempUser.setUsername(username);;
			tempUser.setPassword(password);
			
			currentUser = classBusiness.selectUserByUsernameAndPassword(tempUser);
			
			if (currentUser == null)
				currentUser = null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return currentUser;
	}
	
	@RequestMapping(value="/logins/{username}/{password}", method=RequestMethod.PUT, headers={"Accept=application/json"})
	public @ResponseBody boolean userLogins(@PathVariable("username") String username, @PathVariable("password") String password) {
		boolean statu = false;
		User currentUser = null;
		try {
			
			User tempUser = new User();
			tempUser.setUsername(username);;
			tempUser.setPassword(password);
			
			currentUser = classBusiness.selectUserByUsernameAndPassword(tempUser);
			
			statu = currentUser == null ? false : true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return statu;
	}
}
