package com.knockout.authenticator;

import com.knockout.authenticator.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerIT {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    private BCryptPasswordEncoder passwordEncoder;
    private User user;

    void setup() {
        user = new User();
        user.setUserName("user");
        passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("pass"));
        userRepository.save(user);
    }

    @Test
    void shouldGenerateValidJwtWhenCorrectCredentialProvided() throws Exception {
        user = new User();
        user.setUserName("user");
        passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("pass"));
        userRepository.save(user);

        MvcResult result = mockMvc.perform(post("/authenticate").content("{\n"
                + "\t\"userName\" : \"user\",\n"
                + "\t\"password\" : \"pass\"\n"
                + "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String jwtToken = result.getResponse().getContentAsString();
        assertTrue(!jwtToken.isEmpty());
    }

    @Test
    void shouldReturnAunathorizedWhenIncorrectPasswordProvided() throws Exception {
        mockMvc.perform(post("/authenticate").content("{\n"
                + "\t\"userName\" : \"user\",\n"
                + "\t\"password\" : \"passs\"\n"
                + "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnAunathorizedWhenIncorrectUserProvided() throws Exception {
        mockMvc.perform(post("/authenticate").content("{\n"
                + "\t\"userName\" : \"user\",\n"
                + "\t\"password\" : \"passs\"\n"
                + "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnTrueWhenValidJWTTokenProvided() throws Exception {
        mockMvc.perform(post("/validateJwt").content(
                "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm9ja291dC5jb20iLCJzdWIiOiJ1c2VyIn0.yDKAjP57gJ6G4riqg9CDjq8ZRnYGf8jr_6TErqVMq60")
                .contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

    @Test
    void shouldReturnFalseWhenInvalidJWTTokenProvided() throws Exception {
        mockMvc.perform(post("/validateJwt").content(
                "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm9ja291dC5jb20iVyIn0.yDKAjP57gJ6G4riqg9CDjq8ZRnYGf8jr_6TErqVMq60")
                .contentType(MediaType.TEXT_PLAIN)).andExpect(status().isUnauthorized());
    }

}
