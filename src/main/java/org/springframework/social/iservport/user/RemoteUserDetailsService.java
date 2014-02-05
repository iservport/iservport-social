package org.springframework.social.iservport.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Remote user implementation for the required UserDetails interface.
 * 
 * @author mauriciofernandesdecastro
 */
public class RemoteUserDetailsService 
	implements UserDetailsService
{

	private RemoteUserRepository remoteUserRepository;
	
	/**
	 * Constructor.
	 * 
	 * @param remoteUserRepository
	 */
	@Autowired
	public RemoteUserDetailsService(RemoteUserRepository remoteUserRepository) {
		this.remoteUserRepository = remoteUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		RemoteUser user = null;
		try {
			user = remoteUserRepository.findByUserKey(username);
		}
		catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        RemoteUserDetails principal = RemoteUserDetails.getBuilder()
                .id(user.getId())
                .username(user.getUserKey())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .roles(user.getRoles())
                .providerType(user.getProviderType())
                .build();

        return principal;
    
	}

}
