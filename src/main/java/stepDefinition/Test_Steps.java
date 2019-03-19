package stepDefinition;


import org.openqa.selenium.support.events.EventFiringWebDriver;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import framework.methods.BrowserCluster;
import framework.methods.FrameworkMethods;
import framework.methods.ScenarioStore;
import framework.methods.StepStore;
import libraryMethods.CommonMethods;
import libraryMethods.InputdataManager;
import libraryMethods.ReportingMethods;


public class Test_Steps extends FrameworkMethods{

	EventFiringWebDriver browser=BrowserCluster.getNewBrowser(Thread.currentThread().getId());
	public String currentThreadID=Long.toString(Thread.currentThread().getId());



	@Then("^User navigates to \"([^\"]*)\" site$")
	public void User_navigates_to_different_sites(String input1) throws Throwable {
		StepStore step=new StepStore();
		ScenarioStore scenario=new ScenarioStore();
		String scenarioID=null;
		step.setStepStatus("UNKNOWN");
		String stepId=null;
		try{
			String tmpTID=Long.toString(Thread.currentThread().getId());
			scenarioID=scenario.getScenarioID(tmpTID);
			String[] inputs={input1};
			stepId=getCurrentMethodInfo(inputs);
			System.out.println("Step ID: "+stepId);

			step.setStepDesc(stepId);
			if(CommonMethods.getUrl(input1, browser)){
				step.setStepStatus("pass");
			}else{
				step.setStepStatus("fail");
			}
		}catch(Exception e){
			step.setStepStatus("fail");
		}finally{
			ReportingMethods.logReport(scenarioID,step.getStepStatus(), stepId);
		}
	}

	@When("^User is on Google Home Page$")
	public void User_is_on_Google_Home_Page() throws Throwable {
		StepStore step=new StepStore();
		ScenarioStore scenario=new ScenarioStore();
		String scenarioID=null;
		step.setStepStatus("UNKNOWN");
		String stepId="User is on Google Home Page";
		try{
			String tmpTID=Long.toString(Thread.currentThread().getId());
			scenarioID=scenario.getScenarioID(tmpTID);
			step.setStepDesc(stepId);
			if(CommonMethods.getUrl("http://www.google.com", browser)){
				step.setStepStatus("pass");
			}else{
				step.setStepStatus("fail");
			}
		}catch(Exception e){
			step.setStepStatus("fail");
		}finally{
			ReportingMethods.logReport(scenarioID, step.getStepStatus(), stepId);
		}
	}

	@When("^User is on Walgreens Home Page$")
	public void User_is_on_Walgreens_Home_Page() throws Throwable {
		StepStore step=new StepStore();
		ScenarioStore scenario=new ScenarioStore();
		String scenarioID=null;
		step.setStepStatus("UNKNOWN");
		String stepId="User is on Walgreens Home Page";
		try{
			String tmpTID=Long.toString(Thread.currentThread().getId());
			scenarioID=scenario.getScenarioID(tmpTID);
			step.setStepDesc(stepId);
			if(CommonMethods.getUrl("http://www.walgreens.com", browser)){
				step.setStepStatus("pass");
			}else{
				step.setStepStatus("fail");
			}
		}catch(Exception e){
			step.setStepStatus("fail");
		}finally{
			ReportingMethods.logReport(scenarioID, step.getStepStatus(), stepId);
		}
	}

	@When("^User is on Facebook Home Page$")
	public void User_is_on_Facebook_Home_Page() throws Throwable {
		StepStore step=new StepStore();
		ScenarioStore scenario=new ScenarioStore();
		String scenarioID=null;
		step.setStepStatus("UNKNOWN");
		String stepId="User is on Facebook Home Page";
		try{
			String tmpTID=Long.toString(Thread.currentThread().getId());
			scenarioID=scenario.getScenarioID(tmpTID);
			step.setStepDesc(stepId);
			if(CommonMethods.getUrl("http://www.facebook.com", browser)){
				step.setStepStatus("pass");
			}else{
				step.setStepStatus("fail");
			}
		}catch(Exception e){
			step.setStepStatus("fail");
		}finally{
			ReportingMethods.logReport(scenarioID, step.getStepStatus(), stepId);
		}
	}

	@When("^User is on Toolsqa Home Page$")
	public void User_is_on_Toolsqa_Home_Page() throws Throwable {
		StepStore step=new StepStore();
		ScenarioStore scenario=new ScenarioStore();
		String scenarioID=null;
		step.setStepStatus("UNKNOWN");
		String stepId="User is on Toolsqa Home Page";
		try{
			String tmpTID=Long.toString(Thread.currentThread().getId());
			scenarioID=scenario.getScenarioID(tmpTID);
			step.setStepDesc(stepId);
			if(CommonMethods.getUrl("http://www.toolsqa.com", browser)){
				step.setStepStatus("pass");
			}else{
				step.setStepStatus("fail");
			}
		}catch(Exception e){
			step.setStepStatus("fail");
		}finally{
			ReportingMethods.logReport(scenarioID, step.getStepStatus(), stepId);
		}
		
	}

	@When("^User is on Maven Home Page$")//http://maven.apache.org
	public void User_is_on_Maven_Home_Page() throws Throwable {
		StepStore step=new StepStore();
		ScenarioStore scenario=new ScenarioStore();
		String scenarioID=null;
		step.setStepStatus("UNKNOWN");
		String stepId="User is on Google maven Page";
		try{
			String tmpTID=Long.toString(Thread.currentThread().getId());
			scenarioID=scenario.getScenarioID(tmpTID);
			step.setStepDesc(stepId);
			if(CommonMethods.getUrl("http://maven.apache.org", browser)){
				step.setStepStatus("pass");
			}else{
				step.setStepStatus("fail");
			}
		}catch(Exception e){
			step.setStepStatus("fail");
		}finally{
			ReportingMethods.logReport(scenarioID, step.getStepStatus(), stepId);
		}
	}

	@And("^System fetches all the links from the page$")
	public void System_fetches_links() throws Throwable {
		StepStore step=new StepStore();
		ScenarioStore scenario=new ScenarioStore();
		String scenarioID=null;
		step.setStepStatus("UNKNOWN");
		String stepId="System fetches all the links from the page";
		try{
			String tmpTID=Long.toString(Thread.currentThread().getId());
			scenarioID=scenario.getScenarioID(tmpTID);
			step.setStepDesc(stepId);
			CommonMethods.listAllLinks(browser,stepId,step);

		}catch(Exception e){
			step.setStepStatus("fail");
		}finally{
			ReportingMethods.logReport(scenarioID, step.getStepStatus(), stepId);
		}

	}

}
