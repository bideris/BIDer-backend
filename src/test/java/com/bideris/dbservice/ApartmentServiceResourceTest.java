package com.bideris.dbservice;

import com.bideris.dbservice.helpers.ResponseApartment;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.Post;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.UsersRepository;
import com.bideris.dbservice.resource.ApartmentServiceResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApartmentServiceResourceTest {

    private MockMvc mockMvc;
    private StatusCodes statusCodes =new StatusCodes();

    @InjectMocks
    private ApartmentServiceResource apartmentServiceResource;



    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ApartmentRepository apartmentRepository;

    @MockBean
    private ApartmentServiceResource apartmentServiceResourceMB;

    @Before
    public void setup() throws Exception{
        this.mockMvc = MockMvcBuilders.standaloneSetup(apartmentServiceResource)
                .build();
    }

    @Test
    public void getApartment() throws  Exception{
//        ResponseApartment response = new ResponseApartment();

        Date date = new Date();

        Post post = new Post(
        new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        post.setId(1);

        System.out.println("POST " + post.toString());
        Mockito.when(apartmentRepository.findApartmentById(Mockito.anyInt())).thenReturn(post);


        MvcResult mvcResult = mockMvc.perform(
                get("/apartment/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();

        System.out.println(String.format("Result %s", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void allPoststest() throws Exception{

        MvcResult mvcResult = mockMvc.perform(
                get("/apartment/all")
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void  getApartmentsTest()throws Exception{

        Date date = new Date();
        List<Post> posts = new ArrayList<>();
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        post.setId(1);
        posts.add(post);
        User user = new User("user","user","user","user","user","user", new Date(2000,1,1));
        user.setRole("landlord");
        user.setId(1);
        System.out.println("POST " + post.toString());
        Mockito.when(usersRepository.findUserByIdAndRole(1,"landlord")).thenReturn(user);
        Mockito.when(apartmentRepository.findApartmentsByUser(user)).thenReturn(posts);

        MvcResult mvcResult = mockMvc.perform(
                get("/apartment/all/1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.when(apartmentRepository.findApartmentsByUser(user)).thenReturn(null);

         mvcResult = mockMvc.perform(
                get("/apartment/all/1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void addTest() throws Exception{

        Date date = new Date();
        List<Post> posts = new ArrayList<>();
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        post.setId(1);
        posts.add(post);
        User user = new User("user","user","user","user","user","user", new Date(2000,1,1));
        user.setId(1);
        user.setRole("landlord");

        String json ="{\n" +
                "  \"userFk\": \"1\",\n" +
                "  \"price\": \"1\",\n" +
                "  \"name\": \"test\",\n" +
                "  \"about\": \"test\",\n" +
                "  \"country\": \"test\",\n" +
                "  \"houseNumber\": \"test\",\n" +
                "  \"apartmentNumber\": \"test\",\n" +
                "  \"area\": \"1\",\n" +
                "  \"rooms\": \"1\",\n" +
                "  \"floor\": \"1\",\n" +
                "  \"duration\": \"1\",\n" +
                "  \"startDate\": \"2000-01-01\",\n" +
                "  \"builtYear\": \"2000-01-01\"\n" +
                "}";

        Mockito.when(apartmentRepository.findApartmentsByUser(new User())).thenReturn(posts);
        Mockito.when(usersRepository.findUserByIdAndRole(1,"landlord")).thenReturn(user);


        MvcResult mvcResult = mockMvc.perform(
                post("/apartment/add/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.when(usersRepository.findUserByIdAndRole(1,"landlord")).thenReturn(null);
        user.setRole("");
        Mockito.when(usersRepository.findUserById(1)).thenReturn(user);

        mvcResult = mockMvc.perform(
                post("/apartment/add/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    }

    @Test
    public  void deleteTest() throws Exception{

        Date date = new Date();
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        Post post2 = new Post(
                new User(), 111, "post2","post2", "post2", "post2", "post2",
                "post2", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        User user = new User("user","user","user","user","user","user", new Date(2000,1,1));
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        user.setId(1);

        MvcResult mvcResult = mockMvc.perform(
                get("/apartment/delete/1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.when(apartmentRepository.findApartmentById(Mockito.anyInt())).thenReturn(post);

        Mockito.when(usersRepository.findUserByIdAndRole(1,"landlord")).thenReturn(user);
        Mockito.when(apartmentRepository.findApartmentsByUser(user)).thenReturn(posts);

        mvcResult = mockMvc.perform(
                get("/apartment/delete/1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void getwinnersTest() throws Exception{


        Date date = new Date();
        List<Post> posts = new ArrayList<>();
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        post.setId(1);
        posts.add(post);

        Mockito.when(apartmentRepository.findApartmentsByUser(new User())).thenReturn(posts);


        MvcResult mvcResult = mockMvc.perform(
                get("/apartment/winners/1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }



}
