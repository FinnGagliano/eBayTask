package com.shutl.controller;

import com.shutl.model.Quote;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class QuoteController {

    @RequestMapping(value = "/quote", method = POST)
    public @ResponseBody Quote quote(@RequestBody Quote quote) {
        Long price = Math.abs((Long.valueOf(quote.getDeliveryPostcode(), 36) - Long.valueOf(quote.getPickupPostcode(), 36))/100000000);
        // Markup the price of the quote depending on vehicle used.
        double markup = 1;
        switch (quote.getVehicle()) {
          case "bicycle":
            markup = 1.1;
            break;
          case "motorcycle":
            markup = 1.15;
            break;
          case "parcel_car":
            markup = 1.2;
            break;
          case "small_van":
            markup = 1.3;
            break;
          case "large_van":
            markup = 1.4;
            break;
        }
        price = (Long) Math.round(price * markup);
        return new Quote(quote.getPickupPostcode(), quote.getDeliveryPostcode(), quote.getVehicle(), price);
    }
}
