package com.bideris.dbservice;

import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.*;
import com.bideris.dbservice.repository.*;
import com.bideris.dbservice.resource.AuctionServiceResource;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuctionServiceResourceTest {

    private MockMvc mockMvc;
    private StatusCodes statusCodes =new StatusCodes();

    @InjectMocks
    private AuctionServiceResource auctionServiceResource;


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

    @Before
    public void setup() throws Exception{
        this.mockMvc = MockMvcBuilders.standaloneSetup(auctionServiceResource)
                .build();
    }

    @Test
    public void getByid()  throws Exception{
        Mockito.when(auctionRepository.findAuctionById(Mockito.anyInt())).thenReturn(new Auction());


        MvcResult mvcResult = mockMvc.perform(
                get("/auction/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void getByPost() throws Exception{
        Mockito.when(auctionRepository.findAuctionByPostFk(Mockito.anyInt())).thenReturn(new Auction());


        MvcResult mvcResult = mockMvc.perform(
                get("/auction/post/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getAllAuctions()throws Exception {

        Mockito.when(auctionRepository.findAll()).thenReturn(new ArrayList<Auction>());


        MvcResult mvcResult = mockMvc.perform(
                get("/auction/all")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getDoneAuctionList() throws Exception{
        Date date = new Date();
        User user = new User("test","test","test","test","test","test", new Date(2000,1,1));
        user.setId(1);
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );


        List<UserAuction> auctions = new ArrayList<>();


        Auction auction = new Auction(date, 3,"test", post, 1,user , 1);
        auction.setId(1);
        Auction auction2 = new Auction(date, 3,"Ended", post, 1,user , 1);
        auction2.setId(2);
        Auction auction3 = new Auction(date, 3,"Done", post, 1,user , 1);
        auction3.setId(3);

        UserAuction ua = new UserAuction();
        ua.setUserFk(1);ua.setUser(user);ua.setAuctionFk(1);ua.setAuction(auction);
        auctions.add(ua);
        UserAuction ua2 = new UserAuction();
        ua2.setUserFk(1);ua2.setUser(user);ua2.setAuctionFk(2);ua2.setAuction(auction2);
        auctions.add(ua2);
        UserAuction ua3 = new UserAuction();
        ua3.setUserFk(1);ua3.setUser(user);ua3.setAuctionFk(3);ua3.setAuction(auction3);
        auctions.add(ua3);
        System.out.println("AAA "+auctions);
        Mockito.when(userAuctionRepository.findUserAuctionsByUserFk(Mockito.anyInt())).thenReturn(auctions);

        MvcResult mvcResult = mockMvc.perform(
                get("/auction/user/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void accept() throws Exception {
        Date date = new Date();
        User user = new User("test","test","test","test","test","test", new Date(2000,1,1));
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );



        Auction auction = new Auction(date, 3,"Ended", post, 1,user , 1);

        UserAuction ua = new UserAuction();

        ua.setUserFk(1);ua.setUser(user);ua.setAuctionFk(1);ua.setAuction(auction);

        Mockito.when(userAuctionRepository.findUserAuctionByUserFkAndAuctionFk(Mockito.anyInt() ,Mockito.anyInt())).thenReturn(ua);

//        Mockito.when(auctionRepository.save(auction));

        MvcResult mvcResult = mockMvc.perform(
                post("/auction/accept/1/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();

        Auction auction2 = new Auction(date, 3,"test", post, 1,null , null);
        auction2.setId(2);
        UserAuction ua2 = new UserAuction();
        ua2.setUserFk(1);ua2.setUser(user);ua2.setAuctionFk(1);ua2.setAuction(auction2);

        Mockito.when(userAuctionRepository.findUserAuctionByUserFkAndAuctionFk(Mockito.anyInt() ,Mockito.anyInt())).thenReturn(ua2);

        mvcResult = mockMvc.perform(
                post("/auction/accept/1/2")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getMessages() throws Exception{

        Mockito.when(messageRepository.findMessagesByAuctionFk(Mockito.anyInt())).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(
                get("/auction/messages/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getMessageByid() throws Exception {

        Mockito.when(messageRepository.findMessageById(Mockito.anyInt())).thenReturn(new Message());

        MvcResult mvcResult = mockMvc.perform(
                get("/auction/message/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void addMessage() throws Exception {

        Mockito.when(auctionRepository.findAuctionById(Mockito.anyInt())).thenReturn(new Auction());
        Mockito.when(usersRepository.findUserById(Mockito.anyInt())).thenReturn(new User());

        String json ="{\n" +
                "  \"text\": \"test\",\n" +
                "  \"auctionFk\": \"1\",\n" +
                "  \"userFk\": \"1\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/auction/message/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();
    }


    @Test
    public void getBids() throws Exception{
        Mockito.when(bidRepository.findBidsByAuctionFk(Mockito.anyInt())).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(
                get("/auction/bids/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getBidById() throws Exception{

        Mockito.when(bidRepository.findBidById(Mockito.anyInt())).thenReturn(new Bid());

        MvcResult mvcResult = mockMvc.perform(
                get("/auction/bids/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void addBid() throws Exception{
        Mockito.when(auctionRepository.findAuctionById(Mockito.anyInt())).thenReturn(new Auction());
        Mockito.when(usersRepository.findUserById(Mockito.anyInt())).thenReturn(new User());

        String json ="{\n" +
                "  \"sum\": \"1\",\n" +
                "  \"auctionFk\": \"1\",\n" +
                "  \"userFk\": \"1\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(
                post("/auction/bid/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(status().isOk()).andReturn();
    }


    @Test
    public void getDoneWonAuctionList()throws Exception {
        Date date = new Date();
        User user = new User("test","test","test","test","test","test", new Date(2000,1,1));
        user.setId(1);
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );


        List<UserAuction> auctions = new ArrayList<>();


        Auction auction = new Auction(date, 3,"test", post, 1,user , 1);
        auction.setId(1);
        Auction auction2 = new Auction(date, 3,"Ended", post, 1,user , 1);
        auction2.setId(2);
        Auction auction3 = new Auction(date, 3,"Done", post, 1,user , 1);
        auction3.setId(3);

        UserAuction ua = new UserAuction();
        ua.setUserFk(1);ua.setUser(user);ua.setAuctionFk(1);ua.setAuction(auction);
        auctions.add(ua);
        UserAuction ua2 = new UserAuction();
        ua2.setUserFk(1);ua2.setUser(user);ua2.setAuctionFk(2);ua2.setAuction(auction2);
        auctions.add(ua2);
        UserAuction ua3 = new UserAuction();
        ua3.setUserFk(1);ua3.setUser(user);ua3.setAuctionFk(3);ua3.setAuction(auction3);
        auctions.add(ua3);

        Mockito.when(userAuctionRepository.findUserAuctionsByUserFk(Mockito.anyInt())).thenReturn(auctions);

        MvcResult mvcResult = mockMvc.perform(
                get("/auction/won/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getEndedAuctionList() throws Exception{
        Mockito.when(userAuctionRepository.findUserAuctionsByUserFk(Mockito.anyInt())).thenReturn(new ArrayList<>());

        Date date = new Date();
        User user = new User("test","test","test","test","test","test", new Date(2000,1,1));
        user.setId(1);
        Post post = new Post(
                new User(), 111, "test","test", "test", "test", "test",
                "test", 1.0, 1.0, 1.0, 1.0, date, date, 1
        );


        List<UserAuction> auctions = new ArrayList<>();


        Auction auction = new Auction(date, 3,"test", post, 1,user , 1);
        auction.setId(1);
        Auction auction2 = new Auction(date, 3,"Ended", post, 1,user , 1);
        auction2.setId(2);
        Auction auction3 = new Auction(date, 3,"Done", post, 1,user , 1);
        auction3.setId(3);

        UserAuction ua = new UserAuction();
        ua.setUserFk(1);ua.setUser(user);ua.setAuctionFk(1);ua.setAuction(auction);
        auctions.add(ua);
        UserAuction ua2 = new UserAuction();
        ua2.setUserFk(1);ua2.setUser(user);ua2.setAuctionFk(2);ua2.setAuction(auction2);
        auctions.add(ua2);
        UserAuction ua3 = new UserAuction();
        ua3.setUserFk(1);ua3.setUser(user);ua3.setAuctionFk(3);ua3.setAuction(auction3);
        auctions.add(ua3);

        Mockito.when(userAuctionRepository.findUserAuctionsByUserFk(Mockito.anyInt())).thenReturn(auctions);

        MvcResult mvcResult = mockMvc.perform(
                get("/auction/ended/1")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();


    }
}