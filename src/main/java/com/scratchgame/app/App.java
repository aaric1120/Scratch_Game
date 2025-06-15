package com.scratchgame.app;

import java.io.File;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception{

        // Check the input parameters are valid
        if (args.length != 4 || !args[0].equals("--config") || !args[2].equals("--betting-amount")) {
            System.out.println("The parameters provided did not match the requirements.");
            System.out.println("Please follow the following example: java -jar {full path}/{jar file name} --config {full path}/{config file} --betting-amount {bet amount}");
            System.exit(1);
        }

        // Check if config file exists and is file
        if (!new File(args[1]).exists() || new File(args[1]).isDirectory()) {
            System.out.println("The config file provided is not a valid config file, please provide valid file path and file name");
            System.exit(1);
        }


        // Validate the betting amount is actually an integer
        try {
            Integer.parseInt(args[3]);
        } catch (Exception e) {
            System.out.println("The betting amount you entered was not valid format, please only use integers as input");
            System.exit(1);
        }


        GenMatrix temp_test = new GenMatrix(args[1]);
        temp_test.generateMatrixElements();
        HashMap<String, String> temp_matrix = temp_test.getMatrix();

        for (int i = 0;i< 3;i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%s ",temp_matrix.get(String.format("%d:%d",i,j)));
            }
            System.out.println();
        }

        Reward test_reward = new Reward(temp_test, args[3]);
        test_reward.checkWin();
        HashMap<String, ArrayList<String>> test_reward_mat = test_reward.getWinElements();
        for (String key: test_reward_mat.keySet()) {
            System.out.printf(key + " : ");
            test_reward_mat.get(key).stream().forEach(bonus -> {
                System.out.printf(bonus + " ");
            });
            System.out.println();

        }

        test_reward.calculateReward();

        System.out.println(test_reward.getReward());
    }
}
