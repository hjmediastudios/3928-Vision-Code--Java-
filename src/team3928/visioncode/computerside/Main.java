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
	static CvFont font = new CvFont(1);

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
					double aspectRatio = (double) bBox.width()/bBox.height();

					if (aspectRatio >= Constants.FILTER_TARGETS_ASPECTRATIO_MIN && aspectRatio <= Constants.FILTER_TARGETS_ASPECTRATIO_MAX) 
					{
						targetSet[numTargets] = new Target(contours, frame);
						numTargets = numTargets + 1;
						System.out.println(numTargets + " targets.");
					}          
				}
				
				contours = contours.h_next();
			}
			
			//Draw crosshairs
			cvLine(frame, cvPoint(0, 240), cvPoint(640, 240), CvScalar.RED, 1, 8, 0);
			cvLine(frame, cvPoint(320, 0), cvPoint(320, 480), CvScalar.RED, 1, 8, 0);
			
			int lowestTargetIndex = -1;
			int currentHighestY = -5000;
			
			if (numTargets > 0)
			{	
				//pick lowest target
				for (int i=0; i<numTargets; i++)
				{
					int currentY = targetSet[i].getCenter().y();
					if (currentY > currentHighestY)
					{
						currentHighestY = currentY;
						lowestTargetIndex = i;
					}
				}
				
				//draw targets
				for (int i=0; i<numTargets; i++)
				{
					targetSet[i].drawTarget(CvScalar.RED);
					cvPutText(frame, " " + targetSet[i].getWidth(), cvPoint(targetSet[i].getLeftX(), targetSet[i].getTopY() - 5), cvFont(1.0, 1), cvScalar(255, 150, 150, 0)); //Width
					cvPutText(frame, " " + targetSet[i].getHeight(), cvPoint(targetSet[i].getLeftX() - 30, targetSet[i].getCenter().y()), cvFont(1.0, 1), CvScalar.GREEN); //Height
					cvPutText(frame, " " + targetSet[i].getArea(), cvPoint(targetSet[i].getRightX(), targetSet[i].getTopY() + 10), cvFont(1.0, 1), CvScalar.YELLOW); //Area
					cvPutText(frame, " " + targetSet[i].getAspectRatio(), cvPoint(targetSet[i].getRightX(), targetSet[i].getTopY() + 22), cvFont(1.0, 1), CvScalar.MAGENTA); //Aspect ratio
					cvPutText(frame, " " + targetSet[i].getRectangularity(), cvPoint(targetSet[i].getRightX(), targetSet[i].getTopY() + 34), cvFont(1.0, 1), CvScalar.BLUE); //Aspect ratio



				}
				
				//highlight highest target
				cvRectangle(frame, cvPoint(targetSet[lowestTargetIndex].getLeftX() - 1, targetSet[lowestTargetIndex].getTopY() - 1), 
						cvPoint(targetSet[lowestTargetIndex].getRightX() + 1, targetSet[lowestTargetIndex].getBottomY() + 1), CvScalar.GREEN, 2, 8, 0);
				
			}


			cvShowImage("Targets", frame);


			cvWaitKey(0);
		} //end main loop
	}
}
