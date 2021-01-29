package STUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Stream;

import STUtils.eDriverHandler.Device;

import static STUtils.eDriverHandler.getDeviceName;

public class eSTProperties
{
	public enum VerificationType {NOT_VERIFIED_AUTHORIZED, NOT_VERIFIED_UNAUTHORIZED, VIA_CALL_AUTHORIZED, VIA_CALL_UNAUTHORIZED, VIA_BLUETOOTH_AUTHORIZED, VIA_BLUETOOTH_UNAUTHORIZED}

    private static Properties configProperties = new Properties();
    private static Properties testDataProperties = new Properties();

    public enum CountryCode { Kiribaty, Canada }
    public enum PromoCode { PREPAID, DISCOUNT}
    public enum SubscriptionState { TRIAL, FREE, PRO }
    public enum StealthTalkAppVersion { CUSTOMER, ENTERPRISE }
    public enum EnterpriseBillingState { ACTIVE, EXPIRED }

    static
    {
        try
        {
            InputStream inputStream = eSTProperties.class.getClassLoader().getResourceAsStream("config.properties");
            configProperties.load(inputStream);
            inputStream = eSTProperties.class.getClassLoader().getResourceAsStream("testData.properties");
            testDataProperties.load(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String lastExecutedTestName = "";

    public static String getDefaultPasscode()
    {
        return testDataProperties.getProperty("default4digitPass");
    }

    public static String getNew4digitPass()
    {
        return testDataProperties.getProperty("new4digitPass");
    }

    public static String getNew6digitPass()
    {
        return testDataProperties.getProperty("new6digitPass");
    }

    public static String getNewCustomNumPass()
    {
        return testDataProperties.getProperty("newCustomNumPass");
    }

    public static String getNewCustomAlphaNumPass()
    {
        return testDataProperties.getProperty("newCustomAlphaNumPass");
    }

    public static String getVerificationCode()
    {
        return testDataProperties.getProperty("verificationCode");
    }

    public static String getStealthTalkPackage()
    {
        return configProperties.getProperty("stealthTalkAppPackage");
    }

    public static String getStealthTalkActivity()
    {
        return configProperties.getProperty("stealthTalkAppActivity");
    }

    public static String getEnterpriseAppPath()
    {
        return configProperties.getProperty("latestStealthTalkEnterpriseInstallerLink");
    }

    public static String getAppPath()
    {
        return configProperties.getProperty("latestStealthTalkInstallerLink");
    }

    static String getAppRegistrationActivity()
    {
        return configProperties.getProperty("appRegistrationActivity");
    }

    public static String getSendPictureId()
    {
        return configProperties.getProperty("sentPictureId");
    }

    public static String getReceivedPictureId()
    {
        return configProperties.getProperty("receivedPictureId");
    }

    public static String getTestContactNameFirstName()
    {
        return testDataProperties.getProperty("testContactNameFirstName");
    }

    public static String getTestContactNameSecondName()
    {
        return testDataProperties.getProperty("testContactNameSecondName");
    }

    public static String getProperty(String propertyName)
    {
        return testDataProperties.getProperty(propertyName);
    }

    public static String getConfigProperty(String propertyName)
    {
        return configProperties.getProperty(propertyName);
    }

	public static String getFirstDeviceProperty(String propertyName)
	{
		String firstDevice = getProperty("firstDevice");
		return getConfigProperty(firstDevice + propertyName);
	}

    public static String getSecondDeviceProperty(String propertyName)
    {
        String secondDevice = getProperty("secondDevice");
        return getConfigProperty(secondDevice + propertyName);
    }

    public static String getThirdDeviceProperty(String propertyName)
    {
        String thirdDevice = getProperty("thirdDevice");
        return getConfigProperty(thirdDevice + propertyName);
    }

    public static String getDevicePhoneNumber(Device device)
    {
        return getConfigProperty(getDeviceName(device) + "PhoneNumber");
    }

	public static String getDeviceFormattedPhoneNumber(Device device)
	{
		return getConfigProperty(getDeviceName(device) + "FormattedPhoneNumber");
	}

	public static String getDeviceCountry(Device device)
	{
		return getConfigProperty(getDeviceName(device) + "Country");
	}

	public static String getGeneratedPhoneNumber() { return getGeneratedPhoneNumber(CountryCode.Canada); }

	public static String getGeneratedUsername()
    {
        return "Test " +  String.valueOf(new Random().nextInt(8999) + 1000);
    }

	public static String getGeneratedPhoneNumber(CountryCode countryCode)
    {
        String operatorsCode = "";
        String number = "";

        switch (countryCode)
        {
            case Canada:
                operatorsCode = "204300";
                number = String.valueOf(new Random().nextInt(8999) + 1000);
                break;
            case Kiribaty:
                operatorsCode = "720013";
                number = String.valueOf(new Random().nextInt(89) + 10);
                break;
        }

        String generatedPhoneNumber = operatorsCode + number;
        return generatedPhoneNumber;
    }

	@Deprecated
	public static String getPhoneNumberForNewUserRegistration()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
		switch (currentDay)
		{
			case 1:
			case 3:
			case 5:
				return getProperty("phoneNumberForFirstRegistrationTesting1");
			case 2:
			case 4:
			case 6:
				return getProperty("phoneNumberForFirstRegistrationTesting2");
			default:
				return getProperty("phoneNumberForFirstRegistrationTesting3");
		}
	}
}
