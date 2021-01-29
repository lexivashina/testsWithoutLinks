package Tests;

import HSUtils.eRetryAnalyzer;
import org.testng.annotations.Test;

import static HSUtils.eHSProperties.*;

public class Test_EmailDelivery extends Base_Test {

    @Deprecated
    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void testEmailDelivery() {
        for (int i = 0; i < 100; i++)
        {
            browser.clearData();
            browser.eMainPage.openAuthWindow();
            browser.eRegistrationPage.changeLanguageIfNeeded();
            browser.eRegistrationPage.openForgotPasswordPage();
            browser.eRegistrationPage.sendRestorePasswordEmail(getEmail());
            verificationCode = browser.getVerificationCode(getEmail(), getEmailPassword());
        }
    }

}
