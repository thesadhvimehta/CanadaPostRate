import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostalRate {

	public static void main(String[] args) {

		float length;
		float width;
		float height;
		float weight;
		String from;
		String to;

		// not enough arguments
		if (args == null || args.length != 7) {
			System.out
					.print("Usage: PostalRate from[postal code] to[postal code] length[cm] width[cm] height[cm] weight[kg] postType[Regular, Xpress, Priority]"
							+ "\n" + "Should have 7 arguments");
			return;
		}

		// enough arguments
		else {

			// see if the values are valid
			try {
				length = Float.valueOf(args[2]);
				width = Float.valueOf(args[3]);
				height = Float.valueOf(args[4]);
				weight = Float.valueOf(args[5]);

			} catch (NumberFormatException e) {
				System.out.print("dimensions should be numerical");
				return;
			}

			from = args[0];
			to = args[1];

			// check for valid postal code
			if (!isValidPostalCode(from) || !isValidPostalCode(to)) {
				System.out.print("Postal code(s) is invalid");
				return;
			}

			// check for valid postType
			String postType = args[6].toUpperCase();
			if (!(postType.equals("REGULAR") || postType.equals("XPRESS") || postType.equals("PRIORITY"))) {
				System.out.print("postType should be [Regular, Xpress, Priority]");
				return;
			}
			// check for valid dimensions
			if (!isDimensionsValid(weight, length, height, width)) {
				return;
			}

			// all the dimensions were ok
			float total = calcPostalRate(from, to, weight);

			if (postType.equals("XPRESS")) {
				total += 5;
			}
			else if (postType.equals("PRIORITY")) {
				total += 10;
			}

			BigDecimal newtotal = round(total, 2);
			System.out.print("Price is: " + newtotal.toString() + "$");

		}

	}

	private static boolean isDimensionsValid(float weight, float length, float height, float width) {
		if (weight <= 0 || weight > 30) {
			System.out.print("weight has to be between 0 and 30 kg");
			return false;
		}

		if (length < 10 || length > 200) {
			System.out.print("length should be between 10cm and 200cm");
			return false;
		}

		if (width < 1.7 || width > 278) {
			System.out.print("width should be between 1.7cm and 278cm");
			return false;
		}

		if (height < 1 || height > 275.6) {
			System.out.print("height should be between 1cm and 275.6cm");
			return false;
		}

		float girth = (float) (height * 2.0 + width * 2.0);

		if (length + girth > 300) {
			System.out.print("Girth is out of bounds");
			return false;
		}

		return true;
	}

	private static boolean isValidPostalCode(String pc) {
		String regex = "^(?!.*[DFIOQUdfioqu])[A-Va-vXYxy][0-9][A-Za-z]?[0-9][A-Za-z][0-9]$";
		Pattern pattern = Pattern.compile(regex);

		if (pc.length() != 6) {
			return false;
		}

		Matcher matcher = pattern.matcher(pc);

		if (!matcher.matches()) {
			return false;
		}

		return true;
	}

	private static float calcPostalRate(String from, String to, float weight) {
		BufferedReader br = null;
		float price = 0;
		char fromSearch = Character.toUpperCase(from.charAt(0));
		char toSearch = Character.toUpperCase(to.charAt(0));

		// from qc
		if (fromSearch == 'G' || fromSearch == 'J') {
			fromSearch = 'H';
		}
		// from ont
		else if (fromSearch == 'K' || fromSearch == 'L' || fromSearch == 'N' || fromSearch == 'P') {
			fromSearch = 'M';
		}
		// to qc
		if (toSearch == 'G' || toSearch == 'J') {
			toSearch = 'H';
		}
		// to ont
		else if (toSearch == 'K' || toSearch == 'L' || toSearch == 'N' || toSearch == 'P') {
			toSearch = 'M';
		}

		try {
			br = new BufferedReader(new FileReader("data.csv"));
			String line = "";
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] entry = line.split(", ");
				if (entry[0].charAt(0) == fromSearch) {
					if (entry[1].charAt(0) == toSearch) {
						return (float) (weight * Float.valueOf(entry[2]));
					}
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}

	public static BigDecimal round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

}
