import mpicbg.imglib.cursor.special.StructuringElementCursor;
import mpicbg.imglib.image.Image;
import mpicbg.imglib.image.ImagePlusAdapter;
import mpicbg.imglib.image.display.imagej.ImageJFunctions;
import mpicbg.imglib.io.ImageOpener;
import mpicbg.imglib.type.numeric.RealType;
import ij.IJ;
import ij.ImagePlus;

import ij.io.OpenDialog;

import mpicbg.imglib.algorithm.ROIAlgorithm;

import mpicbg.imglib.container.array.ArrayContainerFactory;



public class TestROIAlgorithm <T extends RealType<T>> extends ROIAlgorithm<T, T> {

		
	public TestROIAlgorithm(final Image<T> imageIn) {
		super(imageIn.getImageFactory(), imageIn, new int[] {1, 1});
	}

	@Override
	protected boolean patchOperation(StructuringElementCursor<T> cursor,
	        T type) {		
		while (cursor.hasNext())
		{			
			cursor.fwd();
			type.set(cursor.getType());
		}
		return true;
	}

	public static <R extends RealType<R>> void main(String args[])
	{
		OpenDialog od = new OpenDialog("Select an Image File", "");
		
		ImagePlus implus = IJ.openImage(od.getDirectory() + od.getFileName());
		Image<R> im = ImagePlusAdapter.wrap(implus);		
		Image<R> imout;		
		Image<R> imloci;
		
		try {
		    imloci = (new ImageOpener()).openImage(od.getDirectory() + od.getFileName(), new ArrayContainerFactory());				   
		}
		catch (Exception e)
		{
		    imloci = null;
		}
		
		TestROIAlgorithm<R> tra = new TestROIAlgorithm<R>(imloci);
		//int[] pos = new int[2];

		tra.process();
		imout = tra.getResult();

		/*
		LocalizableCursor<R> checkCursor = imout.createLocalizableCursor();		
		while (checkCursor.hasNext())
		{			
			checkCursor.fwd();
			checkCursor.getPosition(pos);
			IJ.log("" + pos[0] + "," + pos[1] + "," + checkCursor.getType().getRealFloat());
		}
		checkCursor.close();
		*/
		
		imloci.getDisplay().setMinMax();
		ImageJFunctions.displayAsVirtualStack(imloci).show();
		
		im.getDisplay().setMinMax();
		ImageJFunctions.displayAsVirtualStack(im).show();

		imout.getDisplay().setMinMax();		
		ImageJFunctions.displayAsVirtualStack(imout).show();
		
		
		
	}
	
	
	
}
