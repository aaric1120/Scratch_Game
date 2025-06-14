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
        GenMatrix temp_test = new GenMatrix();
        temp_test.generateMatrixElements();
        for (String key: temp_test.final_matrix.keySet()) {
            System.out.printf(temp_test.final_matrix.get(key) + " ");
        }
    }
}
