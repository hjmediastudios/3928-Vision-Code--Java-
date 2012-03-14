package team3928.visioncode.computerside;
import com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;



public class Constants
{
	public static final int THRESHOLD_HUE_MIN = 34;
	public static final int THRESHOLD_HUE_MAX = 79;
	public static final int THRESHOLD_SAT_MIN = 133;
	public static final int THRESHOLD_SAT_MAX = 255;
	public static final int THRESHOLD_VAL_MIN = 63;
	public static final int THRESHOLD_VAL_MAX = 255;
	
	public static final int THRESHOLD_DILATION_ITERATIONS = 4;
	
	public static final int FILTER_TARGETS_THRESHOLD_AREA = 300;
}
