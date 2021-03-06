package com.qa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.ThreadContext;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


// Base Test initializes all the POM pages Mobile elements and uses getters and setters to initialize attributes
public class BaseTest {
	protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	protected static ThreadLocal <String> platform = new ThreadLocal<String>();
	protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
	private static AppiumDriverLocalService server;

	TestUtils utils = new TestUtils();
	public SoftAssert softAssert;

	// Method used to initialize the thread local elements

	  public AppiumDriver getDriver() {
		  return driver.get();
	  }

	  public void setDriver(AppiumDriver driver2) {
		  driver.set(driver2);
	  }

	  public Properties getProps() {
		  return props.get();
	  }

	  public void setProps(Properties props2) {
		  props.set(props2);
	  }

	  public HashMap<String, String> getStrings() {
		  return strings.get();
	  }

	  public void setStrings(HashMap<String, String> strings2) {
		  strings.set(strings2);
	  }

	  public String getPlatform() {
		  return platform.get();
	  }

	  public void setPlatform(String platform2) {
		  platform.set(platform2);
	  }

	  public String getDateTime() {
		  return dateTime.get();
	  }

	  public void setDateTime(String dateTime2) {
		  dateTime.set(dateTime2);
	  }

	  public String getDeviceName() {
		  return deviceName.get();
	  }

	  public void setDeviceName(String deviceName2) {
		  deviceName.set(deviceName2);
	  }
	
	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	// Before method starts recording before the method begins execution
	@BeforeMethod
	public void beforeMethod() {
		((CanRecordScreen) getDriver()).startRecordingScreen();
	}
	
	//stop video capturing and create *.mp4 file
	@AfterMethod
	public synchronized void afterMethod(ITestResult result) throws Exception {
		String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
		
		Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();		
		String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName") 
		+ File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();
		
		File videoDir = new File(dirPath);
		
		synchronized(videoDir){
			if(!videoDir.exists()) {
				videoDir.mkdirs();
			}	
		}
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
			stream.write(Base64.decodeBase64(media));
			stream.close();
			utils.log().info("video path: " + videoDir + File.separator + result.getName() + ".mp4");
		} catch (Exception e) {
			utils.log().error("error during video capture" + e.toString());
		} finally {
			if(stream != null) {
				stream.close();
			}
		}
	}

	// Before suite is used to start the appium server before execution starts
	@BeforeSuite
	public void beforeSuite() throws Exception, Exception {
		ThreadContext.put("ROUTINGKEY", "ServerLogs");
		server = getAppiumService();
		if(!checkIfAppiumServerIsRunnning(4723)) {
			server.start();
			server.clearOutPutStreams();
			utils.log().info("Appium server started");
		} else {
			utils.log().info("Appium server already running");
		}
	}

	// This method validates if server is running or not. If not it returns a boolean false
	public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
	    boolean isAppiumServerRunning = false;
	    ServerSocket socket;
	    try {
	        socket = new ServerSocket(port);
	        socket.close();
	    } catch (IOException e) {
	    	System.out.println("1");
	        isAppiumServerRunning = true;
	    } finally {
	        socket = null;
	    }
	    return isAppiumServerRunning;
	}

	// Before test method initializes the run time params and sets the capabilities at run time
  @Parameters({"emulator", "platformName", "udid", "deviceName"})
  @BeforeTest
  public void beforeTest(@Optional("androidOnly")String emulator, String platformName, String udid, String deviceName) throws Exception {
	  setDateTime(utils.dateTime());
	  setPlatform(platformName);
	  setDeviceName(deviceName);
	  URL url;
		InputStream inputStream = null;
		InputStream stringsis = null;
		Properties props = new Properties();
		AppiumDriver driver;
		
		String strFile = "logs" + File.separator + platformName + "_" + deviceName;
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		//route logs to separate file for each thread
		ThreadContext.put("ROUTINGKEY", strFile);
		utils.log().info("log path: " + strFile);
		
	  try {
		  props = new Properties();
		  String propFileName = "config.properties";
		  String xmlFileName = "strings/strings.xml";
		  
		  utils.log().info("load " + propFileName);
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  setProps(props);
		  
		  utils.log().info("load " + xmlFileName);
		  stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  setStrings(utils.parseStringXML(stringsis));
		  
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability("platformName", platformName);
			desiredCapabilities.setCapability("deviceName", deviceName);
			desiredCapabilities.setCapability("udid", udid);
			url = new URL(props.getProperty("appiumURL"));
			
			switch(platformName) {
			case "Android":
				desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));	
				desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
				desiredCapabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
				desiredCapabilities.setCapability("nativeWebScreenshot", true);
				desiredCapabilities.setCapability("androidScreenshotPath", "target/screenshots");
				if(emulator.equalsIgnoreCase("true")) {
					desiredCapabilities.setCapability("avd", deviceName);
					desiredCapabilities.setCapability("avdLaunchTimeout", 120000);
				}
				String androidAppUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
				utils.log().info("appUrl is" + androidAppUrl);
				desiredCapabilities.setCapability("app", androidAppUrl);

				driver = new AndroidDriver(url, desiredCapabilities);
				break;
			case "iOS":
				desiredCapabilities.setCapability("automationName", props.getProperty("iOSAutomationName"));
				String iOSAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
				utils.log().info("appUrl is" + iOSAppUrl);
				desiredCapabilities.setCapability("bundleId", props.getProperty("iOSBundleId"));
				desiredCapabilities.setCapability("app", iOSAppUrl);

				driver = new IOSDriver(url, desiredCapabilities);
				break;
			default:
				throw new Exception("Invalid platform! - " + platformName);
			}
			setDriver(driver);
			utils.log().info("driver initialized: " + driver);
	  } catch (Exception e) {
		  utils.log().fatal("driver initialization failure. ABORT!!!\n" + e.toString());
		  throw e;
	  } finally {
		  if(inputStream != null) {
			  inputStream.close();
		  }
		  if(stringsis != null) {
			  stringsis.close();
		  }
	  }
  }

	public AppiumDriverLocalService getAppiumService() {
		HashMap<String, String> environment = new HashMap<String, String>();
		environment.put("PATH", "" + System.getenv("PATH"));
		environment.put("ANDROID_HOME", "/Users/srikanthparvatikar/Library/Android/sdk");
		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File("/usr/local/bin/node"))
				.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				.usingPort(4723)
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withEnvironment(environment)
				.withLogFile(new File("ServerLogs/server.log")));
	}

  public JSONObject dataProvider(String dataFileName) throws IOException {
	  InputStream datais = null;
	  JSONObject jsonObject;
	  try {
		  datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
		  JSONTokener tokener = new JSONTokener(datais);
		  jsonObject = new JSONObject(tokener);
	  } catch(Exception e) {
		  e.printStackTrace();
		  throw e;
	  } finally {
		  if(datais != null) {
			  datais.close();
		  }
	  }
	  return jsonObject;
  }

  // This method is used to wait thread until the element is visible
  public void waitForVisibility(MobileElement e) {
	  WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
	  wait.until(ExpectedConditions.visibilityOf(e));
  }

  // This method is used to validate if the element is visible
  public Boolean isElementVisible(MobileElement e){
	  waitForVisibility(e);
	  return e.isDisplayed();
  }

