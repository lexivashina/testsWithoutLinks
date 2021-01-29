package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.By;

import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.invokeAndroidEvent;
import static STUtils.eUtils.isExists;

public class eContactsPage extends eCommonMethods {

    static By createContact				= By.id("com.android.contacts:id/first_option_menu");
    static By fieldFirstName			= By.xpath("//*[@text[contains(.,'Name') or contains(.,'First name')]]");
    static By fieldPhone				= By.xpath("(//*[@text[contains(.,'Phone')]])[last()]");
    static By contactForRename			= By.xpath("//*[@text[contains(.,'"+ getTestContactNameFirstName() +"')]]");
    static By contactForDeleteRename    = By.xpath("//*[@text[contains(.,'"+ getTestContactNameSecondName() +"')]]");
    static By saveButton				= By.xpath("//*[@text[contains(.,'SAVE')] or @text[contains(.,'Save')] or @content-desc='Save']");
    static By editButton				= By.xpath("//*[contains(@content-desc, 'Edit')]");
    static By moreOptions				= By.xpath("//*[@content-desc[contains(.,'More options')]]");
    static By deleteButtonInDropDown	= By.xpath("//*[@text[contains(.,'Delete')]]");
    static By deleteButtonInConfirm		= By.xpath("//*[@resource-id[contains(.,'android:id/button1')]]");
    static By detailButton				= MobileBy.AccessibilityId("Details Button");



	public eContactsPage(AndroidDriver<AndroidElement> driver)
    {
        super(driver);
    }

    public void addNewContact()
    {
        invokeAndroidEvent(driver, AndroidKey.CONTACTS);
        click(createContact);
        input(fieldFirstName, getTestContactNameFirstName());
        driver.hideKeyboard();
        input(fieldPhone, getProperty("randomNumber"));
        click(saveButton);
        invokeAndroidEvent(driver, AndroidKey.BACK);
        returnToStealthTalk();
    }

    public void renameNewContact()
    {
        openContact();
        waitThenClick(editButton);
        waitForLoadShort(contactForRename);
        driver.findElement(contactForRename).clear();
        input(fieldFirstName, getTestContactNameSecondName());
        click(saveButton);
        returnToStealthTalk();
    }

    public void deleteNewContact()
    {
        openRenamedContact();
        click(moreOptions);
        click(deleteButtonInDropDown);
        click(deleteButtonInConfirm);
        returnToStealthTalk();
    }

    private static void openStealthTalk(AppiumDriver driver)
    {
        ((AndroidDriver)driver).startActivity(new Activity(getStealthTalkPackage(), getStealthTalkActivity()));
    }

    public void openRenamedContact ()
    {
        invokeAndroidEvent(driver, AndroidKey.CONTACTS);
        verticalScrollUp();
        scrollDownUntilElementVisible(contactForDeleteRename);
        click(contactForDeleteRename);
        if (isExists(detailButton)) click(detailButton);  // Galaxy S6
    }

    public void openContact ()
    {
        invokeAndroidEvent(driver, AndroidKey.CONTACTS);
        verticalScrollUp();
        scrollDownUntilElementVisible(contactForRename);
        click(contactForRename);
        if (isExists(detailButton)) click(detailButton);  // Galaxy S6
    }

    public void returnToStealthTalk ()
    {
        invokeAndroidEvent(driver, AndroidKey.BACK);
        openStealthTalk(getDriver(FIRST_DEVICE));
    }


}
