package Tests;

import Pages.eChatPage;
import Pages.eMainPage;
import Pages.eGooglePhotosPage;
import STUtils.eTestListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import static STUtils.eDriverHandler.*;
import static STUtils.eDriverHandler.Device.*;
import static STUtils.eSTProperties.*;
import static STUtils.eUtils.*;
import static STUtils.eUtils.openGooglePhotos;



@Listeners(eTestListener.class)
public class Test_Share
{
    private eChatPage			secondDevice_eChatPage;

	private eMainPage			firstDevice_eMainPage;
    private eChatPage			firstDevice_eChatPage;
    private eGooglePhotosPage	firstDevice_eGooglePhotosPage;


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
        secondDevice_eChatPage = new eMainPage(getDriver(SECOND_DEVICE)).openChat(getFirstDeviceProperty("AccountName"));
        Assert.assertEquals(secondDevice_eChatPage.getChatTitle(), getFirstDeviceProperty("AccountName"));

        firstDevice_eMainPage = new eMainPage(getDriver(FIRST_DEVICE));
        firstDevice_eChatPage = firstDevice_eMainPage.openChat(getSecondDeviceProperty("AccountName"));
        firstDevice_eChatPage.sendMessage("SEPARATOR");

        firstDevice_eGooglePhotosPage = new eGooglePhotosPage(getDriver(FIRST_DEVICE));
        openGooglePhotos(getDriver(FIRST_DEVICE));
    }

    @DataProvider (name = "status")
    public Object[] getStatus() { return new String[] {"ST Online", "ST Offline"}; }

    @Test (dataProvider = "status")
    public void shareImage(String status)
    {
        setUp();
        if (status.equals("ST Offline")) closeStealthTalk(FIRST_DEVICE);
        firstDevice_eGooglePhotosPage.sharePicture();
        firstDevice_eMainPage.share(getSecondDeviceProperty("AccountName"));
        secondDevice_eChatPage.verifyReceivedPicture(); //TODO Refactor add status verification for picture
        tearDown();
    }

    private void tearDown()
    {
        turnOffDrivers();
    }
}
