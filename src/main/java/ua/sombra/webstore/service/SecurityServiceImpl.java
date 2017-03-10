package ua.sombra.webstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.domain.Role;

@Service
public class SecurityServiceImpl implements SecurityService {

	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	
	@Override
	public String findLoggedInLogin() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String login = user.getUsername();
		return login;
	}

	@Override
	public void autoLogin(String login, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(login);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
				password, userDetails.getAuthorities());

		authenticationManager.authenticate(authenticationToken);

		if (authenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			logger.debug(String.format("Successfully %s auto logged in", login));
		}
	}
		
	public boolean currUserIsAdmin(){
		String login = findLoggedInLogin();
		ua.sombra.webstore.domain.User u = userService.findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}
}
