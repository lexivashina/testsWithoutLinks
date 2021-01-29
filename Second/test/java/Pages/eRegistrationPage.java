package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.Date;

import static Pages.eCommonMethods.Permission.CONTACTS;
import static STUtils.eDriverHandler.Device;
import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.await;
import static STUtils.eUtils.verifyBruteForceTime;

public class eRegistrationPage extends eCommonMethods
{
    By phoneNumberInput 					= By.id("com.app.stealthtalk.testers:id/registration_phone_number_phone");
    By verificationCodeInput 				= By.id("com.app.stealthtalk.testers:id/fragment_approve_code_code");
    By enterEmailVerificationCodeLabel 		= By.id("com.app.stealthtalk.testers:id/fragment_approve_code_title");
    By emailVerificationCodeInput 			= By.id("com.app.stealthtalk.testers:id/step_subview_single_edit_edit_text");
    By passcodeInput 						= By.id("com.app.stealthtalk.testers:id/fragment_pin_edit");
    By allowButton 							= By.id("com.android.packageinstaller:id/permission_allow_button");
    By permissionAllowButtonOrAdressBook 	= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/fragment_address_book' or @resource-id='com.android.packageinstaller:id/permission_allow_button']");
    By countrySelector 						= By.id("com.app.stealthtalk.testers:id/registration_phone_number_country");
    By search 								= MobileBy.AccessibilityId("Search");
    By searchInput 							= By.id("com.app.stealthtalk.testers:id/search_src_text");
    By changePhone 							= By.xpath("//android.widget.Button[@text='CHANGE PHONE']");
    By secretAnswerInput					= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_check_question_answer");
    By select4DigitCode 					= MobileBy.xpath("//*[@text='4-Digit numeric code']");
    By confirmPasscodeLabel					= MobileBy.xpath("//*[@text='Confirm account passcode']");
    By phoneNumberError 					= MobileBy.id("com.app.stealthtalk.testers:id/registration_phone_number_error");
    By errorMessage 						= MobileBy.id("com.app.stealthtalk.testers:id/step_subview_single_edit_error");
    By passcodeError 						= MobileBy.id("com.app.stealthtalk.testers:id/fragment_authorization_setup_confirm_error");
    By backButton 							= MobileBy.AccessibilityId("Navigate up");
    By skipSetRestoreInfoButton				= By.id("com.app.stealthtalk.testers:id/menu_restore_info_skip");
	By setSecretQuestionInputField			= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_question");
	By setSecretAnswerInputField			= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_answer");
	By setEmailInputField					= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_email_email");
	By selectCountryLabel					= By.xpath("//*[@text='Select Country']");
	By phoneNumberSubtitle					= By.xpath("//*[@text='Enter your phone number. Please check the country code is correct']");
	By verificationCodeSubtitle				= By.xpath("//*[contains(@text,'SMS with a code was sent to:')]"); //TODO Change to SMS once bug #149 will fixed
	By secretAnswerSubtitle					= By.xpath("//*[contains(@text, 'Answer your security question:')]");
	By customAlphanumericPasscodeButton		= By.xpath("//*[@text = 'Custom alphanumeric code']");
	By customNumericPasscodeButton			= By.xpath("//*[@text = 'Custom numeric code']");
	By enterNewPasscodeLabel				= By.xpath("//*[@text = 'Set account passcode']");
	By setSecurityQuestionLabel				= By.xpath("//*[@text = 'Set security question']");
	By stepUpButton							= By.xpath("//*[@text = 'STEP UP']");
	By editEmailButton						= By.xpath("//*[@text = 'EDIT EMAIL']");
	By bruteForceBanMessage					= By.xpath("//*[contains(@text,'You made too many unsuccessful registration attempts. Next available attempts: ')]");
	By okButton								= By.xpath("//*[@text='OK' or @text='Ok']");
	By contactsAccessRequest				= By.xpath("//*[@text='Allow StealthTalk to access your contacts?']");
	By nextButton							= By.xpath("//android.widget.Button[@text='NEXT' or @text='Next']");
	By nextButtonOnBoarding					= By.xpath("//android.view.View/android.widget.Button[resource-id='NEXT' or @resource-id='next']");
	By continueSpecialOfferButton			= By.xpath("//android.widget.Button[@content-desc='Register' or @text='Register']");
	By wipeButton							= By.xpath("//android.widget.Button[@text='WIPE' or @content-desc='WIPE ']");
	By verificationCodeError				= By.id("com.app.stealthtalk.testers:id/fragment_approve_code_error");
	By secretAnswerError					= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_check_question_error");
	By approveCodeTitle  					= By.id("com.app.stealthtalk.testers:id/fragment_approve_code_title");
	By addressBook							= By.id("com.app.stealthtalk.testers:id/fragment_address_book");
	By continueButton						= By.xpath("//*[@text='CONTINUE' or @text='Continue']");
	By passcodeRequest						= By.id("com.app.stealthtalk.testers:id/fragment_authorization_setup_title");
	By enterYourPasscodeLabel   			= By.id("com.app.stealthtalk.testers:id/fragment_authorization_setup_confirm_title");
	By passcodeOptions						= By.id("com.app.stealthtalk.testers:id/fragment_authorization_setup_options");
	By onBoardingImage						= By.xpath("//android.widget.Image[@text='phone__frame' or @content-desc='phone__frame']");
	By setupButton							= By.xpath("//android.widget.Button[@text='SET' or @text='Set']");
	By recoveryInformationTitle				= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_title");
	By secretQuestionSelectorMenu			= By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_question_question_selector");
	By recoveryEmailInputField			    = By.id("com.app.stealthtalk.testers:id/fragment_restore_info_setup_email_email");
	By preparedQuestion						= By.xpath("//*[text='"+getProperty("secretAnswerPrepared")+"']");
	By customQuestion						= By.xpath("//*[@text='Custom']");



