package org.springframework.social.iservport.api.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.social.iservport.api.ProviderType;

/**
 * 
 * @author mauriciofernandesdecastro
 */
public class RemoteUserTests {

	@Test
	public void test() {
		RemoteUser remoteUser = new RemoteUser();
		remoteUser.setProviderType(ProviderType.iservport);
		assertEquals("iservport", remoteUser.getProviderTypeAsString());
		assertEquals(ProviderType.iservport, remoteUser.getProviderType());
	}

}
