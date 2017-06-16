package br.com.raphaelneves.repositories;

import br.com.raphaelneves.models.Role;
import br.com.raphaelneves.models.User;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphaeloneves on 16/06/2017.
 */
@Stateless
public class UserRepositoryImpl implements UserRepository {

    private static List<User> users = new ArrayList();

    public UserRepositoryImpl(){
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

    @Override
    public User find(Long id) {
        User user = new User();
        user.setId(id);
        if(users.contains(user)){
            return users.get(users.indexOf(user));
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User auth(User u) {
        User user = null;
        for(User usr : users){
            if(u.getPassword().equals(usr.getPassword()) && u.getUsername().equals(usr.getUsername())){
                user = usr;
                break;
            }
        }
        return user;
    }

    @Override
    public void remove(User user) {
        if(users.contains(user)){
            users.remove(user);
        }
    }

    @Override
    public User save(User user) {
        if (users.contains(user)){
            throw new RuntimeException("User already exists!");
        }
        users.add(user);
        return user;
    }
}
