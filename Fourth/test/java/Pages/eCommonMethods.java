package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class eCommonMethods {
    WebDriver driver;
    Actions actions;

    private final WebDriverWait shortWait;
    private final WebDriverWait regularWait;
    private final WebDriverWait longWait;

    eCommonMethods(WebDriver driver)
    {
        this.driver = driver;
        actions = new Actions(driver);
        shortWait   = new WebDriverWait(driver, 10);
        regularWait = new WebDriverWait(driver, 60);
        longWait    = new WebDriverWait(driver, 300);
    }

    void click(By selector)
    {
        actions.moveToElement(shortWait.until(elementToBeClickable(selector))).click().build().perform();
    }

    void clickIfPossible(By selector)
    {
        if (isAvailable(selector)) click(selector);
    }

    void waitThenClick(By selector)
    {
        regularWait.until(elementToBeClickable(selector));
        click(selector);
    }
    void waitThenClick(By selector, long timespan)
    {
        await(timespan);
        regularWait.until(elementToBeClickable(selector));
        click(selector);

    }

    public WebElement findElement(By selector)
    {
        return shortWait.until(presenceOfElementLocated(selector));
    }

    void input(By selector, String textToInput) { shortWait.until(visibilityOfElementLocated(selector)).sendKeys(textToInput); }

    void clearThenInput(By selector, String text)
    {
        await(500);
        shortWait.until(visibilityOfElementLocated(selector)).clear();
        driver.findElement(selector).sendKeys(text);
    }

    void clear(By locator) { shortWait.until(presenceOfElementLocated(locator)).clear(); }

    void await(long waitValue)
    {
        try{Thread.sleep(waitValue);}
        catch(InterruptedException e){e.printStackTrace();}
    }

    void waitForLoad(By selector)
    {
        regularWait.until(visibilityOfElementLocated(selector));
    }
    void waitForLoadThenClick(By selector)
    {
        waitForLoad(selector);
        click(selector);
    }


    void waitForAnyElement(By ... selectors)
    {
        ExpectedCondition<WebElement>[] conditions = new ExpectedCondition[selectors.length];
        for(int x = 0; x < selectors.length; x++)
        {
            conditions[x] = presenceOfElementLocated(selectors[x]);
        }
        regularWait.until(ExpectedConditions.or(conditions));
    }

    void waitForLoadLong(By selector)
    {
        longWait.until(presenceOfElementLocated(selector));
    }

    void waitForAnyElementLong(By ... selectors)
    {
        ExpectedCondition<WebElement>[] e = new ExpectedCondition[selectors.length];
        for(int x = 0; x < selectors.length; x++) {
            e[x] = presenceOfElementLocated(selectors[x]);
        }
        longWait.until(ExpectedConditions.or(e));
    }

    void waitForLoadShort(By selector)
    {
        shortWait.until(visibilityOfElementLocated(selector));
    }

    void waitForAnyElementShort(By ... selectors)
    {
        ExpectedCondition<WebElement>[] e = new ExpectedCondition[selectors.length];
        for(int x = 0; x < selectors.length; x++) {
            e[x] = presenceOfElementLocated(selectors[x]);
        }
        shortWait.until(ExpectedConditions.or(e));
    }

    void waitUntilElementDisappear(By selector)
    {
        longWait.until(invisibilityOfElementLocated(selector));
    }

    String getText(By selector)
    {
        return shortWait.until(visibilityOfElementLocated(selector)).getText();
    }

    String waitThenGetText(By selector)
    {
        return regularWait.until(visibilityOfElementLocated(selector)).getText();
    }

    String getContent(By selector)
    {
        try
        {
            String text = shortWait.until(presenceOfElementLocated(selector)).getAttribute("contentDescription");
            if (text == null || text.contentEquals("")) text = driver.findElement(selector).getText();
            return text;
        }
        catch (StaleElementReferenceException e)
        {
            return getContent(selector);
        }
    }

    String getAttribute(By selector, String attribute)
    {
        try
        {
            return shortWait.until(presenceOfElementLocated(selector)).getAttribute(attribute);
        }
        catch (StaleElementReferenceException e)
        {
            return getAttribute(selector, attribute);
        }
    }

    void verifyAvailability(By selector)
    {
        List<WebElement> list = driver.findElements(selector);
        Assert.assertNotEquals(list.size(), 0);
    }

    void verifyAbsence(By selector)
    {
        List<WebElement> list = driver.findElements(selector);
        Assert.assertEquals(list.size(), 0);
    }

    void verifyQuantity(By selector, int count)
    {
        List<WebElement> list = driver.findElements(selector);
        Assert.assertEquals(list.size(), count);
    }

    void verifyEmptyField(By locator)
    {
        Assert.assertEquals(driver.findElement(locator).getText(), "");
    }

    boolean isElementDisplayed(By locator)
    {
        boolean visibility;
        try
        {
            visibility = driver.findElement(locator).isDisplayed();
        }
        catch (StaleElementReferenceException exception) { visibility = isElementDisplayed(locator); }
        catch (NoSuchElementException exception) { return false; }
        return visibility;
    }

    boolean isAvailable(By locator)
    {
        List<WebElement> list = driver.findElements(locator);
        if (list.isEmpty()) return false;
        return true;
    }

    boolean isAbsent(By locator)
    {
        List<WebElement> list = driver.findElements(locator);
        if(list.isEmpty())	return true;
        else				return false;
    }
}