	static String bruteForceBanMessage_verificationCodeView		= "Too many failed attempts. Next Try available on ";
	static String bruteForceBanMessageErrorLabel				= "Too many unsuccessful registration attempts. Try again after ";

	public eRegistrationPage(AndroidDriver driver)
    {
        super(driver);
    }

	public eRegistrationPage(Device device)
	{
		super(getDriver(device));
	}

    public eRegistrationPage pressSkipButton()
    {
    	skipSpecialOfferIfPossible();

		waitForLoad(nextButtonOnBoarding);
		for (int i = 0; i < 4; i++)
			click(nextButtonOnBoarding);
        return this;
    }

    private void skipSpecialOfferIfPossible()
	{
		waitForAnyElementLong(nextButtonOnBoarding, continueSpecialOfferButton);
		clickIfPossible(continueSpecialOfferButton);
	}

    public eRegistrationPage inputPhoneNumber(String phoneNumber)
    {
        input(phoneNumberInput, phoneNumber);
		click(nextButton);
		return this;
    }

    public eRegistrationPage inputVerificationCode()
    {
        input(verificationCodeInput, getVerificationCode());
        return this;
    }

	public void inputDefaultPasscode()
	{
		waitForLoadShort(enterYourPasscodeLabel);
		sendKeys(getProperty("default4digitPass"));
	}

    public eMainPage inputRestoreInfo()
    {

        waitForLoadShort(secretAnswerInput);
        input(secretAnswerInput, getProperty("secretAnswer"));
        click(nextButton);
        input(verificationCodeInput, getVerificationCode());
        waitForAbsenceShort(verificationCodeInput);
        return new eMainPage(driver);
    }

	public eMainPage inputRestoreInfoForPreDefined()
	{
		waitForLoadShort(secretAnswerInput);

		verifyAvailability(By.xpath("//*[contains(@text, '"+getProperty("secretQuestionPrepared")+"')]"));
		input(secretAnswerInput, getProperty("secretAnswerPrepared"));
		click(nextButton);
		Assert.assertTrue(getText(enterEmailVerificationCodeLabel).contains(getProperty("hiddenSecondEmail")));
		input(verificationCodeInput, getVerificationCode());
		waitForAbsenceShort(verificationCodeInput);
		return new eMainPage(driver);
	}

