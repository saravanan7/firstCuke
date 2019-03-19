package framework.methods;

import java.net.URL;
import java.rmi.Remote;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import framework.constants.FrameworkConstants;

public class BrowserClusterHelper {

	DesiredCapabilities capabilities =null;
	String platform_name=FrameworkConstants.config.getString("execPlatform");
	String record_video=FrameworkConstants.config.getString("record_video");
	public EventFiringWebDriver chromeDriverInit() throws Exception {
		EventFiringWebDriver eventFiringWebDriver = null;
		WebDriver webDriver = null;
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+FrameworkConstants.config.getString("chromeDriver"));
		capabilities = DesiredCapabilities.chrome();

		if (platform_name.equalsIgnoreCase("win7")) {
			capabilities.setPlatform(Platform.VISTA);
		}
		if (platform_name.equalsIgnoreCase("win8")) {
			capabilities.setPlatform(Platform.WIN8);
		}
		if (platform_name.equalsIgnoreCase("win8_1")) {
			capabilities.setPlatform(Platform.WIN8_1);
		}
		if (platform_name.equalsIgnoreCase("win10")) {
			capabilities.setPlatform(Platform.WIN10);
		}
		if (platform_name.equalsIgnoreCase("linux")) {
			capabilities.setPlatform(Platform.LINUX);
		}
		//		capabilities.setBrowserName(FrameworkConstants.CHROME);
		//		capabilities.setVersion(FrameworkConstants.VERSION);

		// video record
		if (record_video.equalsIgnoreCase("YES")) {
			//			capabilities.setCapability("video", "True"); // NOTE: "True" is a case sensitive string, not boolean.
		} else {
			capabilities.setCapability("video", "False"); // NOTE: "False" is a case sensitive string, not boolean.
		}

		ChromeOptions options = new ChromeOptions();
		if (platform_name.equalsIgnoreCase("linux")) {
			options.addArguments(Arrays.asList("--window-position=0,0"));
			options.addArguments(Arrays.asList("--window-size=1920,1080"));	
		} else {
			options.addArguments(Arrays.asList("--start-maximized"));
		}
		options.addArguments("chrome.switches", "--disable-extensions");
		options.addArguments("disable-infobars");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		if ("Yes".equalsIgnoreCase(FrameworkConstants.config.getString("remote_Driver"))) {
			webDriver = new RemoteWebDriver(new URL(FrameworkConstants.config.getString("remote_Driver_URL")), capabilities);
			//		    webDriver = new RemoteWebDriver(new URL(), capabilities);
		} else {
			webDriver = new ChromeDriver(capabilities);
		}
		webDriver.manage().deleteAllCookies();
		eventFiringWebDriver = new EventFiringWebDriver(webDriver);
		eventFiringWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


		return eventFiringWebDriver;
	}

}
