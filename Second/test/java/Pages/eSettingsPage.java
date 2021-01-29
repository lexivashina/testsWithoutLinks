package Pages;


import STUtils.eDriverHandler.Device;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import static STUtils.eDriverHandler.getDriver;
import static STUtils.eUtils.*;

public class eSettingsPage extends eCommonMethods
{
    By hideTextInNotificationsSwitcher			= By.xpath("//android.widget.FrameLayout[@resource-id='com.app.stealthtalk.testers:id/settings_hide_text_in_notification']/android.view.ViewGroup/android.widget.CheckBox[@resource-id='com.app.stealthtalk.testers:id/settings_item_bool_switch']");
    By getBackButton							= MobileBy.AccessibilityId("Navigate up");
    By changeLanguageSettings					= By.xpath("//*[@text='Application language']");
    By hideTextInNotificationsButton			= By.xpath("//*[@text='Hide text in notifications']");
    By appLanguage								= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/settings_language']//*[@resource-id='com.app.stealthtalk.testers:id/info_button_comment']");


	public eSettingsPage(AndroidDriver driver)
    {
        super(driver);
    }

	public eSettingsPage(Device device)
	{
		super(getDriver(device));
	}


    public void getBack()
    {
        click(getBackButton);
    }

    public void changeApplicationLanguage(String languageToSet)
    {
        click(changeLanguageSettings);
        By languageToSet1 = By.xpath("//*[@text='" + languageToSet + "']");
        click(languageToSet1);
    }

    public String getApplicationLanguage()
    {
        return waitThenGetText(appLanguage);
    }

    public void turnOnHideTextInNotification()
    {
        boolean check = Boolean.parseBoolean(getAttribute(hideTextInNotificationsSwitcher,"checked"));
        if (!check) click(hideTextInNotificationsButton);
        await(1000);
    }

    public void turnOffHideTextInTofication()
    {
        boolean check = Boolean.parseBoolean(getAttribute(hideTextInNotificationsSwitcher,"checked"));
        if (check) click(hideTextInNotificationsButton);
        await(1000);
    }

}
