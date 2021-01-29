package Tests;

import HSUtils.eRetryAnalyzer;
import org.testng.annotations.*;

import java.awt.*;

import static HSUtils.eHSProperties.getEmail;

public class Test_LinksWork extends Base_Test
{
    @BeforeMethod
    public void openSignInPage()
    {
        browser.clearData();
        browser.eMainPage.openAuthWindow();
    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void testLinksWorkProperly(){

        browser.eRegistrationPage.signInToHyperId();
        browser.eAccountPage.verifyAccountAuthorization(getEmail());
        browser.eMainPage.openAdastra();
        browser.eAccountPage.getAIOTCheck();
        browser.eAccountPage.connectWallet();
        browser.eMainPage.openAdastra();
        browser.eAccountPage.getStakingMenuAndStart();
        browser.eAccountPage.connectWallet();


        browser.eRegistrationPage.openWallet();
        browser.eRegistrationPage.signInToSugoiWallet();
        browser.eAccountPage.getKaizenFromWallet();
    }
}
