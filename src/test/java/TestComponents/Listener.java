package TestComponents;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Recourses.ExtentReportNG;

public class Listener extends BaseTest implements ITestListener{

	ExtentTest test;
	private static final ExtentReports extent = ExtentReportNG.getReportObject();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); //Thread safe

    private static Appender EXTENT_BRIDGE; // hold one instance

    @Override
    public void onStart(ITestContext context) {
        // Ensure Extent is initialized
        ExtentReportNG.getReportObject();

        // Attach our Log4j -> Extent appender once
        Logger root = Logger.getRootLogger();
        if (EXTENT_BRIDGE == null) {
            EXTENT_BRIDGE = new ExtentLog4jAppender();
            EXTENT_BRIDGE.setName("EXTENT");
        }
        if (root.getAppender("EXTENT") == null) {
            root.addAppender(EXTENT_BRIDGE);
        }
    }
    
    public static ExtentTest getCurrentTest() {
        return extentTest.get();
    }
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);//unique thread id(ErrorValidationTest)->test
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().log(Status.PASS, "Test Passed");
		extentTest.remove();
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().fail(result.getThrowable());//
		
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String filePath = null;
		try {
			
			filePath = getScreenShot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		extentTest.remove();
	
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
		
		 Logger root = Logger.getRootLogger();
	        if (root.getAppender("EXTENT") != null) {
	            root.removeAppender("EXTENT");
	        }
		
	}
	

}
