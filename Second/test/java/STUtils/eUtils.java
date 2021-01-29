package STUtils;

import Pages.eCommonMethods;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static STUtils.eDriverHandler.Application.OUTLOOK;
import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eSTProperties.*;
import static java.lang.Math.abs;

public class eUtils
{
    private static By clearNotificationButton = By.xpath("//*[@content-desc[contains(.,'Clear all') ]]");
    private static By pushNotification = By.xpath("//*[@resource-id='android:id/text' and @text='New message']");
    private static By allowButton = By.xpath("//*[@resource-id='com.android.packageinstaller:id/permission_allow_button' or @resource-id='android:id/button1']");
    private static By notificationPanel = By.id("com.android.systemui:id/notification_panel");
    private static boolean toSetAnalyzer = true;

    public static void verifyPushNotificationMessage(AndroidDriver driver)
    {
        eCommonMethods wait = new eCommonMethods(driver);
        driver.openNotifications();
        await(10000);
        wait.verifyAvailability(pushNotification);
    }

    public static void verifyTime(String deviceTime, MobileElement sentTime)
    {
        try
        {
            DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            Date date = defaultDateFormat.parse(deviceTime);
            String currentTime = new SimpleDateFormat("hh:mm").format(date);

            String hourValue = currentTime.split(":")[0];
            String minuteValue = currentTime.split(":")[1];
            int possibleMinute = Integer.parseInt(minuteValue) + 1;
            if (possibleMinute > 59) {
                hourValue = (Integer.parseInt(hourValue) + 1 < 10 ? "0" + Integer.parseInt(hourValue) + 1 : String.valueOf(Integer.parseInt(hourValue) + 1));
                possibleMinute = possibleMinute - 59;
                if (hourValue.equals("13")) hourValue = "00";
            }
            String possibleCurrentTime = hourValue + ":" + (possibleMinute < 10 ? "0" + possibleMinute : possibleMinute);

            Assert.assertTrue(sentTime.getText().contains(currentTime) || sentTime.getText().contains(possibleCurrentTime));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public static void verifyTime(String actualTime, long expectedTime)
    {
        Date date = null;
        try {
            actualTime = actualTime.replaceAll("Sent|Delivered|Seen","");
            actualTime = new SimpleDateFormat("yyyy MMM dd ").format(new Date()) + actualTime;
            date = new SimpleDateFormat("yyyy MMM dd hh:mm aa").parse(actualTime);
        }
        catch (Exception e) {e.printStackTrace();}

        if (Math.abs(expectedTime - date.getTime()) > 120000)
            Assert.fail("Difference between actual and expected time is over 2 minutes");
    }

    public static void waitTime(String timeValue)
    {
        switch (timeValue)
        {
            case "5 seconds":
            {
                await(5000);
                return;
            }
            case "1 minute":
            {
                await(60000);
                return;
            }
            case "5 minutes":
            {
                await(360000);
                break;
            }
            case "15 minutes":
            {
                await(9010000);
                break;
            }
            case "30 minutes":
            {
                await(18010000);
                break;
            }
            case "60 minutes":
            {
                await(36010000);
                break;
            }
            case "24 hours":
            {
                await(864010000);
            }
            default: break;
        }
    }

    public static void await(long waitValue)
    {
        try
        {
            Thread.sleep(waitValue);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void reloadDevice(AndroidDriver driver)
    {
        driver.lockDevice();
        await(2000);
        driver.unlockDevice();

        if (new eCommonMethods(driver).returnList(notificationPanel).size() == 1)
        {
            scroll(driver);
        }
    }

    private static void scroll(AndroidDriver driver)
    {
        await(1000);

        Dimension windowSize = driver.manage().window().getSize();

        PointOption finishPoint = PointOption.point(windowSize.width / 2, 1);
        PointOption startPoint = PointOption.point(windowSize.width / 2, windowSize.height - 1);

        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();

        await(1000);
    }

	public static void reInstallApp(Device device)
	{
		installStealthTalk(device, getAppPath());
	}

	public static void reInstallEnterpriseApp(Device device)
    {
        installStealthTalk(device, getEnterpriseAppPath());
    }

	public static void installStealthTalk(Device device, String installerLocation)
	{
		AndroidDriver driver = createDriver(device, OUTLOOK);
		driver.removeApp(getStealthTalkPackage());
		driver.installApp(installerLocation);
		driver.startActivity(new Activity(getStealthTalkPackage(), getAppRegistrationActivity()));
	}

	public static void updateApp(AndroidDriver driver, String installerLocation)
	{
		driver.installApp(installerLocation);
		driver.startActivity(new Activity(getStealthTalkPackage(), getAppRegistrationActivity()));
	}

    public static void closeNotifications(AndroidDriver driver)
    {
        new eCommonMethods(driver).scrollDownUntilElementVisible(clearNotificationButton);
        driver.findElement(clearNotificationButton).click();
    }

    @Deprecated
    public static void allowPermission(AndroidDriver driver)
    {
        await(3000);
        eCommonMethods wait = new eCommonMethods(driver);
        wait.clickIfPossible(allowButton);
    }

    public static boolean toSetRetry()
    {
        if (toSetAnalyzer)
        {
            toSetAnalyzer = false;
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void invokeAndroidEvent(AndroidDriver<AndroidElement> driver, AndroidKey androidKey)
	{
        driver.pressKey(new KeyEvent(androidKey));
    }

    public static boolean isExists(By by) {
        return new eCommonMethods(getDriver(FIRST_DEVICE)).returnList(by).size() == 1;
    }

    public static void openGooglePhotos (AndroidDriver driver)
    {
        driver.startActivity(new Activity(getConfigProperty("photoAppPackage"),
                        getConfigProperty("photoAppActivity")));
    }

	public static String generateRandomPhoneNumber(String beginsWith)
	{
		String phoneNumber;
		do
		{
			phoneNumber = beginsWith + RandomStringUtils.randomNumeric(7);
		}
		while (Integer.parseInt(phoneNumber.substring(3,6)) < 200);

		return phoneNumber;
	}

	public static String generateRandomPhoneNumberKiribati(String beginsWith)
	{
		return beginsWith + RandomStringUtils.randomNumeric(3);
	}

	public static void verifyBruteForceTime(String actualBanDateString, Date expectedBanDate)
	{
		Date actualBanTimeInDateFormat		= null;
		String dateMessageWithCurrentYear	= new SimpleDateFormat("yyyy ").format(new Date()) + actualBanDateString;
		try
		{
			if(dateMessageWithCurrentYear.toLowerCase().contains("am") || dateMessageWithCurrentYear.toLowerCase().contains("pm"))
			{
				actualBanTimeInDateFormat = new SimpleDateFormat("yyyy dd MMM hh:mm aa").parse(dateMessageWithCurrentYear);
			}
			else actualBanTimeInDateFormat = new SimpleDateFormat("yyyy dd MMM hh:mm").parse(dateMessageWithCurrentYear);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		long timeDifferenceInSeconds = abs(actualBanTimeInDateFormat.getTime() - expectedBanDate.getTime()) / 1000;
		if (timeDifferenceInSeconds >= 120)
			Assert.fail("The end of ban is incorrect.\nExpected value should be around: " + expectedBanDate.toString() + "\nActual value: " + actualBanTimeInDateFormat.toString());
	}

    public static void executeInTerminal(String command)
    {
        class StreamGobbler implements Runnable {
            private InputStream inputStream;
            private Consumer<String> consumer;

            public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
                this.inputStream = inputStream;
                this.consumer = consumer;
            }

            @Override
            public void run() {
                new BufferedReader(new InputStreamReader(inputStream)).lines()
                        .forEach(consumer);
            }
        }

        try
        {
            Process process = Runtime.getRuntime().exec(command);
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);

//            process.waitFor();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
