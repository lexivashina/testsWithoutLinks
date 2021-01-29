package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.awt.*;
import java.util.ArrayList;

public class eAccountPage extends eCommonMethods {
    eAccountPage(WebDriver driver) {
        super(driver);
    }

    private final By profileLabel             = By.xpath("//span[contains(text(), 'Profile')]");
    private String accountLabelXpath          = "//span[contains(text(), '%s')]";
    private final By airDropButton            = By.xpath("//span[contains(text(), 'AirDrop')]");
    private final By defiButton               = By.xpath("//span[contains(text(), 'DeFi')]");
    private final By adastraButton            = By.xpath("//a[@class='btn btn--style-gradient'][1]");
    private final By adastraLabel             = By.xpath("//div[contains(text(), 'JCoin')]");
    private final By airDropLabel             = By.xpath("//h3[contains(text(), 'SugoiCrypto.com')]");
    private final By hyperIDLabel             = By.xpath("//div[contains(@class, 'hyperid-btn_logo')]");
    private final By sugoiWalletLink          = By.xpath("*//div/div/p[3]/a");
    private final By kaidzenLink              = By.xpath("*//div/div/p[4]/a");
    private final By kaidzenLabel             = By.xpath("//img[@class='logo__icon']");
    private final By getAIOTButton            = By.xpath("//a[@class='btn-link']");
    private final By stakingButton            = By.xpath("//a[contains(text(), 'Staking')]");
    private final By startStakingButton       = By.xpath("//a[contains(text(), 'Start Staking')]");
    private final By swapXpath                = By.xpath("//h1[@class='title title--size-l text--center']");
    private final By stakingXpath             = By.xpath("//h1[contains(text(), ' Staking ')]");
    private final By dAppsMenu                = By.xpath("//a[contains(text(), 'DApps')]");
    private final By swapButton               = By.xpath("//div[contains(text(), ' Swap ')]");
    private final By kaizenButton             = By.xpath("//h3[contains(text(), 'Kaizen Finance')]");
    private final By connectWallet            = By.xpath("//div[@class='connect']");
    private final By closeWalletConnect       = By.id("walletconnect-qrcode-close");
    static String kaizenMessage  			  = "Kaizen Finance";
    static String swapMessage  				  = "Swap";
    static String stakingMessage  			  = " Staking ";







    public void scrollDowmToKaidzen(){
        switchTabs1();
        WebElement element = driver.findElement(kaidzenLink);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        waitThenClick(kaidzenLink);
        switchTabs3();
        click(kaidzenLabel);
    }

    public void scrollDownToWallet(){

        WebElement element = driver.findElement(sugoiWalletLink);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        waitThenClick(sugoiWalletLink);
        switchTabs2();
    }

    public void verifyAccountAuthorization(String email) {
        waitForLoad(profileLabel);
        Assert.assertTrue(isAvailable(By.xpath(String.format(accountLabelXpath, email))));
    }

    public void turnToAirDrop(){
        waitThenClick(airDropButton);
        waitForLoad(airDropLabel);
    }
    public void turnToAdastra() throws AWTException {
        waitThenClick(defiButton);
        waitForLoad(adastraButton);
        await(2000);
        waitThenClick(adastraButton);
        switchTabs1();
        click(hyperIDLabel);
    }

    public void getAIOTCheck(){
        waitThenClick(getAIOTButton);
        String message = driver.findElement(swapXpath).getText();
        Assert.assertEquals(message,swapMessage);
    }
    public void connectWallet(){
        waitForLoad(connectWallet);
        waitThenClick(connectWallet);
        waitForLoad(closeWalletConnect);
        waitThenClick(closeWalletConnect);
    }

    public void getStakingMenuAndStart(){
        waitThenClick(stakingButton);
        waitThenClick(startStakingButton);
        switchTabs1();

    }

    public void getKaizenFromWallet(){
        waitThenClick(dAppsMenu);
        waitThenClick(kaizenButton);
        await(3000);
        waitForLoad(swapXpath);
        String message = driver.findElement(swapXpath).getText();
        Assert.assertEquals(message,kaizenMessage);


    }




}
