package com.stormpath.sdk.examples.standalone;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TokenAuthTest {
    public static void main(String[] args) throws Exception {

        String command = System.getProperty("command");

        if (command == null || !("getToken".equals(command) || "getPage".equals(command))) {
            System.err.println("Must supply a command:");
            System.err.println("\t-Dcommand=getToken OR");
            System.err.println("\t-Dcommand=getPage OR");
            System.exit(1);
        }

        if ("getToken".equals(command)) {
            getToken();
        } else {
            getPage();
        }
    }

    private static final String APP_URL = "http://localhost:8080";
    private static final String OAUTH_URI = "/oauth/token";
    private static final String PROTECTED_URI = "/dashboard";

    private static void getToken() throws Exception {
        String username = System.getProperty("username");
        String password = System.getProperty("password");

        if (username == null || password == null) {
            System.err.println("Must supply -Dusername=<username> -Dpassword=<password> on the command line");
            System.exit(1);
        }

        PostMethod method = new PostMethod(APP_URL + OAUTH_URI);

        method.setRequestHeader("Origin", APP_URL);
        method.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        method.addParameter("grant_type", "password");
        method.addParameter("username", username);
        method.addParameter("password", password);

        HttpClient client = new HttpClient();
        client.executeMethod(method);

        BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
        String readLine;
        while(((readLine = br.readLine()) != null)) {
            System.out.println(readLine);
        }
    }

    private static void getPage() throws Exception {
        String token = System.getProperty("token");

        if (token == null) {
            System.err.println("Must supply -Dtoken=<access token> on the command line");
            System.exit(1);
        }

        GetMethod method = new GetMethod(APP_URL + PROTECTED_URI);
        HttpClient client = new HttpClient();

        System.out.println("Attempting to retrieve " + PROTECTED_URI + " without token...");

        int returnCode = client.executeMethod(method);
        System.out.println("return code: " + returnCode);

        System.out.println();

        System.out.println("Attempting to retrieve " + PROTECTED_URI + " with token...");

        method.addRequestHeader("Authorization", "Bearer " + token);
        returnCode = client.executeMethod(method);
        System.out.println("return code: " + returnCode);
    }
}
