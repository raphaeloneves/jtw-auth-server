package br.com.raphaelneves.repositories;

import br.com.raphaelneves.models.Role;
import br.com.raphaelneves.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static List<User> users = new ArrayList();

    public UserRepository(){
        createUserMock();
    }

    private void createUserMock(){
        User usr = new User();
        usr.setId(1L);
        usr.setName("Administrator");
        usr.setPassword("admin");
        usr.setUsername("admin");
        Role r1 = new Role("ADMIN");
        usr.getRoles().add(r1);
        users.add(usr);

        User usr1 = new User();
        usr1.setId(2L);
        usr1.setName("Guest");
        usr1.setPassword("guest");
        usr1.setUsername("guest");
        Role r2 = new Role("GUEST");
        usr1.getRoles().add(r2);
        users.add(usr1);
    }

    public User add(User user){
        if (users.contains(user)){
            throw new RuntimeException("User with this credential already exists!");
        }
        users.add(user);
        return user;
    }

    public User find(Long id){
        User user = new User();
        user.setId(id);
        if(users.contains(user)){
            return users.get(users.indexOf(user));
        }
        return null;
    }

    public List<User> findAll(){
        return users;
    }

    public boolean remove(User user){
        if(users.contains(user)){
            users.remove(user);
            return true;
        }
        return false;
    }

    public User authUser(String username, String password){
        User user = null;
        for(User usr : users){
            if(password.equals(usr.getPassword()) && username.equals(usr.getUsername())){
                user = usr;
                break;
            }
        }
        return user;
    }
}
