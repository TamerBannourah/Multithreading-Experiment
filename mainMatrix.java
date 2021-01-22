
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.*;
import java.util.*;
import java.util.concurrent.*;

public class mainMatrix{
	
	public static double addST(MatrixAdd addM, boolean print) {
		
		long start = System.currentTimeMillis();
		addM.addSingleThread(print);
		long end = System.currentTimeMillis();
		
		double timeSec = (end-start)/1000.0;
//		System.out.println("Single Thread Time (in MilliSeconds): " + (end-start));
//		System.out.println("Single Thread Time (in Seconds): " + timeSec);
//		System.out.println("Single Thread Time (in Minutes): " + timeSec/60.0);
		return (end-start);
	}
	
	public static double addMT(MatrixAdd addM) {
		
		long start = System.currentTimeMillis();
		addM.addMultiThread();
		long end = System.currentTimeMillis();
		
		double timeSec = (end-start)/1000.0;
//		System.out.println("Multi Thread Time (in MilliSeconds): " + (end-start));
//		System.out.println("Multi Thread Time (in Seconds): " + timeSec);
//		System.out.println("Multi Thread Time (in Minutes): " + timeSec/60.0);
		return (end-start);
	}
	
	public static double multST(MatrixMult multM, boolean print) {
		
		long start = System.currentTimeMillis();
		multM.multiplySingleThread(print); //set bool to print row confirmation
		long end = System.currentTimeMillis();
		
		double timeSec = (end-start)/1000.0;
//		System.out.println("Single Thread Time (in MilliSeconds): " + (end-start));
//		System.out.println("Single Thread Time (in Seconds): " + timeSec);
//		System.out.println("Single Thread Time (in Minutes): " + timeSec/60.0);
		return (end-start);
		
	}
	
	public static double multMT(MatrixMult multM) {
		
		long start = System.currentTimeMillis();
		multM.multiplyMultithread(); //set bool to print row confirmation
		long end = System.currentTimeMillis();
		
		double timeSec = (end-start)/1000.0;
		
//		System.out.println("Multi Thread Time (in MilliSeconds): " + (end-start));
//		System.out.println("Multi Thread Time (in Seconds): " + timeSec);
//		System.out.println("Multi Thread Time (in Minutes): " + timeSec/60.0);
		return (end-start);
	}
	
	public static double addMTModi(MatrixAdd addM) {
		long start = System.currentTimeMillis();
		addM.addMultiThreadModi(); //set bool to print row confirmation
		long end = System.currentTimeMillis();
		return (end-start);
	}
	
	public static void main(String[] args) {
		
		int bitSize = 1024;
		PrintStream out;
		String fileName = bitSize + "_addMod_MT.txt";
		
		try {
			out = new PrintStream(new File(fileName));
			System.setOut(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 100; i <= 2000; i+=100) { 
			//MatrixMult multM = new MatrixMult(i,i,bitSize);
			MatrixAdd addM = new MatrixAdd(i,i,bitSize);
			addM.fillMatrices();
			//double timeInMilli = addST(addM,false);
			double timeInMilli = addMTModi(addM);
			
			System.out.println(i+","+timeInMilli/1000.0);
		}
		
		//-Xms1024M -Xmx1524M to change JVM heap space
		// Run configurations -> arguments -> JV variables
		// Xmx max mem pool -- Xms intial mem pool
		
//		MatrixAdd addM = new MatrixAdd(100,100,bitSize);
//		addM.fillMatricies();
//		//double timeInMilli = addST(addM,false);
//		double timeInMilli = addMTModi(addM);
//		System.out.println(100+","+timeInMilli/1000.0);
//		//addM.printM1();
//		//addM.printM2();
//		//addM.printAns();
			
	}//end main func
}
