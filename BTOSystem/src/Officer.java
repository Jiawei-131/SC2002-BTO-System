// package BTOSystem.src;

public class Officer extends User {
private Project assignedProject;
private boolean registrationStatus;
private boolean carRegister;
public Officer(String name, String nric,String password)
{
    super(password);
    super(nric);
    super(name);
}

}
