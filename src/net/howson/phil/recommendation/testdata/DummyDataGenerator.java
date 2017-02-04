package net.howson.phil.recommendation.testdata;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class DummyDataGenerator {

	private static final int NUM_PRODUCTS = 100_000;
	private static final int NUM_CUSTOMERS = 100_000;
	private static final int NUM_TRANSACTIONS = 30_000_000;
	private static final double customerCacuhyScale = 0.1;
	private static final double productCacuhyScale = 0.1;

	private static final int CORRELATED_PRODUCTS = 100;

	private static final int CORRELATED_PRODUCTS_MID = CORRELATED_PRODUCTS / 2;
	private static final int BUFFER_SIZE = 1024 * 1024;
	private static final double CORRELATION = 0.7;

	private final Random r = new Random();
	private final PrintStream out;

	public DummyDataGenerator(String[] args) throws FileNotFoundException {
		out = new PrintStream(new BufferedOutputStream(new FileOutputStream(args[0]), BUFFER_SIZE));
	}

	public void run() {

		for (int i = 0; i < NUM_TRANSACTIONS; ++i) {
			int customer;
			do {
				double x = nextCacuhy() * customerCacuhyScale;
				if (x < 0) {
					x = -x;
				}
				customer = (int) (x*NUM_CUSTOMERS);
			} while (customer > NUM_CUSTOMERS || customer < 0);

			int product;
			do {
				double x = nextCacuhy() * productCacuhyScale;
				if (x < 0) {
					x = -x;
				}
				product = (int) (x * NUM_PRODUCTS);
			} while (product > NUM_PRODUCTS || product < 0);

			out.print("customer " + customer);
			out.print(",");
			printProduct(product);
			out.println();
			
			if (product<CORRELATED_PRODUCTS && r.nextDouble() < CORRELATION) {
				out.print("customer " + customer);
				out.print(",");
				if (product<CORRELATED_PRODUCTS_MID) {
					printProduct(product + CORRELATED_PRODUCTS_MID);
				} else {
					printProduct(product - CORRELATED_PRODUCTS_MID);
				}
				out.println();
				
			}
			
			
		}
		out.close();

	}

	private void printProduct(int product) {
		if (product<CORRELATED_PRODUCTS) {
			out.print("correlated product " );
			if (product<CORRELATED_PRODUCTS_MID) {
				out.print(product + " A");
			} else {
				out.print((product-CORRELATED_PRODUCTS_MID) + " B");
			}
		} else {
			out.print("product" +product);
		}
	}

	private double nextCacuhy() {
		return r.nextGaussian() / r.nextGaussian();
	}

	public static void main(String[] args) throws Exception {
		new DummyDataGenerator(args).run();
	}

}
