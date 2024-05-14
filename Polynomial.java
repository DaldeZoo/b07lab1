
public class Polynomial {
	double[] coeff;
	
	public Polynomial() {
		coeff = new double[1];
		coeff[0] = 0;
	}
	public Polynomial(double[] array) {
		coeff = new double[array.length];
		for (int i=0;i<array.length;i++) {
			coeff[i] = array[i];
		}
	}
	public Polynomial add(Polynomial poly) {
		if (coeff.length > poly.coeff.length) {
			Polynomial new_poly;
			new_poly = new Polynomial();
			new_poly.coeff = new double[coeff.length];
			for (int i=0;i<poly.coeff.length;i++) {
				new_poly.coeff[i] += poly.coeff[i];
			}
			return new_poly;
		}
		// else
		for (int i=0;i<this.coeff.length;i++) {
			poly.coeff[i] += this.coeff[i];
		}
		return poly;
	}
	public double evaluate(double x) {
		double sum = coeff[0];
		for (int i=1;i<coeff.length;i++) {
			sum += coeff[i]*Math.pow(x, i);
		}
		return sum;
	}
	public boolean hasRoot(double x) {
		double sum = evaluate(x);
		return sum == 0;
	}
}
