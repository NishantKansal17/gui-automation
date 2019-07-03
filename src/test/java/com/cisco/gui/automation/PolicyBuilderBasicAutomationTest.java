package com.cisco.gui.automation;

import static org.eclipse.rap.selenium.xpath.XPath.any;

import java.util.List;

import org.eclipse.rap.selenium.RapBot;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cisco.gui.automation.report.ReportGenerator;
import com.thoughtworks.selenium.Selenium;

@Listeners(ReportGenerator.class)
public class PolicyBuilderBasicAutomationTest {

	  private final static String baseUrl = "http://172.19.65.92:7070";

	  private WebDriver driver;
	  private Selenium selenium;
	  private RapBot rap;

	  static {
	    System.setProperty( "webdriver.firefox.bin", "/usr/bin/firefox" );
	    // see http://code.google.com/p/selenium/wiki/ChromeDriver
	    if (System.getProperty("os.name").startsWith("Windows")) {
	    	System.setProperty( "webdriver.chrome.driver", "C:\\Users\\nikansal\\Desktop\\chromedriver_win32\\chromedriver.exe" );
	    } else {
	    	System.setProperty( "webdriver.chrome.driver", "/usr/bin/chromedriver" );
	    }
	    // See http://code.google.com/p/selenium/wiki/InternetExplorerDriver
	    System.setProperty( "webdriver.ie.driver", "C:\\Program Files\\Internet Explorer\\IEDriverServer.exe" );
	  }

	  @BeforeClass
	  public void setUp() throws Exception {
		  System.setProperty("DISPLAY", ":1");
	    driver = new FirefoxDriver();
	    //driver = new ChromeDriver();
	    //driver = new InternetExplorerDriver(); // Not yet tested due to missing IE11 support
	    selenium = new WebDriverBackedSelenium( driver, baseUrl );
	    driver.manage().window().setSize( new Dimension( 1000, 600 ) );
	    //driver.manage().window().maximize();
	    rap = new RapBot( driver, selenium );
	    rap.loadApplication( baseUrl + "/pb", false );
	  }

	  @AfterClass
	  public void tearDown() throws Exception {
	    selenium.stop();
	  }

	  @Test
	  public void testLoadRepositoryAfterLogin() throws Exception {
		  rap.input( any().element( "input" ).firstMatch(), "qns-svn" );
		  Thread.sleep(2000);
		  
		  rap.input( any().element( "input" ).lastMatch(), "cisco123" );
		  Thread.sleep(2000);
		  
		  List<WebElement> divs = rap.getWebDriver().findElements(By.tagName("div"));
		  divs.get(divs.size() - 2).click();
		  
		  Thread.sleep(5000);
		  
		  rap.click( any().textElement( "OK" ) );
		  Thread.sleep(20000);
		  
		  List<WebElement> allDivs = rap.getWebDriver().findElements(By.tagName("div"));
		  WebElement fileElm = null;
		  for (WebElement div : allDivs) {
			  if (div.getText().equals("File")) {
				  fileElm = div;
			  }
		  }
		  fileElm.click();
		  Thread.sleep(5000);
		  List<WebElement> filePopup = rap.getWebDriver().findElements(By.tagName("div"));
		  WebElement publishElm = null;
		  for (WebElement div : filePopup) {
			  if (div.getText().equals("Publish to Runtime Environment...")) {
				  System.out.println(div.getText());
				  publishElm = div;
			  }
		  }
		  publishElm.click();
		  Thread.sleep(5000);
		  
		  rap.input( any().element( "textarea" ).lastMatch(), "selenium testing..." );
		  Thread.sleep(5000);
		  
		  rap.click( any().textElement( "OK" ) );
		  Thread.sleep(60000);
	  }
}
