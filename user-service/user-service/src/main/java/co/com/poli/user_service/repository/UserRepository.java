package co.com.poli.user_service.repository;


import co.com.poli.user_service.persistence.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> findById(Long userId);
}
