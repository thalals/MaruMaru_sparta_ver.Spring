package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.MaruMaruSpartaVerSpringApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest     //Junit5 - Runwith 포함
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MaruMaruSpartaVerSpringApplication.class)
//Controller + Spring Security
@WebMvcTest(controllers = {PostController.class}, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@AutoConfigureMockMvc //@Service, @Repository가 붙은 객체들도 모두 메모리에 올림
@RequestMapping("/api")
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void postList_GET() throws Exception {
        MultiValueMap<String, String> query_param = new LinkedMultiValueMap<>();
        query_param.add("page","1");
        query_param.add("sorted","createdAt");

//        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&sorted=createdAt"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/posts").params(query_param))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
    }

}