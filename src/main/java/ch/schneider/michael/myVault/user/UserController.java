package ch.schneider.michael.myVault.user;

import ch.schneider.michael.myVault.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<User> currentUser(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<User>> all() {
        List<User> result = userService.getUsers();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user, id);
        return ResponseEntity.ok(updatedUser);
    }
}
