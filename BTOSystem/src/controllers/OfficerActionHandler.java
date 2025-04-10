package controllers;

import java.util.InputMismatchException;
import java.util.Scanner;
import data.UserDatabase;
import entities.Officer;
import entities.User;
import view.View;

public class OfficerActionHandler implements ActionHandler{
	public User handleAction(int choice,User currentUser, Scanner sc,UserDatabase db){
        switch(choice) {
      //Manager Officer menu
        case 1: handleProjectAction(choice,currentUser,sc);
    	break;
    	case 2:	handleEnquiryAction(choice,currentUser,sc);
    	break;
    	case 3:return(AuthenticationController.resetPassword(currentUser, db, currentUser.getNric(), currentUser.getPassword(), sc));
    	case 4: currentUser=currentUser.logout();
    	return currentUser;
    	default:View.invalidChoice();
        }
		return currentUser;

	}
	

	public void handleEnquiryAction(int choice,User currentUser, Scanner sc) {
		View.enquiryMenu(currentUser,((Officer)currentUser).getEnquiryOptions());
	}
    public void handleProjectAction(int choice,User currentUser, Scanner sc) {
    	do {
		View.projectMenu(currentUser,((Officer)currentUser).getProjectOptions());
		choice=sc.nextInt();
		sc.nextLine();
		switch(choice)
		{
		//Print all projects
		case 1: 
        break;
        //Add projects, check if project already existed?
        case 2:	try { 
        	
        	}
        catch(InputMismatchException e)
        {
        	System.out.println("Invalid input! Please enter the correct type of data");
        	sc.nextLine();
        }
        catch (Exception e) {
        	System.out.println("An unexpected error has occured"+e.getMessage());
        }
        break;
        //Delete BTO Projects , check if exists
        case 3:
        break;
        
        case 4:
        break;
        case 5://TODO Generate Receipt
        break;
        default: View.invalidChoice();
		}}
    	while(choice!=6);
    }
    

}
