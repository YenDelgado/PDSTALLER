package co.com.poli.user_service.service;

import co.com.poli.user_service.persistence.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void delete(User User);
    List<User> findAll();
    User findById(Long id);
}
