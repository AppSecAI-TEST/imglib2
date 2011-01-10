/**
 * Copyright (c) 2009--2010, Stephan Preibisch
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.  Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution.  Neither the name of the Fiji project nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Stephan Preibisch
 */
package mpicbg.imglib.container.dynamic;

import java.util.ArrayList;

import mpicbg.imglib.cursor.Cursor;
import mpicbg.imglib.cursor.dynamic.DynamicCursor;
import mpicbg.imglib.type.Type;

public class ByteDynamicContainer <T extends Type<T>> extends DynamicContainer<T, ByteDynamicContainerAccessor>
{
	final ArrayList<Byte> data;
	
	public ByteDynamicContainer( final DynamicContainerFactory factory, final int[] dim, final int entitiesPerPixel )
	{
		super( factory, dim, entitiesPerPixel );
		
		data = new ArrayList<Byte>();
		
		for ( int i = 0; i < numPixels*entitiesPerPixel; ++i )
			data.add( (byte)0 );
	}
	
	@Override
	public ByteDynamicContainerAccessor update( final Cursor<?> c )
	{
		final DynamicCursor<?> cursor = (DynamicCursor<?>)c;
		final ByteDynamicContainerAccessor accessor = (ByteDynamicContainerAccessor) cursor.getAccessor();
		accessor.updateIndex( cursor.getInternalIndex() );
		
		return accessor;
	}

	@Override
	public ByteDynamicContainerAccessor createAccessor()
	{
		return new ByteDynamicContainerAccessor( this, entitiesPerPixel );
	}

	@Override 
	public void close() { data.clear(); }
	
}
