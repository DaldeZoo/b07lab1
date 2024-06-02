import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	public static void main(String [] args) {
		double[] c1 = {6, -2, 5, 0, 9, -12};
		int[] e1 = {0, 1, 3, 5, 7, 2};
		Polynomial p1 = new Polynomial(c1, e1);
		double[] c2 = {4, 12, 2, 7};
		int[] e2 = {3, 2, 1, 5};
		Polynomial p2 = new Polynomial(c2, e2);
		System.out.println("Polynomial 1:");
		for (int i=0;i<p1.coeff.length;i++) {
			System.out.println("coefficient: " + p1.coeff[i] + "	exponent: " + p1.exp[i]);
		}
		System.out.println();
		System.out.println("Polynomial 2:");
		for (int i=0;i<p2.coeff.length;i++) {
			System.out.println("coefficient: " + p2.coeff[i] + "	exponent: " + p2.exp[i]);
		}
		System.out.println();
		double[] c3 = {2, 3, 0, 0};
		int[] e3 = {2, 0, 1, 3};
		Polynomial p3 = new Polynomial(c3, e3);
		double[] c4 = {4, -3, 4, 5};
		int[] e4 = {3, 2, 1, 0};
		Polynomial p4 = new Polynomial(c4, e4);
		System.out.println("Polynomial 3:");
		for (int i=0;i<p3.coeff.length;i++) {
			System.out.println("coefficient: " + p3.coeff[i] + "	exponent: " + p3.exp[i]);
		}
		System.out.println();
		System.out.println("Polynomial 4:");
		for (int i=0;i<p4.coeff.length;i++) {
			System.out.println("coefficient: " + p4.coeff[i] + "	exponent: " + p4.exp[i]);
		}
		double[] c5 = {1, -5, 6};
		int[] e5 = {2, 1, 0};
		Polynomial p5 = new Polynomial(c5, e5);
		System.out.println();
		System.out.println("Polynomial 5:");
		for (int i=0;i<p5.coeff.length;i++) {
			System.out.println("coefficient: " + p5.coeff[i] + "	exponent: " + p5.exp[i]);
		}
		
		System.out.println();		
		System.out.println("--- Test 1: Adding Polynomial 1 to Polynomial 2 ---");
		p1 = p1.add(p2);
		System.out.println("Polynomial 1:");
		for (int i=0;i<p1.coeff.length;i++) {
			System.out.println("coefficient: " + p1.coeff[i] + "	exponent: " + p1.exp[i]);
		}
		
		System.out.println();		
		System.out.println("--- Test 2: Multiplying Polynomial 3 to Polynomial 4 ---");
		p3 = p3.multiply(p4);
		for (int i=0;i<p3.coeff.length;i++) {
			System.out.println("coefficient: " + p3.coeff[i] + "	exponent: " + p3.exp[i]);
		}
		
		System.out.println();
		System.out.println("--- Test 3: Evaluating and Checking the Existence of a Root ---");
		System.out.println("1. Evaluating Polynomial 5 with x = 7: " + p5.evaluate(7));
		System.out.println("   Checking whether x = 7 is a root of Polynomial 5: " + p5.hasRoot(7));
		System.out.println("2. Evaluating Polynomial 5 with x = 3: " + p5.evaluate(3));
		System.out.println("   Checking whether x = 3 is a root of Polynomial 5: " + p5.hasRoot(3));
		
		System.out.println();
		System.out.println("--- Test 4: Using the File constructor to Initialize the contents of its Polynomial ---");
		String expression = "5-3x2+0x6-7x8+0x3+1x";
        String filePath = "example.txt";

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(expression);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error creating example.txt: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Reading the Polynomial Expression from the file...");
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                String poly = scanner.nextLine();
                System.out.println("Polynomial: " + poly);
            } else {
                System.out.println("Error the file is empty.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File(filePath);
        Polynomial pf = new Polynomial(file);
        System.out.println("The coressponding polynomial: ");
        for (int i=0;i<pf.coeff.length;i++) {
            System.out.println("coefficient: " + pf.coeff[i] + "    exponent: " + pf.exp[i]);
        }
        
        System.out.println();
		System.out.println("--- Test 5: Saving a Polynomial Expression to a File ---");
        double[] cf2 = {5, -3, 7, 1, 0, 5};
        int[] ef2 = {0, 2, 8, 1, 7, 12};
        Polynomial pf2 = new Polynomial(cf2, ef2);
        System.out.println("The Polynomial:");
        for (int i=0;i<pf2.coeff.length;i++) {
			System.out.println("coefficient: " + pf2.coeff[i] + "	exponent: " + pf2.exp[i]);
		}
        System.out.println("Saving to file...");
        pf2.saveToFile("polynomial.txt");
        System.out.println("Reading the polynomial from the file:");
        try (Scanner scanner = new Scanner(new File("polynomial.txt"))) {
            if (scanner.hasNextLine()) {
                String filecontent = scanner.nextLine();
                System.out.println("Polynomial: " + filecontent);
            } else {
                System.out.println("Error the file is empty.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}