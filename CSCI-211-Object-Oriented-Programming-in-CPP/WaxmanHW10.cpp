/*
s * Programming Problem
Stable Marriage Using Backtracking
The Problem:
You have n men and n woman, and their preference rankings of each other, and you need
to match them up so that the total matching is “stable.”
The preference rankings:
You are given 2 n X n arrays, mp (men’s preference) which gives the men’s ranking of the
women, and wp (women’s preference) which gives the women’s ranking of the men.
So mp[i][j] gives man i's ranking of woman j and likewise for the women’s ranking of the men in
wp.
For example in the following tables we have n=3 and the women and men are “named” 0, 1 or 2
and the raking are in the range 0 = highest, 1 second highest and 2 lowest.
int mp[3][3]={0,2,1,
0,2,1,
1,2,0};
int wp[3][3]={2,1,0,
0,1,2,
2,0,1};
So man 1 assigns woman 2 the rank of 1 (i.e. second highest) and prefers woman 0 the best.
What is a stable matching?
A matching is stable if there are no “instabilities.” Say the match assigns m1 to w1 and m2 to
w2. An instability is the situation where there is a mutual greater preference for the other person’s
partner than for one’s own. For example m1 would prefer w2 to w1 and likewise w2 prefers m1
to m2.
How to do it:
Use the same approach that we used for the one dimensional eight queens problem. In the array q,
q[i] is the woman assigned to man i.
The main program stays the same, besides the limits on the loops. All that needs to change is the
ok function. Is could look something like this:
bool ok(int q[], int col) {
col indicates the new man and q[col] the new woman proposed to be added to the
match.
We need to do 2 tests:
1. If the new woman has already been assigned to some man then return false
2. Check the new pair (new man, new woman) against each existing pair consisting of
(current man, current woman) to see if the new pair would make the match unstable. So
a. if the current man prefers the new woman to his partner and
b. if the new woman prefers the current man to her partner
i. this is unstable, so return false
c. if the new man prefers the current woman to his partner and
d. if the current woman prefers the new man to her partner
i. This is unstable, so return false
e. if there are no instabilities introduced with any of the existing (i.e. “current”)
couples, so we return true.
}Example: Here are the preference tables MP and WP from before.
Will “2 0 1” be a solution? This is the following match:
Man
0
1
2
Woman
2
0
1
What about “2 1 0”?
Input Data:
For this program use the arrays mp and wp above. The data will thus be “given” and not obtained
by reading it in.
Output:
Print out all stable matchings, one per line. This is the same output that we did with the one
dimensional eight queens program.
 */

#include <iostream>
#include <cstdlib>
#include <cmath>
using namespace std;

bool ok(int q[], int col)
{

    int mp[3][3]={
    		{0, 2, 1},
    		{0, 2, 1},
			{1, 2, 0}};
    int wp[3][3]={
    		{2, 1, 0},
			{0, 1, 2},
			{2, 0, 1}};

    int current_man, current_woman, new_man, new_woman;

    for(int i=0; i<col; i++)
    {
        current_man=i;
        current_woman=q[i]; //woman assigned to man i
        new_man=col;
        new_woman=q[col];
        if(new_woman==current_woman)
        {
        	return false;
        }
    }

    for(int i=0; i<col; i++)
    {
        current_man=i;
        current_woman=q[i]; //woman assigned to man i
        new_man=col;
        new_woman=q[col];

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
};

void backtrack(int &col)
{
    col--;
    if(col==-1)
    {
    	exit(1);
    }
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
    cout << endl << endl;
}

int main()
{
    int q[3]; q[0]=0;
    int c=0;
    bool from_backtrack=false;

    while(true)
    {
        while(c<3)
        {
        if(!from_backtrack)
            q[c]=-1;
            while(q[c]<3)
            {
                q[c]++;
                if(q[c]==3)
                {
                    backtrack(c);
                    continue;
                }
                if(ok(q,c)) break;
            }
            c++;
            from_backtrack=false;
        }
        print(q);
        backtrack(c);
        from_backtrack=true;
    }
    return 0;
}
