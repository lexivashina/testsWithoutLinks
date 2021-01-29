package HSUtils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class eRetryAnalyzer implements IRetryAnalyzer {
    private int rerunCount = 0;
    private int maxRerunCount = 1;

    @Override
    public boolean retry(ITestResult iTestResult)
    {
        if (!iTestResult.isSuccess())
        {
            if (rerunCount < maxRerunCount)
            {
                iTestResult.getThrowable().printStackTrace();
                System.out.println(
                        "Rerun method " +
                                iTestResult.getMethod().getTestClass().getRealClass().getSimpleName() +
                                "." +
                                iTestResult.getMethod().getMethodName());
                rerunCount++;
                iTestResult.setStatus(ITestResult.SKIP);
                return true;
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        }
        return false;
    }
}
