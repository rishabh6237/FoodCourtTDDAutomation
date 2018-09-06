package foodcourt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import foodcourt.utility.Utility;

/**
* The Schedule Tab Page defines the web elements and the 
* methods implemented on the web elements of Schedule Tab Page
* @author  Rishabh
*/
public class ScheduleTabPage 
{

	WebDriver driver;
	WebDriverWait wait;
	
	By AddNewEmployeeTab = By.xpath("//*[@class='add-emp-row js-add-emp-row']");
    
    
    By firstName = By.id("txtTeamProfileEditFirstName");

    By lastName = By.id("txtTeamProfileEditLastName");
    
    By email = By.id("txtTeamProfileEditEmail");

    By mobile = By.id("txtTeamProfileEditMobile");

    By saveButton = By.id("btnTeamProfileSave");

    By profileName = By.xpath("//*[@class='lblProfileName margin-compact']");    
    
//    By picEnlarge = By.xpath("//*[@class='m-sideReveal-userPhoto pm-profile-img-wrapper js-pmPhotoTab']");
//   
//    By profilePicture=By.xpath("//*[@class='u-textCenter pnlTeamPhotoWrapper']");
    
    
    
 public ScheduleTabPage(WebDriver driver)
    
    {
    
    	this.driver = driver;

    }
	
    
    
  //entering loginName
    public void setFirstName(String strLoginName){

    	driver.findElement(firstName).clear();
    	driver.findElement(firstName).sendKeys(strLoginName);

    }
    
  //entering lastName
    public void setLastName(String strLastName){

    	driver.findElement(lastName).clear();
    	driver.findElement(lastName).sendKeys(strLastName);

    }
    
  //entering email
    public void setEmail(String strEmail){

    	driver.findElement(email).clear();
    	driver.findElement(email).sendKeys(strEmail);

    }
    
  //entering mobile
    public void setMobile(String strMobile)
    {

    	driver.findElement(mobile).clear();
    	driver.findElement(mobile).sendKeys(strMobile);

    }
    
    // click 
    public void clickLogin()
    {

        driver.findElement(saveButton).click();

    }
  
//    // click 
//    public void uploadPicture()
//    {
//
//        driver.findElement(picEnlarge).click();
//    	wait = Utility.getWait(driver,20);
//    	wait.until(ExpectedConditions.visibilityOfElementLocated(profilePicture));
//    
//    	try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    	driver.findElement(profilePicture).sendKeys("C:\\Users\\ashis\\OneDrive\\Pictures\\Screenshots\\pic.png");
//
//    }
    
    
    
    // click add new employee link
    public void AddNewEmployeeLink()
    {

        driver.findElement(AddNewEmployeeTab).click();

    }
    
    public void AddNewEmployee(String strFirstName,String strLastName,String strEmail,String strMobile)

    {
    	driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='app-iframe dg-content-box margin-none ready']")));
    	  
    	this.AddNewEmployeeLink();
    	driver.switchTo().defaultContent();
    	  
    	wait = Utility.getWait(driver,20);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
    	
    	
    	this.setFirstName(strFirstName);
    	this.setLastName(strLastName);
    	this.setEmail(strEmail);
    	this.setMobile(strMobile);
    //	this.uploadPicture();
    	
    	this.clickLogin();
    	
    	
    }
    
   
    //Verifying user has arrived on schedules tab page
    public Boolean VerifyScheduleTabPage(String title)

    {
    	//switching to frame that contains the add new employee link
    	driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='app-iframe dg-content-box margin-none ready']")));
    	
    	Boolean flag=false;
    	wait = Utility.getWait(driver,20);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AddNewEmployeeTab));
    		
    	
    	if(driver.findElement(AddNewEmployeeTab).getText().equalsIgnoreCase(title))
    	{
   	
    			flag= true;
    	}
    	
    	//switching back to main frame
    	driver.switchTo().defaultContent();
    	return flag;
    	
    }
    
    //Verifying user has arrived on schedules tab page
    public Boolean VerifyUserAdded(String strProfileName)

    {
    	Boolean flag=false;
    	
    
    	
    	wait = Utility.getWait(driver,30);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(profileName));
    	
    	if(driver.findElement(profileName).getText().equalsIgnoreCase(strProfileName))
    	{
   	
    			flag= true;
    	}
    	
    	return flag;
    	
    }
   
    
    
    
    
    
}
