package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static HSUtils.eHSProperties.*;

public class eRegistrationPage extends eCommonMethods {
    private final By activePageLanguage                     = By.xpath("//a[@id='kc-current-locale-link']");
    private final By englishLanguageLabel                   = By.xpath("//a[contains(text(), 'English')]");
    private final By emailInputField_SignIn                 = By.id("username");
    private final By passwordInputField_SignIn              = By.id("password");
    private final By emailInputField_SignUp                 = By.id("email");
    private final By passwordInputField_SignUp              = By.id("password");
    private final By passwordConfirmationInputField_SignUp  = By.id("password-confirm");
    private final By reSendVerificationEmailInputField      = By.id("resendverificationemailform-email");
    private final By signInButton                           = By.xpath("//button[@id='kc-login']");
    private final By errorMessage                           = By.xpath("//div[@class='alert alert-error']");
    private final By signUpButton                           = By.xpath("//button[@type='submit' and text()='Sign up']");
    private final By emailFieldError                        = By.xpath("//span[contains(text(), 'HyperID account not found')]");
    private final By passwordFieldError                     = By.xpath("//span[contains(text(), 'Invalid password.')]");
    private final By invalidEmailError                      = By.xpath("//span[contains(text(), 'Please specify email.')]");
    private final By emailAlreadyExistsError                = By.xpath("//span[contains(text(), 'Email already exists.')]");
    private final By passwordConfirmationDoesNotMatchError  = By.xpath("//span[contains(text(), 'Password confirmation doesn')]");
    private final By emptyPasswordError                     = By.xpath("//span[contains(text(), 'Please specify password.')]");
    private final By notEnoughLengthError                   = By.xpath("//span[contains(text(), 'Invalid password: minimum length 6.')]");
    private final By notVerifiedEmailError                  = By.xpath("//p[contains(text(), 'This email is not verified')]");
    private final By createAccountButton                    = By.xpath("//a[contains(text(), 'Create account')]");
    private final By submitVerificationCodeButton           = By.xpath("//button[@type='submit' and text()='Submit']");
    private final By submitResetEmailPasswordButton         = submitVerificationCodeButton;
    private final By verificationCodeInput                  = By.id("verifycodeform-code");
    private final By okButton                               = By.id("kc-login");
    private final By reSendVerificationCodeLabel            = By.xpath("//a[contains(text(), 'Need to enter email validation code?')]");
    private final By sendButton                             = By.xpath("//button[@type='submit' and text()='Send']");
    private final By forgotPasswordLink                     = By.xpath("//a[contains(text(), 'Forgot Password?')]");
    private final By passwordResetLable                     = By.xpath("//h1[contains(text(), 'REQUEST PASSWORD RESET')]");
    private final By permissionRequestedLabel               = By.id("kc-page-title");
    private final By restoreForgottenPasswordField          = By.id("password-new");
    private final By restoreForgottenPasswordFieldConfirm   = By.id("password-confirm");
    private final By acessPermissionRequestedLabel          = By.xpath("//h1[contains(text(), 'Permission requested')]");
    private final By updatePasswordMessage                  = By.xpath("//span[contains(text(), 'You need to change your password.')]");

    eRegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void changeLanguageIfNeeded()
    {
        waitForLoad(activePageLanguage);

        if (!getText(activePageLanguage).contains("English"))
        {
            click(activePageLanguage);
            click(englishLanguageLabel);
        }

        Assert.assertTrue(getText(activePageLanguage).contains("English"));
    }

    public void openSignUpPage()
    {
        waitThenClick(createAccountButton);
        waitForLoad(signUpButton);
    }
    public void sendRestorePasswordEmail(String email)
    {
        clearThenInput(emailInputField_SignIn, email);
        waitThenClick(submitVerificationCodeButton);
    }

    public void createAccount(String email, String password)
    {
        waitForLoad(signUpButton);
        inputEmailAndPasswords(email, password,password);

        waitForAnyElement(permissionRequestedLabel);
        //waitForAnyElement(signInButton, verificationCodeInput);
    }

