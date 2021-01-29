package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static HSUtils.eHSProperties.*;

public class eRegistrationPage extends eCommonMethods {

    private final By emailInputField_SignIn               = By.id("username");
    private final By passwordInputField_SignIn            = By.id("password");
    private final By signInEmailButton                    = By.xpath("//button[@id='kc-login' and text()='Sign in']");

    private final By signInToHyperID                      = By.xpath("//button[contains(text(), 'Sign in')]");
    private final By continueButton                       = By.xpath("//button[contains(text(), 'Continue')]");
    private final By etherumCurrency                       = By.xpath("//div[contains(text(), 'Ethereum')]");
    private final By tetherCurrence                       = By.xpath("//div[contains(text(), 'Tether USD')]");



    eRegistrationPage(WebDriver driver) {
        super(driver);
    }


    public void openWallet()
    {
        driver.get(getWalletUrl());
    }

    public void signInToSugoiWallet()
    {
        click(signInToHyperID);
        waitForLoad(etherumCurrency);
        waitForLoad(tetherCurrence);

    }
    public void signInToHyperId(String email, String password)
    {
        click(signInToHyperID);
        clearThenInput(emailInputField_SignIn, email);
        click(signInEmailButton);
        clearThenInput(passwordInputField_SignIn, password);
        click(continueButton);
    }

    public void signInToHyperId() { signInToHyperId(getEmail(), getEmailPassword()); }



}
