package app;

public class Room 
{
	private Case typeC;

	public Room(Case type) 
	{
		typeC=type;
	}

	public Case getTypeC() 
	{
		return typeC;
	}

	public void setTypeC(Case typeC) 
	{
		this.typeC = typeC;
	}
	
	public String toString()
	{
		return typeC.name();
	}
}
