package com.effecia.netty.socket.client;

import java.util.UUID;

import com.effecia.model.AlertAgent;
import com.effecia.model.AlertLevel;

public class Client {

	public static void main(String[] arg) throws Exception {
		AlertAgent.trigerAlert(UUID.randomUUID().toString(), "Wwarnnnnnnnnnnnnmjhytgfrd4352$_freds", AlertLevel.WARN);
		Thread.sleep(3000);
		AlertAgent.trigerAlert(UUID.randomUUID().toString(), "Fffatalllwith###142Exceptiontest.", AlertLevel.FATAL);
		Thread.sleep(3000);
		AlertAgent.trigerAlert(UUID.randomUUID().toString(), "Eeerrorrr with 142 Exception test.", AlertLevel.ERROR);
	}
	
}