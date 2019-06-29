package com.cisco.gui.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cisco.gui.automation.report.ReportGenerator;
import com.thoughtworks.selenium.Selenium;

import junit.framework.Assert;

@Listeners(ReportGenerator.class)
public class CPSCentralBasicAutomationTest {

	  private final static String baseUrl = "http://172.19.65.92:7070";

	  private WebDriver driver;
	  private Selenium selenium;

	  static {
	    System.setProperty( "webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" );
	    // see http://code.google.com/p/selenium/wiki/ChromeDriver
	    System.setProperty( "webdriver.chrome.driver", "C:\\Users\\nikansal\\Desktop\\chromedriver_win32\\chromedriver.exe" );
	    // See http://code.google.com/p/selenium/wiki/InternetExplorerDriver
	    System.setProperty( "webdriver.ie.driver", "C:\\Program Files\\Internet Explorer\\IEDriverServer.exe" );
	  }

	  @BeforeClass
	  public void setUp() throws Exception {
	    //driver = new FirefoxDriver();
	    driver = new ChromeDriver();
	    //driver = new InternetExplorerDriver(); // Not yet tested due to missing IE11 support
	    selenium = new WebDriverBackedSelenium( driver, baseUrl );
	    driver.manage().window().setSize( new Dimension( 1000, 600 ) );
	    //driver.manage().window().maximize();
	    selenium.open( baseUrl + "/central" );
	    selenium.waitForPageToLoad( "20000" );
	  }

	  @AfterClass
	  public void tearDown() throws Exception {
	    selenium.stop();
	  }

	  @Test
	  public void testLoginToCpsCentral() throws Exception {
		  WebElement userTxt = this.driver.findElement(By.id("User"));
		  userTxt.sendKeys("qns-svn");
		  Thread.sleep(3000);
		  
		  WebElement pwdTxt = this.driver.findElement(By.id("Password"));
		  pwdTxt.sendKeys("cisco123");
		  Thread.sleep(3000);
		  
		  WebElement loginBtn = this.driver.findElement(By.id("loginBtn"));
		  loginBtn.click();
		  Thread.sleep(3000);
		  
		  WebElement app = this.driver.findElement(By.className("app"));
		  WebElement containerFluid = app.findElement(By.className("container-fluid"));
		  WebElement h2 = containerFluid.findElement(By.tagName("h2"));
		  Assert.assertTrue(h2.getText().contains("Cisco Policy Suite"));
		  Thread.sleep(3000);
	  }
}
