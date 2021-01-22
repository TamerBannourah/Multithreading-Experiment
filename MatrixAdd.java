import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.math.*;

public class MatrixAdd {
	
	int h,w, bitLength;
	
	BigInteger[][] addMatrix1;
	BigInteger[][] addMatrix2;
	BigInteger[][] answer;
	
	public MatrixAdd(int height, int width, int bitLength) {
		h = height;
		w = width;
		this.bitLength = bitLength;
		
		addMatrix1 = new BigInteger[h][w];
		addMatrix2 = new BigInteger[h][w];
		answer = new BigInteger[h][w];
	}
	
	public void printM1() {
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				System.out.print(addMatrix1[row][col]+ " ");
			}
			System.out.println();
		}
		System.out.println("-------Add Matrix1---------");
	}
	
	public void printM2() {
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				System.out.print(addMatrix2[row][col]+ " ");
			}
			System.out.println();
		}
		System.out.println("--------Add Matrix2--------");
	}

	public void printAns() {
		System.out.println("Add Answer vvvvv");
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				System.out.print(answer[row][col]+ " ");
			}
			System.out.println();
		}
		System.out.println("Add Answer ^^^^^");
	}

	public void fillMatrices() {
		//System.out.println("Filling The Matricies");
		Random rando = new Random();
		BigInteger temp = new BigInteger(bitLength, rando);
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				temp = new BigInteger(bitLength, rando);
				addMatrix1[row][col] = temp;
			}
		}
		
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				temp = new BigInteger(bitLength, rando);
				addMatrix2[row][col] = temp;
			}
		}
		//System.out.println("Done Filling The Matricies");
	}

	public void addSingleThread(boolean rowConfirm) {
		int percentSeg = 0;
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				answer[row][col] = addMatrix1[row][col].add(addMatrix2[row][col]);
			}
			if(rowConfirm == true) {
				if(percentSeg == 5) {
					float per = (float) (((float) row/(float) h)*100.0);
					System.out.println("Percent Complete: " + Math.round(per)+"%");
					percentSeg = 0;
				}
				percentSeg++;
			}
			
		}
		
	}
	
	public class ThreadAdd implements Runnable{
		
		int r,c;
		public ThreadAdd(int row, int col) {
			r = row;
			c = col;
		}
		
		public void run() {
			answer[r][c] = addMatrix1[r][c].add(addMatrix2[r][c]);
		}
		
	}
	
	
	public void addMultiThread() {
		ExecutorService pool = Executors.newWorkStealingPool();
		
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				ThreadAdd add = new ThreadAdd(row,col);
				pool.execute(add);
			}
		}
		pool.shutdown();
		try {
			pool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(pool.isTerminated() == true) {
//			System.out.println("All Add Threads Complete");
//		}

	}
	
	public class ThreadAddModi implements Runnable{
		
		int r,n;
		public ThreadAddModi(int row, int n) {
			r = row;
			this.n = n;
		}
		
		public void run() {
			for(int i = 0; i < n; i++) {
				answer[r][i] = addMatrix1[r][i].add(addMatrix2[r][i]);
			}
			
		}
		
	}
	public void addMultiThreadModi() {
		ExecutorService pool = Executors.newWorkStealingPool();
		
		for(int row = 0; row < h; row++) {
			ThreadAddModi add = new ThreadAddModi(row,w);
			pool.execute(add);
		}
		
		pool.shutdown();
		try {
			pool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(pool.isTerminated() == true) {
//			System.out.println("All Add Threads Complete");
//		}

	}

	
}//end class
