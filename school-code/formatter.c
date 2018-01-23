/*
 * UVic SENG 265, Fall 2016,  A#4
 *
 * This will contain the bulk of the work for the fourth assignment. It
 * provides similar functionality to the class written in Python for
 * assignment #3.
 *
 * An array of strings will be returned from the format_lines() function,
 * they are to be output to stdout
 * 
 * Allocate memory for both the result array and the strings accessible
 * from the result array
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "formatter.h"

/*prototypes*/
int isCommand(char*);
char **format_file(FILE*);
char **format_lines(char**, int);
void format(char*);
void indent();
void linespacing();

/*creates boolean type*/
typedef enum {false, true} bool;

/*char ** A means "a pointer to a pointer to a char."
allocate memory for strings && arrays of strings*/
char *words_str = NULL;
char **array_str = NULL;
int array_var; /*size of array variable*/
int count; /*current size of array*/

/*int num_words;*/
int ch_count = 0;

int LW_width;
int LM_left = 0;
int LS_spaces;
bool FT_flag = false; /*default formatting is off*/
bool LS_flag = false;
bool LS_change = false;
int temp_LS;
int newpara_flag = 0;
int max_leftmargin = 20;

/*
char * A [] means "an array of pointers to chars," whereas char ** A means "a 
pointer to a pointer to a char." Note that the first one explicitly tells you 
that we are working with an array, while the second does not make such a guarantee. 

1. with "char *A[]", you can't assign any value to A, but A[x] only;
with "char **A", you can assign value to A, and A[x] as well.

2. with "char *A[]", you can initialize an array (e.g. char *A[] = {"foo", "bar"};) 
and use sizeof(A) to get the size (on 64-bit machine, the above example gives you 16 
because it's an array of two char*);
with "char **A", sizeof(A) always returns the size of a pointer (on 64-bit machine, 8).
*/

/*file scope variable for LW variable instead of program scope*/

/*use ADTs and function prototypes to simulate modules*/

/*
 * Function used when a file's contents are to be formatted.
 * The formatted output will be returned as a dynamically-allocated
 * array of strings.
 */

