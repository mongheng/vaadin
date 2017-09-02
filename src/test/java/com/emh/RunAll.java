package com.emh;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.effecia.model.AlertAgent;
import com.effecia.model.AlertLevel;
import com.emh.configuration.ConfigApplicationContext;
import com.emh.model.CarParking;
import com.emh.model.ParkingCashFlow;

public class RunAll {

	@Test
	public void getParkingCashFlow() {
		List<ParkingCashFlow> parkingCashFlows = ConfigApplicationContext.classBusiness.selectListEntity(ParkingCashFlow.class,
				CarParking.class, "carparkingID", "270f8ed3-c09e-4358-b22b-246c7472069b");
		
		parkingCashFlows.forEach(parkingCashFlow -> {
			System.out.println(parkingCashFlow.toString());
		});
	}
	
	@Test
	public void alertSystem() {
		AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring System.......", AlertLevel.WARN);
	}
}
