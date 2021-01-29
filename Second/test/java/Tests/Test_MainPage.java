package Tests;

import Pages.eChatPage;
import Pages.eMainPage;
import STUtils.eDriverHandler;
import STUtils.eTestListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;


import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eSTProperties.getSecondDeviceProperty;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_MainPage
{
    private eMainPage mainPage;

    private eChatPage chatPage;

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
        chatPage = new eChatPage(getDriver(FIRST_DEVICE));
    }




    @Test
    public void swipeBetweenTabs()
    {
        setUp();
        mainPage.openContactsTab();

        mainPage.rightHorizontalScroll();
        Assert.assertEquals(mainPage.stealthtalkTabIsSelected(),"true");

        mainPage.rightHorizontalScroll();
        Assert.assertEquals(mainPage.recentTabIsSelected(),"true");

        mainPage.rightHorizontalScroll();
        Assert.assertEquals(mainPage.favoriteTabIsSelected(),"true");
        tearDown();
    }


    @Test
    public void openTabsByClick()
    {
        setUp();
        mainPage.openFavoriteTab();
        Assert.assertEquals(mainPage.favoriteTabIsSelected(),"true");

        mainPage.openRecentTab();
        Assert.assertEquals(mainPage.recentTabIsSelected(),"true");

        mainPage.openStealthtalkTab();
        Assert.assertEquals(mainPage.stealthtalkTabIsSelected(),"true");

        mainPage.openContactsTab();
        Assert.assertEquals(mainPage.contactsTabIsSelected(),"true");
        tearDown();
    }

    @Test
    public void swipeFromChatToRecent()
    {
        setUp();
        chatPage = mainPage.openChat(getSecondDeviceProperty("AccountName"));
        chatPage.horizontalScroll();
        chatPage.verifyRecentTabOpened();
        tearDown();
    }

    @Test
    public void openSidePageBySwipe()
    {
        setUp();
        mainPage.openSidePageBySwipe();

        mainPage.verifySidePageIsOpened();
        tearDown();
    }

    @Test
    public void openSidePageByButton()
    {
        setUp();
        mainPage.openSidePage();

        mainPage.verifySidePageIsOpened();
        tearDown();
    }

    private void tearDown()
    {
        turnOffDrivers();
    }
}
