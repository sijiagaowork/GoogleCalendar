/**
 * DataModel.java
 * @author Team 9: Parnika De, Viet Dinh, Sijia Gao, and David Janda
 */
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*;
import java.util.*;

/**
 * This class functions as the model of the calendar application.
 * Holds a GregorianCalendar object, a list of events, and a list of views.
 */
public class DataModel
{
    private GregorianCalendar cal;
    private ArrayList<ChangeListener> listeners;
    private List<Event> eventList; // To store events
    private Map<Integer, String> dayMap; // To map recurring events
    private BufferedReader br;
    private int eventDay;
    private int eventMonth;
    private int eventYear;

    /**
     * Constructor that initializes the GregorianCalendar object, list and map of events, and list of views.
     */
    public DataModel()
    {
        cal = new GregorianCalendar();
        listeners = new ArrayList<>();
        eventList = new ArrayList<>();
        /**
         * Creating Map to act as a dictionary so that when we read the
         * day from the file, we can map it back to cal.get(Calendar.DAY_OF_WEEK)
         * this is only for recurring events because we are reading from file for recurring events only.
         * In recurring events each symbol is used to map the day to a event.
         */
        dayMap = new HashMap<>();
        dayMap.put(1, "S");
        dayMap.put(2, "M");
        dayMap.put(3, "T");
        dayMap.put(4, "W");
        dayMap.put(5, "H");
        dayMap.put(6, "F");
        dayMap.put(7, "A");
    }

    /**
     * Sets the day of the event.
     * @param eventDay Day of the event.
     */
    public void setEventDay(int eventDay)
    {
        this.eventDay = eventDay;
    }

    /**
     * Sets the month of the event.
     * @param eventMonth Month of the event.
     */
    public void setEventMonth(int eventMonth)
    {
        this.eventMonth = eventMonth;
    }

    /**
     * Sets the year of the event.
     * @param eventYear Year of the event.
     */
    public void setEventYear(int eventYear)
    {
        this.eventYear = eventYear;
    }

    /**
     * Sets the GregorianCalendar object.
     * @param cal New GregorianCalendar to reference.
     */
    public void setCal(GregorianCalendar cal)
    {
        this.cal = cal;
        update();
    }

