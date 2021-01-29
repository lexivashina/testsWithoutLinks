package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static HSUtils.eUtils.areDatesEquals;
import static HSUtils.eUtils.areEmailEquals;

public class eAccountPage extends eCommonMethods {
    eAccountPage(WebDriver driver) {
        super(driver);
    }
    //region Locators
    private final By profileLabel                              = By.xpath("//span[contains(text(), 'Profile')]");
    private final By logOutSugoiButton                         = By.xpath("//button[contains(@class, 'btn btn-link logout')]");
    private final By logOutHyperIDButton                       = By.xpath("//span[contains(@class, 'glyphicon-exit')]");
    private final By dashboardPageHeader                       = By.xpath("//h1[contains(text(), 'Dashboard - Jasmy')]");
    private final By referralTableBody                         = By.xpath("//table[contains(@class, 'table table-striped table-bordered')]/tbody");
    private final By referralTableRow                          = By.xpath("//tr");
    private final By referralTableColumn                       = By.xpath("//td");
    private String accountLabelXpath                           = "//span[contains(text(), '%s')]";
    private final By copyReferralLinkButton                    = By.xpath("//button[contains(text(),'Copy Referral Link')]");
    private final By copyReferralCodeButton                    = By.xpath("//button[contains(text(),'Copy Referral Code')]");
    private final By referralCodeInputField                    = By.id("referralcodeform-referral_code");
    private final By referralCodeApplyButton                   = By.xpath("//button[contains(text(), 'Apply')]");
    private final By referralMenuButton                        = By.xpath("//span[contains(text(), 'Referral')]");
    private final By invitedByCode                             = By.id("invited-by-code");
    private final By passwordMenu                              = By.xpath("//a[contains(text(), 'Password')]");
    private final By mainPasswordInputField_SignUp             = By.id("password");
    private final By newPasswordInputField_SignUp              = By.id("password-new");
    private final By newPasswordConfirmationInputField_SignUp  = By.id("password-confirm");
    private final By saveButton                                = By.xpath("//button[contains(text(), 'Save')]");
    private final By profileMenuButton                         = By.xpath("//span[contains(text(), 'Profile')]");
//endregion

    //region Common methods
    public void verifyAccountAuthorization(String email) {
        waitForLoad(profileLabel);
        Assert.assertTrue(isAvailable(By.xpath(String.format(accountLabelXpath, email))));
    }

    public void waitUntilPageLoaded()
    {
        waitForLoad(dashboardPageHeader);
    }

    public void logOutSugoi()
    {
        //HACK: If user logging out too fast, sometimes after page update he still displayed as logged-in
        await(2000);
        click(logOutSugoiButton);
    }

    public void logOutHyperID()
    {
        //HACK: If user logging out too fast, sometimes after page update he still displayed as logged-in
        await(2000);
        click(logOutHyperIDButton);
    }
//endregion

    //region Referral

    public void changePassword(String oldPassword, String newPassword)
    {
        await(500);
        waitThenClick(passwordMenu);
        clearThenInput(mainPasswordInputField_SignUp,oldPassword);
        clearThenInput(newPasswordInputField_SignUp,newPassword);
        clearThenInput(newPasswordConfirmationInputField_SignUp,newPassword);
        waitThenClick(saveButton);
    }

    public void openReferralMenu()
    {
        await(1000);
        waitThenClick(referralMenuButton);
    }

    public void openUserProfile()
    {
        await(1000);
        waitThenClick(profileMenuButton);
    }

    public void applyReferralCode(String code)
    {
        await(1000);
        clearThenInput(referralCodeInputField, code);
        click(referralCodeApplyButton);
    }

    public String getReferralLink() throws UnsupportedFlavorException, IOException
    {
        waitThenClick(copyReferralLinkButton);
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }
    public String getReferralCode() throws UnsupportedFlavorException, IOException
    {
        waitThenClick(copyReferralCodeButton);
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }
    public String getInvitedByCode()
    {
        return driver.findElement(invitedByCode).getText();
    }

    public void pasteReferralLink(String referralLink)
    {
        driver.get(referralLink);
    }

    public boolean checkInvitedPerson(Date expectedDate, String expectedEmail, int invitedUsersExpected)
    {
        await(1000);
        waitThenClick(referralMenuButton);

        List<Referral> referrals = getReferralList();

        if(areDatesEquals(referrals.get(0).getJoinDate(), expectedDate) &
                referrals.get(0).getInvitedUsers() == invitedUsersExpected &
                areEmailEquals(referrals.get(0).getUserEmail(), expectedEmail))
        {
            return true;
        }
        return false;
    }

    public List<Referral> getReferralList() {
        List<Referral> referrals = new ArrayList<>();

	    WebElement referralTableBodyWebElement = driver.findElement(referralTableBody);
        List<WebElement> tableRows = referralTableBodyWebElement.findElements(referralTableRow);
        for (WebElement element:tableRows) {
            List<WebElement> tableColumns = element.findElements(referralTableColumn);
                Referral ref = new Referral();
                ref.setUserEmail(tableColumns.get(0));
                ref.setInvitedUsers(tableColumns.get(1));
                ref.setJoinDate(tableColumns.get(2));
                referrals.add(ref);
        }
        return referrals;
    }
    //endregion
}
//=========================================================================================================================================
// Stores info from referral table
//=========================================================================================================================================
class Referral {
    private String userEmail ="";
    private int invitedUsers = -1;
    private Date joinDate;

    Referral(){}

    public void setInvitedUsers(WebElement invitedUsers) { this.invitedUsers =  Integer.parseInt(invitedUsers.getText());}

    public void setJoinDate(WebElement joinDate) {
        try {
            this.joinDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(joinDate.getText().replace("UTC",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setUserEmail(WebElement userEmail) { this.userEmail = userEmail.getText(); }

    public Date getJoinDate() { return joinDate;}

    public int getInvitedUsers() {return invitedUsers;}

    public String getUserEmail() { return userEmail;}
}