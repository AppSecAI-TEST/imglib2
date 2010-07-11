package mpicbg.imglib.algorithm.roi;

import mpicbg.imglib.algorithm.Benchmark;
import mpicbg.imglib.algorithm.OutputAlgorithm;
import mpicbg.imglib.image.Image;
import mpicbg.imglib.outofbounds.OutOfBoundsStrategyFactory;
import mpicbg.imglib.type.ComparableType;
import mpicbg.imglib.type.numeric.ComplexType;

/**
 * Open morphological operation. Operates by creating a {@link MorphErode} and a
 * {@link MorphDilate}, taking the output from the first, and passing it to the second.
 * 
 * @author Larry Lindsey
 *
 * @param <T> {@link Image} type.
 */
public class MorphOpen<T extends ComparableType<T>> implements OutputAlgorithm<T>, Benchmark
{
	
	private final Image<T> image;
	private Image<T> outputImage;
	private MorphDilate<T> dilater;
	private final MorphErode<T> eroder;
	private final StructuringElement strel;
	private final OutOfBoundsStrategyFactory<T> outsideFactory;
	private long pTime;
	
	public MorphOpen(Image<T> imageIn, StructuringElement strelIn)
	{
		this(imageIn, strelIn, null);
	}
	
	public MorphOpen(Image<T> imageIn, StructuringElement strelIn,
			final OutOfBoundsStrategyFactory<T> inOutsideFactory)
	{
		image = imageIn;		
		strel = strelIn;
		eroder = new MorphErode<T>(image, strel, inOutsideFactory);
		dilater = null;
		outputImage = null;
		outsideFactory = inOutsideFactory;
		pTime = 0;		
	}
	
	@Override
	public Image<T> getResult()
	{
		return outputImage;
	}

	@Override
	public boolean checkInput()
	{		
		boolean ok = eroder.checkInput();
		if (dilater != null)
		{
			ok &= dilater.checkInput();
		}
		return ok;
	}

	@Override
	public String getErrorMessage() {
		String errorMsg = "";
		errorMsg += eroder.getErrorMessage();
		if (dilater != null)
		{
			errorMsg += dilater.getErrorMessage();
		}
		
		return errorMsg;
	}
	
	public void close()
	{
		eroder.close();
		if (dilater != null)
		{
			dilater.close();
		}
	}

	@Override
	public boolean process() {
		final long sTime = System.currentTimeMillis();
		pTime = 0;
		boolean rVal = false;
		
		if (eroder.process())
		{
			dilater = new MorphDilate<T>(eroder.getResult(), strel, outsideFactory);
			dilater.setName(image.getName() + " Opened");
			rVal = dilater.process();			
		}
		
		if (rVal)
		{
			outputImage = dilater.getResult();
		}
		else
		{
			outputImage = null;
		}
		
		pTime = System.currentTimeMillis() - sTime;
		
		return rVal;
	}

	@Override
	public long getProcessingTime() {
		return pTime;
	}

}
