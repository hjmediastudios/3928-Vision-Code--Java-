package team3928.visioncode.computerside;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import com.googlecode.javacpp.Loader;

class Thresholding
{
	public static IplImage thresholdForGreen(IplImage img)
	{
		CvSize imgSize = img.cvSize();
		IplImage imgHSV = cvCreateImage(imgSize, 8, 3);
		IplImage imgThreshed = cvCreateImage(imgSize, 8, 1);
		cvCvtColor(img, imgHSV, CV_BGR2HSV);
		cvInRangeS(imgHSV, cvScalar(Constants.THRESHOLD_HUE_MIN, Constants.THRESHOLD_SAT_MIN, Constants.THRESHOLD_VAL_MIN, 0), cvScalar(Constants.THRESHOLD_HUE_MAX, Constants.THRESHOLD_SAT_MAX, Constants.THRESHOLD_VAL_MAX, 255), imgThreshed);
		for (int i=0; i<Constants.THRESHOLD_DILATION_ITERATIONS; i++)
			cvDilate(imgThreshed, imgThreshed, null, 1);
		for (int i=0; i<Constants.THRESHOLD_DILATION_ITERATIONS; i++)
			cvErode(imgThreshed, imgThreshed, null, 1);
		return imgThreshed;
	}

	public static CvSeq detectContours(IplImage img)
	{
		CvMemStorage contourStorage = cvCreateMemStorage(0);
		CvSeq contours = new CvSeq(null);
		cvFindContours(img, contourStorage, contours, Loader.sizeof(CvContour.class), CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);
		return contours;
	}
	
}