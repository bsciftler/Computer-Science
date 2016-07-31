/*
 * WaxmanHW17A.cpp
 *
 *  Created on: Apr 10, 2016
 *  RECURSIVE STABLE MARRIAGE
 *      Author: andrew
 */
#include <iostream>
#include <cstdlib>
using namespace std;

bool ok(int matches[], int column)
{

    int mp[3][3]=
    {
    		{0, 2, 1},
			{0, 2, 1},
			{1, 2, 0}
    };

    int wp[3][3]=
    {
    			{2, 1, 0},
                {0, 1, 2},
                {2, 0, 1}
    };

    int current_man, current_woman, new_man, new_woman;

    for(int i=0; i<column; i++)
    {
        current_man=i;
        current_woman=matches[i]; //woman assigned to man i
        new_man=column;
        new_woman=matches[column];
        if(new_woman==current_woman)
        {
        	return false;
        }
    }

    for(int i=0; i<column; i++)
    {
        current_man=i;
        current_woman=matches[i]; //woman assigned to man i
        new_man=column;
        new_woman=matches[column];

        if(mp[current_man][current_woman]>mp[current_man][new_woman]&&
           wp[new_woman][current_man]<wp[new_woman][new_man])
        {
        	return false;
        }

        if(mp[new_man][current_woman]<mp[new_man][new_woman]&&
           wp[current_woman][new_man]<wp[current_woman][current_man])
        {
        	return false;
        }
    }
    return true;
}

void print(int q[])
{
    static int count=0;
    cout << "SOLUTION " << ++count << endl;
    cout << "Man Woman" << endl;
    for(int i=0; i<3; i++)
    {
        cout << " " << i << "    " << q[i] << endl;
    }
    cout << endl;
}

void move(int* matches, int i)
{
    if(i==3)
    {
        print(matches);
        return;
    }

    for(int j=0; j<3; j++)
    {
        matches[i]=j;
        if(ok(matches,i))
        {
        	move(matches,i+1);
        }
    }
}

int main()
{
    int Matches[3];
    move(Matches,0);
    return 0;
}