    /**
     * Gets the current day.
     * @return The current day.
     */
    public int getToday()
    {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * Gets the current month.
     * @return The current month.
     */
    public int getMonth()
    {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * Sets the current day.
     * @param day Current day.
     */
    public void setDay(int day)
    {
        cal.set(Calendar.DATE, day);
        update();
    }

    /**
     * Sets the current month.
     * @param month Current month.
     */
    public void setMonth(int month)
    {
        cal.set(Calendar.MONTH, month);
        update();
    }

    /**
     * Gets the day of the event.
     * @return Day of the event.
     */
    public int getEventDay()
    {
        return eventDay;
    }

    /**
     * Gets the month of the event.
     * @return Month of the event.
     */
    public int getEventMonth()
    {
        return eventMonth;
    }

    /**
     * Gets the year of the event.
     * @return Year of the event.
     */
    public int getEventYear()
    {
        return eventYear;
    }

    /**
     * Gets the list of events.
     * @return List of events.
     */
    public List<Event> getEventList()
    {
        return eventList;
    }

    /**
     * Sets the list of events.
     * @param eventList List of events.
     */
    public void setEventList(List<Event> eventList)
    {
        this.eventList = eventList;
    }

    /**
     * Overloaded function to create single event without endHour.
     * @param name Name of the event.
     * @param year Year of the event.
     * @param startMonth Start month of the event.
     * @param day Day of the event.
     * @param startHour Start hour of the event.
     */
    public boolean createEvent(String name, int year, int startMonth,
                               int day, int startHour)
    {
        Event e = new Event(name, year, startMonth, day, startHour);
        if(checkConflict(e))
        {
            eventList.add(e);
            update();
            return true;
        }
        update();
        return false;
    }

    /**
     * Overloaded function to create single event with endHour.
     * @param name Name of the event.
     * @param year Year of the event.
     * @param startMonth Start month of the event.
     * @param day Day of the event.
     * @param startHour Start hour of the event.
     * @param endHour End hour of the event.
     */
    public boolean createEvent(String name, int year, int startMonth,
                               int day, int startHour, int endHour)
    {
        Event e = new Event(name, year, startMonth, day, startHour, endHour);
        if (checkConflict(e))
        {
            eventList.add(e);
            update();
            return true;
        }
        update();
        return false;
    }

    /**
     * Overloaded function to create recurring event.
     * @param name Name of the event.
     * @param year Year of the event.
     * @param startMonth Start month of the event.
     * @param endMonth End month of the event.
     * @param day Day of the event.
     * @param startHour Start hour of the event.
     * @param endHour End hour of the event.
     */
    public boolean createEvent(String name, int year, int startMonth, int endMonth,
                               int day, int startHour, int endHour)
    {
        Event e = new Event(name, year, startMonth, endMonth, day, startHour, endHour);
        if (checkConflict(e))
        {
            eventList.add(e);
            update();
            return true;
        }
        update();
        return false;
    }

    /**
     * Checks for a time conflict between events.
     * @return True if there is no conflict. False otherwise.
     */
    private boolean checkConflict(Event e)
    {
        for (Event event: eventList)
        {
            if (event.getYear() == e.getYear()
                    && event.getStartMonth() == e.getStartMonth()
                    && event.getDay() == e.getDay()
                    // scenario 1 - when the new events startHour is conflicting
                    && ((event.getStartHour() <= e.getStartHour()
                    && event.getEndHour() > e.getStartHour())
                    // scenario 2 - when the new events endHour is conflicting
                    || (event.getStartHour() > e.getStartHour()
                    && event.getStartHour() < e.getEndHour())
                    // scenario 3 - when the new event completely over the existing events time
                    || (event.getStartHour() >= e.getStartHour()
                    && event.getStartHour() < e.getEndHour())
            )
                    )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Sorts the list of events using Comparator.
     */
    public void sortEvent()
    {
        Comparator<Event> eventComparator = (e1, e2) ->
        {
            if (e1.getYear() > e2.getYear())
            {
                return 1;
            }
            if (e1.getYear() < e2.getYear())
            {
                return -1;
            }
            if (e1.getStartMonth() > e2.getStartMonth())
            {
                return 1;
            }
            if (e1.getStartMonth() < e2.getStartMonth())
            {
                return -1;
            }
            if (e1.getDay() > e2.getDay())
            {
                return 1;
            }
            if (e1.getDay() < e2.getDay())
            {
                return -1;
            }
            if (e1.getStartHour() > e2.getStartHour())
            {
                return 1;
            }
            if (e1.getStartHour() < e2.getStartHour())
            {
                return -1;
            }
            return 0;
        };
        eventList.sort(eventComparator);
    }

    /**
     * Reads a file to retrieve information about recurring events.
     * @param filePath Path to the file that is going to be read.
     * @return True if the file was successfully read. False otherwise.
     */
    public boolean readFromFile(String filePath)
    {
        File file = new File(filePath);
        try
        {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null)
            {
                String[] stSplit = st.split(";");
                if (stSplit.length != 7)
                {
                    return false;
                }
                String name = stSplit[0];
                int year = Integer.parseInt(stSplit[1]);
                int startMonth = Integer.parseInt(stSplit[2]);
                int endMonth = Integer.parseInt(stSplit[3]);
                String days = stSplit[4];
                int startHour = Integer.parseInt(stSplit[5]);
                int endHour = Integer.parseInt(stSplit[6]);
                GregorianCalendar g = new GregorianCalendar(year, (startMonth - 1), 1);
                while ((g.get(Calendar.MONTH) + 1) <= endMonth && g.get(Calendar.YEAR) == year)
                {
                    if (days.contains(dayMap.get(g.get(Calendar.DAY_OF_WEEK))))
                    {
                        int day = g.get(Calendar.DAY_OF_MONTH);
                        createEvent(name, year, (g.get(Calendar.MONTH) + 1), endMonth, day, startHour, endHour);
                    }
                    g.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        } catch (FileNotFoundException e)
        {
            return false;
        } catch (IOException e)
        {
            return false;
        }
        update();
        return true;
    }

    /**
     * Prints all the events in the list.
     */
    public void printEventList()
    {
        for (Event e : eventList)
        {
            System.out.println(e.toString());
        }
    }

    /**
     * Prints the size of the list of events.
     */
    public void printEventListSize()
    {
        System.out.println(eventList.size());
    }

    /**
     * Gets the GregorianCalendar object used in this model.
     * @return GregorianCalendar object.
     */
    public GregorianCalendar getCal()
    {
        return cal;
    }

    /**
     * Gets the number of days in the current month.
     * @return Number of days in the current month.
     */
    public int getMonthDays()
    {
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Gets the current month (zero-indexed).
     * @return Current month.
     */
    public int getCurrentMonth()
    {
        return cal.get(Calendar.MONTH);
    }

    /**
     * Gets the current year.
     * @return Current year.
     */
    public int getCurrentYear()
    {
        return cal.get(Calendar.YEAR);
    }

    /**
     * Gets the current day.
     * @return Current day.
     */
    public int getCurrentDay()
    {
        return cal.get(Calendar.DATE);
    }

    /**
     * Increases the current day by one.
     */
    public void nextDay()
    {
        cal.add(Calendar.DATE, 1);
        update();
    }

    /**
     * Increases the current month by one.
     */
    public void nextMonth()
    {
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 1);
        update();
    }

    /**
     * Decreases the current day by one.
     */
    public void prevDay()
    {
        cal.add(Calendar.DATE, -1);
        update();
    }

    /**
     * Decreases the current month by one.
     */
    public void prevMonth()
    {
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, 1);
        update();
    }

    /**
     * Nofities the views of any changes in the model.
     */
    public void update()
    {
        for (ChangeListener l: listeners)
        {
            l.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Attachs a view to the model.
     * @param listener View.
     */
    public void attach(ChangeListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Overridden function to get all the events in the given view type.
     * @param viewType Day, week, or month.
     * @return List of events for the selected view type.
     */
    public List<Event> getEventInSelectedView(String viewType)
    {
        sortEvent();
        List<Event> eventListInSelectedView = new ArrayList<>();

        // Checks if view type is day
        if(viewType.equalsIgnoreCase("day"))
        {
            for(Event e: eventList)
            {
                if(e.getYear() == getCurrentYear()
                        && e.getStartMonth() == (getCurrentMonth() + 1)
                        && e.getDay() == getCurrentDay())
                {
                    eventListInSelectedView.add(e); // Adds event in the view type
                }
            }
            // Checks if view type is month
        }
        else if(viewType.equalsIgnoreCase("month"))
        {
            for(Event e: eventList)
            {
                if(e.getYear() == getCurrentYear()
                        && e.getStartMonth() == (getCurrentMonth() + 1))
                {
                    eventListInSelectedView.add(e);
                }
            }
            // Checks if the view type is week view
        }
        else if(viewType.equalsIgnoreCase("week"))
        {
            int weekLowerThreshold = getCal().get(Calendar.DAY_OF_WEEK) - 1; // The first day of the week
            int weekUpperThreshold = 7 - getCal().get(Calendar.DAY_OF_WEEK); // The last day of the week

            for(Event e: eventList)
            {
                GregorianCalendar eventCal = new GregorianCalendar(e.getYear(), (e.getStartMonth() - 1), e.getDay());

                // The time difference
                double diff = (1.0 * getCal().getTimeInMillis() - eventCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);

                if(diff < 0 && Math.abs(diff) < weekUpperThreshold)
                {
                    eventListInSelectedView.add(e);
                }
                else if(diff > 0 && diff <= weekLowerThreshold)
                {
                    eventListInSelectedView.add(e);
                }
                else if (diff == 0)
                {
                    eventListInSelectedView.add(e);
                }
            }
        }
        return eventListInSelectedView;
    }

    /**
     * Overridden function to get all the events in the given date range.
     * For Agenda view type
     * @param startYear Start year of the events.
     * @param startMonth Start month of the events.
     * @param startDay Start day of the events.
     * @param endYear End year of the events.
     * @param endMonth End month of the events.
     * @param endDay End day of the events.
     * @return List of events which fall under the Agenda start and end date.
     */
    public List<Event> getEventInSelectedView(int startYear, int startMonth, int startDay,
                                              int endYear, int endMonth, int endDay)
    {
        sortEvent();
        List<Event> eventListInSelectedView = new ArrayList<Event>();

        for(Event e: eventList)
        {
            if(e.getYear() >= startYear && e.getYear() <= endYear
                    && e.getStartMonth() >= startMonth && e.getStartMonth() <= endMonth
                    && e.getDay() >= startDay && e.getDay() <= endDay)
            {
                eventListInSelectedView.add(e);
            }
        }
        return eventListInSelectedView;
    }

    /**
     * Checks to see if the date in the GregorianCalendar is the same as the current date.
     * @return True if the dates are the same. False otherwise.
     */
    public boolean isToday()
    {
        GregorianCalendar today = new GregorianCalendar();
        return getCurrentYear() == today.get(Calendar.YEAR)
                && getCurrentMonth() == today.get(Calendar.MONTH)
                && getCurrentDay() == today.get(Calendar.DAY_OF_MONTH);
    }
}
