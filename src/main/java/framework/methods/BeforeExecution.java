package framework.methods;

import java.util.Collection;
import java.util.Iterator;

import com.relevantcodes.extentreports.ExtentReports;

import cucumber.api.Scenario;
import cucumber.runtime.HookDefinition;
import gherkin.formatter.model.Tag;
import libraryMethods.ReportingMethods;

public class BeforeExecution implements HookDefinition{
	ScenarioStore scenario=new ScenarioStore();

//	@Override
	public void execute(Scenario arg0) throws Throwable {
		System.out.println("scenario arg0: "+arg0.getName());

	}

//	@Override
	public String getLocation(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
	public boolean isScenarioScoped() {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
	public boolean matches(Collection<Tag> arg0) {
		Iterator<Tag> ite = arg0.iterator();
		while (ite.hasNext()) {
			String scenarioID = ite.next().getName();

			if(scenarioID.startsWith("@S-") || scenarioID.startsWith("@s-")){
				String tmpScenario=scenarioID.replace("@", "");
				String SSID=scenario.getScenarioID(Long.toString(Thread.currentThread().getId()));
				System.out.println("SSID=: "+SSID);
				ExtentReports tmpER= scenario.getReport(SSID);
				scenario.setTest(SSID, ReportingMethods.createExtentTest(tmpScenario, tmpER));
			}

			if(scenarioID.startsWith("@SO-") || scenarioID.startsWith("@so-")){
				String tmpScenario=scenarioID.replace("@", "");
				String SSID=scenario.getScenarioID(Long.toString(Thread.currentThread().getId()));
				System.out.println("SSID=: "+SSID);
				
				if(scenario.getSORowCount(SSID)==null||scenario.getSORowCount(SSID).equals(null)){
					scenario.setSORowCount(SSID,1);
				}else{
					scenario.setSORowCount(SSID,(scenario.getSORowCount(SSID))+1);
				}
				
				ExtentReports tmpER= scenario.getReport(SSID);
				scenario.setTest(SSID, ReportingMethods.createExtentTest(tmpScenario+"_DataRow-"+scenario.getSORowCount(SSID), tmpER));
			}
		}
		return false;
	}

}
