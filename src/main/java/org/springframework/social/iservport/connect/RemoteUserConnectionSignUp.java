package org.springframework.social.iservport.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.iservport.api.ProviderType;
import org.springframework.social.iservport.api.impl.RemoteUser;
import org.springframework.social.iservport.repository.RemoteUserRepository;
import org.springframework.social.iservport.repository.UserKeyAlreadyOnFileException;

/**
 * Remote user connection sign-up
 * 
 * @author mauriciofernandesdecastro
 */
public class RemoteUserConnectionSignUp implements ConnectionSignUp {

    private final RemoteUserRepository remoteUserRepository;

    public RemoteUserConnectionSignUp(RemoteUserRepository remoteUserRepository) {
        this.remoteUserRepository = remoteUserRepository;
    }

    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();
        RemoteUser remoteUser = new RemoteUser(profile);
        try {
			remoteUserRepository.createRemoteUser(profile, "", "", "", "", "", ProviderType.iservport);
		} catch (UserKeyAlreadyOnFileException e) {
			e.printStackTrace();
		}
        return remoteUser.getUserKey();
    }
	
}