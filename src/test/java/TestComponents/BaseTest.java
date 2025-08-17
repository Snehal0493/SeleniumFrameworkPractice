package TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PageObjects.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LoginPage loginPage;

	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/Recourses/GlobalData.properties");
		prop.load(fis);
		String browser = prop.getProperty("Browser");
		if (browser.equalsIgnoreCase("Chrome")) {
			
			ChromeOptions options = new ChromeOptions();
			
			// Use a clean, temporary profile (ChromeDriver default) OR point to an empty dir:
			options.addArguments("user-data-dir=C:/temp/blank-chrome-profile"); // folder must exist and be EMPTY

			// Turn off password manager & autofill via prefs (this is the key)
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("autofill.profile_enabled", false);
			prefs.put("autofill.credit_card_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			// One combined disable-features argument
			options.addArguments(
			    "--incognito",
			    "--no-first-run",
			    "--no-default-browser-check",
			    "--disable-save-password-bubble",
			    "--disable-password-generation",
			    "--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,AutofillServerCommunication"
			);
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
		}
		 else if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "C:/Users/Snehal.Kavitake/OneDrive - Decos/Snehal/edgedriver_win64/msedgedriver.exe");
 
		 driver = new EdgeDriver(); }
		 

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize(); //full screen

		return driver;
	}
	
	public List<HashMap<String, String>> getJsonData(String filePath) throws IOException
	{
		//read json to string
		String JsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
		//String to hashmap 
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<HashMap<String, String>> data = mapper.readValue(
		        JsonContent, new TypeReference<List<HashMap<String, String>>>() {}
		    );
		return data;
		
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File des = new File(System.getProperty("user.dir") + "/Reports/Screenshots" + testCaseName + ".png");
		FileUtils.copyFile(src, des);
		return System.getProperty("user.dir") + "/Reports/Screenshots" + testCaseName + ".png";
	}

	@BeforeMethod(alwaysRun=true)
	public LoginPage launchApplication() throws IOException {
		
		driver = initializeDriver();
		
		loginPage = new LoginPage(driver);
		loginPage.goTo();
		return loginPage;

	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}
	
	 static {
	        URL url = BaseTest.class.getClassLoader().getResource("log4j.properties");
	        System.out.println("Log4j config URL = " + url);
	        if (url != null) {
	            PropertyConfigurator.configure(url);
	        } else {
	            System.err.println("log4j.properties NOT found on classpath");
	        }
	    }

}
