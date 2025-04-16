package util;
import java.util.Scanner;
import data.UserDatabase;
import entities.User;

public interface ActionHandler {
	default void handleFilterAction(User currentUser,Scanner sc)
	{
		currentUser.displayMenu(currentUser.getSortOptions());
		currentUser.setFilter(GetInput.inputLoop("your choice", sc, Integer::parseInt, i->i>0&&i<6));
	}
	User handleAction(int choice,User user, Scanner sc,UserDatabase db);
	void handleEnquiryAction(int choice,User currentUser, Scanner sc);
	void handleProjectAction(int choice,User currentUser, Scanner sc);
}
