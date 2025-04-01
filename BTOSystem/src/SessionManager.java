
public class SessionManager {
	private User currentUser;
	public void setCurrentUser(User user)
	{
		this.currentUser=user;
	}
	public boolean clearSession()
	{
		if(currentUser != null) {
				currentUser.logout();
				currentUser=null;
				return true;
		}
		return false;
	}
}
