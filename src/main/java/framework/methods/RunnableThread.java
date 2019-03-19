package framework.methods;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration2.CombinedConfiguration;

import com.relevantcodes.extentreports.ExtentReports;

//import org.apache.maven.plugin.AbstractMojo;
//import org.apache.maven.plugin.MojoExecutionException;
//import org.apache.maven.plugin.MojofailureException;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import framework.constants.FrameworkConstants;
import libraryMethods.ReportingMethods;

public class RunnableThread implements Runnable{

	CombinedConfiguration config = null;
	String execBrowserName = null;
	List<String> execScenarioList = null;

	public RunnableThread(List<String> scenarioList, CombinedConfiguration config, String browserName){
		this.execBrowserName=browserName;
		this.config=config;
		this.execScenarioList=scenarioList;
	}

	public void run(){
		RuntimeOptions runtimeOptions=null;
		Runtime runtime=null;
		ScenarioStore scenario=new ScenarioStore();
		try{

			for(int scenarioInc=0;scenarioInc<execScenarioList.size();scenarioInc++){
				String currentExecutionScenario=execScenarioList.get(scenarioInc);
				
				scenario.setScenarioID(Long.toString(Thread.currentThread().getId()),currentExecutionScenario.replace("@", ""));
				ExtentReports er=ReportingMethods.createExtentReport(currentExecutionScenario.replace("@", ""));
				System.out.println("SScenarioID: "+scenario.getScenarioID(Long.toString(Thread.currentThread().getId())));
				scenario.setReport(scenario.getScenarioID(Long.toString(Thread.currentThread().getId())),er);

				List<String> runCommands=FrameworkMethods.buildGlueCommand(FrameworkConstants.getTestMode(),currentExecutionScenario);
				BrowserCluster.setBrowserType(Thread.currentThread().getId(), execBrowserName);

				System.out.println("Cuke cmd to be executed ->"+Arrays.toString(runCommands.toArray()));
				runtimeOptions = new RuntimeOptions(runCommands);

				System.out.println("Current Thread ID: "+Thread.currentThread().getId());

				//run
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				ResourceLoader resourceLoader = new MultiLoader(classLoader);
				ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
				runtime = new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
				runtime.getGlue().addBeforeHook(new BeforeExecution());
				runtime.run();
				runtime.exitStatus();
				ReportingMethods.completeReporting(Long.toString(Thread.currentThread().getId()));
			}
		}catch(Exception e){
			System.out.println("RunnableThread-->"+e.toString());
		}finally{

			System.err.println("***************************** Shutting down the browser: "+Thread.currentThread().getId());
			BrowserCluster.exitBrowser(Thread.currentThread().getId(), execBrowserName);
		}
	}

}
