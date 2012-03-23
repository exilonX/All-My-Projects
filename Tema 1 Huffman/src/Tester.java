import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author steli
 */
public class Tester {
	static final String SOLVER_CLASS = "Solver";
	static final String MAIN_CLASS = "Main";

	abstract static class HuffmanTest {
		protected String in;
		protected String out;
		protected String ref;
		private PrintStream oldOut;
		private InputStream oldIn;
		private PrintStream crtOut;
		private InputStream crtIn;

		protected boolean passed;

		HuffmanTest(String in, String out, String ref) {
			this.in = in;
			this.out = out;
			this.ref = ref;
		}

		public boolean isPassed() {
			return passed;
		}

		protected void redirectInput(String file) {
			try {
				oldIn = System.in;
				crtIn = new BufferedInputStream(new FileInputStream(file));
				System.setIn(crtIn);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		protected void restoreInput() {
			System.setIn(oldIn);
			try {
				crtIn.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		protected void redirectOutput(String file) {
			try {
				oldOut = System.out;
				crtOut = new PrintStream(new FileOutputStream(file));
				System.setOut(crtOut);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		protected void restoreOutput() {
			System.setOut(oldOut);
			crtOut.close();
		}

		protected static boolean compareFiles(String f1, String f2) {
			boolean equal = true;
			FileInputStream fis1 = null;
			FileInputStream fis2 = null;
			try {
				fis1 = new FileInputStream(f1);
				fis2 = new FileInputStream(f2);
				int b1;
				int b2;
				while (true) {
					b1 = fis1.read();
					b2 = fis2.read();
					if (b1 == -1 || b2 == -1) {
						equal &= b1 * b2 == 1;
						break;
					} else if (b1 != b2) {
						equal = false;
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis1 != null) {
						fis1.close();
					}
					if (fis2 != null) {
						fis2.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return equal;
		}

		protected static void invokeEncoder(String className) {
			invokeMainWithArg(className, "c");
		}

		protected static void invokeDecoder(String className) {
			invokeMainWithArg(className, "d");
		}

		private static void invokeMainWithArg(String className, String arg) {
			try {
				Class<?> c = Class.forName(className);
				Method m = c.getMethod("main", String[].class);
				m.invoke(null, (Object) new String[] { arg });
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		protected abstract void execute();
	}

	static class EncoderTest extends HuffmanTest {
		EncoderTest(String in, String out, String ref) {
			super(in, out, ref);
		}

		@Override
		public void execute() {
			redirectInput(in);
			redirectOutput(out);
			try {
				invokeEncoder(MAIN_CLASS);
			} finally {
				restoreInput();
				restoreOutput();
			}
			redirectInput(out);
			redirectOutput(ref);
			try {
				invokeDecoder(SOLVER_CLASS);
			} finally {
				restoreInput();
				restoreOutput();
			}

			passed = compareFiles(in, ref);
		}

		@Override
		public String toString() {
			return "Testing encoder for " + in + "...\t";
		}
	}

	static class DecoderTest extends HuffmanTest {
		DecoderTest(String in, String out, String ref) {
			super(in, out, ref);
		}

		@Override
		public void execute() {
			redirectInput(in);
			redirectOutput(out);
			try {
				invokeDecoder(MAIN_CLASS);
			} finally {
				restoreInput();
				restoreOutput();
			}
			redirectInput(in);
			redirectOutput(ref);
			try {
				invokeDecoder(SOLVER_CLASS);
			} finally {
				restoreInput();
				restoreOutput();
			}

			passed = compareFiles(out, ref);
		}

		@Override
		public String toString() {
			return "Testing decoder for " + in + "...\t";
		}
	}

	static class TestSet {
		List<HuffmanTest> tests;

		TestSet() {
			tests = new ArrayList<HuffmanTest>();
		}

		public void addTest(HuffmanTest test) {
			tests.add(test);
		}

		public void runTests() {
			System.out.println("*********************************");
			System.out.println("******* STARTING TESTS **********");
			System.out.println("*********************************");
			for (HuffmanTest test : tests) {
				System.out.print(test);
				try {
					test.execute();
					System.out.println(test.isPassed() ? "PASS" : "FAIL");
					if (!test.isPassed()) {
						System.out.println("Output and reference do not match.");
					}
				} catch (RuntimeException e) {
					System.out.println("FAIL");
					PrintStream err = System.err;
					System.setErr(System.out);
					System.out.println("An execution exception occured:");
					Throwable toPrint = e;
					if (e.getCause() != null) {
						toPrint = e.getCause();
					}
					if (e.getCause() instanceof InvocationTargetException) {
						toPrint = e.getCause().getCause();
					}
					toPrint.printStackTrace();
					System.setErr(err);
				}
			}
		}
	}

	public static void main(String[] args) {
		String inFolder = System.getProperty("os.name").equals("Linux") ? "lin/" : "win/";
		TestSet set = new TestSet();
		set.addTest(new EncoderTest("in/" + inFolder + "/f1.in", "out/f1.out", "ref/f1.ref"));
		set.addTest(new EncoderTest("in/" + inFolder + "/f2.in", "out/f2.out", "ref/f2.ref"));
		set.addTest(new EncoderTest("in/" + inFolder + "/f3.in", "out/f3.out", "ref/f3.ref"));

		set.addTest(new DecoderTest("in/" + inFolder + "/f4.in", "out/f4.out", "ref/f4.ref"));
		set.addTest(new DecoderTest("in/" + inFolder + "/f5.in", "out/f5.out", "ref/f5.ref"));
		set.addTest(new DecoderTest("in/" + inFolder + "/f6.in", "out/f6.out", "ref/f6.ref"));
		set.runTests();
	}
}
