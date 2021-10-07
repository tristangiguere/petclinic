/**
 * Created by IntelliJ IDEA.
 *
 * User: @Trilikin21
 * Date: 24/09/21
 * Ticket: feat(AUTH-CPC-64)
 *
 * User: @JordanAlbayrak
 * Date: 24/09/21
 * Ticket: feat(AUTH-CPC-102)
 *
 * User: @Zellyk
 * Date: 26/09/21
 * Ticket: feat(AUTH-CPC-104)
 *
 */
package com.petclinic.auth.User;

import javassist.NotFoundException;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws NotFoundException;

    boolean verifyPassword(User user, UserIDLessUsernameLessDTO loginUser);

    User createUser(UserIDLessDTO user);

    User passwordReset(long id, String passwd)throws Exception;

}