    //TODO REFACTORING
    public void allowPermission()
    {
        waitForLoad(permissionAllowButtonOrAdressBook);
        if (returnList(allowButton).size()==1) click(allowButton);
    }

    public eRegistrationPage selectCountryCode(String country)
    {
        click(countrySelector);
        click(search);
        input(searchInput, country);
        click(MobileBy.xpath("//android.widget.TextView[@text='"+country+"']"));
        return this;
    }

    public void setStandardPassCode()
    {
    	click(passcodeOptions);
        click(select4DigitCode);
        waitForLoadShort(enterNewPasscodeLabel);
        sendKeys(getDefaultPasscode());
        waitForLoadShort(confirmPasscodeLabel);
		sendKeys(getDefaultPasscode());
    }

    public void verifyInvalidPhoneInput()
    {
    	String country = "Canada";

        waitThenClick(nextButton);
		Assert.assertEquals(waitThenGetText(phoneNumberError), "Please, select country");

		selectCountryCode(country);
		click(nextButton);
		Assert.assertEquals(waitThenGetText(phoneNumberError), "Phone number empty or invalid");

        input(phoneNumberInput, "1");
        click(nextButton);
		Assert.assertEquals(waitThenGetText(phoneNumberError), "Phone number empty or invalid");

        clear(phoneNumberInput);
        inputPhoneNumber(getProperty("phoneNumberForSecretQuestionTesting"));
    }

    public void verifyIncorrectVerificationCode()
    {
        input(verificationCodeInput, "000000");
        Assert.assertEquals(waitThenGetText(verificationCodeError), "Code is invalid");

        clear(verificationCodeInput);
        inputVerificationCode();
    }

    public void verifyIncorrectPasscode()
	{
		waitForLoadShort(passcodeRequest);
        sendKeys("0000");
        Assert.assertEquals(waitThenGetText(passcodeError), "Passcode is invalid");
        clearPasscode();
        sendKeys(getProperty("default4digitPass"));
    }

    public void verifyIncorrectSecretAnswer()
    {
        click(nextButton);
        Assert.assertEquals(waitThenGetText(secretAnswerError), "Answer is not valid");

        clearThenInput(secretAnswerInput, "qwerty");
        click(nextButton);
        Assert.assertEquals(waitThenGetText(secretAnswerError), "Answer is not valid");

        clearThenInput(secretAnswerInput, getProperty("secretAnswer"));
        click(nextButton);
    }

    public void verifyIncorrectApproveCode()
    {
    	waitForLoad(enterYourPasscodeLabel);
		sendKeys("1111");
		waitForLoad(confirmPasscodeLabel);
		sendKeys("1111");
    }

	public void verifyNavigationOnCountriesView()
	{
		click(countrySelector);
		waitForLoadShort(search);
		click(backButton);
		waitForLoadShort(phoneNumberSubtitle);
		click(countrySelector);
		waitForLoadShort(search);
		pressAndroidBackButton();
		waitForLoadShort(phoneNumberSubtitle);
	}

	public void verifyNavigationOnVerificationCodeView(String phoneNumber)
	{
		waitForLoadShort(verificationCodeSubtitle);
		driver.hideKeyboard();
		pressAndroidBackButton();
		waitForLoadShort(phoneNumberSubtitle);
		Assert.assertEquals(getText(phoneNumberInput), phoneNumber);
		click(nextButton);

		inputVerificationCode();
	}

	public void verifyNavigationOnRestoreInfoView()
	{
		waitForLoadShort(secretAnswerSubtitle);
		driver.hideKeyboard();
		pressAndroidBackButton();
		Assert.assertTrue(isPresent(secretAnswerSubtitle));

		input(secretAnswerInput, getProperty("secretAnswer"));
		click(nextButton);

		waitForLoadShort(enterEmailVerificationCodeLabel);
		driver.hideKeyboard();
		pressAndroidBackButton();
		Assert.assertTrue(isPresent(enterEmailVerificationCodeLabel));

		input(verificationCodeInput, getVerificationCode());
	}

