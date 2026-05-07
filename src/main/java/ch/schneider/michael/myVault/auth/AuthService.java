package ch.schneider.michael.myVault.auth;

import ch.schneider.michael.myVault.user.User;
import ch.schneider.michael.myVault.user.UserService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User currentUser(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }
}
