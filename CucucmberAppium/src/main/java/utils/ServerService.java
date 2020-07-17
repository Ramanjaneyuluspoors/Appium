package utils;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class ServerService {
	static AppiumDriverLocalService service;
	static boolean flag=checkIfServerIsRunnning(4723);
	//
	public static AppiumDriverLocalService services(String excutablePath, String jsPath) {
		if(!flag) {
			service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
					.usingDriverExecutable(new File(excutablePath)).withAppiumJS(new File(jsPath)));
			service.start();
		}
		return service;
	}
	
	public static void startService() {
	    service.start();
	}

	public static void stopService() {
		service.stop();
	}
 
     //check if server is running
	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

 
}
