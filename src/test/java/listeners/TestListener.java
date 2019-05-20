package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.sun.xml.bind.Util;

import test.RestTest2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import utilities.ExtentManager;

//**********************************************************************************************************
//Author: Onur Baskirt
//Description: This is the main listener class.
//**********************************************************************************************************
public class TestListener extends RestTest2 implements ITestListener, ISuiteListener, IInvokedMethodListener {

	// Extent Report Declarations
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Extent Reports Version 3 Test Suite started!");
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Extent Reports Version 3  Test Suite is ending!"));
		extent.flush();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(extentTest);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed");
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		test.get().fail(result.getThrowable());
		
		test.get().info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		test.get().info((result.getMethod().getMethodName() + " failed!"));
		
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		
		String targetLocation = null;

		String testClassName = result.getInstanceName().trim();
		Date now = new java.util.Date();
		Timestamp current = new java.sql.Timestamp(now.getTime());
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(current);
		//String timeStamp = Util.getCurrentTimeStamp(); // get timestamp
		String testMethodName = result.getName().toString().trim();
		String screenShotName = testMethodName + timeStamp + ".png";
		//String fileSeperator = System.getProperty("file.separator");
		String reportsPath = System.getProperty("user.dir") + "\\" + "TestReport" + "\\"
				+ "screenshots";
		
		test.get().info("Screen shots reports path - " + reportsPath);
	

		try {
			File file = new File(reportsPath + "\\" + testClassName); // Set
																				// screenshots
																				// folder
			if (!file.exists()) {
				if (file.mkdirs()) {
					test.get().info("Directory: " + file.getAbsolutePath() + " is created!");
				} else {
					test.get().info("Failed to create directory: " + file.getAbsolutePath());
				}

			}

			targetLocation = reportsPath + "\\" + testClassName + "\\" + screenShotName;
			
			
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// define
				
			// location
						File targetFile = new File(targetLocation);
						test.get().info("Screen shot file location - " + screenshotFile.getAbsolutePath());
						test.get().info("Target File location - " + targetFile.getAbsolutePath());
						FileHandler.copy(screenshotFile, targetFile);

					} catch (FileNotFoundException e) {
						test.get().info("File not found exception occurred while taking screenshot " + e.getMessage());
					} catch (Exception e) {
						test.get().info("An exception occurred while taking screenshot " + e.getCause());
					}

					// attach screenshots to report
					try {
						test.get().fail("Screenshot",
								MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
					} catch (IOException e) {
						test.get().info("An exception occured while taking screenshot " + e.getCause());
					}
					test.get().log(Status.FAIL, "Test Failed");
				}
		


	
	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	// This belongs to IInvokedMethodListener and will execute before every method
	// including @Before @After @Test

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {

		String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());

		Reporter.log(textMsg, true);

	}

	// This will return method names to the calling function
	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();
	}

	// This belongs to IInvokedMethodListener and will execute after every method
	// including @Before @After @Test

	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {

		String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod());

		Reporter.log(textMsg, true);

	}

	// This belongs to ISuiteListener and will execute before the Suite start
	@Override
	public void onStart(ISuite arg0) {
		Reporter.log("About to begin executing Suite " + arg0.getName(), true);

	}

	// This belongs to ISuiteListener and will execute, once the Suite is finished
	@Override
	public void onFinish(ISuite arg0) {
		Reporter.log("About to end executing Suite " + arg0.getName(), true);

	}
}
