package HSUtils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static java.lang.Math.*;

public class eUtils {

    public static String getRandomEmail()
    {
        int int_random = abs(ThreadLocalRandom.current().nextInt());
        return int_random+"test"+"@outlook.com";
    }

    public static void createFolderForAllureResults(String folderName)
    {
        try
        {
            Path directory = Paths.get(folderName + "\\allure-results");
            Files.createDirectories(directory);
        }
        catch (Throwable e) {e.printStackTrace();}
    }

    public static void saveAllureResults(String folderName)
    {
        File srcDir = new File("allure-results");
        File destDir = new File(folderName + "\\allure-results");
        try {
            FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executeInTerminal(String command)
    {
        class StreamGobbler implements Runnable {
            private InputStream inputStream;
            private Consumer<String> consumer;

            public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
                this.inputStream = inputStream;
                this.consumer = consumer;
            }

            @Override
            public void run() {
                new BufferedReader(new InputStreamReader(inputStream)).lines()
                        .forEach(consumer);
            }
        }

        try
        {
            Process process = Runtime.getRuntime().exec(command);
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);

            process.waitFor();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public String getRandomVerificationCode(int verificationCodeLength)
    {
        int leftLimit = 33; // symbol '!'
        int rightLimit = 126; // letter '~'
        Random random = new Random();

        String generatedVerificationCode = random.ints(leftLimit, rightLimit + 1)
                .limit(verificationCodeLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedVerificationCode;
    }

    public String getRandomVerificationCode() { return getRandomVerificationCode(10); }

    public static Date getCurrentDateTime()
    {
        Date dateNow = new Date();
        TimeZone.setDefault( TimeZone.getTimeZone("UTC"));
        return dateNow;
    }
    public static boolean areEmailEquals(String actualEmail, String expectedEmail)
    {
        actualEmail = actualEmail.replace("Referral: ","");

        String firstPartOfEmail = expectedEmail.substring(0,3);
        String secondPartOfEmail = expectedEmail.split("@")[1];
        if (actualEmail.contains(firstPartOfEmail) && actualEmail.contains(secondPartOfEmail))
            return true;
        else
            return false;
    }

    public static boolean areDatesEquals(Date expectedDate, Date actualDate)
    {
        long differenceInMinutes = ((expectedDate.getTime() - actualDate.getTime()) / (1000 * 60)) % 60;
        return  differenceInMinutes < 2;
    }


}
