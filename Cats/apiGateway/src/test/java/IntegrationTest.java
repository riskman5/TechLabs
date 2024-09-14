import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.babenko.CatsClient;
import ru.babenko.KafkaProducer;
import ru.babenko.Main;
import ru.babenko.OwnersClient;
import ru.babenko.dao.UsersDao;
import ru.babenko.dtos.Color;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@EnableMethodSecurity
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CatsClient catsClient;
    @MockBean
    private OwnersClient ownersClient;
    @MockBean
    private KafkaProducer kafkaProducer;
    private String jwtToken;

    @Sql(scripts = {"/AddAdmin.sql"})
    @Test
    public void authTest() throws Exception {
        jwtToken = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"admin\",\"password\":\"password\"}"))
                        .andReturn().getResponse().getContentAsString();

    }

    @Sql(scripts = {"/AddUserWithOwner.sql"})
    @Test
    public void getCatsTest() throws Exception {
        jwtToken = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                        .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cats")
                        .header("Authorization", "Bearer " + jwtToken))
                        .andReturn().getResponse().getContentAsString();

        verify(catsClient, times(1)).findAllCats(1L);
    }

    @Sql(scripts = {"/AddUserWithOwner.sql"})
    @Test
    public void filterCatsTest() throws Exception {
        jwtToken = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                        .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cats/filter?color=BLACK&breed=Siamese")
                .header("Authorization", "Bearer " + jwtToken))
                .andReturn().getResponse().getContentAsString();

        verify(catsClient, times(1)).findCatsByParams(Color.BLACK, "Siamese", 1L);
    }

}
