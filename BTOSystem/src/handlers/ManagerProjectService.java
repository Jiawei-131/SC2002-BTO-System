package handlers;

import java.util.Scanner;

import controllers.DateTimeController;
import entities.Manager;
import entities.Project;
import entities.User;
import util.GetInput;
import view.View;

public class ManagerProjectService {

		public static void viewProject(Manager manager,int choice)
		{
			manager.viewProjects(choice);
		}
	    
	    public static void createProject(Manager manager,Scanner sc) {
	 		String btoName=GetInput.getLineInput(sc, "the BTO Name");
	 		String neighbourhood=GetInput.getLineInput(sc, "the neighbourhood");
	 		int unitType1=GetInput.getIntInput(sc, "the Number of 2 Room Units");
	 		int unitType1Price=GetInput.getIntInput(sc, "the Price of 2 Room Units");
	 		int unitType2=GetInput.getIntInput(sc, "the Number of 3 Room Units");
	 		int unitType2Price=GetInput.getIntInput(sc, "the Price of 3 Room Units");
	 		String openDate=GetInput.inputLoop("the Opening Date in DD-MM-YYYY format", sc, s->s, s-> DateTimeController.isValidFormat(s)&&DateTimeController.isAfter(s));
	 		String closeDate=GetInput.inputLoop("the Closing Date in DD-MM-YYYY format", sc, s->s,
	 				s->DateTimeController.isValidFormat(s) &&DateTimeController.isAfter(s, openDate)&&DateTimeController.isAfter(s));
	 		int availableSlots = GetInput.inputLoop("the Number of HDB Officer slots", sc, Integer::parseInt, i->i<=10 && i>0);
//	 	    int isVisibleChoice = GetInput.inputLoop("""
//	 	            the visibility to applicants
//	 	            1. Yes
//	 	            2. No
//	 	            """, sc, Integer::parseInt, i -> i == 1 || i == 2);
//	 	   boolean isVisible = (isVisibleChoice == 1) ? true : false;
	 		manager.createProject(btoName, neighbourhood, unitType1,unitType2, openDate, closeDate,
	 				 availableSlots,unitType1Price,unitType2Price);
	    }
	    
	    public static void updateProject(Manager manager,Scanner sc) {
	 		String btoName=GetInput.getLineInput(sc, "the BTO Name");
	 		if(Project.findByName(btoName).equals(null)||!manager.getNric().equals(Project.findByName(btoName).getManager()))
			{
	 			System.out.println("BTO Project does not exist or you do not have access.");
			}
	 		else {
	 			int choice;
	 			Project project=Project.findByName(btoName);
	 			do {
	 				manager.displayMenu(manager.getBtoOptions());
	 				choice=GetInput.getIntInput(sc,"the field to update");
	 				switch(choice)
	 				{
	 				case 1:String neighbourhood=GetInput.getLineInput(sc, "the new neighbourhood");
	 						project.setNeighbourhood(neighbourhood);
	 					break;
	 				case 2:int unitType1=GetInput.getIntInput(sc, "the new Number of 2 Room Units");
	 						project.setNumberOfType1Units(unitType1);
	 				break;
	 				case 3:int unitType1Price=GetInput.getIntInput(sc, "the new Price of 2 Room Units");
	 						project.setType1SellingPrice(unitType1Price);
	 				break;
	 				case 4:int unitType2=GetInput.getIntInput(sc, "the new Number of 3 Room Units");
	 					project.setNumberOfType2Units(unitType2);
	 				break;
	 				case 5:int unitType2Price=GetInput.getIntInput(sc, "the new Price of 3 Room Units");
	 						project.setType2SellingPrice(unitType2Price);
	 				break;
	 				case 6:String openDate=GetInput.inputLoop("the new Opening Date in DD-MM-YYYY format", sc, s->s, s-> DateTimeController.isValidFormat(s)&&DateTimeController.isAfter(s));
	 						project.setOpeningDate(openDate);
	 				break;
	 				case 7:String closeDate=GetInput.inputLoop("the new Closing Date in DD-MM-YYYY format", sc, s->s,
	 	 	 				s->DateTimeController.isValidFormat(s) &&DateTimeController.isAfter(s,project.getOpeningDate())&&DateTimeController.isAfter(s));
	 						project.setClosingDate(closeDate);
	 				break;
	 				case 8:int availableSlots = GetInput.inputLoop("the new Number of HDB Officer slots", sc, Integer::parseInt, i->i<=10 && i>0);
	 						project.setOfficerSlot(availableSlots);
	 				case 9: int isVisibleChoice = GetInput.inputLoop("""
	 	 	            the visibility to applicants
	 	 	            1. Yes
	 	 	            2. No
	 	 	            """, sc, Integer::parseInt, i -> i == 1 || i == 2);
	 	 	   boolean isVisible = (isVisibleChoice == 1) ? true : false;
	 	 	   			project.setVisibleToApplicant(isVisible);
	 				break;
	 				default:
	 				}
	 			}while (choice != 9);
	 
	 		}

	    }
	    
	    public static void deleteProject(Manager manager,Scanner sc)
	    {
	    	String btoName=GetInput.getLineInput(sc, "the BTO Name");
	 		if(Project.findByName(btoName).equals(null)||!manager.getNric().equals(Project.findByName(btoName).getManager()))
			{
	 			System.out.println("BTO Project does not exist or you do not have access.");
			}
	 		else {
	 			manager.deleteProject(btoName,Project.findByName(btoName));
	 		}
	    }
	    
}
