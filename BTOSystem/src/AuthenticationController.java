public class AuthenticationController {
    private static User currentUser;
    
    public AuthenticationController() {
    	
    }
    public boolean isLoggedIn() {
    	if(this.currentUser!=null)
    	{
    		return true;
    	}
    	else {
    		return false;
    	}
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
    	currentUser=null;
    }
}
