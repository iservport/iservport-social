package org.springframework.social.iservport.api;

import java.io.Serializable;

/**
 * 
 * @author mauriciofernandesdecastro
 */
public class RemoteUser 
	implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	
	public RemoteUser() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
