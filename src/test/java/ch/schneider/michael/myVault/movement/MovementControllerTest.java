package ch.schneider.michael.myVault.movement;

import ch.schneider.michael.myVault.security.Roles;
import ch.schneider.michael.myVault.user.User;
import ch.schneider.michael.myVault.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class MovementControllerTest {

    private MockMvc api;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private UserRepository userRepository;

    private Long userId;

    @BeforeEach
    void setup() {
        this.api = webAppContextSetup(wac).apply(springSecurity()).build();
        movementRepository.deleteAll();
        userRepository.deleteAll();
        userId = userRepository.save(new User(null, "controller-user", "read")).getId();
    }

    @Test
    void testSaveMovement() throws Exception {
        String body = """
                {
                  "amount": 10,
                  "type": "expense",
                  "movement_date": "2026-05-08 08:00:00",
                  "description": "Test REST",
                  "user": { "id": %d, "username": "controller-user", "role": "read" }
                }
                """.formatted(userId);

        api.perform(post("/api/movement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(csrf())
                        .with(jwt().authorities(a("ROLE_" + Roles.Admin), a(Roles.Admin))))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Test REST")));
    }

    private static org.springframework.security.core.authority.SimpleGrantedAuthority a(String role) {
        return new org.springframework.security.core.authority.SimpleGrantedAuthority(role);
    }
}

