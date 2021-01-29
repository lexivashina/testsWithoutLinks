package Tests;

import HSUtils.eRetryAnalyzer;
import HSUtils.eTestListener;
import org.testng.annotations.*;

import static HSUtils.eHSProperties.getEmail;

@Listeners(eTestListener.class)
public class Test_Authorization extends Base_Test
{
    @BeforeMethod
    public void openSignInPage()
    {
        browser.clearData();
        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.changeLanguageIfNeeded();
    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void testAuthorization()
    {
        browser.eRegistrationPage.signInToHyperId();
        browser.eAccountPage.verifyAccountAuthorization(getEmail());
        browser.eAccountPage.logOutSugoi();
    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void testAuthorization_NegativeCases()
    {
        browser.eRegistrationPage.signInToHyperId_NegativeCases();

        browser.eAccountPage.verifyAccountAuthorization(getEmail());
        browser.eAccountPage.logOutSugoi();
    }

}