	public void verifyNavigationOnSetPasscodeView()
	{
		waitForLoadShort(enterNewPasscodeLabel);
		driver.hideKeyboard();
		pressAndroidBackButton();

		click(passcodeOptions);
		click(select4DigitCode);
		waitForLoadShort(enterNewPasscodeLabel);
		sendKeys(getDefaultPasscode());
		waitForLoadShort(confirmPasscodeLabel);
		click(backButton);

		waitForLoadShort(enterNewPasscodeLabel);
		sendKeys(getDefaultPasscode());
		waitForLoadShort(confirmPasscodeLabel);
		sendKeys(getDefaultPasscode());
	}

	public void verifyNavigationOnSetRestoreInfoView()
	{
		click(setupButton);
		waitForLoadShort(setSecretQuestionInputField);
		click(backButton);
		waitForLoadShort(setupButton);
		click(setupButton);

		setCustomTypeQuestion();

		input(setSecretQuestionInputField, getProperty("secretQuestion"));
		input(setSecretAnswerInputField, getProperty("secretAnswer"));
		click(nextButton);

		waitForLoadShort(setEmailInputField);
		click(backButton);

		waitForLoadShort(setSecretQuestionInputField);
		Assert.assertEquals(getText(setSecretQuestionInputField), getProperty("secretQuestion"));
		Assert.assertEquals(getText(setSecretAnswerInputField), getProperty("secretAnswer"));
		click(nextButton);

		input(setEmailInputField, getProperty("emailAccount"));
		click(nextButton);

		waitForLoadShort(enterEmailVerificationCodeLabel);
		click(backButton);
		Assert.assertEquals(getText(setEmailInputField), getProperty("emailAccount"));
		click(nextButton);
		input(verificationCodeInput, getVerificationCode());
		waitForAbsenceShort(verificationCodeInput);
	}

	public void setRecoveryInfo()
	{
		click(setupButton);
		setCustomTypeQuestion();
		waitForLoadShort(setSecretQuestionInputField);
		input(setSecretQuestionInputField, getProperty("secretQuestion"));
		input(setSecretAnswerInputField, getProperty("secretAnswer"));
		click(nextButton);
		waitForLoadShort(setEmailInputField);
		input(setEmailInputField, getProperty("emailAccount"));
		click(nextButton);
		waitForLoadShort(enterEmailVerificationCodeLabel);
		input(verificationCodeInput, getVerificationCode());
		waitForAbsenceShort(verificationCodeInput);
	}

	public void setCustomTypeQuestion()
	{
		waitForLoad(recoveryInformationTitle);
		waitThenClick(secretQuestionSelectorMenu);
		waitThenClick(customQuestion);
	}

	public void skipCongratulationsViewIfPossible()
	{
		By thankYouButton = By.xpath("//android.widget.Button[@text='Thank you' or @content-desc='Thank you']");

		waitForAnyElementLong(thankYouButton, allowButton, setupButton);
		clickIfPossible(thankYouButton);
	}
	public void setSecretQuestionSelectorMenu()
	{
		waitThenClick(setupButton);
		waitThenClick(secretQuestionSelectorMenu);
		waitThenClick(customQuestion);
		input(setSecretQuestionInputField, getProperty("secretQuestion"));
		input(setSecretAnswerInputField, getProperty("secretAnswer"));
		waitThenClick(nextButton);
		input(setEmailInputField, getProperty("emailAccount"));
		waitThenClick(nextButton);
		waitForLoadShort(approveCodeTitle);
		sendKeys("111111");

	}


	public void verifyNavigationOnPhoneNumberView(String phoneNumber, String country)
	{
		pressSkipButton();
		input(phoneNumberInput, phoneNumber);

		driver.hideKeyboard();
		pressAndroidBackButton();
		Assert.assertTrue(isPresent(phoneNumberSubtitle));

		verifyNavigationOnCountriesView();
		Assert.assertEquals(getText(phoneNumberInput), phoneNumber);

		selectCountryCode(country);
		click(nextButton);
	}

