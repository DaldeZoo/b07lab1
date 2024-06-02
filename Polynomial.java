import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	double[] coeff;
	int[] exp;
	
	public Polynomial() {
		coeff = null; // Setting to null since we can't have zeroes in our coefficient array
		exp = null;
	}
	
	public Polynomial(double[] coeff, int[] exp) {
		if (coeff.length != exp.length) {
			System.out.println("Error, the coefficient array and its exponent array are of unequal lengths!");
			return;
		}
		
		int len = 0;
		for (int i=0;i<coeff.length;i++) {
			if (coeff[i] != 0) len++;
		}
		
		this.coeff = new double[len];
		this.exp = new int[len];
		int idx = 0;
		
		for (int i=0;i<coeff.length;i++) {
			if (coeff[i] != 0) {
				this.coeff[idx] = coeff[i];
				this.exp[idx] = exp[i];
				idx++;
			}
		}
	}
	
	public Polynomial(File f) {
        try (Scanner scanner = new Scanner(f)) {
            if (scanner.hasNextLine()) {
                String poly = scanner.nextLine();
                
                String[] terms = poly.split("(?=[+-])"); // Individual term in each index
                
                // Initially, we'll keep the zero coefficients
                double[] tmpcoeff = new double[terms.length];
                int[] tmpexp = new int[terms.length];
                
                int idx = 0;
                
                for (String term : terms) {
                	// A non-zero exponent
                    if (term.contains("x")) {
                        String[] coeffs = term.split("x"); // Splitting at the x and not including it
                        tmpcoeff[idx] = Double.parseDouble(coeffs[0]);
                        // Making sure there is an x after the coefficient
                        if (coeffs.length > 1) {
                        	tmpexp[idx] = Integer.parseInt(coeffs[1]);
                        	idx++;
                        }
                        // There is no exponent, so the exponent equals 1
                        else {
                        	tmpexp[idx] = 1;
                        	idx++;
                        }
                    }
                    // No x, meaning there is a zero exponent and the coefficient equals the term itself
                    else {
                    	tmpcoeff[idx] = Double.parseDouble(term);
                    	tmpexp[idx] = 0;
                    	idx++;
                    }
                }
                
                // Getting rid of the zeroes
        		int new_len = 0;
        		for (int i=0;i<tmpcoeff.length;i++) {
        			if (tmpcoeff[i] != 0) new_len++;
        		}
        		
        		coeff = new double[new_len];
        		exp = new int[new_len];
        		int count = 0;
        		
        		for (int i=0;i<tmpcoeff.length;i++) {
        			if (tmpcoeff[i] != 0) {
        				coeff[count] = tmpcoeff[i];
        				exp[count] = tmpexp[i];
        				count++;
        			}
        		}
            }
        }
        
        catch (FileNotFoundException e) {
            System.err.println("File not found!: " + e.getMessage());
            return;
        }
    }
	
	public void saveToFile(String filename) {
		File file = new File(filename);
		if (!(file.exists())) {
			System.out.println("File does not exist.");
        }
		
		else {
			String expression = "";
			for (int i=0;i<coeff.length;i++) {
				expression = expression.concat(coeff[i] + "");
				if (exp[i] == 1) {
					expression = expression.concat("x");
				}
				else if (exp[i] != 0) {
					expression = expression.concat("x");
					expression = expression.concat(exp[i] + "");
				}
				if ((i<coeff.length-1) && (coeff[i+1] >= 0)) {
					expression = expression.concat("+");
				}
			}
			try (FileWriter writer = new FileWriter(filename)) {
	            writer.write(expression);
	        } catch (IOException e) {
	        	System.err.println("Error with writing to file: " + e.getMessage());
	            e.printStackTrace();
	        }
        }
		
	}
	
	public Polynomial add(Polynomial poly) {
		Polynomial new_poly = new Polynomial();
		Polynomial big_poly = new Polynomial();
		Polynomial small_poly = new Polynomial();
		int len = 0;
		
		if (coeff.length > poly.coeff.length) {
			big_poly = new Polynomial(coeff, exp); // Already removes zero coefficients
			small_poly = new Polynomial(poly.coeff, poly.exp); // Already removes zero coefficients
		}
		else {
			big_poly = new Polynomial(poly.coeff, poly.exp); // Already removes zero coefficients
			small_poly = new Polynomial(coeff, exp); // Already removes zero coefficients
		}
		len = big_poly.coeff.length; // Initially, the size is that of the bigger array
		
		// Note: Initially, we'll keep the zero sums
		
		// Deals with the exponents that are in the smaller array but not the bigger one
		int count;
		for (int i=0;i<small_poly.coeff.length;i++) {
			count = 0;
			for (int j=0;j<big_poly.coeff.length;j++) {
				if (small_poly.exp[i] != big_poly.exp[j]) {
					count++;
				}
				else {
					break;
				}
			}
			if (count == big_poly.coeff.length) { // We found an exponent entry that appears in the smaller but not bigger
				len++;
			}
		}
		
		new_poly.coeff = new double[len];
		new_poly.exp = new int[len];
		
		// Initially, giving the new polynomial all the big polynomial entries
		for (int i=0;i<big_poly.coeff.length;i++) {
			new_poly.coeff[i] = big_poly.coeff[i];
			new_poly.exp[i] = big_poly.exp[i];
		}
		
		// Initially, we'll add all the corresponding entries even those that sum to zero, we'll deal with it eventually
		for (int i=0;i<big_poly.coeff.length;i++) {
			for (int j=0;j<small_poly.coeff.length;j++) {
				if (new_poly.exp[i] == small_poly.exp[j]) {
					new_poly.coeff[i] += small_poly.coeff[j];
				}
			}
		}
		
		int idx = big_poly.coeff.length; // Represents the current index of the new polynomial
		
		// Now we add the entries that are exclusive to the small polynomial
		int icount;
		for (int i=0;i<small_poly.coeff.length;i++) {
			icount = 0;
			for (int j=0;j<big_poly.coeff.length;j++) {
				if (small_poly.exp[i] != big_poly.exp[j]) {
					icount++;
				}
				else {
					break;
				}
			}
			if (icount == big_poly.coeff.length) {
				new_poly.coeff[idx] = small_poly.coeff[i];
				new_poly.exp[idx] = small_poly.exp[i];
				idx++;
			}
		}
		
		// Now we get rid of the zeroes from the sum additions
		new_poly = new Polynomial(new_poly.coeff, new_poly.exp);
		return new_poly;
	}
	
	public Polynomial multiply(Polynomial poly) {
		// Initially, keep zero coefficients
		int len = coeff.length*poly.coeff.length; // Finding, initially, how many terms we'll have before simplification
		double[] c = new double[len];
		int[] e = new int[len];
		int idx = 0;
		
		for (int i=0;i<coeff.length;i++) {
			for (int j=0;j<poly.coeff.length;j++) {
				c[idx] = coeff[i]*poly.coeff[j];
				e[idx] = exp[i]+poly.exp[j];
				idx++;
			}
		}
		
		for (int i=0;i<len;i++) {
			for (int j=0;j<len;j++) {
				if (i!=j && (c[i] != 0) && e[i] == e[j]) {
					c[i] += c[j];
					c[j] = 0;
				}
			}
		}
		
		Polynomial new_poly = new Polynomial(c, e);
		return new_poly;
	}
	
	public double evaluate(double x) {
		double sum = 0;
		for (int i=0;i<coeff.length;i++) {
			sum += coeff[i]*(Math.pow(x, exp[i]));
		}
		return sum;
	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
	
}