package Tests;

import Pages.eBrowserPage;
import org.testng.annotations.*;

import static HSUtils.eHSProperties.defaultBrowserName;

public class Base_Test {
    protected eBrowserPage browser = new eBrowserPage();

    @Parameters({"browserName"})
    @BeforeMethod
    public void setUp(@Optional(defaultBrowserName) String browserName) { browser.setUp(browserName); }

    @AfterSuite
    public void tearDown()
    {
        browser.tearDown();
    }

}
