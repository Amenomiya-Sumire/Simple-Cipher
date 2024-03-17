Fengkai Liu
Yu Liu
CS Project1

In this project, we write a code that is used to encrypt and decrypt data. For encryption and decryption, we converts plain binary data into an encrypted form using a secret key and then convert it back. We use file Handling to reads binary data from and writes encrypted/decrypted binary data to files. The set of operations including permutation, substitution (S-box), XOR operations, and key schedule transformations are all criticle to the encryption and decryption process. The program also includes a series of test cases to demonstrate the functionality when deal with different types of binary data and keys. 

One special feature we have is that we use stringbuilder instead of just string due to the mutability and higher performance of stringbuilder. Due to the immutability of String, frequent modifications may lead to significant memory consumption and ultimately performance degradation, but stringbuilder can avoid such loss. Since we need to split strings frequently in both encryption and decryption process, we decide to use stringbuilder which is a better tool.

Through the process, Fengkai Liu write the methods: xorIt(binary1, binary2), permuteIt(binaryInput), functionF(rightHalf, subkey), keyScheduleTransform(inputKey), encryption(longBinaryInput, inputKey), and encryptBlock(block, inputKey). Yu Liu write the methods: shiftIt(binaryInput), SubstitutionS(binaryInput), runTests(), decryptBlock(block, inputKey), and decryption(longBinaryInput, inputKey). We work together with debugging, and it cost a total of about 5 days to complete the entire project. 
