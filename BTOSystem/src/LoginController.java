public class LoginController {
    private User currentUser;
    public static boolean logIn(String nric,String password)
    {
        if(currentUser.getNric()==nric && currentUser.getPassword()==password){
            return true;
        }
        return false;
    }
    public void logOut()
    {
    }
}
