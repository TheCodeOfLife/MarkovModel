#Markov Modeling, 550 Extra Credit

##By Luke Tannenbaum, Graded by Yen-Chun Chen

---
###The Assignment:

1. Improve the performance of code that generates random text based on predicting characters. For this you'll use a map to store information rather than (re)computing the information.
2. Write a new random-text generation program based on words rather than characters.
3. Modify, run, and report on a performance test comparing HashMap and TreeMap implementations of the Map interface.


####1: Use Map for Markov Prediction:

For this assignment, we used a java HashMap that stored each sub "k" gram with all its respective found characters that came after this substring. Each time a new following character was found after the k gram, it gets added to the list associated with that k gram.
After every k gram was computed, we simply use this said stored map to print out a set number of characters using the weighted probability of the characters to occur, which is built into the map by the number of occurrences in each k gram's list. These following characters are chosen using a random seed which is preset when the program begins.

####2: Use Words with Map for Prediction:

This portion uses the same kind of map again, but for this part we store every few words as the key, and the subsequent list of "every few words" as the value. For this, I used the built in given data structure for these "every few words", called "WordNGram".

There was not much of a difference in implementation between this and part 1, besides switching the search for characters to WordNGrams.

####3: Performance/Testing:

For testing my assignment, I used the given files which involved different books, essays, and my own favorite poem written as plain text. These all seemed to function properly, and gave higher accuracy the higher I rose my number of characters/words that I calculated. 
