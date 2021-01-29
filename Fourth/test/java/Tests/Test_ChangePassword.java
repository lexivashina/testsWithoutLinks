package Tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static HSUtils.eHSProperties.*;

public class Test_ChangePassword extends Base_Test{
    @BeforeMethod
    public void openSignInPage()
    {
        browser.clearData();
        browser.eMainPage.openHyperSphereIdURL();
        browser.eRegistrationPage.signInToHyperId();
    }
    @Test
    public void testChangePassword()
    {
        browser.eAccountPage.changePassword(getEmailPassword(), getWrongPassword());
        browser.eAccountPage.logOutHyperID();
        browser.eRegistrationPage.signInToHyperId(getEmail(), getWrongPassword());
        browser.eAccountPage.changePassword(getWrongPassword(),getEmailPassword());
        browser.eAccountPage.logOutHyperID();

    }
}
