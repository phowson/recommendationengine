package net.howson.phil.recommendation.normalise;

import java.io.IOException;

import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.howson.phil.recommendation.util.ConsendedDataWriter;
import net.howson.phil.recommendation.util.CsvReader;
import net.howson.phil.recommendation.util.Meta;

public class SimpleCsvNormalisation {

	
	
	public SimpleCsvNormalisation() {
		
		
	}
	
	
	public void run(CsvReader input, ConsendedDataWriter output) throws IOException {
	
		final TIntLongHashMap pcounts = new TIntLongHashMap();
		final TObjectIntHashMap<String> cdict = new TObjectIntHashMap<>();
		final TObjectIntHashMap<String> pdict = new TObjectIntHashMap<>();
		
		int ccount = 0;
		int pcount = 0;
		int tcount = 0;
		
		while (input.readRow()) {
			
			String cId = input.getCols()[0];
			String pId = input.getCols()[1];
			
			int iCid = cdict.get(cId);
			if (iCid ==0 ) {
				iCid = ++ccount;
				cdict.put(cId, iCid);
			}

			int iPid = cdict.get(pId);
			if (iPid ==0 ) {
				iPid = ++pcount;
				pdict.put(pId, iPid);
			}
			
			output.output(iCid, iPid);
			
			++tcount;			
		}
		
		
		output.outputMeta(new Meta(cdict, pdict, pcounts, tcount));
		
	}
	
	
}
