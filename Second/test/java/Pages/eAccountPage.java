package Pages;

import STUtils.eDriverHandler;
import STUtils.eSTProperties;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.IExpectedExceptionsHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static Pages.eCommonMethods.Permission.PHOTOS;
import static Pages.eCommonMethods.Permission.СAMERA;
import static STUtils.eDriverHandler.getDeviceName;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eSTProperties.getProperty;
import static STUtils.eSTProperties.getVerificationCode;
import static STUtils.eUtils.await;

public class eAccountPage extends eCommonMethods
{
	By authorizationSettingsButton				= By.xpath("//*[@text='Stealth mode passcode']");
	By digitPassInput							= By.id("com.app.stealthtalk.testers:id/alert_dialog_authorization_pin_edit");
	By newDigitPassInput						= By.id("com.app.stealthtalk.testers:id/fragment_pin_edit");
	By customPassInput							= By.id("com.app.stealthtalk.testers:id/passcode_edit_edit");
	By OKButton									= By.id("android:id/button1");
	By authorizationTimeoutSettings				= By.xpath("//*[@text='Stealth mode timeout']");
	By authorizationOnLockTitle 				= By.id("com.app.stealthtalk.testers:id/settings_account_stealth_mode_disable_on_lock");
	By authorizationOnLockSwitcher				= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/settings_account_stealth_mode_disable_on_lock']/android.view.ViewGroup/*[@resource-id='com.app.stealthtalk.testers:id/settings_item_bool_switch']");
	By hideTextInNotificationsSwitcher			= By.xpath("//android.widget.FrameLayout[@resource-id='com.app.stealthtalk.testers:id/settings_hide_text_in_notification']/android.view.ViewGroup/android.widget.CheckBox[@resource-id='com.app.stealthtalk.testers:id/settings_item_bool_switch']");
	By doneButton								= By.id("com.app.stealthtalk.testers:id/menu_password_set_done");
	By fourDigitPass							= By.xpath("//*[@text='4-Digit numeric code']");
	By sixDigitPass								= By.xpath("//*[@text='6-Digit numeric code']");
	By сustomNumericPasscode					= By.xpath("//*[@text='Custom numeric code']");
	By customAlphanumericPasscode				= By.xpath("//*[@text='Custom alphanumeric code']");
	By getBackButton							= MobileBy.AccessibilityId("Navigate up");
	By forgotPasscodeButton						= By.xpath("//android.widget.Button[@text='FORGOT' or @text='Forgot']");
	By restoreInfoSecretAnswerInput				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_check_question_answer");
	By continueButton							= By.id("android:id/button1");
	By nextButton								= By.xpath("//android.widget.Button[@text='NEXT' or @text='Next']");
	By restoreInfoCheckQuestionButton			= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_check_question_button");
	By restoreInfoSetupQuestionButton			= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_button");
	By restoreInfoSetupEmailButton				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_email_button");
	By emailVerificationCodeRequestLabel		= By.xpath("//android.widget.TextView[@text='Email with a code was sent to:\n" + getProperty("hiddenEmailAccount") + "']");
	By restoreInfoVerificationCodeInput			= By.id("com.app.stealthtalk.testers:id/fragment_approve_code_code");
	By passcodeOptionsButton					= By.id("com.app.stealthtalk.testers:id/fragment_authorization_setup_options");
	By fourDigitPasscodeButton					= By.xpath("//android.widget.TextView[@text='4-Digit numeric code']");
	By enterNewPasscodeLabel					= By.xpath("//android.widget.TextView[@text='Set account passcode']");
	By confirmNewPasscodeLabel					= By.xpath("//android.widget.TextView[@text='Confirm account passcode']");
	By setupButton								= By.xpath("//android.widget.Button[@text='SET' or @text='Set']");
	By confirmButton							= By.xpath("//android.widget.Button[@text='CONFIRM' or @text='Confirm']");
	By changeRestoreInfo						= By.id("com.app.stealthtalk.testers:id/settings_change_restore_information");
	By secretQuestionInputField					= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_question");
	By secretAnswerCheckInputField 				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_check_question_answer");
	By secretAnswerSetupInputField 				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_answer");
	By restoreInfoEmailInputField				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_email_email");
	By deleteStealthTalkAccountButton			= By.id("com.app.stealthtalk.testers:id/settings_account_delete_account");
	By accountDeletionWarning					= By.xpath("//android.widget.TextView[@text = 'Account will be deleted from Eve Talk server including all account data and settings']");
	By subscriptionsSettings					= By.id("com.app.stealthtalk.testers:id/info_button_title");
	By subscriptionsTitle 						= By.id("com.app.stealthtalk.testers:id/fragment_settings_billing_title");
	By subscriptionsInfo 						= By.id("com.app.stealthtalk.testers:id/fragment_settings_billing_label");
	By subscriptionsStatusFromMenu				= By.xpath("//*[@text='Subscription Plan']/following::android.widget.TextView");
	By singInCodeMenu							= By.id("com.app.stealthtalk.testers:id/settings_account_one_time_password");
	By signInCode								= By.id("com.app.stealthtalk.testers:id/fragment_settings_one_time_password_exist_password");
	By takeNewPhotoButton						= By.xpath("//android.widget.TextView[@text='Take new photo']");
	By avatarPreview							= By.id("com.app.stealthtalk.testers:id/fragment_avatar_preview_image_view");
	By doneButtonAvatar							= By.id("com.app.stealthtalk.testers:id/menu_avatar_preview_done");
	By avatar									= By.id("com.app.stealthtalk.testers:id/contact_view_photo");
	By removeAvatar								= By.xpath("//*[@text='Remove photo']");
	By switchAvatarButton						= By.id("com.app.stealthtalk.testers:id/activity_settings_account_photo_action");
	By selectPhotoFromGalleryButton				= By.xpath("//android.widget.TextView[@text='Upload from Gallery']");
	By imageThumbnail							= By.id("com.android.documentsui:id/thumbnail");
	By changeRecoveryInfoMenu					= By.xpath("//*[@text='Change recovery information']");
	By recoveryInfoQuestionMenu 				= By.id("com.app.stealthtalk.testers:id/settings_restore_info_sequrity_question");
	By verificationCodeInput 					= By.id("com.app.stealthtalk.testers:id/fragment_approve_code_code");
	By secretQuestionSelectorMenu				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_question_selector");
	By preparedQuestionInRecoveryInfo			= By.xpath("//*[@text='"+getProperty("secretQuestionPrepared")+"']");
	By customQuestion							= By.xpath("//*[@text='Custom']");
	By restoreInfoEmailMenu						= By.id("com.app.stealthtalk.testers:id/settings_restore_info_email");
	By restoreInfoSetupEmailError 				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_email_error");
	By hiddenEmailInRecoveryInfo				= By.xpath("//*[@text='"+getProperty("hiddenSecondEmail")+"']");
	By deleteAllChatsHistoryMenu				= By.id("com.app.stealthtalk.testers:id/settings_account_delete_all_chats_history");
	By subscriptionState						= By.id("com.app.stealthtalk.testers:id/fragment_settings_billing_title");
	By subscriptionExpiredMessage				= By.id("com.app.stealthtalk.testers:id/fragment_settings_billing_label");

