package ci.miage.ayenon.utils;

public class Utils {

	public static String getInitials(String firstName , String lastName)
	{
		String name = lastName + " " + firstName;
		String names[] = name.split(" ");
		String initials = "";
		for(int i=0; i < names.length ; ++i){
			names[i] = names[i].trim();
			initials += names[i].charAt(0);
		}
		return initials;
		
	}
}
