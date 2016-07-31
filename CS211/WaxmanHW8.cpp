/*
 * WaxmanHW8.cpp
 *
 *  Created on: Feb 25, 2016
 *      Author: andrew
 *      Homework 8
 *		8 numbers in a cross problem
 *		Write a program which allocates the integers 1-8 to the squares in the figure above, subject to
the restrictions that no two adjacent squares contain consecutive integers_ _
 _|1|2|_
|0|3|4|7|
  |5|6|
 */

#include <cstdlib>
#include <iostream>
using namespace std;

void backtrack(int &square)
{
    square--;
    if(square==-1)
	{
    	exit(0);
    }
}

bool ok(int q[8], int square)
{
    int adjacents[8][4]={{-1, -1, -1, -1},
                        {0, -1, -1, -1},
                        {1, -1, -1, -1},
                        {0, 1, 2, -1},
                        {1, 2, 3, -1},
                        {0, 3, 4, -1},
                        {3, 4, 5, -1},
                        {2, 4, 6, -1}};
    for(int i=0; i<square; i++)
    {
        if(q[i]==q[square])
        {
        	return false;
        }
    }
    for(int j=0; adjacents[square][j]!=-1; j++)
    {
        if(abs(q[square]-q[adjacents[square][j]])==1)
        {
        	return false;
        }
    }
    return true;
}

void print(int q[8])
{
    cout << endl;
    cout << "  " << q[1] << " " << q[2] << endl;
    cout << q[0] << " " << q[3] << " " << q[4] << " " << q[7] << endl;
    cout << "  " << q[5] << " " << q[6] << endl;
}

int main()
{
    int q[8]={0}, square=0;
    bool from_backtrack=false;

    while(true)
    {
        while(square<8)
        {
        	if(!from_backtrack)
        	{
        		q[square]=0;
        	}

            while(q[square]<9)
            {
                q[square]++;
                if(q[square]==9)
                {
                    backtrack(square);
                    continue;
                }
                if(ok(q,square))
                {
                	break;
                }
            }
            square++;
            from_backtrack=false;
        }
        print(q);
        backtrack(square);
        from_backtrack=true;
    }
    return 0;
}
