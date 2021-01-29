package Pages;

import org.openqa.selenium.WebDriver;

import java.util.Set;

import static HSUtils.eDriverHandler.*;

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

}
