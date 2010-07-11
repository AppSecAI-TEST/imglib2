package mpicbg.imglib.algorithm.roi;

import mpicbg.imglib.algorithm.Benchmark;
import mpicbg.imglib.algorithm.OutputAlgorithm;
import mpicbg.imglib.image.Image;
import mpicbg.imglib.outofbounds.OutOfBoundsStrategyFactory;
import mpicbg.imglib.type.ComparableType;
import mpicbg.imglib.type.numeric.ComplexType;

/**
 * Close morphological operation. Operates by creating a {@link MorphDilate} and a
 * {@link MorphErode}, taking the output from the first, and passing it to the second.
 * 
 * @author Larry Lindsey
 *
 * @param <T> {@link Image} type.
 */
public class MorphClose<T extends ComparableType<T>> implements OutputAlgorithm<T>, Benchmark
{
	
	private final Image<T> image;
	private Image<T> outputImage;
	private final MorphDilate<T> dilater;
	private MorphErode<T> eroder;
	private final StructuringElement strel;
	private final OutOfBoundsStrategyFactory<T> outsideFactory;
	private long pTime;
	
	public MorphClose(Image<T> imageIn, StructuringElement strelIn)
	{
		this(imageIn, strelIn, null);
	}
	
	public MorphClose(Image<T> imageIn, StructuringElement strelIn,
			final OutOfBoundsStrategyFactory<T> inOutsideFactory)
	{
		image = imageIn;		
		strel = strelIn;
		dilater = new MorphDilate<T>(image, strel, inOutsideFactory);
		eroder = null;
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
		boolean ok = dilater.checkInput();
		if (eroder != null)
		{
			ok &= eroder.checkInput();
		}
		return ok;
	}

	@Override
	public String getErrorMessage() {
		String errorMsg = "";
		errorMsg += dilater.getErrorMessage();
		if (eroder != null)
		{
			errorMsg += eroder.getErrorMessage();
		}
		
		return errorMsg;
	}

	@Override
	public boolean process() {
		final long sTime = System.currentTimeMillis();
		pTime = 0;
		boolean rVal = false;
		
		if (dilater.process())
		{
			eroder = new MorphErode<T>(dilater.getResult(), strel, outsideFactory);
			eroder.setName(image.getName() + " Closed");
			rVal = eroder.process();			
		}
		
		if (rVal)
		{
			outputImage = eroder.getResult();
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

	public void close()
	{
		dilater.close();
		if (eroder != null)
		{
			eroder.close();
		}	
	}
}
