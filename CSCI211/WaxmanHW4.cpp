/*
 * Introduction
Some number of teams are participating in a race. You are not told how many teams are participating but
you do know that:

Each team has a name, which is one of the uppercase letters A-Z.
No two teams have the same name, so there are a maximum number of 26 teams.
Each team has the same number of members.
No two runners cross the finish line at the same time – i.e. there are no ties.
At the end of the race we can write the results as a string of characters indicating the order in which
runners crossed the finish line.
For example: ZZAZAA
We can see there were two teams: A and Z. Team A’s runners finished in 3 rd , 5 th and 6 th place. Team Z’s
runners finished in 1 st , 2 nd and 4 th place.
Scoring the race
Each runner is assigned a score equal to their finishing place. In the example above team Z’s runners
achieved scores of 1, 2 and 4. Team A’s runners scores were 3, 5, and 6 respectively.
The team’s score is the sum of the members score divided by the number of people on the team. So team
A’s score is (3+5+6)/3 = 14/3=4.66 and team Z’s score is (1+2+4)/3=7/3=2.33
The Problem
Write a program to score races as described above.
Input
Your program will ask the user to input a string of uppercase characters indicating the outcome of a race.
Output
Your program will output:




The number of teams.
The number of runners on a team.
The names of the teams – in alphabetical order - together with the team score.
The name and score of the winning team.
So for the example above the program will print:
There are 2 teams.
Each team has 3 runners.
Team Score
A
4.66
Z
2.33
The winning team is team Z with a score of 2.33.
Your program should loop asking for input processing the data until the user enters “done”.
Your program should detect the error condition where teams do not have the same number of runners. In
that case it should print an error message, and resume by requesting the next input.s
 */


#include <iostream>
#include <string>

using namespace std;

int main ()
{

string input;
char Alphabet[26]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
double teamScores[26] = {0}; //For Team Score, 0 Holds A's Score, 1 holds B's Score, 2 holds C's score, etc.
int teamCount[26] = {0}; //Counts how many runners in a specific team. A=0,B=1,C=2, etc.
int runners=0;
int PositionCounter=0;
while (cin >> input)
{
	if (input == "done") //exit the loop
    {
		break;
    }
	if (PositionCounter>=26) //exit loop if array of bounds can happen.
	{
		break;
	}
    //Parse Input to calculate team scores
    for (int i=0; i<input.size(); i++)
    {
          if((int)input[i] >= 97 && (int)input[i]<=122) //LOWER CASE LETTERS
          {
        	  input[i]=toupper(input[i]);//Convert this to capital letter first
        	  goto Calc; //Using go to skip and process as capital letter.
          }
          else if((int)input[i] >= 65 && (int)input[i]<=90) //UPPER CASE LETTERS
          {
              Calc:
			  runners++;
        	  teamCount[((int)input[i]%65)]++;
              teamScores[((int)input[i]%65)]+=(i+1);
          }
          else //Invalid Entry found
          {
        	  cout << "Invalid team name detected. Closing program";
        	  return 0;
          }
    }
}//END OF WHILE LOOP

//Final Calculations
int WinnerPos=0;
for (int i=1;i<26;i++)
{
	if (teamScores[WinnerPos] > teamScores[i] && teamScores[i]!=0)
	{
		WinnerPos=i;
	}
}

int TeamCounter=0;
for (int i=0;i<26;i++)
{
	if (teamCount[i]!=0)
	{
		TeamCounter++;
		teamScores[i]=teamScores[i]/teamCount[i];
		cout << Alphabet[i] << " scored " << teamScores[i] << " points" << endl;
	}
}

cout << "There are " << runners << " runners competing in this race for " << TeamCounter << " teams." << endl;
cout << "The winner is..." << Alphabet[WinnerPos] << " with a score of " << teamScores[WinnerPos] << endl; //LOWEST SCORE WINS!

}//END OF MAIN

