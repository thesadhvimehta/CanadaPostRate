import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.internal.MethodSorter;

public class TestPostalRate {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	// private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private PostalRate pr;

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
		// System.setOut(new PrintStream(errContent));

		pr = new PostalRate();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void no_args_T001() {
		String expected = "Usage: PostalRate from[postal code] to[postal code] length[cm] width[cm] height[cm] weight[kg] postType[Regular, Xpress, Priority]"
				+ "\n" + "Should have 7 arguments";
		;
		pr.main(null);
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void less_7args_T002() {
		String expected = "Usage: PostalRate from[postal code] to[postal code] length[cm] width[cm] height[cm] weight[kg] postType[Regular, Xpress, Priority]"
				+ "\n" + "Should have 7 arguments";
		String args[] = new String[] { "one", "two", "three" };
		pr.main(args);
		assertEquals(expected, outContent.toString());

	}

	@Test
	public void more_7args_T003() {
		String expected = "Usage: PostalRate from[postal code] to[postal code] length[cm] width[cm] height[cm] weight[kg] postType[Regular, Xpress, Priority]"
				+ "\n" + "Should have 7 arguments";
		;
		String args[] = new String[] { "one", "two", "three", "four", "five", "six", "seven", "eight" };
		pr.main(args);
		assertEquals(expected, outContent.toString());

	}

	@Test
	public void dimensions_notNumber_T004() {
		String expected = "dimensions should be numerical";
		String args[] = new String[] { "one", "two", "three", "four", "five", "six", "seven" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void postalCode_Invalid_T005() {
		String expected = "Postal code(s) is invalid";
		String args[] = new String[] { "000000", "000000", "3", "4", "5", "6", "seven" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void invalid_PostType_T006() {
		String expected = "postType should be [Regular, Xpress, Priority]";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "3", "4", "5", "6", "seven" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void weight_less0_T007() {
		String expected = "weight has to be between 0 and 30 kg";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "3", "4", "5", "-1", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());

	}

//	@Test
//	@deprecated
//	public void weight_is0_T008() {
//		String expected = "weight has to be between 0 and 30 kg";
//		String args[] = new String[] { "A8A8A8", "A8A8A8", "3", "4", "5", "0", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}

	@Test
	public void weight_greater30_T009() {
		String expected = "weight has to be between 0 and 30 kg";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "3", "4", "5", "38", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void length_out_of_range_low_T010() {
		String expected = "length should be between 10cm and 200cm";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "9", "4", "5", "25", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void length_out_of_range_high_T011() {
		String expected = "length should be between 10cm and 200cm";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "250", "4", "5", "25", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void width_out_of_range_high_T012() {
		String expected = "width should be between 1.7cm and 278cm";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "180", "300", "5", "25", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void width_out_of_range_low_T013() {
		String expected = "width should be between 1.7cm and 278cm";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "180", "1", "5", "25", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void height_out_of_range_low_T014() {
		String expected = "height should be between 1cm and 275.6cm";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "180", "10", "0", "25", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void height_out_of_range_high_T015() {
		String expected = "height should be between 1cm and 275.6cm";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "180", "10", "300", "25", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void girth_out_of_range_high_T016() {
		String expected = "Girth is out of bounds";
		String args[] = new String[] { "A8A8A8", "A8A8A8", "200", "250", "275", "25", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}

}
