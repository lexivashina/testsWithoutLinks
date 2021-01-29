package Tests;

import CWUtils.eRetryAnalyzer;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

public class Test_CreateDeleteNewWallet extends Base_Test {
    String SecretPhrase = "";
    @BeforeMethod
    public void openSignInPage()
    {
        browser.clearData();
        browser.eMainPage.openAuthWindow();
        browser.eRegistrationPage.signInToHyperId();
    //    browser.eAccountPage.verifyAccountAuthorization();


    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void createNewWallet()
    {
        browser.eAccountPage.pathToNewWalletsMenu();
        browser.eAccountPage.createNewMultiCoinWallet();
        browser.eAccountPage.deleteWallet();
        browser.eMainPage.Refresh();
        browser.eAccountPage.importNewWalletByPrivateKey();
        browser.eAccountPage.deleteWallet();
        browser.eAccountPage.logOut();


    }

    @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void exportWalletByJson()
    {
        browser.eAccountPage.wayToExportByJSON();
        browser.eAccountPage.logOut();
    }

        @Test(retryAnalyzer = eRetryAnalyzer.class)
    public void importNewWalletByJson() throws AWTException
    {
        browser.eAccountPage.importNewWalletByJSON();
        browser.eAccountPage.deleteWallet();
        browser.eAccountPage.logOut();
    }






}
