package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Hotel;
import com.mycompany.myapp.repository.HotelRepository;

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
 * Test class for the HotelResource REST controller.
 *
 * @see HotelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HotelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    @Inject
    private HotelRepository hotelRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHotelMockMvc;

    private Hotel hotel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HotelResource hotelResource = new HotelResource();
        ReflectionTestUtils.setField(hotelResource, "hotelRepository", hotelRepository);
        this.restHotelMockMvc = MockMvcBuilders.standaloneSetup(hotelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hotel = new Hotel();
        hotel.setName(DEFAULT_NAME);
        hotel.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createHotel() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();

        // Create the Hotel

        restHotelMockMvc.perform(post("/api/hotels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hotel)))
                .andExpect(status().isCreated());

        // Validate the Hotel in the database
        List<Hotel> hotels = hotelRepository.findAll();
        assertThat(hotels).hasSize(databaseSizeBeforeCreate + 1);
        Hotel testHotel = hotels.get(hotels.size() - 1);
        assertThat(testHotel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotel.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void getAllHotels() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotels
        restHotelMockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    public void getHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", hotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingHotel() throws Exception {
        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

		int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Update the hotel
        hotel.setName(UPDATED_NAME);
        hotel.setCode(UPDATED_CODE);

        restHotelMockMvc.perform(put("/api/hotels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hotel)))
                .andExpect(status().isOk());

        // Validate the Hotel in the database
        List<Hotel> hotels = hotelRepository.findAll();
        assertThat(hotels).hasSize(databaseSizeBeforeUpdate);
        Hotel testHotel = hotels.get(hotels.size() - 1);
        assertThat(testHotel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotel.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

		int databaseSizeBeforeDelete = hotelRepository.findAll().size();

        // Get the hotel
        restHotelMockMvc.perform(delete("/api/hotels/{id}", hotel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Hotel> hotels = hotelRepository.findAll();
        assertThat(hotels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
