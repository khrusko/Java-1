/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.model;

/**
 *
 * @author karlo_6zwakzv
 */
public class Admin
{
    private final  String userName;
    private final  String password;

    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }


    public String getPassword() {
        return password;
    }



    @Override
    public String toString() {
        return "Admin{" + "Username=" + userName + ", Password=" + password + '}';
    }

}