    public void createAccount_NegativeCases(String email, String password)
    {
        inputEmailAndPasswords("", password, password);
        Assert.assertTrue(isAvailable(invalidEmailError));

        inputEmailAndPasswords(getEmail(), password, password);
        Assert.assertTrue(isAvailable(emailAlreadyExistsError));

        inputEmailAndPasswords(email, password, "password");
        Assert.assertTrue(isAvailable(passwordConfirmationDoesNotMatchError));
        inputEmailAndPasswords(email, "", password);
        Assert.assertTrue(isAvailable(emptyPasswordError));
        inputEmailAndPasswords(email, "123", "123");
        Assert.assertTrue(isAvailable(notEnoughLengthError));

        createAccount(email, password);
    }
    private void inputEmailAndPasswords(String email, String password, String passwordConfirmation)
    {
        clearThenInput(emailInputField_SignUp, email);
        clearThenInput(passwordInputField_SignUp, password);
        clearThenInput(passwordConfirmationInputField_SignUp, passwordConfirmation);

        waitThenClick(signUpButton);
        await(1000);
    }

    public void restoreForgottenPassword()
    {
        driver.get(verificationCode);
        clearThenInput(restoreForgottenPasswordField, getSecondEmailPassword());
        clearThenInput(restoreForgottenPasswordFieldConfirm, getSecondEmailPassword());
        waitThenClick(submitVerificationCodeButton);
    }
    public void submitVerification()
    {
        waitForLoad(submitVerificationCodeButton);
        clearThenInput(verificationCodeInput, verificationCode);
        click(submitVerificationCodeButton);
        waitForLoad(signInButton);
    }
    public void reSendVerificationCode(String email)
    {
        waitForLoad(reSendVerificationCodeLabel);
        waitForLoad(notVerifiedEmailError);
        Assert.assertTrue(isAvailable(notVerifiedEmailError));
        click(reSendVerificationCodeLabel);

        waitForLoad(sendButton);
        input(reSendVerificationEmailInputField, email);

        click(sendButton);
    }

    public void signInToHyperId(String email, String password)
    {
        waitForLoad(signInButton);

        clearThenInput(emailInputField_SignIn, email);
        waitThenClick(signInButton);
        clearThenInput(passwordInputField_SignIn, password);
        waitThenClick(signInButton);

        try {
            if (isAvailable(acessPermissionRequestedLabel)); { grantRequestedPermission(); }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void grantRequestedPermission()
    {
        if (isElementDisplayed(permissionRequestedLabel)) { click(okButton); }

    }
    public void openForgotPasswordPage()
    {
        waitThenClick(forgotPasswordLink);
        // waitForLoad(signUpButton);
    }

    public void signInToHyperId() { signInToHyperId(getEmail(), getEmailPassword()); }

    public void signInToHyperId_NegativeCases()
    {
        //HACK: wait until page loaded
        await(2000);
        inputEmail(getWrongEmail());
        waitForLoad(errorMessage);
        Assert.assertTrue(isAvailable(emailFieldError));
        inputEmail("");
        waitForLoad(errorMessage);
        Assert.assertTrue(isAvailable(emailFieldError));
        inputEmail(getEmail());

        inputPassword("");
        waitForLoad(errorMessage);
        Assert.assertTrue(isAvailable(passwordFieldError));
        inputPassword(getWrongPassword());
        waitForLoad(errorMessage);
        Assert.assertTrue(isAvailable(passwordFieldError));
        inputPassword(getEmailPassword());
    }

    private void inputEmail(String email)
    {
        By emailInput = (isAvailable(emailInputField_SignUp)) ? emailInputField_SignUp : emailInputField_SignIn;
        By proceedButton = (isAvailable(signUpButton)) ? signUpButton : signInButton;

        clearThenInput(emailInput, email);

        waitThenClick(proceedButton);
    }

    private void inputPassword(String password)
    {
        By proceedButton    = (isAvailable(signUpButton)) ? signUpButton : signInButton;
        By passwordInput    = (isAvailable(passwordInputField_SignUp)) ? passwordInputField_SignUp : passwordInputField_SignIn;

        clearThenInput(passwordInput, password);
        click(proceedButton);
    }

    public void recoverPassword() {
        waitThenClick(forgotPasswordLink);
        waitForLoad(passwordResetLable);
        clearThenInput(emailInputField_SignUp, getRestorePasswordEmail());
        click(submitResetEmailPasswordButton);
    }
}
