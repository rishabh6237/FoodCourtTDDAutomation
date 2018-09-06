package foodcourt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import foodcourt.utility.Utility;

public class AcceptInvitePage 

{

	WebDriver driver;
	WebDriverWait wait;
	
	By headText = By.className("text-center");

	By firstName=By.id("signup-firstname");
			
    By password = By.id("signup-password");

    By signUp = By.id("btnSignup");
    
    
    public AcceptInvitePage(WebDriver driver)
    
    {

        this.driver = driver;

    }
    
  //password
    public void setPassword(String strPassword){

    	wait = Utility.getWait(driver,20);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(password));
    	
    	/*Sleep has been put because for some reason the accept invite page is 
    	fluctuating which is causing test case failure*/
    	try
    	{
			Thread.sleep(5000);
			driver.findElement(password).sendKeys(strPassword);
		}
    	catch (InterruptedException e)
    	{
			e.printStackTrace();
		}
    	

    }
    
    
    // clicking login
    public void clickCreateAccount()
    {
    	wait = Utility.getWait(driver,20);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(signUp));
        driver.findElement(signUp).click();

    }
    
    public Boolean verifyUserAcceptInvitePage(String head,String name)

    {
    	Boolean flag=false;
    	
    	wait = Utility.getWait(driver,20);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(headText));
    	wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
    	
    	
    	if(driver.findElement(headText).getText().equalsIgnoreCase(head) && driver.findElement(firstName).getAttribute("value").equalsIgnoreCase(name))
    	{
    	
    		flag= true;
    	}
    	
    	return flag;
    	
    }
    
    public void createUserAccount(String strPassword)

    {
    	
    	
    	this.setPassword(strPassword);
    	
    	this.clickCreateAccount();
    	
    	try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
