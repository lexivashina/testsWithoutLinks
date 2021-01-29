package Pages;

import CWUtils.eImapApi;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static CWUtils.eDriverHandler.*;

public class eBrowserPage {

	private WebDriver driver;

    public eMainPage eMainPage;
    public eRegistrationPage eRegistrationPage;
    public eAccountPage eAccountPage;

    public void setUp(String browserName)
    {
        driver              = getDriverIfNotExist(browserName);

        eMainPage           = new eMainPage(driver);
        eRegistrationPage   = new eRegistrationPage(driver);
        eAccountPage        = new eAccountPage(driver);

        eMainPage.open();
    }

    public void tearDown()
    {
        turnOffDriver();
    }

    public void clearData()
    {
        driver.manage().deleteAllCookies();
    }

    public void changeWindow()
    {
        String currentWindowHandle      = driver.getWindowHandle();
        Set<String> allWindowHandles    = driver.getWindowHandles();

        if (!allWindowHandles.isEmpty()) allWindowHandles.remove(currentWindowHandle);
        allWindowHandles.stream().forEach(windowHandle -> driver.switchTo().window(windowHandle));
    }

    public String getVerificationCode(String email, String password) { return new eImapApi(email, password).getVerificationCode(); }
}
