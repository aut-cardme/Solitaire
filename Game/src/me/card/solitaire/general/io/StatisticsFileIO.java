/*
 * Copyright Ashley Thew, Connor Teh-Hall 2019.
 * Project for AUT University, Program Design & Construction
 */

package me.card.solitaire.general.io;

import me.card.solitaire.general.statistics.PlayerStatistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class StatisticsFileIO {

    public static List<PlayerStatistics> load(String filePath) {
        List<PlayerStatistics> list = new ArrayList<>();
        File file = new File(new File("."), filePath);
        if (file.exists()) {
            try {
                BufferedReader br = Files.newBufferedReader(file.toPath());
                String input = "";
                while ((input = br.readLine()) != null) {
                    String[] args = input.split(", ");
                    String name = args[0];
                    int moves = Integer.parseInt(args[1]);
                    int seconds = Integer.parseInt(args[2]);
                    list.add(new PlayerStatistics(name, moves, seconds));
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void save(PlayerStatistics statistics, String filePath) {
        File file = new File(new File("."), filePath);
        try {
            if(!file.exists()){

            }
            BufferedWriter bw = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            bw.append(String.format("%s, %d, %d", statistics.getName(), statistics.getMoves(), statistics.getSeconds()));
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
