package net.tack.school.notes.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.tack.school.notes.model.User;
import net.tack.school.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserEndPoints.class)
class UserEndPointsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Test
    public void test1() throws Exception {

        User user = new User();

        MvcResult result = mvc.perform(
                post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 500);

        //result.andExpect(status().isOk()).andExpect(content().string("{}"));

    }

    @Test
    public void test() throws Exception {
        mvc.perform(get("/api/accounts")).andExpect(status().isOk());
    }

}