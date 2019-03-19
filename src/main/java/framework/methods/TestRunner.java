package framework.methods;

import java.util.Arrays;
import java.util.List;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import framework.constants.FrameworkConstants;
import libraryMethods.ReportingMethods;
import cucumber.runtime.Runtime;

public class TestRunner extends FrameworkMethods{

	public TestRunner(){
		
	}

	public static void main(String args[]){
		try{
			initThread(args);
		}catch(Exception e){
			System.out.println("Exception occurred--> "+e.toString());
		}
	}

	public static void initThread(String args[]){
		try{
			//TestRunner SIT smoke @SmokeTest
			if(args.length==4){
				FrameworkConstants.getConfig();
				FrameworkConstants.setTestEnvironment(args[1]);
				FrameworkConstants.setTestMode((args[2]));
				ReportingMethods.createReportDirectory();
				List<String> scenariosList = Arrays.asList(args[3].split(","));
				int chunkSize=FrameworkMethods.getChunkConfiguration().get("threadPoolSize");
				int instanceSize=FrameworkMethods.getChunkConfiguration().get("scenariosChunkSize");
				if((chunkSize*instanceSize)<scenariosList.size()){
					instanceSize=scenariosList.size();
					chunkSize=1;
				}
				List<String>[] scenarioChunks = FrameworkMethods.splitTheList(scenariosList, instanceSize);
				ExecutorServiceHelper.chromeBrowserExecutorService(chunkSize, scenarioChunks,scenariosList);
			}else{
				throw new Exception("Arguments are not given properly");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
