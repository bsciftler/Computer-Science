/*
 * WaxmanHW11.cpp
 *
 Run the following program and make sure that you can explain the output,
Note: the sizeof() function returns the number of bytes of the argument passed to it.
 *  Created on: Mar 3, 2016
 *      Author: andrew
 */

#include <iostream>
using namespace std;
int main( )
{
	int b[3][2];
	cout<<sizeof(b)<<endl;
	//An integer is 4 bytes, whole array has 6 integers
	// 6*4= 24
	cout<<sizeof(b+0)<<endl;
//This is the size of a pointer, as I am running 64-bit machine it prints 8.
	cout<<sizeof(*(b+0))<<endl;
//This the size of the element 0 in the a 2D array
//There are 3 1D arrays with 2 integers
//The output is 4(Size of Integer)* 2 (number of elements in the 1D Array)=8


	cout<<"The address of b is: "<<b<<endl;
// The Line above prints 0012FF68
	cout<<"The address of b+1 is: "<<b+1<<endl;
// You add 12 bytes to the original address
	cout<<"The address of &b is: "<<&b<<endl;
// &b is the same as b as it points to the whole 2D array
//Result : "0012FF68"
	cout<<"The address of &b+1 is: "<<&b+1<<endl<<endl;
//
	return 0;
}


