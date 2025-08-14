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
import org.sikuli.script.FindFailed;
import org.testng.Assert;


public class SubmitOrderTest extends BaseTest{
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException, FindFailed
	{

		LoginPage loginPage = new LoginPage(driver);
		ProductCatalogue productCatalogue = loginPage.loginApplication(input.get("email"), input.get("password"));
		productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		

	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest() throws FindFailed
	{
		//"ZARA COAT 3";
		ProductCatalogue productCatalogue = loginPage.loginApplication("anshika@gmail.com", "Iamking@000");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
		
}
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{

		
		List<HashMap<String,String>> data = getJsonData(System.getProperty("user.dir")+"/src/test/java/TestData/login.json");
		return new Object[][]  {{data.get(0)} , {data.get(1) } };
		
	}
	  
	
	
	
	
	
	
	
	


}
