package entities;

import controllers.AuthenticationController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import data.UserDatabase;


public abstract class User {
private String name;
protected String nric;
private int age;
private String maritalStatus;
private String password;
private boolean isLogin=false;
private AuthenticationController ac;
private Role role;

public User() {
	
}

public abstract void displayMenu(List<String> options);
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

public static List<Project> findByNameContainsSorted(String keyword) {
    return Project.loadAllProjects().stream()
        .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
        .sorted(Comparator.comparing(Project::getName))
        .toList();
}

public static List<Project> getAllProjectsSortedByName() {
    return Project.loadAllProjects().stream()
        .sorted(Comparator.comparing(Project::getName))
        .toList();
}



public Role getRole() {
	return this.role;
}

public void login()
{
//	ac.logIn(this, password);
	System.out.println("Login successful");
	this.isLogin=true;
}

public User logout() {
	System.out.printf("GoodBye %s !\n",this.name);
//	ac.logOut(this);
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

public User changePassword(String newPassword,UserDatabase db)
{
	db.updateUserPassword(this.getNric(), newPassword);
	System.out.println("Password is changed");
	System.out.println("Please login again!");
	return(this.logout());
}

public String getMaritalStatus(){
    return maritalStatus;
}

public void setMaritalStatus(String maritalStatus){
    this.maritalStatus=maritalStatus;
}
public List<String> getMenuOptions() {
    return Arrays.asList(
        "1. Projects",
        "2. Enquiries",
        "3. Change Password",
        "4. Logout"
    );
}
public abstract List<String> getProjectOptions();
public abstract List<String> getEnquiryOptions();
	
}
