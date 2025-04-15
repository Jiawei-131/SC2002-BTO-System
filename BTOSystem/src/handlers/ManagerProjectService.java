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
	 		int unitType1=GetInput.inputLoop("the Number of 2 Room Units", sc, Integer::parseInt, i->i>0);
	 		int unitType1Price=GetInput.inputLoop("the Price of 2 Room Units", sc, Integer::parseInt, i->i>0);
	 		int unitType2=GetInput.inputLoop("the Number of 3 Room Units", sc, Integer::parseInt, i->i>0);
	 		int unitType2Price=GetInput.inputLoop("the Price of 3 Room Units", sc, Integer::parseInt, i->i>0);
	 		String openDate=GetInput.inputLoop("the Opening Date in DD-MM-YYYY format", sc, s->s, s-> DateTimeController.isValidFormat(s)&&DateTimeController.isAfter(s));
	 		String closeDate=GetInput.inputLoop("the Closing Date in DD-MM-YYYY format", sc, s->s,
	 				s->DateTimeController.isValidFormat(s) &&DateTimeController.isAfter(s, openDate)&&DateTimeController.isAfter(s));
	 		int availableSlots = GetInput.inputLoop("the Number of HDB Officer slots", sc, Integer::parseInt, i->i<=10 && i>0);
	 		manager.createProject(btoName, neighbourhood, unitType1,unitType2, openDate, closeDate,
	 				 availableSlots,unitType1Price,unitType2Price);
	    }
	    
	    public static void updateProject(Manager manager,Scanner sc) {
	    	manager.editProject(sc);
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
