public class AuthenticationController {
    private User currentUser;
    
    public AuthenticationController() {
    	
    }
    
    public boolean logIn(User user,String password)
    {
        if(user.getPassword().equals(password)){
        	user.login();
        	this.currentUser = user;
            return true;
        }
        return false;
    }
    public void logOut()
    {
    	currentUser.logout();
    }
}
