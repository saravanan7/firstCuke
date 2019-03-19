package libraryMethods;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import framework.constants.FrameworkConstants;
import framework.methods.BrowserCluster;
import framework.methods.ScenarioStore;

public class ReportingMethods {

	static ScenarioStore scenario=new ScenarioStore();

	public static ExtentReports createExtentReport(String reportName) {
		ExtentReports report=null;
		try{
			String reportPath=createReportPath(reportName);
			report=new ExtentReports(reportPath);
			report.loadConfig(new File(System.getProperty("user.dir")+FrameworkConstants.config.getString("extentConfigPath")));
		}catch(Exception e){
			System.out.println("Exception: createExtentReport: "+e.toString());
			e.printStackTrace();
		}
		return report;
	}

	public static ExtentTest createExtentTest(String testName, ExtentReports extentReport) {
		ExtentTest extentTest=null;
		try{
			extentTest=extentReport.startTest(testName);
		}catch(Exception e){
			System.out.println("Exception: getReportInstance: ");
			e.printStackTrace();
		}
		return extentTest;
	}

	public static void logReport(String scenarioID,String status,String stepName ) {
		ExtentTest extentTest=null;
		LogStatus stepStatus=null;
		try{
			if("pass".equalsIgnoreCase(status)){
				stepStatus=LogStatus.PASS;
			}else if("fail".equalsIgnoreCase(status)){
				stepStatus=LogStatus.FAIL;
			}else if("UNKNOWN".equalsIgnoreCase(status)){
				stepStatus=LogStatus.UNKNOWN;
			}else if("ERROR".equalsIgnoreCase(status)){
				stepStatus=LogStatus.ERROR;
			}
			extentTest=scenario.getTest(scenarioID);
			String screenShot=extentTest.addScreenCapture(getScreenshot(scenarioID));
			extentTest.log(stepStatus, stepName, screenShot);
		}catch(Exception e){
			System.out.println("Exception: LogReport: ");
			e.printStackTrace();
		}
	}

	public static void completeReporting(String threadID){
		try{
			String scenarioID=scenario.getScenarioID(threadID);
			ExtentReports extentReport=scenario.getReport(scenarioID);
			ExtentTest	extentTest=scenario.getTest(scenarioID);
			extentReport.endTest(extentTest);
			extentReport.flush();
		}catch(Exception e){
			System.out.println("Exception: Completing Report: "+e.toString());
			e.printStackTrace();
		}
	}

	public static void createReportDirectory(){
		try{
			String dateTime = CommonMethods.getReportDate();
			String subPath=FrameworkConstants.getTestMode()+File.separator+FrameworkConstants.getTestEnvironment()+File.separator+dateTime;
			String reportPath=System.getProperty("user.dir")+FrameworkConstants.config.getString("reportPath")+File.separator+subPath.toUpperCase();
			File fPath=new File(reportPath);
			if(!fPath.exists()){
				fPath.mkdirs();
			}
			FrameworkConstants.setReportPath(reportPath);
		}catch(Exception e){
			System.out.println("Exception: Create Report Path: "+e.toString());
			e.printStackTrace();
		}
	}

	public static String createReportPath(String reportingScenario){
		String path=null;
		try{
			String dateTimeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			String reportPath=FrameworkConstants.getReportPath()+File.separator+reportingScenario;
			File fPath=new File(reportPath);
			if(!fPath.exists()){
				fPath.mkdirs();
			}
			path=reportPath+File.separator+reportingScenario+"_"+dateTimeStamp+".html";
		}catch(Exception e){
			System.out.println("Exception: Create Report Path: "+e.toString());
			e.printStackTrace();
		}
		return path;
	}



	public static String createScreenshotPath(String reportingScenario){
		String screenshotPath=null;
		try{
			screenshotPath=FrameworkConstants.getReportPath()+File.separator+reportingScenario+ File.separator+"Screenshots";
			File fPath=new File(screenshotPath);
			if(!fPath.exists()){
				fPath.mkdirs();
			}

		}catch(Exception e){
			System.out.println("Exception: Create Report Path: "+e.toString());
			e.printStackTrace();
		}
		return screenshotPath;
	}

	public static String getScreenshot(String reportingScenario) {
		String destination = null;
		try{
			EventFiringWebDriver browser=BrowserCluster.getNewBrowser(Thread.currentThread().getId());
			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) browser;
			File source = ts.getScreenshotAs(OutputType.FILE);
			destination = createScreenshotPath(reportingScenario)+File.separator+browser.getTitle()+"_"+dateName+".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
		}catch(Exception e){
			System.out.println("Exception: Take screenshot: "+e.toString());
			e.printStackTrace();
		}
		return destination;
	}



}
