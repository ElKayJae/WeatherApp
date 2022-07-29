package com.vttp2022.weatherapp.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


public class Weather {

    private static final Logger logger = LoggerFactory.getLogger(Weather.class);

    private String city;
    private String temperature;

    public List<Conditions> conditionsList = new LinkedList<>();


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temp) {
        this.temperature = temp;
    }

    public List<Conditions> getConditionsList() {
        return conditionsList;
    }

    public void setConditionsList(List<Conditions> conditionsList) {
        this.conditionsList = conditionsList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void addContition(String description, String icon){
        Conditions c = new Conditions();
        c.setDescription(description);
        c.setIcon(icon);
        conditionsList.add(c);
    }

    public static Weather create(String json) throws IOException{
        Weather w = new Weather();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader r=  Json.createReader(is);
            JsonObject o = r.readObject();
            logger.info(o.toString());
            w.city = o.getString("name");
            logger.info("city name > "+ w.city);
            JsonObject mainObj = o.getJsonObject("main");
            logger.info("mainObj "+mainObj.toString());
            w.temperature = mainObj.getJsonNumber("temp").toString();
            w.conditionsList = o.getJsonArray("weather").stream()
                                .map(v -> (JsonObject)v)
                                .map(v -> Conditions.createJson(v))
                                .toList();
        }
        return w;
    }

}
