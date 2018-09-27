package CS146.HW.HW1.HW1_LinkedList;

public class LinkedListApp {
    public static void main(String[] args) {
        linkedList prisoners = new linkedList();
//        assertTrue(prisoners.isEmpty()); //before inserting, list is empty
//
//        assertEquals(0, prisoners.getSize()); // Size is 0
//
//        assertFalse(prisoners.isEmpty()); // after inserting element, list is not empty
//
//        assertEquals(1, prisoners.getSize()); //size is 1

        prisoners.insert(1);
        prisoners.insert(2);
        prisoners.insert(3);
        prisoners.insert(4);
        prisoners.insert(5);
        prisoners.insert(6);

        prisoners.print();

        prisoners.removePerNumber(3);



    }

}
