// POST 	/api/movement 	→ Create transaction
// GET 	/api/movement	    →List all transactions
// GET 	/api/movement/{id} 	→ List that specific transaction
// PUT 	/api/movement/{id} 	→ Update that specific transaction
// DEL	/api/movement/{id} 	→ Delete that specific transaction



package ch.schneider.michael.myVault.movement;

import ch.schneider.michael.myVault.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
@RequestMapping("/api/movement")
public class MovementController {

    private final MovementService movementService;

    MovementController(MovementService movementService) {
        this.movementService = movementService;
    }


    // GET /api/movement
    @GetMapping
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Movement>> all() {
        List<Movement> result = movementService.getMovements();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/movement/{id}
    @GetMapping("/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Movement> one(@PathVariable Long id) {
        Movement movement = movementService.getMovement(id);
        return new ResponseEntity<>(movement, HttpStatus.OK);
    }

    // POST /api/movement
    @PostMapping
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Movement> newMovement(@Valid @RequestBody Movement movement) {
        Movement savedMovement = movementService.insertMovement(movement);
        return new ResponseEntity<>(savedMovement, HttpStatus.CREATED);
    }

    // PUT /api/movement/{id}
    @PutMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Movement> updateMovement(@PathVariable Long id, @Valid @RequestBody Movement movement) {
        Movement updatedMovement = movementService.updateMovement(movement, id);
        return new ResponseEntity<>(updatedMovement, HttpStatus.OK);
    }

    // DELETE /api/movement/{id}
    @DeleteMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
