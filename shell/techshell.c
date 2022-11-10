//Jayden Tarrance

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>

void input();

void process(char **args, int counter){
		
	pid_t pid = fork();

	//if child process
	if (pid == 0){
		char *args_clean[counter+1];
		int cleanidx = 0;
		//compares each arg for file redirection
                for (int i = 0; i < counter; i++){
			//if file input
			if (strcmp(args[i], "<") == 0){
				//opens file
				FILE* infile = fopen(args[i+1], "r");
				//checks file and prints error if there is one
				if (infile == NULL){
					printf("Error %d (%s)\n", errno, strerror(errno));
					exit(1);
				}
				//redirects stdin from infile
				dup2(fileno(infile), 0);
				fclose(infile);
				i++;
				continue;
				
			}
			//if file output
                        if (strcmp(args[i], ">") == 0){
				//opens file
                                FILE* outfile = fopen(args[i+1], "w");
				//redirects stdout to outfile
                                dup2(fileno(outfile), 1);
                                fclose(outfile);
                                ++i;
                                continue;
                        }
			//saves args into args_clean excluding < or >
                        args_clean[cleanidx++] = args[i];
                }
		//null terminates args_clean
		args_clean[cleanidx] = NULL;
		
		//executes the command or prints error 
		if (execvp(args_clean[0], args_clean) != 0){
			printf("Error %d (%s)\n", errno, strerror(errno));	
			exit(1);
		}
	
	}
	int status;
	wait(&status);
	if (WIFEXITED(status)){
		int exit_status = WEXITSTATUS(status);
		if (exit_status != 0){
			//printf("Exit Status: %d\n", exit_status);
		}
	}
	input();
}

void pwd(char **args, int counter){
	char pwd[100];
	//checks for right num of arg
	if (counter > 1){
		printf("Too many arguments...pwd Usage: pwd\n");
	}
	if (counter == 1){
		printf("%s\n", getcwd(pwd, 100));
	}
	input();
}
void cd(char **args, int counter){
	//checks for correct num arg and prints usage
	if (counter > 2){
		printf("Too many arguments...cd Usage: cd /home\n");
	}
	if (counter < 2){
		printf("Not enough arguments...cd Usage: cd /home\n");
	}
	//if right amount of arg then change dir or print error if there is one
	if (counter == 2){
		if (chdir(args[1]) == 0){
			chdir(args[1]);
		}
		else {
			printf("Error %d (%s)\n", errno, strerror(errno));
		}
	}
	input();
}

void tokenizer(char * input){
	char * tokens;
	int counter = 0;
	char *args[256];
	//seperates the input by spaces and saves into array args
	tokens = strtok(input," ,\n");
	while (tokens != NULL){
		args[counter] = tokens;
		tokens = strtok(NULL," ,\n");
		counter += 1;
	}
	//if the first arg is cd 
	if (strcmp(args[0], "cd") == 0){
		cd(args, counter);	
	}
	//if the first arg is pwd
	else if (strcmp(args[0], "pwd") == 0){
		pwd(args, counter);
	}
	else {
		//if not cd or pwd or exit then send to process
		process(args, counter);
	}
}

void input(){
        char input[256];
        char cwd[100];
	//print the cwd and get user input
        printf("%s$ ", getcwd(cwd, 100));
        fgets(input, 256, stdin);
	//exit command
        if (strcmp(input, "exit\n") == 0){
                exit(0);
        }
        tokenizer(input);
}

void main (){
	input();
}	
