package Tests;

import HSUtils.eTestListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static HSUtils.eHSProperties.*;

@Listeners(eTestListener.class)
public class Test_ForgotPassword extends Base_Test {

    @BeforeMethod
    public void openSignUpPage()
    {
        browser.clearData();
        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.changeLanguageIfNeeded();
        browser.eRegistrationPage.openForgotPasswordPage();
    }
    @Test
    public void testForgotPassword()
    {
        browser.eRegistrationPage.sendRestorePasswordEmail(getSecondEmail());
        verificationCode = browser.getVerificationCode(getSecondEmail(), getSecondEmailPassword());
        browser.eRegistrationPage.restoreForgottenPassword();
    }


}
