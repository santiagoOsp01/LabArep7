package edu.eci.co.m2loginservices;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.lang.util.ByteSource;

public class Repository {
    private Map<String, String> hashedPasswords = new HashMap<>();
    private static Repository instance;
    static ByteSource saltSource;

    public Repository(){
        saltSource = new SecureRandomNumberGenerator().nextBytes();
        hashedPasswords.put("santiago",encrypt("1234"));
        hashedPasswords.put("jorge",encrypt("4321"));
    }

    public String encrypt(String psw){
        byte[] salt = saltSource.getBytes();
        Sha512Hash hash = new Sha512Hash(psw, saltSource, 1_000_000);
        return hash.toHex();
    }

    public String getPassword(String user){
        return hashedPasswords.get(user);
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }
}
