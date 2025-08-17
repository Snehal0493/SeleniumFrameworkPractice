package SeleniumLearning.SeleniumFrameworkPractice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.CartPage;
import PageObjects.CheckoutPage;
import PageObjects.ConfirmationPage;
import PageObjects.LoginPage;
import PageObjects.OrderPage;
import PageObjects.ProductCatalogue;
import TestComponents.BaseTest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.sikuli.script.FindFailed;
import org.testng.Assert;


public class SubmitOrderTest extends BaseTest{
	Logger log = Logger.getLogger(SubmitOrderTest.class);
	

	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException, FindFailed
	{

		LoginPage loginPage = new LoginPage(driver);
		log.info("Starting the login process with email: " + input.get("email"));
		ProductCatalogue productCatalogue = loginPage.loginApplication(input.get("email"), input.get("password"));
		
		log.info("Login successful, navigating to product catalogue.");
		productCatalogue.getProductList();
		
		log.info("Searching for product: " + input.get("product"));
		productCatalogue.addProductToCart(input.get("product"));
		
		log.info("Product added to cart, proceeding to cart page.");
		CartPage cartPage = productCatalogue.goToCartPage();

		log.info("Verifying product display in cart for product: " + input.get("product"));
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		
		log.info("Product verification successful, proceeding to checkout.");
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		log.info("Entering user details for checkout.");
		checkoutPage.selectCountry("india");
		
		log.info("Submitting order.");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		log.info("Order submitted, verifying confirmation message.");
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		log.info("Order confirmation successful.");
		

	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest() throws FindFailed
	{
		String productName = "ZARA COAT 3";
		
		log.info("Starting order history test for product: " + productName);
		ProductCatalogue productCatalogue = loginPage.loginApplication("srocks0304@gmail.com", "Iamking@000");
		log.info("Navigating to product catalogue.");
		
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		log.info("Navigating to orders page.");
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
		log.info("Order history test completed successfully for product: " + productName);
		
}
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{

		
		List<HashMap<String,String>> data = getJsonData(System.getProperty("user.dir")+"/src/test/java/TestData/login.json");
		return new Object[][]  {{data.get(0)} , {data.get(1) } };
		
	}
	  
	
	
	
	
	
	
	
	


}