	static String subscriptionExpiredLabel 			= "Subscription expired";
	static String subscriptionActiveLabel			= "Subscription active";
	static String subscriptionExpiredInfoLabel  	= "Subscription expires on\n";
	static String restoreInfoSetupEmailErrorLabel 	= "Email is empty or invalid";
	static String subscriptionExpiredMessageLabel	= "Your PRO subscription expired. You have been downgraded to FREE plan.";

	LocalDateTime expiredTime_Last;
	LocalDateTime expiredTime;
	LocalDateTime activatedTime;
	LocalDateTime prolongTime;

	public eAccountPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}

	public eAccountPage(eDriverHandler.Device device)
	{
		super(getDriver(device));
	}

	public void openAuthorizationSettings(String passType,String passValue)
	{
		scrollDownUntilElementVisible(authorizationSettingsButton);
		click(authorizationSettingsButton);
		switch (passType)
		{
			case "digit":
			{
				waitForLoadShort(enterPasscodeInputField);
				sendKeys(passValue);
				break;
			}
			case "custom":
			{
				input(customPassInput,passValue);
				click(OKButton);
				break;
			}
			default:break;
		}
	}

	public void setAuthorizationTimeout(String authorizationTimeout)
	{
		By authorizationTimeout1 = By.xpath("//*[@text='" + authorizationTimeout + "']");
		click(authorizationTimeout1);
	}

	public void openAuthorizationTimeout()
	{
		scrollDownUntilElementVisible(authorizationTimeoutSettings);
		click(authorizationTimeoutSettings);
	}

	public void turnOffDropAuthorizationOnLock()
	{
		scrollDownUntilElementVisible(authorizationOnLockSwitcher);
		boolean check = Boolean.parseBoolean(getAttribute(authorizationOnLockSwitcher,"checked"));
		if (check) click(authorizationOnLockTitle);
		await(1000);
	}

	public void turnOnDropAuthorizationOnLock()
	{
		scrollDownUntilElementVisible(authorizationOnLockSwitcher);
		boolean check = Boolean.parseBoolean(getAttribute(authorizationOnLockSwitcher,"checked"));
		if (!check) click(authorizationOnLockTitle);
		await(1000);
		getBack();
	}

	public void changePasscode(String passType, String passValue)
	{
		switch (passType)
		{
			case "digit":
			{
				if (passValue.length()==4)
				{
					click(passcodeOptionsButton);
					click(fourDigitPass);
				}
				waitForLoadShort(enterNewPasscodeLabel);
				sendKeys(passValue);
				waitForLoadShort(confirmNewPasscodeLabel);
				sendKeys(passValue);
				break;
			}
			case "custom":
			{
				click(passcodeOptionsButton);

				if (passValue.length() == 8)
				{
					click(сustomNumericPasscode);
				}
				else click(customAlphanumericPasscode);

				waitForLoadShort(enterNewPasscodeLabel);
				input(customPassInput, passValue);
				click(setupButton);
				waitForLoadShort(confirmNewPasscodeLabel);
				input(customPassInput, passValue);
				click(confirmButton);
				waitForAbsenceShort(setupButton);
				break;
			}
			default:
				Assert.fail("Unknown type of passcode");
		}
	}

	public void getBack()
	{
		await(2000);
		click(getBackButton);
	}

	public void openForgotPasscodeView()
	{
		scrollDownUntilElementVisible(authorizationSettingsButton);
		click(authorizationSettingsButton);
		click(forgotPasscodeButton);
		input(restoreInfoSecretAnswerInput, getProperty("secretAnswer"));
		click(nextButton);
		waitForLoadShort(emailVerificationCodeRequestLabel);
		input(restoreInfoVerificationCodeInput, getProperty("verificationCode"));
	}

	public void selectNew4DigitPasscode()
	{
		click(passcodeOptionsButton);
		click(fourDigitPasscodeButton);

		waitForLoadShort(enterNewPasscodeLabel);
		sendKeys(getProperty("new4digitPass"));

		waitForLoadShort(confirmNewPasscodeLabel);
		sendKeys(getProperty("new4digitPass"));
	}

	public void setStandardRestoreInfo()
	{
		scrollDownUntilElementVisible(changeRestoreInfo);
		click(changeRestoreInfo);
		inputPasscode();
		click(setupButton);

		input(secretQuestionInputField, getProperty("secretQuestion"));
		input(secretAnswerCheckInputField, getProperty("secretAnswer"));
		click(nextButton);

		input(restoreInfoEmailInputField, getProperty("emailAccount"));
		click(nextButton);

		input(restoreInfoVerificationCodeInput, getProperty("verificationCode"));
	}

	public void deleteAccount()
	{
		scrollDownUntilElementVisible(deleteStealthTalkAccountButton);
		click(deleteStealthTalkAccountButton);

		inputPasscode();
		waitForLoadShort(accountDeletionWarning);
		click(continueButton);
	}

	public void openSubscriptionMenu()
	{
		scrollDownUntilElementVisible(subscriptionsSettings);
		waitThenClick(subscriptionsSettings);
		inputPasscode();
	}

	private void openSubscriptionMenuFromMainPage()
	{
		if (!isVisible(subscriptionsSettings))
		{
			eMainPage eMainPage = new eMainPage(driver);
			eMainPage.verifyAbsenceQrButton();
			eMainPage.openAccountSettings();
		}

		openSubscriptionMenu();
	}

	private void saveExpirationTime()
	{
		expiredTime_Last = expiredTime;
		expiredTime = getSubscriptionTime();
	}

	public void verifyFreeEnterpriseSubscriptionPlan()
	{
		openSubscriptionMenuFromMainPage();
		Assert.assertEquals(driver.findElement(subscriptionState).getText(), "ENTERPRISE");
		Assert.assertEquals(driver.findElement(subscriptionExpiredMessage).getText(), subscriptionExpiredMessageLabel);
		getBack();
	}

	public void verifyProEnterpriseSubscriptionPlan()
	{
		openSubscriptionMenuFromMainPage();
		Assert.assertEquals(driver.findElement(subscriptionState).getText(), "ENTERPRISE");
		saveExpirationTime();
		getBack();
	}

	public void verifyFreeSubscriptionPlan()
	{
		openSubscriptionMenuFromMainPage();
    	Assert.assertEquals(driver.findElement(subscriptionExpiredMessage).getText(), "Your PRO subscription expired. You have been downgraded to FREE plan.");
	}

	public void verifyProSubscriptionPlan()
	{
		openSubscriptionMenuFromMainPage();
		Assert.assertEquals(driver.findElement(subscriptionState).getText(), "PRO");
		saveExpirationTime();
		getBack();
	}

	public void verifySummingSubscriptions()
	{
		long expirationTime_Last = Date.from(expiredTime_Last.atZone(ZoneId.systemDefault()).toInstant()).getTime();
		long expirationTime = Date.from(expiredTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();
		long currentTime = System.currentTimeMillis();

		long awaitValue_Last = (currentTime < expirationTime_Last) ? expirationTime_Last - currentTime : (expirationTime_Last + 43200000) - currentTime;
		long awaitValue = (currentTime < expirationTime) ? expirationTime - currentTime : (expirationTime + 43200000) - currentTime;

		Assert.assertTrue(awaitValue > awaitValue_Last);
	}

	public void waitUntilSubscriptionExpires()
	{
		long expirationTime = Date.from(expiredTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();
		long currentTime = System.currentTimeMillis();

		long awaitValue = (currentTime < expirationTime) ? expirationTime - currentTime : (expirationTime + 43200000) - currentTime;
		await(awaitValue + 60000);
	}

	public LocalDateTime verifyExpired()
	{
		openSubscriptionMenuFromMainPage();

		expiredTime = getSubscriptionTime();
		verifyExpiredStatus();
		Assert.assertTrue(expiredTime.isBefore(LocalDateTime.now()));
		click(getBackButton);
		return expiredTime;
	}

	public LocalDateTime verifyActive()
	{
		eAccountPage eAccountPage	= new eAccountPage(driver);
		eMainPage eMainPage			= new eMainPage(driver);

		eMainPage.openAccountSettings();
		eAccountPage.openSubscriptionMenu();
		activatedTime = getSubscriptionTime();
		verifyActiveStatus();
		Assert.assertTrue(activatedTime.isAfter(LocalDateTime.now()));
		Assert.assertTrue((LocalDateTime.now().plusMinutes(12).isAfter(activatedTime)));
		Assert.assertTrue(activatedTime.isAfter(expiredTime));
		return activatedTime;
	}

	public void verifyProlong()
	{
		prolongTime = getSubscriptionTime();
		Assert.assertTrue(prolongTime.isAfter(activatedTime));
		click(backButton);
	}

	public void verifyExpiredStatus()
	{
		Assert.assertEquals(getText(subscriptionsTitle), subscriptionExpiredLabel);
		Assert.assertTrue(getText(subscriptionsInfo).contains(subscriptionExpiredInfoLabel));
	}

	public void verifyActiveStatus()
	{
		Assert.assertEquals(getText(subscriptionsTitle), subscriptionActiveLabel);
	}

	public LocalDateTime getSubscriptionTime()
	{
		String dateText = getText(subscriptionsInfo).replace(subscriptionExpiredInfoLabel,"");
		String pattern = "dd MMMM yyyy k:mm aaa";
		SimpleDateFormat inputFormat = new SimpleDateFormat(pattern);
		Date expired = null;
		try { expired = inputFormat.parse(dateText); }
		catch (ParseException e)
		{ e.printStackTrace(); }
		return expired.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public String getSignInCode()
	{
		waitThenClick(singInCodeMenu);
		String code = getText(signInCode);
		click(backButton);
		return code;
	}

	public void setAvatarFromCamera()
	{
		waitForLoad(switchAvatarButton);
		click(switchAvatarButton);
		click(takeNewPhotoButton);
		allowPermission(СAMERA);
		takePhoto();
		waitForLoadShort(avatarPreview);
		click(doneButtonAvatar);
	}

	public void setAvatarFromGallery()
	{
		click(switchAvatarButton);
		click(selectPhotoFromGalleryButton);
		allowPermission(PHOTOS);
		String d = getDeviceName(eDriverHandler.Device.FIRST_DEVICE);
		if (getDeviceName(eDriverHandler.Device.FIRST_DEVICE).equals("GalaxyS5")) {
			By galleryButton = By.xpath("//*[@text='Gallery']/parent::*/parent::*");
			By photo = By.xpath("(//com.sec.samsung.gallery.glview.composeView.PositionControllerBase.ThumbObject)[last()]");

			waitThenClick(galleryButton);
			waitThenClick(photo);
		} else {
			click(imageThumbnail);
		}

		waitForLoadShort(avatarPreview);
		click(doneButtonAvatar);
	}

	public void removeAvatar()
	{
		click(switchAvatarButton);
		click(removeAvatar);
	}

	public void setRecoveryQuestion_PreDefined_Verify()
	{
		eMainPage eMainPage = new eMainPage(driver);
		eMainPage.openAccountSettings();
		openChangeRecoveryInfoMenu();
		click(recoveryInfoQuestionMenu);
		input(secretAnswerCheckInputField, getProperty("secretAnswer"));
		click(restoreInfoCheckQuestionButton);
		inputVerificationCode();
		changeSecretQuestionPrepared();
		verifyQuestion();
	}

	public void setRecoveryQuestion_Custom()
	{
		eMainPage eMainPage = new eMainPage(driver);
		eMainPage.openAccountSettings();
		openChangeRecoveryInfoMenu();
		click(recoveryInfoQuestionMenu);
		input(secretAnswerCheckInputField, getProperty("secretAnswerPrepared"));
		click(restoreInfoCheckQuestionButton);
		inputVerificationCode();
		changeSecretQuestionCustom();
	}

	public void changeSecretQuestionPrepared()
	{
		click(secretQuestionSelectorMenu);
		click(preparedQuestionInRecoveryInfo);
		input(secretAnswerSetupInputField, getProperty("secretAnswerPrepared"));
		click(restoreInfoSetupQuestionButton);
	}

	public void changeSecretQuestionCustom()
	{
		click(secretQuestionSelectorMenu);
		click(customQuestion);
		input(secretQuestionInputField, getProperty("secretQuestion"));
		input(secretAnswerSetupInputField, getProperty("secretAnswer"));
		click(restoreInfoSetupQuestionButton);
	}

	public void openChangeRecoveryInfoMenu()
	{
		click(changeRecoveryInfoMenu);
		inputPasscode();
	}

	public void verifyQuestion()
	{
		openChangeRecoveryInfoMenu();
		verifyAvailability(preparedQuestionInRecoveryInfo);
	}

	public void inputVerificationCode()
	{
		input(verificationCodeInput, getVerificationCode());
	}

	public void changeRecoveryEmail_Verify()
	{
		click(restoreInfoEmailMenu);
		input(secretAnswerCheckInputField, getProperty("secretAnswerPrepared"));
		click(restoreInfoCheckQuestionButton);
		inputVerificationCode();
		verifyIncorrectEmail();
		clearThenInput(restoreInfoEmailInputField, getProperty("secondEmail"));
		click(restoreInfoSetupEmailButton);
		inputVerificationCode();
		verifyEmail();
	}

	public void verifyEmail()
	{
		openChangeRecoveryInfoMenu();
		Assert.assertEquals(getText(hiddenEmailInRecoveryInfo), getProperty("hiddenSecondEmail"));
	}

	public void verifyIncorrectEmail()
	{
		input(restoreInfoEmailInputField, RandomStringUtils.randomAlphanumeric(5));
		click(restoreInfoSetupEmailButton);
		Assert.assertEquals(getText(restoreInfoSetupEmailError), restoreInfoSetupEmailErrorLabel);
	}

	public void setDefaultEmail()
	{
		openChangeRecoveryInfoMenu();
		click(restoreInfoEmailMenu);
		input(secretAnswerCheckInputField, getProperty("secretAnswer"));
		click(restoreInfoCheckQuestionButton);
		inputVerificationCode();
		input(restoreInfoEmailInputField, getProperty("emailAccount"));
		click(restoreInfoSetupEmailButton);
		inputVerificationCode();
		waitForAbsenceShort(verificationCodeInput);
	}

	public void verifyAvatarFromCamera()
	{
		new eMainPage(driver).openAccountSettings();
		setAvatarFromCamera();
		removeAvatar();
	}

	public void verifyAvatarFromGallery()
	{
		setAvatarFromGallery();
		removeAvatar();
	}

	public void clearChatHistory()
	{
		scrollDownUntilElementVisible(deleteAllChatsHistoryMenu);
		click(deleteAllChatsHistoryMenu);
		click(continueButton);
		getBack();
	}
}