	public void verifyBruteForceBan_VerificationCode(String phoneNumber, long minutes)
	{
		invokeBanOnVerificationCode();
		Date expectedBanDate = verifyErrorMessage_VerificationCodeView(minutes);
		verifyErrorMessage_PhoneNumberView(phoneNumber, expectedBanDate);
	}

	public Date verifyErrorMessage_VerificationCodeView(long minutes)
	{
		long currentTime = new Date().getTime();
		Date expectedBanDate = new Date (currentTime + minutes*60000);
		String bruteForceBanMessageText = getText(verificationCodeError);
		String  actualBanDateString = bruteForceBanMessageText.replaceAll(bruteForceBanMessage_verificationCodeView, "");

		verifyBruteForceTime(actualBanDateString, expectedBanDate);

		clear(verificationCodeInput);
		input(verificationCodeInput, getProperty("invalidVerificationCode"));
		Assert.assertEquals(getText(verificationCodeError), bruteForceBanMessageText);
		return expectedBanDate;
	}

	public void verifyErrorMessage_PhoneNumberView(String phoneNumber, Date expectedBanDate)
	{
		driver.hideKeyboard();
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

		clear(phoneNumberInput);
		inputPhoneNumber(phoneNumber);
		String bruteForceBanMessageText = getText(phoneNumberError);
		String actualBanDateString = bruteForceBanMessageText.replaceAll(bruteForceBanMessageErrorLabel, "");

		verifyBruteForceTime(actualBanDateString, expectedBanDate);

		waitBanAndClickNext(expectedBanDate);
	}

	private void invokeBanOnVerificationCode() {
		int attempt = 0;
		while(attempt++ < 3)
		{
			input(verificationCodeInput, getProperty("invalidVerificationCode"));
			waitForLoadShort(verificationCodeError);
			clear(verificationCodeInput);
		}
		input(verificationCodeInput, getProperty("invalidVerificationCode"));
	}

	public void verifyBruteForceBanOnPasscode(String phoneNumber, long minutes)
	{
		invokeBanOnPasscode();

		long currentTime = new Date().getTime();
		Date expectedBanDate = new Date (currentTime + minutes*60000);
		String bruteForceBanMessageText = getText(phoneNumberError);
		String actualBanDateString = bruteForceBanMessageText.replaceAll(bruteForceBanMessageErrorLabel, "");

		verifyBruteForceTime(actualBanDateString, expectedBanDate);

		waitBanAndClickNext(expectedBanDate);

	}

	private void invokeBanOnPasscode() {

		int attempt = 0;
		sendKeys(getProperty("new4digitPass"));


		while(attempt++ < 2)
		{
			waitForLoadShort(passcodeError);
			clearPasscode();
			sendKeys(getProperty("new4digitPass"));
		}
		clickIfPossible(nextButton);
	}

	public void verifyBruteForceBanOnSecretQuestion(long minutes)
	{
		invokeBanOnSecretQuestion();

		long currentTime = new Date().getTime();
		Date expectedBanDate = new Date (currentTime + minutes*60000);
		String bruteForceBanMessageText = getText(phoneNumberError);
		String actualBanDateString = bruteForceBanMessageText.replaceAll(bruteForceBanMessageErrorLabel, "");

		verifyBruteForceTime(actualBanDateString, expectedBanDate);

		waitBanAndClickNext(expectedBanDate);
	}

	private void invokeBanOnSecretQuestion() {
		int attempt = 0;
		waitForLoad(passcodeRequest);
		sendKeys(getDefaultPasscode());

		//input(secretAnswerInput, getProperty("invalidSecretAnswer"));
		click(nextButton);
		while(attempt++ < 2)
		{
			waitForLoadShort(secretAnswerError);
			clickIfPossible(nextButton);
		}
		clickIfPossible(nextButton);
	}

	public void verifyBruteForceBanOnEmailVerificationCode(long minutes)
	{
		invokeBanOnEmailVerificationCode();

		long currentTime = new Date().getTime();
		Date expectedBanDate = new Date (currentTime + minutes*60000);
		String bruteForceBanDateString = getBruteForceBanDateFromErrorLabel();

		verifyBruteForceTime(bruteForceBanDateString, expectedBanDate);

		waitBanAndClickNext(expectedBanDate);
	}

