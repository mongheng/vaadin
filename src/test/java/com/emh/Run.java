package com.emh;

import java.util.List;
import java.util.UUID;

import com.effecia.model.AlertAgent;
import com.effecia.model.AlertLevel;
import com.emh.configuration.ConfigApplicationContext;
import com.emh.model.CarParking;
import com.emh.model.ParkingCashFlow;
import com.emh.util.Utility;

public class Run {

	public static void main(String[] args) {
		System.out.println(Utility.encryptionPassword("MoNgheng#@$fg132"));
		System.out.println(Utility.decryptionPassword("237-339-240-315-318-309-336-315-111-198-114-312-315-153-159-156-"));
		AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring System.......", AlertLevel.WARN);
		//AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring System.......", AlertLevel.ERROR);
		//AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring System.......", AlertLevel.FATAL);
		test();
	}

	private static void test() {
		List<ParkingCashFlow> parkingCashFlows = ConfigApplicationContext.classBusiness.selectListEntity(ParkingCashFlow.class,
				CarParking.class, "carparkingID", "270f8ed3-c09e-4358-b22b-246c7472069b");
		
		parkingCashFlows.forEach(parkingCashFlow -> {
			System.out.println(parkingCashFlow.toString());
		});
	}
}
