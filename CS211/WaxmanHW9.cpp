/*
 * WaxmanHW9.cpp
 *
 *  Created on: Feb 25, 2016
 *      Author: andrew
 *		HW 9
 *		8 Queens problem, but the stupid way, brute force way
 */

#include<cmath>
#include<fstream>
#include<iostream>
using namespace std;

bool ok(int b[][8])
{
    int rQueens=0, dQueens=0;

    for(int row=0; row<8; row++){
        for(int column=0; column<8; column++)
        {
            //Rows test
            if(b[row][column]==1)
            {
            	rQueens++;
            }
            if(rQueens>1)
            {
            	return false;
            }

            //Diagonals test
            for(int j=1; ((column-j)>=0)&&((row-j)>=0); j++)
            {
            	if(b[row-j][column-j]==1&&b[row][column]==1)
            	{
            		return false;
            	}
            }
            for(int k=1; ((column-k)>=0)&&((row+k)<8); k++)
            {
            	if(b[row+k][column-k]==1&&b[row][column]==1)
            	{
            		return false;
            	}
            }
        }
        rQueens=0;
    }

    return true;
}

void print(int b[][8], int count)
{
    cout << "SOLUTION " << count << ": " << endl;
	for(int row=0; row<8; row++)
	{
		for(int column=0; column<8; column++)
		{
			cout << b[row][column] << " ";
		}
		cout << endl;
	}
	cout << endl;
}

int main(){

    int board[8][8]={0};
    int count = 0;

    for(int i0 =0; i0 <8; i0 ++)
        for(int i1 =0; i1 <8; i1 ++)
            for(int i2 =0; i2 <8; i2 ++)
                for(int i3 =0; i3 <8; i3 ++)
                    for(int i4 =0; i4 <8; i4 ++)
                        for(int i5 =0; i5 <8; i5 ++)
                            for(int i6 =0; i6 <8; i6 ++)
                                for(int i7 =0; i7 <8; i7 ++)
                                {
                                	//Set up Board
                                    board[i0][0]=1;
                                    board[i1][1]=1;
                                    board[i2][2]=1;
                                    board[i3][3]=1;
                                    board[i4][4]=1;
                                    board[i5][5]=1;
                                    board[i6][6]=1;
                                    board[i7][7]=1;

                                    // if this configuration is conflict-free, print the count and the board
                                    if(ok(board)) print(board, ++count);

                                    // reset the board for the next configuration
                                    board[i0][0]=0;
                                    board[i1][1]=0;
                                    board[i2][2]=0;
                                    board[i3][3]=0;
                                    board[i4][4]=0;
                                    board[i5][5]=0;
                                    board[i6][6]=0;
                                    board[i7][7]=0;
                                }
    return 0;
}



