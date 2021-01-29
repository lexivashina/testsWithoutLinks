package Tests;

import HSUtils.eRetryAnalyzer;
import HSUtils.eTestListener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Date;

import static HSUtils.eHSProperties.*;
import static HSUtils.eUtils.*;

@Listeners(eTestListener.class)
public class Test_Registration extends Base_Test
{
    @BeforeMethod
    public void openSignUpPage()
    {
        browser.clearData();
        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.changeLanguageIfNeeded();
        browser.eRegistrationPage.openSignUpPage();
    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void testRegisterNewUser() throws IOException, UnsupportedFlavorException
    {
        Date userInvitedTime = null;
        String newEmail1 = getRandomEmail();
        String newEmail2 = getRandomEmail();
        String newEmail3 = getRandomEmail();
        String firstUserReferralLink = "";
        String firstUserReferralCode = "";
        String secondUserReferralCode = "";

        browser.eRegistrationPage.createAccount(newEmail1, getSecondEmailPassword());
        browser.eRegistrationPage.grantRequestedPermission();
        browser.eAccountPage.openReferralMenu();
        firstUserReferralLink = browser.eAccountPage.getReferralLink();
        firstUserReferralCode = browser.eAccountPage.getReferralCode();
        browser.eAccountPage.logOutSugoi();

        browser.eAccountPage.pasteReferralLink(firstUserReferralLink);
        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.openSignUpPage();
        browser.eRegistrationPage.createAccount(newEmail2, getSecondEmailPassword());
        userInvitedTime = getCurrentDateTime();
        browser.eRegistrationPage.grantRequestedPermission();
        browser.eAccountPage.openReferralMenu();
        secondUserReferralCode = browser.eAccountPage.getReferralCode();
        browser.eAccountPage.openUserProfile();
        Assert.assertEquals(browser.eAccountPage.getInvitedByCode(), firstUserReferralCode);
        browser.eAccountPage.logOutSugoi();

        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.signInToHyperId(newEmail1, getSecondEmailPassword());
        Assert.assertTrue(browser.eAccountPage.checkInvitedPerson(userInvitedTime, newEmail2, 0));
        browser.eAccountPage.logOutSugoi();

        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.openSignUpPage();
        browser.eRegistrationPage.createAccount(newEmail3, getSecondEmailPassword());
        browser.eRegistrationPage.grantRequestedPermission();
        browser.eAccountPage.openUserProfile();
        browser.eAccountPage.applyReferralCode(secondUserReferralCode);
        browser.eAccountPage.openUserProfile();

        Assert.assertEquals(browser.eAccountPage.getInvitedByCode(), secondUserReferralCode);
        browser.eAccountPage.logOutSugoi();

        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.signInToHyperId(newEmail1, getSecondEmailPassword());
        Assert.assertTrue(browser.eAccountPage.checkInvitedPerson(userInvitedTime, newEmail2, 1));
        browser.eAccountPage.logOutSugoi();
    }

}
