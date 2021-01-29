package STUtils;

import Pages.eCommonMethods;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static STUtils.eSTProperties.*;


public class eDriverHandler
{
	public enum Application	{OUTLOOK, EVE_TALK, GOOGLE_PLAY}
	public enum Device		{FIRST_DEVICE, SECOND_DEVICE, THIRD_DEVICE}
	public enum PhoneModel	{MOTOGPLAY, HTC, SAMSUNG_S5, SAMSUNG_S6, XT1053, HuaweiP}

    static AndroidDriver<AndroidElement>[] drivers = new AndroidDriver[Device.values().length];

    static AndroidStartScreenRecordingOptions recordingOptions = AndroidStartScreenRecordingOptions
            .startScreenRecordingOptions().withTimeLimit(Duration.ofMinutes(30)).withBitRate(600000);

    private static String firstDeviceName	= getProperty("firstDevice");
    private static String secondDeviceName	= getProperty("secondDevice");
    private static String thirdDeviceName	= getProperty("thirdDevice");

    private static By crashReportMessageInput		= By.id("com.app.stealthtalk.testers:id/alert_dialog_crash_report_edit");
    private static By sendButton					= By.id("android:id/button1");
    private static By stealthTalkMainPageHeader		= By.id("com.app.stealthtalk.testers:id/activity_app_bar_layout");
    private static By registrationView				= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/activity_registration_nav_controller' or @resource-id='com.app.stealthtalk.testers:id/activity_registration_splash_fragment']"); //Second id is for support of old versions
    private static By serviceExpiredLabel			= By.id("com.app.stealthtalk.testers:id/activity_billing_blocked_activate_button");
	private static By contactsAccessRequest			= By.xpath("//*[@text='Allow StealthTalk to access your contacts?']");

    public static AndroidDriver<AndroidElement> getDriver(Device device)
    {
        if (drivers[device.ordinal()] != null) return drivers[device.ordinal()];

		return createDriver(device, Application.EVE_TALK);
    }

    public static AndroidDriver<AndroidElement> createDriver(Device device, Application application)
	{
		String deviceName = getDeviceName(device);
		AndroidDriver<AndroidElement> driver = new AndroidDriver<>(getURL(deviceName), getCapabilities(deviceName, application));
		drivers[device.ordinal()] = driver;

		try {driver.startRecordingScreen(recordingOptions);}
		catch (WebDriverException e) {System.err.format("\nProblem with screen recording on %s\n", deviceName);}

		if(application == Application.EVE_TALK) checkForCrashReports(driver);

		return driver;
	}

    public static String getDeviceName(Device device)
    {
        switch (device)
        {
            case FIRST_DEVICE:
                return firstDeviceName;
            case SECOND_DEVICE:
                return secondDeviceName;
            case THIRD_DEVICE:
                return thirdDeviceName;
            default:
                return "UNKNOWN DEVICE";
        }
    }

    public static String getDeviceName(AndroidDriver driver)
    {
        for(int i=0; i < drivers.length; i++)
            if(drivers[i] == driver)
                return getDeviceName(Device.values()[i]);
        return null;
    }

	//Device specific
	public static PhoneModel getPhoneModel(AndroidDriver driver)
	{
		String deviceUdid = driver.getCapabilities().getCapability("udid").toString();
		switch(deviceUdid)
		{
			case "ZY223JDT5C":			return PhoneModel.MOTOGPLAY;
			case "07157df708270d3e":	return PhoneModel.SAMSUNG_S6;
			case "4d00fb084ad721b7":	return PhoneModel.SAMSUNG_S5;
			case "FA44CWM09741":		return PhoneModel.HTC;
            case "T076000P39":          return PhoneModel.XT1053;
            case "39V4C19823005150":	return PhoneModel.HuaweiP;
			default:					Assert.fail("Device not supported by this method: " + deviceUdid); return null;
		}
	}

