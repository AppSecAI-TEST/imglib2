/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2015 Tobias Pietzsch, Stephan Preibisch, Barry DeZonia,
 * Stephan Saalfeld, Curtis Rueden, Albert Cardona, Christian Dietz, Jean-Yves
 * Tinevez, Johannes Schindelin, Jonathan Hale, Lee Kamentsky, Larry Lindsey, Mark
 * Hiner, Michael Zinsmaier, Martin Horn, Grant Harris, Aivar Grislis, John
 * Bogovic, Steffen Jaensch, Stefan Helfrich, Jan Funke, Nick Perry, Mark Longair,
 * Melissa Linkert and Dimiter Prodanov.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imglib2;

/**
 * A point is a location in EuclideanSpace.
 * 
 * @author ImgLib2 developers
 * @author Lee Kamentsky
 * @author Stephan Saalfeld
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class RealPoint extends AbstractRealLocalizable implements RealPositionable
{
	/**
	 * Protected constructor that can re-use the passed position array.
	 * 
	 * @param position
	 *            array used to store the position.
	 * @param copy
	 *            flag indicating whether position array should be duplicated.
	 */
	protected RealPoint( final double[] position, final boolean copy )
	{
		super( copy ? position.clone() : position );
	}

	/**
	 * Create a point in <i>nDimensional</i> space initialized to 0,0,...
	 * 
	 * @param n
	 *            number of dimensions of the space
	 */
	public RealPoint( final int n )
	{
		super( n );
	}

	/**
	 * Create a point at a definite location in a space of the dimensionality of
	 * the position.
	 * 
	 * @param position
	 *            the initial position. The length of the array determines the
	 *            dimensionality of the space.
	 */
	public RealPoint( final double... position )
	{
		this( position, true );
	}

	/**
	 * Create a point at a definite location in a space of the dimensionality of
	 * the position.
	 * 
	 * @param position
	 *            the initial position. The length of the array determines the
	 *            dimensionality of the space.
	 */
	public RealPoint( final float... position )
	{
		super( position.length );
		setPosition( position );
	}

	/**
	 * Create a point using the position and dimensionality of a
	 * {@link RealLocalizable}
	 * 
	 * @param localizable
	 *            the initial position. Its dimensionality determines the
	 *            dimensionality of the space.
	 */
	public RealPoint( final RealLocalizable localizable )
	{
		super( localizable.numDimensions() );
		localizable.localize( position );
	}

	@Override
	public void fwd( final int d )
	{
		++position[ d ];
	}

	@Override
	public void bck( final int d )
	{
		--position[ d ];
	}

	@Override
	public void move( final int distance, final int d )
	{
		position[ d ] += distance;
	}

	@Override
	public void move( final long distance, final int d )
	{
		position[ d ] += distance;
	}

	@Override
	public void move( final Localizable localizable )
	{
		for ( int d = 0; d < n; d++ )
			position[ d ] += localizable.getDoublePosition( d );
	}

	@Override
	public void move( final int[] distance )
	{
		for ( int d = 0; d < n; d++ )
			position[ d ] += distance[ d ];
	}

	@Override
	public void move( final long[] distance )
	{
		for ( int d = 0; d < n; d++ )
			position[ d ] += distance[ d ];
	}

	@Override
	public void setPosition( final Localizable localizable )
	{
		localizable.localize( position );
	}

	@Override
	public void setPosition( final int[] position )
	{
		for ( int d = 0; d < n; d++ )
			this.position[ d ] = position[ d ];
	}

	@Override
	public void setPosition( final long[] position )
	{
		for ( int d = 0; d < n; d++ )
			this.position[ d ] = position[ d ];
	}

	@Override
	public void setPosition( final int position, final int d )
	{
		this.position[ d ] = position;
	}

	@Override
	public void setPosition( final long position, final int d )
	{
		this.position[ d ] = position;
	}

	@Override
	public void move( final float distance, final int d )
	{
		position[ d ] += distance;
	}

	@Override
	public void move( final double distance, final int d )
	{
		position[ d ] += distance;
	}

	@Override
	public void move( final RealLocalizable localizable )
	{
		for ( int d = 0; d < n; ++d )
			position[ d ] += localizable.getDoublePosition( d );
	}

	@Override
	public void move( final float[] distance )
	{
		for ( int d = 0; d < n; ++d )
			position[ d ] += distance[ d ];
	}

	@Override
	public void move( final double[] distance )
	{
		for ( int d = 0; d < n; ++d )
			position[ d ] += distance[ d ];
	}

	@Override
	public void setPosition( final RealLocalizable localizable )
	{
		localizable.localize( position );
	}

	@Override
	public void setPosition( final float[] position )
	{
		for ( int d = 0; d < n; ++d )
			this.position[ d ] = position[ d ];
	}

	@Override
	public void setPosition( final double[] position )
	{
		for ( int d = 0; d < n; ++d )
			this.position[ d ] = position[ d ];
	}

	@Override
	public void setPosition( final float position, final int d )
	{
		this.position[ d ] = position;
	}

	@Override
	public void setPosition( final double position, final int d )
	{
		this.position[ d ] = position;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		char c = '(';
		for ( int i = 0; i < numDimensions(); i++ )
		{
			sb.append( c );
			sb.append( position[ i ] );
			c = ',';
		}
		sb.append( ")" );
		return sb.toString();
	}

	/**
	 * Create a point that stores its coordinates in the provided position
	 * array.
	 * 
	 * @param position
	 *            array to use for storing the position.
	 */
	public static RealPoint wrap( final double[] position )
	{
		return new RealPoint( position, false );
	}
}
