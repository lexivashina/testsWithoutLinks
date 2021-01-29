package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public class eGooglePhotosPage extends eCommonMethods
{
    By photoToSent			= By.xpath("(//*[contains(@content-desc, 'Photo taken')])[1]");
    By albumsButton			= By.xpath("//*[@content-desc[contains(.,'Albums')]]");
    By firstAlbum			= By.xpath("(//*[@resource-id[contains(.,'com.google.android.apps.photos:id/album_cover_view')]])[1]");
    By share				= MobileBy.AccessibilityId("Share");
    By shareWithEvetalk = By.xpath("//android.widget.TextView[@text='Eve Talk']");
    By scrollingAppForShare	= By.xpath("//*[@resource-id='com.google.android.apps.photos:id/peoplekit_third_party_scroll_view']"); //TODO Check on different models
    By photosButton			= By.id("com.google.android.apps.photos:id/tab_photos");

    public eGooglePhotosPage(AndroidDriver<AndroidElement> driver)
    {
        super(driver);
    }

    public void scrollAppForSharingAndClick()
    {
        waitForLoad(scrollingAppForShare);
        MobileElement element = driver.findElement(scrollingAppForShare);
        Dimension scrollingAppForShareSize = element.getSize();
        Point scrollingAppForShareLocation = element.getLocation();

        while (returnList(shareWithEvetalk).isEmpty()) {
            PointOption startPoint = PointOption.point(scrollingAppForShareLocation.getX() + scrollingAppForShareSize.width / 5 * 4,
                    scrollingAppForShareLocation.getY() + scrollingAppForShareSize.height / 2);
            PointOption finishPoint = PointOption.point(scrollingAppForShareLocation.getX() + scrollingAppForShareSize.width / 5,
                    scrollingAppForShareLocation.getY() + scrollingAppForShareSize.height / 2);
            new TouchAction<>(driver).press(startPoint).waitAction().moveTo(finishPoint).release().perform();
        }
        click(shareWithEvetalk);
    }

    public eMainPage sharePicture()
	{
	    By notNowButton = By.id("com.google.android.apps.photos:id/negative_button");
	    waitForAnyElementShort(photosButton, notNowButton);
	    if (isPresent(notNowButton)) click(notNowButton);
        click(photosButton);
        click(photoToSent);
        click(share);
        scrollAppForSharingAndClick();
        return new eMainPage(driver);
    }

}
