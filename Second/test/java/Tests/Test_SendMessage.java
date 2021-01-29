package Tests;

import Pages.eCallPage;
import Pages.eChatPage;
import Pages.eMainPage;
import STUtils.eDriverHandler;
import STUtils.eTestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Pages.eChatPage.*;
import static STUtils.eDriverHandler.Device.*;
import static STUtils.eDriverHandler.*;
import static STUtils.eSTProperties.getConfigProperty;
import static STUtils.eSTProperties.getProperty;
import static STUtils.eUtils.toSetRetry;
import static STUtils.eUtils.waitTime;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Listeners(eTestListener.class)
public class Test_SendMessage
{
    private eCallPage FIRST_DEVICE_eCallPage;
    private eMainPage FIRST_DEVICE_eMainPage;
    private eMainPage SECOND_DEVICE_eMainPage;
    private eChatPage FIRST_DEVICE_eChatPage;
    private eChatPage SECOND_DEVICE_eChatPage;
    private eCallPage SECOND_DEVICE_eCallPage;
    private eMainPage THIRD_DEVICE_eMainPage;
    private eChatPage THIRD_DEVICE_eChatPage;
    private eCallPage THIRD_DEVICE_eCallPage;

    private String firstDeviceChat = getConfigProperty( getProperty("firstDevice")+"AccountName");
    private String secondDeviceChat = getConfigProperty(getProperty("secondDevice")+"AccountName");
    private String thirdDeviceChat = getConfigProperty(getProperty("thirdDevice")+"AccountName");

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

    private void setUpFirstDevice()
    {
        FIRST_DEVICE_eChatPage = new eMainPage(getDriver(FIRST_DEVICE)).openChat(secondDeviceChat);
        Assert.assertEquals(FIRST_DEVICE_eChatPage.getChatTitle(),secondDeviceChat);
    }

    private void setUpSecondDevice()
    {
        SECOND_DEVICE_eMainPage = new eMainPage(getDriver(SECOND_DEVICE));
        turnOffDriver(SECOND_DEVICE);
    }


    private void setUpTwoDevices()
    {
        FIRST_DEVICE_eMainPage = new eMainPage(eDriverHandler.getDriver(FIRST_DEVICE));
        SECOND_DEVICE_eMainPage = new eMainPage(eDriverHandler.getDriver(SECOND_DEVICE));
        FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openChat(secondDeviceChat);
        SECOND_DEVICE_eChatPage = SECOND_DEVICE_eMainPage.openChat(firstDeviceChat);

        Assert.assertEquals(FIRST_DEVICE_eChatPage.getChatTitle(),secondDeviceChat);
        Assert.assertEquals(SECOND_DEVICE_eChatPage.getChatTitle(),firstDeviceChat);
    }

    private void setUpThreeDevices()
    {
        FIRST_DEVICE_eMainPage	= new eMainPage(getDriver(FIRST_DEVICE));
        FIRST_DEVICE_eCallPage	= new eCallPage(getDriver(FIRST_DEVICE));
        FIRST_DEVICE_eChatPage	= FIRST_DEVICE_eMainPage.openChat(secondDeviceChat);

        SECOND_DEVICE_eMainPage	= new eMainPage(getDriver(SECOND_DEVICE));
        SECOND_DEVICE_eCallPage	= new eCallPage(getDriver(SECOND_DEVICE));
        SECOND_DEVICE_eChatPage	= SECOND_DEVICE_eMainPage.openChat(firstDeviceChat);

        THIRD_DEVICE_eMainPage = new eMainPage(getDriver(THIRD_DEVICE));
        THIRD_DEVICE_eCallPage = new eCallPage(getDriver(THIRD_DEVICE));
        THIRD_DEVICE_eChatPage = THIRD_DEVICE_eMainPage.openChat(firstDeviceChat);
    }

    @Test
    public void sendUsualOfflineMessage()
    {
        setUpFirstDevice();
        setUpSecondDevice();

        String randomMessage = randomAlphanumeric(10);
        FIRST_DEVICE_eChatPage.sendMessage(randomMessage);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSentLabel, messageTypeDefault);

