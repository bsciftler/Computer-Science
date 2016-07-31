/*
 *      Author: andrew
 *      8 Queens 2D WITH GOTO AND NO ROW VALUE.
 */
#include<iostream>
#include <cmath>

using namespace std;

int main()
{

int Queens[8]={0},column=0, count=1;

NC:column++; //next column

if (column==8)
{
	goto print;//Print solution
}
	Queens[column]=-1; //-1 to get top row then use nr++ to get to 0.
	NR:Queens[column]++;

	if (Queens[column]==8)
	{
		goto BackTrack; //Invalid entry, go backtrack.
	}
	for (int i=0; i<column; i++)
	{
		if (Queens[column]==Queens[i] || column-i==abs(Queens[column]-Queens[i]))
		{
			goto NR; //Go to New Row
		}
	}
	goto NC; //Go to New Column

BackTrack:column--;

	if (column==-1)
	{
		return 0;
	}
	goto NR; //Go to new Row

	print: cout <<count <<":" <<endl;
	count++;

	for (int row=0; row<8; row++)
	{

	  for (int column=0; column<8; column++)
	  {
		  if (Queens[column]==row)
		  {
			  cout << 1 << " ";
		  }
		  else
		  {
			  cout << 0 << " ";
		  }
      }
	  cout << endl;
	}
	goto BackTrack;
}
