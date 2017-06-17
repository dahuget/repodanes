#!/usr/bin/env python3

#Import required libraries
import fileinput
import sys
import argparse
import re

class Formatter:
	"""A class that formats input text per the second assignment specifications"""

	#Data attributes, instance variables
	#any attribute or method with two leading underscores in its name (but none at end) is private
	#Class attributes, owned bu the class as a whole ie. class-wide constants
	def __init__(self, filename, textinput):
		
		self.filename = filename
		self.textinput = textinput
		#output list
		self.post_line = []
		#data attributes
		self.num_chars = 0
		self.LW_width = 0
		self.LM_left = 0
		self.LS_spaces = 0
		self.UP = 0
		self.SW = 0
		self.FT_flag = False #default formatting is off
		self.blank_flag = False
		self.new_para_flag = 0
		self.temp_LS = 0
		self.LS_flag = False
		self.LS_change = False
		self.format_string = ""

	#class method because calling static methods
	#accessed via
	#@classmethod
	def format(self, line):
		isFormat = self.isCommand(line)
		#checks that formatting is off & that line is not
		#a formatting command line

		if self.FT_flag == False and isFormat != 1:
			#formatting flag is off, print out line as is
			self.post_line.append(line)
		#checks that formatting is on & that line is not
		#a formatting command line
		elif self.FT_flag == True and isFormat !=1:
			#splits line into list of words, removing all whitespace
			words = line.split()
			#formats & prints words as per commands set	
			#for each word in the list "words"
			for x in range(len(words)):
				#adds linespacing if max line width reached with first word in list
				if self.num_chars + len(words[x]) +1 > self.LW_width:
					#append string to post_line list
					self.post_line.append(self.format_string)
					self.format_string = ""
					self.lineSpacing()
					self.num_chars = 0
				#adds space between words, only if not first word in new line
				elif self.num_chars > self.LM_left:
					self.format_string += " "
					self.num_chars += 1
				#adds left margin spaces, if new line
				if self.num_chars == 0:
					if self.new_para_flag == 1:
						self.lineSpacing()
						self.new_para_flag = 0
					self.indent()
					self.num_chars += self.LM_left
				#inverts case for all letters in string
				if self.SW == 1:
					words[x] = words[x].swapcase()	
				#converts lowercase to uppercase letters in string
				if self.UP == 1:
					words[x] = words[x].upper()
				#finally, prints word in list
				self.format_string += words[x]
				self.num_chars += len(words[x])
				self.blank_flag = False	

	#function to add appropriate left indentation (.LM)
	def indent(self):
		for x in range(self.LM_left):
			self.format_string += " "

	def lineSpacing(self):
		#change in linespacing
		if self.LS_change == True:
			for x in range(self.temp_LS):
				self.post_line.append("")
			self.LS_flag == False
			self.temp_LS = self.LS_spaces
		else:
			for x in range(self.LS_spaces):
				self.post_line.append("")

	#belongs to the class but doesn't use the object itself at all
	#accessed via other methods self.isCommand(self.line ?) or class function cls.isCommand(line)
	#@staticmethod
	def isCommand(self, line):
		isFormat = 0
		#split the line into a list, removing whitespace
		words = line.split()
		#checks for each type of command
		#sets formatting flag as True if its a format command line
		if words[0] == ".LW":
			try:
				#casts word in list to integer 
				self.LW_width = int(words[1])
				self.FT_flag = True
			except ValueError:
				sys.stderr.write('\nValueError: invalid .LW value: %s\n\n' % (words[1]))
			isFormat = 1
		elif words[0] == ".LM":
			try:
				#checks if left margin needs to be added/subtracted
				#from current left margin position or not
				#any() returns true if - or + is found
				if any("-" in word for word in words):
					self.LM_left = self.LM_left + int(words[1])
				elif any("+" in word for word in words):
					self.LM_left = self.LM_left + int(words[1])
				else:
					self.LM_left = int(words[1])
				#checks for valid margins
				if self.LM_left < 0:
					sys.stderr.write('\n**** .LM Command created invalid negative line margin : %s ****\n' % (self.LM_left))
					sys.stderr.write('**** Changed .LM to zero ****\n\n')
					self.LM_left = 0
				if self.LM_left > (self.LW_width - 20):
					self.LM_left = (self.LW_width - 20)
				self.FT_flag = True
			except ValueError:
				sys.stderr.write('\nValueError: invalid .LM value: %s\n\n' % (words[1]))	
			isFormat = 1
		elif words[0] == ".LS":
			try:
				if self.LS_flag == True:
					self.temp_LS = self.LS_spaces
					self.LS_change = True
				#casts word in list to integer
				self.LS_spaces = int(words[1])
				if self.LS_spaces > 2 or self.LS_spaces < 0:
					sys.stderr.write('\n**** .LS Command created invalid line spacing >2 or <0 : %s ****\n' % (self.LS_spaces))
					sys.stderr.write('**** Changed .LS to zero ****\n\n')
					self.LS_spaces = 0
				self.LS_flag = True
				self.FT_flag = True
			except ValueError:
				sys.stderr.write('\nValueError: invalid .LS value: %s\n\n' % (words[1]))	
			isFormat = 1
		elif words[0] == ".SW":
			self.SW = 1
			self.FT_flag = True
			isFormat = 1
		elif words[0] == ".UP":
			self.UP = 1
			self.FT_flag = True
			isFormat = 1
		elif words[0] == ".FT":
			if words[1] == "on":
				self.FT_flag = True
			else: #.FT off
				self.FT_flag = False
			isFormat = 1
		return isFormat

	#handles input text
	def input_text(self, text_lines):
		for line in text_lines:
			#line is empty
			if line.strip() == '':
				if self.format_string != "":
					self.post_line.append(self.format_string)
					self.format_string = ""
				self.post_line.append("")
				self.lineSpacing()
				self.new_para_flag = 1
				self.num_chars = 0
			else:
				#remove new line characters
				line = line.replace('\n', "")
				self.format(line)
		#reached end of line with words still in format_string	
		if self.FT_flag == True and self.new_para_flag != 1:
			self.post_line.append(self.format_string)
			self.format_string = ""
		return

#If the Formatter class is instantiated from outside of textdriver.py, 
#it will suffice to just return a list of formatted lines
	#this is a method that is a function that is stored as a class attribute
	#accessed via Formatter.get_lines()
	def get_lines(self):
		
		#file name provided in first argument
		if self.filename != None and self.textinput == None:
			if self.filename == "stdin": 
				try:	
					#filename = "stdin"
					fileinput = sys.stdin.readlines()	
					self.input_text(fileinput)
				except IOError:
					ErrorMsg = ["\nIOError: cannot read from stdin\n"]
					return ErrorMsg
			else:
				#filename = args.filename
				try:
					clean = filter(None, re.match('(\/?\w+)+\.txt', self.filename).groups())
				except AttributeError:
					clean = ""
					ErrorMsg = ["\nAttributeError: file input name (%s) invalid, must be .txt type.\n" % (self.filename)]
					return ErrorMsg
				try:
					fileinput = open(self.filename)
				except IOError:
					sys.stderr.write("\nIOError: cannot open file: %s\n" % (self.filename))
				else:
					self.input_text(fileinput)
					fileinput.close()
				
		#list of strings provided in second argument
		if self.filename == None and self.textinput != None:
			try:
				#checks if textinput is a list
				isinstance(self.textinput, list)
				self.input_text(self.textinput)
			except TypeError:
				ErrorMsg = ["\nTypeError: expected list as input\n"]
				return ErrorMsg
		return self.post_line
