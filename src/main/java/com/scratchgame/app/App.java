package com.scratchgame.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.*;
import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception{
        Rules config = ConfigParser.parseConfig("config.json");

        // Accessing configuration values
        System.out.println("Game grid: " + config.getColumns() + "x" + config.getRows());

        // Accessing symbol configurations
        config.getSymbols().forEach((symbol, symbolConfig) -> {
            System.out.println("Symbol: " + symbol);
            System.out.println("  Type: " + symbolConfig.getType());
            if (symbolConfig.getRewardMultiplier() != null) {
                System.out.println("  Multiplier: " + symbolConfig.getRewardMultiplier());
            }
        });

        // Accessing probabilities
        config.getProbabilities().getStandardSymbols().forEach(prob -> {
            System.out.printf("Position [%d,%d] probabilities:%n", prob.getColumn(), prob.getRow());
            prob.getSymbols().forEach((sym, weight) ->
                    System.out.printf("  %s: %d%n", sym, weight));
        });

        // Accessing win combinations
        config.getWinCombinations().forEach((name, combo) -> {
            System.out.println("Win combination: " + name);
            System.out.println("  Multiplier: " + combo.getRewardMultiplier());
            if (combo.getCoveredAreas() != null) {
                System.out.println("  Covered areas:");
                combo.getCoveredAreas().forEach(area ->
                        System.out.println("    " + area));
            }
        });
    }
}
