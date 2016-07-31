/*
 * WaxmanHW12.cpp
 *As discussed in class ...
Modify the Eight Queens program (1 dimensional array – no goto version) so that it prints out a
chessboard with some “fancy” representation of a queen in the appropriate positions.
How to do it:
In class we went over the code to print out a chessboard. You need to:
1. Augment that code by adding two additional “box”es, wq which represents a picture of a
queen placed in a “white” square, and bq, representing a picture of a queens placed in a “black”
square.
2. After the code that fills the array board[8][8] with the addresses of bb and wb, insert code to
change eight entries in that array to reflect the positions of eight queens on the board. You
will get these positions from a one dimensional array q[8] representing a solution to the eight
queens problem. The change that you make in these eight places is to replace the pointer in board
for one representing either a wq, or bq, as appropriate for that position. You know the eight
positions because, given q[i], i represents the column and q[i] represents the row. You can tell
whether its black or white by looking at the row and column indexes of its position on the board.
3. The code described above (in 2.) goes into the print function. You pass the arrays q and
board to print. In the function you
• modify board using q
• print the picture of the board
• “Clean up” array board, by restoring its original values, to get ready for the next call to
print.
 *  Created on: Mar 18, 2016
 *      Author: andrew
 */

#include <iostream>
#include <cstdlib>
#include <cmath>
using namespace std;

void print(int q[])
{
	static int solution=0;
	int i,j,k,l;

	typedef char box[5][7];

	box BlackBox,WhiteBox,BlackQueen,WhiteQueen,*Board[8][8];
	char H=char(219);

    //Create Black Queen Image on White box
    for(i=0;i<5;i++)
    {
        for(j=0;j<7;j++)
        {
            if(i==0||i==4) BlackQueen[i][j]=' ';
            if(i==1)
            {
                if(j%2==0)
                {
                	BlackQueen[i][j]=' ';
                }
                else
                {
                	BlackQueen[i][j]=H;
                }
            }
            if(i==2||i==3)
            {
                if(j==0||j==6)
                {
                	BlackQueen[i][j]=' ';
                }
                else
                {
                	BlackQueen[i][j]=H;
                }
            }
        }
    }

	//Create White Queen Image by doing the inverse of Black Queen Image
	for(i=0;i<5;i++)
	{
		for(j=0;j<7;j++)
		{
			if(BlackQueen[i][j]==' ')
			{
				WhiteQueen[i][j]=char(219);
			}
			if(BlackQueen[i][j]==H)
			{
				WhiteQueen[i][j]=' ';
			}
		}
	}

	//Create Both White box and Black box of a Chess board
	for(i=0;i<5;i++)
		for( j=0;j<7;j++)
		{
			WhiteBox[i][j]=' '; //Shoot "blank" or white
			BlackBox[i][j]=char(219);
		}

	//fill board with pointers to Black box and White box in alternate positions to create empty Chess board
	for(i=0;i<8;i++)
	{
		for(j=0;j<8;j++)
		{
			if((i+j)%2==0)
			{
				Board[i][j]=&WhiteBox;
			}
			else
			{
				Board[i][j]=&BlackBox;
			}
		}
	}
	// print the board via the pointers in array board
	for(i=0;i<8;i++)
	{
		for(j=0;j<8;j++)
		{
			if(q[j]==i)
			{
				if((Board[i][j])==&WhiteBox)
				{
					Board[i][j]=&BlackQueen;
				}
				else
				{
					Board[i][j]=&WhiteQueen;
				}
			}
		}
	}

	//Print Solution Number
	cout << endl << "SOLUTION " << ++solution << endl;

	//Print the Board
	for(i=0;i<8;i++)
	{
		for(k=0;k<5;k++)
		{
			cout<<" "<<char(179); //print left border
			for(j=0;j<8;j++)
			{
				for(l=0;l<7;l++)
				{
					cout<<(*Board[i][j])[k][l];
				}
			}
			cout<<char(179)<<endl;
		}
	}
	cout<<" ";

	for(i=0;i<7*8;i++)
	{
		cout<<char(196);
	}
	cout<<endl;

    //Reset Chess Board
    for(i=0;i<8;i++)
    {
		for(j=0;j<8;j++)
			if((i+j)%2==0)
			{
				Board[i][j]=&WhiteBox;
			}
			else
			{
				Board[i][j]=&BlackBox;
			}
    }
}

bool ok(int Queens[], int column)
{
    for(int i=0; i<column; i++)
    {
        if(Queens[column]==Queens[i] || (column-i)==abs(Queens[column]-Queens[i]))
        {
        	return false;
        }
    }
    return true;
}

void backtrack(int &column)
{
    column--;
    if(column==-1)
    {
    	exit(1);
    }
}

int main()
{
    int Queens[8]={0};
    int column=0;
    bool from_backtrack=false;

    while(true)
    {
        while(column<8)
        {
        	if(!from_backtrack)
        	{
        		Queens[column]=-1;
            	while(Queens[column]<8)
            	{
            		Queens[column]++;
            		if(Queens[column]==8)
            		{
            			backtrack(column);
            			continue;
            		}
            		if(ok(Queens,column))
            		{
            			break;
            		}
            	}
            	column++;
            	from_backtrack=false;
        	}
        }
        print(Queens);
        backtrack(column);
        from_backtrack=true;
    }
    return 0;
}


