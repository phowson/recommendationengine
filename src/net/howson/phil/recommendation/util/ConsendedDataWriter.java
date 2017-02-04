package net.howson.phil.recommendation.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import gnu.trove.procedure.TObjectIntProcedure;
import gnu.trove.procedure.TObjectProcedure;

public class ConsendedDataWriter implements Closeable, TObjectIntProcedure<String> {
	private final OutputStream outputStream;
	private final OutputStream metaOutputStream;
	private final ByteBuffer ob = ByteBuffer.allocateDirect(8);
	private final byte[] b = ob.array();
	private final ByteBuffer ob2 = ByteBuffer.allocateDirect(1024);
	private final byte[] b2 = ob2.array();

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

	public void outputMeta(Meta meta) throws IOException {
		ob.position(0);
		ob.putInt(meta.getCdict().size());
		ob.putInt(meta.getPdict().size());
		this.metaOutputStream.write(b);
		ob.position(0);
		ob.putInt(meta.getTcount());
		this.metaOutputStream.write(b, 0, 4);

		meta.getCdict().forEachEntry(this);

	}

	@Override
	public boolean execute(String a, int b) {
		ob2.position(0);
		ob2.putInt(a.length());
		ob2.put(a.getBytes());
		ob2.putInt(b);

		try {
			metaOutputStream.write(b2, 0, ob2.position());
		} catch (IOException e) {
			// Ugly throw, improve
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public void close() throws IOException {
		this.outputStream.close();
		this.metaOutputStream.close();
	}

}
