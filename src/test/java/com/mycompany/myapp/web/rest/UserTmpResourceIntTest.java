package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.UserTmp;
import com.mycompany.myapp.repository.UserTmpRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UserTmpResource REST controller.
 *
 * @see UserTmpResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserTmpResourceIntTest {


    @Inject
    private UserTmpRepository userTmpRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserTmpMockMvc;

    private UserTmp userTmp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserTmpResource userTmpResource = new UserTmpResource();
        ReflectionTestUtils.setField(userTmpResource, "userTmpRepository", userTmpRepository);
        this.restUserTmpMockMvc = MockMvcBuilders.standaloneSetup(userTmpResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userTmp = new UserTmp();
    }

    @Test
    @Transactional
    public void createUserTmp() throws Exception {
        int databaseSizeBeforeCreate = userTmpRepository.findAll().size();

        // Create the UserTmp

        restUserTmpMockMvc.perform(post("/api/userTmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userTmp)))
                .andExpect(status().isCreated());

        // Validate the UserTmp in the database
        List<UserTmp> userTmps = userTmpRepository.findAll();
        assertThat(userTmps).hasSize(databaseSizeBeforeCreate + 1);
        UserTmp testUserTmp = userTmps.get(userTmps.size() - 1);
    }

    @Test
    @Transactional
    public void getAllUserTmps() throws Exception {
        // Initialize the database
        userTmpRepository.saveAndFlush(userTmp);

        // Get all the userTmps
        restUserTmpMockMvc.perform(get("/api/userTmps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userTmp.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUserTmp() throws Exception {
        // Initialize the database
        userTmpRepository.saveAndFlush(userTmp);

        // Get the userTmp
        restUserTmpMockMvc.perform(get("/api/userTmps/{id}", userTmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userTmp.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserTmp() throws Exception {
        // Get the userTmp
        restUserTmpMockMvc.perform(get("/api/userTmps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserTmp() throws Exception {
        // Initialize the database
        userTmpRepository.saveAndFlush(userTmp);

		int databaseSizeBeforeUpdate = userTmpRepository.findAll().size();

        // Update the userTmp

        restUserTmpMockMvc.perform(put("/api/userTmps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userTmp)))
                .andExpect(status().isOk());

        // Validate the UserTmp in the database
        List<UserTmp> userTmps = userTmpRepository.findAll();
        assertThat(userTmps).hasSize(databaseSizeBeforeUpdate);
        UserTmp testUserTmp = userTmps.get(userTmps.size() - 1);
    }

    @Test
    @Transactional
    public void deleteUserTmp() throws Exception {
        // Initialize the database
        userTmpRepository.saveAndFlush(userTmp);

		int databaseSizeBeforeDelete = userTmpRepository.findAll().size();

        // Get the userTmp
        restUserTmpMockMvc.perform(delete("/api/userTmps/{id}", userTmp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserTmp> userTmps = userTmpRepository.findAll();
        assertThat(userTmps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
