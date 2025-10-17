package com.slackow.joinautosprintmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SprintOptions {
    public boolean autoSprintOnWorldJoin = true;
    public boolean autoSprintOnRespawn = true;
    public boolean changeDefaultToToggleSprint = true;
    private static final String CONFIG = "joinautosprint.json";
    private static SprintOptions options;
    private SprintOptions(){}
    public static SprintOptions load(Path configDir) {
        if (configDir == null) {
            configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config");
        }
        if (options != null) return options;
        if (Files.notExists(configDir.resolve(CONFIG))) {
            options = new SprintOptions();
            options.save(configDir);
            setSprinting();
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
        if (configDir == null) {
            configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config");
        }
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

    public boolean anyOn() {
        return autoSprintOnWorldJoin || autoSprintOnRespawn;
    }

    public static void setSprinting() {
        Minecraft instance = Minecraft.getInstance();
        var options = instance.options;
        if (options == null) return;
        var sprintKey = options.keySprint;
        if (!options.toggleSprint().get()){
            options.toggleSprint().set(true);
        }
        if (!sprintKey.isDown()) {
            sprintKey.setDown(true);
        }
        System.out.println("Set to be sprinting");
    }
}
