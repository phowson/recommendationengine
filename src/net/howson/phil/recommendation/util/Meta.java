package net.howson.phil.recommendation.util;

import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class Meta {
	private final TObjectIntHashMap<String> cdict;
	private final TObjectIntHashMap<String> pdict;
	private final TIntLongHashMap pcounts;
	private final int tcount;
	
	
	public Meta(TObjectIntHashMap<String> cdict, TObjectIntHashMap<String> pdict, TIntLongHashMap pcounts, int tcount) {
		super();
		this.cdict = cdict;
		this.pdict = pdict;
		this.pcounts = pcounts;
		this.tcount = tcount;
	}
	
	public TObjectIntHashMap<String> getCdict() {
		return cdict;
	}
	public TObjectIntHashMap<String> getPdict() {
		return pdict;
	}
	public TIntLongHashMap getPcounts() {
		return pcounts;
	}

	public int getTcount() {
		return tcount;
	}
	
	
	
}
