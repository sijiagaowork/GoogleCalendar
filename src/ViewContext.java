/**
 * ViewContext.java
 * @author Team 9: Parnika De, Viet Dinh, Sijia Gao, and David Janda
 */

/**
 * Context class for the View.
 */
public class ViewContext
{
    View view;

    /**
     * Constructor that assigns the current view.
     * @param view View to use.
     */
    public ViewContext(View view)
    {
        this.view = view;
    }

    /**
     * Gets the current view.
     * @return Current view.
     */
    public View getView()
    {
        return this.view;
    }

    /**
     * Sets the current view.
     * @param view Current view.
     */
    public void setView(View view)
    {
        this.view = view;
    }
}
