/*
 * WaxmanHW19.cpp
 *
 *  Created on: Apr 18, 2016
 *      Author: andrew
 */
#include <cstdlib>
#include <iostream>
using namespace std;

int k; //number of bishops
int n; //size of the board

//q is an array of squares, where b is a bishop and q[b] is the square the bishop occupies
bool ok(int q[], int b, int size)
{
    //identify the row and column the bishop is in
    int row = q[b]/size, column = q[b]%size, current_row, current_column;

    for(int i=0; i<b; i++)
    {
        current_row = q[i]/size;
        current_column = q[i]%size;

        if(abs(row-current_row)==abs(column-current_column))
        {
        	return false;
        }
    }
    return true;
}

void backtrack(int &bishop, int count)
{
    bishop--;
    if(bishop==-1)
    {
cout << k << " bishops can be placed on an " << n << " by " << n << " chessboard in " << count << " different ways.";
exit(1);
    }
}

int main()
{
    while(true)
    {
        //User input
        cout << "Enter n: ";
        cin >> n;
        cout << "Enter k: ";
        cin >> k;

        //Solution/backtracking algorithm
        int* q = new int[k]; //q[bishop] is a square on the n*n grid
        int count = 0, b = 0, t;
        q[b] = 0;

        while(true)
        {//While 1: until we found all the possible solutions
            while(b<k)
            {//While 2: for each bishop
                while(q[b]< n*n)
                { //for each square
                    if(ok(q,b,n)) { break; }
                    else { q[b]++; }
                }

                if(q[b]==n*n)
                {
                    backtrack(b, count);
                    q[b]++;
                    continue;
                }

                else
                {
                    t = q[b];
                    b++;
                    q[b] = t;
                }
            }//While 2
            count++;
            backtrack(b, count);
            q[b]++;
        }//While 1
    }//End of the WHOLE Loop
    return 0;
}
