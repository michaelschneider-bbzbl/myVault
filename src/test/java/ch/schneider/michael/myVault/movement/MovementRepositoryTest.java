package ch.schneider.michael.myVault.movement;

import ch.schneider.michael.myVault.user.User;
import ch.schneider.michael.myVault.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MovementRepositoryTest {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() {
        User user = userRepository.save(new User(null, "test-user", "read"));

        Movement created = movementRepository.save(
                new Movement(null, 100, "income", LocalDateTime.of(2026, 5, 8, 8, 0, 0), "repo test", user, null)
        );
        assertThat(created.getId()).isNotNull();

        Optional<Movement> loaded = movementRepository.findById(created.getId());
        assertThat(loaded).isPresent();
        assertThat(loaded.get().getAmount()).isEqualTo(100);

        loaded.get().setAmount(250);
        Movement updated = movementRepository.save(loaded.get());
        assertThat(updated.getAmount()).isEqualTo(250);

        movementRepository.deleteById(updated.getId());
        assertThat(movementRepository.findById(updated.getId())).isEmpty();
    }
}