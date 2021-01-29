package Tests;

import CWUtils.eRetryAnalyzer;
import org.testng.annotations.*;

import static CWUtils.eHSProperties.*;

//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//                                  Works only with disabled email verification
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
public class Test_Registration extends Base_Test
{
    @BeforeMethod
    public void openSignUpPage()
    {
        browser.clearData();
        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.openSignUpPage();
    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void registerNewUser() {
        browser.eMainPage.Refresh();
        browser.eRegistrationPage.createAccount(getTestEmail(), getSecondEmailPassword());
        browser.eRegistrationPage.grantRequestedPermission();
        browser.eAccountPage.logOut();
    }


    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void registerNewUser_NegativeCases()
    {
        browser.eMainPage.Refresh();
        browser.eRegistrationPage.createAccount_NegativeCases(getAnotherTestEmail(), getSecondEmailPassword());

        browser.eRegistrationPage.grantRequestedPermission();
        browser.eAccountPage.logOut();
    }




}
