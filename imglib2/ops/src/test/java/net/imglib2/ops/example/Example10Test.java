/*

Copyright (c) 2011, Barry DeZonia.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
  * Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
  * Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
  * Neither the name of the Fiji project developers nor the
    names of its contributors may be used to endorse or promote products
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/

package net.imglib2.ops.example;

import static org.junit.Assert.*;

import org.junit.Test;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.ops.DiscreteNeigh;
import net.imglib2.ops.Function;
import net.imglib2.ops.function.complex.CartesianComplexFunction;
import net.imglib2.ops.function.complex.DFTFunction;
import net.imglib2.ops.function.complex.IDFTFunction;
import net.imglib2.ops.function.real.ConstantRealFunction;
import net.imglib2.ops.function.real.RealImageFunction;
import net.imglib2.type.numeric.complex.ComplexDoubleType;
import net.imglib2.type.numeric.real.DoubleType;


/**
 * 
 * @author Barry DeZonia
 *
 */
public class Example10Test {
	
	private final long XSIZE = 50;
	private final long YSIZE = 50;
	
	private Img<DoubleType> testImg;
	private Function<long[],DoubleType> image;
	private Function<long[],ComplexDoubleType> dft;
	
	private boolean veryClose(double d1, double d2) {
		return Math.abs(d1-d2) < 0.00001;
	}

	private Img<DoubleType> allocateImage() {
		final ArrayImgFactory<DoubleType> imgFactory = new ArrayImgFactory<DoubleType>();
		return imgFactory.create(new long[]{XSIZE,YSIZE}, new DoubleType());
	}

	private Img<DoubleType> makeInputImage() {
		Img<DoubleType> inputImg = allocateImage();
		RandomAccess<DoubleType> accessor = inputImg.randomAccess();
		long[] pos = new long[2];
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {
				pos[0] = x;
				pos[1] = y;
				accessor.setPosition(pos);
				accessor.get().setReal(x+y);
			}			
		}
		return inputImg;
	}

	private void testDFT() {
		image = new RealImageFunction<DoubleType,DoubleType>(testImg, new DoubleType());
		Function<long[],DoubleType> zero = new ConstantRealFunction<long[],DoubleType>(new DoubleType(),0);
		Function<long[],ComplexDoubleType> spatialFunction =
			new CartesianComplexFunction<long[],DoubleType,DoubleType,ComplexDoubleType>
			(image,zero,new ComplexDoubleType());
		dft = new DFTFunction<ComplexDoubleType>(
				spatialFunction, new long[]{XSIZE,YSIZE}, new long[2], new long[2], new ComplexDoubleType());
		// TODO - test something
		assertTrue(true);
	}
	
	private void testIDFT() {
		DiscreteNeigh neigh = new DiscreteNeigh(new long[2], new long[2], new long[2]);
		Function<long[],ComplexDoubleType> idft = new IDFTFunction<ComplexDoubleType>(
			dft, new long[]{XSIZE,YSIZE}, new long[2], new long[2], new ComplexDoubleType());
		long[] pos = new long[2];
		DoubleType original = new DoubleType();
		ComplexDoubleType computed = new ComplexDoubleType();
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {
				pos[0] = x;
				pos[1] = y;
				neigh.moveTo(pos);
				image.evaluate(neigh, pos, original);
				idft.evaluate(neigh, pos, computed);
				assertTrue(veryClose(computed.getRealDouble(), original.getRealDouble()));
				assertTrue(veryClose(computed.getImaginaryDouble(), 0));
				/*
				{
					System.out.println(" FAILURE at ("+x+","+y+"): expected ("
							+original.getRealDouble()+",0) actual ("+computed.getRealDouble()+","+computed.getImaginaryDouble()+")");
					success = false;
				}
				*/
			}
		}
	}

	@Test
	public void test() {
		testImg = makeInputImage();
		testDFT();
		testIDFT();
	}
}
