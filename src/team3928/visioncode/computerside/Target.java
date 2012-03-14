package team3928.visioncode.computerside;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;


public class Target 
{
	private static IplImage originImage;
	private CvRect bBox;
	
	public Target()
	{
		//empty constructor for a target class
	}
	
	public Target(CvRect box, IplImage img)
	{
		originImage = img;
		bBox = box;
	}
	
	public void drawTarget()
	{
		cvRectangle(originImage, cvPoint(bBox.x(), bBox.y()), cvPoint(bBox.x() + bBox.width(), bBox.y() + bBox.height()), CvScalar.BLUE	, 2, 8, 0);
	}
}
