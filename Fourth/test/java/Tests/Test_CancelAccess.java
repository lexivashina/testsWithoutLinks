package Tests;

import HSUtils.eRetryAnalyzer;
import HSUtils.eTestListener;
import org.testng.annotations.*;

import static HSUtils.eHSProperties.*;

@Listeners(eTestListener.class)
public class Test_CancelAccess extends Base_Test{

	@BeforeMethod
	public void openSignInPage()
	{
		browser.clearData();
		browser.eMainPage.openAuthWindow();
		browser.eRegistrationPage.signInToHyperId();
		browser.eAccountPage.waitUntilPageLoaded();
	}

	@Test(retryAnalyzer = eRetryAnalyzer.class)
	public void testCancelAccess()
	{
		browser.eHyperSecureIdPage.open();
		browser.eHyperSecureIdPage.cancelAccess();
		browser.eMainPage.openAccountPage();

		browser.eAccountPage.verifyAccountAuthorization(getEmail());

		browser.eAccountPage.logOutSugoi();
		browser.clearData();

		browser.eMainPage.openAuthWindow();
		browser.eRegistrationPage.signInToHyperId();

		browser.eAccountPage.verifyAccountAuthorization(getEmail());
		browser.eAccountPage.logOutSugoi();
	}

}
