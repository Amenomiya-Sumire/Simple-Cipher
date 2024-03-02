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
    String[] splitBlocksLeftHalf = new String[blocks.length];
    
    for (int i = 0; i < blocks.length; i++) {
      String firstHalf = blocks[i].substring(0, 32);
      splitBlocksLeftHalf[i] = firstHalf;
    }
    
    return splitBlocksLeftHalf;
  }
  
  public static String[] splitBlocksIntoRightHalves(String[] blocks) {
    String[] splitBlocksRightHalf = new String[blocks.length];
    
    for (int i = 0; i < blocks.length; i++) {
      String secondHalf = blocks[i].substring(32, 64);
      splitBlocksRightHalf[i] = secondHalf;
    }
    return splitBlocksRightHalf;
  }

  public static String[] inputProcessing() {
    String input = getFileContent("src/data.txt");
	  if (input != null) {
		  return divideDataIntoBlocks(toBinaryString(input));
	  } else {
      return null;
    }
  }

  public static void xorIt(String binary1, String binary2) {
  
  }
  
  public static void shiftIt(String binaryInput) {
  
  }
  
  public static void permuteIt(String binaryInput) {
  
  }
  
  public static void substitutionS(String binaryInput) {
  
  }
  
  public static void functionF(String rightHalf, String subkey) {
  
  }

  public static void encryptBlock(String block, String inputKey) {}

  public static void decryptBlock(String block, String inputKey) {}
  
  public static void encryption(long longBinaryInput, String inputKey) {}
  
  public static void decryption(long longBinaryInput, String inputKey) {}

  public static void keyScheduleTransform(String inputKey) {
  
  }
  
  public static void runTests() {
  
  }
}
