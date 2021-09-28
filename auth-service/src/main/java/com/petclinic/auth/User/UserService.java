package com.petclinic.auth.User;

import javassist.NotFoundException;

public interface UserService {


    User createUser(UserIDLessDTO user);

    User passwordReset(long id, String passwd)throws NotFoundException;

    User emailReset(long id, String email) throws NotFoundException;
}
