package framework.constants;


import java.io.File;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.builder.combined.CombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class FrameworkConstants {

	public static String CHROME="chrome";
	public static String VERSION = "latest";
	public static String FIREFOX="firefox";
	public static String IE="ie";
	public static String SAFARI="safari";
	public static String OPERA="opera";

	public static String CHROME_DRIVER="chromeDriver";
	public static String GECKO_DRIVER="geckoDriver";
	public static String IE_DRIVER="ieDriver";
	public static String SAFARI_DRIVER="safariDriver";
	public static String OPERA_DRIVER="operaDriver";

	public static String MAXIMIZE_BROWSER="maximizeBrowser";
	public static String CLOSE_BROWSER="closeBrowser";
	public static String CLEAR_COOKIES="clearCookies";

	public static String REPORT_PATH="reportPath";
	public static String HTML_REPORT_PATH="htmlReportPath";
	public static String TAKE_pass_SCREENSHOT="takepassScreenshot";

	public static String PARALLEL_EXECUTION="parallelExecution";
	public static String CROSS_BROWSER_EXECUTION="crossBrowserExecution";

	public static String DESKTOP="desktop";
	public static String MOBILE="mobile";
	public static String TABLET="tablet";

	public static String MOBILE_DEVICE_NAME="mobileDeviceName";
	public static String MOBILE_DEVICE_MODEL="mobileDeviceModel";
	public static String TABLET_DEVICE_NAME="tabletDeviceName";
	public static String TABLET_DEVICE_MODEL="tabletDeviceModel";

	public static String TEST_MODE=null;
	public static String TEST_ENVIRONMENT=null;
	public static String TEST_REPORT_PATH=null;


	public static CombinedConfiguration config=null;

	public static FrameworkConstants frameworkConstant=new FrameworkConstants();

	public FrameworkConstants getInstance() {
		return frameworkConstant;
	}

	public static void getConfig() throws ConfigurationException{
		// TODO Auto-generated constructor stub
		Parameters params = new Parameters();
		CombinedConfigurationBuilder builder = new CombinedConfigurationBuilder()
		    .configure(params.fileBased().setFile(new File(System.getProperty("user.dir")+"/src/main/resources/conf/config.xml")));
		 config = builder.getConfiguration();
	}

	public static String getTestMode(){
		return TEST_MODE;
	}

	public static void setTestMode(String testMode){
		TEST_MODE=testMode;
	}

	public static String getTestEnvironment(){
		return TEST_ENVIRONMENT;
	}

	public static void setTestEnvironment(String testEnv){
		TEST_ENVIRONMENT=testEnv;
	}

	public static String getReportPath(){
		return TEST_REPORT_PATH;
	}

	public static void setReportPath(String reportPath){
		TEST_REPORT_PATH=reportPath;
	}

}
