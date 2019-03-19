package framework.methods;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.events.EventFiringWebDriver;

public class BrowserCluster {
	public static final String CHROME = "chrome";
	public static final String IE = "internetExplorer";
	public static final String FIREFOX = "firefox";
	public static final String SAFARI = "safari";
	public static final String ANDROID_DRIVER = "android";
	public static final String IOS_DRIVER = "ios";
	public static EventFiringWebDriver browser = null;
	public static Map<String, EventFiringWebDriver> chromeMap = null;
	public static Map<String, String> browserTypeMap = null;

	private static BrowserClusterHelper browserClusterHelper = null;


	public static EventFiringWebDriver getBrowser(long threadId) {
		EventFiringWebDriver eventFiringWebDriver = null;
		String tagThreadId = Long.toString(threadId);
		try {
			if (chromeMap != null && chromeMap.get(tagThreadId) != null) {
				eventFiringWebDriver = (EventFiringWebDriver) chromeMap.get(tagThreadId);
			} else{
				System.out.println("No browser/driver is initiated. Initiating browser for the threadID: "+tagThreadId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventFiringWebDriver;
	}

	public static synchronized EventFiringWebDriver getNewBrowser(long threadId) {
		EventFiringWebDriver eventFiringWebDriver = null;
		String tagThreadId = Long.toString(threadId);

		if (browserClusterHelper == null) {
			browserClusterHelper = new BrowserClusterHelper();
		}

		// Just to give enough time for the new browser to instantiate
		try {
			eventFiringWebDriver = getBrowser(threadId);
			if (eventFiringWebDriver != null) {
				return eventFiringWebDriver;
			}

			// Given some time to browser to instantiate
			Thread.sleep(2000L);


			// DRIVER INITIALIZATION BEGINS HERE
			if (CHROME.equals(browserTypeMap.get(tagThreadId))) {
				// CHROME
				if (chromeMap == null) {
					chromeMap = new HashMap<String, EventFiringWebDriver>();
				}
				eventFiringWebDriver = browserClusterHelper.chromeDriverInit();
				chromeMap.put(tagThreadId, eventFiringWebDriver);
				System.out.println("chromeMap size:"+chromeMap.size());

			} else if (IE.equals(browserTypeMap.get(tagThreadId))) {
				// INTERNET EXPLORER

			} else if (FIREFOX.equals(browserTypeMap.get(tagThreadId))) {
				// FIREFOX


			} else if (SAFARI.equals(browserTypeMap.get(tagThreadId))) {
				// SAFARI

			} else if (ANDROID_DRIVER.equals(browserTypeMap.get(tagThreadId))) {

			} else if (IOS_DRIVER.equals(browserTypeMap.get(tagThreadId))) {
				// IOS/IPHONE DRIVER

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventFiringWebDriver;
	}

	public static synchronized void setBrowserType(long threadId, String browserType) {
		if (browserTypeMap == null) {
			browserTypeMap = Collections.synchronizedMap(new HashMap<String, String>());
		}
		browserTypeMap.put(Long.toString(threadId), browserType);
	}

	public static synchronized void exitBrowser(long threadId, String browserType){
		String browserThreadId = Long.toString(threadId);
		try{
			System.out.println("1:"+browserTypeMap.get(browserThreadId));
			System.out.println("chromeMap=>"+chromeMap.size());
			System.out.println("2:"+chromeMap.get(browserThreadId));
			if (CHROME.equals(browserTypeMap.get(browserThreadId))) {
				if (chromeMap != null && chromeMap.get(browserThreadId) != null) {
					System.out.println("Closing Chrome browser instance tagged with thread ID <"+browserThreadId+">");
					chromeMap.get(browserThreadId).quit();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
