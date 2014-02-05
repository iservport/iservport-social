/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.iservport.user;



/**
 * Repositório para RemoteUser.
 * 
 * @author mauriciofernandesdecastro
 */
public interface RemoteUserRepository {

	/**
	 * Create a new RemoteUser and add it to this repository.
	 * 
	 * @param userKey
	 * @param firstName
	 * @param lastName
	 * @param displayName
	 * @param profileUrl
	 * @param imageUrl
	 * @param password
	 * 
	 * 
	 * 
	 * @throws UserKeyAlreadyOnFileException 
	 */
	RemoteUser createRemoteUser(String userKey, String firstName, String lastName, String displayName, String profileUrl, String imageUrl, String password) throws UserKeyAlreadyOnFileException;

	/**
	 * Find an RemoteUser by its internal identifier.
	 * 
	 * @param id
	 */
	RemoteUser findById(int id);

	/**
	 * Find a RemoteUser by the user key. 
	 * 
	 * @throws UserKeyNotFoundException a RemoteUser could not be found with the provided signin name
	 */
	RemoteUser findByUserKey(String userKey) throws UserKeyNotFoundException;

	/**
	 * Authenticate a remote user via sign-in and password.
	 * 
	 * @param userKey
	 * @param password
	 * 
	 * @throws UserKeyNotFoundException
	 * @throws InvalidPasswordException
	 */
	RemoteUser authenticate(String userKey, String password) throws UserKeyNotFoundException, InvalidPasswordException;
}