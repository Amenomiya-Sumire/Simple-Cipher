Fengkai Liu
Yu Liu
CS Project1

In this project, we write a code used to encrypt and decrypt data. For encryption and decryption, we convert plain binary data into an encrypted form using a secret key and then convert it back. We use file Handling to read binary data from and write encrypted/decrypted binary data to files. The set of operations including permutation, substitution (S-box), XOR operations, and key schedule transformations are all crucial to the encryption and decryption process. The program also includes a series of test cases to demonstrate the functionality when dealing with different types of binary data and keys.

Through the process, Fengkai Liu mainly handles the encryption side, while Yu Liu handles the decryption side. Fengkai Liu also writes the methods: xorIt(binary1, binary2), permuteIt(binaryInput), functionF(rightHalf, subkey), and keyScheduleTransform(inputKey). Yu Liu write the methods: shiftIt(binaryInput), SubstitutionS(binaryInput), and runTests(). We work together with debugging, and it costs a total of about 5 days to complete the entire project.

Special Features:

1. StringBuilder instead of normal String operation
We use string builder instead of just string due to the mutability and higher performance of string builder. Due to the immutability of String, frequent modifications may lead to significant memory consumption and ultimately performance degradation, but string builders can avoid such loss. Since we need to split strings frequently in both encryption and decryption process, we decide to use string builder, which is a better tool.

The main methods of string builder that we use are:
(1) StringBuilder append(X x): This method appends the string representation of the X type argument to the sequence.
(2) StringBuilder insert(): This method inserts the string representation of the char argument into this sequence.
(3) String toString(): This method returns a string representing the data in this sequence.

(The method descriptions are from https://www.geeksforgeeks.org/stringbuilder-class-in-java-with-examples/)

Additional note:
(1) String.valueOf() method converts different types of values into string.

(The method description is from https://www.javatpoint.com/java-string-valueof)

2. Robustness, Flexibility, Error Handling and User-Friendliness
We have additional questions to ask the user about the path of the file, since it might be confusing. To read a file needs its path, and we let the user choose whether the program and the file are both in a src directory. If yes, our program automatically adds the "src/" in front of the file name, so the user does not need to input the path of the file manually. Otherwise, the user needs to enter the path of the file.

Furthermore, we do not force the user to enter ".txt" at the end of the both original file name and output filename. But it still works when the user adds it.

We also find that it's not possible to automatically determine the data type of input and decrypt accordingly. These two cases must be considered separately.
(1) If the original data is a binary string, the attempt of converting it back to text will cause an error.
(2) If the original datatype is a text, it needs to convert back to text.
Therefore, we ask user about the data type in the original file when decrypting.

We have plenty of error handling methods. The error message will appear whenever an error occurs, and the user can re-enter the inputs.