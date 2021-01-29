package Tests;

import CWUtils.eRetryAnalyzer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class Test_SendMoney extends Base_Test {
    @BeforeMethod
    public void openSignInPage() {

        browser.clearData();
        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.signInToHyperId();
        browser.eAccountPage.verifyAccountAuthorization();
    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void sendMoneyTest() {

        browser.eAccountPage.sendMoney();
        browser.eAccountPage.logOut();

    }
}
