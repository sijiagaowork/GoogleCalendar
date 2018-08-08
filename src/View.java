/**
 * View.java
 * @author Team 9: Parnika De, Viet Dinh, Sijia Gao, and David Janda
 */
import javax.swing.JTable;

/**
 * This interface is the View for the day, week, and month view.
 * Serves as the Strategy interface.
 */
public interface View
{
    abstract JTable createEventTable();
    abstract JTable timeTable();
    abstract void updateEventTable();
    abstract void updateLabel();
}
