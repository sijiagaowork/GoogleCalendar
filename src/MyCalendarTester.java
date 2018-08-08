/**
 * @author Team 9: Parnika De, Viet Dinh, Sijia Gao, and David Janda
 * CS 151 - Summer 2018
 * Class Project - GUI Calendar
 * Tester class for the calendar application.
 */
public class MyCalendarTester
{
    /**
     * Main function.
     * Creates a DataModel object and CalendarView object.
     * Attachs the view to the model.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args)
    {
        DataModel model = new DataModel();
        CalendarView calendarView = new CalendarView(model);
        model.attach(calendarView);
    }
}
