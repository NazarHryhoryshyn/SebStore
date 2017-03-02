package ua.sombra.webstore.service;

public interface SecurityService {

    String findLoggedInEmail();

    void autoLogin(String email, String password);
    
    public boolean currUserIsAdmin();
}
