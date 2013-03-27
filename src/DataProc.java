import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.math.*;

public class DataProc 
{
	private ArrayList<DataPoint> avgCycle = new ArrayList<DataPoint>();
	private ArrayList<DataPoint> dataList = new ArrayList<DataPoint>();
	private ArrayList<List<DataPoint>> cycleList = new ArrayList<List<DataPoint>>();
	private File fileChoice = null;
	private String inData = "";
	

	public void readData()									//Get a File, and then create a ArrayList with DataPoints
	{
		/*Variables*/
		Scanner inFile = new Scanner(System.in);
		String test = "", gatherString = "";
		int count = 0, commaCount = 0 ;
		double time = 0.0, yValue = 0.0;
		boolean dataStart = false, negative = false;
		
		System.out.println("Select a File...");
		//File Chooser
		try
		{
			JFileChooser chooser = new JFileChooser();		//Create the FileChooser Window
			int returnVal = chooser.showOpenDialog(null);	//Checks to see if user chose a file
			if(returnVal == JFileChooser.APPROVE_OPTION)	//User chose a file
			{
				fileChoice = chooser.getSelectedFile();
			}
			
			inData = fileChoice.toString();					//Our Data File that was obtained through the file chooser
		}
		catch(NullPointerException e)						//In the case user does not select a file.
		{
			System.out.println("No File Selected"); 		//Output Exception
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
		//End File Chooser
		if(inData.contains(".txt"))							//Make sure the user chose a valid file
			System.out.println("Valid File Selected");
		else
		{
			System.out.println("Invalid File Selected");
		}
		System.out.println("Loading Data...");
		try
		{
			Scanner scanner = new Scanner(new FileInputStream(inData));
			for(int i = 0; i < 11; i ++)					//Loop to go through the trash lines
			{
				test = scanner.nextLine();
			}
			while(scanner.hasNext())						//While the scanner is not empty continue grabbing strings
			{
				test = scanner.next();	
				count = 0;
				commaCount = 0;								//A count to keep track of the # of commas in the string
				for(int i = 0; i < test.length() ; i++)
				{
					if(count == 0)							//Once at the begininig of the string: Gather the number one character at a time to obtain time
					{
						for(int k = 0; test.charAt(k) != ','; k++)//Loop to get the full number character by character
						{
							gatherString = gatherString + test.charAt(k);
						}
						count ++;
						time = Double.parseDouble(gatherString);
							
					}//End if count == 0
					if(commaCount == 2)						//When commaCount is equal to 2, the next character is the start of the Y value
					{
						gatherString = "";
						for(; test.charAt(i) != ','; i++)	//Loop until the next comma to get the whole Y Value.
						{
							if(test.charAt(i) == '-')		//If the start of this string begins with '-' it is a negative number
							{
								negative = true;
								i++;
							}	
							gatherString = gatherString + test.charAt(i);
						}//End loop to get Y value
						
						yValue = Double.parseDouble(gatherString);//Convert the string to a double value named yValue
							

						if(negative)						//If Negative, add make Yvalue negative
						{
							yValue = -yValue;
							negative = false;
						}
						
						yValue = yValue - 1;				//Eliminate the natural g on the sensor(1 g)
						dataList.add(new DataPoint(time, yValue));//Add to list

					}//End if commaCount == 2
					if(test.charAt(i) == ',')//Everytime we reach a comma, increase the comma count and be sure to erase the gatherString
					{
						commaCount ++;
						gatherString = "";
					}
				}
				count = 0;
				
			}//End While
			System.out.println("Data Retrivial Complete.");
			scanner.close();
			inFile.close();
		}//End Try
		catch(Exception e)
		{
			System.out.println(e);							//Output Exception
		}	
	}//End readData method
	
	public ArrayList<Double> getAllTimes()					//Returns all time values from a given data set
	{
		ArrayList<Double> list = new ArrayList<Double>();
		
		for(int i = 0; i < dataList.size() - 1; i++)
		{
			double temp = dataList.get(i).getTime();
			list.add(temp);
		}
		
		return list;
	}//End getAllTimes()
	public ArrayList<Double> getAllValues()					//Returns all time values from a given data set
	{
		ArrayList<Double> list = new ArrayList<Double>();
		
		for(int i = 0; i < dataList.size() - 1; i++)
		{
			double temp = dataList.get(i).getValue();
			list.add(temp);
		}
		
		return list;
	}//End getAllValues()
	
	public ArrayList<DataPoint> getDataList()
	{
		return dataList;
	}//End getDataList()
	
	public String getFileName()								//Returns the file name
	{
		return inData;
	}//End getFileName()
	
	public void tableToText()								//Used for Debug purposes. Outputs to where you selected your workspace to be
	{
		PrintWriter outFile;
		
		try
		{
			outFile = new PrintWriter(new BufferedWriter(new FileWriter("Run.txt", true)));//Output File
			outFile.println("Time:\t\t\t\tY:\t\t\t");		//Set the columns
			outFile.println("-----------------------------------------------------------");	
			
			for(int i = 0; i < dataList.size() - 1; i++)
			{
				outFile.println(dataList.get(i).getTime() + "\t\t\t\t" + dataList.get(i).getValue());
			}
			
			outFile.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void seperateIntoCycles()
	{
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
					cycleStart = i;	
				}	
				count = 0;
			}
		}
	}
	public void calculateAvgCycle()
	{
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
		double accelAvg;

		for (int i = 0; i < avgLength ; i++) 		//this number indicates what element of the avg cycle we are calculating
		{
			accelSum = 0;
			num_used = 0;
			for(int j = 0;	j < cycleList.size();j++)		//this loop goes through all the cycles and grabs the Datapoint at element i and gets its acceleration value
			{
				if ( i  < cycleList.get(j).size())		
				{
					accelSum = accelSum + cycleList.get(j).get(i).getValue();
					num_used++;
				}
						
			}		
			accelAvg = accelSum/num_used;
			avgCycle.add(new DataPoint(i*interval,accelAvg));	//creation of new Datapoint in avgCycle with accerlation of sum of acceleration of all the cycles that had a datapoint at that element
																		// divided by the number of cycles that had a datapoint at that element 
		}

		
		PrintWriter outFile;	

		try
		{
			outFile = new PrintWriter(new BufferedWriter(new FileWriter("Run.txt", true)));//Output File
			 for(int x = 0; x < avgCycle.size(); x++)			
			{
				outFile.println(avgCycle.get(x).getValue());
			}
			
/*			 for(int x = 0; x < cycleList.size() ;x++)
			 {
				 if (cycleList.get(x).size() > 310 || cycleList.get(x).size() < 200 )
					 outFile.println(cycleList.get(x).size() +" "+ x);
			 }
			
	*/		
			
			 
			
		outFile.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}