package net.howson.phil.recommendation.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class CondensedDataReader implements Closeable {

	private final InputStream inputStream;

	private int productId;
	private int customerId;
	private final ByteBuffer b = ByteBuffer.allocateDirect(8);
	private final byte[] ba = b.array();

	public CondensedDataReader(InputStream inputStreamReader) {
		this.inputStream = inputStreamReader;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getProductId() {
		return productId;
	}

	public boolean next() throws IOException {
		int i = inputStream.read(ba);

		if (i != 8) {
			return false;
		}
		b.position(0);
		productId = b.getInt();
		customerId = b.getInt();
		return true;
	}

	@Override
	public void close() throws IOException {
		this.inputStream.close();
	}

}
