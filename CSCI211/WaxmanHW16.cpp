/*
 * WaxmanHW16.cpp
 *
 *  Created on: April 11, 2016
 *      Author: andrew
 */

#include<iostream>
using namespace std;

int fib(int n)
{
	static int memo [70]={0};
	if(n==1 || n==2)
	{
		return 1;
	}
	if (memo[n]!=0)
	{
		return memo[n];
	}
	return memo[n] = fib(n - 1) + fib(n - 2);
}

int main()
{
	for(int i=1; i<=70; i++)
	{
		cout << "Fib: " << i << " = " << fib(i)<< endl;
	}
	return 0;
}
/*
Questions:
1. Why does the program get increasingly slower for each successive value of i?
The program is exponential in how quickly it grows
2. What technique can we use to speed it up?
I can save time by making a memoized fib program. This saves time as the computer will not need to compute
some numbers again. I already wrote the quote.
3. After some value if i, the values printed for fib(i) get quite “strange.”
47
a. What is strange about them and why is this happening?
The amount of calculations had literally shot through the roof. 2^47 must be a massive number and it turned the flag bit on, making the number negative.
b. How can we fix this up?
I can use long and and use unsigned C++ to avoid getting negative numbers.
*/
