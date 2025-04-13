package util;

import controllers.AuthenticationController;
import entities.User;
import java.util.Scanner;
import data.UserDatabase;

public interface PasswordReset {
	static public User resetPassword (Scanner sc,User currentUser,UserDatabase db)
	{
		String password;
        do {
        	password=GetInput.getLineInput(sc, "your old Password");
        }while(!AuthenticationController.passwordCheck(password,currentUser));
        do {
        	GetInput.getLineInput(sc, "your new Password");
        }while(!AuthenticationController.isValidPassword(password,currentUser));
        return(AuthenticationController.resetPassword(currentUser, db, password));
	}
}
