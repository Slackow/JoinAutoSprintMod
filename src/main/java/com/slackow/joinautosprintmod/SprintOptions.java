package com.slackow.joinautosprintmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SprintOptions {
    public boolean autoSprintOnWorldJoin = true;
    public boolean autoSprintOnRespawn = true;
    private static final String CONFIG = "joinautosprintmod.json";
    private static SprintOptions options;
    private SprintOptions(){}
    public static SprintOptions load(Path configDir) {
        if (options != null) return options;
        if (Files.notExists(configDir.resolve(CONFIG))) {
            options = new SprintOptions();
            options.save(configDir);
            return options;
        }
        try {
            options = new Gson().fromJson(Files.newBufferedReader(configDir.resolve(CONFIG)), SprintOptions.class);
        } catch (IOException e) {
            System.err.println("Failed to load config file: " + CONFIG);
            e.printStackTrace(System.err);
            options = new SprintOptions();
        }
        return options;
    }
    public void save(Path configDir) {
        try {
            Files.createDirectories(configDir);
        } catch (IOException ignored) {
        }
        try {
            Files.writeString(configDir.resolve(CONFIG), new GsonBuilder().setPrettyPrinting().create().toJson(options, SprintOptions.class));
        } catch (IOException e) {
            System.err.println("Failed to save options to " + configDir.resolve(CONFIG));
            e.printStackTrace(System.err);
        }
    }
}
