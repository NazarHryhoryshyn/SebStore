package ua.sombra.webstore.service;

public interface SecurityService {

    String findLoggedInLogin();

    void autoLogin(String login, String password);
    
    public boolean currUserIsAdmin();
}
