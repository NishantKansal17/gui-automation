package com.cisco.gui.automation;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
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
	  private String fileDownloadPath = "C:/Program Files (x86)/Jenkins/workspace/cps-gui-automation";

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
		  Map<String, Object> prefsMap = new HashMap<String, Object>();
		   prefsMap.put("profile.default_content_settings.popups", 0);
		   prefsMap.put("download.default_directory", fileDownloadPath);
		   
		   ChromeOptions option = new ChromeOptions();
		   option.setExperimentalOptions("prefs", prefsMap);
		   option.addArguments("--test-type");
		   option.addArguments("--disable-extensions");
	       
	    driver = new ChromeDriver(option);
	    //driver = new InternetExplorerDriver(); // Not yet tested due to missing IE11 support
	    selenium = new WebDriverBackedSelenium( driver, baseUrl );
	    driver.manage().window().setSize( new Dimension( 1000, 600 ) );
	    //driver.manage().window().maximize();
	    File d = new File(fileDownloadPath);
	    if (d.isDirectory()) {
	    	File[] files = d.listFiles();
	    	for (File f : files) {
	    		if (f.getName().contains(".cps")) {
	    			f.delete();
	    		}
	    	}
	    }
	  }

	  @AfterClass
	  public void tearDown() throws Exception {
	    selenium.stop();
	  }

	  @Test
	  public void testExportCps() throws Exception {
		  selenium.open( baseUrl + "/central" );
		  selenium.waitForPageToLoad( "20000" );
		  WebElement userTxt = this.driver.findElement(By.id("User"));
		  userTxt.sendKeys("qns-svn");
		  Thread.sleep(3000);
		  
		  WebElement pwdTxt = this.driver.findElement(By.id("Password"));
		  pwdTxt.sendKeys("cisco123");
		  Thread.sleep(3000);
		  
		  WebElement loginBtn = this.driver.findElement(By.id("loginBtn"));
		  loginBtn.click();
		  Thread.sleep(3000);
		  
		  WebElement linkImportExport = this.driver.findElement(By.linkText("Import/Export"));
		  linkImportExport.click();
		  Thread.sleep(3000);
		  
		  WebElement exportUrl = this.driver.findElement(By.id("exportUrl"));
		  exportUrl.clear();
		  exportUrl.sendKeys("http://pcrfclient01/repos/configuration");
		  Thread.sleep(5000);
		  
		  WebElement exportName = this.driver.findElement(By.id("exportName"));
		  exportName.clear();
		  exportName.sendKeys("gui-automation-export");
		  Thread.sleep(5000);
		  
		  WebElement exportButton = this.driver.findElement(By.id("exportButton"));
		  exportButton.click();
		  Thread.sleep(20000);
	  }
	  
	  @Test
	  public void testImportCps() throws Exception {
		  selenium.open( baseUrl + "/central" );
		  selenium.waitForPageToLoad( "20000" );
		  WebElement userTxt = this.driver.findElement(By.id("User"));
		  userTxt.sendKeys("qns-svn");
		  Thread.sleep(3000);
		  
		  WebElement pwdTxt = this.driver.findElement(By.id("Password"));
		  pwdTxt.sendKeys("cisco123");
		  Thread.sleep(3000);
		  
		  WebElement loginBtn = this.driver.findElement(By.id("loginBtn"));
		  loginBtn.click();
		  Thread.sleep(3000);
		  
		  WebElement linkImportExport = this.driver.findElement(By.linkText("Import/Export"));
		  linkImportExport.click();
		  Thread.sleep(3000);
		  
		  WebElement importTab = this.driver.findElement(By.linkText("Import"));
		  importTab.click();
		  Thread.sleep(3000);
		  
		  File exportedFile = null;
		  File d = new File(fileDownloadPath);
		    if (d.isDirectory()) {
		    	File[] files = d.listFiles();
		    	for (File f : files) {
		    		if (f.getName().contains(".cps")) {
		    			exportedFile = f;
		    			break;
		    		}
		    	}
		    }
		    
		    WebElement inputGroup = this.driver.findElement(By.className("input-group"));
		    WebElement inputGrpBtn = inputGroup.findElement(By.className("input-group-btn"));
		    WebElement permissionBtn = inputGrpBtn.findElement(By.className("permission_button"));
		    WebElement importFile = permissionBtn.findElement(By.id("importFile"));
		    //System.out.println("File Path: " + exportedFile.getAbsolutePath());
		    importFile.sendKeys(exportedFile.getAbsolutePath());
		    Thread.sleep(4000);
		    
		    WebElement importUrl = this.driver.findElement(By.id("importUrl"));
		    importUrl.clear();
		    importUrl.sendKeys("http://pcrfclient01/repos/configuration");
		    Thread.sleep(2000);
		    
		    WebElement importButton = this.driver.findElement(By.id("importButton"));
		    importButton.click();
		    Thread.sleep(20000);
		    
		    this.driver.switchTo().activeElement();
		    
		    WebElement closeBtn = this.driver.findElement(By.xpath("//*[@id=\"responseModal\"]/div/div/div[3]/button"));
		    closeBtn.click();
		    Thread.sleep(5000);
		    
		    WebElement publishLink = this.driver.findElement(By.linkText("Publish"));
		    publishLink.click();
		    Thread.sleep(5000);
		    
		    WebElement commitMessage = this.driver.findElement(By.id("commitMessage"));
		    commitMessage.sendKeys("Publishing...");
		    Thread.sleep(5000);
		    
		    List<WebElement> rows = this.driver.findElements(By.className("row"));
		    System.out.println(rows.get(rows.size()-1));
		    WebElement row = rows.get(rows.size()-1);
		    WebElement colXs12 = row.findElement(By.className("col-xs-12"));
		    WebElement pullRight = colXs12.findElement(By.className("pull-right"));
		    List<WebElement> buttons = pullRight.findElements(By.tagName("button"));
		    System.out.println(buttons.size());
		    buttons.get(1).click();
		    Thread.sleep(20000);
	  }
	  
	  @Test
	  public void testLoginToCpsCentral() throws Exception {
		  selenium.open( baseUrl + "/central" );
		  selenium.waitForPageToLoad( "20000" );
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
