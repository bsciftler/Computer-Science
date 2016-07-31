/*
 *      Author: andrew
 *      8 Queens 1D WITH GOTO
 *
 */
#include<iostream>
#include <cmath>

using namespace std;

int main()
{

int q[8],c=0, count=1;
    q[0]=0;

nc:c++; //next column

if (c==8)
{
	goto print;
}
	q[c]=-1; //-1 to get top row when we use nr: r++
	nr:q[c]++;

	if (q[c]==8)
	{
		goto backtrack;
	}
	for (int i=0; i<c; i++)
	{
		if (q[c]==q[i] || c-i==abs(q[c]-q[i]))
		{
			goto nr; //all row/diagonal tests
		}
	}
	goto nc;

backtrack:c--;

	if (c==-1)
	{
		return 0;
	}
	goto nr;

	print: cout <<count <<":" <<endl;
	count ++;

	for (int r=0; r<8; r++)
	{

	  for (int c=0; c<8; c++)
	  {
		  if (q[c]==r) cout <<1;
		  else cout << 0;
      }
	  cout <<endl;
	}
	  goto backtrack;
}

