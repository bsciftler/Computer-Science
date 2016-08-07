/*  WaxmanHW21.cpp
 *Starting with the Rat class (see Handouts) do the following:
1. Add the following operators to the class:
operator-()
operator*()
operator/()
2. Make sure Rats are reduced to lowest terms. So if a Rat is 2/4 it should be
reduced to 1â„2.
3. If a Rat represents an â€œimproper fractionâ€ (i.e. numerator >denominator) print
the Rat as a â€œmixed number.â€ So 6/4 will be printed as 1 1â„2

Created on: May 7, 2016
      Author: andrew
 */
#include <iostream>
using namespace std;

int GCD (int a, int b)
{
int remainder;
while (true)
{
	remainder=a%b;
	if (remainder == 0)
	{
		break;
	}
	a=b;
	b=remainder;
}
return b;
}

class Rat
{
private:
	int n;
	int d;
public:
	// constructors
	// default constructor
	Rat()
	{
		n=0;
		d=1;
	}
	// 2 parameter constructor
	Rat(int i, int j)
	{
		n=i;
		d=j;
	}
	// conversion constructor
	Rat(int i)
	{
		n=i;
		d=1;
	}
	//accessor functions (usually called get() and set(...) )
	int getN(){ return n;}
	int getD(){ return d;}
	void setN(int i){ n=i;}
	void setD(int i){ d=i;}

	//arithmetic operators
	Rat operator+(Rat r)
	{
		Rat t;
		t.n=n*r.d+d*r.n;
		t.d=d*r.d;
		return t;
	}
	Rat operator-(Rat r)
	{
		Rat t;
		t.n=n*r.d-d*r.n;
		t.d=d*r.d;
		return t;
	}
	Rat operator*(Rat A)
	{
		Rat t;
		t.n=n*A.n;
		t.d=d*A.d;
		return t;
	}
	Rat operator/(Rat A)
	{
		Rat t;
		t.n=n*A.d;
		t.d=d*A.n;
		return t;
	}
	void reduce()
	{
		int gcd=GCD(n,d);
		cout << "THE GCD IS: " << gcd << endl;
		n=(n/gcd);
		d=(d/gcd);
	}

	void mixed()
	{
		int mixed=(n/d);
		n=n-(mixed*d);
		cout << mixed <<" + " <<n<<" / "<<d<<endl;;
	}
	// 2 overloaded i/o operators
	friend ostream& operator<<(ostream& os, Rat r);
	friend istream& operator>>(istream& is, Rat& r);
}; //end Rat


// operator<<() is NOT a member function but since it was declared a friend of Rat
// it has access to its private parts.
ostream& operator<<(ostream& os, Rat r)
{
	os<<r.n<<" / "<<r.d<<endl;
	return os;
}
// operator>>() is NOT a member function but since it was declared a friend of Rat
// it has access to its provate parts.
istream& operator>>(istream& is, Rat& r)
{
	is>>r.n>>r.d;
	return is;
}

int main()
{
	Rat x(1,2), y(2,3), z, A(4,6), B(5,2);

	z=x+y;
	cout<<z << endl;

	cout << "BEFORE REDUCTION of A: " << A << endl;
	A.reduce();
	cout<< "A after reduction: " << A;

	x.setN(3);
	y.setD(2);
	z=x+y;
	cout<<z << endl;
	//cin>>x;
	//cout<<x << endl;
	z= x+5;
	cout << z;
	cout << "ORIGINAL B: " << B;
	cout << "Mixed B: ";
	B.mixed();
	cout << endl;
	return 0;
}
