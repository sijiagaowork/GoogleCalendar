import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
/*
  EmailsTest -- unit tests for the Emails class.
 */
public class MatrixTest {
    
    // Matrix Create
    @Test
    public void testMatrixCreate() {
        Matrix m = new Matrix(10,4);
        assertEquals(10,m.getRow());
    }
    
    @Test
    public void testMatrixString() {
        Matrix m = new Matrix(10,4);
        m.setValueAt(0,0,10);
        m.setValueAt(0,0,-10);
        System.out.println(m);
    }

    @Test
    public void testValueSet() {
        Matrix m = new Matrix(10,4);
        m.setValueAt(0,0,10);
        m.setValueAt(1,1,-10);
        assertEquals(10,m.getValueAt(0,0));
    }

    @Test
    public void testAdd() {
        Matrix m1 = new Matrix(10,4);
        m1.setValueAt(0,0,10);
        Matrix m2 = new Matrix(10,4);
        m2.setValueAt(0,0,10);
        Matrix m = Matrix.add(m1, m2);
        assertEquals(20,m.getValueAt(0,0));
    }

    @Test
    public void testMultiply() {
        Matrix m = new Matrix(10,4);
        m.setValueAt(0,0,10);
        m.multiply(20);
        assertEquals(20,m.getValueAt(0,0));
    }

    @Test
    public void testGetDiagonalSum() {
        Matrix m = new Matrix(10,4);
        m.setValueAt(0,0,10);
        m.setValueAt(1,1,-10);
        assertEquals(0,m.getDiagonalSum());
    }


}
