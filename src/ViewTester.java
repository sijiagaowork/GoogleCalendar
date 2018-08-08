import javax.swing.*;

public class ViewTester
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame();
                DataModel dataModel = new DataModel();
                DayView dayview = new DayView(dataModel);
                //frame.setLayout(new FlowLayout());
                frame.add(dayview);
                frame.setSize(600, 400);
                frame.setVisible(true);
                //frame.setBackground(Color.WHITE);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}