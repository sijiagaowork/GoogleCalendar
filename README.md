# GoogleCalendar

##Summary 
In the project for our CS151 course at SJSU, we designed and implemented a calendar similar to the google calendar. We are expected to have obtained fundamental design and programming techniques to conduct this project, including MVC (Model/View/Controller) design pattern and GUI programming skills.

##Minimum Functional Requirements

Title of your calendar
Current calendar time. (Today's calendar time is default.)
Three buttons to select different views, Day, Week, and Month
One panel to display the calendar with the selected view. (Day view is default.)
GUI interface through which the user can enter a new event schedule. The interface should include one button labeled "Add". When the user clicks on the add button, the new event is scheduled on the calendar. The user can enter events in the past as well as in the future. The view should reflect this change immediately.
GUI interface through which the user can specify the name of batch file that contains already scheduled events. The interface also includes button labeled "From file". When the user clicks on the button, the events stored in the file are scheduled on the calendar. The view will be updated after all the events of files are processed. The file contain events for the future as well as those from the past. For simplicity, assume that the user of the program will click on this function "From file" only once right after the program starts. When the user click on "From file" button, have the user enter the name of the file to read. When you submit the soft copy, include input.txt of yours that will be read when the user clicks on FromFile option.
Each event in the file takes up one line and consists of the following items separated by a semi-colon ;

event name: a name can have a space
year: 4 digits
starting month: 1 ~ 12
ending month: 1 ~ 12
days: days will be a sequence of day abbreviations, SMTWHFA, without any space in it. Days can be any order. No space is allowed in days
starting time: use 24 hour system (0 ~ 23)
ending time: use 24 hour system (0 ~ 23)
For example, the following line schedules Math Class on every M,W,F from January to February 2006. The class starts from 5:00 pm and ends at 6:00 pm on the scheduled date.

Math Class;2006;1;2;MWF;17;18;

Two buttons with an arrow to advance the current calendar time backward and forward. The user is supposed to move the calendar backward or forward inifinitely. The selected view will reflect the change immediately.
For simplicity, assume the events are scheduled based on hours. We don't consider minute and second in this project.
The user should be able to use/test your calendar without any user manual. That is, the application should have a user friendly interface.
##Additional Feature

Implement at least one additional feature of calendar. We decided to implement buttons that allowed users to choose a different layout for their calendar.

##Final Report

The main concept from this course that we applied in our project was how to create a graphical user interface. Although we first learned how to draw a variety of shapes in our Intro to Programming classes, this class introduced how to implement actual GUI programs. To bring our program to life, we used JFrames, JPanels, JTextAreas, JButtons, and other various things. We enforced Action Listeners to all the JButtons so they would act as functions to the calendar. Our team also decided to use an interface to allow multiple inheritance, i.e. our special feature utilizing the strategy pattern, the agenda layouts. We also designed our program based off of the Model View Controller architecture/pattern, which we learned about in lecture as well as in a homework assignment.

Although most of the concepts applied in our projects were learned from this course, we did have to do a little research on different material. Some concepts, we had to brush up on, such as the strategy pattern. Our team was familiar with it, but to be sure we completely understood the pattern, we did more research about it via internet and textbook. We also did research online on how to customize the look of buttons. In our calendar, we decided to make all the days of the calendar into buttons, but we did not want them to look like the default JButton. Another component that we decided to use in our calendar that was not mentioned in class was JScrollPane, which allows for horizontal and vertical scroll bars. This was in case our agenda did not fit in the dimensions of our JTextArea.
