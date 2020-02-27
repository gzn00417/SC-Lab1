package P1;

import java.util.*;
import java.io.*;

public class MagicSquare {
	static final int N = 200;
	public static int[][] square = new int[N][N];
	public static boolean[] vis = new boolean[N * N + 1];

	public static void main(String[] args) throws IOException {
		for (char i = '1'; i <= '5'; i++) {
			System.out.println(i + " " + String.valueOf(isLegalMagicSquare("src/P1/txt/" + i + ".txt")));
		}
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while (n <= 0 || n % 2 == 0) {
			System.out.println("Input Wrong");
			n = sc.nextInt();
		}
		generateMagicSquare(n);
		System.out.println("6" + " " + String.valueOf(isLegalMagicSquare("src/P1/txt/" + "6" + ".txt")));
		return;
	}

	public static boolean isLegalMagicSquare(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader reader = new FileReader(file);
		BufferedReader bReader = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String line = "";

		int n = 0, m = 0;
		Arrays.fill(vis, false);
		while ((line = bReader.readLine()) != null) {
			String[] l = line.split("\t");
			m = l.length;
			for (int i = 0; i < m; i++) {
				square[n][i] = Integer.valueOf(l[i].trim());
				if (square[n][i] <= 0 || vis[square[n][i]])
					return false;
				else
					vis[square[n][i]] = true;
			}
			n++;
		}
		bReader.close();
		if (n != m)
			return false;
		int s1 = 0, s2 = 0, s = 0;
		for (int i = 0; i < n; i++) {
			s1 += square[i][i];
			s2 += square[n - i - 1][i];
		}
		if (s1 == s2)
			s = s1;
		else
			return false;
		for (int i = 0; i < n; i++) {
			s1 = s2 = 0;
			for (int j = 0; j < n; j++) {
				s1 += square[i][j];
				s2 += square[j][i];
			}
			if (s1 != s || s2 != s)
				return false;
		}
		return true;
	}

	public static boolean generateMagicSquare(int n) throws IOException {
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
				if (row == 0)
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		File file = new File("src/P1/txt/6.txt");
		PrintWriter output = new PrintWriter(file);
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				output.print(magic[i][j] + "\t");
			output.println();
		}
		output.close();
		return true;
	}
}