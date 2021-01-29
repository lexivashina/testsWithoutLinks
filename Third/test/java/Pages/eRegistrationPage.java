package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static CWUtils.eHSProperties.*;

public class eRegistrationPage extends eCommonMethods {

    private final By emailInputField_SignIn               = By.id("username");
    private final By passwordInputField_SignIn            = By.id("password");
    private final By emailInputField_SignUp               = By.id("email");
    private final By passwordInputField_SignUp            = By.id("password");
    private final By signInEmailButton                    = By.xpath("//*[@id=\"kc-login\"]");
    private final By signInPassWordButton                 = By.xpath("//*[@id=\"password\"]");
    private final By signInButton                         = By.xpath("//button[@id='kc-login']");
    private final By signUpButton                         = By.xpath("//button[contains(text(),'Sign up')]");
    private final By emailFieldError                      = By.xpath("//span[contains(text(), 'Invalid email address.')]");
    private final By invalidEmailOrPasswordError          = By.xpath("//*[contains(text(), 'Invalid password.')]");
    private final By createAccountButton                  = By.xpath("//a[@class = 'info_registration']");
    private final By ConfirmpasswordInputField_SignUp     = By.id("password-confirm");
    private final By LoadSign                             = By.id("kc-page-title");
    private final By permissionRequestedLabel             = By.id("kc-page-title");
    private final By okButton                             = By.id("kc-login");
    private final By invalidEmailError                    = By.xpath("//span[contains(text(), 'Please specify email.')]");
    private final By emailAlreadyExistsError              = By.xpath("//span[contains(text(), 'Email already exists.')]");
    private final By passwordConfirmationDoesNotMatchError= By.xpath("//span[contains(text(), 'Password confirmation doesn')]");
    private final By emptyPasswordError                   = By.xpath("//span[contains(text(), 'Please specify password.')]");
    private final By notEnoughLengthError                 = By.xpath("//span[contains(text(), 'Invalid password: minimum length 6.')]");
    private final By passwordConfirmationInputField_SignUp= By.id("password-confirm");









    eRegistrationPage(WebDriver driver) {
        super(driver);
    }


    public void signInToHyperId(String email, String password)
    {
        clearThenInput(emailInputField_SignIn, email);
        click(signInEmailButton);
        clearThenInput(passwordInputField_SignIn, password);
        click(signInEmailButton);
    }

    public void signInToHyperId() { signInToHyperId(getEmail(), getEmailPassword()); }

    public void createAccount(String email, String password)
    {
        waitForAnyElement(LoadSign);
        clearThenInput(emailInputField_SignUp, email);
        clearThenInput(passwordInputField_SignUp, password);
        clearThenInput(ConfirmpasswordInputField_SignUp, password);

        click(signUpButton);
    }
    public void grantRequestedPermission()
    {
        if (isElementDisplayed(emailInputField_SignIn)) { signInToHyperId(); }

        if (isElementDisplayed(permissionRequestedLabel)) { click(okButton); }
    }
    public void inputCorrectEmail(String email)
    {
        clearThenInput(emailInputField_SignIn, email);
        click(signInEmailButton);
    }




    public void openSignUpPage()
    {
        click(createAccountButton);
    }


    public void signInToHyperId_NegativeCases()
    {
        verifyEmptyEmailFieldErrors();
        verifyIncorrectEmailFieldError();

        //TODO: Following methods not working yet
        //New
        inputCorrectEmail(getEmail());
        verifyEmptyPasswordFieldError();
        verifyInvalidEmailPasswordError();
        await(3000);

    }

    private void verifyEmptyEmailFieldErrors()
    {
        By proceedButton = (isAvailable(signUpButton)) ? signUpButton : signInEmailButton;

        waitThenClick(proceedButton);

        waitForLoad(emailFieldError);

        Assert.assertTrue(isAvailable(emailFieldError));
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
        By emailInput = (isAvailable(emailInputField_SignUp)) ? emailInputField_SignUp : emailInputField_SignIn;
        By passwordInput = (isAvailable(passwordInputField_SignUp)) ? passwordInputField_SignUp : passwordInputField_SignIn;
        By passwordConfirmationInput = passwordConfirmationInputField_SignUp;
        By proceedButton = (isAvailable(signUpButton)) ? signUpButton : signInButton;

        clearThenInput(emailInput, email);
        clearThenInput(passwordInput, password);
        clearThenInput(passwordConfirmationInput, passwordConfirmation);

        waitThenClick(proceedButton);
        await(1000);
    }

    private void verifyIncorrectEmailFieldError()
    {
        By proceedButton = (isAvailable(signUpButton)) ? signUpButton : signInEmailButton;
        By emailInput = (isAvailable(emailInputField_SignUp)) ? emailInputField_SignUp : emailInputField_SignIn;

        input(emailInput, getWrongEmail());
        waitThenClick(proceedButton);

        waitForLoad(emailFieldError);

        Assert.assertTrue(isAvailable(emailFieldError));
    }

    private void verifyEmptyPasswordFieldError()
    {
        By proceedButton    = (isAvailable(signUpButton)) ? signUpButton : signInEmailButton;
        By passwordInput    = (isAvailable(passwordInputField_SignUp)) ? passwordInputField_SignUp : passwordInputField_SignIn;
        By emailInput       = (isAvailable(emailInputField_SignUp)) ? emailInputField_SignUp : emailInputField_SignIn;

        clear(passwordInput);

        input(passwordInput, getWrongPassword());
        waitThenClick(proceedButton);

        waitUntilElementDisappear(emailFieldError);

        Assert.assertFalse(isAvailable(emailFieldError));
       // Assert.assertTrue(isAvailable(blankPasswordFieldError));
    }


    private void verifyInvalidEmailPasswordError()
    {
        //clearThenInput(emailInputField_SignIn, getEmail());
        clearThenInput(passwordInputField_SignIn, getWrongPassword());
        waitThenClick(signInPassWordButton);

        waitForLoad(invalidEmailOrPasswordError);
        Assert.assertTrue(isAvailable(invalidEmailOrPasswordError));
        waitThenClick(signInPassWordButton);
    }





}
