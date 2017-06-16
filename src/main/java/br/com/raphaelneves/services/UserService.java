package br.com.raphaelneves.services;

import br.com.raphaelneves.models.User;
import br.com.raphaelneves.models.UserLogged;
import br.com.raphaelneves.repositories.UserRepository;
import br.com.raphaelneves.services.interfaces.JwtManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;

@Stateless
public class UserService {

    @EJB
    private UserRepository userRepository;

    @EJB
    private JwtManager jwt;

    public UserLogged login(User usr, UriInfo uriInfo){
        User user = userRepository.auth(usr);
        if(user != null)
            return jwt.create(user, uriInfo.getAbsolutePath().toString());
        return null;
    }

    public User find(Long id){
        return userRepository.find(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void logout(String token){
        jwt.revoke(token);
    }
}
