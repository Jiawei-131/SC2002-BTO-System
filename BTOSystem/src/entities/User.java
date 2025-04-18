package entities;

import controllers.AuthenticationController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import data.ProjectDatabase;
import data.UserDatabase;
import util.Role;
import util.Filter;


public abstract class User implements Filter {
private String filterDescription = "Alphabetical";
private Comparator<Project> comparator = Comparator.comparing(Project::getName);
private String name;
protected String nric;
protected int age;
protected String maritalStatus;
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

public List<Project> sort() {
    return ProjectDatabase.loadAllProjects().stream()
        .sorted(comparator)
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

public String getFilterDescription() {
    return filterDescription;
}

@Override
public void setFilterDescription(String desc) {
    this.filterDescription = desc;
}

@Override
public Comparator<Project> getComparator() {
    return comparator;
}

@Override
public void setComparator(Comparator<Project> comp) {
    this.comparator = comp;
}


public List<String> getMenuOptions() {
    return Arrays.asList(
        "1. Projects",
        "2. Enquiries",
        "3. Change Password",
        "4. Filter Settings",
        "5. Logout"
    );
}
public List<String> getSortOptions() {
    return Arrays.asList(
			"1. Neighbourhood",
			"2. 2-Room Types",
			"3. 3-Room Types",
			"4. Opening Date",
			"5. Closing Date"
    );
}
public abstract List<String> getProjectOptions();
public abstract List<String> getEnquiryOptions();
	
}
