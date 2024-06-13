package co.com.poli.user_service.service;

import co.com.poli.user_service.persistence.entity.User;
import co.com.poli.user_service.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    @Override
    @Transactional (rollbackOn = Exception.class)
    public void save(User user) {repository.save(user);}

    @Override
    @Transactional (rollbackOn = Exception.class)
    public void delete(User user) {repository.delete(user);}

    @Override
    @Transactional (rollbackOn = Exception.class)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional (rollbackOn = Exception.class)
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
