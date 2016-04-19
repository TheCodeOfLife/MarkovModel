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

1. How long does it take using the provided, brute force code to generate order-5 text using Romeo and Juliet (romeo.txt) and generating 100, 200, 400, 800, and 1600 random characters. Do these timings change substantially when using order-1 or order-10 Markov models? Why?

As seen below, these values seem to be able to be generated faster the higher order they are given, and the more characters they have to red, the faster they seem to run as well. Maybe not an order of magnitude better, but certainly by a constant amount.

Order-5:
100: 412 milliseconds
200: 343
400: 339
800: 362
1600: 337

Order-1:
100: 505 milliseconds
200: 624
400: 583
800: 467
1600: 447

Order-10:
100: 374 milliseconds
200: 349
400: 346
800: 377
1600: 332

2. Romeo has roughly 153,000 characters. Hawthorne's Scarlet Letter contains roughly 500,000 characters. How long do you expect the brute force code to take to generate order-5 text when trained on hathorne.txt given the timings you observe for romeo when generating 400, 800, 1600 random characters? Do empirical results match what you think? How long do you think it will take to generate 1600 random characters using an order-5 Markov model when the King James Bible is used as the training text --- our online copy of this text contains roughly 4.4 million characters. Justify your answer -- don't test empirically, use reasoning.

I claim that the higher number of characters are used in a text, the longer the time to generate the characters will be, however the accuracy also goes up with the number of characters in a text, because theoretically it has been trained on more samples. In comparison from romeo to scarlet, I think the amount of time will approximately double from the ~4x change in number of characters. The evidence shows it was a bit higher, around 920 ms, but still kind of close. For KJV, I think it could take a lot of time, but more of a lgn than a n^2 amount- maybe around 250x more? I would think it would be on a logarithmic curve from the origial romeo. 

3. Provide timings using your Map/Smart model for both creating the map and generating 200, 400, 800, and 1600 character random texts with an order-5 Model and romeo.txt. Provide some explanation for the timings you observe.

See Above.