/*allocate memory for strings(words_str) && arrays of strings (array_str)*/
char **format_file(FILE *infile) {
	int isFormattingCommand;
	char *line = NULL;
	size_t len = 0;
	ssize_t read;
	/*initial size of array, to be adjusted when required*/
	array_var = 10000;
	count = 0;
	/*allocates memory for an array of strings that will be returned at end of method*/
	array_str = (char**)malloc(sizeof(char*) * array_var);
	if(array_str == NULL){ 
		fprintf(stderr, "Error mallocing a memory for an array of strings");
		exit(1);
	}
	/*allocates memory for a string that will hold line to be formatted*/
	words_str = (char*)malloc(sizeof(char) * (120));
	if(words_str == NULL){ 
		fprintf(stderr, "Error mallocing a memory for string");
		exit(1);
	}
	/*from slide 34, reads in file line by line*/
	while((read = getline(&line, &len, infile)) != -1){

		/*checks for formatting command line*/
		isFormattingCommand = isCommand(line);
		if(FT_flag == false && isFormattingCommand != 1){
			/*formatting is off, print line as is aka add to array*/
			array_str[count] = (char*)malloc(sizeof(char)*(strlen(line)+1));
			if(array_str[0] == NULL){ 
				fprintf(stderr, "Error mallocing a memory in array for string");
				exit(1);
			}
			strncpy(array_str[count], line, strlen(line)+1);
			count++;
		}else if (FT_flag == true && isFormattingCommand != 1){
			/*blank line*/
			if(strncmp(line, "\n", strlen(line)) ==0){
				/*we have a blank line so add the prev line to array if we are in the middle*/
				if(strncmp(words_str, "\0", 1) != 0){ /*string is not empty*/
					strncat(words_str, "\n", 2);
					array_str[count] = (char*)malloc(sizeof(char)*(strlen(words_str)+1));
					if(array_str[0] == NULL){ 
						fprintf(stderr, "Error mallocing a memory in array for string");
					exit(1);
					}
					strncpy(array_str[count], words_str, strlen(words_str)+1);
					count++;
					ch_count = 0;
					/*clears word string*/
					strncpy(words_str, "\0", 1);
				}
				/*add blank line*/
				array_str[count] = (char*)malloc(sizeof(char)*2);
				if(array_str[0] == NULL){ 
					fprintf(stderr, "Error mallocing a memory in array for string");
					exit(1);
				}
				strncpy(array_str[count], "\n", 2);
				count++;
				linespacing();
				newpara_flag = 1;
			}
			/*formats line by calling format function, passing line*/
			format(line);
		}
		//fprintf(stderr, "line = %s\n", line);
		//fprintf(stderr, "array_str[%d] = %s\n", count-1, array_str[count-1]);
	}
	
	/*reached end of line with words still in word_str*/
	if(FT_flag == true && newpara_flag != 1){
		strncat(words_str, "\n", 2);
		array_str[count] = (char*)malloc(sizeof(char)*(strlen(words_str)+1));
		if(array_str[0] == NULL){ 
			fprintf(stderr, "Error mallocing a memory in array for string");
			exit(1);
		}
		strncpy(array_str[count], words_str, strlen(words_str)+1);
		count++;
		ch_count = 0;
		/*clears word string*/
		strncpy(words_str, "\0", 1);
	}
	if(line){
		free(line);
	}
	/*add end of array, null terminator*/
	array_str[count] = NULL; 
	return array_str;
}
/*removes white space and adds words to words_str, once line width 
is reached then words_str is added to the array of strings array_str*/
void format(char *line){
	char* d;
	char* localtemp =NULL;
	localtemp = (char*)malloc(sizeof(char) * (strlen(line)+1));
	if(localtemp == NULL){ 
		fprintf(stderr, "Error mallocing a memory for string");
		exit(1);
	}
	strncpy(localtemp, line, strlen(line)+1);
	d = strtok(localtemp, " \n");/*removes whitespace and newline characters*/
	while(d){
		int n = strlen(d);
		/*adds linespacing if max line width reached with first word in list*/
		if(ch_count + n + 1 > LW_width){
			/*new line required so add string to array of strings*/
			strncat(words_str, "\n", 2);
			array_str[count] = (char*)malloc(sizeof(char)*(strlen(words_str)+1));
			if(array_str[0] == NULL){ 
				fprintf(stderr, "Error mallocing a memory in array for string");
				exit(1);
			}
			strncpy(array_str[count], words_str, strlen(words_str)+1);
			count++;
			/*clears word string*/
			strncpy(words_str, "\0", 1);
			linespacing();
			ch_count = 0;
		/*adds space between words, only if not first word in new line*/
		} else if(ch_count > LM_left){
			strncat(words_str, " ", 2);
			ch_count++;
		}
		/*adds left margin spaces, if new line*/
		if(ch_count == 0){
			if(newpara_flag == 1){
				linespacing();
				newpara_flag = 0;
			}
			indent();
			ch_count += LM_left;
		}
		/*finally, adds word to string*/
		strncat(words_str, d, n+1);
		ch_count += n;
		d = strtok(NULL, " \n");
	}
	if(localtemp){
		free(localtemp);
	}	

}

/*method to check for format command and sets appropriate format method variables*/
int isCommand(char* line){
	int isFormattingCommand = 0;
	char* d;

	if(strncmp(line, ".LW", 3) == 0){
		/*determines line width specified*/
		d = strtok(line, ".LW ");
		LW_width = atoi(d);
		FT_flag = true;
		isFormattingCommand = 1;
	}else if(strncmp(line, ".LM", 3) == 0){
		/*determines the left margin spaces*/
		d = strtok(line, ".LM ");
		/*checks if left margin needs to be added/subtracted from current LM_left*/
		if(strncmp(d, "+", 1) == 0 || strncmp(d, "-", 1) == 0){
			LM_left += atoi(d);
		} else{
			LM_left = atoi(d);
		}
		if(LM_left > (LW_width - max_leftmargin)){
			LM_left = LW_width - 20;
		}	
		FT_flag = true;
		isFormattingCommand = 1;
	}else if(strncmp(line, ".LS", 3) == 0){
		/*determines the number of linespacing*/
		if(LS_flag == true){
			temp_LS = LS_spaces;
			LS_change = true;
		}
		d = strtok(line, ".LS ");
		LS_spaces = atoi(d);
		LS_flag = true;
		FT_flag = true;
		isFormattingCommand = 1;
	}else if(strncmp(line, ".FT on", 6) == 0){
		/*if formatting is on, continue formatting*/
		FT_flag = true;
		isFormattingCommand = 1;
	}else if(strncmp(line, ".FT off", 7) == 0){
		/*if formatting is off, print input as is*/
		FT_flag = false;
		isFormattingCommand = 1;
	}
	return isFormattingCommand;
}

