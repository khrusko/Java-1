
package cinema.model;
/**
 *
 * @author karlo_6zwakzv
 */
public class User
{

    private final String userName;
    private final String password;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
