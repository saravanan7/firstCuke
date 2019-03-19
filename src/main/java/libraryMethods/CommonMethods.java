package libraryMethods;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import framework.methods.StepStore;

public class CommonMethods {

	

	public static String getReportDate() {
		String reqDate = null;
		try {
			Calendar cal = Calendar.getInstance();
			String date=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			if(date.length()==1){
				date="0"+date;
			}
			String year=Integer.toString(cal.get(Calendar.YEAR));
			reqDate =getMonthForInt(cal.get(Calendar.MONTH)) + "-" + date+ "-" +year;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return reqDate;
	}
	
	static String getMonthForInt(int num) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11 ) {
			month = months[num];
		}
		return month;
	}

	public static boolean getUrl(String URL, EventFiringWebDriver browser){
		browser.get(URL);
		return true;
	}

	public static void listAllLinks(EventFiringWebDriver browser,String stepId,StepStore step){
		String status="fail";
		Map<String,String> messageMap=null;
		try{

			if (messageMap == null) {
				messageMap = new HashMap<String, String>();
				step.addMessageMap(messageMap);
			}
			System.out.println("getting elements for "+browser.getCurrentUrl());
			List<WebElement> list=browser.findElements(By.tagName("a"));
			for(int inc=0;inc<list.size();inc++){
				if(!(list.get(inc).getText().equals(""))){
//					System.out.println("Link name is : "+list.get(inc).getText());
				}
			}
			if(list.size()!=0){
				status="pass";
			}
		}catch(Exception e){
			messageMap.put("listAllLinks: Exception occurred:", e.toString());
			step.setStepStatus("error");
		}finally{
			step.setStepStatus(status);
		}
	}

}
