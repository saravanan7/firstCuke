package framework.methods;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import framework.constants.FrameworkConstants;

public class ExecutorServiceHelper {

	static ExecutorService chromeBrowserExecService=null;

	public static void chromeBrowserExecutorService(int fixedThreadPoolSize,List<String>[] scenarioChunkList,List<String> scenarioList){
		try{
			if(chromeBrowserExecService==null){
				chromeBrowserExecService=Executors.newFixedThreadPool(fixedThreadPoolSize);
			}

			if(scenarioList.size()>1){
				for (int i = 0; i < scenarioChunkList.length; i++) {
					chromeBrowserExecService.execute(new RunnableThread(scenarioChunkList[i], FrameworkConstants.config, BrowserCluster.CHROME));
				}}else{
					chromeBrowserExecService.execute(new RunnableThread(scenarioList, FrameworkConstants.config, BrowserCluster.CHROME));
				}

			try {
				chromeBrowserExecService.shutdown();
			} catch (Exception ie) {
				chromeBrowserExecService.shutdownNow();
			}

		}catch(Exception e){
			System.out.println("chromeBrowserExecutorService--> "+e.toString());
		}
	}

}
