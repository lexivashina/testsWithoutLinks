package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static HSUtils.eHSProperties.getHyperSphereIdPageUrl;
import static HSUtils.eHSProperties.getMainPageUrl;

public class eMainPage extends eCommonMethods {
    private final By mainPageHeader              = By.xpath("//div[contains(@class, 'header__card')]");
    private final By signInUpButton              = By.xpath("//a[contains(@class, 'auth-link')]");
    private final By goToAccountButton           = By.xpath("//a[contains(text(), 'Go to account')]");


    eMainPage(WebDriver driver) {
        super(driver);
    }

    public void openMainPageUrl()
    {
        driver.navigate().to(getMainPageUrl());
        waitForLoad(mainPageHeader);
    }
    public void openHyperSphereIdURL()
    {
        driver.get(getHyperSphereIdPageUrl());
    }

    public void openAuthWindow()
    {
        openMainPageUrl();
        click(signInUpButton);
    }


    public void openAccountPage()
    {
        openMainPageUrl();
        click(goToAccountButton);
    }


}


