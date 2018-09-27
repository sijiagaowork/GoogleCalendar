//CS 151 section-4 
//Rui Jin 
//Assignment 1 Q2

public class Matrix {
	private int row;
	private int column;
	private int[][] matrix;
	
public Matrix()
{
	row=0;
	column=0;
	
}

public Matrix(int a, int b)
{
	row = a;
	column=b;
	matrix = new int[row][column];	
}


 String getString()
 {    String str="";
       
       for (int i=0; i<row ; i++)
       {     
    	       for (int k=0; k<column; k++)
    	         {  str+=matrix[i][k]+"\t"; } 
    	
    	   str+="\n";    
       }
       
	return str;
 }
 
 
 int getRow()
 {
   return row;
 }
 
 
 int getColumn()
 {
    return column;
 }
 
 
 
 int getValueAt(int row, int column)
 {
	 return matrix[row][column];
 }

 void setValueAt(int row, int column, int value)
 {
	 matrix[row][column]=value;
 }
 
 static Matrix add(Matrix one, Matrix other)
 {
	  Matrix temp = new Matrix(one.getRow(),one.getColumn()); 
	  
	    
	     for (int i=0; i<one.getRow(); i++)
	     {
	    	   for(int k=0; k<one.getColumn(); k++)
	    	   {
	    		   int sum = one.getValueAt(i,k) + other.getValueAt(i, k);
	    		   temp.setValueAt(i,k,sum);
	    	   }
	     }
	 return temp;
 }
 
 Matrix multiply(int x)
 {   
	 Matrix temp = new Matrix(row,column); 
	 for (int i=0; i<row; i++)
     {
    	   for(int k=0; k<column; k++)
    	   {
    		   temp.setValueAt(i,k,matrix[i][k]*x);
    	   }
     }
	 return temp;
 }
 
 Integer getDiagonalSum()
 {       
	     int sum=0;
	    
	     if (row != column)
	     {    	 
	     	 return Integer.MIN_VALUE;	 
	     }
	     
	     for (int i=0; i<row; i++)
	     {
	    	 sum += matrix[i][i];	
	    	 
	     }
	     
	     Integer a = new Integer(sum);
	
	 return a.intValue();   
	 
 }
 
}//end class
