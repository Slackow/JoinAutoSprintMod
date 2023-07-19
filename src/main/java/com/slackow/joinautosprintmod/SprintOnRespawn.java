package com.slackow.joinautosprintmod;

import net.fabricmc.loader.api.FabricLoader;

public class SprintOnRespawn {
    // some people may not want optional sprint functionality
    public static boolean val = true;
    static {
        // if mod jar has "no" and "respawn" in that order, with any case, and any number of letters between them
        FabricLoader.getInstance().getModContainer("joinautosprint").ifPresent(
        mod -> {
            try {
                val = mod.getOrigin().getPaths().stream()
                        .noneMatch(p -> p.getFileName().toString()
                                .matches(".*(?i)no.*respawn.*"));
            } catch (Exception ignored) {
                val = true;
            }
        });
    }
}
