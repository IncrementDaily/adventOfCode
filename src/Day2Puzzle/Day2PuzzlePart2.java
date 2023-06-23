package Day2Puzzle;

import java.io.*;
import java.util.HashMap;

public class Day2PuzzlePart2 {
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
        // X = lose
        // Y = draw
        // Z = win

        HashMap<String, Integer> outcomeToScore = new HashMap<>();
        // OppRock to lose -> Scissors lose
        outcomeToScore.put("A X", 3);
        // OppRock to draw -> rock draw
        outcomeToScore.put("A Y", 4);
        // OppRock to win -> paper win
        outcomeToScore.put("A Z", 8);
        // OppPaper to lose -> rock lose
        outcomeToScore.put("B X", 1);
        // OppPaper to draw -> paper draw
        outcomeToScore.put("B Y", 5);
        // OppPaper to win -> scissors win
        outcomeToScore.put("B Z", 9);
        // OppScissors to lose -> paper lose
        outcomeToScore.put("C X", 2);
        // OppScissors to draw -> scissors draw
        outcomeToScore.put("C Y", 6);
        // OppScissors to win -> rock win
        outcomeToScore.put("C Z", 7);

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
