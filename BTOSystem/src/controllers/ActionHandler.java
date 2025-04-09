package controllers;
import java.util.Scanner;
import data.UserDatabase;
import entities.User;

public interface ActionHandler {
	User handleAction(int choice,User user, Scanner sc,UserDatabase db);
	void handleEnquiryAction(int choice,User currentUser, Scanner sc);
	void handleProjectAction(int choice,User currentUser, Scanner sc);
}
