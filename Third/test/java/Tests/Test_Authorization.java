package Tests;

import CWUtils.eRetryAnalyzer;
import org.testng.annotations.*;

import static CWUtils.eHSProperties.*;

public class Test_Authorization extends Base_Test
{
    @BeforeMethod
    public void openSignInPage()
    {
        browser.clearData();
        browser.eMainPage.openAuthWindow();

    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void testAuthorization()
    {

        browser.eRegistrationPage.signInToHyperId();
        browser.eAccountPage.logOut();
        browser.eMainPage.Refresh();

    }

@Test(retryAnalyzer = eRetryAnalyzer.class)
    public void testAuthorization_NegativeCases()
    {

        browser.eRegistrationPage.signInToHyperId_NegativeCases();
        openSignInPage();

        browser.eRegistrationPage.signInToHyperId();

        browser.eAccountPage.verifyAccountAuthorization();
        browser.eAccountPage.logOut();
    }
}
