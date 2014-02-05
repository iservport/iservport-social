package org.springframework.social.iservport.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

/**
 * Remote user details.
 * 
 * Required either by Spring Security as well as by Spring Social.
 * 
 * @author mauriciofernandesdecastro
 */
public class RemoteUserDetails extends SocialUser {

	private static final long serialVersionUID = 1L;

	private Integer id;

    private String firstName;

    private String lastName;
    
    private String roles;
    
    private ProviderType providerType;

    /**
     * Required security constructor.
     * 
     * @param username
     * @param password
     * @param authorities
     */
    public RemoteUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Integer getId() {
		return id;
	}
    public void setId(Integer id) {
		this.id = id;
	}
    
    public String getFirstName() {
		return firstName;
	}
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
    
    public String getLastName() {
		return lastName;
	}
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
    public String getRoles() {
		return roles;
	}
    public void setRoles(String roles) {
		this.roles = roles;
	}
    
    public ProviderType getProviderType() {
		return providerType;
	}
    public void setProviderType(ProviderType providerType) {
		this.providerType = providerType;
	}
    
    public static Builder getBuilder() {
    	return new Builder();
    }
    
    /**
     * Remote user details builder.
     * 
     * @author mauriciofernandesdecastro
     */
    public static class Builder {

        private Integer id;

        private String username;

        private String firstName;

        private String lastName;

        private String password;
        
        private String roles;

        private Set<GrantedAuthority> authorities;
        
        private ProviderType providerType;

        public Builder() {
            this.authorities = new HashSet<>();
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder password(String password) {
            if (password == null) {
                password = "SocialUser";
            }

            this.password = password;
            return this;
        }

        public Builder roles(String roles) {
            this.roles = roles;
            
            for (String role: roles.replace(" ", "").split(",")) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                this.authorities.add(authority);            	
            }

            return this;
        }

        public Builder providerType(ProviderType providerType) {
            this.providerType = providerType;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public RemoteUserDetails build() {
        	RemoteUserDetails user = new RemoteUserDetails(username, password, authorities);

            user.id = id;
            user.firstName = firstName;
            user.lastName = lastName;
            user.roles = roles;
            user.providerType = providerType;

            return user;
        }
    }
}
