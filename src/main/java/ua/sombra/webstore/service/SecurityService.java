package ua.sombra.webstore.service;

public interface SecurityService {

	public String findLoggedInLogin();

    public void autoLogin(String login, String password);
}
