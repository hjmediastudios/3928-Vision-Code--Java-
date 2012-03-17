package team3928.visioncode.computerside;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;


public class Target 
{
	private IplImage originImage;
	private CvRect bBox;
	private CvSeq contour;
	
	public Target()
	{
		//empty constructor for a target class
	}
	
	public Target(CvSeq ctr, IplImage img)
	{
		contour = ctr;
		originImage = img;
		bBox = cvBoundingRect(ctr, 1);
	}
	
	public void drawTarget(CvScalar color)
	{
		cvRectangle(originImage, cvPoint(bBox.x(), bBox.y()), cvPoint(bBox.x() + bBox.width(), bBox.y() + bBox.height()), color	, 2, 8, 0);
		cvCircle(originImage, getCenter(), 3, color, 2, 8, 0);
		cvLine(originImage, getCenter(), cvPoint(getCenter().x(), 240), color, 1, 8, 0);
		cvLine(originImage, getCenter(), cvPoint(320, getCenter().y()), color, 1, 8, 0);
	}
	
	public int getLeftX()
	{
		return bBox.x();
	}
	public int getRightX()
	{
		return bBox.x() + bBox.width(); 
	}
	public int getTopY()
	{
		return bBox.y();
	}
	public int getBottomY()
	{
		return bBox.y() + bBox.height();
	}
	
	public CvPoint getCenter()
	{
		return cvPoint(getLeftX()+(bBox.width()/2), getTopY()+(bBox.height()/2));
	}
	
	public int getWidth()
	{
		return bBox.width();
	}
	public int getHeight()
	{
		return bBox.height();
	}
	public int getArea()
	{
		return bBox.width() * bBox.height();
	}
	public double getAspectRatio()
	{
		return (double) bBox.width() / bBox.height();
	}
	public double getRectangularity()
	{
		return (cvContourArea(contour, CV_WHOLE_SEQ, 0)/getArea()) * 100;
	}
	
	public int navInfo_getXOffset()
	{
		return getCenter().x() - (originImage.cvSize().width() / 2);
	}
	
	public double navInfo_getDistance()
	{
		return 0.5; //TODO add calculation after calibration
	}
}
