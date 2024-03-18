/*
Name: Fengkai Liu, Yu Liu
School email: fliu24@u.rochester.edu, yliu307@u.rochester.edu
Assignment: Project 1
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Cipher {
  public static void main(String[] args) {
    runTests();

    Scanner scanner = new Scanner(System.in);

    System.out.println("Do you want to encrypt or decrypt (E/D): ");
    String operation1 = scanner.next();

    // Handling invalid input of operation
    while (!operation1.equalsIgnoreCase("E") && !operation1.equalsIgnoreCase("D")) {
      System.out.println("Invalid input. Please enter 'E' for encrypt or 'D' for decrypt:");
      operation1 = scanner.next();
    }

    String fileName;
    String longBinaryInput;

    while (true) {
      // Ask the user whether the file and the program are both in a src directory,
      // since locating a file needs its path, not only its name.
      // The user might be confused about this.
      System.out.println("Are your file and this program both in a src directory?");
      System.out.println("Enter Y for yes, N for no: ");
      String operation2 = scanner.next();

      while (!operation2.equalsIgnoreCase("Y") && !operation2.equalsIgnoreCase("N")) {
        System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no:");
        operation2 = scanner.next();
      }

      if (operation2.equalsIgnoreCase("Y")) {
        System.out.println(
            "Notice: you can enter the \".txt\" at the end of the file name but it's not necessary.");
        System.out.println("File name: ");
        fileName = "src/" + scanner.next();
        if (!fileName.endsWith(".txt")) {
          fileName += ".txt";
        }
      } else {
        System.out.println(
            "Notice: you can enter the \".txt\" at the end of the file name but it's not necessary.");
        System.out.println(
            "Notice: it supposed to work with relative path, but when it fails, you might try absolute path.");
        System.out.println("Enter the path and the file name: ");
        fileName = scanner.next();
        if (!fileName.endsWith(".txt")) {
          fileName += ".txt";
        }
      }

      // Try to get file content.
      // If there is any error reading the file, it will print an error message.
      try {
        longBinaryInput = getFileContent(fileName);
        break;
      } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
      }
    }

    // Ensures the secret key is 56 digit.
    System.out.println("Secret key: ");
    String inputKey = scanner.next();
    while (inputKey.length() != 56) {
      System.out.println("Please enter a 56 digit secret key: ");
      inputKey = scanner.next();
    }

    System.out.println(
        "Notice: you can enter the \".txt\" at the end of the file name but it's not necessary.");
    System.out.println("Output file name: ");
    String outputFileName = scanner.next();
    if (!outputFileName.endsWith(".txt")) {
      outputFileName += ".txt";
    }

    // Get the original file's parent directory for placing the output file.
    File file = new File(fileName).getParentFile();
    if (file == null) {
      System.out.println("Invalid file path");
      return;
    }

    File outputFile = new File(file, outputFileName);

    try {
      FileWriter writer = new FileWriter(outputFile);
      if (operation1.equalsIgnoreCase("E")) {
        // Encrypting
        writer.write(encryption(longBinaryInput, inputKey));
      } else {
        // Decrypting
        System.out.println("Enter 'T' for text data or 'B' for binary string data:");
        String dataType = scanner.next();
        if (dataType.equalsIgnoreCase("T")) {
          writer.write(
              new String(new BigInteger(decryption(longBinaryInput, inputKey), 2).toByteArray())
                  .replaceAll("\u0000", ""));
        } else if (dataType.equalsIgnoreCase("B")) {
          writer.write(decryption(longBinaryInput, inputKey));
        }
      }
      writer.close();

      // Notify the user whether it successfully operates or an error occurs.
      System.out.println(
          "File "
              + (operation1.equalsIgnoreCase("E") ? "encrypted" : "decrypted")
              + " and saved successfully.");
    } catch (IOException e) {
      System.out.println("An error occurred while writing the file: " + e.getMessage());
    }
  }

  public static void runTests() {
    System.out.println("Output for: encryption(all ones, all ones)");
    System.out.println(
        encryption(
            "1111111111111111111111111111111111111111111111111111111111111111",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: encryption(all zeros, all ones)");
    System.out.println(
        encryption(
            "0000000000000000000000000000000000000000000000000000000000000000",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: encryption(all zeros, all zeros)");
    System.out.println(
        encryption(
            "0000000000000000000000000000000000000000000000000000000000000000",
            "0000000000000000000000000000000000000000000000000000000000000000"));

    System.out.println("\nOutput for: encryption(block,all zeros), where:");
    String block = "1100110010000000000001110101111100010001100101111010001001001100";
    System.out.println("block = " + block);
    System.out.println(
        encryption(block, "0000000000000000000000000000000000000000000000000000000000000000"));

    System.out.println("\nOutput for: decryption(all ones, all ones)");
    System.out.println(
        decryption(
            "0101011010001110111001000111100001001110010001100110000011110101",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: decryption(all zeros, all ones)");
    System.out.println(
        decryption(
            "1100111010001000100011011010110110110010100101011001100000101000",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: decryption(all zeros, all zeros)");
    System.out.println(
        decryption(
            "1010100101110001000110111000011110110001101110011001111100001010",
            "0000000000000000000000000000000000000000000000000000000000000000"));

    System.out.println("\nOutput for: decryption(block,all ones), where:");
    String decryptBlock1 = "0101011010001110111001000111100001001110010001100110000011110101";
    System.out.println("block = " + decryptBlock1);
    System.out.println(
        decryption(
            decryptBlock1, "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: decryption(block,all zeros), where:");
    String decryptBlock2 = "0011000101110111011100100101001001001101011010100110011111010111";
    System.out.println("block = " + decryptBlock2);
    System.out.println(
        decryption(
            decryptBlock2, "0000000000000000000000000000000000000000000000000000000000000000"));
  }

  public static String getFileContent(String pathToFile) throws IOException {
    /*
     Using the inputted file path to locate the file.
     Then, it will read the contents of the file into a byte array
     Finally, using the java new String() method
     to convert the byte array into a String
    */

    return new String(Files.readAllBytes(Paths.get(pathToFile)));
  }

  public static String toBinaryString(String input) {
    // Divide the string into an array of characters
    char[] inputCharArray = input.toCharArray();
    StringBuilder binaryData = new StringBuilder();

    // Convert all the characters to binary, and add them to the binaryData string
    for (char c : inputCharArray) {
      StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(c));

      // Ensure that all characters will be converted into a binary number with 8 digits
      while (binaryString.length() < 8) {
        binaryString.insert(0, "0");
      }
      binaryData.append(binaryString);
    }

    return binaryData.toString();
  }

  public static String[] divideDataIntoBlocks(String binaryData) {
    int blockSize = 64;

    // Compute and ceiling the number of Blocks
    int numberOfBlocks = (int) Math.ceil((double) binaryData.length() / blockSize);
    String[] blocks = new String[numberOfBlocks];

    for (int i = 0; i < numberOfBlocks; i++) {
      // Compute the start index of every block we operate.
      int startIndex = i * blockSize;

      /*
      If the start index + block size is smaller, the blocks still need to be filled.
      If the binaryData.length() is smaller, it means that we are operating the last block.
      It ensures that we do not access the index out of bound.
      */
      int endIndex = Math.min(startIndex + blockSize, binaryData.length());

      // Divide data into blocks
      StringBuilder block = new StringBuilder(binaryData.substring(startIndex, endIndex));

      // Ensure every block has the size of 64, adding 0 to the end if its size is smaller
      while (block.length() < blockSize) {
        block.append("0");
      }
      blocks[i] = block.toString();
    }

    return blocks;
  }

  public static String[] keyScheduleTransform(String inputKey) {
    // Splitting the input key into half.
    String firstHalf = inputKey.substring(0, 28);
    String secondHalf = inputKey.substring(28, 56);

    // Creating an array to store the generated sub-keys.
    String[] roundKeys = new String[10];

    /*
     Doing the key schedule transformation:
     1. In each round, a left shift is applied separately to both halves.
     2. Concatenating first half and second half.
     3. Take the first 32 bits and form the round sub-key ki.
     4. Store the generated round sub-keys into an array.
    */
    for (int i = 0; i < roundKeys.length; i++) {
      firstHalf = shiftIt(firstHalf);
      secondHalf = shiftIt(secondHalf);
      String ki = firstHalf + secondHalf;
      roundKeys[i] = ki.substring(0, 32);
    }

    return roundKeys;
  }

  public static String functionF(String rightHalf, String subkey) {
    // Convert the input string into long and XOR the input string with the sub-key.
    StringBuilder XORedString = new StringBuilder(xorIt(rightHalf, subkey));

    // Ensure the XORed result length is 32.
    // If not, add 0 at the end of the result string
    while (XORedString.length() < 32) {
      XORedString.insert(0, "0");
    }

    String[] binarySegments = new String[4];

    // Divide the 32-bit blocks into segments of 8 bits.
    for (int i = 0; i < binarySegments.length; i++) {
      binarySegments[i] = XORedString.substring(i * 8, (i + 1) * 8);
    }

    StringBuilder dataAfterSubstitution = new StringBuilder();

    // Doing S-box substitution and concatenate the String back to 32 bits.
    for (String segment : binarySegments) {
      dataAfterSubstitution.append(substitutionS(segment));
    }

    return permuteIt(String.valueOf(dataAfterSubstitution));
  }

  public static String substitutionS(String binaryInput) {
    // Input existing S-Box for looking up
    String[][] S =
        new String[][] {
            {"01100011", "01111100", "01110111", "01111011", "11110010", "01101011", "01101111", "11000101", "00110000", "00000001", "01100111", "00101011", "11111110", "11010111", "10101011", "01110110"},
            {"11001010", "10000010", "11001001", "01111101", "11111010", "01011001", "01000111", "11110000", "10101101", "11010100", "10100010", "10101111", "10011100", "10100100", "01110010", "11000000"},
            {"10110111", "11111101", "10010011", "00100110", "00110110", "00111111", "11110111", "11001100", "00110100", "10100101", "11100101", "11110001", "01110001", "11011000", "00110001", "00010101"},
            {"00000100", "11000111", "00100011", "11000011", "00011000", "10010110", "00000101", "10011010", "00000111", "00010010", "10000000", "11100010", "11101011", "00100111", "10110010", "01110101"},
            {"00001001", "10000011", "00101100", "00011010", "00011011", "01101110", "01011010", "10100000", "01010010", "00111011", "11010110", "10110011", "00101001", "11100011", "00101111", "10000100"},
            {"01010011", "11010001", "00000000", "11101101", "00100000", "11111100", "10110001", "01011011", "01101010", "11001011", "10111110", "00111001", "01001010", "01001100", "01011000", "11001111"},
            {"11010000", "11101111", "10101010", "11111011", "01000011", "01001101", "00110011", "10000101", "01000101", "11111001", "00000010", "01111111", "01010000", "00111100", "10011111", "10101000"},
            {"01010001", "10100011", "01000000", "10001111", "10010010", "10011101", "00111000", "11110101", "10111100", "10110110", "11011010", "00100001", "00010000", "11111111", "11110011", "11010010"},
            {"11001101", "00001100", "00010011", "11101100", "01011111", "10010111", "01000100", "00010111", "11000100", "10100111", "01111110", "00111101", "01100100", "01011101", "00011001", "01110011"},
            {"01100000", "10000001", "01001111", "11011100", "00100010", "00101010", "10010000", "10001000", "01000110", "11101110", "10111000", "00010100", "11011110", "01011110", "00001011", "11011011"},
            {"11100000", "00110010", "00111010", "00001010", "01001001", "00000110", "00100100", "01011100", "11000010", "11010011", "10101100", "01100010", "10010001", "10010101", "11100100", "01111001"},
            {"11100111", "11001000", "00110111", "01101101", "10001101", "11010101", "01001110", "10101001", "01101100", "01010110", "11110100", "11101010", "01100101", "01111010", "10101110", "00001000"},
            {"10111010", "01111000", "00100101", "00101110", "00011100", "10100110", "10110100", "11000110", "11101000", "11011101", "01110100", "00011111", "01001011", "10111101", "10001011", "10001010"},
            {"01110000", "00111110", "10110101", "01100110", "01001000", "00000011", "11110110", "00001110", "01100001", "00110101", "01010111", "10111001", "10000110", "11000001", "00011101", "10011110"},
            {"11100001", "11111000", "10011000", "00010001", "01101001", "11011001", "10001110", "10010100", "10011011", "00011110", "10000111", "11101001", "11001110", "01010101", "00101000", "11011111"},
            {"10001100", "10100001", "10001001", "00001101", "10111111", "11100110", "01000010", "01101000", "01000001", "10011001", "00101101", "00001111", "10110000", "01010100", "10111011", "00010110"}
        };

    // The first 4 bits determine the row in the S-box,
    // The last 4 bits determine the column in the S-box
    int row = Integer.parseInt(binaryInput.substring(0, 4), 2);
    int column = Integer.parseInt(binaryInput.substring(4), 2);

    // Correspond the numbers with the position of S-box.
    return S[row][column];
  }

  public static String permuteIt(String binaryInput) {
    // Input existing permutation table
    int[] permutationTable = {
      16, 7, 20, 21, 29, 12, 28, 17,
      1, 15, 23, 26, 5, 18, 31, 10,
      2, 8, 24, 14, 32, 27, 3, 9,
      19, 13, 30, 6, 22, 11, 4, 25
    };

    StringBuilder permutedString = new StringBuilder();

    // Add every character correspond with permutation table's position in a new string.
    for (int j : permutationTable) {
      if (j - 1 < binaryInput.length()) {
        permutedString.append(binaryInput.charAt(j - 1));
      } else {
        permutedString.append("0");
      }
    }

    return permutedString.toString();
  }

  public static String xorIt(String binary1, String binary2) {
    StringBuilder result = new StringBuilder();

    // Ensuring two binary inputs have the same length.
    // If not, add 0 at the end of the shorter binary input.
    int maxLength = Math.max(binary1.length(), binary2.length());
    StringBuilder binary1StringBuilder = new StringBuilder(binary1);
    while (binary1StringBuilder.length() < maxLength) {
      binary1StringBuilder.insert(0, "0");
    }
    // Modify the original string using string-builder.
    binary1 = binary1StringBuilder.toString();
    StringBuilder binary2StringBuilder = new StringBuilder(binary2);
    while (binary2StringBuilder.length() < maxLength) {
      binary2StringBuilder.insert(0, "0");
    }
    // Modify the original string using string-builder.
    binary2 = binary2StringBuilder.toString();

    // Manually doing XOR (comparing evey character on the 2 input binary strings).
    // This is better than ^ operator because it can take larger input.
    for (int i = 0; i < maxLength; i++) {
      char bit1 = binary1.charAt(i);
      char bit2 = binary2.charAt(i);
      result.append((bit1 == bit2) ? '0' : '1');
    }
    return result.toString();
  }

  public static String shiftIt(String binaryInput) {
    // Manually left shifts the binary input string.
    // This is better than the << operator because it can prevent overflow
    return binaryInput.substring(1) + binaryInput.charAt(0);
  }

  public static String encryptBlock(String block, String inputKey) {
    // Generating round keys using key schedule transform.
    String[] roundKeys = keyScheduleTransform(inputKey);

    // Divide the block into half.
    String left = block.substring(0, block.length() / 2);
    String right = block.substring(block.length() / 2);

    // Encryption process
    for (String roundKey : roundKeys) {
      String result = functionF(right, roundKey);
      result = xorIt(left, result);
      left = right;
      right = result;
    }

    return left + right;
  }

  public static String decryptBlock(String block, String inputKey) {
    // Use the same key to generate round keys.
    String[] roundKeys = keyScheduleTransform(inputKey);

    // Divide the block into half.
    String left = block.substring(0, block.length() / 2);
    String right = block.substring(block.length() / 2);

    // Reverse process of encryption
    for (int i = roundKeys.length - 1; i >= 0; i--) {
      String tempLeft = left;
      left = right;
      right = tempLeft;

      left = xorIt(left, functionF(right, roundKeys[i]));
    }

    return left + right;
  }

  public static String encryption(String longBinaryInput, String inputKey) {
    // Convert the input into binary string
    // We consider the case that English characters may appear in the file
    String binaryString = toBinaryString(longBinaryInput);

    // Store the divided blocks into an array.
    String[] processedInput = divideDataIntoBlocks(binaryString);
    StringBuilder encryptedMessage = new StringBuilder();

    // Encrypt every block in the array and concatenate together.
    for (String s : processedInput) {
      encryptedMessage.append(encryptBlock(s, inputKey));
    }

    return encryptedMessage.toString();
  }

  public static String decryption(String longBinaryInput, String inputKey) {
    String[] processedInput = divideDataIntoBlocks(longBinaryInput);
    StringBuilder decryptedMessage = new StringBuilder();

    // Decrypt every block in the array and concatenate together.
    for (String s : processedInput) {
      decryptedMessage.append(decryptBlock(s, inputKey));
    }

    // Convert the binary string into the original string.
    return decryptedMessage.toString();
  }
}
