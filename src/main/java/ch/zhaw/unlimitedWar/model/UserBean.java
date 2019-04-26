package ch.zhaw.unlimitedWar.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

public class UserBean implements Serializable {
    private String name;
    private String mail;
    private String md5Password;

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

    public void setPassword(String password) {
        this.md5Password = getPasswordMD5Text(password);
    }

    public String getPasswordMD5Text(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            md5Password = number.toString(16);
            while (md5Password.length() < 23) {
                md5Password = "0" + md5Password;
            }
            return md5Password;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
