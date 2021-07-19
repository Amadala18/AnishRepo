'''
Created on May 1, 2021

@author: 
         Anish Madala (anishmadala@vt.edu)
'''

import hashlib
import itertools
import time

import matplotlib.pyplot as plot

start_time = time.time()

wordsFile = open('possibleWords.txt', 'r')
words = []

for word in wordsFile:
    words.append(word.rstrip('\n'))

password = input("Enter password: ")
hash_type = int(input("Enter hashing algorithm sha256 or sha512 (type 256 or 512): "))
#password_hash = null

def hashPass(hashType): 
    if (hashType == 256):
        return hashlib.sha256(password.encode("ascii")).hexdigest()
    elif (hashType == 512):
        return hashlib.sha512(password.encode("ascii")).hexdigest()
    
passLength = len(password)


# words: each word from possible password words file
# passwordHashed: user's password that is either hashed as sha256 or sha512
# passLength: the length of the user's password
# returns an array with the number of guesses before guessing correctly and the word that was guessed correctly

def dictionary_attack(words, passwordHashed, passLength):
    guesses = 0
    
    for i in range(len(words)):
        # stores the possible combinations of words of i length from the input file in array 
        # the higher i is, the longer the execution will take
        for combination in itertools.combinations(words, i):
            possibleWords = []
            counter = 0
            for x in range(len(combination)):
                # adds on the length of the combination to counter
                counter = counter + len(combination[x])
            # if we found a combination whose length is equal to user password length, add it to array of possible passwords
            if counter == passLength:
                possibleWords.append(combination)
            # in each password in possible words, and its permutations, check if it is equal to user password hashed
            for y in range(len(possibleWords)):
                for z in itertools.permutations(possibleWords[y], len(possibleWords[y])):
                    # begin construction of user password
                    word = ""
                    # add letters in z to word
                    for k in z:
                        word = word + k
                    
                    #if current word's hash in either 256 or 512 is equal to the user's password hashed, user password is found    
                    if hash_type == 256:       
                        # returns num of guesses before the correct one, and the guessed word/password                 
                        if passwordHashed == hashlib.sha256(word.encode("ascii")).hexdigest():
                            return [guesses, word]
                        #increment guesses if word is not the correct password 
                        guesses = guesses + 1
                    elif hash_type == 512:                        
                        if passwordHashed == hashlib.sha512(word.encode("ascii")).hexdigest():
                            return [guesses, word]
                        guesses = guesses + 1            
            
    #returns message that password not found, and tried some number of guesses
    return [guesses, "Couldn't guess entered password"]

# call dictionary attack function
attackResults = dictionary_attack(words, hashPass(hash_type), passLength)
end_time = time.time()




print('\nResults: ')
print("Hash Type: sha", str(hash_type))
print("Dictionary Size:", len(words))
print("Elapsed Time:", str(round(end_time - start_time, 2)) + " seconds.")
print("Number of Guesses: " + str(attackResults[0]))


#create subplots for guesses and time
fig, (ax1, ax2) = plot.subplots(1,2, sharex=False, sharey=True)
#fills up width of screen
fig.set_figwidth(16)
#color of bar set to red
ax1.bar("Elapsed Time", end_time - start_time, color='red')
ax2.bar("Number of Guesses", attackResults[0])
#title for the subplots
plot_title = "Hash Method: sha" + str(hash_type) + "   " + "Dictionary Size: " + str(len(words)) + "   Password: " + attackResults[1] + "   Guesses: " + str(attackResults[0]) + "   Elapsed Time: " + str(round(end_time - start_time, 2)) 
fig.suptitle(plot_title)
#brings up the bar graph
plot.show()