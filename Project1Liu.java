/*
Name: Aki Liu (Fengkai Liu)
School email: fliu24@u.rochester.edu
Assignment: Project 1
*/

public class Project1Liu {
  public static void main(String[] args) {
    String input = "";
    System.out.println(input);
    
    String binaryData = toBinaryString(input);
    System.out.println(binaryData);
    
    String[] blocks = divideDataIntoBlocks(toBinaryString(input));
    
    for(String block : blocks) {
      System.out.println(block);
    }
  }

  public static String toBinaryString(String input) {
    // Divide the string into an array of characters
    char[] inputCharArray = input.toCharArray();
    String binaryData = "";

    // Convert all the characters to binary, and add them to the binaryData string
    for (char c : inputCharArray) {
      binaryData += Integer.toBinaryString(c);
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

  public static void encryptBlock() {}

  public static void decryptBlock() {}
}
