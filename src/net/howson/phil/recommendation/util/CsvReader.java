package net.howson.phil.recommendation.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Stripped down CSV Reader for reading large CSV files
 */
public class CsvReader implements Closeable {

	
	private final InputStream inputStream;
	private final String [] cols;
	private final StringBuilder[] sb;
	
	public CsvReader(InputStream inputStreamReader, int cols) {
		this.inputStream = inputStreamReader;
		this.cols = new String[cols];
		this.sb = new StringBuilder[cols];
		for (int i = 0; i<cols; ++i) {
			this.sb[i] = new StringBuilder();
		}
	}

	public String[] getCols() {
		return cols;
	}
	
	
	
	public boolean readRow() throws IOException {
		int c = 0;
		while (true) {
			int i = inputStream.read();
			if (i==-1) {
				return false;
			} else if (i==',') {
				++c;
			} else if (i=='\n') {
				break;
			} else if (i=='\r'){
				
			} else {
				sb[c].append((char) i);
			}
			
			
		}
		for (int i = 0; i<cols.length; ++i) {
			cols[i] = sb[i].toString();
			sb[i].delete(0, Integer.MAX_VALUE);
		}	
		
		return true;
	}
	
	@Override
	public void close() throws IOException {
		this.inputStream.close();
	}
}
