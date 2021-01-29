package HSUtils;

import org.openqa.selenium.InvalidArgumentException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class eRecorder {

	private static Process screenRecordingProcess;
	ProcessBuilder processBuilder = new ProcessBuilder();

	private final String basePath =  new File("").getAbsolutePath()+"\\ffmpeg\\bin\\";
	private final String recorderPath = basePath+"ffmpeg.exe";
	private String recorderArgs = "";
	private String videoOutputPath = "";


	public eRecorder()
	{
		initArgsByDefault();
	}

//region Public methods
	public void overrideRecorderArgs(String [] recorderArgs)
	{
		String newArgs = "";
		for (int i=0; i<recorderArgs.length; i++)
		{
			if (!recorderArgs[i].startsWith("-")) throw new InvalidArgumentException("Recorder argument incorrect. Invalid argument: "+recorderArgs[i]+".");
			else
				newArgs+=recorderArgs[i]+" ";
		}
		this.recorderArgs = newArgs;
	}

	public void setOutputPath(String path)
	{
		if (!path.equals("")) { videoOutputPath=" "+path+"\\videoReport.mkv"; }
		else
			setVideoOutputPathByDefault();
	}
	public void startRecording()
	{
		//eHSProperties.lastExecutedTestName
		// Run this on Windows, cmd, /c = terminate after this run
		processBuilder.command("cmd","/c",recorderPath + recorderArgs + videoOutputPath);
		try {
			screenRecordingProcess = processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void stopRecording()
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
//endregion

//region Private methods
	private void initArgsByDefault()
	{
		if (recorderArgs.isEmpty()) {setRecorderArgsByDefault();}
		if (videoOutputPath.isEmpty()) {setVideoOutputPathByDefault();}
	}
	private void setRecorderArgsByDefault()
	{
		this.recorderArgs = " -f gdigrab -framerate 30 -video_size hd1080 -i desktop";
	}
	private void setVideoOutputPathByDefault()
	{
		this.videoOutputPath = " C:";
	}
//endregion Private methods


}
