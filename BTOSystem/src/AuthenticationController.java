public class AuthenticationController {
    private User currentUser;
    
    public AuthenticationController() {
    	
    }
    
    public boolean logIn(User currentUser,String password)
    {
        if(currentUser.getPassword().equals(password)){
        	currentUser.login();
        	this.currentUser = currentUser;
            return true;
        }
        return false;
    }
    public void logOut()
    {
    	currentUser.logout();
    }
}
