package SeleniumLearning.SeleniumFrameworkPractice;

import org.testng.annotations.Test;

import PageObjects.CartPage;
import PageObjects.LoginPage;
import PageObjects.ProductCatalogue;
import TestComponents.BaseTest;

import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.apache.tools.ant.taskdefs.Retry;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.testng.Assert;


public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException, FindFailed {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginApplication("anshika@gmail.com", "Iamki000");
		Assert.assertEquals("Incorrect email or password1.", loginPage.getErrorMessage());

	}
	

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException, FindFailed
	{

		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = loginPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
		productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);

	}

	
	

}
