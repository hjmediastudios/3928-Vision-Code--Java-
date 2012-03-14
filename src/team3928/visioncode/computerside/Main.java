package team3928.visioncode.computerside;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import com.googlecode.javacv.cpp.opencv_core.CvScalar;


public class Main {

	static IplImage frame;
	static IplImage frameThreshed;
	static CvCapture camera;
	static Target[] targetSet = new Target[50];
	static int numTargets = 0;

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

			//Select targets
			numTargets = 0;
			
			while (contours != null)
			{
				double area = cvContourArea(contours, CV_WHOLE_SEQ, 0);
				//				cvRectangle(frame, cvPoint(bBox.x(), bBox.y()), cvPoint(bBox.x() + bBox.width(), bBox.y() + bBox.height()), CvScalar.BLUE	, 2, 8, 0);
				if (area > Constants.FILTER_TARGETS_THRESHOLD_AREA)
				{
					CvRect bBox = cvBoundingRect(contours, 1);
					float aspectRatio = (float) bBox.width()/bBox.height();

					if (aspectRatio >= Constants.FILTER_TARGETS_ASPECTRATIO_MIN && aspectRatio <= Constants.FILTER_TARGETS_ASPECTRATIO_MAX) 
					{
						targetSet[numTargets] = new Target(bBox, frame);
						numTargets = numTargets + 1;
						System.out.println(numTargets + " targets.");
					}          
				}
				
				contours = contours.h_next();
			}
			
			//Draw crosshairs
			cvLine(frame, cvPoint(0, 240), cvPoint(640, 240), CvScalar.RED, 1, 8, 0);
			cvLine(frame, cvPoint(320, 0), cvPoint(320, 480), CvScalar.RED, 1, 8, 0);
			
			if (numTargets > 0)
			{
				for (int i=0; i<numTargets; i++)
				{
					targetSet[i].drawTarget(cvScalarAll((255/numTargets)*i));
					System.out.println("Drew Target " + i);
				}
			}


			cvShowImage("Targets", frame);


			cvWaitKey(0);
		} //end main loop
	}
}
