import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostalRate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		float length;
		float width;
		float height;
		float weight;
		float girth;
		String from;
		String to;

		if (args == null || args.length != 7) {
			System.out
					.print("Usage: PostalRate from[postal code] to[postal code] length[cm] width[cm] height[cm] weight[kg] postType[Regular, Xpress, Priority]"
							+ "\n" + "Should have 7 arguments");
			return;
		}
		
	
		
		
		else {

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
			
			if (!isValidPostalCode(from) || !isValidPostalCode(to)) {
				System.out.print("Postal code(s) is invalid");
				return;
			}
			
			
			if (!(args[6].equals("Regular") || args[6].equals("Xpress") || args[6].equals("Priority"))) {
				System.out.print("postType should be [Regular, Xpress, Priority]");
				return;
			}
			
			if (weight <= 0 || weight > 30) {
				System.out.print("weight has to be between 0 and 30 kg");
				return;
			}
			
			if (length < 10 || length > 200) {
				System.out.print("length should be between 10cm and 200cm");
				return;
			}
			
			if (width < 1.7 || width > 278) {
				System.out.print("width should be between 1.7cm and 278cm");
				return;
			}
			
			if (height < 1 || height > 275.6) {
				System.out.print("height should be between 1cm and 275.6cm");
				return;
			}
			
			girth = (float) (height*2.0 + width*2.0);
			
			if (length + girth > 300) {
				System.out.print("Girth is out of bounds");
				return;
			}
			
		}

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

}
