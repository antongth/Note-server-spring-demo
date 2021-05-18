package net.tack.school.notes;

import net.tack.school.notes.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ServerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    //private RestTemplate template = new RestTemplate();

    private UserDTO.Request.RegisterUser userDto;

    @Test
    void test() {
        userDto = new UserDTO.Request.RegisterUser();
        userDto.setLogin("Isak17");
        userDto.setPassword("apple");
        userDto.setLastName("N");
        userDto.setFirstName("Isak");
        userDto.setPatronymic("F");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("Hallow world!");

//        RestTemplateBuilder builder = new RestTemplateBuilder();
//        builder.build().postForObject("http://localhost:8888/api/accounts", userDto, UserDto.class)

    }
}
