package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import customAnnotation.RetryCountIfFailed;

public class Retry implements IRetryAnalyzer {
		 
	    private int count = 0;
	    private static int maxTry = 3;
	 /*
	    @Override
	    public boolean retry(ITestResult iTestResult) {
	        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
	            if (count < maxTry) {                            //Check if maxtry count is reached
	                count++;                                     //Increase the maxTry count by 1
	                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed
	                return true;                                 //Tells TestNG to re-run the test
	            } else {
	                iTestResult.setStatus(ITestResult.FAILURE);  //If maxCount reached,test marked as failed
	            }
	        } else {
	            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
	        }
	        return false;
	    }*/
	    
	    @Override
		public boolean retry(ITestResult result) {

			// check if the test method had RetryCountIfFailed annotation
			RetryCountIfFailed annotation = result.getMethod().getConstructorOrMethod().getMethod()
					.getAnnotation(RetryCountIfFailed.class);
			// based on the value of annotation see if test needs to be rerun
			if((annotation != null) && (count < annotation.value()))
			{
				count++;
				return true;
			}
			return false;
		}
	 
	}
