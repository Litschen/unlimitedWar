package ch.zhaw.unlimitedWar.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserBean implements Serializable {

    //region static variables
    public static final String HASH_INSTANCE = "MD5";
    public static final int INTEGER_SIGNUM_POSITIVE_VALUE = 1;
    public static final int MD5_HASH_NUMBER = 16;
    private final static Logger LOGGER = Logger.getLogger(ResultSet.class.getName());
    public static final String DATABASE_ERROR = "DATABASE ERROR: Could not establish connection";
    //endregion

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

    public String getPasswordMD5Text(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_INSTANCE);
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(INTEGER_SIGNUM_POSITIVE_VALUE, messageDigest);
            md5Password = number.toString(MD5_HASH_NUMBER);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
        }
        return md5Password;

}
}
