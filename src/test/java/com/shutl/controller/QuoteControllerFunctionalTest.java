package com.shutl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutl.Application;
import com.shutl.model.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class QuoteControllerFunctionalTest {

    @Autowired private WebApplicationContext webApplicationContext;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testBasicService() throws Exception {
        Quote quoteData = new Quote("SW1A1AA", "EC2A3LT", "bicycle");
        MvcResult result = this.mockMvc.perform(post("/quote")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quoteData)))
            .andExpect(status().isOk())
            .andReturn();

        Quote quote = objectMapper.readValue(result.getResponse().getContentAsString(), Quote.class);
        assertEquals(quote.getPickupPostcode(), "SW1A1AA");
        assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
        assertEquals(quote.getVehicle(), "bicycle");
        assertEquals(quote.getPrice(), new Long(348));
    }

    @Test
    public void testVariablePricingByDistance() throws Exception {
        Quote quoteData = new Quote("SW1A1AA", "EC2A3LT", "motorcycle");
        MvcResult result = this.mockMvc.perform(post("/quote")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quoteData)))
            .andExpect(status().isOk())
            .andReturn();

        Quote quote = objectMapper.readValue(result.getResponse().getContentAsString(), Quote.class);
        assertEquals(quote.getPickupPostcode(), "SW1A1AA");
        assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
        assertEquals(quote.getVehicle(), "motorcycle");
        assertEquals(quote.getPrice(), new Long(363));

        quoteData = new Quote("AL15WD", "EC2A3LT", "motorcycle");
        result = this.mockMvc.perform(post("/quote")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quoteData)))
            .andExpect(status().isOk())
            .andReturn();

        quote = objectMapper.readValue(result.getResponse().getContentAsString(), Quote.class);
        assertEquals(quote.getPickupPostcode(), "AL15WD");
        assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
        assertEquals(quote.getVehicle(), "motorcycle");
        assertEquals(quote.getPrice(), new Long(351));
    }

    @Test
    public void testUndefinedVehicle() throws Exception {
      Quote quoteData = new Quote("SW1A1AA", "EC2A3LT", "the_eagles");
      MvcResult result = this.mockMvc.perform(post("/quote")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(quoteData)))
          .andExpect(status().isOk())
          .andReturn();

      Quote quote = objectMapper.readValue(result.getResponse().getContentAsString(), Quote.class);
      assertEquals(quote.getPickupPostcode(), "SW1A1AA");
      assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
      assertEquals(quote.getVehicle(), "the_eagles");
      assertEquals(quote.getPrice(), new Long(316))
    }

    @Test
    public void testZeroDistanceZeroPrice() throws Exception {
      Quote quoteData = new Quote("SW1A1AA", "SW1A1AA", "large_van");
      MvcResult result = this.mockMvc.perform(post("/quote")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(quoteData)))
          .andExpect(status().isOk())
          .andReturn();

          Quote quote = objectMapper.readValue(result.getResponse().getContentAsString(), Quote.class);
          assertEquals(quote.getPickupPostcode(), "SW1A1AA");
          assertEquals(quote.getDeliveryPostcode(), "SW1A1AA");
          assertEquals(quote.getVehicle(), "large_van");
          assertEquals(quote.getPrice(), new Long(0))
    }
}
