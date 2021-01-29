package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static HSUtils.eHSProperties.getHyperSphereIdPageUrl;

public class eHyperSecureIdPage extends eCommonMethods {
    private final By mainPageText                   = By.xpath("//h2[contains(text(), 'User profile')]");
    private final By applicationsHeader             = By.xpath("//h2[contains(text(), 'Applications')]");
    private final By signInPageLink                 = By.xpath("//a[contains(text(), 'Sign in')]");
    private final By createAccountPageLink          = By.xpath("//a[contains(text(), 'Create account')]");
    private final By signInPageTitle                = By.xpath("");
    private final By emailInputField_SignIn         = By.id("loginform-email");
    private final By passwordInputField_SignIn      = By.id("loginform-password");
    private final By signInButton                   = By.xpath("//button[@type='submit' and text()='Sign in']");
    private final By applicationsTab                = By.xpath("//li/a[contains(text(), 'Applications')]");
    private final By revokeAccess                   = By.xpath("//button[@type='submit' and text()='Revoke Access']");


    eHyperSecureIdPage(WebDriver driver) { super(driver); }

    public void open()
    {
        driver.get(getHyperSphereIdPageUrl());
    }

    public void openSignInPage()
    {
        driver.get(getHyperSphereIdPageUrl());

        waitThenClick(signInPageLink);
    }

    public void signIn(String email, String password)
    {
        clearThenInput(emailInputField_SignIn, email);
        clearThenInput(passwordInputField_SignIn, password);
        click(signInButton);
    }

    public void signInIfRequired(String email, String password)
    {
        if (isElementDisplayed(signInPageLink)) {
            clearThenInput(emailInputField_SignIn, email);
            clearThenInput(passwordInputField_SignIn, password);
            click(signInButton);
        }
    }

    public void cancelAccess()
    {

        waitThenClick(applicationsTab);

        waitForLoad(applicationsHeader);
        await(1500);
        click(revokeAccess);

        waitUntilElementDisappear(revokeAccess);
    }

}
