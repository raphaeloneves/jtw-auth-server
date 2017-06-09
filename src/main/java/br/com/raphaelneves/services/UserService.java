package br.com.raphaelneves.services;

import br.com.raphaelneves.models.User;
import br.com.raphaelneves.models.UserLogged;
import br.com.raphaelneves.repositories.UserRepository;
import br.com.raphaelneves.utils.JwtUtil;

import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;

/**
 * Created by raphaeloneves on 08/06/2017.
 */
public class UserService {
    public static UserRepository userRepository = new UserRepository();

    public UserLogged login(User usr, UriInfo uriInfo){
        User user = userRepository.authUser(usr.getUsername(), usr.getPassword());
        UserLogged logged = null;
        if(user != null){
            String token = JwtUtil.createToken(user, uriInfo.getAbsolutePath().toString());
            logged = new UserLogged(user.getUsername(), token, new Date());
        }
        return logged;
    }

    public User find(String id){
        return userRepository.find(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
