package ch.schneider.michael.myVault.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.findByOrderByUsernameAsc();
    }

    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " not found"));
    }

    public User insertUser(User user) {
        return repository.save(user);
    }

    public User updateUser(User user, Long id) {
        return repository.findById(id)
                .map(userOrig -> {
                    userOrig.setUsername(user.getUsername());
                    userOrig.setRole(user.getRole());
                    return repository.save(userOrig);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return repository.save(user);
                });
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
