package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.*;

import static CWUtils.eHSProperties.*;

public class eAccountPage extends eCommonMethods {
    eAccountPage(WebDriver driver) {
        super(driver);
    }

    private final By passwordInputField_SignIn            = By.id("password");
    private final By profileLabel             = By.xpath("//h1[@class = 'text text--center page__title']");
    private final By logOutButton             = By.xpath("//button[contains(text(),'Log out')]");
    private final By moreInfoButton           = By.xpath("//a[@href = '/more']");
    private String accountLabelXpath          = "//span[contains(text(), '%s')]";
    private final By walletImgButton          = By.xpath("//a[@href = '/wallets/list']");
    private final By plusWalletButton         = By.xpath("//a[@href = '/wallets/add']");
    private final By createMultiCoinButton    = By.xpath("//button[contains(text(),'Create multi-coin wallet')]");
    private final By walletNameField          = By.xpath("//*[@id=\"wallet-name\"]");
    private final By createButton             = By.xpath("//button[contains(text(),'Create')]");
    private final By continueButton           = By.xpath("//button[contains(text(),'Continue')]");
    private final By newWallet                = By.xpath("//p[contains(text(), 'Test1234')]");
    private final By currencyChoice           = By.xpath("//p[contains(text(), 'Ethereum')]");
    private final By sendMoneyButtonMP        = By.xpath("//a[@href = '/wallets/send']");
    private final By recepientField           = By.id("recipient");
    private final By secretPhraseField        = By.id("phrase");
    private final By importSecretPhraseButton = By.xpath("//button[contains(text(),'Import')]");
    private final By amountOfMonyeField       = By.id("amount");
    private final By removeNewWallet          = By.xpath("//button[contains(text(),'Remove wallet')]");
    private final By sendMoneyButton          = By.xpath("//button[contains(text(),'Send')]");
    private final By networkFeeArea           = By.xpath("//p[contains(text(), Total)]");
    private final By waitForLoad              = By.xpath("//div[contains(text(), Ethereum)]");
    private final By importMultiCoinButton    = By.xpath("//button[contains(text(),'Import multi-coin wallet')]");
    private final By generalImportWallet      = By.xpath("//button[contains(text(),'Import wallet')]");
    private final By walletNameInput          = By.id("wallet-name");
    private final By importWalletButton       = By.xpath("//button[contains(text(),'Import')]");
    private final By newSecretPhraseXpath     = By.xpath("//p[(@class = 'text text--color-primary text--size-l text--extra-bold')]");
    private final By loadPrivateDataChoice    = By.xpath("//p[contains(text(),'Phrase')]");
    private final By exportWalletButton       = By.xpath("//p[contains(text(),'Export wallet')]");
    private final By walletExportButton       = By.xpath("//p[(@class = 'text text--bold')]");
    private final By exportWayChoicePrivatekey= By.xpath("//p[contains(text(),'Private Key')]");
    private final By exportWayChoiceJSON      = By.xpath("//p[contains(text(),'JSON Keystore')]");
    private final By privateKey               = By.xpath("//p[(@class = 'text text--color-primary text--size-l text--extra-bold private-key')]");
    private final By privateKeyInputField     = By.id("private-key");
    private final By JSONPassword             = By.id("password");
    private final By JSONFile                 = By.xpath("//*[(@alt = 'Browse')]");
    private final By exportJSON               = By.xpath("//button[contains(text(),'Export')]");
    String SecretPhrase = "";
    String PrivateKey = "";

    public void verifyAccountAuthorization() {
        waitForLoad(profileLabel);
    }

    public void logOut()
    {
        click(moreInfoButton);
        click(logOutButton);
    }
    public void pathToNewWalletsMenu()
    {
        waitThenClick(walletImgButton);
        waitThenClick(plusWalletButton);
    }


