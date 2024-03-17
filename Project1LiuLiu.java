/*
Name: Fengkai Liu, Yu Liu
School email: fliu24@u.rochester.edu, yliu30
Assignment: Project 1
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Project1LiuLiu {
  public static void main(String[] args) {
    runTests();

    Scanner scanner = new Scanner(System.in);

    System.out.println("Do you want to encrypt or decrypt (E/D): ");
    if (scanner.next().equalsIgnoreCase("E")) {
      System.out.println("File name: ");
      try {
        String fileName = "src/" + scanner.next();
        String longBinaryInput = getFileContent(fileName);
        System.out.println("Secret key: ");
        String inputKey = scanner.next();

        System.out.println("Output file: ");
        String outputFileName = scanner.next();

        File file = new File(fileName).getParentFile();
        if (file != null) {
          File outputFile = new File(file, outputFileName);
          FileWriter writer = new FileWriter(outputFile);

          writer.write(encryption(longBinaryInput, inputKey));
          writer.close();

          System.out.println("File encrypted and saved successfully");
        } else {
          System.out.println("Invalid file path");
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else if (scanner.next().equalsIgnoreCase("D")) {
      System.out.println("File name: ");
      try {
        String fileName = "src/" + scanner.next();
        String longBinaryInput = getFileContent(fileName);
        System.out.println("Secret key: ");
        String inputKey = scanner.next();

        System.out.println("Output file: ");
        String outputFileName = scanner.next();

        File file = new File(fileName).getParentFile();
        if (file != null) {
          File outputFile = new File(file, outputFileName);
          FileWriter writer = new FileWriter(outputFile);

          writer.write(decryption(longBinaryInput, inputKey));
          writer.close();

          System.out.println("File decrypted and saved successfully");
        } else {
          System.out.println("Invalid file path");
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static void runTests() {
    System.out.println("Output for: encryption(all ones, all ones)");
    System.out.println(
        encryptBlock(
            "1111111111111111111111111111111111111111111111111111111111111111",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: encryption(all zeros, all ones)");
    System.out.println(
        encryptBlock(
            "0000000000000000000000000000000000000000000000000000000000000000",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: encryption(all zeros, all zeros)");
    System.out.println(
        encryptBlock(
            "0000000000000000000000000000000000000000000000000000000000000000",
            "0000000000000000000000000000000000000000000000000000000000000000"));

    System.out.println("\nOutput for: encryption(block,all zeros), where:");
    String block = "1100110010000000000001110101111100010001100101111010001001001100";
    System.out.println("block = " + block);
    System.out.println(
        encryptBlock(block, "0000000000000000000000000000000000000000000000000000000000000000"));

    System.out.println("\nOutput for: decryption(all ones, all ones)");
    System.out.println(
        decryptBlock(
            "0101011010001110111001000111100001001110010001100110000011110101",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: decryption(all zeros, all ones)");
    System.out.println(
        decryptBlock(
            "1100111010001000100011011010110110110010100101011001100000101000",
            "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: decryption(all zeros, all zeros)");
    System.out.println(
        decryptBlock(
            "1010100101110001000110111000011110110001101110011001111100001010",
            "0000000000000000000000000000000000000000000000000000000000000000"));

    System.out.println("\nOutput for: decryption(block,all ones), where:");
    String decryptBlock1 = "0101011010001110111001000111100001001110010001100110000011110101";
    System.out.println("block = " + decryptBlock1);
    System.out.println(
        decryptBlock(
            decryptBlock1, "1111111111111111111111111111111111111111111111111111111111111111"));

    System.out.println("\nOutput for: decryption(block,all zeros), where:");
    String decryptBlock2 = "0011000101110111011100100101001001001101011010100110011111010111";
    System.out.println("block = " + decryptBlock2);
    System.out.println(
        decryptBlock(
            decryptBlock2, "0000000000000000000000000000000000000000000000000000000000000000"));
  }

  public static String getFileContent(String pathToFile) throws IOException {
    return new String(Files.readAllBytes(Paths.get(pathToFile)));
  }

  public static String[] divideDataIntoBlocks(String binaryData) {
    int blockSize = 64;

    // Compute and ceiling the number of Blocks
    int numberOfBlocks = (int) Math.ceil((double) binaryData.length() / blockSize);
    String[] blocks = new String[numberOfBlocks];

    for (int i = 0; i < numberOfBlocks; i++) {
      // Compute the start index of every block we operate.
      int startIndex = i * blockSize;

      /* If the start index + block size is smaller, the blocks still need to be filled.
      If the binaryData.length() is smaller, it means that we are operating the last block.
      It ensures that we do not access the index out of bound.*/
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
    String C = inputKey.substring(0, 28);
    String D = inputKey.substring(28, 56);
    //store the generated subkeys
    String[] roundKeys = new String[10];

    for (int i = 0; i < roundKeys.length; i++) {
      C = shiftIt(C);
      D = shiftIt(D);
      String ki = C + D;
      roundKeys[i] = ki.substring(0, 32);
    }

    return roundKeys;
  }

  public static String functionF(String rightHalf, String subkey) {
    //convert to long and go through XOR operation
    StringBuilder XORedString =
        new StringBuilder(
            Long.toBinaryString(Long.parseLong(rightHalf, 2) ^ Long.parseLong(subkey, 2)));
    //make sure its length is 32
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
    //input S-Box lookup
    String[][] S =
        new String[][] {
          {
            "01100011",
            "01111100",
            "01110111",
            "01111011",
            "11110010",
            "01101011",
            "01101111",
            "11000101",
            "00110000",
            "00000001",
            "01100111",
            "00101011",
            "11111110",
            "11010111",
            "10101011",
            "01110110"
          },
          {
            "11001010",
            "10000010",
            "11001001",
            "01111101",
            "11111010",
            "01011001",
            "01000111",
            "11110000",
            "10101101",
            "11010100",
            "10100010",
            "10101111",
            "10011100",
            "10100100",
            "01110010",
            "11000000"
          },
          {
            "10110111",
            "11111101",
            "10010011",
            "00100110",
            "00110110",
            "00111111",
            "11110111",
            "11001100",
            "00110100",
            "10100101",
            "11100101",
            "11110001",
            "01110001",
            "11011000",
            "00110001",
            "00010101"
          },
          {
            "00000100",
            "11000111",
            "00100011",
            "11000011",
            "00011000",
            "10010110",
            "00000101",
            "10011010",
            "00000111",
            "00010010",
            "10000000",
            "11100010",
            "11101011",
            "00100111",
            "10110010",
            "01110101"
          },
          {
            "00001001",
            "10000011",
            "00101100",
            "00011010",
            "00011011",
            "01101110",
            "01011010",
            "10100000",
            "01010010",
            "00111011",
            "11010110",
            "10110011",
            "00101001",
            "11100011",
            "00101111",
            "10000100"
          },
          {
            "01010011",
            "11010001",
            "00000000",
            "11101101",
            "00100000",
            "11111100",
            "10110001",
            "01011011",
            "01101010",
            "11001011",
            "10111110",
            "00111001",
            "01001010",
            "01001100",
            "01011000",
            "11001111"
          },
          {
            "11010000",
            "11101111",
            "10101010",
            "11111011",
            "01000011",
            "01001101",
            "00110011",
            "10000101",
            "01000101",
            "11111001",
            "00000010",
            "01111111",
            "01010000",
            "00111100",
            "10011111",
            "10101000"
          },
          {
            "01010001",
            "10100011",
            "01000000",
            "10001111",
            "10010010",
            "10011101",
            "00111000",
            "11110101",
            "10111100",
            "10110110",
            "11011010",
            "00100001",
            "00010000",
            "11111111",
            "11110011",
            "11010010"
          },
          {
            "11001101",
            "00001100",
            "00010011",
            "11101100",
            "01011111",
            "10010111",
            "01000100",
            "00010111",
            "11000100",
            "10100111",
            "01111110",
            "00111101",
            "01100100",
            "01011101",
            "00011001",
            "01110011"
          },
          {
            "01100000",
            "10000001",
            "01001111",
            "11011100",
            "00100010",
            "00101010",
            "10010000",
            "10001000",
            "01000110",
            "11101110",
            "10111000",
            "00010100",
            "11011110",
            "01011110",
            "00001011",
            "11011011"
          },
          {
            "11100000",
            "00110010",
            "00111010",
            "00001010",
            "01001001",
            "00000110",
            "00100100",
            "01011100",
            "11000010",
            "11010011",
            "10101100",
            "01100010",
            "10010001",
            "10010101",
            "11100100",
            "01111001"
          },
          {
            "11100111",
            "11001000",
            "00110111",
            "01101101",
            "10001101",
            "11010101",
            "01001110",
            "10101001",
            "01101100",
            "01010110",
            "11110100",
            "11101010",
            "01100101",
            "01111010",
            "10101110",
            "00001000"
          },
          {
            "10111010",
            "01111000",
            "00100101",
            "00101110",
            "00011100",
            "10100110",
            "10110100",
            "11000110",
            "11101000",
            "11011101",
            "01110100",
            "00011111",
            "01001011",
            "10111101",
            "10001011",
            "10001010"
          },
          {
            "01110000",
            "00111110",
            "10110101",
            "01100110",
            "01001000",
            "00000011",
            "11110110",
            "00001110",
            "01100001",
            "00110101",
            "01010111",
            "10111001",
            "10000110",
            "11000001",
            "00011101",
            "10011110"
          },
          {
            "11100001",
            "11111000",
            "10011000",
            "00010001",
            "01101001",
            "11011001",
            "10001110",
            "10010100",
            "10011011",
            "00011110",
            "10000111",
            "11101001",
            "11001110",
            "01010101",
            "00101000",
            "11011111"
          },
          {
            "10001100",
            "10100001",
            "10001001",
            "00001101",
            "10111111",
            "11100110",
            "01000010",
            "01101000",
            "01000001",
            "10011001",
            "00101101",
            "00001111",
            "10110000",
            "01010100",
            "10111011",
            "00010110"
          }
        };
    //first 4 bits determine row, last 4 bits determine column
    int row = Integer.parseInt(binaryInput.substring(0, 4), 2); 
    int column = Integer.parseInt(binaryInput.substring(4), 2); 
    
    // Correspond the numbers with the position of S-box.
    return S[row][column];
  }

  public static String permuteIt(String binaryInput) {
    //input the permutation table
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
    // make the length of two string be the same
    int maxLength = Math.max(binary1.length(), binary2.length());
    StringBuilder binary1Builder = new StringBuilder(binary1);
    while (binary1Builder.length() < maxLength) {
      binary1Builder.insert(0, "0");
    }
    binary1 = binary1Builder.toString();
    StringBuilder binary2Builder = new StringBuilder(binary2);
    while (binary2Builder.length() < maxLength) {
      binary2Builder.insert(0, "0");
    }
    binary2 = binary2Builder.toString();

    // use XOR operation on each bit
    for (int i = 0; i < maxLength; i++) {
      char bit1 = binary1.charAt(i);
      char bit2 = binary2.charAt(i);
      //if the same, return 0, and vise versa
      result.append((bit1 == bit2) ? '0' : '1');
    }
    return result.toString();
  }

  public static String shiftIt(String binaryInput) {
    return binaryInput.substring(1) + binaryInput.charAt(0);
  }

  public static String encryptBlock(String block, String inputKey) {
    String[] roundKeys = keyScheduleTransform(inputKey);

    String left = block.substring(0, block.length() / 2);
    String right = block.substring(block.length() / 2);

    for (String roundKey : roundKeys) {
      String result = functionF(right, roundKey);
      result = xorIt(left, result);
      left = right;
      right = result;
    }

    return left + right;
  }

  public static String decryptBlock(String block, String inputKey) {
    String[] roundKeys = keyScheduleTransform(inputKey);

    String left = block.substring(0, block.length() / 2);
    String right = block.substring(block.length() / 2);

    for (int i = roundKeys.length - 1; i >= 0; i--) {
      String tempLeft = left;
      left = right;
      right = tempLeft;

      left = xorIt(left, functionF(right, roundKeys[i]));
    }

    return left + right;
  }

  public static String encryption(String longBinaryInput, String inputKey) {
    String[] processedInput = divideDataIntoBlocks(longBinaryInput);
    StringBuilder encryptedMessage = new StringBuilder();
    for (String s : processedInput) {
      encryptedMessage.append(encryptBlock(s, inputKey));
    }

    return encryptedMessage.toString();
  }

  public static String decryption(String longBinaryInput, String inputKey) {
    String[] processedInput = divideDataIntoBlocks(longBinaryInput);
    StringBuilder decryptedMessage = new StringBuilder();

    for (String s : processedInput) {
      decryptedMessage.append(decryptBlock(s, inputKey));
    }

    return decryptedMessage.toString();
  }
}
