package HSUtils;

import org.testng.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static HSUtils.eHSProperties.*;

public class eTestListener implements ITestListener, ISuiteListener {
    eRecorder recorder = new eRecorder();
    @Override
    public void onTestStart(ITestResult iTestResult) {
        saveMethodName(iTestResult);
        recorder.setOutputPath(createFolders());
        recorder.startRecording();

        //startScreenRecording();
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        recorder.stopRecording();
        //stopScreenRecording();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        recorder.stopRecording();
        //stopScreenRecording();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        recorder.stopRecording();
        //stopScreenRecording();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        recorder.stopRecording();
        //stopScreenRecording();
    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    private static void saveMethodName(ITestResult iTestResult)
    {
        eHSProperties.lastExecutedTestName = iTestResult.getMethod().getTestClass().getRealClass().getSimpleName()
                + "_"
                + iTestResult.getMethod().getMethodName();
    }

    private static String createFolders()
    {
        try
        {
            Path directory = Paths.get("Hypersphere_Web_Tests_" + get("browserName") + "\\videoReports\\"
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\\"
                    + lastExecutedTestName);
            Files.createDirectories(directory);

            int x = 0;
            while(Files.exists(directory.resolve("Run_" + ++x))){}

            eHSProperties.pathToTestReportVideo = directory.resolve("Run_" + x).toAbsolutePath().toString();
            Files.createDirectories(Paths.get(pathToTestReportVideo));
        }
        catch (Throwable e) {e.printStackTrace();}
        return pathToTestReportVideo;
    }

    @Override
    public void onStart(ISuite iSuite) {

    }

    @Override
    public void onFinish(ISuite iSuite) {

    }
}