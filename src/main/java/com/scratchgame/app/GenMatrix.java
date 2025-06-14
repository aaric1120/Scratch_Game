package com.scratchgame.app;

import java.util.HashMap;
import java.util.Random;

public class GenMatrix {
    public Rules config;
    public HashMap<String, String> final_matrix = new HashMap<>();

    public GenMatrix() {
        try {
            config = ConfigParser.parseConfig("config.json");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public void generateMatrixElements() {
        // Get the count of special symbols for random selection
        int bonus_symbol = config.getProbabilities().getBonusSymbols().getSymbols().size();
        for (int i = 0; i < config.getProbabilities().getStandardSymbols().size();i++) {
            // Get Curr row and column
            int row = config.getProbabilities().getStandardSymbols().get(i).getRow();
            int column = config.getProbabilities().getStandardSymbols().get(i).getColumn();

            int curr_size = config.getProbabilities().getStandardSymbols().get(i).getSymbols().size();
            int total_size = bonus_symbol + curr_size;
            String[] elements = new String[total_size];
            int[] weights = new int[total_size];
            int total_weight = 0;

            // populate the arrays with standard symbols
            int count = 0;
            for (String key: config.getProbabilities().getStandardSymbols().get(i).getSymbols().keySet()) {
                elements[count] = key;
                weights[count] = config.getProbabilities().getStandardSymbols().get(i).getSymbols().get(key);
                total_weight += weights[count];
                count++;
            }

            // populate the arrays with special symbols
            for (String key: config.getProbabilities().getBonusSymbols().getSymbols().keySet()) {
                elements[count] = key;
                weights[count] = config.getProbabilities().getBonusSymbols().getSymbols().get(key);
                total_weight += weights[count];
                count++;
            }


            // Generate a random value to match elements
            Random random = new Random();
            int randomNumber = random.nextInt(total_weight);

            int curr_index = 0;
            while(true) {
                if (randomNumber - weights[curr_index] > 0) {
                    randomNumber = randomNumber - weights[curr_index];
                    curr_index++;
                } else {
                    final_matrix.put(String.format("%s_%s", row, column),elements[curr_index]);
                    break;
                }
            }
        }

    }
}
