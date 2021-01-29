package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static HSUtils.eHSProperties.getAdastraPageUrl;
import static HSUtils.eHSProperties.getMainPageUrl;

public class eMainPage extends eCommonMethods {
    private final By mainPageHeader = By.xpath("//div[contains(@class, 'header__card')]");
    private final By signInUpButton = By.xpath("//div[contains(text(), 'Sign in')]");


    eMainPage(WebDriver driver) {
        super(driver);
    }

    public void open()
    {
        driver.get(getMainPageUrl());
        driver.manage().window().maximize();
        waitForLoad(mainPageHeader);
    }

    public void openAuthWindow()
    {
        open();
        waitThenClick(signInUpButton);
    }
    public void openAdastra()
    {
        driver.get(getAdastraPageUrl());
        driver.manage().window().maximize();
    }


}
