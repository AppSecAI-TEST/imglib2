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

package net.imglib2.ops;


/**
 * 
 * @author Barry DeZonia
 *
 */
public class RegionIndexIterator implements Iterator<long[]> {

	private boolean invalid;
	private final long[] ctr;
	private final long[] negOffs;
	private final long[] posOffs;
	private final long[] position;
	private final long[] maxes;
	private final long[] mins;
	
	public RegionIndexIterator(long[] ctr, long[] negOffs, long[] posOffs) {
		this.ctr = ctr.clone();
		this.negOffs = negOffs.clone();
		this.posOffs = posOffs.clone();
		this.position = new long[ctr.length];
		this.mins = new long[ctr.length];
		this.maxes = new long[ctr.length];
		setMinsAndMaxes();
		reset();
	}

	public RegionIndexIterator(Neighborhood<long[]> neigh) {
		this(neigh.getKeyPoint(), neigh.getNegativeOffsets(), neigh.getPositiveOffsets());
	}
	
	@Override
	public boolean hasNext() {
		if (invalid) return true;
		for (int i = 0; i < position.length; i++) {
			if (position[i] < maxes[i])
				return true;
		}
		return false;
	}
	
	@Override
	public boolean hasPrev() {
		if (invalid) return true;
		for (int i = 0; i < position.length; i++) {
			if (position[i] > mins[i])
				return true;
		}
		return false;
	}
	
	@Override
	public void fwd() {
		if (invalid) {
			first();
			invalid = false;
			return;
		}
		for (int i = 0; i < position.length; i++) {
			position[i]++;
			if (position[i] <= maxes[i]) return;
			position[i] = mins[i];
		}
		throw new IllegalArgumentException("can't move fwd() beyond end of region");
	}
	
	@Override
	public void bck() {
		if (invalid) {
			last();
			invalid = false;
			return;
		}
		for (int i = 0; i < position.length; i++) {
			position[i]--;
			if (position[i] >= mins[i]) return;
			position[i] = maxes[i];
		}
		throw new IllegalArgumentException("can't move bck() before start of region");
	}
	
	@Override
	public void reset() {
		invalid = true;
	}
	
	@Override
	public long[] getPosition() {
		return position;
	}
	
	@Override
	public void relocate(long[] keyPt) {
		for (int i = 0; i < ctr.length; i++)
			ctr[i] = keyPt[i];
		setMinsAndMaxes();
		reset();
	}

	// -- private helpers --
	
	private void first() {
		for (int i = 0; i < position.length; i++)
			position[i] = mins[i];
	}
	
	private void last() {
		for (int i = 0; i < position.length; i++)
			position[i] = maxes[i];
	}
	
	private void setMinsAndMaxes() {
		for (int i = 0; i < ctr.length; i++) {
			mins[i] = ctr[i] - negOffs[i];
			maxes[i] = ctr[i] + posOffs[i];
		}
	}
}
