package Day2Puzzle;

import java.io.*;
import java.util.HashMap;

public class Day2PuzzlePart1 {
    public static void main(String[] args) {

        BufferedReader reader = null;
        try {
            File day2PuzzleData = new File("src\\Day2Puzzle\\Day2PuzzleData.txt");
            reader = new BufferedReader(new FileReader(day2PuzzleData));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

            // Play Rock = 1
            // Play Paper = 2
            // Play Scissors = 3
            // Lose = 0
            // Draw = 3
            // Win = 6

            HashMap<String, Integer> outcomeToScore = new HashMap<>();
            // OppRock to Rock - Draw
            outcomeToScore.put("A X", 4);
            // OppRock to Paper - Win
            outcomeToScore.put("A Y", 8);
            // OppRock to Scissors - Lose
            outcomeToScore.put("A Z", 3);
            // OppPaper to Rock - Lose
            outcomeToScore.put("B X", 1);
            // OppPaper to Paper - Draw
            outcomeToScore.put("B Y", 5);
            // OppPaper to Scissors - Win
            outcomeToScore.put("B Z", 9);
            // OppScissors to Rock - Win
            outcomeToScore.put("C X", 7);
            // OppScissors to Paper - Lose
            outcomeToScore.put("C Y", 2);
            // OppScissors to Scissors - Draw
            outcomeToScore.put("C Z", 6);

            String line;
            int totalScore = 0;
        try {
            while ((line = reader.readLine()) != null){
                totalScore += outcomeToScore.get(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(totalScore);
    }
}