    public void createNewMultiCoinWallet()
    {
        waitThenClick(createMultiCoinButton);
        clearThenInput(walletNameField, getWalletName());
        waitThenClick(createButton);
        waitForAnyElement(newSecretPhraseXpath);
        SecretPhrase = getElementText(newSecretPhraseXpath); //SecretPhrase
        waitThenClick(continueButton);
        //Another method
        deleteWallet();
        waitThenClick(plusWalletButton);
        //importNewMultiCoinWallet
        importNewMultiCoinWallet();
        //importNewWallet
        pathToNewWalletsMenu();
        importNewWalletByPhrase();


    }
    public void importNewMultiCoinWallet()
    {
        waitThenClick(importMultiCoinButton);
        clearThenInput(walletNameInput, getWalletName());
        clearThenInput(secretPhraseField, SecretPhrase);
        waitThenClick(importWalletButton);
    }
    public void importNewWalletByPhrase()
    {
        waitThenClick(generalImportWallet);
        waitThenClick(currencyChoice);
        waitThenClick(loadPrivateDataChoice);
        clearThenInput(walletNameInput, getWalletName());
        clearThenInput(secretPhraseField, SecretPhrase);
        waitThenClick(importWalletButton);
    }

    public void importNewWalletByPrivateKey()
    {
        wayToExportByPrivateKey();
        PrivateKey = getElementText(privateKey);
        await(5000);
        waitThenClick(continueButton);
        pathToNewWalletsMenu();
        waitThenClick(generalImportWallet);
        waitThenClick(currencyChoice);
        waitThenClick(exportWayChoicePrivatekey);
        clearThenInput(walletNameInput, getWalletName());
        clearThenInput(privateKeyInputField, PrivateKey);
        waitThenClick(importWalletButton);
    }
    public void importNewWalletByJSON() throws AWTException {
        await(500);
        pathToNewWalletsMenu();
        waitThenClick(generalImportWallet);
        waitThenClick(currencyChoice);
        waitThenClick(exportWayChoiceJSON);
        clearThenInput(walletNameInput, getWalletName());
        clearThenInput(JSONPassword, getEmailPassword());
        waitThenClick(JSONFile);
        copyPathToJSONFile();
        pastePathToJSONFile();
        waitThenClick(importWalletButton);
        waitForAnyElement(moreInfoButton);
    }


    private void wayToExportByPrivateKey() {
        waitThenClick(moreInfoButton);
        waitThenClick(exportWalletButton);
        waitThenClick(walletExportButton);
        waitThenClick(currencyChoice);
        waitThenClick(exportWayChoicePrivatekey);
        await(1000);
        waitForAnyElement(privateKey);
    }

    public void wayToExportByJSON() {
        waitThenClick(moreInfoButton);
        waitThenClick(exportWalletButton);
        waitThenClick(walletExportButton);
        waitThenClick(currencyChoice);
        waitThenClick(exportWayChoiceJSON);
        await(1000);

        clearThenInput(passwordInputField_SignIn, getEmailPassword());
        waitThenClick(exportJSON);
        await(13000);

        waitThenClick(continueButton);
    }


    public void deleteWallet()
    {
        waitThenClick(walletImgButton);
        waitThenClick(newWallet);
        waitThenClick(removeNewWallet);
    }

    public void recepientField(String etherium_Wallet){clearThenInput(recepientField, etherium_Wallet);}

    public void secretPhraseInput(String Secret_Phrase)
    {
        clearThenInput(secretPhraseField, Secret_Phrase);
        waitThenClick(importSecretPhraseButton);
    }
    public void amountOfMoneyInput_Send(String getAmountOfMoney)
    {
        waitForAnyElementLong(networkFeeArea);
        await(2000);
        clearThenInput(amountOfMonyeField, getAmountOfMoney);
        await(500);
        waitThenClick(sendMoneyButton);
    }

    public void sendMoney()
    {
        await(1000);
        waitThenClick(sendMoneyButtonMP);
        await(1000);
        waitThenClick(currencyChoice);
        await(1000);
        secretPhraseInput(getSecret_Phrase());
        await(1000);
        waitForAnyElement(waitForLoad);
        await(1000);
        waitThenClick(sendMoneyButtonMP);
        await(1000);
        waitThenClick(currencyChoice);
        await(1000);
        recepientField(getWalletHash());
        await(1000);
        amountOfMoneyInput_Send(getTransferAmountOfMoney());

    }
}