/*method to add appropriate linespacing (.LS) to array of strings*/
void linespacing(){
	int i = 0;
	/*allocate space in memory for blank new line*/
	array_str[count] = (char*)malloc(sizeof(char)*2);
	if(array_str[0] == NULL){ 
		fprintf(stderr, "Error mallocing a memory in array for string");
		exit(1);
	}
	/*if there is a change in linespacing*/
	if(LS_change == true){
		for(i = 0; i < temp_LS; i++){
			/*add blank new line to array of strings*/
			strncpy(array_str[count], "\n", 2);
			count++;	
		}
		//LS_flag = false;
		LS_change = false;
		temp_LS = LS_spaces;
	}else{	/*no change in linespaceing */
		for(i = 0; i < LS_spaces; i++){
			/*add blank new line to array of strings*/
			strncpy(array_str[count], "\n", 2);
			count++;
		}
	}
}

/*method to add indents to the left-hand margin (.LM)*/
void indent(){
	int i;
	for(i = 0; i < LM_left; i++){
		strncat(words_str, " ", 2);
	}
}

//char ** A means "a pointer to a pointer to a char."
/*
 * Function used when an array of strings is to be formatted.
 * The formatted output will be returned as a dynamically-allocated
 * array of strings.
 */
//allocate memory for strings && arrays of strings
char **format_lines(char **lines, int num_lines) {
	int isFormattingCommand;
	char *line = NULL;
	/*initial size of array, to be adjusted when required*/
	array_var = num_lines*2;
	count = 0;

	/*allocates memory for an array of strings that will be returned at end of method*/
	array_str = (char**)malloc(sizeof(char*) * array_var);
	if(array_str == NULL){ 
		fprintf(stderr, "Error mallocing a memory for an array of strings");
		exit(1);
	}
	/*allocates memory for a string that will hold line to be formatted*/
	words_str = (char*)malloc(sizeof(char) * (120));
	if(words_str == NULL){ 
		fprintf(stderr, "Error mallocing a memory for string");
		exit(1);
	}
	/*reads in file line by line*/
	int i;
	for(i = 0; i < num_lines; i++){
		line = lines[i];
		/*checks for formatting command line*/
		isFormattingCommand = isCommand(line);
		
		if(FT_flag == false && isFormattingCommand != 1){
			/*formatting is off, print line as is aka add to array*/
			
			array_str[count] = (char*)malloc(sizeof(char)*(strlen(line)+1));
			if(array_str[0] == NULL){ 
				fprintf(stderr, "Error mallocing a memory in array for string");
				exit(1);
			}
			
			strncpy(array_str[count], line, strlen(line)+1);

			count++;
		}else if (FT_flag == true && isFormattingCommand != 1){
			/*blank line*/
			if(strncmp(line, "\n", strlen(line)) ==0){
				/*we have a blank line so add the prev line to array if we are in the middle*/
				if(strncmp(words_str, "\0", 1) != 0){ /*string is not empty*/
					strncat(words_str, "\n", 2);
					array_str[count] = (char*)malloc(sizeof(char)*(strlen(words_str)+1));
					if(array_str[0] == NULL){ 
						fprintf(stderr, "Error mallocing a memory in array for string");
					exit(1);
					}
					strncpy(array_str[count], words_str, strlen(words_str)+1);
					count++;
					ch_count = 0;
					/*clears word string*/
					strncpy(words_str, "\0", 1);
				}
				/*add blank line*/
				array_str[count] = (char*)malloc(sizeof(char)*2);
				if(array_str[0] == NULL){ 
					fprintf(stderr, "Error mallocing a memory in array for string");
					exit(1);
				}
				strncpy(array_str[count], "\n", 2);
				count++;
				linespacing();
				newpara_flag = 1;
			}
			
			/*formats line by calling format function, passing line*/
			format(line);

		}
	}

	/*reached end of line with words still in word_str*/
	if(FT_flag == true && newpara_flag != 1){
		strncat(words_str, "\n", 2);
		array_str[count] = (char*)malloc(sizeof(char)*(strlen(words_str)+1));
		if(array_str[0] == NULL){ 
			fprintf(stderr, "Error mallocing a memory in array for string");
			exit(1);
		}
		strncpy(array_str[count], words_str, strlen(words_str)+1);
		count++;
		ch_count = 0;
		/*clears word string*/
		strncpy(words_str, "\0", 1);
	}
	/*add end of array, null terminator*/
	array_str[count] = NULL; 

	return array_str;
}

/*
 * a wrapper function that calls malloc; if allocation fails, it
 * reports an arror and exits the program. Used as a memory allocator
 * that never returns failure.
 */
void *emalloc(size_t n){ 
	void *p;

	p = malloc(n);
	if(p == NULL){
		fprintf(stderr, "malloc of %zu bytes failed", n);
		exit(1);
	}
	return p;
}

