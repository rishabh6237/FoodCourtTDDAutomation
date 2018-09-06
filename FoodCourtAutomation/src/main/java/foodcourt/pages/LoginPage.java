package foodcourt.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import foodcourt.utility.Utility;

/**
* The Login Page defines the web elements and the 
* methods implemented on the web elements of Login Page
* @author  Rishabh
*/
public class LoginPage

{
	WebDriver driver;
	WebDriverWait wait;
	
    By loginName = By.id("login");

    By password = By.id("password");

    By loginButton = By.name("btnLogin");
    
    By foodCourtTitle = By.className("company");
    
    public LoginPage(WebDriver driver)
    {

        this.driver = driver;

    }
    

//entering loginName
    public void setLoginName(String strLoginName){

    	driver.findElement(loginName).clear();
    	driver.findElement(loginName).sendKeys(strLoginName);

    }

    
//entering password
    public void setPassword(String strPassword){

    	driver.findElement(password).clear();

    	driver.findElement(password).sendKeys(strPassword);

    }

    

 // clicking login
public void clickLogin()
{

    driver.findElement(loginButton).click();

}
	
public Boolean verifyLoginFoodCourtPage(String title)

{
	Boolean flag=false;
	
	wait = Utility.getWait(driver,20);
	wait.until(ExpectedConditions.visibilityOfElementLocated(foodCourtTitle));
			
	if(driver.findElement(foodCourtTitle).getText().equalsIgnoreCase(title))
	{
	
		flag= true;
	}
	
	return flag;
	
}

public void loginFoodCourt(String strLoginName,String strPassword)

{
	
	this.setLoginName(strLoginName);
	this.setPassword(strPassword);
	this.clickLogin();
}



}


