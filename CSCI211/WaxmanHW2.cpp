/*
 * Consider the two arrays a and b.
a: 1 2 3 4 5
b: 3 4 5 1 2
It is possible to transform array a into array b by right shifting each element of a to the “right”
three places. If an element “falls off” the back of the array have it come around the front and
keep counting positions. That is how 3 in array ended up in the first position of array b. One
way to look at this is to imagine that we are moving the element around in a circular manner.
In the example above, we have right shifted the array 3 positions to the right.
Definition: Let a and b be two integer arrays of the same length. We say that they are “shift
equivalent” if array a can be right shifted to create array b.
Problem
Write a function
bool equivalent(int a[], int b[], int n)
which takes two arrays a and b of length n and returns true is they are shift equivalent and false
otherwise.
 */

#include <iostream>
using namespace std;

bool CheckShift(int a[], int b[], int n, int shift) {
    for (int j=0;j<n;j++)
    {
        if (a[j] != b[(j+shift)%n])
        {
            return false;
        }
    }
    return true;
}

bool equivalent(int a[], int b[], int n)
{
    for (int i=0;i<n;i++)
    {
        if (CheckShift(a, b, n, i))
        {
            return true;
        }
    }
    return false;
}

int main ()
{
	int a []= {1,2,3,4,5};
	int b []= {3,4,5,1,2};
	//3,4,5,1,2 is the original set.
	if (equivalent(a,b,5))
	{
		cout << "The two Arrays are shift equivalent!";
	}
	else
	{
		cout << "The two arrays are NOT shift equivalent!";
	}
}

