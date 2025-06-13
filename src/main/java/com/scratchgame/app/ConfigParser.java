package com.scratchgame.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.File;

public class ConfigParser {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static Rules parseConfig(String filePath) throws Exception {
        return mapper.readValue(new File(filePath), Rules.class);
    }

    // Helper method to convert "x:y" strings to coordinates
    public static int[] parseCoordinate(String coord) {
        String[] parts = coord.split(":");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }
}