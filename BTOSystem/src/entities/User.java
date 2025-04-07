package entities;

import controllers.AuthenticationController;
import view.View;
import java.util.Scanner;


public abstract class User {
private String name;
private String nric;
private int age;
private String maritalStatus;
private String password;
private boolean isLogin=false;
private AuthenticationController ac;
private Role role;

public User() {
	
}

public abstract void displayMenu();
//public abstract User handleChoice(int choice,View view,Scanner sc);


public User(String name, String nric,int age, String maritalStatus,String password,AuthenticationController ac,Role role){
    this.name=name;
    this.nric=nric;
    this.age=age;
    this.maritalStatus=maritalStatus;
    this.password=password;
    this.ac=ac;
    this.role=role;
    }

public Role getRole() {
	return this.role;
}

public void login()
{
	ac.logIn(this, password);
	System.out.printf("Welcome %s! \n",this.name);
	this.isLogin=true;
}

public User logout() {
	System.out.printf("GoodBye %s !\n",this.name);
	ac.logOut(this);
	this.isLogin=false;
	return null;
}

public boolean isLogin()
{
	return isLogin;
}

public int getAge()
{
    return age;
}

public String getNric()
{
    return nric;
}

public String getUsername()
{
    return name;
}

public void setPassword(String password){
    this.password=password;
}

public String getPassword()
{
    return password;
}

public boolean changePassword(String newPassword)
{
    if(isLogin){
        this.password=newPassword;
        return true;
    }
    return false;
}

public String getMaritalStatus(){
    return maritalStatus;
}

public void setMaritalStatus(String maritalStatus){
    this.maritalStatus=maritalStatus;
}


	
}
