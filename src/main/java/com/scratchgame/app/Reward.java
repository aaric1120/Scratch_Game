package com.scratchgame.app;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reward {
    private GenMatrix matrix_obj;
    private int bet_amount;
    private int reward = 0;
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

    public Integer getReward() {return reward;}

    public void checkWin(){
        // check for symbol counts
        checkCount();

        //check for straight lines
        checkStraight();

        // check for diagoanal lines
        checkDiagonal();
    }

    public void finalOutput() {
        // Showt the matrix
        System.out.println("matrix: ");
        for (int i = 0;i <= row;i++) {
            for (int j = 0; j <= column; j++) {
                System.out.printf("%s ",matrix_obj.getMatrix().get(String.format("%d:%d",i,j)));
            }
            System.out.println();
        }
        System.out.println();

        // show the reward
        System.out.printf("reward:%d\n\n",reward);

        // Show winning combinations and bonus symbol
        if (reward != 0) {
            System.out.println("applied_winning_combinations:");
            for (String key: winElements.keySet()) {
                System.out.printf("%s:",key);
                winElements.get(key).forEach(cond -> {
                    System.out.printf("%s ", cond);
                });
                System.out.println();
            }
            System.out.println();
            if (matrix_obj.getCurr_bonus() != null && !matrix_obj.getCurr_bonus().equals("MISS")) {
                System.out.printf("applied_bonus_symbol:%s\n\n", matrix_obj.getCurr_bonus());
            }
        }
    }

    public void checkCount() {
        for(String key:matrix_obj.getCount().keySet()) {
            List<String> check_cond = matrix_obj.getConfig().getWinCombinations().entrySet().stream().filter(entry -> entry.getValue().getCount() != null &&
                    entry.getValue().getCount() == matrix_obj.getCount().get(key)).map(Map.Entry::getKey).toList();
            if (check_cond.size() != 0) {
                winElements.put(key, new ArrayList<>());
                winElements.get(key).add(check_cond.get(0));
            }
        }
    }

    public void checkStraight() {
        // check Horizontally
        for(List<String> coord: matrix_obj.getConfig().getWinCombinations().get("same_symbols_horizontally").getCoveredAreas()) {
            if (winElements.containsKey(matrix_obj.getMatrix().get(coord.get(0)))) {
                if (coord.size() > 1) {
                    String curr_sym = matrix_obj.getMatrix().get(coord.get(0));
                    for (int i = 1; i < coord.size(); i++) {
                        if (!curr_sym.equals(matrix_obj.getMatrix().get(coord.get(i)))) {
                            break;
                        } else if (i == coord.size() - 1) {
                            winElements.get(curr_sym).add("same_symbols_horizontally");
                        }
                    }
                } else if (coord.size() == 1) {
                    winElements.get(coord.get(0)).add("same_symbols_horizontally");
                }

            }
        }

        // Check vertical elements
        for(List<String> coord: matrix_obj.getConfig().getWinCombinations().get("same_symbols_vertically").getCoveredAreas()) {
            if (winElements.containsKey(matrix_obj.getMatrix().get(coord.get(0)))) {
                if (coord.size() > 1) {
                    String curr_sym = matrix_obj.getMatrix().get(coord.get(0));
                    for (int i = 1; i < coord.size(); i++) {
                        if (!curr_sym.equals(matrix_obj.getMatrix().get(coord.get(i)))) {
                            break;
                        } else if (i == coord.size() - 1) {
                            winElements.get(curr_sym).add("same_symbols_vertically");
                        }
                    }
                } else if (coord.size() == 1) {
                    winElements.get(coord.get(0)).add("same_symbols_vertically");
                }

            }
        }
    }

    public void checkDiagonal() {
        // left to right
        for(List<String> coord: matrix_obj.getConfig().getWinCombinations().get("same_symbols_diagonally_left_to_right").getCoveredAreas()) {
            if (winElements.containsKey(matrix_obj.getMatrix().get(coord.get(0)))) {
                if (coord.size() > 1) {
                    String curr_sym = matrix_obj.getMatrix().get(coord.get(0));
                    for (int i = 1; i < coord.size(); i++) {
                        if (!curr_sym.equals(matrix_obj.getMatrix().get(coord.get(i)))) {
                            break;
                        } else if (i == coord.size() - 1) {
                            winElements.get(curr_sym).add("same_symbols_diagonally_left_to_right");
                        }
                    }
                } else if (coord.size() == 1) {
                    winElements.get(coord.get(0)).add("same_symbols_diagonally_left_to_right");
                }

            }
        }

        // right to left
        for(List<String> coord: matrix_obj.getConfig().getWinCombinations().get("same_symbols_diagonally_right_to_left").getCoveredAreas()) {
            if (winElements.containsKey(matrix_obj.getMatrix().get(coord.get(0)))) {
                if (coord.size() > 1) {
                    String curr_sym = matrix_obj.getMatrix().get(coord.get(0));
                    for (int i = 1; i < coord.size(); i++) {
                        if (!curr_sym.equals(matrix_obj.getMatrix().get(coord.get(i)))) {
                            break;
                        } else if (i == coord.size() - 1) {
                            winElements.get(curr_sym).add("same_symbols_diagonally_right_to_left");
                        }
                    }
                } else if (coord.size() == 1) {
                    winElements.get(coord.get(0)).add("same_symbols_diagonally_right_to_left");
                }

            }
        }
    }

    public void calculateReward(){
        if (winElements.size() != 0) {
            // calculate the current win conditions for each symbol that matches
            for (String key: winElements.keySet()) {
                double curr_reward = bet_amount * matrix_obj.getConfig().getSymbols().get(key).getRewardMultiplier();
                for (String win: winElements.get(key)) {
                    curr_reward *= matrix_obj.getConfig().getWinCombinations().get(win).getRewardMultiplier();
                }
                reward += curr_reward;
            }

            // Add the bonus elements
            if (matrix_obj.getCurr_bonus() != null) {
                if (matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getRewardMultiplier() != null) {
                    reward *= matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getRewardMultiplier();
                } else if (matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getExtra() != null) {
                    reward += matrix_obj.getConfig().getSymbols().get(matrix_obj.getCurr_bonus()).getExtra();
                }
            }
        }
    }
}
