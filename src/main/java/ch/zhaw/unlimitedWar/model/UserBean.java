package ch.zhaw.unlimitedWar.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

public class UserBean implements Serializable {
    private String name;
    private String mail;
    private String password;

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
        return password;
    }

    public void setPassword(String password) {
        this.password = getPasswordMD5Text(password);
    }

    private static String getPasswordMD5Text(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 23) {
                hashtext = "0" + hashtext;
            }
            return hashtext;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