	private void waitBanAndClickNext(Date expectedBanDate) {
		long expectedBanTime = expectedBanDate.getTime();
		long current_Time = new Date().getTime();
		await(expectedBanTime - current_Time);
		click(nextButton);
	}

	private String getBruteForceBanDateFromErrorLabel()
	{
		String bruteForceBanMessageText = getText(phoneNumberError);
		return bruteForceBanMessageText.replaceAll(bruteForceBanMessageErrorLabel, "");
	}

	private void invokeBanOnEmailVerificationCode() {
		input(secretAnswerInput, getProperty("secretAnswer"));
		click(nextButton);

		input(verificationCodeInput, getProperty("invalidVerificationCode"));
		int attempt = 0;
		while(attempt++ < 2)
		{
			scrollDownUntilElementVisible(verificationCodeError);
			clear(verificationCodeInput);
			input(verificationCodeInput, getProperty("invalidVerificationCode"));
		}
	}

	public void reRegistrationForBilling(String number)
	{
		pressSkipButton();
		selectCountryCode(getProperty("kiribatiCountryCode"));
		inputPhoneNumber(number);
		inputVerificationCode();
		inputDefaultPasscode();
		skipCongratulationsViewIfPossible();
		allowPermission(CONTACTS);
	}

	public void registrationForBilling(String number)
	{
		pressSkipButton();
		selectCountryCode(getProperty("kiribatiCountryCode"));
		inputPhoneNumber(number);
		inputVerificationCode();
		setStandardPassCode();
		skipCongratulationsViewIfPossible();
		setRecoveryInfo();
		skipCongratulationsViewIfPossible();
		allowPermission(CONTACTS);
	}

	public void registrationWith_PreDefinedQuestion_Email()
	{
		pressSkipButton();
		selectCountryCode(getDeviceCountry(FIRST_DEVICE));
		inputPhoneNumber(getProperty("phoneNumberForSecretQuestionTesting"));
		inputVerificationCode();
		inputDefaultPasscode();
		allowPermission(CONTACTS);
	}

	/*******************************************************************************************************************
	 * Legacy method and locators required for Update test
	 *******************************************************************************************************************/
	public void legacyRegistration(String country, String phoneNumber)
	{
		waitForAnyElementLong(nextButton, contactsAccessRequest);

		pressSkipButton();
		click(selectCountryLabel);
		click(search);
		input(searchInput, country);
		click(MobileBy.xpath("//android.widget.TextView[@text='"+country+"']"));

		input(phoneNumberInput, phoneNumber);
		click(continueButton);

		input(verificationCodeInput_legacy, getVerificationCode());

		waitForAnyElementShort(passcodeInput, secretAnswerInput_legacy);
		if (isPresent(secretAnswerInput_legacy))
		{
			input(secretAnswerInput_legacy, getProperty("secretAnswer"));
			click(continueButton);

			input(verificationCodeInput_legacy, getVerificationCode());
		}
		else {
			input(passcodeInput, getDefaultPasscode());
			click(skipSetRestoreInfoButton);
		}

		waitForAnyElementShort(contactsAccessRequest, profilePhoneNumber);
		allowPermission(CONTACTS);
		Assert.assertTrue(getText(profilePhoneNumber).contains(phoneNumber));
	}

	private By selectCountryButton_legacy	= By.id("com.app.stealthtalk.testers:id/registration_phone_country_name");
	private By secretAnswerInput_legacy		= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/step_subview_single_edit_edit_text' or @text='Answer']");
	private By verificationCodeInput_legacy	= By.xpath("//*[@resource-id='com.app.stealthtalk.testers:id/step_subview_single_edit_edit_text' or @text='Approve code']");
	private By profilePhoneNumber			= By.xpath("(//*[@resource-id='com.app.stealthtalk.testers:id/activity_app_bar_layout']/*/android.widget.TextView)[1]");
	private By phoneNumberInput_legacy		= By.id("com.app.stealthtalk.testers:id/registration_phone_phone_edit");
}
