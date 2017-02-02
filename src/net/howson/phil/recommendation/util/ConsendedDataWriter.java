package net.howson.phil.recommendation.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ConsendedDataWriter implements Closeable{
	private final OutputStream outputStream;
	private final OutputStream metaOutputStream;
	private final ByteBuffer ob = ByteBuffer.allocateDirect(8);
	private final byte [] b = ob.array();
	
	
	public ConsendedDataWriter(OutputStream outputStream, OutputStream countsOutputStream) {
		super();
		this.outputStream = outputStream;
		this.metaOutputStream = countsOutputStream;
	}
	
	public void output(int customerId, int productId) throws IOException {
		ob.position(0);
		ob.putInt(customerId);
		ob.putInt(productId);		
		this.outputStream.write(b);
	}
	
	public void outputMeta(Meta meta) {
		
	}
	
	
	
	
	
	@Override
	public void close() throws IOException {
		this.outputStream.close();
		this.metaOutputStream.close();
	}
	
	
	
	
}
