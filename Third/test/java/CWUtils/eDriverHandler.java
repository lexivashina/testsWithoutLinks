package CWUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static CWUtils.eHSProperties.*;

public class eDriverHandler {
    static WebDriver driver;


    public static WebDriver getDriverIfNotExist(String browserName)
    {
        setProperty("browserName", browserName);
        DesiredCapabilities capabilities;

        switch (browserName){
            case "Chrome":
                if (driver == null)
                {
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("no-sandbox");
                    driver = new ChromeDriver(options);
                }
                else {
                    if(driver.toString().contains("Firefox")) {
                        driver.quit();
                        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("no-sandbox");
                        driver = new ChromeDriver(options);
                    }

                }
            break;
            case "Firefox":
                if (driver == null)
                {
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
                    capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability("marionette",true);
                    FirefoxOptions options = new FirefoxOptions();
                    options.merge(capabilities);
                    driver= new FirefoxDriver(options);
                }
                else {
                    if(driver.toString().contains("Chrome"))
                    {
                        driver.quit();
                        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
                        capabilities = DesiredCapabilities.firefox();
                        capabilities.setCapability("marionette",true);
                        FirefoxOptions options = new FirefoxOptions();
                        options.merge(capabilities);
                        driver= new FirefoxDriver(options);
                    }
                }
            break;
        }
        return driver;
    }



    public static WebDriver getDriver(String browserName) {
        setProperty("browserName", browserName);

        DesiredCapabilities capabilities;

        switch (browserName)
        {
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "Firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
                capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette",true);
                driver= new FirefoxDriver(capabilities);
                break;
        }

        return driver;
    }

    public static void turnOffDriver() { driver.quit(); }
}
