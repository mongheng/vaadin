package com.emh;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.emh.util.Utility;

public class Run {

	public static void main(String[] args) {
		System.out.println(Utility.encryptionPassword("12345"));
		System.out.println(Utility.encryptionPassword("MoNgheng#@$fg132"));
		System.out.println(Utility.encryptionPassword("EarMoNgheng#@$fg132"));
		System.out.println(
				Utility.decryptionPassword("238-341-243-319-323-315-343-323-120-208-125-324-328-167-174-172-"));
		//System.out.println(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath());
		// AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring
		// System.......", AlertLevel.WARN);
		// AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring
		// System.......", AlertLevel.ERROR);
		// AlertAgent.trigerAlert(UUID.randomUUID().toString(), "MongHeng Waring
		// System.......", AlertLevel.FATAL);
		// test();
		
		Date date = new Date();
		date = java.sql.Date.valueOf(LocalDate.now());
		
		System.out.println("Date = " + new Date());
		System.out.println("Convert = " + date.toString());
		
		LocalDate now = LocalDate.now();
		LocalDate end = now.withDayOfMonth(now.lengthOfMonth());
		System.out.println("end date is : " + end.toString());
		
		try {
			testHttpClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * private static void test() { List<ParkingCashFlow> parkingCashFlows =
	 * ConfigApplicationContext.classBusiness.selectListEntity(ParkingCashFlow.
	 * class, CarParking.class, "carparkingID",
	 * "270f8ed3-c09e-4358-b22b-246c7472069b");
	 * 
	 * parkingCashFlows.forEach(parkingCashFlow -> {
	 * System.out.println(parkingCashFlow.toString()); }); }
	 */
	
	private static void testHttpClient() throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut("http://localhost:8090/vaadinproject/logins/sok/12345");
	 
	    HttpResponse response = client.execute(httpPut);
	    System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
	 
	}
}
