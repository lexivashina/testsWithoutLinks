package Tests;

import Pages.eChatPage;
import Pages.eContactInfoPage;
import Pages.eMainPage;
import Pages.eCallPage;
import STUtils.eTestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.*;
import static STUtils.eSTProperties.*;
import static STUtils.eSTProperties.VerificationType.*;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_Verification
{
    private eMainPage			FIRST_DEVICE_eMainPage;
    private eContactInfoPage	FIRST_DEVICE_eContactInfoPage;
    private eCallPage			FIRST_DEVICE_eCallPage;
    private eChatPage			FIRST_DEVICE_eChatPage;

    private eMainPage			SECOND_DEVICE_eMainPage;
    private eContactInfoPage	SECOND_DEVICE_eContactInfoPage;
    private eCallPage			SECOND_DEVICE_eCallPage;
	private eChatPage			SECOND_DEVICE_eChatPage;

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
		FIRST_DEVICE_eMainPage			= new eMainPage(FIRST_DEVICE);
		FIRST_DEVICE_eCallPage			= new eCallPage(FIRST_DEVICE);
		FIRST_DEVICE_eChatPage			= new eChatPage(FIRST_DEVICE);
		FIRST_DEVICE_eContactInfoPage	= new eContactInfoPage(FIRST_DEVICE);
	}

    private void setUpTwoDevices()
    {
    	setUp();
		SECOND_DEVICE_eMainPage			= new eMainPage(SECOND_DEVICE);
		SECOND_DEVICE_eChatPage			= new eChatPage(SECOND_DEVICE);
		SECOND_DEVICE_eCallPage			= new eCallPage(SECOND_DEVICE);
		SECOND_DEVICE_eContactInfoPage	= new eContactInfoPage(SECOND_DEVICE);

		SECOND_DEVICE_eMainPage.openContactInfo(getFirstDeviceProperty("AccountName"));
		SECOND_DEVICE_eContactInfoPage.openVerificationPage();
    }

    @Test
    public void VerificationViaCall_Authorized()
    {
        setUpTwoDevices();
		FIRST_DEVICE_eMainPage.openContactInfo(getSecondDeviceProperty("AccountName"));
		SECOND_DEVICE_eContactInfoPage.callToVerify();
        FIRST_DEVICE_eCallPage.answerVerificationCall();
        FIRST_DEVICE_eCallPage.verify();
        SECOND_DEVICE_eCallPage.verify();
        SECOND_DEVICE_eCallPage.terminateVerificationCall();
        SECOND_DEVICE_eContactInfoPage.getBack();

        SECOND_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_CALL_AUTHORIZED);
        FIRST_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_CALL_AUTHORIZED);

        SECOND_DEVICE_eContactInfoPage.executeStealthCall();
        FIRST_DEVICE_eCallPage.answerStealthCall();
		checkVerificationStatusOnCallAndMessages(VIA_CALL_AUTHORIZED);
		SECOND_DEVICE_eChatPage.terminateCall();

		SECOND_DEVICE_eChatPage.openContactInfo();
        SECOND_DEVICE_eContactInfoPage.dropVerification();
        SECOND_DEVICE_eContactInfoPage.getBack();
		FIRST_DEVICE_eChatPage.openContactInfo();

        SECOND_DEVICE_eContactInfoPage.checkVerificationStatus(NOT_VERIFIED_AUTHORIZED);
        FIRST_DEVICE_eContactInfoPage.checkVerificationStatus(NOT_VERIFIED_AUTHORIZED);

		SECOND_DEVICE_eContactInfoPage.executeStealthCall();
		FIRST_DEVICE_eCallPage.answerStealthCall();
		checkVerificationStatusOnCallAndMessages(NOT_VERIFIED_AUTHORIZED);
		SECOND_DEVICE_eChatPage.terminateCall();
        tearDown();
    }

	@Test
	public void VerificationViaCall_Unauthorized()
	{
		setUpTwoDevices();
		FIRST_DEVICE_eMainPage.openContactInfo(getSecondDeviceProperty("AccountName"));
		SECOND_DEVICE_eContactInfoPage.callToVerify();
		FIRST_DEVICE_eCallPage.answerVerificationCall();
		FIRST_DEVICE_eCallPage.verify();
		SECOND_DEVICE_eCallPage.verify();
		SECOND_DEVICE_eCallPage.terminateVerificationCall();
		SECOND_DEVICE_eContactInfoPage.getBack();

		SECOND_DEVICE_eContactInfoPage.getBack();
		SECOND_DEVICE_eMainPage.openSidePage().unauthorize();
		SECOND_DEVICE_eMainPage.openContactInfo(getFirstDeviceProperty("AccountName"));
		FIRST_DEVICE_eContactInfoPage.getBack();
		FIRST_DEVICE_eMainPage.openSidePage().unauthorize();
		FIRST_DEVICE_eMainPage.openContactInfo(getSecondDeviceProperty("AccountName"));

		SECOND_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_CALL_UNAUTHORIZED);
		FIRST_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_CALL_UNAUTHORIZED);

		SECOND_DEVICE_eContactInfoPage.executeSecuredCall();
		FIRST_DEVICE_eCallPage.answerSecureCall();
		checkVerificationStatusOnCallAndMessages(VIA_CALL_UNAUTHORIZED);
		SECOND_DEVICE_eChatPage.terminateCall();

		SECOND_DEVICE_eChatPage.openContactInfo();
		SECOND_DEVICE_eContactInfoPage.dropVerification();
		SECOND_DEVICE_eContactInfoPage.getBack();
		FIRST_DEVICE_eChatPage.openContactInfo();

		SECOND_DEVICE_eContactInfoPage.checkVerificationStatus(NOT_VERIFIED_UNAUTHORIZED);
		FIRST_DEVICE_eContactInfoPage.checkVerificationStatus(NOT_VERIFIED_UNAUTHORIZED);

		SECOND_DEVICE_eContactInfoPage.executeSecuredCall();
		FIRST_DEVICE_eCallPage.answerSecureCall();
		checkVerificationStatusOnCallAndMessages(NOT_VERIFIED_UNAUTHORIZED);
		SECOND_DEVICE_eChatPage.terminateCall();
		tearDown();
	}

 	@Test
    public void VerificationViaBluetooth()
	{
        setUpTwoDevices();
        SECOND_DEVICE_eContactInfoPage.verifyInPerson();
        FIRST_DEVICE_eCallPage.rejectCall();
        SECOND_DEVICE_eContactInfoPage.verifyInPerson();
        FIRST_DEVICE_eCallPage.answerVerificationCall();
        SECOND_DEVICE_eContactInfoPage.allowBluetoothAuth();
        FIRST_DEVICE_eContactInfoPage.allowBluetoothAuth();

        FIRST_DEVICE_eContactInfoPage.verifyContactVerificationInContactList();
		FIRST_DEVICE_eMainPage.openContactInfo(getSecondDeviceProperty("AccountName"));

        SECOND_DEVICE_eContactInfoPage.getBack();
        SECOND_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_BLUETOOTH_AUTHORIZED);
        FIRST_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_BLUETOOTH_AUTHORIZED);

		SECOND_DEVICE_eContactInfoPage.executeStealthCall();
		FIRST_DEVICE_eCallPage.answerStealthCall();
		checkVerificationStatusOnCallAndMessages(VIA_BLUETOOTH_AUTHORIZED);
		SECOND_DEVICE_eChatPage.terminateCall();

		SECOND_DEVICE_eChatPage.getBackToMainView();
		SECOND_DEVICE_eMainPage.openSidePage().unauthorize();
		SECOND_DEVICE_eMainPage.openContactInfo(getFirstDeviceProperty("AccountName"));
		FIRST_DEVICE_eChatPage.getBackToMainView();
		FIRST_DEVICE_eMainPage.openSidePage().unauthorize();
		FIRST_DEVICE_eMainPage.openContactInfo(getSecondDeviceProperty("AccountName"));

		SECOND_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_BLUETOOTH_UNAUTHORIZED);
		FIRST_DEVICE_eContactInfoPage.checkVerificationStatus(VIA_BLUETOOTH_UNAUTHORIZED);

		SECOND_DEVICE_eContactInfoPage.executeSecuredCall();
		FIRST_DEVICE_eCallPage.answerSecureCall();
		checkVerificationStatusOnCallAndMessages(VIA_BLUETOOTH_UNAUTHORIZED);
		SECOND_DEVICE_eChatPage.terminateCall();
		tearDown();
    }

	private void checkVerificationStatusOnCallAndMessages(VerificationType verificationType)
	{
		FIRST_DEVICE_eCallPage.checkVerificationStatus(verificationType);
		SECOND_DEVICE_eCallPage.checkVerificationStatus(verificationType);

		SECOND_DEVICE_eCallPage.openChat();
		FIRST_DEVICE_eCallPage.openChat();

		SECOND_DEVICE_eChatPage.sendMessage(RandomStringUtils.randomAlphabetic(10));
		FIRST_DEVICE_eChatPage.checkMessageSenderVerificationStatus(verificationType);

		FIRST_DEVICE_eChatPage.sendMessage(RandomStringUtils.randomAlphabetic(10));
		SECOND_DEVICE_eChatPage.checkMessageSenderVerificationStatus(verificationType);
	}

    private void tearDown()
    {
        turnOffDrivers();
    }
}
