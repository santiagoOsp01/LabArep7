package edu.eci.co.m2loginservices;

import static spark.Spark.*;
public class loginServices {

    private static Repository repository;

    public static void main( String[] args ) {
        repository = Repository.getInstance();
        port(getPort());
        secure(getKeyStore(), getKeyStorePWD(), null, null);
        get("login", (req,res) -> confirmPassword(req.queryParams("v"),req.queryParams("t")));
    }
    public static String confirmPassword(String user, String password){
        if (repository.getPassword(user).equals(repository.encrypt(password))){
            return "success";
        }
        return "denied";
    }
    public static String getKeyStore() {
        if (System.getenv("KEYSTORE") != null) {
            return System.getenv("KEYSTORE");
        }
        return "keystores/ecikeystore.p12";
    }
    public static String getKeyStorePWD() {
        if (System.getenv("KEYSTOREPWD") != null) {
            return System.getenv("KEYSTOREPWD");
        }
        return "123456";
    }
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
    }
}
