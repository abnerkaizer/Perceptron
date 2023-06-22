package tp2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private static double[][][] base = new double[625][2][];

	public static void main(String[] args) {
		for (int i = 0; i < base.length; i++) {
			base[i][0] = new double[4];
			base[i][1] = new double[3];
		}
		Path filePath = Paths.get("src", "tp2", "balance-scale.data");
		String fileName = filePath.toString();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				String c[] = line.split(",");
				if (c[0].equals("L")) {
					base[i][1][0] = 1;
					base[i][1][1] = 0;
					base[i][1][2] = 0;
				} else if (c[0].equals("B")) {
					base[i][1][0] = 0;
					base[i][1][1] = 1;
					base[i][1][2] = 0;
				} else if (c[0].equals("R")) {
					base[i][1][0] = 0;
					base[i][1][1] = 0;
					base[i][1][2] = 1;
				} else {
					base[i][1][0] = 0;
					base[i][1][1] = 0;
					base[i][1][2] = 0;
				}
				for (int j = 1; j < c.length; j++) {
					base[i][0][j - 1] = Double.parseDouble(c[j]);
				}
				i++;
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		double startTime = System.currentTimeMillis();
		execute(100000);
		double endTime = System.currentTimeMillis();
		double tempo = (endTime - startTime) / 1000;
		System.out.printf("Tempo: %.2f s\n", tempo);
	}

	private static void execute(int epocas) {
		Perceptron perceptron = new Perceptron(4, 3, 0.01);
		for (int e = 0; e < epocas; e++) {
			double erroAproxEpoca = 0;
			double erroClassE = 0;
			for (int a = 0; a < base.length; a++) {
				double[] x = base[a][0];
				double[] y = base[a][1];
				List<Double> xin = new ArrayList<Double>(x.length + 1);
				for (double d : x) {
					xin.add(d);
				}
				double[] theta = perceptron.treinar(xin, y);
				double erroAproxAmostra = 0;
				for (int i = 0; i < theta.length; i++) {
					erroAproxAmostra += Math.abs((y[i] - theta[i]));
				}
				double max = maior(theta);
				for (int i = 0; i < theta.length; i++) {
					if (theta[i] == max)
						theta[i] = 1;
					else
						theta[i] = 0;
				}
				double erroClassA = 0;
				for (int i = 0; i < theta.length; i++) {
					erroClassA += Math.abs((y[i] - theta[i]));
				}
				if (erroClassA > 0) {
					erroClassA = 1;
				} else {
					erroClassA = 0;
				}
				erroClassE += erroClassA;
				erroAproxEpoca += erroAproxAmostra;

			}
			System.out.println("Epoca: " + (e + 1) + " - erro de aproximação: " + erroAproxEpoca
					+ " - erro de classificação: " + erroClassE);
		}
	}

	private static double maior(double[] numbers) {
		double maxNumber = Double.MIN_VALUE;

		for (double number : numbers) {
			if (number > maxNumber) {
				maxNumber = number;
			}
		}
		return maxNumber;
	}
}
