/*
 * WaxmanHW22.cpp
 */

#include <iostream>
using namespace std;

//RAT CLASS:
class Rat{
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
        t.n = n*r.d - d*r.n;
        t.d = d*r.d;
        return t;
    }
    Rat operator*(Rat r)
    {
        Rat t;
        t.n = n*r.n;
        t.d = d*r.d;
        return t;

    }
    Rat operator/(Rat r)
    {
        Rat t;
        t.n = n*r.d;
        t.d = d*r.n;
        return t;
    }

    friend ostream& operator<<(ostream& os, Rat r);
    friend istream& operator>>(istream& is, Rat& r);
};
int gcd(int a, int b)
{//this function returns the gcd through recursion
    if(b == 0)
    {
        return a;
    }
    else
    {
        return gcd(b, a % b);
    }
}//end of gcd

ostream& operator<<(ostream& os, Rat r)
{//since declared friend of Rat, access to private parts.
    if(r.d==0)
    {
        os<<r.n<<"/"<<r.d<<endl;
    }
    else if(r.n>=r.d)
    {//if it is an improper fraction, it must be expanded
        if(r.n%r.d==0)
        {
            os<<r.n/r.d<<endl;
        }
        else
        {
            int x=gcd(r.n%r.d,r.d);//just in case the reduced fraction needs to be simplified further
            os<<r.n/r.d<<" "<<(r.n%r.d)/x<<" / "<<(r.d)/x<<endl;
        }
    }
    else if(r.n==0)
    {
        os<<r.n<<endl;//if the numerator is 0, then return 0
    }
    else
    {
        int x=gcd(r.n%r.d,r.d);//simplify the proper fraction
        os<<r.n/x<<" / "<<r.d/x<<endl;
    }
    return os;
}
Rat reduce(int &i, int &j)
{//this function reduces r.d and r.n accordingly so it can be used
    for(int a=i;a>0;a--)
    {
        if(i%a==0 && j%a==0)
        {
            i=i/a;
            j=j/a;
            break;
        }
    }
    return Rat(i,j);
}// end of reduce

istream& operator>>(istream& is, Rat& r)
{//since declared friend of Rat, access to private parts.
    is>>r.n>>r.d;
    reduce(r.n,r.d);//reduce the numbers before using them
    return is;
}//end of istream
//END OF RAT CLASS

double continued_frac_1(int a[], int i, int f)
{//uses recursion to get a quick easy decimal
    if (i==f) return (double) a[i];//base case
    return ((double)a[i] + 1/continued_frac_1(a, i+1, f));//recursive call
}//end of frac_1

int* continued_frac_2(int a[], int i)
{//uses recursion to put the fraction into a 2d array.
    static int v[2]={0,0};

    int temp;

    if(i==2)
    {//base case
        v[1]=1;
        v[0]=a[2];
        return v;
    }
    continued_frac_2(a, i+1);//recursive call
    temp=v[0];
    v[0]=a[i]*temp+v[1];
    v[1]=temp;
    return v;

}//end of frac_2

Rat continued_frac_3(int a[], int i)
{//this uses rat class to make fraction from frac_2 into simpler terms
    int *b = continued_frac_2(a,i);//temporary 2d array calls frac_2
    Rat r(b[0],b[1]);//convert to rat, so fraction will be simplified
    return r;
}//end of frac_3
int main()
{
    int array[] = {3, 7, 16, -1};
    cout << "Continued fractions function 1: " <<
    continued_frac_1(array, 0,4) << endl;

    int *a = continued_frac_2(array,0);//

    cout << "Continued fractions function 2: " <<
    a[0] << '/' << a[1] << endl;
    // delete []a;

    cout << "Continued fractions function 3: " <<
    continued_frac_3(array, 0) << endl;

    Rat X(4,6);

    return 0;
}

