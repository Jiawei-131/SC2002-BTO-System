package entities;

import controllers.AuthenticationController;
import view.View;


public abstract class User {
private String name;
private String nric;
private int age;
private String maritalStatus;
private String password;
private boolean isLogin=false;
private AuthenticationController ac;

public User() {
	
}

public User(String name, String nric,int age, String maritalStatus,String password,AuthenticationController ac){
    this.name=name;
    this.nric=nric;
    this.age=age;
    this.maritalStatus=maritalStatus;
    this.password=password;
    this.ac=ac;
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

public void displayMenu(View view)
{
	
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
