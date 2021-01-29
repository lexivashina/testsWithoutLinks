package HSUtils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static HSUtils.eHSProperties.*;

public class eUtils {
    private static Process screenRecordingProcess;

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

    public static void startScreenRecording()
    {
        ProcessBuilder screenRecordingProcessBuilder = new ProcessBuilder("cmd", "/c", "ffmpeg -f gdigrab -framerate 30 -video_size hd1080 -i desktop " + pathToTestReportVideo + "\\videoReport.avi", "-y");

        try {
            screenRecordingProcess = screenRecordingProcessBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopScreenRecording()
    {
        OutputStream outputStream = screenRecordingProcess.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        try {
            outputStreamWriter.write("q");
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        screenRecordingProcess.destroy();
    }
}
