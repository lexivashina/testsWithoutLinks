package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.awt.*;

import static CWUtils.eHSProperties.*;

public class eMainPage extends eCommonMethods {
    private final By signInButton             = By.xpath("//button[contains(text(),'HyperID')]");

    eMainPage(WebDriver driver) {
        super(driver);
    }

    public void open()
    {
        driver.navigate().to(getMainPageUrl());
    }
    public void  Refresh()
    {
        driver.navigate().refresh();
    }

    public void openAuthWindow()
    {
        Refresh();
        open();
        waitThenClick(signInButton);
    }
}
