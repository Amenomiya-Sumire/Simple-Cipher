```
Fengkai Liu
Yu Liu
CS Project1
```

## Program Introduction

This Java program is designed to encrypt and decrypt files using a simple ECB (Electronic Codebook) mode. In this program, the user can encrypt and decrypt .txt files that includes either binary string data and text data with a 56 bit secret key. 

It includes the following methods:
- **main(String[] args)**: Controls the program's flow, including user input for operation selection, file paths, and key input.
- **runTests()**: Demonstrates the program's encryption and decryption capabilities through preset test cases.
- **getFileContent(String pathToFile)**: Reads and returns the content of a specified file as a string.
- **toBinaryString(String input)**: Converts text input into a binary string representation.
- **divideDataIntoBlocks(String binaryData)**: Splits a binary string into 64-bit blocks for processing.
- **keyScheduleTransform(String inputKey)**: Generates a series of sub-keys from a 56-bit key for use in encryption or decryption rounds.
- **functionF(String rightHalf, String subkey)**: Applies a series of transformations using a sub-key.
- **substitutionS(String binaryInput)**: Performs substitution on a binary input using an S-box.
- **permuteIt(String binaryInput)**: Applies a permutation to a binary input based on a predefined table.
- **xorIt(String binary1, String binary2)**: Performs bitwise XOR operation on two binary strings.
- **shiftIt(String binaryInput)**: Left-shifts a binary input by one bit.
- **encryptBlock(String block, String inputKey)**: Encrypts a 64-bit block using the generated sub-keys.
- **decryptBlock(String block, String inputKey)**: Decrypts a 64-bit block using the generated sub-keys.
- **encryption(String longBinaryInput, String inputKey)**: Encrypts a long binary input by processing each block with encryptBlock.
- **decryption(String longBinaryInput, String inputKey)**: Decrypts a long binary input by processing each block with decryptBlock.

Fengkai Liu wrote the methods: main, encryption, encryptBlock, xorIt, permuteIt, functionF, and keyScheduleTransform. 
Yu Liu wrote the methods: decryption, decryptBlock, shiftIt, SubstitutionS, and runTests. 
We work together with debugging, and it costs a total of about 5 days to complete the entire project.

## Special Features:
### StringBuilder instead of normal String operation
We use string builder instead of just string due to the mutability and higher performance of string builder. Due to the immutability of String, frequent modifications may lead to significant memory consumption and ultimately performance degradation, but string builders can avoid such loss. Since we need to split strings frequently in both encryption and decryption process, we decide to use string builder, which is a better tool.

The main methods of string builder that we use are:
1. StringBuilder append(X x): This method appends the string representation of the X type argument to the sequence.
2. StringBuilder insert(): This method inserts the string representation of the char argument into this sequence.
3. String toString(): This method returns a string representing the data in this sequence.

(The method descriptions are from https://www.geeksforgeeks.org/stringbuilder-class-in-java-with-examples/)

Additional note: String.valueOf() method converts different types of values into string.

(The method description is from https://www.javatpoint.com/java-string-valueof)

### Robustness, Flexibility, Error Handling and User-Friendliness
We have additional questions to ask the user about the path of the file, since it might be confusing. To read a file needs its path, and we let the user choose whether the program and the file are both in a src directory. If yes, our program automatically adds the "src/" in front of the file name, so the user does not need to input the path of the file manually. Otherwise, the user needs to enter the path of the file.

Furthermore, we do not force the user to enter ".txt" at the end of the both original file name and output filename. But it still works when the user adds it.

We also find that it's not possible to automatically determine the data type of input and decrypt accordingly. These two cases must be considered separately.
1. If the original data is a binary string, the attempt of converting it back to text will cause an error.
2. If the original datatype is a text, it needs to convert back to text.
Therefore, we ask user about the data type in the original file when decrypting.

We have plenty of error handling methods. The error message will appear whenever an error occurs, and the user can re-enter the inputs.
