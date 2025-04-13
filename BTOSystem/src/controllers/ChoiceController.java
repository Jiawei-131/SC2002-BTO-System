package controllers;

import java.util.InputMismatchException;
import data.UserDatabase;

import java.util.Scanner;
import entities.Applicant;
import entities.Manager;
import entities.Officer;
import entities.User;
import util.ActionHandler;
import view.View;

public class ChoiceController {
	
	//ManagerActionHandler handler = new ManagerActionHandler();
	public User choice(int choice,User currentUser,Scanner sc,UserDatabase db) {
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
		currentUser=handler.handleAction(choice, currentUser, sc,db);
		return currentUser;

        }
}

