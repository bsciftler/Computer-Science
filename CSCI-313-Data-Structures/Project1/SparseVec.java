public interface SparseVec 
{	
	// problem 1
	int getLength();				//return the length of the vector
	int numElements();				//return total number of nonzero elements in the vector
	int getElement(int idx);	   	//return the element at a given idx 
	void clearElement(int idx); 	//set the element at idx to zero
	void setElement(int idx, int val) throws VectorException; 	//set the element at idx to val
	// get all nonzero indices
	int[] getAllIndices();
	// get all nonzero values
	int[] getAllValues();
	
	// methods for problem 2
	SparseVec addition(SparseVec otherV) throws VectorException;// this vector + otherV
												// return a new vector storing the result
	SparseVec substraction(SparseVec otherV) throws VectorException;	// this vector - otherV
												// return a new vector storing the result
	SparseVec multiplication(SparseVec otherV) throws VectorException;	// this matrix .* otherV
												// return a new vector storing the result
	void append(LLSparseVecNode currentA);
	LLSparseVecNode getHead();
}