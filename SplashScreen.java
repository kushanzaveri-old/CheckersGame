import java.awt.*;
import hsa.Console;
import java.lang.*;
public class SplashScreen extends Thread
{
    private Console c;

    Color erase = new Color (51, 51, 86, 255);
    Color use = new Color (46, 255, 220);
    Color square = new Color (81, 170, 191);
    Font title = new Font ("Consolas", Font.BOLD, 30);
    boolean[] truths = new boolean [504];
    public void preform ()
    {
	int boardSize = 1;
	c.setColor (erase);
	c.fillRect (0, 0, 1023, 759);
	c.setColor (use);
	while (boardSize <= 504)
	{
	    c.fillRect ((1023 - boardSize) / 2, (759 - boardSize) / 2, boardSize, boardSize);
	    boardSize++;
	    delay (3);
	}
	for (int i = 1 ; i <= 534 ; i++)
	{
	    c.setColor (Color.black);
	    c.fillRect (244, 112, i, 15);
	    c.fillRect (778 - i, 631, 1, 15);
	    c.fillRect (763, 112, 15, i);
	    c.fillRect (244, 646 - i, 15, 1);
	    delay (4);
	}
	c.setColor (square);
	for (int i = 63 ; i <= 441 ; i += 126)
	{
	    c.fillRect (259, 127 + i, 63, 63);
	    c.fillRect (700, 568 - i, 63, 63);
	    delay (10);
	    for (int x = 63 ; x <= i ; x += 63)
	    {

		c.fillRect (259 + x, 127 + i - x, 63, 63);
		c.fillRect (700 - x, 568 - i + x, 63, 63);
		delay (40);
		if (i == 441 && x == 189)
		    break;
	    }
	}

	for (int y = 0 ; y <= 126 ; y += 63)
	{
	    for (int x = 0 ; x <= 378 ; x += 126)
	    {
		for (int i = 0 ; i < 40 ; i++)
		{
		    c.setColor (erase);
		    c.fillOval (353 - i / 2 + x + (y % 2 == 0 ? 0:
		    - 63), 137 + y, i, i);
		    c.setColor (use);
		    c.fillOval (354 - i / 2 + x + (y % 2 == 0 ? -63:
		    0), 578 - y, i, i);
		    delay (5);
		}
	    }
	}
	c.setColor (erase);
	c.setFont (title);
	for (int i = 1 ; i <= 50 ; i++)
	{
	    erase = new Color (51, 51, 86, 0 + i);
	    c.setColor (erase);
	    for (int x = 0 ; x < 8 ; x++)
		c.drawString ("" + "CHECKERS".charAt (x), 282 + (x) * 63, 355);
	    delay (20);
	}
	for (int i = 1 ; i <= 50 ; i++)
	{
	    erase = new Color (51, 51, 86, 0 + i);
	    c.setColor (erase);
	    for (int x = 0 ; x < 8 ; x++)
	    {
		if (x == 2)
		    c.drawString (":", 250 + x * 63, 420);
		c.drawString ("" + "BYKUSHAN".charAt (x), 282 + x * 63, 420);
	    }

	    delay (20);
	}
	delay (1500);
	clearScreen ();
    }


    private void delay (int x)
    {
	try
	{
	    sleep (x);
	}
	catch (InterruptedException e)
	{
	}
    }


    private void clearScreen ()
    {
	int rand = 0;
	erase = new Color (51, 51, 86, 255);
	c.setColor (erase);
	for (int i = 0 ; !isAllTrue () ; i++)
	{
	    rand = ((int) (Math.random () * 504) + 1);
	    if (truths [rand - 1] != true)
	    {
		c.drawLine (rand + 243, 0, rand + 243, 759);
		c.drawLine (778 - rand, 0, 778 - rand, 759);
		truths [rand - 1] = true;
		delay (5);
	    }
	}
	title = new Font ("Consolas", Font.BOLD, 50);
	c.setColor (use);
	for (int x = 0 ; x < 5 ; x++)
	{
	    c.drawString ("" + "ENJOY".charAt (x), 354 + x * 63, 420);
	    delay (250);
	}
	delay (1500);
    }


    private boolean isAllTrue ()
    {
	for (int x = 0 ; x < 504 ; x++)
	{
	    if (truths [x] == false)
		return false;
	}
	return true;
    }


    public SplashScreen (Console con)
    {
	c = con;
    }


    public void run ()
    {
	preform ();
    }
}


