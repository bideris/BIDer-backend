package com.bideris.dbservice;

import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.Auction;
import com.bideris.dbservice.model.Post;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.*;
import com.bideris.dbservice.resource.LandlordServiceResource;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class LandlordServiceResourceTest {

    private MockMvc mockMvc;
    private StatusCodes statusCodes =new StatusCodes();
    private String role = "landlord";

    @InjectMocks
    private LandlordServiceResource landlordServiceResource;


    @Mock
    AuctionRepository auctionRepository;

    @Mock
    UserAuctionRepository userAuctionRepository;
    @Mock
    MessageRepository messageRepository;
    @Mock
    UsersRepository usersRepository;
    @Mock
    BidRepository bidRepository;
    @Mock
    ApartmentRepository apartmentRepository;
    @Mock
    ReviewRepository reviewRepository;

    @Before
    public void setup() throws Exception{
        this.mockMvc = MockMvcBuilders.standaloneSetup(landlordServiceResource)
                .build();
    }

    @Test
    public void getLandlord() throws Exception{
        User user = new User("test","test","test","test","test","test", new Date(2000,1,1));
        user.setRole(role);
        user.setId(1);

        Mockito.when(usersRepository.findUserByIdAndRole(1,role)).thenReturn(new User());


        MvcResult mvcResult = mockMvc.perform(
                get("/landlord/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void add() throws Exception{
        User test = new User("test","test","test","test","test","test", new Date(2000,1,1));
        test.setId(1);
        test.setRole(role);
        Mockito.when(usersRepository.findUserByIdAndRole(1,role)).thenReturn(null);


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
                post("/landlord/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();

        System.out.println(String.format("resultatas %s",mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void delete() throws Exception{
        User user = new User("test","test","test","test","test","test", new Date(2000,1,1));
        user.setRole(role);
        user.setId(1);

        Date date = new Date();
        List<Post> posts = new ArrayList<>();
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );
        post.setId(1);
        posts.add(post);

        Mockito.when(apartmentRepository.findApartmentsByUser(new User())).thenReturn(posts);


        Mockito.when(usersRepository.findUserByIdAndRole(1,role)).thenReturn(new User());
        Mockito.when(apartmentRepository.findApartmentsByUser(user)).thenReturn(posts);


        MvcResult mvcResult = mockMvc.perform(
                post("/landlord/delete/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getwinners() throws Exception{

        User owner = new User("owner","owner","owner","owner","owner","owner", new Date(2000,1,1));
        User user = new User("user","user","user","user","user","user", new Date(2000,1,1));
        owner.setId(1);
        owner.setRole(role);
        user.setId(2);
        user.setRole("user");

        Mockito.when(usersRepository.findUserById(1)).thenReturn(owner);
        Mockito.when(usersRepository.findUserById(1)).thenReturn(user);


        String json ="{\n" +
                "  \"rate\": \"5\",\n" +
                "  \"feedback\": \"test\",\n" +
                "  \"userRFk\": \"1\",\n" +
                "  \"userFFk\": \"2\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/landlord/writeReview/1/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();


    }
}