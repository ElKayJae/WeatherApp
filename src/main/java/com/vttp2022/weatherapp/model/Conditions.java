package com.vttp2022.weatherapp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.JsonObject;

public class Conditions {
    
    private static final Logger logger = LoggerFactory.getLogger(Conditions.class);
    private String description;
    private String icon;


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public static Conditions createJson(JsonObject o){
        Conditions c = new Conditions();
        c.description = "%S - %s".formatted(o.getString("main"), o.getString("description"));
        return c;
    }
}
