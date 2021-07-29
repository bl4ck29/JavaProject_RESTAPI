package com.market.skin;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.skin.controller.ItemsController;
import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.ErrorCode;
import com.market.skin.model.Items;
import com.market.skin.model.SuccessCode;
import com.market.skin.repository.ItemsRepository;
import com.market.skin.service.ItemsService;

import java.util.Random;

import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.ResponseEntity;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemsController.class)
public class ItemsControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    private ItemsController itemsController;
    @MockBean
    private ItemsService itemsService;
    @MockBean
    private ItemsRepository itemsRepository;
    

    @Before
    public void setUp(){
        List<Items> testDB = new ArrayList<>();
        testDB.add(new Items(1, 1, 1, "path 1", 9.1f));
        testDB.add(new Items(2, 2, 2, "path 2", 9.2f));
        testDB.add(new Items(3, 3, 3, "path 3", 9.0f));
    }

    @Test
    public void createExistedItem() throws Exception{
        Items newItem = new Items(1, 1, 1, "path 1", 2.2f);
        Mockito.when(itemsController.createItems(newItem))
            .thenReturn(ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).build()));
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patient")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(newItem));

        mvc.perform(mockRequest).andExpect(status().isConflict());
    }
}