    private static DesiredCapabilities getCapabilities (String deviceToUse, Application application)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,			deviceToUse);
		capabilities.setCapability(MobileCapabilityType.UDID,					getConfigProperty(deviceToUse+"udid"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,			getConfigProperty(deviceToUse+"platformName"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,		getConfigProperty(deviceToUse+"platformVersion"));
		capabilities.setCapability(MobileCapabilityType.NO_RESET,				getConfigProperty(deviceToUse+"noReset"));
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,	getConfigProperty("newCommandTimeout"));
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,		"UiAutomator2");
		capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,		getConfigProperty(deviceToUse+"SystemPort"));
		//capabilities.setCapability("setWebContentsDebuggingEnabled", "true");

		switch (application)
		{
			case OUTLOOK:
				capabilities.setCapability("appPackage", getConfigProperty("outlookAppPackage"));
				capabilities.setCapability("appActivity", getConfigProperty("outlookAppActivity"));
				break;
			case EVE_TALK:
				capabilities.setCapability("appPackage", getConfigProperty("stealthTalkAppPackage"));
				capabilities.setCapability("appActivity", getConfigProperty("stealthTalkAppActivity"));
				break;
            case GOOGLE_PLAY:
                capabilities.setCapability("appPackage", getConfigProperty("googlePlayAppPackage"));
                capabilities.setCapability("appActivity", getConfigProperty("googlePlayAppActivity"));
                break;
		}
		return capabilities;
	}

    private static URL getURL(String device)
    {
        try
        {
            return new URL(getConfigProperty(device+"url"));
        }
        catch (MalformedURLException e)
        {
            return null;
        }
    }

    public static void turnOffDriver(Device device)
    {
        if (drivers[device.ordinal()] != null)
        {
            AndroidDriver driver = getDriver(device);
            try
            {
                driver.pressKey(new KeyEvent(AndroidKey.HOME));
                driver.closeApp();
                removeAllScreenRecordings(driver);
                driver.quit();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }

            drivers[device.ordinal()] = null;
        }
    }

    public static void turnOffDrivers()
    {
        for(Device device : Device.values())
        {
            turnOffDriver(device);
        }
    }

    public static String getDeviceId(Device device)
    {
        return getDriver(device).getCapabilities().getCapability("udid").toString();
    }

    private static void removeAllScreenRecordings(AndroidDriver driver) {
        try
        {
            driver.stopRecordingScreen();
            Runtime.getRuntime().exec("adb -s " + driver.getCapabilities().getCapability("udid") +" shell rm storage/emulated/0/*.mp4");
        }
        catch (IOException | WebDriverException  e)
        {
            e.printStackTrace(System.out);
        }
    }

    private static void checkForCrashReports(AndroidDriver driver)
    {
        try
		{
            eCommonMethods eCommonMethods = new eCommonMethods(driver);
            eCommonMethods.waitForAnyElementLong(crashReportMessageInput, stealthTalkMainPageHeader, registrationView, serviceExpiredLabel, contactsAccessRequest);
            if (eCommonMethods.isPresent(crashReportMessageInput))
            {
                System.out.println("Crash happened in " + lastExecutedTestName);
                eCommonMethods.input(crashReportMessageInput, lastExecutedTestName);
                eCommonMethods.click(sendButton);
            }
        }
        catch (Exception e) {e.printStackTrace(System.out);}
    }

    public static void openStealthTalk(Device device)
    {
        getDriver(device).activateApp(getStealthTalkPackage());
    }

    public static void closeStealthTalk(Device device)
    {
        if (getDriver(device).getCurrentPackage().equals(getStealthTalkPackage()))
		{
			getDriver(device).pressKey(new KeyEvent(AndroidKey.HOME));
		}
        getDriver(device).terminateApp(getStealthTalkPackage());
    }

	public static void restartStealthTalk(Device device)
	{
		getDriver(device).pressKey(new KeyEvent(AndroidKey.HOME));
		getDriver(device).terminateApp(getStealthTalkPackage());
		getDriver(device).startActivity(new Activity(getStealthTalkPackage(), getStealthTalkActivity()));
	}
}
