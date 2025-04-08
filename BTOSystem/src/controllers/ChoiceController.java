package controllers;

import java.util.InputMismatchException;

import java.util.Scanner;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
import view.View;

public class ChoiceController {
	
	//ManagerActionHandler handler = new ManagerActionHandler();
	public User choice(int choice,User currentUser,Scanner sc) {
		ActionHandler handler = null;
		if (currentUser instanceof Officer) {
			handler=new OfficerActionHandler();
        } 
      //Manager Applicant menu
		else if (currentUser instanceof Applicant) {
			handler=new ApplicantActionHandler();
        } 
        //Manager Project menu
		else if (currentUser instanceof Manager) {
			handler = new ManagerActionHandler();
        }
		currentUser=handler.handleAction(choice, currentUser, sc);
		return currentUser;
//        switch(choice) {
//      //Manager Officer menu
//        case 1: if (currentUser instanceof Officer) {
//        	((Officer)currentUser).viewProjects();
//        } 
//      //Manager Applicant menu
//		else if (currentUser instanceof Applicant) {
//			((Applicant)currentUser).viewProjects();
//        } 
//        //Manager Project menu
//		else if (currentUser instanceof Manager) {
//				ManagerActionHandler handler = new ManagerActionHandler();
//				handler.handleAction(choice, currentUser, sc);
//        }
//    	break;
//    	case 2:if (currentUser instanceof Officer) {
//    		//TODO fill in with option
//		        } 
//        		else if (currentUser instanceof Applicant) {
//        			//TODO fill in with option
//		        } 
//    			else if (currentUser instanceof Manager) {
//    				//TODO fill in with option
//		        }
//    	break;
//    	case 3:if (currentUser instanceof Manager) {
//		            View.approvalMenu(currentUser);
//		            sc.nextLine();
//	        	}
//    			else {
//    				currentUser=currentUser.logout();	
//    			}
//    	break;
//        case 4:if (currentUser instanceof Manager) {
//			currentUser=currentUser.logout();
//		        }
//				else {
//					View.invalidChoice();
//				}
//    	break;
//    	case 5:if (currentUser instanceof Manager) {
//    				currentUser=currentUser.logout();
//		        }
//    			else {
//    				View.invalidChoice();
//    			}
//    	break;
//        default:
//        	View.invalidChoice();
//        }

        }
	
	
	


}

