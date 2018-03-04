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
		String args[] = new String[] { "Z9W623", "H9W6C3", "3", "4", "5", "6", "seven" };

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
	
	
	
	
	@Test
	public void post_QC_AB_3kg_regular_T018() {
		String expected = "Price is: 5.32$";
		String args[] = new String[] { "h9w6c3", "t6g2r3", "10", "1.7", "10", "3", "Regular" };

		pr.main(args);
		assertEquals(expected, outContent.toString());
	}
	
	
	
	
//	
//	@Test
//	public void post_QC_AB_3kg_xpress_T019() {
//		String expected = "Price is: 10.32$";
//		String args[] = new String[] { "H9W6C3", "T6G2R3", "15", "3", "100", "3", "Xpress" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_AB_3kg_priority_T020() {
//		String expected = "Price is: 15.32$";
//		String args[] = new String[] { "H9W6C3", "T6G2R3", "20", "100", "3", "3", "Priority" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_BC_5dot4kg_T021() {
//		String expected = "Price is: 9.3$";
//		String args[] = new String[] { "H9W6C3", "V6T1Z4", "10", "1.7", "10", "5.4", "Regular"};
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void  post_QC_MB_29kg_T022() {
//		String expected = "Price is: 53.27$";
//		String args[] = new String[] { "H9W6C3", "R3T2N2", "20", "10", "100", "29", "Regular"};
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//
//	
//	@Test
//	public void post_QC_NB_21kg_T023() {
//		String expected = "Price is: 25.30$";
//		String args[] = new String[] { "H9W6C3", "E2L4L5", "30", "20", "90", "21", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_NL_0dot499kg_T024() {
//		String expected = "Price is: 1.10$";
//		String args[] = new String[] { "H9W6C3", "A1C5S7", "22", "22", "90", "0.499", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_NS_19dot9kg_T025() {
//		String expected = "Price is: 42.92$";
//		String args[] = new String[] { "H9W6C3", "B3H4R2", "33", "30", "30", "19.9", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_BC_10kg_T026() {
//		String expected = "Price is: 17.23$";
//		String args[] = new String[] { "H9W6C3", "V6T1Z4", "19", "25", "25", "10", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_NT_3dot6kg_T027() {
//		String expected = "Price is: 12.06$";
//		String args[] = new String[] { "H9W6C3", "X0A0H0", "15", "30", "30", "3.6", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_ON_5dot9kg_T028() {
//		String expected = "Price is: 10.84$";
//		String args[] = new String[] { "H9W6C3", "M6J1H7", "17", "40", "40", "5.9", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_SK_23dot5kg_T029() {
//		String expected = "Price is: 44.22$";
//		String args[] = new String[] { "H9W6C3", "H9W6C3", "30", "50", "60", "22.6", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_SK_23dot5kg_T030() {
//		String expected = "Price is: 44.02$";
//		String args[] = new String[] { "H9W6C3", "S7N5E8", "60", "10", "10", "23.5", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_YT_17dot3kg_T031() {
//		String expected = "Price is: 37.44$";
//		String args[] = new String[] { "H9W6C3", "Y1A5K4", "40", "20", "20", "17.3", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
//	@Test
//	public void post_QC_PE_6dot9kg_T032() {
//		String expected = "Price is: 14.88$";
//		String args[] = new String[] { "H9W6C3", "C1A4P3", "35", "50", "50", "6.9", "Regular" };
//
//		pr.main(args);
//		assertEquals(expected, outContent.toString());
//	}
//	
	
}
