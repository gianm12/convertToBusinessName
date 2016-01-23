package convertToBusinessName;

public class convertToBusnNameOnly {
public static void main(String[] args) {
	
	String techName = "firstName";
	String name=null;
	char upperLetter;
	
	String[] names= techName.split("(?=[A-Z])"); //Separate by upper case letters. 
	for (int i = 0; i < names.length; i++) 
	{
		name=names[i];
		if(name.startsWith("_"))
		{
			name= name.substring(1, name.length());
		}
		if(name.endsWith("_"))
		{
			name= name.substring(0, name.length()-1);
		}
		if(!Character.isUpperCase((name.charAt(0))))
		{
			upperLetter =  name.toUpperCase().charAt(0);
			name = upperLetter +  name.substring(1, name.length());
		}
		System.out.println(name);

	}
}
	
}
