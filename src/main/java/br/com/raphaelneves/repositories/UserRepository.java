package br.com.raphaelneves.repositories;

import br.com.raphaelneves.models.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserRepository {

    User find(Long id);
    List<User> findAll();
    User auth(User user);
    void remove(User user);
    User save(User user);

}
