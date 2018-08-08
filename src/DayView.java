/**
 * DayView.java
 * @author Team 9: Parnika De, Viet Dinh, Sijia Gao, and David Janda
 */
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * Days of the week.
 */
enum DAYS
{
    Dummy, Sun, Mon, Tue, Wed, Thu, Fri, Sat;
}

/**
 * This class displays events that occur on the selected day.
 */
public class DayView extends JPanel implements ChangeListener, View
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private DataModel model;
    private static Calendar calendar;
    private static final int TIME_ROWS = 23;
    private static final int TIME_COLUMN = 1;
    private static final int COLUMN = 2;
    private static final int ROW_HEIGHT = 64;
    private TableModel eventTableModel;
    private JLabel dateLabel;
    private JTable eventTable;
    private int tempDate[];

    /**
     * Constructor that sets up the day view.
     * @param dataModel DataModel object.
     */
    public DayView(DataModel dataModel)
    {
        model = dataModel;
        calendar = model.getCal();
        eventTableModel = new DefaultTableModel(TIME_ROWS,COLUMN);
        tempDate = new int[]{calendar.get(Calendar.DATE),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR)};

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        dateLabel = new JLabel();
        updateLabel();

        JPanel fullPanel = new JPanel(new BorderLayout());
        eventTable = createEventTable();
        updateEventTable();
        fullPanel.add(timeTable(), BorderLayout.WEST);
        fullPanel.add(eventTable, BorderLayout.CENTER);
        scrollPane.setViewportView(fullPanel);

        add(dateLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Paints the component.
     * @param g Graphics context.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }

    /**
     * Called when the model is updated.
     * @param event ChangeEvent.
     */
    @Override
    public void stateChanged(ChangeEvent event)
    {
        updateLabel();
        updateEventTable();
    }

    /**
     * Creates a table to show the events.
     * @return Table to show the events.
     */
    @Override
    public JTable createEventTable()
    {
        for (int i = 0; i < TIME_ROWS; ++i)
        {
            eventTableModel.setValueAt(0, i, COLUMN-1);
        }
        JTable t = new JTable(eventTableModel)
        {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row))
                {
                    c.setBackground(getBackground());
                    int modelRow = convertRowIndexToModel(row);
                    int data = (int)getModel().getValueAt(modelRow, COLUMN - 1);
                    if (data == 1) c.setBackground(Color.YELLOW);
                    if (data == 2) c.setBackground(new Color(176,224,230));
                    if (data == 3) c.setBackground(Color.RED);
                    if (data == 4) c.setBackground(Color.PINK);
                    if (data == 5) c.setBackground(Color.ORANGE);
                    if (data == 6) c.setBackground(Color.MAGENTA);
                    if (data == 7) c.setBackground(Color.LIGHT_GRAY);
                    if (data == 8) c.setBackground(Color.GREEN);
                    if (data == 9) c.setBackground(new Color(128, 0, 128));
                    if (data == 10) c.setBackground(new Color(0, 128, 128));
                    if (data == 11) c.setBackground(new Color(152,251,152));
                    if (data == 12) c.setBackground(new Color(128, 128, 0));
                    if (data == 13) c.setBackground(new Color(128, 0, 0));
                    if (data == 14) c.setBackground(new Color(192, 192, 192));
                    if (data == 15) c.setBackground(new Color(0, 255, 255));
                    if (data == 16) c.setBackground(new Color(255, 215, 0));
                    if (data == 17) c.setBackground(new Color(255, 127, 0));
                    if (data == 18) c.setBackground(new Color(210, 105, 30));
                }

                return c;
            }
        };

        for (int i = 0; i < TIME_ROWS; ++i)
        {
            t.setRowHeight(i, ROW_HEIGHT/2);
        }

        t.getColumnModel().getColumn(1).setMinWidth(0);
        t.getColumnModel().getColumn(1).setMaxWidth(0);
        t.getColumnModel().getColumn(1).setWidth(0);

        return t;
    }

    /**
     * Creates a table to display the time.
     * @return Table to display the time.
     */
    @Override
    public JTable timeTable(){
        TableModel model = new DefaultTableModel(TIME_ROWS,TIME_COLUMN);

        for (int i = 1; i < 13; ++i)
        {
            String s = " " + i + "am";
            model.setValueAt(s, i-1, TIME_COLUMN-1);
        }

        for (int i = 1; i < 12; ++i)
        {
            String s = " " + i + "pm";
            model.setValueAt(s, i+11, TIME_COLUMN-1);
        }

        JTable t = new JTable(model);
        for (int i = 0; i < TIME_ROWS; ++i)
        {
            t.setRowHeight(i, ROW_HEIGHT);
        }

        return t;
    }

    /**
     * Updates the table to display events.
     */
    @Override
    public void updateEventTable()
    {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        if (tempDate[0] != day || tempDate[1] != month || tempDate[2] != year)
        {
            for (int i = 0; i < TIME_ROWS; ++i)
            {
                eventTableModel.setValueAt("", i, COLUMN - 2);
                eventTableModel.setValueAt(0, i, COLUMN - 1);
            }
            tempDate[0] = day;
            tempDate[1] = month;
            tempDate[2] = year;
        }

        List<Event> eventList = model.getEventInSelectedView(year, month, day, year, month, day);

        int hiddenData = 1;
        for (Event event: eventList)
        {
            int eventIndex = ((int)event.getStartHour() - 1);
            int hightlightIndex = ((int)event.getEndHour() - 1);
            eventTableModel.setValueAt(event.getName(), eventIndex, COLUMN - 2);
            for (int i = eventIndex; i <= hightlightIndex; ++i)
            {
                eventTableModel.setValueAt(hiddenData, i, COLUMN - 1);
            }
            ++hiddenData;
        }

        DefaultTableModel tableModel = (DefaultTableModel) eventTable.getModel();
        tableModel.fireTableDataChanged();

        for (int i = 0; i < TIME_ROWS; ++i)
        {
            eventTable.setRowHeight(i, ROW_HEIGHT);
        }
    }

    /**
     * Updates the label that displays the date.
     */
    @Override
    public void updateLabel()
    {
        DAYS[] arrayOfDays = DAYS.values();
        int day = calendar.get(Calendar.DATE);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        String s = arrayOfDays[dayofweek] + " " + Integer.toString(day) + " [Day View]";
        dateLabel.setText(s);
    }
}
