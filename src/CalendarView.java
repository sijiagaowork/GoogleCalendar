/**
 * CalendarView.java
 * @author Team 9: Parnika De, Viet Dinh, Sijia Gao, and David Janda
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class functions as the main view for the calendar application.
 */
public class CalendarView extends JFrame implements ChangeListener
{
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 400;
    private final static int DAYS_IN_WEEK = 7;
    private final static int TEXT_COLUMN = 55;
    private final static int MAX_DAY_BUTTONS = 42;
    private DataModel model;
    private DayView dayView;
    private WeekView weekView;
    private MonthView monthView;
    private ViewContext viewContext;
    private List<Event> eventList;
    private JTextArea textArea;
    private JButton[] dayButton;
    private String[] months = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
    private String[] days = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };

    /**
     * Constructor that sets up the view.
     * @param m DataModel object used by the view.
     */
    public CalendarView(DataModel m)
    {
        model = m;
        // Get the different View Strategies
        viewContext = new ViewContext(new DayView(model));
        dayView = (DayView) viewContext.getView();
        viewContext = new ViewContext(new WeekView(model));
        weekView = (WeekView) viewContext.getView();
        viewContext = new ViewContext(new MonthView(model));
        monthView = (MonthView) viewContext.getView();
        model.attach(dayView);
        model.attach(weekView);
        model.attach(monthView);

        this.setTitle("Calendar");
        this.setLayout(new BorderLayout());
        this.createLeftPanel();
        this.createRightPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    /**
     * Calls methods from the model to change the current day depending on input.
     * @param option Prev or next indicating previous or next day.
     */
    private void changeDay(String option)
    {
        if (option.equalsIgnoreCase("prev"))
        {
            model.prevDay();
        }
        else if (option.equalsIgnoreCase("next"))
        {
            model.nextDay();
        }
    }

    /**
     * Calls methods from model to change the current month depending on input.
     * @param option Prev or next indicating previous or next month.
     */
    private void changeMonth(String option)
    {
        if (option.equalsIgnoreCase("prev"))
        {
            model.prevMonth();
        }
        else if (option.equalsIgnoreCase("next"))
        {
            model.nextMonth();
        }
    }

    /**
     * Creates the left panel which includes:
     *      Today button,
     *      Previous and next day buttons,
     *      Create button,
     *      Text area that displays the current month and year,
     *      Labels that indicate the days of the week,
     *      Buttons for each day of the month.
     */
    private void createLeftPanel()
    {
        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new FlowLayout());

        JButton buttonToday = new JButton("Today");
        buttonToday.addActionListener(event ->
        {
            model.setDay(model.getToday());
            model.setMonth(model.getMonth());
        });

        JButton buttonPrevDay = new JButton("<");
        buttonPrevDay.addActionListener(event -> changeDay("prev"));

        JButton buttonNextDay = new JButton(">");
        buttonNextDay.addActionListener(event -> changeDay("next"));

        JButton buttonCreate = new JButton("CREATE");
        buttonCreate.setBackground(Color.ORANGE);
        buttonCreate.setOpaque(true);
        buttonCreate.setBorderPainted(false);
        buttonCreate.addActionListener(event ->
        {
            JTextField textFieldEventTitle = new JTextField("Untitled Event", TEXT_COLUMN);
            JTextField textFieldDate = new JTextField(7);
            String currentMonth;
            String currentDay;

            if ((model.getCurrentMonth() + 1) < 10)
            {
                currentMonth = "0" + String.valueOf(model.getCurrentMonth() + 1);
            }
            else
            {
                currentMonth = String.valueOf(model.getCurrentMonth() + 1);
            }

            if (model.getCurrentDay() < 10)
            {
                currentDay = "0" + String.valueOf(model.getCurrentDay());
            }
            else
            {
                currentDay = String.valueOf(model.getCurrentDay());
            }

            String currentDate = currentMonth + "/" + currentDay + "/" + model.getCurrentYear();
            textFieldDate.setText(currentDate);
            JTextField textFieldStartingTime = new JTextField(5);
            JTextField textFieldEndingTime = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new BorderLayout());

            JPanel myPanel1 = new JPanel();
            myPanel1.add(textFieldEventTitle, BorderLayout.NORTH);
            myPanel1.add(Box.createVerticalStrut(15));

            JPanel myPanel2 = new JPanel();
            myPanel2.add(new JLabel("Date: (MM/DD/YYYY)"));
            myPanel2.add(textFieldDate);
            myPanel2.add(new JLabel("Starting time (HH:00)"));
            myPanel2.add(textFieldStartingTime);
            myPanel2.add(new JLabel("to"));
            myPanel2.add(new JLabel("Ending time (HH:00)"));
            myPanel2.add(textFieldEndingTime);

            myPanel.setLayout(new BorderLayout());
            myPanel.add(myPanel1, BorderLayout.NORTH);
            myPanel.add(myPanel2, BorderLayout.SOUTH);

            int input = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please enter event title, date, starting time and ending time \n", JOptionPane.OK_CANCEL_OPTION);

            if (input != JOptionPane.CLOSED_OPTION)
            {
                String eventTitle = textFieldEventTitle.getText();
                String date = textFieldDate.getText(); // Specially, if the user did not enter anything it means current button's date.
                String startingTime = textFieldStartingTime.getText();
                String endingTime = textFieldEndingTime.getText();

                if (date.equals(""))
                {
                    System.out.println("no date");
                }

                int theDay = 0;
                int theMonth = 0;
                int theYear = 0;

                if (!date.equals(""))
                {
                    theMonth = Integer.parseInt(date.substring(0, 2));
                    theDay = Integer.parseInt(date.substring(3, 5));
                    theYear = Integer.parseInt(date.substring(6));

                    System.out.println(theDay);
                    System.out.println(theMonth);
                    System.out.println(theYear);
                }

                if (startingTime.equals(""))
                {
                    System.out.println("no events created");
                }
                else
                {
                    int startingHour = Integer.parseInt(startingTime);
                    System.out.println(startingHour);

                    boolean isEventCreated = false;
                    if("".equals(endingTime)) {
                        isEventCreated = model.createEvent(eventTitle, theYear, theMonth, theDay, startingHour);
                    } else {
                        int endingHour = Integer.parseInt(endingTime);
                        System.out.println(endingHour);
                        isEventCreated = model.createEvent(eventTitle, theYear, theMonth, theDay, startingHour, endingHour);
                    }

                    // Controller
                    if(!isEventCreated) {
                        JOptionPane.showMessageDialog(null, "Conflicting event found! Event not created. Please try again with a different time.",
                                "Event Info", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Event created.",
                                "Event Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        leftButtonPanel.add(buttonToday);
        leftButtonPanel.add(buttonPrevDay);
        leftButtonPanel.add(buttonNextDay);
        leftButtonPanel.add(buttonCreate);


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        String month = months[model.getCurrentMonth()] + " ";
        textArea.setText(month);
        textArea.append(String.valueOf(model.getCurrentYear()) + "\n\n");
        textArea.setOpaque(false);
        textPanel.add(textArea);

        JPanel monthButtonPanel = new JPanel();
        JButton buttonPrevMonth = new JButton("<");
        buttonPrevMonth.addActionListener(event -> changeMonth("prev"));

        JButton buttonNextMonth = new JButton(">");
        buttonNextMonth.addActionListener(event -> changeMonth("next"));

        monthButtonPanel.add(buttonPrevMonth);
        monthButtonPanel.add(buttonNextMonth);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));

        JPanel daysOfWeekPanel = new JPanel();
        daysOfWeekPanel.setLayout(new GridLayout(1, DAYS_IN_WEEK));
        JLabel[] dayOfWeekLabel = new JLabel[DAYS_IN_WEEK];

        for (int j = 0; j < days.length; j++)
        {
            dayOfWeekLabel[j] = new JLabel(days[j], JLabel.CENTER);
            daysOfWeekPanel.add(dayOfWeekLabel[j]);
        }

        JPanel daysPanel = new JPanel();
        daysPanel.setLayout(new GridLayout(0, DAYS_IN_WEEK));

        // Initialize dayButton
        dayButton = new JButton[MAX_DAY_BUTTONS];
        for (int i = 0; i < MAX_DAY_BUTTONS; i++)
        {
            dayButton[i] = new JButton();
            dayButton[i].setPreferredSize(new Dimension(50, 50));
        }
        updateDayButtons();


        for (int i = 0; i < MAX_DAY_BUTTONS; i++)
        {
            // Add action listener to the buttons
            final int index = i;
            dayButton[i].addActionListener(event ->
            {
                int day = Integer.parseInt(dayButton[index].getText());
                model.setDay(day);
                eventList = model.getEventInSelectedView("day");
                updateDayButtons();
            });

            // Add button to daysPanel
            if(dayButton[i] != null)
            {
                daysPanel.add(dayButton[i]);
            }
        }

        leftPanel.add(leftButtonPanel);
        leftPanel.add(textPanel);
        leftPanel.add(monthButtonPanel);
        leftPanel.add(daysOfWeekPanel);
        leftPanel.add(daysPanel);
        this.add(leftPanel, BorderLayout.WEST);
    }

    /**
     * Updates the orientation of the day buttons to reflect the current month and year.
     */
    private void updateDayButtons()
    {
        GregorianCalendar temp = new GregorianCalendar(model.getCurrentYear(), model.getCurrentMonth(), 1);
        int k = temp.get(Calendar.DAY_OF_WEEK);
        int dayButtonIndex = 0;
        temp.add(Calendar.DATE, -1);
        int prevDay = temp.get(Calendar.DAY_OF_MONTH) - k + 1;

        for (; dayButtonIndex < k - 1 ; dayButtonIndex++)
        {
            dayButton[dayButtonIndex].setText(String.valueOf(prevDay++));
            dayButton[dayButtonIndex].setBackground(null);
            dayButton[dayButtonIndex].setOpaque(false);
            dayButton[dayButtonIndex].setBorderPainted(true);
            dayButton[dayButtonIndex].setEnabled(false);
        }

        for (int i = 0; i < model.getMonthDays(); i++, dayButtonIndex++)
        {
            dayButton[dayButtonIndex].setText(String.valueOf(i+1));
            dayButton[dayButtonIndex].setEnabled(true);

            dayButton[dayButtonIndex].setBackground(null);
            dayButton[dayButtonIndex].setOpaque(false);
            dayButton[dayButtonIndex].setBorderPainted(true);

            if (i + 1 == model.getCurrentDay())
            {
                dayButton[dayButtonIndex].setBackground(Color.GRAY);
                dayButton[dayButtonIndex].setOpaque(true);
                dayButton[dayButtonIndex].setBorderPainted(true);
            }
            if (i + 1 == model.getCurrentDay() && model.isToday())
            {
                dayButton[dayButtonIndex].setBackground(Color.PINK);
                dayButton[dayButtonIndex].setOpaque(true);
                dayButton[dayButtonIndex].setBorderPainted(true);
            }
        }

        for (int d = 0; dayButtonIndex < MAX_DAY_BUTTONS; d++, dayButtonIndex++)
        {
            dayButton[dayButtonIndex].setText(String.valueOf(d+1));
            dayButton[dayButtonIndex].setEnabled(false);
        }

        int month = model.getCurrentMonth();
        textArea.setText(String.valueOf(months[month]) + " ");
        textArea.append(String.valueOf(model.getCurrentYear()) + "\n\n");
    }

    /**
     * Creates the right panels which includes:
     *      Day button,
     *      Week button,
     *      Month button,
     *      Agenda button,
     *      From File button,
     *      DayView.
     */
    private void createRightPanel()
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new FlowLayout());

        JButton buttonDay = new JButton("Day");
        JButton buttonWeek = new JButton("Week");
        JButton buttonMonth = new JButton("Month");
        JButton buttonAgenda = new JButton("Agenda");
        JButton buttonFromFile = new JButton("From File");

        rightButtonPanel.add(buttonDay);
        rightButtonPanel.add(buttonWeek);
        rightButtonPanel.add(buttonMonth);
        rightButtonPanel.add(buttonAgenda);
        rightButtonPanel.add(buttonFromFile);

        rightPanel.add(rightButtonPanel, BorderLayout.NORTH);

        // Default highlight
        buttonDay.setBackground(Color.GREEN);
        buttonDay.setOpaque(true);
        buttonDay.setBorderPainted(true);

        buttonDay.addActionListener(event ->
        {
            weekView.setVisible(false);
            monthView.setVisible(false);
            rightPanel.remove(1);
            rightPanel.add(dayView,BorderLayout.CENTER);
            dayView.setVisible(true);

            // Change highlights
            buttonDay.setBackground(Color.GREEN);
            buttonDay.setOpaque(true);
            buttonDay.setBorderPainted(true);
            buttonWeek.setBackground(null);
            buttonWeek.setOpaque(false);
            buttonWeek.setBorderPainted(true);
            buttonMonth.setBackground(null);
            buttonMonth.setOpaque(false);
            buttonMonth.setBorderPainted(true);
            buttonAgenda.setBackground(null);
            buttonAgenda.setOpaque(false);
            buttonAgenda.setBorderPainted(true);
        });

        buttonWeek.addActionListener(event ->
        {
            dayView.setVisible(false);
            monthView.setVisible(false);
            rightPanel.remove(1);
            rightPanel.add(weekView, BorderLayout.CENTER);
            weekView.setVisible(true);

            // Change highlights
            buttonWeek.setBackground(Color.GREEN);
            buttonWeek.setOpaque(true);
            buttonWeek.setBorderPainted(true);
            buttonDay.setBackground(null);
            buttonDay.setOpaque(false);
            buttonDay.setBorderPainted(true);
            buttonMonth.setBackground(null);
            buttonMonth.setOpaque(false);
            buttonMonth.setBorderPainted(true);
            buttonAgenda.setBackground(null);
            buttonAgenda.setOpaque(false);
            buttonAgenda.setBorderPainted(true);
        });

        buttonMonth.addActionListener(event ->
        {
            dayView.setVisible(false);
            weekView.setVisible(false);
            rightPanel.remove(1);
            rightPanel.add(monthView, BorderLayout.CENTER);
            monthView.setVisible(true);

            // Change highlights
            buttonMonth.setBackground(Color.GREEN);
            buttonMonth.setOpaque(true);
            buttonMonth.setBorderPainted(true);
            buttonWeek.setBackground(null);
            buttonWeek.setOpaque(false);
            buttonWeek.setBorderPainted(true);
            buttonDay.setBackground(null);
            buttonDay.setOpaque(false);
            buttonDay.setBorderPainted(true);
            buttonAgenda.setBackground(null);
            buttonAgenda.setOpaque(false);
            buttonAgenda.setBorderPainted(true);
        });

        buttonAgenda.addActionListener(event ->
        {
            // Change highlights
            buttonAgenda.setBackground(Color.GREEN);
            buttonAgenda.setOpaque(true);
            buttonAgenda.setBorderPainted(true);
            buttonDay.setBackground(null);
            buttonDay.setOpaque(false);
            buttonDay.setBorderPainted(false);
            buttonWeek.setBackground(null);
            buttonWeek.setOpaque(false);
            buttonWeek.setBorderPainted(true);
            buttonMonth.setBackground(null);
            buttonMonth.setOpaque(false);
            buttonMonth.setBorderPainted(true);

            JTextField textFieldStartingDate = new JTextField( 10);
            JTextField textFieldEndingDate = new JTextField( 10);

            JPanel myPanel = new JPanel();

            // Ask user input
            myPanel.add(new JLabel("Starting date: (MM/DD/YYYY)"));
            myPanel.add(textFieldStartingDate);
            myPanel.add(Box.createVerticalStrut(15));
            myPanel.add(new JLabel("Ending date: (MM/DD/YYYY)"));
            myPanel.add(textFieldEndingDate);

            JOptionPane.showConfirmDialog(null, myPanel,
                    "Please enter event title, date, starting time and ending time \n", JOptionPane.OK_CANCEL_OPTION);

            String startingDate = textFieldStartingDate.getText();
            String endingDate = textFieldEndingDate.getText();

            if (startingDate.equals("") || endingDate.equals(""))
            {
                System.out.println("no time period entered");
            }

            int startingDay = 0;
            int startingMonth = 0;
            int startingYear = 0;

            int endingDay = 0;
            int endingMonth = 0;
            int endingYear = 0;

            if (!((startingDate.equals("") || endingDate.equals(""))))
            {
                startingMonth = Integer.parseInt(startingDate.substring(0, 2));
                startingDay = Integer.parseInt(startingDate.substring(3, 5));
                startingYear = Integer.parseInt(startingDate.substring(6));

                endingMonth = Integer.parseInt(endingDate.substring(0, 2));
                endingDay = Integer.parseInt(endingDate.substring(3, 5));
                endingYear = Integer.parseInt(endingDate.substring(6));

                System.out.println(startingDay);
                System.out.println(startingMonth);
                System.out.println(startingYear);

                System.out.println(endingDay);
                System.out.println(endingMonth);
                System.out.println(endingYear);
            }

            eventList = model.getEventInSelectedView(startingYear, startingMonth, startingDay,
                    endingYear, endingMonth, endingDay);

            JPanel agendaPanel = new JPanel();
            JTextArea agendaArea = new JTextArea();

            for (Event e: eventList)
            {
                int eventStartHour = (int) e.getStartHour();
                int eventEndHour = (int) e.getEndHour();
                String eventName = e.getName();
                agendaArea.append(eventName + " " + eventStartHour + " - " + eventEndHour + "\n");
            }

            dayView.setVisible(false);
            weekView.setVisible(false);
            monthView.setVisible(false);
            rightPanel.remove(1);
            agendaPanel.add(agendaArea);
            rightPanel.add(agendaPanel, BorderLayout.CENTER);
            agendaPanel.setVisible(true);
        });

        buttonFromFile.addActionListener(event ->
        {
            JTextField textFieldFilePath = new JTextField( 25);
            JPanel myPanel = new JPanel();

            // Ask user input
            myPanel.add(new JLabel("File path:"));
            myPanel.add(textFieldFilePath);
            myPanel.add(Box.createHorizontalStrut(50));

            JOptionPane.showConfirmDialog(null, myPanel,
                    "Please enter file path to read from \n", JOptionPane.OK_CANCEL_OPTION);

            String filePath = textFieldFilePath.getText();

            if (!model.readFromFile(filePath))
            {
                JOptionPane.showMessageDialog(null, "Error reading file",
                        "File Read Info", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Done reading from file",
                        "File Read Info", JOptionPane.INFORMATION_MESSAGE);
            }

            model.printEventList();
        });

        rightPanel.add(dayView, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.CENTER);
    }

    /**
     * Called when the model is updated.
     * @param e ChangeEvent.
     */
    public void stateChanged(ChangeEvent e)
    {
        updateDayButtons();
    }
}
