package team3928.visioncode.computerside;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;


public class Main {
	
	static IplImage frame;
	static IplImage frameThreshed;
	static CvCapture camera;

	public static void main(String[] args)
	{

		//Initialize stuff
		camera = cvCreateFileCapture("TestImages/capture.avi");
		if (camera == null)
		{
			System.out.println("ERROR: Capture not initialized.");
		}
		
		while (true)
		{
			frame = cvQueryFrame(camera);
			if (frame == null)
			{
				System.out.println("ERROR: No frame found.");
			}
			
			frameThreshed = Thresholding.thresholdForGreen(frame);
			CvSeq contours = Thresholding.detectContours(frameThreshed);
			
			
			cvShowImage("Threshold", frameThreshed);

			
			
			
			cvWaitKey(0);
		} //end main loop
	}
}
