
public class DataPoint
{
	private double time, yValue;
	
	public DataPoint()
	{
		time = 0;
		yValue = 0;
	}
	public DataPoint(double t, double y)
	{
		time = t;
		yValue = y;
	}
	public double getTime()
	{
		return time;
	}
	public double getValue()
	{
		return yValue;
	}
	public String toString()
	{
		return "Time: " + time + "\t\tValue: " + yValue;
	}
}

