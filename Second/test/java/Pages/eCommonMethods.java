package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

import static Pages.eCommonMethods.Permission.СAMERA;
import static STUtils.eDriverHandler.getPhoneModel;
import static STUtils.eSTProperties.getDefaultPasscode;
import static STUtils.eUtils.await;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class eCommonMethods
{
	WebDriver webDriver;
    AndroidDriver<AndroidElement> driver;
    private WebDriverWait shortWait;
    private WebDriverWait regularWait;
    private WebDriverWait longWait;

	By photoAcceptButton				= By.xpath("//*[@resource-id='com.sec.android.app.camera:id/okay' or @resource-id='org.codeaurora.snapcam:id/done_button']");
	By allowButton						= By.xpath("//android.widget.Button[@text='ALLOW' or @text='Allow']");
	By cameraPermissionRequest			= By.xpath("//*[@text='Camera permissions']");
	By microphonePermissionRequest		= By.xpath("//*[@text='Microphone permissions']");
	By locationPermissionRequest		= By.xpath("//*[@text='Location permissions']");
	By contactsAccessRequest			= By.xpath("//*[@text='Matching you with your contacts']");
	By photosPermissionRequest			= By.xpath("//*[@text='Allow StealthTalk to access photos, media, and files on your device?']");
	By storagePermissionRequest			= By.xpath("//*[@text='Storage permissions']");
	By backButton 						= MobileBy.AccessibilityId("Navigate up");
	By enterPasscodeInputField			= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_passcode_edit");
	By commonСameraOkButton				= By.xpath("//*[@text='OK' or @content-desc='done' or @content-desc='OK' or @content-desc='Review done']");
	By urlAdressInChrome				= By.id("com.android.chrome:id/url_bar");

	public eCommonMethods(AndroidDriver<AndroidElement> driver)
    {
        this.driver	= driver;
        shortWait	= new WebDriverWait(driver,10);
        regularWait	= new WebDriverWait(driver,60);
        longWait	= new WebDriverWait(driver, 180);
    }

	public eCommonMethods(WebDriver webDriver, AndroidDriver<AndroidElement> driver) {
		this.webDriver 	= webDriver;
		this.driver		= driver;
		shortWait		= new WebDriverWait(driver,10);
		regularWait		= new WebDriverWait(driver,60);
		longWait		= new WebDriverWait(driver, 180);
	}

	public eCommonMethods(WebDriver webDriver)
	{
		this.webDriver = webDriver;
	}

	public enum Permission
	{
		CONTACTS 			("Allow StealthTalk to access your contacts?"),
		MICROPHONE 			("Allow StealthTalk to record audio?"),
		LOCATION 			("Allow StealthTalk to access this device's location?"),
		PHOTOS 				("Allow StealthTalk to access photos"),
		СAMERA 				("Allow StealthTalk to take pictures and record video?"),
		STORAGE				("Allow StealthTalk to access photos, media, and files on your device?");

		public String name;
		Permission (String name) { this.name = name;}
	}

    public void waitThenClick(By locator)
    {
        regularWait.until(elementToBeClickable(locator)).click();
    }

    public void click(By locator)
    {
    	try
		{
			shortWait.until(elementToBeClickable(locator)).click();
		}
    	catch(StaleElementReferenceException exception)
		{
			click(locator);
		}
    }


    void clickByCoordinates(By locator)
    {
        shortWait.until(visibilityOfElementLocated(locator));
        Point elementCenterPoint = driver.findElement(locator).getCenter();
        PointOption elementPointOption = PointOption.point(elementCenterPoint);
        new TouchAction<>(driver).tap(elementPointOption).perform();
    }

    public void input(By locator, String textToInput)
    {
        shortWait.until(visibilityOfElementLocated(locator)).sendKeys(textToInput);
    }

    public void sendKeys(String textToInput)
	{
		driver.getKeyboard().sendKeys(textToInput);
	}

    public String waitThenGetText(By locator)
    {
		return regularWait.until(visibilityOfElementLocated(locator)).getText();
    }

    public String getText(By locator)
    {
		try
		{
			return shortWait.until(presenceOfElementLocated(locator)).getText();
		}
		catch(StaleElementReferenceException e)
		{
			return getContentDescription(locator);
		}
    }

    public int getHeight(By locator)
	{
		return shortWait.until(presenceOfElementLocated(locator)).getSize().height;
	}

    public String getContentDescription(By locator)
    {
    	try
		{
			return shortWait.until(presenceOfElementLocated(locator)).getAttribute("content-desc");
		}
    	catch(StaleElementReferenceException e)
		{
			return getContentDescription(locator);
		}
    }

    public String getAttribute(By locator, String attribute)
    {
        return shortWait.until(visibilityOfElementLocated(locator)).getAttribute(attribute);
    }

    public List<AndroidElement> returnList(By locator)
    {
        return driver.findElements(locator);
    }

	public void waitForLoadShort(By locator)
	{
		shortWait.until(visibilityOfElementLocated(locator));
	}

	public void waitForAnyElementShort(By ... locators)
	{
		ExpectedCondition<WebElement>[] conditions = new ExpectedCondition[locators.length];
		for(int x = 0; x < locators.length; x++)
		{
			conditions[x] = visibilityOfElementLocated(locators[x]);
		}
		shortWait.until(ExpectedConditions.or(conditions));
	}

    public void waitForLoad(By locator)
    {
        regularWait.until(visibilityOfElementLocated(locator));
    }

    public void waitForLoad(By ... locators)
    {
        ExpectedCondition<WebElement>[] conditions = new ExpectedCondition[locators.length];
        for(int x = 0; x < locators.length; x++)
        {
            conditions[x] = visibilityOfElementLocated(locators[x]);
        }
        regularWait.until(ExpectedConditions.or(conditions));
    }

    public void waitForLoadLong(By locator)
	{
		longWait.until(visibilityOfElementLocated(locator));
	}

	public void waitForAnyElementLong(By ... locators)
	{
		ExpectedCondition<WebElement>[] conditions = new ExpectedCondition[locators.length];
		for(int x = 0; x < locators.length; x++)
		{
			conditions[x] = visibilityOfElementLocated(locators[x]);
		}
		longWait.until(ExpectedConditions.or(conditions));
	}

    public void waitForAbsenceShort(By locator)
    {
        shortWait.until(invisibilityOfElementLocated(locator));
    }

    public AndroidElement waitThenReturn(By locator)
    {
        regularWait.until(visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public void verifyAvailability(By locator)
    {
        List<AndroidElement> list = returnList(locator);
        Assert.assertEquals(list.size(), 1);
    }

    public void verifyQuantity(By locator, int elementsAmount)
    {
    	await(1000);
        List<AndroidElement> list = returnList(locator);
        Assert.assertEquals(list.size(), elementsAmount);
    }

    public void clickIfPossible(By locator)
    {
        if (isPresent(locator))
        {
            click(locator);
        }
    }

    public void verticalScrollDown()
    {
        await(1000);
        Dimension windowSize = driver.manage().window().getSize();
        PointOption startPoint = PointOption.point(windowSize.width/2, windowSize.height/2);
        PointOption finishPoint = PointOption.point(windowSize.width/2, 1);
        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
        await(1000);
    }

	public void scrollDownUntilElementVisibleWeb (By locator){

		WebElement element = driver.findElement(locator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();

	}

    public void verticalScrollUp()
    {
        await(1000);
        Dimension windowSize = driver.manage().window().getSize();
        PointOption startPoint = PointOption.point(windowSize.width/2, windowSize.height/5);
        PointOption finishPoint = PointOption.point(windowSize.width/2, windowSize.height/2);
        new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
        await(1000);
    }

    public void scrollDownUntilElementVisible (By locator){
        int attempts = 0;
        while (returnList(locator).isEmpty() && attempts++ < 25){
            verticalScrollDown();
        }
    }

    public void scrollUpUntilElementVisible (By locator){
        int attempts = 0;
        while (returnList(locator).isEmpty() && attempts++ < 25){
            verticalScrollUp();
        }
    }

    public void tapCallNotification()
    {
        await(1000);
        Dimension windowSize = driver.manage().window().getSize();
        PointOption point = PointOption.point(windowSize.width/4, windowSize.height/15);
        new TouchAction<>(driver).tap(point).perform();
    }

    public void inputPasscode()
    {
        waitForLoadShort(enterPasscodeInputField);
        sendKeys(getDefaultPasscode());
        waitForAbsenceShort(enterPasscodeInputField);
    }

	public void inputPasscode(String passcode)
	{
		waitForLoadShort(enterPasscodeInputField);
		clearPasscode();
		sendKeys(passcode);
	}

    public boolean isVisible(By locator)
    {
        List<AndroidElement> list = driver.findElements(locator);
        if (list.isEmpty()) return false;
        return list.get(0).isDisplayed();
    }

    public boolean isPresent(By locator)
    {
    	return !driver.findElements(locator).isEmpty();
    }

    public boolean isPresentWithWait(By locator, Integer seconds)
	{
		WebDriverWait waiter = new WebDriverWait(driver, seconds);
		try {
			waiter.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return !driver.findElements(locator).isEmpty();
	}

    public void clear(By locator)
    {
        waitForLoadShort(locator);
        driver.findElement(locator).clear();
    }

    public void clearThenInput(By locator, String textToInput)
    {
        shortWait.until(visibilityOfElementLocated(locator));
        AndroidElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(textToInput);
    }

	public boolean isAbsent(By locator)
	{
		List<AndroidElement> list = driver.findElements(locator);
		if(list.isEmpty())	return true;
		else				return false;
	}

	public void pressAndroidBackButton()
	{
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}

	//TODO REFACTORING DEVICE SPECIFIC: MOTOGPLAY XT1053
	public void takePhoto()
	{
		allowPermission(СAMERA);
		await(5000);
		switch(getPhoneModel(driver))
		{
			case MOTOGPLAY:
				driver.pressKey(new KeyEvent(AndroidKey.CAMERA));
				await(7000);
				new TouchAction<>(driver).tap(PointOption.point(437, 1037)).perform();
				break;
			case XT1053:
				click(By.id("com.motorola.camera:id/preview_surface"));
				click(By.id("com.motorola.camera:id/review_approve"));
				break;
			default:
				driver.pressKey(new KeyEvent(AndroidKey.CAMERA));
				waitThenClick(commonСameraOkButton);
				break;
		}
	}

	public void allowPermission (Permission permission)
	{
		By generalPermission= By.xpath("//*[contains(@text,'"+permission.name+"')]");
		if(isPresentWithWait(generalPermission, 5))
		{
			click(allowButton);
			waitForAbsenceShort(allowButton);
		}
	}

	public void checkPhotosPermission()
	{
		if (isPresent(photosPermissionRequest))
		{
			click(allowButton);

		}
	}

	public void checkStoragePermission()
	{
		if (isPresent(storagePermissionRequest))
		{
			click(allowButton);

		}
	}

	public void checkMicrophonePermissionRequest()
	{
		if(isPresent(microphonePermissionRequest))
		{
			click(allowButton);
			waitForAbsenceShort(allowButton);
		}
	}

	public void checkBluetoothPermissionRequest()
	{
		if(isPresent(locationPermissionRequest))
		{
			click(allowButton);
			waitForAbsenceShort(allowButton);
		}
	}

	public void getBackToMainView()
	{
		while(isPresent(backButton))
		{
			click(backButton);
			await(1000);
		}
	}

    public void clearPasscode()
    {
        for(int x = 0; x < 4; x++) driver.pressKey(new KeyEvent(AndroidKey.DEL));
    }

	void verifyAbsence(By selector)
	{
		List<AndroidElement> list = driver.findElements(selector);
		Assert.assertEquals(list.size(), 0);
	}

	void longClick(By selector)
	{
		waitForLoadShort(selector);
		Point elementCenterPoint = driver.findElement(selector).getCenter();
		PointOption elementPointOption = PointOption.point(elementCenterPoint);
		new TouchAction<>(driver).longPress(elementPointOption).release().perform();
	}
}
