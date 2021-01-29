package STUtils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static STUtils.eDriverHandler.*;
import static STUtils.eSTProperties.getConfigProperty;
import static STUtils.eUtils.await;

public class eTestListener implements ITestListener, IRetryAnalyzer, ISuiteListener
{
    private int retryCount;
    private int maxRetryCount = 1;
    private static Path pathToLogs;

    @Override
    public boolean retry(ITestResult iTestResult)
    {
        if (retryCount < maxRetryCount)
        {
            iTestResult.getThrowable().printStackTrace();
            System.out.println(
                    "Rerun method " + iTestResult.getMethod().getTestClass().getRealClass().getSimpleName()
                            + "." + iTestResult.getMethod().getMethodName());
            retryCount++;
            return true;
        }
        return false;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {}

    @Override
    public void onTestSuccess(ITestResult iTestResult)
    {
        saveMethodName(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult)
    {
        saveMethodName(iTestResult);
        createFolders(iTestResult);
        saveLogsAndVideos();
        turnOffDrivers();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult)
    {
        saveMethodName(iTestResult);
        createFolders(iTestResult);
        saveLogsAndVideos();
        turnOffDrivers();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    private void createFolders(ITestResult testResult)
    {
        try
        {
            Path directory = Paths.get("failures\\"
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\\"
                    + testResult.getTestClass().getRealClass().getSimpleName()
                    + "." + testResult.getMethod().getMethodName());
            Files.createDirectories(directory);

            int x = 0;
            while(Files.exists(directory.resolve("Run " + ++x))){}

            pathToLogs = directory.resolve("Run " + x);
            Files.createDirectory(pathToLogs);

        }
        catch (Throwable e) {e.printStackTrace();}
    }

    static void saveLogsAndVideos()
    {
        for(Device device : Device.values())
        {
            if (drivers[device.ordinal()] != null)
            {
                createScreenshot(getDriver(device), getDeviceName(device));
                saveScreenRecording(device);
                saveLogs(device);
                turnOffDriver(device);
            }
        }
    }

    private static void createScreenshot(AndroidDriver driver, String device)
    {
        try
        {
            File file = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, pathToLogs.resolve(device + "\\Screenshot.jpg").toFile());
        }
        catch (Throwable e) {e.printStackTrace();}
    }

    private static void saveScreenRecording(Device device)
    {
        try
        {
            await(10000);
            String video = getDriver(device).stopRecordingScreen();
            byte[] decodedVideo = Base64.getMimeDecoder().decode(video);
            Files.write(pathToLogs.resolve(getDeviceName(device) + "\\Video.mp4"), decodedVideo);
        }
        catch (Throwable e) {e.printStackTrace();}
    }

    private static void saveLogs(Device device)
    {
        try
        {
            Runtime.getRuntime().exec("adb -s "+getDeviceId(device)+" pull storage/emulated/0/Android/data/"+getConfigProperty("stealthTalkAppPackage")+"/logs \""
                    + pathToLogs.toAbsolutePath()+ "\\" + getDeviceName(device)+ "\\Logs\"");
			Runtime.getRuntime().exec("adb -s "+getDeviceId(device)+" pull storage/emulated/0/Android/data/"+getConfigProperty("stealthTalkAppPackage")+"/temp \""
					+ pathToLogs.toAbsolutePath()+ "\\" + getDeviceName(device)+ "\\Logs\"");
        }
        catch (Throwable e) {e.printStackTrace();}
    }

    private static void saveMethodName(ITestResult iTestResult)
    {
        eSTProperties.lastExecutedTestName = iTestResult.getMethod().getTestClass().getRealClass().getSimpleName()
                + "."
                + iTestResult.getMethod().getMethodName();
    }

    @Override
    public void onStart(ISuite iSuite)
    {
        for (ITestNGMethod method : iSuite.getAllMethods())
        {
            method.setRetryAnalyzer(new eTestListener());
        }
    }

    @Override
    public void onFinish(ISuite iSuite)
    {

    }
}
