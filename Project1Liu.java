/*
Name: Aki Liu (Fengkai Liu)
School email: fliu24@u.rochester.edu
Assignment: Project 1
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Project1Liu {
  public static void main(String[] args) {
    String[] blocks = inputProcessing();
    String[] splitBlocksLeftHalf = splitBlocksIntoLeftHalves(blocks);
    String[] splitBlocksRightHalf = splitBlocksIntoRightHalves(blocks);

    for (int i = 0; i < blocks.length; i++) {
      System.out.println(splitBlocksLeftHalf[i]);
      System.out.println(splitBlocksRightHalf[i]);
    }
  }

  public static String getFileContent(String pathToFile) {
    try {
      return new String(Files.readAllBytes(Paths.get(pathToFile)));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String toBinaryString(String input) {
    // Divide the string into an array of characters
    char[] inputCharArray = input.toCharArray();
    String binaryData = "";

    // Convert all the characters to binary, and add them to the binaryData string
    for (char c : inputCharArray) {
      String binaryString = Integer.toBinaryString(c);

      // Ensure that all characters will be converted into a binary number with 8 digits
      while (binaryString.length() < 8) {
        binaryString = "0" + binaryString;
      }
      binaryData += binaryString;
    }

    return binaryData;
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
      String block = binaryData.substring(startIndex, endIndex);

      // Ensure every block has the size of 64, adding 0 to the end if its size is smaller
      while (block.length() < blockSize) {
        block += "0";
      }
      blocks[i] = block;
    }

    return blocks;
  }

  public static String[] splitBlocksIntoLeftHalves(String[] blocks) {
    String[] leftHalf = new String[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      String firstHalf = blocks[i].substring(0, 32);
      leftHalf[i] = firstHalf;
    }

    return leftHalf;
  }

  public static String[] splitBlocksIntoRightHalves(String[] blocks) {
    String[] rightHalf = new String[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      String secondHalf = blocks[i].substring(32, 64);
      rightHalf[i] = secondHalf;
    }
    return rightHalf;
  }

  public static String[] inputProcessing() {
    String input = getFileContent("src/data.txt");
    if (input != null) {
      return divideDataIntoBlocks(toBinaryString(input));
    } else {
      return null;
    }
  }

  public static void xorIt(String binary1, String binary2) {}

  public static void shiftIt(String binaryInput) {}

  public static String permuteIt(String binaryInput) {
    int[] permutationTable = {
        16, 7, 20, 21, 29, 12, 28, 17,
        1, 15, 23, 26, 5, 18, 31, 10,
        2, 8, 24, 14, 32, 27, 3, 9,
        19, 13, 30, 6, 22, 11, 4, 25
    };
    
    String permutedString = "";
    
    // Add every character correspond with permutation table's position in a new string.
    for (int i = 0; i < permutationTable.length; i++) {
      permutedString += binaryInput.charAt(permutationTable[i] - 1);
    }
    
    return permutedString;
  }

  public static String substitutionS(String binaryInput) {
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

    int binaryInt = Integer.parseInt(binaryInput, 2); // Convert the binary string input into int.
    String hexInput = Integer.toHexString(binaryInt); // Convert the binary int input into hex.
    
    // Ensure that the hex number will be 2 digits.
    while (hexInput.length() < 2) {
      hexInput = "0" + hexInput;
    }

    // Read the high and low of the hex number.
    int high = Integer.parseInt(hexInput.substring(0, 1), 16);
    int low = Integer.parseInt(hexInput.substring(1, 2), 16);

    // Correspond the numbers with the position of S-box.
    return S[high][low];
  }

  public static String[] functionF(String[] rightHalf, String subkey) {
    String[] binarySegments = new String[rightHalf.length * 4];

    // Divide the 32-bit blocks into segments of 8 bits.
    for (int i = 0; i < rightHalf.length; i++) {
      for (int j = 0; j < 4; j++) {
        binarySegments[4 * i + j] = rightHalf[i].substring(j * 8, (j + 1) * 8);
      }
    }
    
    String[] dataAfterSubstitution = new String[rightHalf.length];

    // Doing S-box substitution and concatenate the String back to 32 bits.
    for (int i = 0; i < binarySegments.length; i++) {
      String concatenatedString = substitutionS(binarySegments[i]);
      
      // Ensuring that the index will not out of bound
      if (i + 1 < binarySegments.length)
        concatenatedString += substitutionS(binarySegments[i + 1]);
      if (i + 2 < binarySegments.length)
        concatenatedString += substitutionS(binarySegments[i + 2]);
      if (i + 3 < binarySegments.length)
        concatenatedString += substitutionS(binarySegments[i + 3]);
      
      dataAfterSubstitution[i / 4] = concatenatedString;
    }
    
    String[] dataAfterPermutation = new String[rightHalf.length];
    
    for (int i = 0; i < dataAfterSubstitution.length; i++) {
      dataAfterPermutation[i] = permuteIt(dataAfterSubstitution[i]);
    }
    
    return dataAfterPermutation;
  }

  public static void encryptBlock(String block, String inputKey) {}

  public static void decryptBlock(String block, String inputKey) {}

  public static void encryption(long longBinaryInput, String inputKey) {}

  public static void decryption(long longBinaryInput, String inputKey) {}

  public static void keyScheduleTransform(String inputKey) {}

  public static void runTests() {}
}
