package edu.eci.co;

import static spark.Spark.*;
public class SecureSpark {


    public static void main( String[] args ) {
        port(getPort());
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        secure("ecikeystore.p12", "123456", null, null);
        get("hello", (req,res) -> "Hello World!");
    }
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
