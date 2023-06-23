package Day25Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day25PuzzlePart1 {
    public static BufferedReader getDataFileReader(String filepath){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }
    public static int getDataFileLineCount(String filePath){
        int lineCount = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.readLine() != null){
                lineCount++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }
    public static String readDataLine(BufferedReader reader){
        String dataLine = null;
        try {
            dataLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataLine;
    }
    public static void closeDataFile(BufferedReader reader){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long decodeLongFromSNAFU(String snafu){
        char[] numericalPlaces = snafu.toCharArray();
        long currentExponent = snafu.length()-1;
        long fiveBaseExponent;
        long term;
        List<Long> listOfTerms = new ArrayList<>();

        for (char c : numericalPlaces){
            long termCoefficient = 0;
            try{
                termCoefficient = Long.parseLong(String.valueOf(c));
            }catch(NumberFormatException e){
                if (String.valueOf(c).equals("-")){
                    termCoefficient = -1;
                }
                if (String.valueOf(c).equals("=")){
                    termCoefficient = -2;
                }
            }
            fiveBaseExponent = (long) Math.pow(5,currentExponent);
            term = termCoefficient * fiveBaseExponent;
            listOfTerms.add(term);
            currentExponent--;
        }

        long finalResult = 0;
        for (Long termyMcTermFace : listOfTerms){
            finalResult += termyMcTermFace;
        }
        return finalResult;
    }

  /*  private static long findMinimumOvershot(long numBeingEncoded){
        // Find minimum value of x for which the expression 5^x > numBeingEncoded is true
        long guess1Exponent = 0;
        while (Math.pow(5,guess1Exponent) < numBeingEncoded ){
            guess1Exponent++;
        }
        long overshotGuess1 = (long) Math.pow(5,guess1Exponent);

        // Find minimum value of x for which the expression (5^x)*2 > numBeingEncoded is true
        long guess2Exponent = 0;
        while (Math.pow(5,guess2Exponent)*2 < numBeingEncoded){
            guess2Exponent++;
        }
        long overshotGuess2 = (long) Math.pow(5,guess2Exponent)*2;

        return Math.min(overshotGuess1, overshotGuess2);
    }
    private static String findFirstDigit(long numBeingEncoded){
        // Find minimum value of x for which the expression 5^x > numBeingEncoded is true
        long guess1Exponent = 0;
        while (Math.pow(5,guess1Exponent) < numBeingEncoded ){
            guess1Exponent++;
        }
        long overshotGuess1 = (long) Math.pow(5,guess1Exponent);

        // Find minimum value of x for which the expression (5^x)*2 > numBeingEncoded is true
        long guess2Exponent = 0;
        while (Math.pow(5,guess2Exponent)*2 < numBeingEncoded){
            guess2Exponent++;
        }
        long overshotGuess2 = (long) Math.pow(5,guess2Exponent)*2;

        // Find correct overshot
        long overshot = Math.min(overshotGuess1, overshotGuess2);

        // Find digit for this place
        long place  = (overshot == overshotGuess1) ? 1 : 2;

        return String.valueOf(place);
    }
    private static long findMaxPlaceExponent(long numBeingEncoded){
        // Find minimum value of x for which the expression 5^x > numBeingEncoded is true
        long guess1Exponent = 0;
        while (Math.pow(5,guess1Exponent) < numBeingEncoded ){
            guess1Exponent++;
        }
        long overshotGuess1 = (long) Math.pow(5,guess1Exponent);

        // Find minimum value of x for which the expression (5^x)*2 > numBeingEncoded is true
        long guess2Exponent = 0;
        while (Math.pow(5,guess2Exponent)*2 < numBeingEncoded){
            guess2Exponent++;
        }
        long overshotGuess2 = (long) Math.pow(5,guess2Exponent)*2;

        return Math.min(guess1Exponent, guess2Exponent);
    }
    private static long findMaxPlace(long numBeingEncoded){

        // Find minimum value of x for which the expression 5^x > numBeingEncoded is true
        long guess1Exponent = 0;
        while (Math.pow(5,guess1Exponent) < numBeingEncoded){
            guess1Exponent++;
        }
        long overshotGuess1 = (long) Math.pow(5,guess1Exponent);

        // Find minimum value of x for which the expression (5^x)*2 > numBeingEncoded is true
        long guess2Exponent = 0;
        while (Math.pow(5,guess2Exponent)*2 < numBeingEncoded){
            guess2Exponent++;
        }
        long overshotGuess2 = (long) Math.pow(5,guess2Exponent)*2;

        long choice = Math.min(guess1Exponent, guess2Exponent);

        // Find place
        if (choice == overshotGuess2){
            return 2;
        }else{
            return 1;
        }
    }
    public static long findCurrentError(long minimumOvershot, long numBeingEncoded){
        return minimumOvershot - numBeingEncoded;
    }
    private static String buildSNAFUScaffold(long highestExponent){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<=highestExponent;i++){
            sb.append("0");
        }
        return sb.toString();
    }
    public static String encodeSNAFUfromLong(long value){
        String snafu = "";
        long minimumOvershot;
        long maxPlace;
        long currentError;

        while (decodeLongFromSNAFU(snafu) != (value)) {
            minimumOvershot = findMinimumOvershot(value);
            maxPlace = findMaxPlace(value);
            currentError = findCurrentError(minimumOvershot, value);

        }



        return snafu;
    }*/


    public static String snafuFromNumber(long number) {
        long remainder = number;
        StringBuilder out = new StringBuilder();
        while (remainder > 0) {
            char glyph = ' ';
            switch ((int)(remainder % 5)) {
                case 0:
                    glyph = '0';
                    break;
                case 1:
                    glyph = '1';
                    break;
                case 2:
                    glyph = '2';
                    break;
                case 3:
                    glyph = '=';
                    break;
                case 4:
                    glyph = '-';
                    break;
                default:
                    throw new RuntimeException("Unreachable");
            }
            out.append(glyph);
            remainder += 2;  // This works for some reason.
            remainder /= 5;
        }
        return out.reverse().toString();
    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day25Puzzle/Day25PuzzleData.txt";
        String testDataFilePath = "src/Day25Puzzle/TestData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);
        BufferedReader testDataFileReader = getDataFileReader(testDataFilePath);

        String line;
        long totalFuelNeeded = 0;
        while ((line = readDataLine(dataFileReader)) != null) {
            StringBuilder sb = new StringBuilder();

            long fuelNeededByBalloon = decodeLongFromSNAFU(line);
            snafuFromNumber(fuelNeededByBalloon);

            totalFuelNeeded += fuelNeededByBalloon;
        }

        System.out.println(totalFuelNeeded);
        System.out.println(snafuFromNumber(totalFuelNeeded));

    }
}
