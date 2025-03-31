
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class BTOSystem {
    public static void main(String[] args)
    {
    	AuthenticationController ac = new AuthenticationController();
    	
    	Scanner sc = new Scanner(System.in);
        List<User> users=new ArrayList<>();
        users.add(new Applicant("ggna","T0022060J",24,"sINGLE","password",false));
        users.add(new Applicant("gg","T002206wJ",24,"sINGLE","password",false));
        
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
