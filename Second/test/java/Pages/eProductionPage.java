package Pages;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;

import static STUtils.eSTProperties.getConfigProperty;

public class eProductionPage extends eCommonMethods {
    public eProductionPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    private By searchPanel                      = By.xpath("//android.widget.TextView[contains(@text, 'Search for apps & games')]");
    private By searchInputField                 = By.xpath("//android.widget.EditText[contains(@text, 'Search for apps & games')]");
    private By installButton                    = By.xpath("//android.widget.Button[contains(@text, 'Install')]");
    private By uninstallButton                  = By.xpath("//android.widget.Button[contains(@text, 'Uninstall')]");
    private By uninstallAlertButton             = By.xpath("//android.view.ViewGroup[android.widget.Button[contains(@text, 'Cancel')]]/android.widget.Button[contains(@text, 'Uninstall')]");
    private By openAppButton                    = By.xpath("//android.widget.Button[contains(@text, 'Open') and @enabled = 'true']");
    private By nextButtonOnBoarding             = By.xpath("//android.view.View/android.widget.Button[resource-id='NEXT' or @resource-id='next']");
    private By continueSpecialOfferButton       = By.xpath("//android.widget.Button[@content-desc='Continue' or @text='Continue']");
    private By stealthTalkGPMenu                = By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]");
    private static String STApp                 = "StealthTalk";
    private static String foundedApp            = "//android.widget.LinearLayout[android.widget.LinearLayout/android.view.View[contains(@content-desc, 'App: %s')]]";

    public void findSTInGooglePlay() { search(STApp); }

    private void search(String app)
    {
        waitThenClick(searchPanel);
        waitForLoad(searchInputField);
        input(searchInputField, app);
        performSearch();
      //  waitThenClick(By.xpath(String.format(foundedApp, app)));
    }
    private void performSearch()
    {
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
    }
    public void installApp()
    {
        deleteAppIfPossible();
        waitThenClick(installButton);
        waitForLoad(openAppButton);
    }
    private void deleteAppIfPossible()
    {
        waitForAnyElementLong(installButton, uninstallButton);
        if (isPresent(uninstallButton))
        {
            click(uninstallButton);
            waitThenClick(uninstallAlertButton);
            waitForLoad(installButton);
        }
    }
    public void verifySTInstall()
    {
        waitThenClick(openAppButton);
        waitForAnyElementLong(nextButtonOnBoarding, continueSpecialOfferButton);
    }
    public void uninstallApp()
    {
        driver.activateApp(getConfigProperty("googlePlayAppPackage"));
        waitForAnyElementShort(openAppButton);
        waitThenClick(stealthTalkGPMenu);
        deleteAppIfPossible();
    }
}
