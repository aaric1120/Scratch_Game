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

        // Generate the matrix
        GenMatrix output_matrix = new GenMatrix(args[1]);
        output_matrix.generateMatrixElements();

        // Generate logic for calculating reward
        Reward output_reward = new Reward(output_matrix, args[3]);
        output_reward.checkWin();
        output_reward.calculateReward();

        // Final output
        output_reward.finalOutput();
    }
}
