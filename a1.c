
  #include <stdlib.h>
  #include <stdio.h>
  #include <string.h>
  #include <assert.h>
   
   
 #define MAX_WORDS 66000
 #define MAX_WORD_LEN 20
 #define MAX_LINE_LEN 132

typedef enum {false, true} bool;
  
  /*prototypes*/
  void cat_words(char*);
  int isCommand(char*);
  void cmd_LW(char*);
  void cmd_LM(char*);
  void cmd_LS(char*);
  void indent();
  void lineSpacing();
  void cat_buffer(char*);
  
 char words[MAX_WORDS];
 char output_arr[MAX_WORDS];
 int num_words;
 int ch_count;

 int LW_width;
 int LM_left = 0;
 int LS_spaces;
 bool formatting = false;

 static int total_c;

 /*
 * modified original code supplied by Mike Zastre in tokenizeD.c
 * for cat_words and chomp.
 * Processes a buffer line of input - tokenizing each word 
 */
 void cat_words(char *buffer){         
 	char* d;
 	 	/*determine linespacing?????*/
 	if(strncmp(buffer, "\n", 1) == 0){

 		/*//printf("DEBUG: buffer = <%s>\n",buffer);
 		// We have a blank line so finish the prev
 		// line if we are in the middle*/
 		if (ch_count > 0){
 			strncat(words, "\n", MAX_LINE_LEN);
 			lineSpacing();
 			ch_count++;
 		  	total_c++;
 		}
 		
 		/* add this blank line */
 		strncat(words, "\n", MAX_LINE_LEN);
 		ch_count = 0;
 		total_c++;
 		lineSpacing();
 		/*indent();

 		total_c += LM_left;
 		ch_count = LM_left;*/
 		
 	}
	
	d = strtok(buffer, " \n\0"); /*a space is specified as the
	separator parameter for tokenizing each word*/

 	if(strncmp(buffer, ".LW", 3) == 0){
 		d = strtok(buffer, "\n\0");
 		d = strtok(NULL, "\n\0");
 	} else if(strncmp(buffer, ".LM", 3) == 0){
 		d = strtok(buffer, "\n\0");
 		d = strtok(NULL, "\n\0");
 	} else if(strncmp(buffer, ".LS", 3) == 0){
 		d = strtok(buffer, "\n\0");
 		d = strtok(NULL, "\n\0");
 	} 

	/*indents first line*/
 	/*
 	if(total_c == 0){
 		indent();
 		total_c = LM_left;
 		ch_count = LM_left;
 	}*/

	while(d) {
		int n = strlen(d);
		/*ch_count += strlen(d);*//*keep track of characters in line*/
		
		
		if(ch_count + n +1 > LW_width){
			/*printf("ch_count = %d, n = %d\n", ch_count, n);*/
			strncat(words, "\n", MAX_LINE_LEN);
			lineSpacing();
			total_c++;
			/*indent();
 			total_c += LM_left;
 			ch_count = LM_left;*/
 			ch_count = 0;
 		} else if(ch_count > LM_left){
 			strncat(words," ", MAX_WORD_LEN);
 			ch_count++;
 			total_c++;
 		} 
 		if(ch_count == 0) {
 			indent();
			total_c += LM_left;
 			ch_count = LM_left;
 		}

		strncat(words, d, MAX_WORD_LEN);/*adds tokenized word to words array*/
		total_c += n;
		ch_count += n;
 		d = strtok(NULL, " \n\0"); /*if you didn't pass NULL here,
		it'd start tokenizing the string again from the beginning*/
	}	
 }

/*adds unformatted buffer to words array*/
void cat_buffer(char *buffer){  
	strncat(words, buffer, MAX_LINE_LEN);
	total_c += strlen(buffer);
}

/*indents LM_left spaces from the left-hand margin*/
void indent(){         
 	int i;
 	for(i = 0; i < LM_left; i++){
 		strncat(words, " ", 1);
	}
 }

void lineSpacing(){
	int i;
 	for(i = 0; i < LS_spaces; i++){
 		strncat(words, "\n", 1);
 		total_c++;
	}
}

/*determines if the line contains a formatting command*/
/* Return 1 if this was a formatting on/off command, 0 otherwise */

int isCommand(char* buffer){
	int isFormattingCommand = 0;

	if(strncmp(buffer, ".LW", 3) == 0){
		/*determines width specified*/
		cmd_LW(buffer);
		formatting = true;
	} else if(strncmp(buffer, ".LM", 3) == 0){
		/*determines the left spaces*/
		cmd_LM(buffer);
		/*set .FT on*/
		formatting = true;
	}else if(strncmp(buffer, ".FT on", 6) == 0){
		/*if formatting is on, continue formatting*/
		formatting = true;
		isFormattingCommand = 1;
	}else if(strncmp(buffer, ".FT off", 7) == 0){
		/*if formatting is off, print input as is*/
		formatting = false;
		isFormattingCommand = 1;
	}else if(strncmp(buffer, ".LS", 3) == 0){
		/*determines the number of linespacing*/
		cmd_LS(buffer);
		formatting = true;
	}
	return(isFormattingCommand);
}
/*assigns an integer to the .LW width specified by input*/
void cmd_LW(char *buffer){
	char* d;
	
	d = strtok(buffer, ".LW ");
	
	LW_width = atoi(d);
}
/*assigns an integer to the .LM left spacing specified by input*/
void cmd_LM(char *buffer){
	char* d;
	
	d = strtok(buffer, ".LM ");
	
	LM_left = atoi(d);

}
/*assigns an integer to the .LS left spacing specified by input*/
void cmd_LS(char *buffer){
	char* d;
	
	d = strtok(buffer, ".LS ");
	
	LS_spaces = atoi(d);
	/*printf("linespacing: %d\n", LS_spaces);*/

}

int main(int argc, char *argv[]) {
	int isFormattingCommand; 
	FILE *d_file;
	char buffer[MAX_LINE_LEN];
	char *input = argv[1];
	d_file = fopen(input, "r");
	fprintf(stderr, "Echoing input file...\n");
	while (fgets(buffer, MAX_LINE_LEN, d_file)) { /*reads input text to buffer array*/
		/*fprintf(stderr, "%s", buffer);*//*echos input file*/
		isFormattingCommand = isCommand(buffer);

		if(formatting){
			cat_words(buffer);/*removes white space*/
		} else if (isFormattingCommand != 1) {
			/*formatting is off*/
			
			cat_buffer(buffer);
		}
	}
	if(ch_count > 0){
		strncat(words, "\n", MAX_LINE_LEN);
		total_c++;
	}
	/*
	if(!formatting){
		words[total_c] = '\n';
		words[total_c+1] = '\0';
	}else{
		words[total_c] = '\0';
	}else{

	}*/

	fprintf(stderr, "the formated text output is:\n");
	fprintf(stdout, "%s", words);

	/*for(i = 0; i<num_words;i++){
		printf("%s\n", words);
	}*/
	
	return 0;
 }