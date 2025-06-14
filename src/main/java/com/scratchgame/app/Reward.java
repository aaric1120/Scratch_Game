package com.scratchgame.app;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Reward {
    private GenMatrix matrix_obj;
    private int bet_amount;
    private HashMap<String, ArrayList<String>> winElements = new HashMap<>();

    public Reward(GenMatrix input_matrix, String betamount) {
        matrix_obj = input_matrix;
        bet_amount = Integer.parseInt(betamount);
    }

    public HashMap<String, ArrayList<String>> getWinElements() {
        return winElements;
    }

    public void checkWin(){
        for(String key:matrix_obj.getCount().keySet()) {
            if (matrix_obj.getCount().get(key) > 2) {
                winElements.put(key, new ArrayList<>());
                winElements.get(key).add(matrix_obj.getConfig().getWinCombinations().entrySet().stream().filter(entry -> entry.getValue().getCount() != null &&
                        entry.getValue().getCount() == 3).map(Map.Entry::getKey).collect(Collectors.toList()).get(0));
            }
        }
    }

    public Integer calculateReward(){
        int final_output = 0;
    }
}
