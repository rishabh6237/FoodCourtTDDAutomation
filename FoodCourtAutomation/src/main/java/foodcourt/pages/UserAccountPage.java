package foodcourt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import foodcourt.utility.Utility;

/**
* The User Account Page defines the web elements and the 
* methods implemented on the web elements of User Account Page
* @author  Rishabh
*/
public class UserAccountPage 

{

	WebDriver driver;
	WebDriverWait wait;
	
	By email = By.className("email");
	
	public UserAccountPage(WebDriver driver)
    
    {

        this.driver = driver;

    }
	
	public Boolean verifyUserIsCreated(String strEmail)

    {
    	Boolean flag=false;
    	
    	wait = Utility.getWait(driver,30);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(email));
    	
    	if(driver.findElement(email).getText().equalsIgnoreCase(strEmail))
    	{
    	
    		flag= true;
    	}
    	
    	return flag;
    	
    }
	
	
	
}
