import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;



public class main {

	   public static void main(String[] args){
		
		  DataProc test = new DataProc();
		 test.readData();
		 test.seperateIntoCycles();
		 test.calculateAvgCycle();
	   }
		  
		  
		  /*
		   
		 ArrayList<DataPoint> avgCycle = new ArrayList<DataPoint>();
		  
		PrintWriter outFile;	
		ArrayList<List<DataPoint>> cycleList = new ArrayList<List<DataPoint>>();
		ArrayList<DataPoint> dataList = test.getDataList();

		int numCycles = 0;
		int cycleStart = -1;
		int cycleStop;
		double tolerance = .25;		//tolerance level of 0 (not the correct value yet)
		int count = 0;

		double value; 
		for (int i = 0; i < dataList.size(); i++)						//goes through the datapoints in dataList
		{
			value = dataList.get(i).getValue();							
			if (((value < tolerance) && (value >= 0)) || ((value > (tolerance * -1)) && (value <= 0)))	//if it is within a tolerable level of 0 for long enough set the end of the cycle to be the last datapoint within the tolerance 
				count++;
			else 
			{
				if (count >= 50)
				{
					if (cycleStart != -1)						// on first runthough only set cycle start, on all others assign new cycle
					{
						cycleStop = i - 1;
						cycleList.add(dataList.subList(cycleStart, cycleStop));	
					}
					numCycles++;
					cycleStart = i;	
				}	
				count = 0;
			}
		}
			
		

	// this is the delay between datapoints
		double interval = dataList.get(1).getTime() - dataList.get(0).getTime();


	//calculate the length the avg cycle should have be getting the sum of the lengths of each cycle and dividing by the number of cycles
		int avgLength = 0;

		for( int x = 0; x < cycleList.size();x++)			
		{
			avgLength = avgLength + cycleList.get(x).size();
		}
		avgLength = avgLength/cycleList.size();

		int num_used = 0;		
		double accelSum = 0;	

		for (int i = 0; i < avgLength ; i++) 		//this number indicates what element of the avg cycle we are calculating
		{
			accelSum = 0;
			num_used = 0;
			for(int j = 0;	j < cycleList.size();j++)		//this loop goes through all the cycles and grabs the Datapoint at element i and gets its acceleration value
			{
				if ( i  < cycleList.get(j).size())		
				{
					accelSum = accelSum+ cycleList.get(j).get(i).getValue();
					num_used++;
				}
				avgCycle.add(new DataPoint(accelSum/num_used, i*interval));		//creation of new Datapoint in avgCycle with accerlation of sum of acceleration of all the cycles that had a datapoint at that element 
			}										// divided by the number of cycles that had a datapoint at that element
		}



		try
		{
			outFile = new PrintWriter(new BufferedWriter(new FileWriter("Run.txt", true)));//Output File
			
			 for(int x = 0; x < cycleList.get(1488).size() ;x++)			
			 
			{
				outFile.println(cycleList.get(1488).get(x).getValue());
			}
		
			for(int x = 0; x < cycleList.get(1531).size() ;x++)			
			{
			outFile.println(cycleList.get(1531).get(x).getValue());
			}
			
			 for(int x = 0; x < cycleList.size() ;x++)
			 {
				 if (cycleList.get(x).size() > 310 || cycleList.get(x).size() < 200 )
					 outFile.println(cycleList.get(x).size() +" "+ x);
			 }
			
			
			outFile.println(avgLength);
			 
			
		outFile.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	*/

}
