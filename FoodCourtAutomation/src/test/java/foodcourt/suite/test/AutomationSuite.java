package foodcourt.suite.test;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.*;
import foodcourt.pages.*;
import foodcourt.utility.Utility;


/**
* The Automation suite runs the TDD automation framework that 
* will automate the test cases written in feature related to Food Court Website
* @author  Rishabh
*/
public class AutomationSuite
{

	WebDriver driver;

	LoginPage objLogin;
	HomePage objHome;
	ScheduleTabPage objSchedule;
	Utility objUtility;
	AcceptInvitePage objAcceptInvite;
	UserAccountPage objUserAccount;
	
	@BeforeTest
	public void BeforeTest()
	
	{
		objUtility = new Utility(driver);
		
		driver = objUtility.getChromeDriver();
	
		objUtility.setPropertyFiles();
	}
	
	
	
	
	/**
     * This test case will let admin login into Food Court Website.
     * The admin will add a new user to be registered as an employee
     * The invite sent to the user will be fetched and then user will 
     * register himself/herself as an employee.
     */
	@Test
	public void UserCreation() throws Exception
	
	{
	
		try
		{
		
		//Hitting URL	
		String mainURL = objUtility.getPropTestcase().getProperty("mainURL");
		driver.get(mainURL);
		
		//Verifying Login Page
	    objLogin = new LoginPage(driver);
		Assert.assertTrue(objLogin.verifyLoginFoodCourtPage("Food Court"),"The Login page could not be verified");
	    
		//Admin Logging in
	    String adminLogin = objUtility.getPropTestcase().getProperty("adminLogin");
	    String adminPassword = objUtility.getPropTestcase().getProperty("adminPassword");
	    objLogin.loginFoodCourt(adminLogin,adminPassword);
	     
	    // Home Page is verified and then admin goes to schedule tab
	    objHome = new HomePage(driver);
	    Assert.assertTrue(objHome.verifyHomePage("Dashboard"),"The Home page could not be verified");
	    objHome.ClickScheduleTab();
	    
	    //Schedule Tab Page is verified
	    objSchedule = new ScheduleTabPage(driver);
	    Assert.assertTrue(objSchedule.VerifyScheduleTabPage("Add New Employee"),"The Scheduled Tab page could not be verified");
	    
	    //New Random Email is created
	    String emailHead = objUtility.getPropTestcase().getProperty("emailHead");
	    String emailType = objUtility.getPropTestcase().getProperty("emailType");
	    
	    //fetching and updating the auto increment number used for email id
	    int count = objUtility.getFileData();
		count++;
		String input=String.valueOf(count);
		objUtility.writeFile(input);
	    
	    String email = emailHead+"+"+input+"@"+emailType;
	    System.out.println("email "+email);
	    String firstName =objUtility.getPropTestcase().getProperty("firstName");
	    String lastName=objUtility.getPropTestcase().getProperty("lastName");
	    String mobile=objUtility.getPropTestcase().getProperty("mobile");
	    String emailPassword=objUtility.getPropTestcase().getProperty("emailPassword");
	   
	 
	    //Admin adds New employee
	    objSchedule.AddNewEmployee(firstName,lastName,email, mobile);
	    Assert.assertTrue(objSchedule.VerifyUserAdded(firstName + " "+lastName),"The User added could not be verified");
	    
	    //Using sleep because sometimes it is taking time for the email to be received
	    Thread.sleep(5000);
	    
	    //The invite sent to user is fetched and loaded
	    String inviteURL = objUtility.fetchInviteURL(email,emailPassword,"Setup your Deputy account");	    
		System.out.println("inviteURL "+inviteURL);
		driver.close();
		driver = objUtility.getChromeDriver();
		driver.get(inviteURL);
		
		//The invite page is verified with Title and First Name
		objAcceptInvite = new AcceptInvitePage(driver);
		Assert.assertTrue(objAcceptInvite.verifyUserAcceptInvitePage("You've been asked to join Food Court",firstName),"The User added could not be verified");
		
		//User account is created
		String userAccountPassword=objUtility.getPropTestcase().getProperty("userAccountPassword");
		objAcceptInvite.createUserAccount(userAccountPassword);
		
		//Employee Page is verified with email id
		objUserAccount= new UserAccountPage(driver);
		objUserAccount.verifyUserIsCreated(email);
		
		}
	    catch(Exception e)
		{
	    	System.out.println("Error "+e.getMessage());
	    	objUtility.takeScreenShot(driver);
			throw e;
		}
	}

	
	@AfterTest
    public void AfterTest()
	 
	{

	driver.quit();
	
	}
	
	
}

