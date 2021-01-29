package Tests;

import Pages.eCallPage;
import Pages.eChatPage;
import Pages.eContactInfoPage;
import Pages.eMainPage;
import STUtils.eDriverHandler;
import STUtils.eTestListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.Device.*;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eDriverHandler.turnOffDrivers;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_Call
{
    private eChatPage FIRST_DEVICE_eChatPage;
    private eCallPage FIRST_DEVICE_eCallPage;
    private eMainPage FIRST_DEVICE_eMainPage;

    private eMainPage SECOND_DEVICE_eMainPage;
    private eCallPage SECOND_DEVICE_eCallPage;
    private eChatPage SECOND_DEVICE_eChatPage;

    private eMainPage THIRD_DEVICE_eMainPage;
    private eCallPage THIRD_DEVICE_eCallPage;

    private eContactInfoPage contactInfoPage;

    private String firstDeviceChat = getConfigProperty(getProperty("firstDevice")+"AccountName");
    private String secondDeviceChat = getConfigProperty(getProperty("secondDevice")+"AccountName");

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

    private void setUpTwoDevices()
    {
        FIRST_DEVICE_eMainPage = new eMainPage(FIRST_DEVICE);
        FIRST_DEVICE_eCallPage = new eCallPage(FIRST_DEVICE);

        SECOND_DEVICE_eMainPage = new eMainPage(SECOND_DEVICE);
        SECOND_DEVICE_eCallPage = new eCallPage(SECOND_DEVICE);
    }

    private void setUpOfflineCall()
    {
        setUpTwoDevices();
        FIRST_DEVICE_eChatPage	= FIRST_DEVICE_eMainPage.openChat(secondDeviceChat);
        SECOND_DEVICE_eChatPage	= SECOND_DEVICE_eMainPage.openChat(firstDeviceChat);
    }

    private void setUpThreeDevices()
    {
        setUpTwoDevices();
        THIRD_DEVICE_eMainPage = new eMainPage(eDriverHandler.getDriver(THIRD_DEVICE));
    }

    @org.junit.Test
    public void secureCall()
    {
        setUpTwoDevices();
        FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openChat(getSecondDeviceProperty("AccountName"));
        FIRST_DEVICE_eChatPage.executeUsualCall();

        Assert.assertEquals(getSecondDeviceProperty("AccountName"), FIRST_DEVICE_eCallPage.getProfileNameInOpenCallView());

        String callStatus1 = SECOND_DEVICE_eCallPage.getCallStatus();
        String callStatus2 = FIRST_DEVICE_eCallPage.getCallStatus();
        Assert.assertEquals(callStatus1,"Is calling...");
        Assert.assertEquals(callStatus2,"Calling...");
        SECOND_DEVICE_eCallPage.rejectCall();

		FIRST_DEVICE_eChatPage.executeUsualCall();
        SECOND_DEVICE_eCallPage.answerSecureCall();

        Assert.assertNotEquals(callStatus1, FIRST_DEVICE_eCallPage.getCallStatus());
        Assert.assertNotEquals(callStatus2, SECOND_DEVICE_eCallPage.getCallStatus());

        FIRST_DEVICE_eCallPage.verifySound();
        SECOND_DEVICE_eCallPage.verifySound();

        FIRST_DEVICE_eCallPage.cancelCall();
        Assert.assertEquals(getSecondDeviceProperty("AccountName"), FIRST_DEVICE_eChatPage.getChatTitle());
        tearDown();
    }

    @org.junit.Test
    public void stealthCall()
    {
        setUpTwoDevices();
        FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openChat(getSecondDeviceProperty("AccountName"));
        FIRST_DEVICE_eChatPage.executeStealthCall();

        Assert.assertEquals(FIRST_DEVICE_eCallPage.getProfileNameInOpenCallView(), getSecondDeviceProperty("AccountName"));

        String сallStatus1 = SECOND_DEVICE_eCallPage.getCallStatus();
        String сallStatus2 = FIRST_DEVICE_eCallPage.getCallStatus();
        Assert.assertEquals(сallStatus1,"Is calling...");
        Assert.assertEquals(сallStatus2,"Calling...");

        FIRST_DEVICE_eCallPage.verifySecureCall();
        SECOND_DEVICE_eCallPage.verifySecureCall();

		SECOND_DEVICE_eCallPage.rejectCall();
		FIRST_DEVICE_eChatPage.executeStealthCall();

        SECOND_DEVICE_eCallPage.answerStealthCall();

        Assert.assertNotEquals(сallStatus1, FIRST_DEVICE_eCallPage.getCallStatus());
        Assert.assertNotEquals(сallStatus2, SECOND_DEVICE_eCallPage.getCallStatus());

        FIRST_DEVICE_eCallPage.verifySound();
        SECOND_DEVICE_eCallPage.verifySound();

        FIRST_DEVICE_eCallPage.verifySecureCall();
        SECOND_DEVICE_eCallPage.verifySecureCall();

        FIRST_DEVICE_eCallPage.cancelCall();
        Assert.assertEquals(getSecondDeviceProperty("AccountName"), FIRST_DEVICE_eChatPage.getChatTitle());
        tearDown();
    }

    @org.junit.Test
    public void secureCallFromContextMenu()
    {
        setUpTwoDevices();
        FIRST_DEVICE_eMainPage.executeUsualCall_FromContextMenu(getSecondDeviceProperty("AccountName"));

        Assert.assertEquals(getSecondDeviceProperty("AccountName"), FIRST_DEVICE_eCallPage.getProfileNameInOpenCallView());

        String callStatus1 = SECOND_DEVICE_eCallPage.getCallStatus();
        String callStatus2 = FIRST_DEVICE_eCallPage.getCallStatus();
        Assert.assertEquals(callStatus1,"Is calling...");
        Assert.assertEquals(callStatus2,"Calling...");

        SECOND_DEVICE_eCallPage.answerSecureCall();

        Assert.assertNotEquals(callStatus1, FIRST_DEVICE_eCallPage.getCallStatus());
        Assert.assertNotEquals(callStatus2, SECOND_DEVICE_eCallPage.getCallStatus());

        FIRST_DEVICE_eCallPage.verifySound();
        SECOND_DEVICE_eCallPage.verifySound();

        tearDownFromContextMenu();
    }


  /*  @org.junit.Test
    public void attachMemberToCall()
    {
        setUpThreeDevices();
        THIRD_DEVICE_eMainPage = new eMainPage(getDriver(THIRD_DEVICE));
        THIRD_DEVICE_eCallPage = THIRD_DEVICE_eMainPage.getCallPage();

        FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openChat(getSecondDeviceProperty("AccountName"));
        FIRST_DEVICE_eChatPage.executeUsualCall();
        SECOND_DEVICE_eCallPage.answerSecureCall();

        FIRST_DEVICE_eCallPage.attachMember();

        THIRD_DEVICE_eCallPage.answerSecureCall();

        FIRST_DEVICE_eCallPage.verifyMemberAttached();

        FIRST_DEVICE_eCallPage.verifySoundInGroup();
        SECOND_DEVICE_eCallPage.verifySoundInGroup();
        THIRD_DEVICE_eCallPage.verifySoundInGroup();

        FIRST_DEVICE_eCallPage.cancelCall();
        tearDown();
    }

   */

    @org.junit.Test
    public void openChatFromCall()
    {
        setUpTwoDevices();
        FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openChat(getSecondDeviceProperty("AccountName"));
        FIRST_DEVICE_eChatPage.executeUsualCall();
        SECOND_DEVICE_eCallPage.answerSecureCall();

        eChatPage chatPage = FIRST_DEVICE_eCallPage.openChat();
        chatPage.verifyChatOpenedFromCall();

        SECOND_DEVICE_eCallPage.cancelCall();
        tearDown();
    }

    @org.junit.Test
    public void testCallButtons()
    {
        setUpTwoDevices();
        FIRST_DEVICE_eChatPage = FIRST_DEVICE_eMainPage.openChat(getSecondDeviceProperty("AccountName"));
        FIRST_DEVICE_eChatPage.executeUsualCall();

        SECOND_DEVICE_eCallPage.answerSecureCall();

        FIRST_DEVICE_eCallPage.verifyMuteButton();
        FIRST_DEVICE_eCallPage.verifySpeakerButton();

        FIRST_DEVICE_eCallPage.cancelCall();
        Assert.assertEquals(getSecondDeviceProperty("AccountName"), FIRST_DEVICE_eChatPage.getChatTitle());
        tearDown();
    }

    @org.junit.Test
    public void offlineCall()
    {
        setUpOfflineCall();

        SECOND_DEVICE_eMainPage.closeStealthTalkApp(true);
        FIRST_DEVICE_eChatPage.executeUsualCall();
        SECOND_DEVICE_eCallPage.answerSecureCall();

        Assert.assertEquals(firstDeviceChat, SECOND_DEVICE_eCallPage.getProfileNameInOpenCallView());
        Assert.assertEquals(secondDeviceChat, FIRST_DEVICE_eCallPage.getProfileNameInOpenCallView());

        FIRST_DEVICE_eCallPage.cancelCall();
        tearDown();
    }

   	private void tearDown()
	{
		turnOffDrivers();
	}

    private void tearDownFromContextMenu()
    {
        FIRST_DEVICE_eCallPage.cancelCall();
        turnOffDrivers();
    }
}
