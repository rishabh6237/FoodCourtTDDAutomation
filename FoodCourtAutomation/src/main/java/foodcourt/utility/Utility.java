package foodcourt.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utility
{

	WebDriver driver;
	public static Properties propTestcase;
	
	public Utility(WebDriver driver)
    
    {
    
    	this.driver = driver;

    }
	
	/**
	 * Purpose : Returns a instance of Explicit Web Driver Wait
	 */
	public static WebDriverWait getWait(WebDriver driver,int seconds)
	{
	return new WebDriverWait(driver,seconds);	
	}
	
	/**
	 * Purpose : Loads the data that will be used in automation
	 * @author rish
	 */
	public void setPropertyFiles()
	{
		
		
		propTestcase = new Properties();
		try {
			
		propTestcase.load(new FileInputStream(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"data.properties"));
		System.out.println(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"data.properties");
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Purpose : returns the data property file
	 * @author rish
	 */
	public Properties getPropTestcase(){
		  return propTestcase;
	}
	
	/**
	 * Purpose : returns chrome driver
	 * @author rish
	 */
	public WebDriver getChromeDriver()
	{
	
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+
		File.separator+"drivers"+File.separator+"chromedriver.exe");
		//adding some options to chrome driver	
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");	
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability(ChromeOptions.CAPABILITY,options);
		driver = new ChromeDriver(caps);
		
		return driver;
	}
	
	
	/**
	 * Purpose : returns the invited URL from the email account
	 * @author rish
	 */
	public String fetchInviteURL(String email ,String password,String emailHeaderContent) throws IOException
	{
		
			String inviteURL = "";
			
			Properties props = new Properties();
			try 
			{
				//loads the smtp properties file
				props.load(new FileInputStream(new File(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"smtp.properties")));
			
				System.out.println("session is be8ing created: ");
				Session session = Session.getDefaultInstance(props, null);

				Store store = session.getStore("imaps");
				store.connect("smtp.gmail.com",email,password);
				//reads inbox
				Folder inbox = store.getFolder("inbox");
				inbox.open(Folder.READ_WRITE);
				
				//fetches mails from inbox
				Message[] messages = inbox.getMessages();
				for (int i = (messages.length-1); i > (messages.length-4); i--) {
					
					if(messages[i].getSubject().contains(emailHeaderContent))
					{
					String content = getTextFromMessage(messages[i]);
						
					int loc = content.indexOf("https:");
					String url = content.substring(loc);
					
					
					loc = url.indexOf(" ");
					url = url.substring(0,loc);
					System.out.println("URL "+url);
					inviteURL=url;
					break;
					
					}
				}
				
				//closing inbox
				inbox.close(true);
				store.close();
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return inviteURL;
		
	}
	
	/**
	 * Purpose : returns the mail body content from mail
	 * @author rish
	 */
	
	private String getTextFromMessage(Message message) throws IOException, MessagingException 
	{
	    String result = "";
	    if (message.isMimeType("text/plain")) 
	    {
	        result = message.getContent().toString();
	    }
	    else if (message.isMimeType("multipart/*")) 
	    {
	        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	        result = getTextFromMimeMultipart(mimeMultipart);
	    }
	    return result;
	}

	/**
	 * Purpose : used by getTextFromMessage to handle MimeMultipart Mails
	 * @author rish
	 */
	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException 
	{
	    int count = mimeMultipart.getCount();
	    if (count == 0)
	        throw new MessagingException("Multipart with no body parts not supported.");
	    boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
	    if (multipartAlt)
	        return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
	    String result = "";
	    for (int i = 0; i < count; i++) 
	    {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        result += getTextFromBodyPart(bodyPart);
	    }
	    return result;
	}

	/**
	 * Purpose : used by getTextFromMimeMultipart
	 * @author rish
	 */
	private String getTextFromBodyPart(BodyPart bodyPart) throws IOException, MessagingException 
	{
	    String result = "";
	    if (bodyPart.isMimeType("text/plain")) 
	    {
	        result = (String) bodyPart.getContent();
	    } 
	    else if (bodyPart.isMimeType("text/html")) 
	    {
	        String html = (String) bodyPart.getContent();
	        result = org.jsoup.Jsoup.parse(html).text();
	    }
	    else if (bodyPart.getContent() instanceof MimeMultipart)
	    {
	        result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	    }
	    return result;
	}
	
	
	/**
	 * Purpose : used to read the current count value that will be used to create the email id
	 * @author rish
	 */
	public int getFileData()

	{
		String count="";
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"Count.txt"));
			
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    count = sb.toString();
			    count=count.trim();
			    //System.out.println(count);
			    br.close();
			    
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		return Integer.parseInt(count);
	}
	
	
	/**
	 * Purpose : used to write count value that will be used to create the email id in the next iteration
	 * @author rish
	 */
	public void writeFile(String value)
	{
	    File fileToBeModified = new File(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"Count.txt");    
	    FileWriter writer = null;
	     
	    try
	    {
	    	if(fileToBeModified.delete()){
	    		fileToBeModified.createNewFile();
	    	}else{
	    	    //throw an exception indicating that the file could not be cleared
	    	}
	        writer = new FileWriter(fileToBeModified);
	        writer.write(value);
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	        writer.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	}

	/**
	 * Purpose : used to take screenshot of the screen in case there is any failure
	 * @author rish
	 */
	
	public void takeScreenShot(WebDriver driver) throws Exception
	{
		
		System.out.println("Taking Screenshot");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		DateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh_mm_ss a");
		Date date = new Date();
		
		
		String dateString=dateFormat.format(date);
		System.out.println("dateString "+dateString);
		
	    String fileLocation= System.getProperty("user.dir")+File.separator+"ErrorScreenShot"+File.separator;

	    System.out.println("fileLocation" +fileLocation);
		FileUtils.copyFile(scrFile, new File(fileLocation+"Error on "+dateString+".png"));
		
		System.out.println("Taken Screenshot");
		}
	
	
}
