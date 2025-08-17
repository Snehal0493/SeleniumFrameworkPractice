package SeleniumLearning.SeleniumFrameworkPractice;

import org.testng.annotations.Test;

import PageObjects.CartPage;
import PageObjects.LoginPage;
import PageObjects.ProductCatalogue;
import TestComponents.BaseTest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Retry;
import org.sikuli.script.FindFailed;
import org.testng.Assert;


public class ErrorValidationsTest extends BaseTest {

	
	Logger log = Logger.getLogger(ErrorValidationsTest.class);
	
	
	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException, FindFailed {

		LoginPage loginPage = new LoginPage(driver);
		
		log.info("Attempting to login with invalid credentials.");
		loginPage.loginApplication("anshika@gmail.com", "Iamki000");
		
		log.info("Checking for error message after failed login attempt.");
		Assert.assertEquals("Incorrect email or password1.", loginPage.getErrorMessage());
		log.info("Error message validation successful.");

	}
	

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException, FindFailed
	{

		String productName = "ZARA COAT 3";
		
		log.info("Starting product error validation for product: " + productName);
		ProductCatalogue productCatalogue = loginPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
		
		log.info("Fetching product list.");
		productCatalogue.getProductList();
		
		log.info("Attempting to add product to cart: " + productName);
		productCatalogue.addProductToCart(productName);
		
		log.info("Navigating to cart page.");
		CartPage cartPage = productCatalogue.goToCartPage();
		
		log.info("Verifying product display in cart for product: " + productName);
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		log.info("Product display verification result: " + match);
		Assert.assertFalse(match);

	}

	
	

}
