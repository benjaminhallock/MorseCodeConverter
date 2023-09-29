/**
 * @Author: Benjamin Hallock
 * @Title: CSC 3250 OOP HW2
 * @Notes: This inputs a txt file of morse code and letters and
 * Encapsulates the data into a PRIVATE container that
 * provides public methods to access the data.
 * I chose not to resize the array or populate null objects.
 */
import java.util.Scanner;
import java.io.*;
class MorseLetter {
    private char letter;
    private String code;
    public MorseLetter(char letter, String code) {this.letter = letter;this.code = code;}
    public MorseLetter() { this(' ', ""); }
    public char getLetter() {return letter;}
    public String getCode() {return code;}
    public void setLetter(char letter) {this.letter = letter;}
    public void setCode(String code) {this.code = code;}
}
class MorseCode {
    private MorseLetter[] morseList;
    public MorseCode() {morseList = new MorseLetter[26];}
    public MorseCode(MorseLetter[] morseList) {this.morseList = morseList;}
    public int size() {
        return morseList.length;
    }
    public boolean add(char letter, String code, int index) {
        if (Character.isLetter(letter) && code.matches("[.-]+") && index >= 0 && index < morseList.length) {
            letter = Character.toUpperCase(letter);
            morseList[index] = new MorseLetter(letter, code);
            return true;
        }
        return false;
}
    public char getLetter(String code) {
        int i = indexOfMorse(code);
        if (i == -1) {
            return ' ';
        } else {
            return morseList[i].getLetter();
        }
    }
    public String getCode(char letter) {
        int i = indexOfLetter(letter);
        if (i == -1) {
            return "";
        } else {
            return morseList[i].getCode();
        }
    }
    public boolean updateCharWithCode(char c, String code) {
        int i = indexOfLetter(c);
        if (i == -1) {
            return false;
        } else {
            morseList[i].setCode(code);
            morseList[i].setLetter(c);
            return true;
        }
    }
    public boolean updateCodeWithChar(String code, char c) {
        int i = indexOfMorse(code);
        if (i == -1) {
            return false;
        } else {
            morseList[i].setLetter(c);
            return true;
        }
    }
    public boolean deleteChar(char c) {
        int i = indexOfLetter(c);
        if (i == -1) {
            return false;
        } else {
            morseList[i] = new MorseLetter();
            return true;
        }
    }
    public boolean deleteCode(String code) {
        int i = indexOfMorse(code);
        if (i == -1) {
            return false;
        } else {
            morseList[i] = new MorseLetter();
            return true;
        }
    }
    public String textToMorse(String text) {
        StringBuilder morseCode = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                String code = getCode(c);
                if (!code.equals(""))
                    morseCode.append(code).append(" ");
            } else if (c == ' ') { //extra code for sentences
                morseCode.append("  ");
            } else {
                morseCode.append("\n Bummer, dude! Invalid character: ").append(c).append(". I'll type the rest of ur word below. \n");
            }
        }
        return morseCode.toString().trim();
    }
    public String morseToText(String morseCode) {
        StringBuilder text = new StringBuilder();
        String[] morseWords = morseCode.split("   "); // extra code for triple space sentences
        for (String morseWord : morseWords) {
            String[] morseLetters = morseWord.split(" ");
            for (String code : morseLetters) {
                char c = getLetter(code);
                if (c == ' ') {
                    text.append("\n Bummer, dude! Invalid Morse code: " + code + ". I'll type the rest of ur word below.\n");
                } else {
                    text.append(c);
                }
            }
            text.append(" ");
        }
        return text.toString().trim();
    }
    private int indexOfLetter(char letter) {
        if (Character.isUpperCase(letter)) {
            return letter - 'A';
        } else if (Character.isLowerCase(letter)) {
            return letter - 'a';
        } else {
            return -1;
        }
    }
    private int indexOfMorse(String morseCode) {
        int i = 0;
        boolean found = false;
        int index = -1;
        while (i < morseList.length && !found) {
            if (morseList[i].getCode().equals(morseCode)) {
                found = true;
                index = i;
            } else
                i++;
        }
        return index;
    }
}
public class MorseCodeConverter {
    public static void main(String[] args) throws FileNotFoundException {
        String defaultDirectory = System.getProperty("user.dir");
        String fileName = "morse.txt";
        String filePath = defaultDirectory + "/" + fileName;
        Scanner scanner = new Scanner(new File(filePath));
        int i = 0;
        MorseCode morseCode = new MorseCode();
        while (i < morseCode.size() && scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                char letter = parts[0].charAt(0);
                String code = parts[1];
                morseCode.add(letter, code, i);
                i++;
            } else
                System.out.println("Invalid line format: " + line);
        }
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What's the word, dude?");
            System.out.println("1. Convert text to Morse code");
            System.out.println("2. Convert Morse code to text");
            System.out.println("3. Bail out, dude!");
            System.out.print("Choose your wave (1, 2, or 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Drop a word dude (*CAPITAL* letters only): ");
                    String textToConvert = scanner.nextLine();
                    String morseResult = morseCode.textToMorse(textToConvert);
                    System.out.println("Cowabunga, dude! Morse code: " + morseResult);
                    break;
                case 2:
                    System.out.print("Hit me with some Morse code (spaces in-between letters): ");
                    String morseToConvert = scanner.nextLine();
                    String textResult = morseCode.morseToText(morseToConvert);
                    System.out.println("Here's the groove, dude: " + textResult);
                    break;
                case 3:
                    System.out.println("Later, dude! Catch you on the flip side.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Whoa, dude! That's not a valid choice. Try 1, 2 or 3.");
            }
        }
    }
}
