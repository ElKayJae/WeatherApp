package com.vttp2022.weatherapp.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2022.weatherapp.service.WeatherService;
import com.vttp2022.weatherapp.model.Weather;

@Controller
@RequestMapping ("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public String getWeather(@RequestParam(required=true) String city, Model model){
        Optional<Weather> opt = weatherService.getWeather(city);
        if (opt.isEmpty())
        return "weather";

        model.addAttribute("weather", opt.get());
        return "weather";
    }
}
