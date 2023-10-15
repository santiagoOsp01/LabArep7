package edu.eci.co.m1webpage;

import static spark.Spark.*;
public class WebServer {

    private static SecureURLReader secureURLReader;
    public static void main( String[] args ) {
        secureURLReader = SecureURLReader.getInstance();
        port(getPort());
        secure(getKeyStore(), getKeyStorePWD(), null, null);
        get("/", (req, res) -> getIndex());
        get("/login", (req,res) -> conLogin(req.queryParams("v"),req.queryParams("t")));
    }
    public static String conLogin(String user, String password){
        try {
            if (secureURLReader.readURL(user,password).equals("success")){
                return getresponce();
            }
            return "la clave o usuario estan mal vuelva a intentar";
        } catch (Exception e) {
            return "ERROR CON EL GET";
        }
    }
    private static String getIndex(){
        return  """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Lab 7 Arep</title>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body>
                <h1>Loging Seguro</h1>
                <form action="/log">
                    <label for="name">usuario:</label><br>
                    <input type="text" id="name" name="name" value=""><br><br>
                    <label for="name1">contraseña:</label><br>
                    <input type="password" id="name1" name="name1" value=""><br><br>
                    <input type="button" value="submit" onclick="loadGetMsg()">
                </form>
                <div id="getrespmsg"></div>

                <script>
                    function loadGetMsg() {
                        let nameVar = document.getElementById("name").value;
                        let nameVar1 = document.getElementById("name1").value;
                        const xhttp = new XMLHttpRequest();
                        xhttp.onload = function() {
                            document.getElementById("getrespmsg").innerHTML =
                            this.responseText;
                        }
                        xhttp.open("GET", "/login?v="+nameVar+"&t="+nameVar1);
                        xhttp.send();
                    }
                </script>
                </body>
                </html>
                """;
    }
    private static String getresponce(){
        return  """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Lab 7 Arep</title>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body>
                <h1>Loging exitoso</h1>
                <p> bienvenido a la pagina</p>
                <p> calificar suave gracias</p>
                <p> :) </p>
                </body>
                </html>
                """;
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
        return 5000;
    }
}
