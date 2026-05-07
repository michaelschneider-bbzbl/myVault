package ch.schneider.michael.myVault.movement;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {
    private final MovementRepository repository;

    public MovementService(MovementRepository repository) {
        this.repository = repository;
    }

    public List<Movement> getMovements() { 
        return repository.findByOrderByIdAsc();
    }

    public Movement getMovement(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movement " + id + " not found"));
    }

    public Movement insertMovement(Movement movement) {
        return repository.save(movement);
    }
    
    public Movement updateMovement(Movement movement, Long id) {
        return repository.findById(id)
                .map(movementOrig -> {
                    movementOrig.setAmount(movement.getAmount());
                    movementOrig.setType(movement.getType());
                    movementOrig.setMovementDate(movement.getMovementDate());
                    movementOrig.setDescription(movement.getDescription());
                    movementOrig.setUser(movement.getUser());
                    movementOrig.setCategory(movement.getCategory());
                    return repository.save(movementOrig);
                })
                .orElseGet(() -> {
                    movement.setId(id);
                    return repository.save(movement);
                });
    }

    public void deleteMovement(Long id) {
        repository.deleteById(id);
    }
}