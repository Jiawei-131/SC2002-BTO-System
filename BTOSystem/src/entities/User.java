package entities;

public abstract class User {
private String name;
private String nric;
private int age;
private String maritalStatus;
private String password;
private boolean isLogin=false;

public User() {
	
}

public User(String name, String nric,int age, String maritalStatus,String password){
    this.name=name;
    this.nric=nric;
    this.age=age;
    this.maritalStatus=maritalStatus;
    this.password=password;
}
public void login()
{
	System.out.printf("Welcome %s ?\n",this.name);
	this.isLogin=true;
}
public void logout() {
	System.out.printf("GoodBye %s ?\n",this.name);
	this.isLogin=false;
}
public void displayMenu()
{
	System.out.println("bye");
}
public String getNric()
{
    return nric;
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
