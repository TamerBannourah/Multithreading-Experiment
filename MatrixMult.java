import java.math.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMult{
	
	int n;
	int m;
	int bitLength;
	
	BigInteger[][] multMatrix1;
	BigInteger[][] multMatrix2;
	BigInteger[][] answer;
	
	
	public MatrixMult(int height, int width, int len) {
		n = height;
		m = width;
		bitLength = len;
		multMatrix1 = new BigInteger[n][m];
		multMatrix2 = new BigInteger[m][n];
		answer = new BigInteger[n][n];
	}
	
	public void printM1() {
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < m; col++) {
				System.out.print(multMatrix1[row][col]+ " ");
			}
			System.out.println();
		}
		System.out.println("--------Mult Matrix1--------");
	}
	
	public void printM2() {
		for(int row = 0; row < m; row++) {
			for(int col = 0; col < n; col++) {
				System.out.print(multMatrix2[row][col]+ " ");
			}
			System.out.println();
		}
		System.out.println("--------Mult Matrix2--------");
	}
	
	public void printAns() {
		System.out.println("Mult Answer vvvvv");
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < n; col++) {
				System.out.print(answer[row][col]+ " ");
			}
			System.out.println();
		}
		System.out.println("Mult Answer ^^^^^");
	}
	
	public void fillMatrices() {
		Random rand = new Random();
		BigInteger temp = new BigInteger(bitLength, rand);
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < m; col++) {
				temp = new BigInteger(bitLength, rand);
				multMatrix1[row][col] = temp;
			}
		}
		
		for(int row = 0; row < m; row++) {
			for(int col = 0; col < n; col++) {
				temp = new BigInteger(bitLength, rand);
				multMatrix2[row][col] = temp;
			}
		}
		
	}
	
	public void fillWithCounting() {
		BigInteger temp = BigInteger.ZERO;
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < m; col++) {
				temp = temp.add(BigInteger.ONE);
				multMatrix1[row][col] = temp;
			}
		}
		
		for(int row = 0; row < m; row++) {
			for(int col = 0; col < n; col++) {
				temp = temp.add(BigInteger.ONE);
				multMatrix2[row][col] = temp;
			}
		}
	}
	
	public void clearAns() {
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < n; col++) {
				answer[row][col] = null;
			}
		}
	}
	
	public void multiplySingleThread(boolean printRowConfirmation) {
		BigInteger rowTotal = BigInteger.ZERO;
		int percentSeg = 0;
		
		for(int arow = 0; arow < n; arow++) {
			for(int acol = 0; acol < n; acol++) {
				for(int m1 = 0; m1 < m; m1++) {
					//System.out.println("M1:"+ matrix1[arow][m1]);
					//System.out.println("M2:"+ matrix2[m1][acol]);
					BigInteger subprod = multMatrix1[arow][m1].multiply(multMatrix2[m1][acol]);
					rowTotal = rowTotal.add(subprod);
				}
				//System.out.println(arow +"," + acol+": "+ rowTotal);
				answer[arow][acol] = rowTotal;
				rowTotal = BigInteger.ZERO;
			}
			
			if(printRowConfirmation == true) {
				if(percentSeg == 5) {
					float per = (float) (((float) arow/(float) n)*100.0);
					System.out.println("Percent Complete: " + Math.round(per)+"%");
					percentSeg = 0;
				}
				percentSeg++;
			}
				
		}//end row loop
		
	}
	
	public class dotProduct implements Runnable{
		
		int r;
		int c;
		public dotProduct(int row, int col) {
			  r = row;
			  c = col;
		}

		@Override
		public void run() {
			BigInteger total = BigInteger.ZERO;
			//System.out.println("Thread "+Thread.currentThread().getId());
			//System.out.println("active threads: "+Thread.activeCount());
			for(int m1 = 0; m1 < m; m1++) {
				BigInteger subprod = multMatrix1[r][m1].multiply(multMatrix2[m1][c]);
				total = total.add(subprod);
				//System.out.println("subtotal: "+total);
			}
			answer[r][c] = total;
			
		}
	}
	
	public void multiplyMultithread() {
		ExecutorService pool = Executors.newWorkStealingPool();


		for(int arow = 0; arow < n; arow++) {
			for(int acol = 0; acol < n; acol++) {
				dotProduct dp = new dotProduct(arow,acol);
				pool.execute(dp);
				//System.out.println(arow +"," + acol+": "+ rowTotal);
			}
			//System.out.println("Row "+arow +" Complete");
		}
		pool.shutdown();

		try {
			pool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(pool.isTerminated() == true) {
//			System.out.println("All Multiply Threads Complete");
//		}
		
	}//end multiplyMultithread
	
	
	
}
