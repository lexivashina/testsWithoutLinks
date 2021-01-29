package CWUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class eHSProperties {

    private static final Properties testDataProperties = new Properties();

    public static final String defaultBrowserName = "Chrome";
    

    static
    {
        try
        {
            InputStream inputStream = eHSProperties.class.getClassLoader().getResourceAsStream("testData.properties");

            //random email
            int int_random = abs(ThreadLocalRandom.current().nextInt());
            testDataProperties.put("TestEmail", "NewTestEmail"+int_random+"@gmail.com" );
            testDataProperties.put("AnotherTestEmail", "NewEmail"+int_random+"@gmail.com" );


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            testDataProperties.load(inputStreamReader);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static String get(String propertyName)   { return testDataProperties.getProperty(propertyName); }

    public static void setProperty(String propertyName, String propertyValue) { testDataProperties.setProperty(propertyName, propertyValue); }


    public static String getMainPageUrl()              { return get("mainPageURL"); }
    public static String getEmail()                    { return get("firstEmailAccount"); }
    public static String getEmailPassword()            { return get("firstEmailPassword"); }

    public static String getWalletName()               { return get("Wallet_Name");}
    public static String getWalletHash()               {return get("WalletHash");}
    public static String getSecret_Phrase()            {return get("Secret_phrase");}
    public static String getNewSecretPhrase()          {return get("NewSecretPhrase");}

    public static String getTransferAmountOfMoney()    {return get("TransferAmountofMoney");}

    public static String getTestEmail()                { return get("TestEmail"); }
    public static String getAnotherTestEmail()         { return get("AnotherTestEmail"); }

    public static String getSecondEmail()              { return get("secondEmailAccount"); }
    public static String getSecondEmailPassword()      { return get("secondEmailPassword"); }


    public static String getWrongEmail()               { return get("wrongEmailAccount"); }
    public static String getIncorrectEmail()           { return get("incorrectEmailAccount"); }
    public static String getWrongPassword()            { return get("wrongEmailPassword"); }
}
