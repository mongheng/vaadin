package com.emh.controllermvc;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

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
}
