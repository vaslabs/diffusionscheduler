import java.util.ArrayList;
import java.util.List;

/**
 * This class has been deprecated and it is not used any more
 * for scheduling statistics
 * @author nicolav0
 *@deprecated
 */
public class Statistics {
	
	private List<String> titles;
	private List<String> descriptions;
	private List<String> values;

	public Statistics()
	{
		titles = new ArrayList<String>();
		descriptions = new ArrayList<String>();
		values = new ArrayList<String>();
	}
	
	public void log(String title, String description, String value)
	{
		titles.add(title);
		descriptions.add(description);
		values.add(value);
	}

	public void showLog()
	{
		for (int i = 0; i < titles.size(); i++)
			System.out.println(titles.get(i) + " | " + descriptions.get(i) + " | " + descriptions.get(i));
	}
}
