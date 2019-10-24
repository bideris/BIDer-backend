package com.bideris.dbservice;

import com.bideris.dbservice.helpers.PasswordHashing;
import com.bideris.dbservice.model.Auction;
import com.bideris.dbservice.model.Post;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.model.UserAuction;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.AuctionRepository;
import com.bideris.dbservice.repository.UserAuctionRepository;
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

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private UserAuctionRepository userAuctionRepository;

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

        String json ="{\n" +
                "  \"userName\": \"aaa\",\n" +
                "  \"email\": \"a@gamil.com\",\n" +
                "  \"password\": \"tesT123123\",\n" +
                "  \"password2\": \"tesT123123\",\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"test\",\n" +
                "  \"about\": \"test\",\n" +
                "  \"birthdate\": \"2000-01-01\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/user/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();

        User user = new User("test","test@gmail.com", PasswordHashing.hashPassword("tesT123123"),"test","test","test", new Date(2000,1,1));
        user.setRole("user");
        user.setId(1);

        Mockito.when(usersRepository.findUserByUserNameOrEmailAndRole("test","test@gamil.com","user")).thenReturn(user);
        String json2 ="{\n" +
                "  \"userName\": \"test\",\n" +
                "  \"email\": \"test@gamil.com\",\n" +
                "  \"password\": \"tesT123123\",\n" +
                "  \"password2\": \"tesT123123\",\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"test\",\n" +
                "  \"about\": \"test\",\n" +
                "  \"birthdate\": \"2000-01-01\"\n" +
                "}";

         mvcResult = mockMvc.perform(
                post("/user/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)

        ).andExpect(status().isOk()).andReturn();


    }

    @Test
    public void LikePost() throws Exception{
        User test = new User("test","test","test","test","test","test", new Date(2000,1,1));
        Mockito.when(usersRepository.findUserByUserName(test.getUserName())).thenReturn(null);

        Date date = new Date();

        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        post.setId(1);

        System.out.println("POST " + post.toString());

        User user = new User("user","user","user","user","user","user", new Date(2000,1,1));
        user.setId(2);
        user.setRole("user");
        Auction auction = new Auction(date, 3,"Done", post, 1,user , 1);
        UserAuction ua = new UserAuction();
        ua.setUserFk(1);ua.setUser(user);ua.setAuctionFk(1);ua.setAuction(auction);

        Mockito.when(usersRepository.findUserById(1)).thenReturn(user);
        Mockito.when(apartmentRepository.findApartmentById(1)).thenReturn(post);
        Mockito.when(auctionRepository.findAuctionByPostFk(1)).thenReturn(auction);
        Mockito.when(userAuctionRepository.findUserAuctionByUserFkAndAuctionFk(1,1)).thenReturn(ua);

        MvcResult mvcResult = mockMvc.perform(
                post("/user/like/1/1")
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isOk()).andReturn();

        Mockito.when(auctionRepository.findAuctionByPostFk(1)).thenReturn(null);

        mvcResult = mockMvc.perform(
                post("/user/like/1/1")
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isOk()).andReturn();


    }
}