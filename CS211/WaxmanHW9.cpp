/*
 * WaxmanHW9.cpp
 *
 *  Created on: Feb 25, 2016
 *      Author: andrew
 *		HW 9
 *		8 Queens problem, but the most inefficient way.
 */

#include<cmath>
#include<fstream>
#include<iostream>
using namespace std;

bool ok(int board[][8])
{
    int Queens=0;

    for(int row=0; row<8; row++)
    {
        for(int column=0; column<8; column++)
        {
            //Rows test
            if(board[row][column]==1)
            {
            	Queens++;
            }
            if(Queens>1)
            {
            	return false;
            }
            //Diagonals test
            for(int j=1; ((column-j)>=0)&&((row-j)>=0); j++)
            {
            	if(board[row-j][column-j]==1 && board[row][column]==1)
            	{
            		return false;
            	}
            }
            for(int k=1; ((column-k)>=0)&&((row+k)<8); k++)
            {
            	if(board[row+k][column-k]==1 && board[row][column]==1)
            	{
            		return false;
            	}
            }
        }
        Queens=0;
    }
    return true;
}

void print(int board[][8], int count)
{
    cout << "SOLUTION " << count << ": " << endl;
	for(int row=0; row<8; row++)
	{
		for(int column=0; column<8; column++)
		{
			cout << board[row][column] << " ";
		}
		cout << endl;
	}
	cout << endl;
}

int main(){

    int board[8][8]={0};
    int count = 0;

    for(int A =0; A < 8; A ++)
        for(int B =0; B < 8; B++)
            for(int C =0; C < 8; C++)
                for(int D =0; D < 8; D++)
                    for(int E =0; E < 8; E++)
                        for(int F =0; F < 8; F++)
                            for(int G=0; G < 8; G++)
                                for(int H =0; H < 8; H++)
                                {
                                	//Set up Board
                                    board[A][0]=1;
                                    board[B][1]=1;
                                    board[C][2]=1;
                                    board[D][3]=1;
                                    board[E][4]=1;
                                    board[F][5]=1;
                                    board[G][6]=1;
                                    board[H][7]=1;

                                    // if this configuration is conflict-free, print the count and the board
                                    if(ok(board))
                                    {
                                    	print(board, ++count);
                                    }

                                    // reset the board for the next configuration
                                    board[A][0]=0;
                                    board[B][1]=0;
                                    board[C][2]=0;
                                    board[D][3]=0;
                                    board[E][4]=0;
                                    board[F][5]=0;
                                    board[G][6]=0;
                                    board[H][7]=0;
                                }
    return 0;
}



