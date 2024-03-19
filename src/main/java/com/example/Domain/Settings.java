package com.example.Domain;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static Settings instance;

    private final String repoType;

    private final String repoCake;
    private final String repoOrder;


    private Settings(String repoType, String cake, String order) {
        this.repoType = repoType;
        this.repoCake = cake;
        this.repoOrder = order;
    }

    public String getRepoCake() {
        return repoCake;
    }

    public String getRepoOrder() {
        return repoOrder;
    }

    public String getRepoType() {
        return repoType;
    }

    private static Properties loadSettings() {
        try (FileReader fr = new FileReader("settings.properties.txt")) {
            Properties settings = new Properties();
            settings.load(fr);
            return settings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Settings getInstance() {
        Properties properties = loadSettings();
        instance = new Settings(properties.getProperty("repo_type"), properties.getProperty("Cake"), properties.getProperty("Order"));
        return instance;
    }
}
