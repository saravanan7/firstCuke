package framework.methods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import framework.constants.FrameworkConstants;

public class FrameworkMethods {
	public static final String CHROME = "chrome";
	public static EventFiringWebDriver browser = null;
	public static Map<String, EventFiringWebDriver> chromeBinaryMap = null;


	public static EventFiringWebDriver getChromeDriver(){
		try{
			System.out.println("User directory:"+System.getProperty("user.dir"));
			System.out.println("User Home:"+System.getProperty("user.home"));
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Extensions\\drivers\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			if(chromeBinaryMap==null){
				chromeBinaryMap = new HashMap<String, EventFiringWebDriver>();
			}
			browser=new EventFiringWebDriver(driver);

			chromeBinaryMap.put("browser", browser);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		}catch(Exception e){

		}
		return browser;
	}

	public static List<String> buildGlueCommand(String[] args) {

		List<String> argList = new ArrayList<String>();

		String strScenario=args[3];
		String strTestMode=args[2].toLowerCase();

		if (StringUtils.isNotBlank(strScenario)) {
			argList.add("--glue");
			argList.add("stepDefinition");
			argList.add("src/testScripts/feature/"+strTestMode);
			argList.add("--plugin");
			argList.add("html:Reports/html");
			argList.add("--plugin");
			argList.add("junit:Reports/junit/cucumber.xml");
			argList.add("--tags");
			argList.add(strScenario);
			argList.add("--strict");
		} else {
			System.out.println("Unable to start the test --> Test scenario not specified");
			return null;
		}
		return argList;
	}

	public static List<String> buildGlueCommand(String TestMode, String sceariosList) {

		List<String> argList = new ArrayList<String>();
//		String subPath=FrameworkConstants.getTestMode()+File.separator+FrameworkConstants.getTestEnvironment();
		if (!sceariosList.equals(null)) {
			argList.add("--glue");
			argList.add("stepDefinition");
			argList.add("src/main/resources/feature/"+TestMode);
			argList.add("--tags");
			argList.add(sceariosList);
			argList.add("--strict");
		} else {
			System.out.println("Unable to start the test 2nd method --> Test scenario not specified");
			return null;
		}
		return argList;
	}

	@SuppressWarnings("unchecked")
	public static List<String>[] splitTheList(List<String> data, int splitUnits) {
		if (splitUnits >= data.size()) {
			return new List[] { data };
		}

		List<String>[] results = null;
		Iterator<String> iter = data.iterator();

		if ((data.size() % splitUnits) > 0) {
			results = new List[(data.size() / splitUnits) + 1];
		} else {
			results = new List[data.size() / splitUnits];
		}

		for (int i = 0; i < results.length; i++) {
			results[i] = new ArrayList<String>();
		}

		int count = 0;

		while (iter.hasNext())
			results[count++ / splitUnits].add(iter.next());

		return results;
	}

	public static Map<String, Integer> getChunkConfiguration() {
		int threadPoolSize = 1;
		int scenariosChunkSize = 1000;
		Map<String, Integer> chunkConfig = new HashMap<String, Integer>();

		try {
			if (FrameworkConstants.config.getString("reExecuteScenarios").equalsIgnoreCase("YES")) {
				threadPoolSize = 1;
				scenariosChunkSize = 1;
			} else {
				threadPoolSize =Integer.parseInt(FrameworkConstants.config.getString("noOfInstances"));
				scenariosChunkSize = Integer.parseInt(FrameworkConstants.config.getString("scenarioPerInstance"));
			}
			chunkConfig.put("threadPoolSize", threadPoolSize);
			chunkConfig.put("scenariosChunkSize", scenariosChunkSize);
		} catch (Exception e) {
		}
		return chunkConfig;
	}

	public static String getCurrentStepDesc(String strStep, String[] args) {
		try {
			strStep = ((strStep.replaceAll("\\$", "").trim()).replaceAll("[^a-zA-Z]", " ")).replaceAll("\\s", "X");

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					strStep = strStep.replaceFirst("XXXXX", " " + args[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Occurred: getCurrentStepDesc: "+e.toString());
		}
		return strStep;
	}

	public static String getCurrentMethodInfo(String[] args) {
		StackTraceElement[] ste = null;
		String className = null;
		Method[] methods = null;
		String methodName = null;

		try {
			ste = Thread.currentThread().getStackTrace();
			className = ste[2].getClassName();
			methodName = ste[2].getMethodName();

			methods = Class.forName(className).getDeclaredMethods();

			for (Method method : methods) {

				if (method.getName().equals(methodName)) {
					String anotation = method.getDeclaredAnnotations()[0].toString();
					String anotationValue = anotation.substring(anotation.indexOf("value=") + 7);
//					System.out.println(anotationValue);
					methodName = getCurrentStepDesc(anotationValue, args);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Occurred: getCurrentMethodInfo: "+e.toString());
		}
		return methodName;
	}


}
