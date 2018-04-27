package com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.User;

import java.util.List;

/**
 * Created by Ramon on 27/04/2018.
 */

public interface UsersRepository {

    void addUser(User u);
    void removeUser(User u);
    void updateUser(User u);
    boolean existsUser(String username);
    boolean logInSuccessful(String username, String password);
    boolean logInSuccessfulByEmail(String email, String password);
    User getUser(String username);
    List<User> getAllUsers();

}
