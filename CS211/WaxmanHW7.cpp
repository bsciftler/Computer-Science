/*
 * WaxmanHW7.cpp
 *
 *  Created on: Feb 18, 2016
 *      Author: andrew
 *      1D Array 8 Queens with 1D array WITHOUT goto
 */

#include <iostream>
#include <cstdlib>
using namespace std;

bool ok (int board []) //Check if it is valid location to place a Queen
{
	for (int i=7;i>=0;i--)
	{
		for (int j=0;j<i;j++)
		{
			if (board[i]==board[j]||(i-j)==abs(board[i]-board[j]))
			{
				return false;
			}
		}
	}
	return true;
}

void print (int board [])
{
	static int count=1;
	cout << "Solution #" << count++ << ":" << endl;
	for (int i=0;i<8;++i)
	{
		cout << board[i] << " ";
	}
	cout << endl;
}

int main()
{
	int board[8]={0};
	for (board[0]=0;board[0]<8;board[0]++)
	{
		for (board[1]=0;board[1]<8;board[1]++)
		{
			for (board[2]=0;board[2]<8;board[2]++)
			{
				for (board[3]=0;board[3]<8;board[3]++)
				{
					for (board[4]=0;board[4]<8;board[4]++)
					{
						for (board[5]=0;board[5]<8;board[5]++)
						{
							for (board[6]=0;board[6]<8;board[6]++)
							{
								for (board[7]=0;board[7]<8;board[7]++)
								{
									if(ok(board))
									{
											print(board);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	return 0;
}


