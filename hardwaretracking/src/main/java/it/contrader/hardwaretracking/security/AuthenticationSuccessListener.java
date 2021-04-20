package it.contrader.hardwaretracking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import it.contrader.hardwaretracking.entity.UserPrincipal;
import it.contrader.hardwaretracking.service.LoginAttemptService;


@Component
public class AuthenticationSuccessListener {
	
	private LoginAttemptService loginAttemptService;

	@Autowired
	public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
		this.loginAttemptService = loginAttemptService;
	}
	
	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		
		if(principal instanceof UserPrincipal) {
			UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
			
			//letteralmente evict = "sfrattare", quindi in caso di successo rimuove
			//l'user dalla cache dei tentativi
		}
	}
	
	

}