package foodcourt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import foodcourt.utility.Utility;

/**
* The Home Page defines the web elements and the 
* methods implemented on the web elements of Home Page
* @author  Rishabh
*/
public class HomePage
{

	WebDriver driver;
	WebDriverWait wait;
	
    By scheduleTab = By.xpath("//li[@class='navbar-tab u-hidden--mobile m-topHeader-roster']//span");

    By dashboardTitle = By.xpath("//div[@id='my-dashboard-summary']//h3");
    
    public HomePage(WebDriver driver)
    {

        this.driver = driver;

    }
    
    public Boolean verifyHomePage(String title)

    {
 	
    	Boolean flag=false;
    	
    	wait = Utility.getWait(driver,20);
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardTitle));
    			
    	if(driver.findElement(dashboardTitle).getText().equalsIgnoreCase(title))
    		{
   	
    			flag= true;
    		}
    	
    	return flag;
    	
    }

    // clicking schedule Tab
    public void ClickScheduleTab() throws InterruptedException
    {
    	wait = Utility.getWait(driver,20);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleTab));
        driver.findElement(scheduleTab).click();
        Thread.sleep(5000);

    }
	
	
}
