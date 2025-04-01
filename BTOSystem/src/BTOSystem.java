
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class BTOSystem {
    public static void main(String[] args)
    {
    	final String File_Path="Data/login.txt";
    	AuthenticationController ac = new AuthenticationController();
    	
    	Scanner sc = new Scanner(System.in);
        List<User> users=new ArrayList<>();
        users.add(new Applicant("ggna","T003306J",24,"sINGLE","password",false));
        users.add(new Applicant("gg","T0033061J",24,"sINGLE","password",false));
        
        System.out.println("BTO Management System (BMS)");
        System.out.println("Please enter your NRIC:");
        String nric = sc.nextLine();
        for(int i=0;i<2;i++) {

        	if(nric.equals(users.get(i).getNric()))
        	{
                System.out.println("Please enter your password:");
        		String password = sc.nextLine();
        		ac.logIn(users.get(i), password);
        		break;
        	}
        	
        }
        
    }
}
