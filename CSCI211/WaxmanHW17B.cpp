/*
 * WaxmanHW17B.cpp
 *
 *  Created on: Apr 11, 2016
 *      Author: andrew
 *      RECURSIVE 8 NUMBERS IN A CROSS
 */
#include <iostream>
#include <cstdlib>
using namespace std;

bool ok(int cross[8],int c)
{
         int a[8][4]=
         {
        		 {-1},
				 {0,-1},
				 {1,-1},
				 {0,1,2,-1},
				 {1,2,3,-1},
				 {0,3,4,-1},
				 {3,4,5,-1},
				 {2,4,6,-1}
         };
         for(int i=0;i<c;i++)
         {
                if(cross[i]==cross[c])
                {
                	return false;
                }
         }
         for(int i=0; a[c][i]!=-1;i++)
         {
                if(abs(cross[c]-cross[a[c][i]])==1)
                {
                	return false;
                }
         }
         return true;
}

void print(int q[8])
{
    static int count=0;
    cout << "SOLUTION " << ++count << endl;
    cout << "  " << q[1] << " " << q[2] << endl;
    cout << q[0] << " " << q[3] << " " << q[4] << " " << q[7] << endl;
    cout << "  " << q[5] << " " << q[6] << endl << endl;
}

void move(int* answer, int i)
{
    if(i==8)
    {
        print(answer);
        return;
    }

    for(int j=1; j<=8; j++)
    {
        answer[i]=j;
        if(ok(answer,i))
        {
        	move(answer,i+1);
        }
    }
}

int main()
{
    int answer[8];
    move(answer,0);
    return 0;
}
