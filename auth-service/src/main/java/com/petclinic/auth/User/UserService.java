package com.petclinic.auth.User;

import javassist.NotFoundException;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws NotFoundException;

    boolean verifyPassword(User user, UserIDLessUsernameLessDTO loginUser);

    User createUser(UserIDLessDTO user);

    User passwordReset(long id, String passwd)throws Exception;

}
