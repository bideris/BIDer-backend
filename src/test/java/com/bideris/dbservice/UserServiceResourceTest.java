package com.bideris.dbservice;

import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.UsersRepository;
import com.bideris.dbservice.resource.UserServiceResource;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceResourceTest {


    private MockMvc mockMvc;

    @InjectMocks
    private UserServiceResource userServiceResource;

    @Mock
    private UsersRepository usersRepository;

    @Before
    public void setup() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(userServiceResource)
                .build();
    }

    @Test
    public void getUsers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getUser() throws Exception {


        mockMvc.perform(
                get("/user/test")
                .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk());

    }

    @Test
    public void add() throws Exception {

        User test = new User("test","test","test","test","test","test", new Date(2000,1,1));
        Mockito.when(usersRepository.findUserByUserName(test.getUserName())).thenReturn(null);


        String json ="{\n" +
                "  \"userName\": \"test\",\n" +
                "  \"email\": \"test\",\n" +
                "  \"password\": \"test\",\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"test\",\n" +
                "  \"about\": \"test\",\n" +
               "  \"birthdate\": \"2000-01-01\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/user/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();

        System.out.println(String.format("resultatas %s",mvcResult.getResponse().getContentAsString()));

    }

    @Test
    public void delete() throws Exception {
        User test = new User("test","test","test","test","test","test", new Date(2000,1,1));
        Mockito.when(usersRepository.findUserByUserNameAndRole(test.getUserName(),"user")).thenReturn(test);

        mockMvc.perform(
                post("/user/delete/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)


        ).andExpect(status().isOk());

    }


    @Test
    public void register() throws Exception {

        User test = new User("test","test","test","test","test","test", new Date(2000,1,1));
        Mockito.when(usersRepository.findUserByUserName(test.getUserName())).thenReturn(null);


        String json ="{\n" +
                "  \"userName\": \"test\",\n" +
                "  \"email\": \"test@gamil.com\",\n" +
                "  \"password\": \"tAAest123\",\n" +
                "  \"password2\": \"tAAest123\",\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"test\",\n" +
                "  \"about\": \"test\",\n" +
                "  \"birthdate\": \"2000-01-01\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();

        System.out.println(String.format("resultatas %s",mvcResult.getResponse().getContentAsString()));

    }

    @Test
    public void login() throws Exception{
        User test = new User("test","test","test","test","test","test", new Date(2000,1,1));
        Mockito.when(usersRepository.findUserByUserName(test.getUserName())).thenReturn(null);


        String json ="{\n" +
                "  \"userName\": \"test\",\n" +
                "  \"email\": \"test@gamil.com\",\n" +
                "  \"password\": \"tAAest123\",\n" +
                "  \"password2\": \"tAAest123\",\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"test\",\n" +
                "  \"about\": \"test\",\n" +
                "  \"birthdate\": \"2000-01-01\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();

        System.out.println(String.format("resultatas %s",mvcResult.getResponse().getContentAsString()));

    }

    @Test
    public void LikePost(){
        User test = new User("test","test","test","test","test","test", new Date(2000,1,1));
        Mockito.when(usersRepository.findUserByUserName(test.getUserName())).thenReturn(null);


        String json ="{\n" +
                "  \"userName\": \"test\",\n" +
                "  \"email\": \"test@gamil.com\",\n" +
                "  \"password\": \"tAAest123\",\n" +
                "  \"password2\": \"tAAest123\",\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"test\",\n" +
                "  \"about\": \"test\",\n" +
                "  \"birthdate\": \"2000-01-01\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();

        System.out.println(String.format("resultatas %s",mvcResult.getResponse().getContentAsString()));

    }
}