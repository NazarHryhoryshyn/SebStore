package ua.sombra.webstore.service.databaseService.interfaces;

public interface SecurityService {

	public String findLoggedInLogin();

    public void autoLogin(String login, String password);
}
