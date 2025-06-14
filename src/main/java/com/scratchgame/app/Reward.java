package com.scratchgame.app;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Reward {
    private GenMatrix matrix_obj;
    private int bet_amount;
    private int row;
    private int column;
    private HashMap<String, ArrayList<String>> winElements = new HashMap<>();

    public Reward(GenMatrix input_matrix, String betamount) {
        matrix_obj = input_matrix;
        bet_amount = Integer.parseInt(betamount);
        row = matrix_obj.getDimensions()[0];
        column = matrix_obj.getDimensions()[1];
    }

    public HashMap<String, ArrayList<String>> getWinElements() {
        return winElements;
    }

    public void checkWin(){
        // check for symbol counts
        for(String key:matrix_obj.getCount().keySet()) {
            if (matrix_obj.getCount().get(key) > 2) {
                winElements.put(key, new ArrayList<>());
                winElements.get(key).add(matrix_obj.getConfig().getWinCombinations().entrySet().stream().filter(entry -> entry.getValue().getCount() != null &&
                        entry.getValue().getCount() == matrix_obj.getCount().get(key)).map(Map.Entry::getKey).collect(Collectors.toList()).get(0));
            }
        }

        //check for straight lines
        // check for diagoanal lines
        checkDiagonal();
    }

    public void checkstraight() {

    }

    public void checkDiagonal() {
        // left to right
        String curr_sym = matrix_obj.getMatrix().get(String.format("%d_%d", 0,0));
        for (int i = 1; i <= column; i++) {
            if (!curr_sym.equals(matrix_obj.getMatrix().get(String.format("%d_%d",i ,i)))) {
                break;
            } else if (i == column) {
                winElements.get(curr_sym).add("same_symbols_diagonally_left_to_right");
            }

        }

        // right to left
        curr_sym = matrix_obj.getMatrix().get(String.format("%d_%d", 0,column));
        for (int i = 1; i <= row;i++) {
            if (!curr_sym.equals(matrix_obj.getMatrix().get(String.format("%d_%d",i ,column - i)))) {
                break;
            } else if (i == row) {
                winElements.get(curr_sym).add("same_symbols_diagonally_right_to_left");
            }
        }
    }

    public Integer calculateReward(){
        int reward = 0;
        if (winElements.size() == 0) {
            return reward;
        }
        // calculate the current win conditions for each symbol that matches
        for (String key: winElements.keySet()) {
            double curr_reward = bet_amount * matrix_obj.getConfig().getSymbols().get(key).getRewardMultiplier();
            for (String win: winElements.get(key)) {
                curr_reward *= matrix_obj.getConfig().getWinCombinations().get(win).getRewardMultiplier();
            }
            reward += curr_reward;
            System.out.println(reward);
        }

        // Add the bonus elements
        if (matrix_obj.getCurr_bonus() != null) {
            if (matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getRewardMultiplier() != null) {
                reward *= matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getRewardMultiplier();
                System.out.println(matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getRewardMultiplier());
            } else if (matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getExtra() != null) {
                reward += matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getExtra();
                System.out.println(matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getExtra());
            }
        }
        return reward;
    }
}