        getDriver(SECOND_DEVICE);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageDeliveredLabel, messageTypeDefault);

        SECOND_DEVICE_eChatPage = new eMainPage(getDriver(SECOND_DEVICE)).openChat(firstDeviceChat);
        Assert.assertEquals(SECOND_DEVICE_eChatPage.getChatTitle(), firstDeviceChat);

        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSeenLabel, messageTypeDefault);
        SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSeenLabel, messageTypeDefault);
        tearDown();
    }

    @Test
    public void sendSecuredOfflineMessage()
    {
        setUpFirstDevice();
        setUpSecondDevice();

        String randomMessage = RandomStringUtils.random(10,true,true);
        FIRST_DEVICE_eChatPage.sendMessage(randomMessage,true);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSentLabel, messageTypeSecure);

        getDriver(SECOND_DEVICE);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageDeliveredLabel, messageTypeSecure);

        SECOND_DEVICE_eChatPage = new eMainPage(getDriver(SECOND_DEVICE)).openChat(firstDeviceChat);
        Assert.assertEquals(SECOND_DEVICE_eChatPage.getChatTitle(), firstDeviceChat);
        SECOND_DEVICE_eChatPage.openLastSecuredMessage();

        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSeenLabel, messageTypeSecure);
        SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSeenLabel, messageTypeSecure);
        tearDown();
    }

    @ DataProvider(name = "TimeValues")
    public Object[][] getTimeValues(ITestContext c)
    {
        Object[][] fullTimeValuesArray = new Object[][] { {"1 minute", "1m" }, {"5 minutes", "5m"} };
        return Arrays.asList(c.getIncludedGroups()).contains("Smoke_Test") ? new Object[][] { fullTimeValuesArray[0] } : fullTimeValuesArray;
    }

    @Test(dataProvider = "TimeValues", groups = {"Full_Test", "Smoke_Test"})
    public void sendTimeOfflineMessage(String timeValue, String shortTimeValue)
    {
        setUpFirstDevice();
        setUpSecondDevice();

        String randomMessage = RandomStringUtils.random(10,true,true) + " " + timeValue;
        FIRST_DEVICE_eChatPage.sendMessage(randomMessage,false,true, timeValue);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSentLabel, messageTypeSelfDestruct, shortTimeValue);

        getDriver(SECOND_DEVICE);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageDeliveredLabel, messageTypeSelfDestruct, shortTimeValue);

        SECOND_DEVICE_eChatPage = new eMainPage(getDriver(SECOND_DEVICE)).openChat(firstDeviceChat);
        SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSeenLabel, messageTypeSelfDestruct, shortTimeValue);

        waitTime(timeValue);
        SECOND_DEVICE_eChatPage.verifyMessageAbsence(randomMessage);
		FIRST_DEVICE_eChatPage.verifyMessageAbsence(randomMessage);

        FIRST_DEVICE_eChatPage.turnOffTimeMode();
        tearDown();
    }

    @Test(dataProvider = "TimeValues", groups = {"Full_Test", "Smoke_Test"})
    public void sendSecuredTimeOfflineMessage(String timeValue, String shortTimeValue)
    {
        setUpFirstDevice();
        setUpSecondDevice();

        String randomMessage = RandomStringUtils.random(10,true,true) + " " + timeValue;
        FIRST_DEVICE_eChatPage.sendMessage(randomMessage,true,true, timeValue);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSentLabel, messageTypeSecureSelfDestruct, shortTimeValue);

        getDriver(SECOND_DEVICE);
        FIRST_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageDeliveredLabel, messageTypeSecureSelfDestruct, shortTimeValue);

        SECOND_DEVICE_eChatPage = new eMainPage(getDriver(SECOND_DEVICE)).openChat(firstDeviceChat);
        Assert.assertEquals(SECOND_DEVICE_eChatPage.getChatTitle(), firstDeviceChat);
        SECOND_DEVICE_eChatPage.openLastSecuredMessage();
        SECOND_DEVICE_eChatPage.verifyLastMessage(randomMessage, messageSeenLabel, messageTypeSecureSelfDestruct, shortTimeValue);

        waitTime(timeValue);
        SECOND_DEVICE_eChatPage.verifyMessageAbsence(randomMessage);
        FIRST_DEVICE_eChatPage.verifyMessageAbsence(randomMessage);

        FIRST_DEVICE_eChatPage.turnOffTimeMode();
        tearDown();
    }

    @Test
    public void sendPhotos()
    {
        setUpTwoDevices();
        SECOND_DEVICE_eChatPage.verifyAttachPhotoCancel();

        SECOND_DEVICE_eChatPage.sendMessage("SEPARATOR");
        SECOND_DEVICE_eChatPage.attachPhotoFromGallery();
        SECOND_DEVICE_eChatPage.verifySentPhoto();
        FIRST_DEVICE_eChatPage.verifyReceivedPicture();

        FIRST_DEVICE_eChatPage.sendMessage("SEPARATOR");
        FIRST_DEVICE_eChatPage.attachPhotoFromCamera();
        FIRST_DEVICE_eChatPage.verifySentPhoto();
        SECOND_DEVICE_eChatPage.verifyReceivedPicture();
        tearDown();
    }

    @Test
    public void deleteMessage()
    {
        setUpFirstDevice();
        FIRST_DEVICE_eChatPage.sendMessage(RandomStringUtils.random(5,true,true));
        FIRST_DEVICE_eChatPage.sendMessage(RandomStringUtils.random(5,true,true));
        String message = FIRST_DEVICE_eChatPage.deleteMessage();

        Assert.assertNotEquals(message, FIRST_DEVICE_eChatPage.getLastSentMessage());
        tearDown();
    }

    @Test
    public void forwardUsualMessage()
    {
        setUpTwoDevices();
		String message = randomAlphanumeric(5);
        FIRST_DEVICE_eChatPage.sendMessage(message, false);
        SECOND_DEVICE_eChatPage.forwardMessageToThirdDevice(false);
        SECOND_DEVICE_eChatPage.verifyLastMessage(message);
        SECOND_DEVICE_eChatPage.getBack();

		message = randomAlphanumeric(5);
        FIRST_DEVICE_eChatPage.sendMessage(message, false);
        SECOND_DEVICE_eMainPage.openSidePage().authorize();
        SECOND_DEVICE_eMainPage.openChat(firstDeviceChat);
        SECOND_DEVICE_eChatPage.forwardMessageToThirdDevice(true);
		SECOND_DEVICE_eChatPage.verifyLastSecureMessage(message);
        tearDown();
    }

    @Test
    public void forwardSecureMessage()
    {
        setUpTwoDevices();
		String message = randomAlphanumeric(5);
		FIRST_DEVICE_eChatPage.sendMessage(message, true);
        SECOND_DEVICE_eChatPage.verifySecureMessageNotAuthorized();
        SECOND_DEVICE_eChatPage.forwardMessageToThirdDevice(false);
		SECOND_DEVICE_eChatPage.verifyLastMessage(message);
		SECOND_DEVICE_eChatPage.getBack();

		message = randomAlphanumeric(5);
        FIRST_DEVICE_eChatPage.sendMessage(message,false); //Fix for second secure message
        SECOND_DEVICE_eMainPage.openChat(firstDeviceChat);
        SECOND_DEVICE_eChatPage.forwardMessageToThirdDevice(true);
		SECOND_DEVICE_eChatPage.verifyLastSecureMessage(message);
        tearDown();
    }

    @Test
    public void CopyMessage()
    {
        setUpFirstDevice();
        String message = randomAlphanumeric(5);
        FIRST_DEVICE_eChatPage.sendMessage(message);
        FIRST_DEVICE_eChatPage.verifyCopyMessage(message);
        tearDown();
    }

    @Test
    public void verifyMessageWhileInAnotherChat()
    {
		String firstMessage		= RandomStringUtils.random(5,true,true);
		String secondMessage	= RandomStringUtils.random(5,true,true);

        setUpTwoDevices();
        SECOND_DEVICE_eChatPage.getBack().openChat(thirdDeviceChat);
        SECOND_DEVICE_eChatPage.sendMessage(firstMessage);
        FIRST_DEVICE_eChatPage.sendMessage(secondMessage);
        SECOND_DEVICE_eChatPage.verifyMessageAvailability(firstMessage);
        SECOND_DEVICE_eChatPage.verifyNewMessageThroughNotification(secondMessage);
        SECOND_DEVICE_eChatPage.verifyMessageAbsence(firstMessage);
        SECOND_DEVICE_eChatPage.verifyMessageAvailability(secondMessage);

        tearDown();
    }

    private void tearDown()
    {
        turnOffDrivers();
    }
}
