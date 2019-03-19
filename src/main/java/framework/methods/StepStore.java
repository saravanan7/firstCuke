package framework.methods;

import java.util.HashMap;
import java.util.Map;

public class StepStore {

	public static StepStore stepStore=new StepStore();
	public static String currentStepDesc = null;
	public static Map<String, String> currentStepMsgMap = new HashMap<String,String>();//StepID,MsgMap
	public static String currentStepStatus = null;

	public StepStore getInstance() {
		return stepStore;
	}
	public synchronized void addMessageMap(Map<String,String> messageMap) {
		currentStepMsgMap=messageMap;
	}
	
	public synchronized Map<String, String> getMessageMap() {
		return currentStepMsgMap;
	}

	public synchronized String getStepDesc() {
		return currentStepDesc;
	}

	public synchronized void setStepDesc(String stepDesc) {
		currentStepDesc=stepDesc;
	}



	public synchronized void setStepStatus(String stepStatus) {
		currentStepStatus=stepStatus;

	}
	
	public synchronized String getStepStatus() {
		return currentStepStatus;
	}

//	public synchronized String getStepStatus(String StepID) {
//		return currentStepStatus.get(StepID);
//
//	}
//
//	public void removeStepStatus(String StepID) {
//		currentStepStatus.remove(StepID);
//	}


}
