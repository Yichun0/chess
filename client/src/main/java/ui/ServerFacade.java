package ui;

import Response.LoginRespond;

public class ServerFacade {
    private final String serverUrl;
    public ServerFacade(String url){
        serverUrl = url;
    }
    public void register(){

    }
    public LoginRespond loginRespond(){
        // pass in the request --> write a body
        // call the server API
        //return object --> read body
        // pass in back to preLogin
        LoginRespond sucessLogin = new LoginRespond();
        return sucessLogin;
    }
}
