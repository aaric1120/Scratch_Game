package com.scratchgame.app;

import java.util.*;

public class App {
    public static void main(String[] args) throws Exception{
        GenMatrix temp_test = new GenMatrix(args[1]);
        temp_test.generateMatrixElements();
        HashMap<String, String> temp_matrix = temp_test.getMatrix();
        for (String key: temp_matrix.keySet()) {
            System.out.printf(temp_matrix.get(key) + " ");
        }
        System.out.println();

        HashMap<String, Integer> temp_count = temp_test.getCount();
        for (String key: temp_count.keySet()) {
            System.out.printf("%s - %d \n", key, temp_count.get(key));
        }

        System.out.println(temp_test.getCurr_bonus());
        Reward test_reward = new Reward(temp_test, args[3]);
        test_reward.checkWin();
        HashMap<String, ArrayList<String>> test_reward_mat = test_reward.getWinElements();
        for (String key: test_reward_mat.keySet()) {
            System.out.println(key + " " + test_reward_mat.get(key).get(0));
        }

        System.out.println(test_reward.calculateReward());
    }
}
