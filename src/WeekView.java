/**
 * WeekView.java
 * @author Team 9: Parnika De, Viet Dinh, Sijia Gao, and David Janda
 */
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * This class displays events that occur on the selected week.
 */
public class WeekView extends JPanel implements ChangeListener, View
{
    private static final long serialVersionUID = 1L;
    private DataModel model;
    private static Calendar calendar;

    private static final int TIME_ROWS = 25;
    private static final int TIME_COLUMN = 1;
    private static final int WEEK_COLUMNS = 8;
    private static final int COLUMNS = 2;
    private static final int ROW_HEIGHT = 38;
    private static final int COLUMN_WIDTH = 150;
    private TableModel eventTableModel;
    private JLabel dateLabel;
    private JTable eventTable;

    /**
     * Constructor that sets up the week view.
     * @param dataModel DataModel object.
     */
    public WeekView(DataModel dataModel){
        model = dataModel;
        calendar = model.getCal();
        eventTableModel = new DefaultTableModel(TIME_ROWS, WEEK_COLUMNS);

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
        eventTable = createEventTable();
        updateEventTable();
    }

    /**
     * Creates a table to show the events.
     * @return Table to show the events.
     */
    @Override
    public JTable createEventTable()
    {
        int daysInWeek = WEEK_COLUMNS;

        for (int i = 0; i < TIME_ROWS; ++i)
        {
            for (int j = 0; j < daysInWeek; ++j)
            {
                if (i == 0)
                {
                    DAYS[] arrayOfDays = DAYS.values();
                    int day = calendar.get(Calendar.DATE);
                    String s = arrayOfDays[j] + "";
                    eventTableModel.setValueAt(s + "", i, j);
                }
                else
                {
                    eventTableModel.setValueAt("", i, j);
                }
            }
        }

        JTable t = new JTable(eventTableModel)
        {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row))
                {
                    c.setBackground(getBackground());
                    //int modelRow = convertRowIndexToModel(row);
                    String hiddenDataString = (String)getModel().getValueAt(0, COLUMNS - 2);
                    //System.out.println(hiddenDataString);
                    String[] hiddenDataRows = hiddenDataString.split("-");
                    String[][] hiddenDataColumns = new String[TIME_ROWS][WEEK_COLUMNS];
                    for (int i = 0; i < TIME_ROWS; i++)
                    {
                        hiddenDataColumns[i] = hiddenDataRows[i].split(":");
                    }

                    for (int i = 0; i < TIME_ROWS; i++)
                    {
                        for (int j = 0; j < WEEK_COLUMNS; j++)
                        {
                            if (!"0".equals(hiddenDataColumns[i][j]) && i == row && j == column)
                            {
                                int data = Integer.parseInt(hiddenDataColumns[i][j]) % 18;
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
                                if (data == 0) c.setBackground(new Color(210, 105, 30));
                            }
                        }
                    }
                }
                return c;
            }
        };

        for (int i = 0; i < TIME_ROWS; ++i)
        {
            t.setRowHeight(i, ROW_HEIGHT/2);
        }

        for (int i = 1; i < daysInWeek; ++i)
        {
            t.getColumnModel().getColumn(i).setPreferredWidth(COLUMN_WIDTH);
        }

        t.getColumnModel().getColumn(0).setMinWidth(0);
        t.getColumnModel().getColumn(0).setMaxWidth(0);
        t.getColumnModel().getColumn(0).setWidth(0);
        return t;
    }

    /**
     * Creates a table to display the time.
     * @return Table to display the time.
     */
    @Override
    public JTable timeTable(){
        TableModel model = new DefaultTableModel(TIME_ROWS, TIME_COLUMN);

        JTable t = new JTable(model);
        for (int i = 0; i < TIME_ROWS; ++i)
        {
            String s = " ";
            if(i < 9)
                s += "0";
            s += (i+1) + ":00";
            if(i < TIME_ROWS-2)
                model.setValueAt(s, i + 1, TIME_COLUMN - 1);

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
        List<Event> eventList = model.getEventInSelectedView("week");
        int eventCount = 1;
        int hiddenDataRow = 1;
        int hiddenDataColumn = 1;
        int[][] hiddenData = new int[TIME_ROWS][WEEK_COLUMNS];

        for (int i = 0; i < TIME_ROWS; i++)
        {
            for (int j = 0; j < WEEK_COLUMNS; j++)
            {
                hiddenData[i][j] = 0;
            }
        }

        for (Event event: eventList)
        {
            int eventIndex = (int) event.getStartHour();
            int hightlightIndex = (int) event.getEndHour();
            int eventColumn = event.getDayOfWeek() - 1;
            eventTableModel.setValueAt(event.getName(), eventIndex, eventColumn + COLUMNS - 1);
            hiddenDataColumn = eventColumn + COLUMNS - 1;
            for (hiddenDataRow = eventIndex; hiddenDataRow < hightlightIndex; hiddenDataRow++)
            {
                hiddenData[hiddenDataRow][hiddenDataColumn] = eventCount;
            }
            eventCount++;

        }

        String hiddenDataString = "";
        for (int i = 0; i < TIME_ROWS; i++)
        {
            for (int j = 0; j < WEEK_COLUMNS; j++)
            {
                hiddenDataString += hiddenData[i][j] + ":";
            }
            hiddenDataString += "-";
            //System.out.println(hiddenDataString);
        }

        eventTableModel.setValueAt(hiddenDataString, 0, COLUMNS - 2);
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
        String s = arrayOfDays[dayofweek] + " " + Integer.toString(day) + " [Week View]";
        dateLabel.setText(s);
    }
}