//  public void waitForVisibility(WebElement e){
//	  Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
//	  .withTimeout(Duration.ofSeconds(30))
//	  .pollingEvery(Duration.ofSeconds(5))
//	  .ignoring(NoSuchElementException.class);
//
//	  wait.until(ExpectedConditions.visibilityOf(e));
//	  }

	// This method is used to cleat a text field
  public void clear(MobileElement e) {
	  waitForVisibility(e);
	  e.clear();
  }

  // This method is used to click an element
  public void click(MobileElement e) {
	  waitForVisibility(e);
	  e.click();
  }

	// This method is used to click an element and log the info
  public void click(MobileElement e, String msg) {
	  waitForVisibility(e);
	  utils.log().info(msg);
	  ExtentReport.getTest().log(Status.INFO, msg);
	  e.click();
  }

	// This method is used to send keys in an text box
  public void sendKeys(MobileElement e, String txt) {
	  waitForVisibility(e);
	  e.sendKeys(txt);
  }

	// This method is used to send keys in an text box and log info
  public void sendKeys(MobileElement e, String txt, String msg) {
	  waitForVisibility(e);
	  utils.log().info(msg);
	  ExtentReport.getTest().log(Status.INFO, msg);
	  e.sendKeys(txt);
  }

	// This method is used to send keys and along press enter
  public void sendKeysAndPressEnter(MobileElement element, String input){
	  	Actions actions = new Actions(getDriver());
	  	actions.click(element).sendKeys(input).sendKeys(Keys.ENTER).build().perform();
  }

	// This method is used to simulate click on back button on mobile
  public void backButtonTap(){
	  	AndroidDriver driver = (AndroidDriver) getDriver();
	  driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
  }

  // This method is used to get text attribute value of an MobileElement
  public String getAttribute(MobileElement e, String attribute) {
	  waitForVisibility(e);
	  return e.getAttribute(attribute);
  }

  // This method is used to get text from an MobileElement
  public String getText(MobileElement e, String msg) {
	  String txt = null;
	  waitForVisibility(e);
	  switch(getPlatform()) {
	  case "Android":
		  txt = getAttribute(e, "text");
		  break;
	  case "iOS":
		  txt = getAttribute(e, "label");
		  break;
	  }
	  utils.log().info(msg + txt);
	  ExtentReport.getTest().log(Status.INFO, msg);
	  return txt;
  }

	// This method is used to close the mobile APP
  public void closeApp() {
	  ((InteractsWithApps) getDriver()).closeApp();
  }

	// This method is used to launch the mobile APP
  public void launchApp() {
	  ((InteractsWithApps) getDriver()).launchApp();
  }

  // This method is used to scroll to an element until it is found for Android device
  public MobileElement scrollToElementText(String visibleText) {
		return (MobileElement) ((FindsByAndroidUIAutomator) getDriver()).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
						+ "new UiSelector().textContains(\""+visibleText+"\").instance(0))");
  }

	// This method is used to scroll to an element until it is found for IOS device
  public void iOSScrollToElement() {
	  RemoteWebElement element = (RemoteWebElement)getDriver().findElement(By.name("test-ADD TO CART"));
	  String elementID = element.getId();
	  HashMap<String, String> scrollObject = new HashMap<String, String>();
	  scrollObject.put("element", elementID);
	  scrollObject.put("toVisible", "sdfnjksdnfkld");
	  getDriver().executeScript("mobile:scroll", scrollObject);
  }

  // This method is used to quit the driver after the execution is completed
  @AfterTest
  public void afterTest() {
	  getDriver().quit();
  }
}
