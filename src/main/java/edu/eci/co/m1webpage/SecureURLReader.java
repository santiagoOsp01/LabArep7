package edu.eci.co.m1webpage;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecureURLReader {

    private static SecureURLReader instance;
    public static SecureURLReader getInstance() {
        if (instance == null) {
            instance = new SecureURLReader();
        }
        return instance;
    }
    public String readURL(String user, String password) throws Exception {
        try {
            // Create a file and a password representation
            File trustStoreFile = new File(getTrustKeyStore());
            char[] trustStorePassword = getTrustPwdStore().toCharArray();
            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);
            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Init the TrustManagerFactory using the truststore object
            tmf.init(trustStore);
            //Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "https://ec2-52-23-161-131.compute-1.amazonaws.com:5001/login?v="+user+"&t="+password;
        //String url = "https://localhost:5001/login?v="+user+"&t="+password;
        return getContent(url);
    }
    public String getContent(String url) throws IOException {
        String site = url;
        // Crea el objeto que representa una URL
        URL siteURL = new URL(site);
        // Crea el objeto que URLConnection
        URLConnection urlConnection = siteURL.openConnection();
        // Obtiene los campos del encabezado y los almacena en un estructura Map
        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        // Obtiene una vista del mapa como conjunto de pares <K,V>
        // para poder navegarlo
        Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
        // Recorre la lista de campos e imprime los valores
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String headerName = entry.getKey();
            //Si el nombre es nulo, significa que es la linea de estado
            if (headerName != null) {
                System.out.print(headerName + ":");
            }
            List<String> headerValues = entry.getValue();
            for (String value : headerValues) {
                System.out.print(value + "\n");
            }
        }

        System.out.println("-------message-body------");
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();
        try (reader) {
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                response.append(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return response.toString();

    }
    public static String getTrustKeyStore() {
        if (System.getenv("TRUSTKEYSTORE") != null) {
            return System.getenv("TRUSTKEYSTORE");
        }
        return "keystores/myTrustStore";
    }
    public static String getTrustPwdStore() {
        if (System.getenv("PWDSTORE") != null) {
            return System.getenv("PWDSTORE");
        }
        return "123456";
    }

}
