package ch.schneider.michael.myVault.auth;

import ch.schneider.michael.myVault.security.Roles;
import ch.schneider.michael.myVault.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<User> currentUser(Principal principal) {
        return ResponseEntity.ok(authService.currentUser(principal));
    }
}
