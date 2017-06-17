#!/usr/bin/env python3

#For comments use: # or """

#Import required libraries
import fileinput
import sys


#global variables to be used
num_chars = 0
LW_width = 0
LM_left = 0
LS_spaces = 0
#default formatting is off
FT_flag = False
FT = 'off'
blank_flag = False
new_para_flag = 0
old_ls = 0

#function that formats lines according to width (.LW),
#left margin (.LM), and linespacing (.LS) specified
#input: a list of words
#stdout: prints each word in the list to standard out
def format(words):
	global num_chars
	global LW_width
	global LM_left
	global blank_flag
	global new_para_flag

	#for each word in the list "words"
	for x in range(len(words)):
		
		#if blank_flag == True:
			#print()
			#lineSpacing()
		#adds linespacing if max line width reached with first word in list
		if num_chars + len(words[x]) +1 > LW_width:
			print()
			lineSpacing()
			num_chars = 0
		#adds space between words, only if not first word in new line
		elif num_chars > LM_left:
			print(" ", end = "")
			num_chars += 1
		#adds left margin spaces, if new line
		if num_chars == 0:
			if new_para_flag == 1:
				lineSpacing()
				new_para_flag = 0
			indent()
			num_chars += LM_left
		#finally, prints word in list
		print("%s" %words[x], end="")
		num_chars += len(words[x])
		blank_flag = False


#function to add appropriate left indentation (.LM)
def indent():
	global LM_left
	for x in range(LM_left):
		print(" ", end = "")

#function to add appropriate linespacing (.LS)
def lineSpacing():
	global LS_spaces
	global old_ls
	for x in range(LS_spaces):
		print()

#function to check for formatting commands
#input: a string line
#returns: an integer (1 if line is a formatting line, 0 otherwise)
def isCommand(line):
	global num_chars
	global LW_width #.LW
	global LM_left #.LM
	global LS_spaces #.LS
	global FT_flag #.FT
	global FT
	global new_para_flag
	global old_ls
	isFormat = 0

	#split the line into a list, removing whitespace
	words = line.split()

	#checks for each type of command
	#sets formatting flag as True if its a format command line
	if words[0] == ".LW":
		#casts word in list to integer 
		LW_width = int(words[1])
		FT_flag = True
		isFormat = 1
	elif words[0] == ".LM":
		#checks if left margin needs to be added/subtracted
		#from current left margin position or not
		if any("-" in word for word in words):
			LM_left = LM_left + int(words[1])
		elif any("+" in word for word in words):
			LM_left = LM_left + int(words[1])
		else:
			LM_left = int(words[1])
		#checks for valid margins
		if (LM_left < 0):
			LM_left = 0
		if (LM_left > (LW_width - 20)):
			LM_left = (LW_width - 20)
		FT_flag = True
		isFormat = 1
	elif words[0] == ".LS":
		#casts word in list to integer
		LS_spaces = int(words[1])
		FT_flag = True
		isFormat = 1
	elif words[0] == ".FT":
		if words[1] == "on":
			FT_flag = True
		else: #.FT off
			FT_flag = False
		isFormat = 1
	
	return isFormat

def main():

	global num_chars
	global LW_width
	global LM_left
	global LS_spaces
	global FT_flag
	global FT
	global new_para_flag

	for line in fileinput.input():
		#checks for blank new lines
		if line.strip() == '':
			print()
			if FT_flag == True:
				print()
				lineSpacing()
			num_chars = 0
			new_para_flag = 1
		else:
			#determines if line has formatting commands
			isFormat = isCommand(line)

			#checks that formatting is off & that line is not
			#a formatting command line
			if FT_flag == False and isFormat != 1:
				#formatting flag is off, print out line as is
				print(line, end="")
			#checks that formatting is on & that line is not
			#a formatting command line
			elif FT_flag == True and isFormat !=1:
				#splits line into list of words, removing all whitespace
				words = line.split()
				#formats & prints words as per commands set
				format(words)

	#prints new line at end of file	
	if num_chars > 0:
		print()
	if blank_flag == True:
			print()


if __name__=='__main__':
	main()