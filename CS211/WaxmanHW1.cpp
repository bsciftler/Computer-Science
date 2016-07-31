/*
 * cs211
Find the Value
Write a program to find and print the first perfect square (i*i) whose last two digits
are both odd.
Very important
Make sure to check that the answer you get is indeed a perfect square.
 */

#include <iostream>
#include <cmath>
using namespace std;


int main ()
{
	int test=0;
	int i=0;
	bool found=true; //It is is true i DID NOT find my number
	while (found) //So I will search until I find my number
	{
		test=i*i;
		int isitOdd1=test%10; //The right most value.
		int isitOdd2=(test/10)%10; //Isolate the tenth value
		if ((isitOdd1%2!=0) && (isitOdd2%2!=0) && test > 100)
		{
			found=false; //break the loop.
		}
		else
		{
			i++;
		}
	}
	cout << "The smallest square with the last two digits being odd is: " << test << endl;
	cout << "The Square root of the answer is: " << sqrt(test) << endl;
//NOTE: NO SOLUTION EXISTS!!!!!!!!!!!!!!!!!!!!!!
}



