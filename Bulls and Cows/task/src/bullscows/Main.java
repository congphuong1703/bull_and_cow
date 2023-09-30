package bullscows;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String numberOfSecretRaw = "";
        String numberOfSymbolsRaw = "";
        Integer numberOfSecret;
        Integer numberOfSymbols;
        try {
            System.out.println("Please, enter the secret code's length:");
            numberOfSecretRaw = sc.next();
            numberOfSecret = Integer.parseInt(numberOfSecretRaw);

            System.out.println("Input the number of possible symbols in the code:");
            numberOfSymbolsRaw = sc.next();
            numberOfSymbols = Integer.parseInt(numberOfSymbolsRaw);

            if (numberOfSecret > 10 || numberOfSecret < 1) {
                System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
                return;
            }
            if (numberOfSymbols > 36 || numberOfSymbols < 1) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return;
            }
            if (numberOfSecret > numberOfSymbols) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", numberOfSecret, numberOfSymbols);
                return;
            }
        } catch (Exception e) {
            System.out.printf("Error: %s isn't a valid number", numberOfSecretRaw);
            return;
        }
        String secretCode = initSecretCode(numberOfSecret, numberOfSymbols);
        System.out.println("Okay, let's start a game!");
        play(secretCode);
    }

    private static String initSecretCode(int numberOfSecret, int numberOfSymbols) {

        // choose a Character random from this String

        StringBuilder alphaNumericString = new StringBuilder();
        for (int i = 0; i < numberOfSymbols; i++) {
            if (i > 9) {
                alphaNumericString.append(Character.toString((char) i + 87));
            } else {
                alphaNumericString.append(Character.toString((char) i + 48));
            }
        }
        String symbols = "";
        if (numberOfSymbols > 10) {
            symbols += "(0 - 9,a - " + alphaNumericString.charAt(alphaNumericString.length() - 1) + ")";
        } else {
            symbols += "(0 - " + alphaNumericString.charAt(alphaNumericString.length() - 1) + ")";
        }
        System.out.println("The secret is prepared: " + "*".repeat(numberOfSecret) + " " + symbols + ".");
        // create StringBuffer size of alphaNumericString
        StringBuilder secretCode = new StringBuilder(numberOfSecret);

        for (int i = 0; ; i++) {

            // generate a random number between
            // 0 to alphaNumericString variable length
            int index = (int) (alphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            if (secretCode.toString().indexOf(alphaNumericString.charAt(index)) == -1) {
                secretCode.append(alphaNumericString.charAt(index));
            }
            if (numberOfSecret == secretCode.length()) {
                break;
            }
        }

        return secretCode.toString();
    }
//
//    private static String initSecretCode(int numberOfSecret) {
////        System.out.println("The secret code is prepared: ****.");
//        StringBuilder secretCode = new StringBuilder();
//        while (true) {
//            long pseudoRandomNumber = System.nanoTime();
//            for (int i = 0; i < String.valueOf(pseudoRandomNumber).length(); i++) {
//                if (secretCode.toString().indexOf(String.valueOf(pseudoRandomNumber).charAt(i)) == -1) {
//                    secretCode.append(String.valueOf(pseudoRandomNumber).charAt(i));
//                }
//                if (secretCode.length() == numberOfSecret) {
//                    return secretCode.toString();
//                }
//            }
//        }
//
//    }

    private static void play(String secretCode) {
        boolean isWin = false;
        for (int i = 1; ; i++) {
            System.out.println("Turn " + i + ":");
            String guessCode = sc.next();
            isWin = handleLogic(secretCode, guessCode);
            if (isWin)
                break;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static boolean handleLogic(String secretCode, String guessCode) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guessCode.length(); i++) {
            int indexChar = secretCode.indexOf(guessCode.charAt(i));
            if (indexChar != -1) {
                if (secretCode.charAt(i) == guessCode.charAt(i)) {
                    bulls++;
                } else {
                    cows++;
                }
            }
        }
        String result = "";
        if (bulls > 0) {
            result += bulls + " bull(s)";
        }
        if (cows > 0) {
            if (result.length() > 0) {
                result += " and ";
            }
            result += cows + " cow(s)";
        }
        if (result.length() == 0) {
            result += "None.";
        }
        System.out.println("Grade: " + result + " The secret code is " + secretCode);
        return bulls == secretCode.length();
    }
}
