package Tests;

import Pages.eProductionPage;
import STUtils.eTestListener;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Application.GOOGLE_PLAY;
import static STUtils.eDriverHandler.Device.*;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_Production {
    private eProductionPage eProductionPage;


    @BeforeSuite
    public void beforeSuite(ITestContext testContext)
    {
        if (toSetRetry())
        {
            for (ITestNGMethod method : testContext.getAllTestMethods())
            {
                method.setRetryAnalyzer(new eTestListener());
            }
        }
    }

    private void setUp()
    {
        AndroidDriver<AndroidElement> driver = createDriver(SECOND_DEVICE, GOOGLE_PLAY);
        eProductionPage = new eProductionPage(driver);
    }

    @Test
    public void installSTFromGooglePlay()
    {
        setUp();

        eProductionPage.findSTInGooglePlay();
        eProductionPage.installApp();
        eProductionPage.verifySTInstall();
        eProductionPage.uninstallApp();

        tearDown();
    }
    private void tearDown() {
        turnOffDrivers();
    }
}
