package com.emh;

import com.emh.util.Utility;
import com.vaadin.server.VaadinService;

public class Run {

	public static void main(String[] args) {
		System.out.println(Utility.encryptionPassword("12345"));
		System.out.println(Utility.encryptionPassword("MoNgheng#@$fg132"));
		System.out.println(Utility.encryptionPassword("EarMoNgheng#@$fg132"));
		System.out.println(Utility.decryptionPassword("238-341-243-319-323-315-343-323-120-208-125-324-328-167-174-172-"));
		System.out.println(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath());
		//AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring System.......", AlertLevel.WARN);
		//AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring System.......", AlertLevel.ERROR);
		//AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring System.......", AlertLevel.FATAL);
		//test();
	}

	/*private static void test() {
		List<ParkingCashFlow> parkingCashFlows = ConfigApplicationContext.classBusiness.selectListEntity(ParkingCashFlow.class,
				CarParking.class, "carparkingID", "270f8ed3-c09e-4358-b22b-246c7472069b");
		
		parkingCashFlows.forEach(parkingCashFlow -> {
			System.out.println(parkingCashFlow.toString());
		});
	}*/
}
