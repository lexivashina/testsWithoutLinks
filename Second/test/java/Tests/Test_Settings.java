package Tests;

import Pages.eChatPage;
import Pages.eMainPage;
import Pages.eSettingsPage;
import STUtils.eDriverHandler;
import STUtils.eTestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.Device.SECOND_DEVICE;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eDriverHandler.turnOffDrivers;
import static STUtils.eSTProperties.getConfigProperty;
import static STUtils.eSTProperties.getProperty;
import static STUtils.eUtils.*;

@Listeners(eTestListener.class)
public class Test_Settings
{
    String languageToSet = "English";

    private eMainPage		 SECOND_DEVICE_eMainPage;
    private eSettingsPage	 FIRST_DEVICE_eSettingsPage;
    private eMainPage		 FIRST_DEVICE_eMainPage;
    private eChatPage		 FIRST_DEVICE_eChatPage;

    private String firstDeviceChat = getConfigProperty(getProperty("firstDevice")+"AccountName");

    @BeforeSuite
    public void beforeSuite(ITestContext testContext)
    {
        if (toSetRetry())
        {
            for (ITestNGMethod method : testContext.getAllTestMethods())
            {
                method.setRetryAnalyzer(new eTestListener());
            }
        }
    }

    private void setUp()
    {
        FIRST_DEVICE_eMainPage		= new eMainPage(eDriverHandler.getDriver(FIRST_DEVICE));
        FIRST_DEVICE_eSettingsPage	= FIRST_DEVICE_eMainPage.openSidePage().openSettings();
    }

    private void setUp_Notification()
    {
        SECOND_DEVICE_eMainPage		= new eMainPage(eDriverHandler.getDriver(SECOND_DEVICE));
        FIRST_DEVICE_eMainPage		= new eMainPage(eDriverHandler.getDriver(FIRST_DEVICE));
        FIRST_DEVICE_eChatPage		= new eChatPage(getDriver(FIRST_DEVICE));
        FIRST_DEVICE_eSettingsPage	= FIRST_DEVICE_eMainPage.openSidePage().openSettings();
    }

    @Test
    public void testHideTextInNotification()
    {
        setUp_Notification();
        FIRST_DEVICE_eSettingsPage.turnOnHideTextInNotification();
        FIRST_DEVICE_eSettingsPage.getBack();
        FIRST_DEVICE_eChatPage = SECOND_DEVICE_eMainPage.openChat(firstDeviceChat);
        String textToSend = RandomStringUtils.random(10,true,true);
        FIRST_DEVICE_eChatPage.sendMessage(textToSend);

        verifyPushNotificationMessage(FIRST_DEVICE_eMainPage.getDriver());
        tearDown_Notification();
    }

    @Test
    public void testLanguage()
    {
        setUp();
        FIRST_DEVICE_eSettingsPage.changeApplicationLanguage(languageToSet);

        Assert.assertEquals(FIRST_DEVICE_eSettingsPage.getApplicationLanguage(), languageToSet);
        tearDown();
    }

    private void tearDown_Notification()
    {
        closeNotifications(FIRST_DEVICE_eMainPage.getDriver());
        FIRST_DEVICE_eMainPage.openSidePage().openSettings();
        FIRST_DEVICE_eSettingsPage.turnOffHideTextInTofication();
        tearDown();
    }

    private void tearDown()
    {
        turnOffDrivers();
    }
}
