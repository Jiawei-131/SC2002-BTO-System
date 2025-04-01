
import java.util.Scanner;
import data.UserDatabase;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
public class BTOSystem {
    public static void main(String[] args)
    {
    	final String File_Path="data/LoginInfo.txt";
    	UserDatabase db= new UserDatabase(File_Path);
    	AuthenticationController ac = new AuthenticationController();
    	Scanner sc = new Scanner(System.in);
//        List<User> users=new ArrayList<>();
//        users.add(new Applicant("ggna","T003306J",24,"sINGLE","password",false));
//        users.add(new Applicant("gg","T0033061J",24,"sINGLE","password",false));
        Map<String,String>users=db.readUsers();
        System.out.println("BTO Management System (BMS)");
        System.out.println("Please enter your NRIC:");
        String nric = sc.nextLine();
        for(User user : user) {

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
