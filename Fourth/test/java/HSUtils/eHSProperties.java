package HSUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class eHSProperties {
    public static String verificationCode;
    public static String lastExecutedTestName;
    public static String pathToTestReportVideo;

    private static final Properties testDataProperties = new Properties();

    public static final String defaultBrowserName = "Chrome";

    static
    {
        try
        {
            InputStream inputStream = eHSProperties.class.getClassLoader().getResourceAsStream("testData.properties");
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

    public static String getMainPageUrl()           { return get("mainPageURL"); }
    public static String getHyperSphereIdPageUrl()  { return get("hyperSphereIdURL"); }

    public static String getEmail()                 { return get("firstEmailAccount"); }
    public static String getEmailPassword()         { return get("firstEmailPassword"); }

    public static String getSecondEmail()           { return get("secondEmailAccount"); }
    public static String getSecondEmailPassword()   { return get("secondEmailPassword"); }

    public static String getWrongEmail()            { return get("wrongEmailAccount"); }
    public static String getWrongPassword()         { return get("wrongEmailPassword"); }

    public static String getRestorePasswordEmail()  { return get("restorePasswordEmail"); }
}
