package handlers;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import controllers.AuthenticationController;
import controllers.DateTimeController;
import controllers.ProjectController;
import data.ProjectApplicationDatabase;
import data.ProjectDatabase;
import entities.Manager;
import entities.Project;
import entities.ProjectApplication;
import util.GetInput;
import view.View;

public class ManagerProjectService {
	   public static void editProject(Manager manager,Scanner sc)
	   {
		   	ProjectController pc= new ProjectController();
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
					case 1 -> project.setNeighbourhood(GetInput.inputLoop("the new neighbourhood", sc, s->s, s->pc.getProject(s)==null));
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
	   
	   public static Project getActiveProject(Manager manager)
	   {
		   List<Project> projects = ProjectDatabase.loadAllProjects();
		   Project temp=null;
		   for(Project project:projects)
		   {
				if(manager.getNric().equals(project.getManager()))
				{
					temp=project;
					break;
				}
		   }
		   return temp;
	   }
	   
	   
	   public static void showProject(Manager manager,int type)
	   {
		   List<Project> projects = ProjectDatabase.loadAllProjects();
			for(Project project:projects)
			{
				if(type==1)//Print all project
				{
					View.displayProjectDetails(manager, project);
				}
				else {//Print only projects that the manager created
					if(manager.getNric().equals(project.getManager()))
					{
						View.displayProjectDetails(manager, project);
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
		   ProjectController pc= new ProjectController();
			String btoName=GetInput.inputLoop("the BTO Name", sc, s->s, s->pc.getProject(s)==null);
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
	   
	   public static void generateReport(Manager manager,Scanner sc)
	   {
		   List<ProjectApplication> projectApplications;
		   Comparator<ProjectApplication> comparator=Comparator.comparing(ProjectApplication::getProjectName);
	    	ProjectApplicationDatabase ProjectApplicationdb=new ProjectApplicationDatabase();
	    	projectApplications=ProjectApplicationdb.readApplication();
	    	manager.displayMenu(manager.getReportFilterOptions());
	    	int choice = GetInput.inputLoop("your Choice", sc, Integer::parseInt, i-> i<=4&&i>0);
	    	switch(choice)
	    	{
	    		case 1->comparator=Comparator.comparing(ProjectApplication::getProjectName);
	    		case 2->comparator=Comparator.comparing(ProjectApplication::getFlatType);
	    		case 3->comparator=Comparator.comparingInt(ProjectApplication::getAge);
	    		case 4->comparator=Comparator.comparing(ProjectApplication::getMaritalStatus);
	    	}
	    	
	    	projectApplications=projectApplications.stream()
			            .sorted(comparator)
			            .toList();
	    	for(ProjectApplication projectApplication:projectApplications)
	    	{ 
	        	System.out.printf("""
	        			Application Details
	        			Project Name: %s
	        			Applicant NRIC: %s
	        			Applicant Age: %s
	        			Applicant MaritalStatus
	        			Flat Type: %s
	        			Status: %s
	        			
	        			""", projectApplication.getProjectName(),projectApplication.getApplicantId(),projectApplication.getAge(),projectApplication.getMaritalStatus(), projectApplication.getFlatType(), projectApplication.getApplicationStatus());
	    	}
	    	
	   }
}
