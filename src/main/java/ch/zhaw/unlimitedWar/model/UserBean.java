package ch.zhaw.unlimitedWar.model;

import java.io.Serializable;

import static ch.zhaw.unlimitedWar.controller.UserController.getPasswordMD5Text;

public class UserBean implements Serializable {


    //region data fields
    private String name;
    private String mail;
    private String md5Password;
    //endregion

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return md5Password;
    }

    public void setPassword(String md5Password) {
       this.md5Password = md5Password;
    }

    public void setPasswordFirstTime(String password){
        this.md5Password = getPasswordMD5Text(password);
    }

}
