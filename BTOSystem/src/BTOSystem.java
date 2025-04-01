
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class BTOSystem {
    public static void main(String[] args)
    {
    	int choice;
    	User currentUser = null;
    	AuthenticationController ac = new AuthenticationController();
    	
    	Scanner sc = new Scanner(System.in);
        List<User> users=new ArrayList<>();
        users.add(new Applicant("ggna","T0022060J",24,"sINGLE","password",false));
        users.add(new Applicant("gg","T002206wJ",24,"sINGLE","password",false));
        
        
        do {
        System.out.println("""
        		BTO Management System (BMS)
        		1: Login
        		2: Register
        		3: Exit
        		""");
        System.out.println("Please enter your choice :");
        choice=sc.nextInt();
        sc.nextLine();
        
        switch(choice) {
        case 1:        
	    	System.out.println("Please enter your NRIC:");
	        String nric = sc.nextLine();
	        for(int i=0;i<2;i++) {
	
	        	if(nric.equals(users.get(i).getNric()))
	        	{
	                System.out.println("Please enter your password:");
	        		String password = sc.nextLine();
	        		currentUser=users.get(i);
	        		ac.logIn(users.get(i), password);
	        		break;
	        	}
	        	
	        }
	        break;
        case 2:
        	System.out.println("pLEASE DONT REGISTER!");
        	break;
        case 3:
        	System.out.println("Good bYE");
            System.exit(0);
        	break;
        default:System.out.println("Please enter a valid choice");
        }}
        while(choice!=3&&ac.isLoggedIn()==false);
        
        if (currentUser != null) {

            currentUser.displayMenu();
        }
        
        
    }
}
