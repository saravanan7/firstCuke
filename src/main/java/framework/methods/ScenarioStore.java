package framework.methods;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ScenarioStore {

	private static ScenarioStore scenario = new ScenarioStore();
	private static Map<String, ExtentReports> currentExtentReport = new HashMap<String, ExtentReports>();//ScenarioID,ReportID
	private static Map<String, ExtentTest> currentExtentTest = new HashMap<String, ExtentTest>();//ScenarioID,TestID
	private static Map<String, String> currentScenarioID = new HashMap<String, String>();//ThreadID,ScenarioID
	private static Map<String, Integer> currentScenarioOutlineCount = new HashMap<String, Integer>();//ScenarioID,ExampleRowCount
	private static Map<String, String> sharedStepProperties = null;

	public ScenarioStore(){

	}

	public ScenarioStore getInstance(){
		return scenario;
	}

	//SCENARIO ID

	public synchronized String getScenarioID(String threadID) {
		return currentScenarioID.get(threadID);
	}

	public synchronized void setScenarioID(String ThreadID,String scenarioID) {
		currentScenarioID.put(ThreadID, scenarioID);
	}

	//EXTENT-REPORT

	public synchronized ExtentReports getReport(String scenarioID) {
		return currentExtentReport.get(scenarioID);
	}

	public synchronized void setReport(String ScenarioID,ExtentReports reportID) {
		currentExtentReport.put(ScenarioID, reportID);
	}

	//EXTENT-TEST

	public synchronized ExtentTest getTest(String scenarioID) {
		return currentExtentTest.get(scenarioID);
	}

	public synchronized void setTest(String ScenarioID,ExtentTest testID) {
		currentExtentTest.put(ScenarioID, testID);
	}


	//SCENARIO ID

	public synchronized Integer getSORowCount(String scenarioID) {
		return currentScenarioOutlineCount.get(scenarioID);
	}

	public synchronized void setSORowCount(String scenarioID,Integer rowCount) {
		currentScenarioOutlineCount.put(scenarioID, rowCount);
	}

	//SHARED-PROPERTIES
	public synchronized Map<String, String> getSharedStepProperties() {
		return sharedStepProperties;
	}

	public synchronized void setSharedStepProperties(Map<String, String> sharedStepProperties) {
		this.sharedStepProperties = sharedStepProperties;
	}

}
