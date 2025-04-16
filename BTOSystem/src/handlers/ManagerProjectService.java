package handlers;

import java.util.List;
import java.util.Scanner;

import controllers.DateTimeController;
import data.ProjectDatabase;
import entities.Manager;
import entities.Project;
import util.GetInput;
import view.ProjectView;
import view.View;

public class ManagerProjectService {
	   public static void editProject(Manager manager,Scanner sc)
	   {
			String btoName=GetInput.getLineInput(sc, "the BTO Name");
	        Project project = ProjectDatabase.findByName(btoName);
	        
	        if (project == null || !manager.getNric().equals(project.getManager())) {
				System.out.println("BTO Project does not exist or you do not have access.");
			}
			else {
				int choice;
				do {
					manager.displayMenu(manager.getBtoOptions());
					choice=GetInput.getIntInput(sc,"the field to update");
					switch(choice)
					{
					case 1 -> project.setNeighbourhood(GetInput.getLineInput(sc, "the new neighbourhood"));
		            case 2 -> project.setNumberOfType1Units(GetInput.inputLoop("the new Number of 2 Room Units", sc, Integer::parseInt, i -> i > 0));
		            case 3 -> project.setType1SellingPrice(GetInput.inputLoop("the new Price of 2 Room Units", sc, Double::parseDouble, i -> i > 0));
		            case 4 -> project.setNumberOfType2Units(GetInput.inputLoop("the new Number of 3 Room Units", sc, Integer::parseInt, i -> i > 0));
		            case 5 -> project.setType2SellingPrice(GetInput.inputLoop("the new Price of 3 Room Units", sc, Double::parseDouble, i -> i > 0));
		            case 6 -> project.setOpeningDate(GetInput.inputLoop("the new Opening Date in DD-MM-YYYY format", sc, s -> s,
		            		s -> DateTimeController.isValidFormat(s) && DateTimeController.isAfter(s)));
		            case 7 -> project.setClosingDate(GetInput.inputLoop("the new Closing Date in DD-MM-YYYY format", sc, s -> s,
		            		s -> DateTimeController.isValidFormat(s) && DateTimeController.isAfter(s, project.getOpeningDate())));
		            case 8 -> project.setOfficerSlot(GetInput.inputLoop("the new Number of HDB Officer slots", sc, Integer::parseInt, i -> i <= 10 && i > 0));
		            case 9 -> {
		                int isVisibleChoice = GetInput.inputLoop("""
		                        the visibility to applicants
		                        1. Yes
		                        2. No
		                        """, sc, Integer::parseInt, i -> i == 1 || i == 2);
		                project.setVisibleToApplicant(isVisibleChoice == 1);
		            }
					default->{}
					}
				}while (choice != 9);

			}
	   }
	   
	   public static void showProject(Manager manager,int type)
	   {
		   List<Project> projects = ProjectDatabase.loadAllProjects();
			for(Project project:projects)
			{
				if(type==1)//Print all project
				{
					View.displayProjectDetails(project);
				}
				else {//Print only projects that the manager created
					if(manager.getNric().equals(project.getManager()))
					{
						View.displayProjectDetails(project);
					}
				}
			}
	   }
	   
	   public static void deleteProject(Manager manager,Scanner sc)
	   {
	   	String btoName=GetInput.getLineInput(sc, "the BTO Name");
	   	Project project = ProjectDatabase.findByName(btoName);
			if(project == null || !manager.getNric().equals(project.getManager()))
			{
				System.out.println("BTO Project does not exist or you do not have access.");
			}
			else {
				ProjectDatabase.delete(btoName);
			}
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
			Project project = new Project(btoName, neighbourhood,unitType1,unitType1Price, unitType2,unitType2Price,
	    			openDate, closeDate,manager.getNric(),availableSlots);
			ProjectDatabase.save(project);
	    	
	   }
}
