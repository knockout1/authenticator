package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceIT {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("pass"));
        userRepository.save(user);
    }

    @Test
    public void shouldGenerateValidJwtWhenCorrectCredentialProvided() throws Exception {
        mockMvc.perform(post("/authenticate").content("{\n"
                + "\t\"userName\" : \"user\",\n"
                + "\t\"password\" : \"pass\"\n"
                + "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAunathorizedWhenIncorrectPasswordProvided() throws Exception {
        mockMvc.perform(post("/authenticate").content("{\n"
                + "\t\"userName\" : \"user\",\n"
                + "\t\"password\" : \"passs\"\n"
                + "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnAunathorizedWhenIncorrectUserProvided() throws Exception {
        mockMvc.perform(post("/authenticate").content("{\n"
                + "\t\"userName\" : \"user\",\n"
                + "\t\"password\" : \"passs\"\n"
                + "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

}
