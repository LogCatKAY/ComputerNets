package com.bmstu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by alexey on 07.10.18.
 */
public class FileGenerator {

    public void generate(String fileName) {
        File file = new File(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(int i = 0; i < 127; i++) {
                bw.write(generateWord());
                if(i != 126) bw.write(" ");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private String generateWord() {
        int bound = 3 + (int)(Math.random() * 6);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < bound; i++) {
            sb.append((3 + (int)(Math.random() * 7)));
        }

        return sb.toString();
    }
}
