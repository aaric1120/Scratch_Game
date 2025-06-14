package com.scratchgame.app;

import java.util.HashMap;
import java.util.Random;

public class GenMatrix {
    private Rules config;
    private HashMap<String, String> final_matrix = new HashMap<>();

    private HashMap<String, Integer> element_count = new HashMap<>();

    // Not specified in the document, but I assumed the bonus symbol can only appear once in the matrix
    private boolean bonusChecked = false;

    private String curr_bonus;

    public GenMatrix(String config_file) {
        try {
            config = ConfigParser.parseConfig(config_file);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public HashMap<String,String> getMatrix() {
        return final_matrix;
    }

    public HashMap<String, Integer> getCount() {
        return element_count;
    }

    public Rules getConfig() {
        return config;
    }

    public String getCurr_bonus(){
        return curr_bonus;
    }

    public void generateMatrixElements() {
        // Get the count of special symbols for random selection
        int bonus_symbol = config.getProbabilities().getBonusSymbols().getSymbols().size();
        for (int i = 0; i < config.getProbabilities().getStandardSymbols().size();i++) {
            // Get Curr row and column
            int row = config.getProbabilities().getStandardSymbols().get(i).getRow();
            int column = config.getProbabilities().getStandardSymbols().get(i).getColumn();

            // This is the array to use if the bonus symbol has already appeared once
            int curr_size = config.getProbabilities().getStandardSymbols().get(i).getSymbols().size();
            String[] elements_nb = new String[curr_size];
            int[] weights_nb = new int[curr_size];
            int total_weight_nb = 0;

            // This is the array to use if the bonus symbol has not been chosen yet
            int total_size = bonus_symbol + curr_size;
            String[] elements = new String[total_size];
            int[] weights = new int[total_size];
            int total_weight = 0;

            // populate the arrays with standard symbols
            int count = 0;
            for (String key: config.getProbabilities().getStandardSymbols().get(i).getSymbols().keySet()) {
                elements[count] = key;
                weights[count] = config.getProbabilities().getStandardSymbols().get(i).getSymbols().get(key);

                // for the none bonus array
                elements_nb[count] = elements[count];
                weights_nb[count] = weights[count];
                total_weight += weights[count];
                total_weight_nb = total_weight;

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
            int randomNumber;

            // choose which arrays to use
            String[] curr_ele = elements;
            int[] curr_weight = weights;

            if (!bonusChecked) {
                randomNumber = random.nextInt(total_weight);
            } else {
                curr_ele = elements_nb;
                curr_weight = weights_nb;
                randomNumber = random.nextInt(total_weight_nb);
            }

            // System.out.println(randomNumber);

            int curr_index = 0;
            while(true) {
                if (randomNumber - curr_weight[curr_index] > 0) {
                    randomNumber = randomNumber - curr_weight[curr_index];
                    curr_index++;
                } else {
                    final_matrix.put(String.format("%s_%s", row, column),curr_ele[curr_index]);
                    if (config.getProbabilities().getBonusSymbols().getSymbols().containsKey(curr_ele[curr_index])) {
                        bonusChecked = true;
                        curr_bonus = curr_ele[curr_index];
                    } else {
                        // Keep track of how many times non bonus symbol appears
                        if (element_count.containsKey(curr_ele[curr_index])) {
                            element_count.put(curr_ele[curr_index], element_count.get(curr_ele[curr_index]) + 1);
                        } else {
                            element_count.put(curr_ele[curr_index], 1);
                        }
                    }
                    break;
                }
            }
        }
    }
}
