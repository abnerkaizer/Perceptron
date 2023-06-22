package tp2;

import java.util.List;
import java.util.Random;

public class Perceptron {
	private double[][] w;
	private double ni;
	private Random rand;

	public Perceptron(int qtdIn, int qtdOut, double ni) {
		rand = new Random();
		this.w = new double[qtdIn + 1][qtdOut];
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[0].length; j++) {
				w[i][j] = -0.03 + 0.06 * rand.nextDouble();// [-0.03,0.03]
			}
		}
		this.ni = ni;
	}

	public double[] treinar(List<Double> x, double[] y) {
		double[] theta = new double[y.length];
		x.add(1.0);
		for (int j = 0; j < y.length; j++) {
			double uj = 0;
			for (int i = 0; i < x.size(); i++) {
				uj += x.get(i) * this.w[i][j];
			}
			theta[j] = 1 / (1 + Math.pow(Math.E, -uj));
			for (int i = 0; i < x.size(); i++) {
				this.w[i][j] += ni * (y[j] - theta[j]) * x.get(i);
			}
		}
		return theta;
	}
}
