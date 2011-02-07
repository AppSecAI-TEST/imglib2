package script.imglib.test;

import mpicbg.imglib.image.Image;
import mpicbg.imglib.type.numeric.integer.UnsignedByteType;
import script.imglib.ImgLib;
import script.imglib.analysis.Histogram;

public class TestPlot {
	static public final void main(String[] args) {
		//String src = "http://imagej.nih.gov/ij/images/bridge.gif";
		String src = "/home/albert/Desktop/t2/bridge.gif";
		
		Image<UnsignedByteType> img = ImgLib.<UnsignedByteType>open(src);

		try {
			 // Show the histogram in a JFrame
			new Histogram<UnsignedByteType>(img).asChart(true);
			
			// Show the histogram as an imglib image in a virtual ImageJ display
			ImgLib.wrap(new Histogram<UnsignedByteType>(img).asImage()).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}