/*
 * Solve 8 Queens Problem in a 2D arrau
 * WaxmanHW5.cpp
 */

#include <iostream>
#include <fstream>
using namespace std;

void print (int board [] [8])
{
	static int count=1;
	cout << "Solution #" << count++ << ":";

	for (int i=0; i < 8; i++)// Both have 8
	{
		cout << endl;
		for (int j =0; j < 8; j++)
		{
			cout << board[i][j];
		}
	}
	cout << " " << endl;
}

int main ()
{
int board [8][8]={0};
board [0][0] = 1;
int column=0, row;

		NC: column++;
		if (column==8)
		{
			print(board);
			goto BackTrack;
		}
			row=-1;
			NR: row++;
			if (row==8)
			{
				goto BackTrack;
			}

			//Three Tests
			for (int i=1;(column-i)>=0;i++)
			{
				if (board[row][column-i]==1)
				{
					goto NR; //Go to Next Row
				}
			}

			for (int i=1;(row-i)>=0 && (column-i)>=0; i++)
			{
				if(board[row-i][column-i]==1)
				{
					goto NR; // Go to Next Row
				}
			}
			for (int i=1;(row+i)<=7 && (column-i) >=0; i++)
			{
				if (board[row+i][column-i]==1)
				{
					goto NR; //Go to Next Row
				}
			}
		board[row][column]=1;//Place Queen
		goto NC; //Next Column
//If it fails to place a queen.
	BackTrack: column--;
	if (column==-1)
	{
		return 0;
	}
	row=0;
	while (board[row][column] !=1)
	{
		row++;
	}
	board[row][column]=0;//Delete Queens
	goto NR;//Start again
}//End of Main
