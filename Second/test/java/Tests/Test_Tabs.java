package Tests;

import Pages.eChatPage;
import Pages.eContactInfoPage;
import Pages.eContactsPage;
import Pages.eMainPage;
import STUtils.eTestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static STUtils.eDriverHandler.Device.FIRST_DEVICE;
import static STUtils.eDriverHandler.Device.SECOND_DEVICE;
import static STUtils.eDriverHandler.getDriver;
import static STUtils.eDriverHandler.turnOffDrivers;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.toSetRetry;

@Listeners(eTestListener.class)
public class Test_Tabs {

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

    private eMainPage eMainPageFirstDevice;
    private eMainPage eMainPageSecondDevice;
    private eContactsPage eContactsPageFirstDevice;
    private eChatPage eChatPageFirstDevice;
    private eContactInfoPage eContactInfoPageFirstDevice;

    private String  firstDevice = getConfigProperty(getProperty("firstDevice")+"AccountName");
    private String  secondDevice = getConfigProperty(getProperty("secondDevice")+"AccountName");


    private void setUp()
    {
        eMainPageFirstDevice		= new eMainPage(getDriver(FIRST_DEVICE));
        eContactsPageFirstDevice	= new eContactsPage(getDriver(FIRST_DEVICE));
        eChatPageFirstDevice        = new eChatPage(getDriver(FIRST_DEVICE));
        eContactInfoPageFirstDevice = new eContactInfoPage(FIRST_DEVICE);
    }
  /*  private void setUpSecondDevice()
    {
        eMainPageFirstDevice		= new eMainPage(getDriver(SECOND_DEVICE));
        eContactsPageFirstDevice	= new eContactsPage(getDriver(SECOND_DEVICE));
        eChatPageFirstDevice        = new eChatPage(getDriver(SECOND_DEVICE));
        eContactInfoPageFirstDevice = new eContactInfoPage(SECOND_DEVICE);
    }

   */

    private void setUpRecent()
    {
        eMainPageFirstDevice        = new eMainPage(getDriver(FIRST_DEVICE));
        eChatPageFirstDevice        = new eChatPage(getDriver(FIRST_DEVICE));
        eMainPageSecondDevice       = new eMainPage(getDriver(SECOND_DEVICE));

        eMainPageSecondDevice.openRecentTab();
        eMainPageFirstDevice.openChat(secondDevice);
        eChatPageFirstDevice.sendMessage(RandomStringUtils.random(5, true, true));
    }

    @Test
    public void basicFunctionality_ContactsTab()
    {
        setUp();
        eMainPageFirstDevice.openContactsTab();

        eMainPageFirstDevice.search_getBack(secondDevice);
        eMainPageFirstDevice.openChat_getBack(secondDevice);
        eMainPageFirstDevice.executeUsualCall_fromTab_getBack(secondDevice);
        eMainPageFirstDevice.executeSecuredCall_fromTab_getBack(secondDevice);
//        eMainPageFirstDevice.invite(getProperty("contactToInvite"));  //TODO REFACTORING This test needs adaptation with different phones. For now it was adapted only for Moto G Play
        tearDown();
    }

    @Test
    public void Add_Rename_Delete_ContactsTab()
    {
        setUp();

        eContactsPageFirstDevice.addNewContact();
        eMainPageFirstDevice.verifyContactInContactTab(getTestContactNameFirstName());

        eContactsPageFirstDevice.renameNewContact();
        eMainPageFirstDevice.verifyNoContactInContactTab(getTestContactNameFirstName());
        eMainPageFirstDevice.verifyContactInContactTab(getTestContactNameSecondName());

        eContactsPageFirstDevice.deleteNewContact();
        eMainPageFirstDevice.verifyNoContactInContactTab(getTestContactNameFirstName());
        eMainPageFirstDevice.verifyNoContactInContactTab(getTestContactNameSecondName());

        tearDown();
    }

    @Test
    public void basicFunctionality_FavoritesTab()
    {
        setUp();
        eMainPageFirstDevice.openContactInfo(secondDevice);
        eContactInfoPageFirstDevice.addToFavorite();
        eMainPageFirstDevice.openFavoriteTab();

        eMainPageFirstDevice.openChat_getBack(secondDevice);
        eMainPageFirstDevice.search_getBack(secondDevice);
        eMainPageFirstDevice.executeUsualCall_fromTab_getBack(secondDevice);
        eMainPageFirstDevice.executeSecuredCall_fromTab_getBack(secondDevice);

        eMainPageFirstDevice.openContactInfoFromContextMenu(secondDevice);
        eContactInfoPageFirstDevice.removeFromFavorites();
        tearDown();
    }

    @Test
    public void basicFunctionality_StealthTalkTab()
    {
        setUp();
        eMainPageFirstDevice.openStealthtalkTab();

        eMainPageFirstDevice.search_getBack(secondDevice);
        eMainPageFirstDevice.executeUsualCall_fromTab_getBack(secondDevice);
        eMainPageFirstDevice.executeSecuredCall_fromTab_getBack(secondDevice);
        eMainPageFirstDevice.verifyQrView();

        tearDown();
    }


    @Test
    public void basicFunctionality_RecentTab()
    {
        setUpRecent();
        eMainPageFirstDevice.getBackFromChat();
        eMainPageFirstDevice.openRecentTab();

        eMainPageFirstDevice.searchRecent_getBack(secondDevice);
        eMainPageFirstDevice.openChat_getBack(secondDevice);
        eMainPageFirstDevice.executeUsualCall_fromTab_getBack(secondDevice);
        eMainPageFirstDevice.executeSecuredCall_fromTab_getBack(secondDevice);
        eMainPageFirstDevice.searchNonExistentUser();

        tearDown();
    }

    @Test
    public void checkRecent_RecentTab()
    {
        setUpRecent();

        eMainPageSecondDevice.archiveChat(firstDevice);
        eChatPageFirstDevice.sendMessage(RandomStringUtils.random(5, true, true));
        eMainPageSecondDevice.verifyRecentAdded(firstDevice);

        tearDown();
    }

    private void tearDown()
    {
        turnOffDrivers();
    }

}
