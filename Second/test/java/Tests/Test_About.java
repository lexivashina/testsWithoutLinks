package Tests;

import Pages.eMainPage;
import Pages.eSidePanelPage;
import STUtils.eDriverHandler;
import STUtils.eTestListener;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.turnOffDrivers;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_About
{
    private eMainPage mainPage;
    private eSidePanelPage eSidePanelPage;

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
        mainPage = new eMainPage(eDriverHandler.getDriver(FIRST_DEVICE));
        eSidePanelPage = mainPage.openSidePage();
    }

    @Test
    public void testAbout_Website()
    {
        setUp();
        eSidePanelPage.openWebsite();
		eSidePanelPage.verifyWebsite();
		tearDown();
    }

    @Test
    public void testAbout_Feedback()
    {
        setUp();
        eSidePanelPage.openFeedback();
        eSidePanelPage.verifyFeedback();
		tearDown();
    }

    private void tearDown()
    {
        turnOffDrivers();
    }

}
