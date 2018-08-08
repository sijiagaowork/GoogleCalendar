import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class describes a calendar event.
 */
public class Event
{
    private String name;
    private int year;
    private int startMonth;
    private int endMonth;
    private int day;
    private double startHour;
    private double endHour;
    private GregorianCalendar cal;

    /**
     * Constructor for single event wihtout endHour.
     * @param name Name of the event.
     * @param year Year of the event.
     * @param startMonth Start month of the event.
     * @param day Day of the event.
     * @param startHour Start hour of the event.
     */
    public Event(String name, int year, int startMonth,
                 int day, double startHour)
    {
        this.name = name;
        this.year = year;
        this.startMonth = startMonth;
        this.endMonth = -1;
        this.day = day;
        this.startHour = startHour;
        this.endHour = 23;
        cal = new GregorianCalendar(year, startMonth-1, day);
    }

    /**
     * Constructor for single event with endHour.
     * @param name Name of the event.
     * @param year Year of the event.
     * @param startMonth Start month of the event.
     * @param day Day of the event.
     * @param startHour Start hour of the event.
     * @param endHour End hour of the event.
     */
    public Event(String name, int year, int startMonth,
                 int day, double startHour, double endHour)
    {
        this.name = name;
        this.year = year;
        this.startMonth = startMonth;
        this.endMonth = -1;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        cal = new GregorianCalendar(year, startMonth-1, day);
    }

    /**
     * Constructor for recurring event.
     * @param name Name of the event.
     * @param year Year of the event.
     * @param startMonth Start month of the event.
     * @param endMonth End month of the event.
     * @param day Day of the event.
     * @param startHour Start hour of the event.
     * @param endHour End hour of the event.
     */
    public Event(String name, int year, int startMonth, int endMonth,
                 int day, double startHour, double endHour)
    {
        this.name = name;
        this.year = year;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        cal = new GregorianCalendar(year, startMonth-1, day);
    }

    /**
     * Gets the name of the event.
     * @return Name of the event.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the event.
     * @param name Name of the event.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the year of the event.
     * @return Year of the event.
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Sets the year of the event.
     * @param year Year of the event.
     */
    public void setYear(int year)
    {
        this.year = year;
    }

    /**
     * Gets the start month of the event.
     * @return Start month of the event.
     */
    public int getStartMonth()
    {
        return startMonth;
    }

    /**
     * Sets the start month of the event.
     * @param startMonth Start month of the event.
     */
    public void setStartMonth(int startMonth)
    {
        this.startMonth = startMonth;
    }

    /**
     * Gets the end month of the event.
     * @return End month of the event.
     */
    public int getEndMonth()
    {
        return endMonth;
    }

    /**
     * Sets the end month of the event.
     * @param endMonth End month of the event.
     */
    public void setEndMonth(int endMonth)
    {
        this.endMonth = endMonth;
    }

    /**
     * Gets the day of the event.
     * @return Day of the event.
     */
    public int getDay()
    {
        return day;
    }

    /**
     * Sets the day of the event.
     * @param day Day of the event.
     */
    public void setDay(int day)
    {
        this.day = day;
    }

    /**
     * Gets the start hour of the event.
     * @return Start hour of the event.
     */
    public double getStartHour()
    {
        return startHour;
    }

    /**
     * Sets the start hour of the event.
     * @param startHour Start hour of the event.
     */
    public void setStartHour(int startHour)
    {
        this.startHour = startHour;
    }

    /**
     * Gets the end hour of the event.
     * @return End hour of the event.
     */
    public double getEndHour()
    {
        return endHour;
    }

    /**
     * Sets the end hour of the event.
     * @param endHour End hour of the event.
     */
    public void setEndHour(int endHour)
    {
        this.endHour = endHour;
    }

    /**
     * Gets the day of the week of the event
     * @return Day of week of the event
     */
    public int getDayOfWeek()
    {
        return this.cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Gets a String of the details of the event.
     * @return Details of the event.
     */
    @Override
    public String toString()
    {
        return name + " " + year + " " + startMonth + " " + endMonth + " " +
                day + " " + startHour + " " + endHour;
    }
}
