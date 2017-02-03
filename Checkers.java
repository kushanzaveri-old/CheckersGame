/*
    Name: Kushan Zaveri
    Program Name : Checkers.java
    Date: January 13th , 2015
    Description:

    Instance Variable Dictionary
+------------------+--------------------------+----------------------------------------------------------------------------------+
|       Name       |           Type           |                                     Purpose                                      |
+------------------+--------------------------+----------------------------------------------------------------------------------+
| c                | Console                  | This is a reference variable which                                               |
|                  |                          | gives access to all of the methods                                               |
|                  |                          | in the Console class.                                                            |
| playingSide      | int                      | This variable will be 1 if player one                                            |
|                  |                          |  is playing as red, or will be 2 if player                                       |
|                  |                          | two is playing as red. It is essentially the                                     |
|                  |                          | side who is playing as red.                                                      |
| playerOne        | String                   | This variable holds the name of player one                                       |
| playerTwo        | String                   | This variable holds the name of player two.                                      |
| ROWS             | final int                | Has a value of 8, stores the amount of rows                                      |
|                  |                          | in the board array.                                                              |
| COLUMNS          | final int                | Has a value of 8, stores the amount of columns                                   |
|                  |                          |  in the board array.                                                             |
| EMPTY            | final int                | Has a value of 0. The board identifies this value                                |
|                  |                          |  as an empty tile.                                                               |
| RED              | final int                | Has a value of 1. The program identifies this value                              |
|                  |                          |  as a red piece                                                                  |
| RED_KING         | final int                | Has a value of 2. The program                                                    |
|                  |                          | identifies this value as a red king.                                             |
| BLACK            | final int                | Has a value of 4. The program identifies                                         |
|                  |                          | this value as a black piece.                                                     |
| BLACK_KING       | final int                | Has a value of 5. The program identifies                                         |
|                  |                          | this value as a black king.                                                      |
| HIGHSCORES       | final int                | Has a value of 10. Stores the max amount of                                      |
|                  |                          | highscores there can be.                                                         |
| movingSide       | int                      | Holds the value of who is currently                                              |
|                  |                          | moving (RED/BLACK).                                                              |
| chosenSquare     | int array (1 dimension)  | In index 0 it will hold the Column                                               |
|                  |                          | and in index 1 it will hold the Row of the                                       |
|                  |                          | selected tile. This is used for selecting                                        |
|                  |                          | the moving piece.                                                                |
| chosenSquare1    | int array (1 dimension)  | In index 0 it will hold the Column                                               |
|                  |                          |  and in index 1 it will hold the Row of the                                      |
|                  |                          | selected tile. This is used for selecting                                        |
|                  |                          | the moving location.                                                             |
| board            | int array (2 dimensions) | This is an array with a total of 64 spaces                                       |
|                  |                          | (one for each space on the board).                                               |
|                  |                          | This array will keep track of all the pieces and                                 |
|                  |                          |  where they are on the board.                                                    |
| gameOver         | boolean                  | Will be true if the game is over. It is the variable                             |
|                  |                          |  that determines when to end the game.                                           |
| movableLocations | int array (2 dimensions) | Holds the row and column of all the locations                                    |
|                  |                          | a piece can move. Updates after a piece is selected.                             |
| jumpsPossible    | boolean                  | Will be true if jumps are possible and will be false if jumps are not.           |
| buttonColor      | Color                    | This is a color that is used throughout the game for buttons.                    |
| xSquare          | int                      | Holds the column location of the selected piece during the userInput method.     |
|                  |                          |  This is to prevent the cursor from moving.                                      |
| ySquare          | int                      | Holds the row location of the selected piece during the userInput method.        |
|                  |                          |  This is to prevent the cursor from moving.                                      |
| fileOpened       | boolean                  | This will be true if the file was already opened.                                |
| fileCorrupt      | boolean                  | This will be true if the file is corrupted and will be false if the file is not. |
+------------------+--------------------------+----------------------------------------------------------------------------------+
*/
import hsa.Console;
import java.awt.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Checkers
{
    // Instance Variables
    Console c;
    String playerOne, playerTwo, winner;
    final int ROWS = 8, COLUMNS = 8, EMPTY = 0, RED = 1, RED_KING = 2, BLACK = 4, BLACK_KING = 5, HIGHSCORES = 10;
    int movingSide, xSquare, ySquare, playingSide, mainMenuChoice;
    int[] chosenSquare = new int [2], chosenSquare1 = new int [2];
    int[] [] board = new int [ROWS] [COLUMNS];
    boolean[] [] movableLocations = new boolean [ROWS] [COLUMNS];
    boolean jumpsPossible, gameOver, fileOpened, fileCorrupt;
    Color buttonColor = new Color (211, 125, 14);
    String[] entries = new String [HIGHSCORES];
    Font defaultGameFont = new Font ("Calibri", 0, 30);
    public void splashScreen ()
    {
	SplashScreen s = new SplashScreen (c);
	s.run ();
    }


    private char pauseProgram ()
    {
	return c.getChar ();
    }


    public void openFile ()
    {
	if (!fileCorrupt)
	{
	    String tempStr;
	    try
	    {
		BufferedReader br = new BufferedReader (new FileReader ("highscores.kaz"));
		tempStr = br.readLine ();
		if (tempStr != null && tempStr.equals ("File valid"))
		{
		    for (int i = 0 ; i < HIGHSCORES ; i++)
		    {
			tempStr = br.readLine ();
			if (tempStr == null)
			    break;
			else
			{
			    entries [i] = tempStr;
			    fileOpened = true;
			    fileCorrupt = false;
			}
		    }
		}
		else
		    fileCorrupt = true;
		br.close ();
	    }
	    catch (IOException e)
	    {
		fileCorrupt = true;
	    }
	}
    }


    /*
	Purpose: The purpose of this method is to display the
	main menu and to accept the selection of the option
	(by calling a helper method).
    */
    public void mainMenu ()
    {
	c.setFont (defaultGameFont);
	movingSide = RED;
	displayImage ("mainMenu.jpg");
	selection (true);
    }


    /*
	Purpose: This is a helper method that will accept
	the selection of either the main menu choice or
	who will play as red.

	Purpose of loops:
	There is one while loop in the method. It is there
	so that the user can move the cursor as long as
	they have not pressed enter.

	Purpose of conditional statements:
	There are multiple conditional statements

	The first conditional statement checks if the cursor has been
	moved (with use of the changes boolean variable). If it has
	moved, the program will preformChanges

	The next conditional statement (ternary operator) is there in
	order to convert the input in to lower case.

	After that, the following conditional statement checks if enter
	is pressed. If it is, it will assign values and break. If enter
	was not pressed, the program will move on to the last if statement
	(explained next)

	The last if statement is to check whether or not a legal key was
	pressed and if it was, it will change the offset accordingly
	as well as make changes = true. If a legal key was not pressed,
	changes = false.

	Local variable dictionary
       +---------+---------+--------------------------------------------------------------------------+
       |  Name   |  Type   |                                  Purpose                                 |
       +---------+---------+--------------------------------------------------------------------------+
       | input   | char    | Holds the user input as a character                                      |
       |         |         |                                                                          |
       | offset  | int     | The purpose of this variable is to hold the offset. This offset is used  |
       |         |         | when drawing the cursor on the screen.                                   |
       |         |         |                                                                          |
       | changes | boolean | This variable will be used to determine if changes were made or not.     |
       |         |         | This variable is used in a conditional statement mentioned above         |
       |         |         |                                                                          |
       | mode    | boolean | This variable will be true/false depending on where this method is       |
       |         |         | called. Mode will be true if it comes from mainMenu() and will be        |
       |         |         | false if it comes from gameSetup(). This variable is what determines     |
       |         |         | the current purpose of this method. It is a parameter.                   |
       +---------+---------+--------------------------------------------------------------------------+
    */
    private void selection (boolean mode)
    {
	char input = ' ';
	int offset = 0;
	boolean changes = true;
	while (true)
	{
	    if (changes)
		preformChanges (offset, mode);
	    input = c.getChar ();
	    input = input >= 'A' && input <= 'Z' ? input += 32:
	    input;
	    if ((int) input == 10)
	    {
		if (mode)
		    mainMenuChoice = offset / 153 + 1;
		else
		    playingSide = offset / 512 + 1;
		break;
	    }
	    else
	    {
		if (input == 's' && mode)
		{
		    offset = offset == 459 ? 0:
		    offset + 153;
		    changes = true;
		}
		else if (input == 'd' && !mode)
		{
		    offset = offset >= 512 ? 0:
		    offset + 512;
		    changes = true;
		}
		else
		    changes = false;
	    }
	}
    }


    /*
	Purpose: The purpose of this method is draw and erase
	the cursor in a new location. It is called in the
	selection() method.

	Purpose of conditional statements

	The one conditional statement is here to determine which
	mode the method is operating in. If mode is true it is
	operating in mode 1 and so the method will preform different
	commands. Otherwise it is operating in mode 2, and once again
	operating accordingly.

	+--------+---------+-----------------------------------------------------+
	| Name   |  Type   |                       Purpose                       |
	+--------+---------+-----------------------------------------------------+
	| offset | int     | This is a parameter. The offset is determined by    |
	|        |         | the selection() method. The offset is used to know  |
	|        |         | by how much the coordinates should change when dr-  |
	|        |         | awing the new square.                               |
	|        |         |                                                     |
	| mode   | boolean | This is a parameter. It is used to know the current |
	|        |         | purpose of this method. Depending on its value, the |
	|        |         | offset will affect a different coordinate.          |
	+--------+---------+-----------------------------------------------------+
    */
    private void preformChanges (int offset, boolean mode)
    {
	if (mode)
	{
	    c.setColor (Color.white);
	    c.drawRoundRect (329, 187 + offset, 365, 71, 10, 10);
	    c.setColor (buttonColor);
	    c.drawRoundRect (329, 187 + (offset > 0 ? offset - 153:
	    459), 365, 71, 10, 10);
	}
	else
	{
	    c.setColor (Color.white);
	    c.drawRoundRect (83 + offset, 655, 345, 65, 10, 10);
	    c.setColor (buttonColor);
	    c.drawRoundRect (83 + (offset >= 512 ? 0:
	    512), 655, 345, 65, 10, 10);
	}
    }


    /*
	Purpose: Displays the instructions of the program. Will return
	to the main menu after the user presses a key.
    */
    public void instructions ()
    {
	displayImage ("Instructions.jpg");
	pauseProgram ();
    }


    /*

    */
    public void highScores ()
    {
	if (!fileOpened)
	    openFile ();
	if (entries [0] == null)
	{
	    JOptionPane.showMessageDialog (null, "File is corrupt/empty");
	    fileOpened = false;
	}
	else
	{
	    displayImage ("Winners.jpg");
	    c.setColor (Color.white);
	    for (int i = 0 ; i < HIGHSCORES ; i++)
	    {
		if (entries [i] != null)
		    c.drawString (entries [i], 50, 170 + i * 30);
		else
		    break;
	    }
	    if (c.getChar () == '1')
		clearFile ();
	}
    }


    private void clearFile ()
    {
	for (int i = 0 ; i < HIGHSCORES ; i++)
	    entries [i] = null;
	try
	{
	    PrintWriter pw = new PrintWriter (new FileWriter ("highscores.kaz"));
	    pw.println ("File valid");
	    pw.close ();
	}
	catch (IOException e)
	{
	}
    }


    private void addEntry ()
    {
	if (!fileOpened)
	    openFile ();
	for (int i = 9 ; i > 0 ; i--)
	    if (entries [i - 1] != null)
		entries [i] = entries [i - 1];
	entries [0] = winner;
	try
	{
	    PrintWriter pw = new PrintWriter (new FileWriter ("highscores.kaz"));
	    pw.println ("File valid");
	    for (int i = 0 ; i < 10 ; i++)
		if (entries [i] != null)
		    pw.println (entries [i]);
	    pw.close ();
	}
	catch (IOException e)
	{
	}
	fileCorrupt = false;
    }


    /*
	Purpose: Asks the users for their names and manipulates the
		 name if necessary. It will also accept the selection
		 of which player wants to play as red.

	Purpose of any loops:

	Both while loops are there to ask the user for their name until
	a valid name (not blank) is entered.

	Purpose of any conditional statements:

	The first and second conditional statements are there to check
	if the entered name is blank and if it is, it will ask for the
	user input again.

	The first and second use of the ternary operator is to reduce
	the length of both names if they are too long.

	The third use of the ternary operator is to add the number 2
	after player two's name if it matches player one's name.



    */
    public void gameSetup ()
    {
	displayImage ("GameSetup.jpg");
	while (true)
	{
	    c.setCursor (12, 1);
	    playerOne = c.readLine ();
	    if (!playerOne.equals (""))
		break;
	    JOptionPane.showMessageDialog (null, "You must enter a valid name, it cannot be blank.");
	}
	while (true)
	{
	    c.setCursor (21, 1);
	    playerTwo = c.readLine ();
	    if (!playerTwo.equals (""))
		break;
	    JOptionPane.showMessageDialog (null, "You must enter a valid name, it cannot be blank");
	}
	playerOne = playerOne.length () > 29 ? playerOne.substring (0, 29):
	playerOne;
	playerTwo = playerTwo.length () > 29 ? playerTwo.substring (0, 29):
	playerTwo;
	playerTwo = playerOne.equalsIgnoreCase (playerTwo) ? playerTwo + "2":
	playerTwo;
	selection (false);
	initialBoard ();
    }


    /*
	Purpose: Fills the board with the initial values.

	Purpose of any loops:

	The nested for loop is so that the program goes through
	every space of the array.
    */
    private void initialBoard ()
    {
	for (int i = 0 ; i < ROWS ; i++)
	{
	    for (int x = 0 ; x < COLUMNS ; x++)
	    {
		board [i] [x] = i % 2 != x % 2 ? (i > 4 ? RED:
		(i < 3 ? BLACK:
		    EMPTY)):
		    EMPTY;
	    }
	}
    }


    /*
	Purpose: This method is responsible for (re)drawing the game screen.

	Purpose of any loops:

	The for loop is here so that program goes through every space in the
	array

	Purpose of any conditional statements:

	The first if statement checks if the current square is a black square.
	The reason why this is done is so that the program knows not to cont-
	inue any further if is a white square since a piece can never be on
	a white square in checkers.

	After that, there is a use of the ternary operator which is there to
	determine which color to to draw the piece.

	The last if statement is to know if the letter 'K' should be drawn on
	the piece or not based on if the piece at the location is a king.

	The two uses of the ternary operator at the end of the method are there
	to determine which player's name to write on the black and red side of
	the board.

	Local Loop Variables:

	i, int, goes through every row of the board. Starts at 0 and ends at 7
	increments by one.
	x, int, goes through every column of the board. Starts at 0 and ends at 7
	increments by one.
    */
    public void drawBoard ()
    {
	displayImage ("GameBoard.jpg");
	for (int i = 0 ; i < ROWS ; i++)
	{
	    for (int x = 0 ; x < COLUMNS ; x++)
	    {
		if (i % 2 != x % 2)
		{
		    c.setColor (board [i] [x] == RED || board [i] [x] == RED_KING ? Color.red:
		    board [i] [x] == BLACK || board [i] [x] == BLACK_KING ? Color.gray:
		    Color.black);
		    c.fillOval (x * 60 + 83, i * 60 + 190, 40, 40);
		    c.setColor (Color.white);
		    if (board [i] [x] == BLACK_KING || board [i] [x] == RED_KING)
		    {
			c.drawString ("K", x * 60 + 95, i * 60 + 220);
		    }
		}
	    }
	}
	c.drawString (playingSide == 1 ? playerOne:
	playerTwo, 100, 710);
	c.drawString (playingSide == 1 ? playerTwo:
	playerOne, 100, 150);
    }


    private void displayImage (String imageLocation)
    {
	Image picture = Toolkit.getDefaultToolkit ().getImage (imageLocation);
	MediaTracker tracker = new MediaTracker (new Frame ());
	tracker.addImage (picture, 0);
	try
	{
	    tracker.waitForAll ();
	}
	catch (InterruptedException e)
	{
	}
	c.drawImage (picture, 0, 0, null);
    }


    /*
	Purpose: This variable is responsible for asking the user input
		for the piece to move and where to move it. It also er-
		rror traps these choices

	Purpose of any loops:

	The first two for loops (nested) are there mainly to check
	which pieces can leally move. This section also establishes
	the mandatory jump rule

	The next two loops are while loops that repeat until a valid
	selection is made (for either the moving piece and the movi-
	ng location).

	Purpose of any conditional statements:

	The first set of nested if statements are there in order to
	check if the location in the board matches the moving side,
	if that piece has any legal moves, and which array to store
	that piece's location in.

	The next if statement makes sure that if there is a jump pos-
	sible all the other legal 'moves' are replace with the jumps

	After that, there are two similar if statements (if(!gameOver))
	that check if game is over, and if it is don't execute any more code
	(skip over it).

	The last set of if statements are within in the while loop. They are
	there to check if the selection was legal and if true, then break.

	Local Variable Dictionary
	+----------------+--------------+----------------------------------------------------+
	|      Name      |     Type     |                      Purpose                       |
	+----------------+--------------+----------------------------------------------------+
	| possibleMoves  | boolean[][]  | A specific location in the array will be true      |
	|                |              | if the piece can legally move. This location in    |
	|                |              | the array is the same row,column location it would |
	|                |              |  be in the board array                             |
	|                |              |                                                    |
	| possibleJumps  | boolean[][]  | A specific location in the array will be true      |
	|                |              | if the piece can legally jump. This location in    |
	|                |              | the array is the same row,column location it would |
	|                |              |  be in the board array.                            |
	|                |              |                                                    |
	| isJump         | boolean      | Will be true if there are jumps and will be false  |
	|                |              | if there aren't any                                |
	+----------------+--------------+----------------------------------------------------+
	Local Loop Variables:
	i, int, goes through every row of the board. Starts at 0 and ends at 7
	increments by one.
	x, int, goes through every column of the board. Starts at 0 and ends at 7
	increments by one.
    */
    public void askData ()
    {
	boolean[] [] possibleMoves = new boolean [ROWS] [COLUMNS];
	boolean[] [] possibleJumps = new boolean [ROWS] [COLUMNS];
	boolean isJump = false;
	xSquare = 0;
	ySquare = 0;
	moveIndicator (1);
	gameOver = true;
	for (int i = 0 ; i < ROWS ; i++)
	{
	    for (int x = 0 ; x < COLUMNS ; x++)
	    {
		if (board [i] [x] == movingSide || board [i] [x] == movingSide + 1)
		{
		    if (movablePieces (movingSide, i, x))
		    {
			if (jumpsPossible)
			{
			    isJump = true;
			    possibleJumps [i] [x] = true;
			}
			else
			    possibleMoves [i] [x] = true;
			gameOver = false;
		    }
		}
	    }
	}
	if (isJump)
	    possibleMoves = possibleJumps;
	// Accept Selections of location, error trap location
	if (!gameOver)
	{
	    while (true)
	    {
		chosenSquare = userInput ();
		c.setColor (chosenSquare [0] % 2 == chosenSquare [1] % 2 ? buttonColor:
		Color.black);
		c.drawRect (73 + chosenSquare [0] * 60, 181 + chosenSquare [1] * 60, 59, 59);
		if (movablePieces (movingSide, chosenSquare [1], chosenSquare [0]) && possibleMoves [chosenSquare [1]] [chosenSquare [0]] || gameOver)
		{
		    c.setColor (Color.yellow);
		    c.drawRect (74 + chosenSquare [0] * 60, 182 + chosenSquare [1] * 60, 57, 57);
		    break;
		}
		JOptionPane.showMessageDialog (null, "This tile does not contain a piece that can legally move.");
	    }
	}
	// Accepts selection of location to move it to, error trap location
	if (!gameOver)
	{
	    moveIndicator (2);
	    while (true)
	    {
		chosenSquare1 = userInput ();
		if (movableLocations [chosenSquare1 [1]] [chosenSquare1 [0]] == true || gameOver)
		    break;
		c.setColor (chosenSquare [0] % 2 == chosenSquare [1] % 2 ? buttonColor:
		Color.black);
		c.drawRect (73 + chosenSquare1 [0] * 60, 181 + chosenSquare1 [1] * 60, 59, 59);
		JOptionPane.showMessageDialog (null, "You cannot move it here! Please Try Again. If you are trying to double jump, please make the preliminary jump first.");
	    }
	}
    }


    /*
	Purpose: This is a black box method that will return the selected
		 column index 0 of an array and the selected row in index 1
		 of an array.

	Purpose of any conditional statements:

	The first conditional statement (ternary operator) is there to
	covert the entered character to lower case.

	The next conditional statement (if statement) is there to make sure
	that the entered key is not 'enter' or 'x' (special keys with different
	purposes).

	Considering that the previously mentioned conditional statement is true,
	the program will go through two uses of the ternary operator. The ternary
	operator is used in this case to make sure that the character is either
	w,a,s,d and that the square can move in that direction without going off
	the board. Following those two ternary operators is one more, which makes
	sure that valid changes were made. Lastly, following this ternary operator
	is an if statement which will make changes to the board, if valid changes
	were made when entering a key.

	On the flip side, if the keys entered were either the 'enter' key or x,
	we are brought to another if structure. Basically, it will assign values
	to the choice array if 'enter' is selected. If 'x' is selected then the
	program will make gameOver = true.

	Purpose of any loops:

	This loop will continue until either enter or x is pressed.

	Local Variable Dictionary
	+------------------+---------+-------------------------------------------+
	|       Name       |  Type   |                  Purpose                  |
	+------------------+---------+-------------------------------------------+
	| choice           | int[]   | Will hold the selected column in index    |
	|                  |         | 0 and the selected row in index 1.        |
	|                  |         |                                           |
	| y1               | int     | Will hold the previous value of the s-    |
	|                  |         | elected row.This is necessary because     |
	|                  |         | then we will know if changes were made    |
	|                  |         |                                           |
	| x1               | int     | Will hold the previous value of the s-    |
	|                  |         | elected column.This is necessary because  |
	|                  |         | then we will know if changes were made    |
	|                  |         |                                           |
	| validChangesMade | boolean | This will hold the value of true/false    |
	|                  |         | depending on if valid changes were made   |
	|                  |         | to the row or column                      |
	| input            | char    | Will hold the user's input in the form of |
	|                  |         | a character                               |
	+------------------+---------+-------------------------------------------+
    */
    private int[] userInput ()
    {
	int[] choice = new int [2];
	int y1 = 0, x1 = 0;
	boolean validChangesMade;
	char input = ' ';
	while ((int) input != 10 && input != 'x')
	{
	    c.setColor (Color.white);
	    c.drawRect (73 + xSquare, 181 + ySquare, 59, 59);
	    input = c.getChar ();
	    input = input >= 'A' && input <= 'Z' ? input += 32:
	    input;
	    if ((int) input != 10 && input != 'x')
	    {
		y1 = ySquare;
		ySquare = input == 'w' && ySquare > 0 ? ySquare - 60:
		input == 's' && ySquare < 420 ? ySquare + 60:
		ySquare;
		x1 = xSquare;
		xSquare = input == 'a' && xSquare > 0 ? xSquare - 60:
		input == 'd' && xSquare < 420 ? xSquare + 60:
		xSquare;
		validChangesMade = x1 != xSquare || y1 != ySquare ? true:
		false;
		if (validChangesMade)
		{
		    c.setColor ((x1 / 60) % 2 == (y1 / 60) % 2 ? buttonColor:
		    Color.black);
		    c.drawRect (73 + x1, 181 + y1, 59, 59);
		}
	    }
	    else
	    {
		if ((int) input == 10)
		{
		    choice [0] = xSquare / 60;
		    choice [1] = ySquare / 60;
		}
		else
		    gameOver = true;
	    }
	}
	return choice;
    }


    /*
	Purpose: This method stores the locations of where a piece
	can move/jump, and returns true/false if it can legally move/
	jump.


	Purpose of any loops:
	The first nested loop is there in order to clear the list of all
	the legal locations that a piece can move from the last time this
	method was accessed.

	The next loop is there so that it repeats the code in the loop for
	all the regular pieces on the board and the kings on the board of
	the same color. (checking for jumps)

	The loop after that checks through the array that stores all of the
	movable locations to see if there was a jump.

	The following  loop is there so that it repeats the code in the
	loop for all the regular pieces on the board and the kings on the
	board of the same color. (checking for moves)

	The final nested loop is to see if there are any moves and if so,
	it will return true, otherwise it will return false.
	Purpose of any conditional statements:

	Local Loop Variables:

	a, int, goes through every row of the array. Starts at 0 and ends at 7
	increments by one.

	b, int, goes through every column of the array. Starts at 0 and ends at 7
	increments by one.
	The above are used multiple times for the same purpose.

	i,int, changes value from 0 to 1 so that the color changes in the loop.
	Starts at 0, ends at 1, increments by 1.

	Purpose of any conditional statements:

	There is an if statement that ensure that the location on the board that
	is selected matches the color of the piece moving.

	There are four if statements that call canJump. The difference between each
	if statement is that it checks if the piece can jump in every direction (4).
	If this is true, it will store the location in the movableLocations array.

	Next is an if statement that checks of jumps are not possible. If this is true,
	then the program will look for legal moves. Otherwise, it is not necessary to do
	so.

	Then there are four if statements that call canMove. The difference between each
	if statement is that it checks if the piece can move in every direction (4).
	If this is true, it will store the location in the movableLocations array.

	Finally, the last if statement is there to check if there are any legal moves,
	and if it is true, the program will return true, otherwise, it will not meet
	any conditionals and ultimately return false.


	Local Variable Dictionary:


	+-------+------+--------------------------------------------------+
	| Name  | Type |                     Purpose                      |
	+-------+------+--------------------------------------------------+
	| color | int  | This holds the value of the color of the pieces  |
	|       |      | to search legal moves for.                       |
	| i     | int  | This variable holds the "i" location, or the row |
	|       |      | of the selected piece.                           |
	| x     | int  | This variable holds the "x" location, or the row |
	|       |      | of the selected piece.                           |
	+-------+------+--------------------------------------------------+
    */

    private boolean movablePieces (int color, int y, int x)
    {
	jumpsPossible = false;
	for (int a = 0 ; a < ROWS ; a++)
	{
	    for (int b = 0 ; b < COLUMNS ; b++)
	    {
		movableLocations [a] [b] = false;
	    }
	}


	for (int i = 0 ; i <= 1 ; i++)
	{
	    color += i;
	    if (board [y] [x] == color)
	    {
		if (canJump (color, y, x, y - 1, x - 1, y - 2, x - 2))
		    movableLocations [y - 2] [x - 2] = true;
		if (canJump (color, y, x, y + 1, x - 1, y + 2, x - 2))
		    movableLocations [y + 2] [x - 2] = true;
		if (canJump (color, y, x, y - 1, x + 1, y - 2, x + 2))
		    movableLocations [y - 2] [x + 2] = true;
		if (canJump (color, y, x, y + 1, x + 1, y + 2, x + 2))
		    movableLocations [y + 2] [x + 2] = true;
	    }
	}


	for (int a = 0 ; a < ROWS ; a++)
	{
	    for (int b = 0 ; b < COLUMNS ; b++)
	    {
		if (movableLocations [a] [b] == true)
		{
		    jumpsPossible = true;
		    break;
		}
	    }
	}


	// Moving
	if (!jumpsPossible)
	{
	    color -= 1;
	    for (int i = 0 ; i <= 1 ; i++)
	    {
		color += i;
		if (board [y] [x] == color)
		{
		    if (canMove (color, y, x, y + 1, x - 1))
			movableLocations [y + 1] [x - 1] = true;
		    if (canMove (color, y, x, y - 1, x - 1))
			movableLocations [y - 1] [x - 1] = true;
		    if (canMove (color, y, x, y + 1, x + 1))
			movableLocations [y + 1] [x + 1] = true;
		    if (canMove (color, y, x, y - 1, x + 1))
			movableLocations [y - 1] [x + 1] = true;
		}
	    }
	}


	for (int a = 0 ; a < ROWS ; a++)
	{
	    for (int b = 0 ; b < COLUMNS ; b++)
	    {
		if (movableLocations [a] [b] == true)
		    return true;
	    }
	}


	return false;
    }


    /*
	Purpose: This method will determine if a piece can move based on the input from
		 the parameters. If it can true will be returned, otherwise false.

	Purpose of ternary operator:
	There are many parts to this ternary operator, I will explain them below

	1. Check if the landing location falls on the board
	2. Check if the landing location is unoccupied
	3. Make sure that the piece is moving in the correct direction
	    This is determined by color and the difference between rowTo and rowFrom
	If all are true, the move is legal, otherwise the move is illegal.
       Local Variable Dictionary:

	+---------+------+--------------------------------------------------------+
	|  Name   | Type |                        Purpose                         |
	+---------+------+--------------------------------------------------------+
	| color   | int  | This holds the value of the color of the pieces        |
	|         |      | to search legal moves for.                             |
	| rowFrom | int  | This will hold the location of the row the piece is    |
	|         |      | coming from.                                           |
	| rowTo   | int  | This will hold the location of the row the piece is    |
	|         |      | going to.                                              |
	| colFrom | int  | This will hold the location of the column the piece is |
	|         |      | coming from.                                           |
	| colTo   | int  | This will hold the location of the column the piece is |
	|         |      | going to.                                              |
	+---------+------+--------------------------------------------------------+
    */

    private boolean canMove (int color, int rowFrom, int colFrom, int rowTo, int colTo)
    {
	return rowTo >= 0 && rowTo <= 7 && colTo >= 0 && colTo <= 7 && board [rowTo] [colTo] == EMPTY && ((color == RED && rowFrom - rowTo == 1) || (color == BLACK && rowFrom - rowTo == -1) || ((color == RED_KING || color == BLACK_KING) && (rowFrom - rowTo == 1 || rowFrom - rowTo == -1))) ? true:
	false;
    }


    /*
	Purpose: This will determine whether or not a jump is legal for a piece
		 from the current row and column, over another row and column, to
		 a third row and column.

	Purpose of conditional statements:
	There are many parts to this ternary operator, I will explain them below

	1. Check if the landing location falls on the board
	2. Check if the landing location is unoccupied
	3. Check if the piece in the jumping location is not of the same color.
	4. Check if the piece in the jumping location is not empty.
	5. Make sure that the piece is moving in the correct direction
	    This is determined by color and the difference between landingRow and landingColumn
	If all are true, the jump is legal, otherwise the jump is illegal.

	+--------------------+------+-----------------------------------------------------------+
	|        Name        | Type |                          Purpose                          |
	+--------------------+------+-----------------------------------------------------------+
	| color              | int  | This holds the value of the color of the pieces           |
	|                    |      | to search legal moves for.                                |
	| rowFrom            | int  | This will hold the location of the row the piece is       |
	|                    |      | coming from.                                              |
	| colFrom            | int  | This will hold the location of the column the piece is    |
	|                    |      | coming from.                                              |
	| jumpingPieceRow    | int  | This will hold the row of the piece being jumped over.    |
	| jumpingPieceColumn | int  | This will hold the column of the piece being jumped over. |
	| landingRow         | int  | This will hold the row of where the piece will land.      |
	| landingColumn      | int  | This will hold the column of where the piece will land.   |
	+--------------------+------+-----------------------------------------------------------+

    */
    private boolean canJump (int color, int rowFrom, int colFrom, int jumpingPieceRow, int jumpingPieceColumn, int landingRow, int landingColumn)
    {
	return landingRow >= 0 && landingRow <= 7 && landingColumn >= 0 && landingColumn <= 7 && board [landingRow] [landingColumn] == EMPTY && board [jumpingPieceRow] [jumpingPieceColumn] != movingSide && board [jumpingPieceColumn] [jumpingPieceRow] != movingSide + 1 && board [jumpingPieceRow] [jumpingPieceColumn] != EMPTY && (board [rowFrom] [colFrom] == RED && rowFrom - landingRow == 2 || board [rowFrom] [colFrom] == BLACK && rowFrom - landingRow == -2 || (board [rowFrom] [colFrom] == RED_KING || board [rowFrom] [colFrom] == BLACK_KING) && (rowFrom - landingRow == 2 || rowFrom - landingRow == -2)) ? true:
	false;
    }


    /*
	Purpose: The purpose of this method is to display the move results,
		update the board array, and check for multiple jumps.

	 Purpose of any conditional statements:

	 There are 2 if structures in this method.
	 The first if structure checks if there are any multiple jumps. If so,
	 it will ask for the user input. Otherwise it will just switch sides
	 and end the method.
	 The next if structure checks if the location that the user has selected
	 to double jumps is a legal selection or if the user chooses to resign. If
	 either of those conditions are true, the loop will break.

	 Purpose of any loops:

	 There is one while loop that will continue until it breaks (if it matches
	 the condition mentioned above). This loop is here so that the program co-
	 ntinuously asks for userInput until is is valid.

    */
    public void display ()
    {
	if (!gameOver)
	{
	    makeMove (chosenSquare [1], chosenSquare [0], chosenSquare1 [1], chosenSquare1 [0]);
	    drawBoard ();
	    if (multipleJump ())
	    {
		moveIndicator (3);
		chosenSquare = chosenSquare1;
		movablePieces (movingSide, chosenSquare [1], chosenSquare [0]);
		while (true)
		{
		    c.setColor (Color.yellow);
		    c.drawRect (74 + chosenSquare [0] * 60, 182 + chosenSquare [1] * 60, 57, 57);
		    chosenSquare1 = userInput ();
		    if (movableLocations [chosenSquare1 [1]] [chosenSquare1 [0]] == true || gameOver)
			break;
		    c.setColor (chosenSquare [0] % 2 == chosenSquare [1] % 2 ? buttonColor:
		    Color.black);
		    c.drawRect (73 + chosenSquare1 [0] * 60, 181 + chosenSquare1 [1] * 60, 59, 59);
		    JOptionPane.showMessageDialog (null, "You cannot move it here! Please Try Again.");
		}
		display ();
	    }
	    else
	    {
		movingSide = movingSide == RED ? BLACK:
		RED;
	    }
	}
    }


    /*
	Purpose: This method checks if any double jumps are possible and if there are
		 it will return true, otherwise it returns false. It checks by using
		 the canJump method.
	Purpose of if statements:
	The first if statement is to check if a jump was made by comparing the original
	location of the piece to where it moved.

	The other if statements (4) are there to call canJump() for all 4 directions that
	a piece can move. If any return true, it tells us that there is another jumps
	available.

	Local variable dictionary
	+--------------------+---------+-------------------------------------------+
	|        Name        |  Type   |                  Purpose                  |
	+--------------------+---------+-------------------------------------------+
	| doubleJumpPossible | boolean | Will be true if double jumps              |
	|                    |         | are possible and will be false otherwise. |
	+--------------------+---------+-------------------------------------------+
    */
    private boolean multipleJump ()
    {
	boolean doubleJumpPossible = false;
	if (chosenSquare [1] - chosenSquare1 [1] == 2 || chosenSquare [1] - chosenSquare1 [1] == -2)
	{
	    if (canJump (movingSide, chosenSquare1 [1], chosenSquare1 [0], chosenSquare1 [1] - 1, chosenSquare1 [0] - 1, chosenSquare1 [1] - 2, chosenSquare1 [0] - 2))
		doubleJumpPossible = true;
	    if (canJump (movingSide, chosenSquare1 [1], chosenSquare1 [0], chosenSquare1 [1] + 1, chosenSquare1 [0] - 1, chosenSquare1 [1] + 2, chosenSquare1 [0] - 2))
		doubleJumpPossible = true;
	    if (canJump (movingSide, chosenSquare1 [1], chosenSquare1 [0], chosenSquare1 [1] - 1, chosenSquare1 [0] + 1, chosenSquare1 [1] - 2, chosenSquare1 [0] + 2))
		doubleJumpPossible = true;
	    if (canJump (movingSide, chosenSquare1 [1], chosenSquare1 [0], chosenSquare1 [1] + 1, chosenSquare1 [0] + 1, chosenSquare1 [1] + 2, chosenSquare1 [0] + 2))
		doubleJumpPossible = true;
	}
	return doubleJumpPossible;
    }


    /*
	Purpose: This is a helper method that displays circles on the board
		regarding whose turn it is and what the current selection is
		for.
	Purpose of conditional statements:
	The one if statement is there so that the program doesn't go through
	unneccessary code for other modes. The code is skipping over is the
	code for displaying whose turn it is, which does not change as often
	as the modes do.

	Local Variable Dictionary:
	+------+------+---------------------------------------------+
	| Name | Type |                   Purpose                   |
	+------+------+---------------------------------------------+
	| mode | int  | This can be either 1,2,3. Based on the mode |
	|      |      | the program will draw ovals in different l- |
	|      |      | ocations and will preform certain code bas- |
	|      |      | ed on the mode.                             |
	+------+------+---------------------------------------------+
    */
    private void moveIndicator (int mode)
    {
	if (mode != 2)
	{
	    c.setColor (movingSide == RED ? Color.white:
	    Color.blue);
	    c.fillOval (17, 138, 20, 20);
	    c.setColor (movingSide == RED ? Color.blue:
	    Color.white);
	    c.fillOval (17, 683, 20, 20);
	}
	c.setColor (Color.white);
	c.fillRect (660, 580, 40, 150);
	c.setColor (Color.blue);
	c.fillOval (663, 533 + mode * 50, 30, 30);
    }



    /*
	Purpose: The purpose of this method is to make changes to the array
		based on the move.
	Purpose of conditional statements:
	The one conditional statement is there to check if there was a jump.
	If there was a jump, the program will be instructed to erase the
	contents of the location of where the jumped piece was.

	Local Variable Dictionary:
	+---------+------+---------------------------------+
	|  Name   | Type |             Purpose             |
	+---------+------+---------------------------------+
	| rowFrom | int  | This will hold the value of     |
	|         |      | the row the piece came from.    |
	| colFrom | int  | This will hold the value of     |
	|         |      | the column the piece came from. |
	| rowTo   | int  | This will hold the value of     |
	|         |      | the row the piece goes to.      |
	| colTo   | int  | This will hold the value of     |
	|         |      | the column the piece goes to.   |
	+---------+------+---------------------------------+
    */
    private void makeMove (int rowFrom, int colFrom, int rowTo, int colTo)
    {
	board [rowTo] [colTo] = board [rowFrom] [colFrom];
	//Kinging
	board [rowTo] [colTo] = rowTo == 0 && board [rowFrom] [colFrom] == RED ? RED_KING:
	rowTo == 7 && board [rowFrom] [colFrom] == BLACK ? BLACK_KING:
	board [rowFrom] [colFrom];
	// Erasing pieces
	board [rowFrom] [colFrom] = EMPTY;
	// If move is jump making special changes
	if (rowFrom - rowTo == 2 || rowFrom - rowTo == -2)
	    board [(rowTo + rowFrom) / 2] [(colTo + colFrom) / 2] = EMPTY;
    }


    /*
	Purpose: This method will display the end game screen which
		shows the winner's name. It will also add the winner's
		name to the high scores list.


	Purpose of any conditional statements:

	There is one ternary operator in the method. This ternary operator
	determines who the winner was by looking at whose move it was when
	the game ended (Red/Black) and who played as that side. Whoever's move
	it was when the game ended, that person is not the winner.
    */
    public void endGame ()
    {
	c.clear ();
	displayImage ("EndGame.jpg");
	c.setColor (Color.white);
	winner = movingSide == RED ? playingSide == 1 ? playerTwo:
	playerOne:
	playingSide == 2 ? playerTwo:
	playerOne;
	c.drawString (winner, 140, 240);
	addEntry ();
	pauseProgram ();
    }


    /*
	Purpose: This method displays the ending message and then closes the screen
		after the user presses a key.
    */
    public void goodbye ()
    {
	c.clear ();
	displayImage ("Goodbye.jpg");
	pauseProgram ();
	c.close ();
    }


    /*
	Purpose: This method creates the console object.
    */

    public Checkers ()
    {
	c = new Console (38, 128, "Checkers");

    }


    /*
	Purpose: This is the main method which controls
	    the flow of the program.

	Purpose of conditional statements:

	There is are three if statements in this method.
	The largest if structure is to control what method
	to call based on the mainMenuChoice.

	The next if statement  if (c.gameOver) is to break
	the do while loop is is in, if the game is over.

	The last if statement is within the 'else' of the first
	if statement mentioned. It just checks if the mainMenuChoice
	was 3 and if so, it will go to the highScores method.

	Purpose of loops:

	This main method has 2 do while loops.

	The first do while loop will continue until the user choses
	option 4 in the main menu. This is to loop the program until
	the user wishes to exit.

	The other do while loop will continue to loop until the gameOver
	is true.

	Local Variable Dictionary
	+------+----------+----------------------------------------+
	| Name |   Type   |                Purpose                 |
	+------+----------+----------------------------------------+
	| c    | Checkers | This creates an object of the Checkers |
	|      |          | class and gives access to the methods  |
	|      |          | in the Checkers class.                 |
	+------+----------+----------------------------------------+
    */

    public static void main (String[] args)
    {
	Checkers c = new Checkers ();
	c.splashScreen ();
	do
	{
	    c.mainMenu ();
	    if (c.mainMenuChoice == 1)
	    {
		c.gameSetup ();
		c.drawBoard ();
		do
		{
		    c.askData ();
		    c.display ();
		}
		while (!c.gameOver);
		c.endGame ();
	    }
	    else if (c.mainMenuChoice == 2)
		c.instructions ();
	    else
	    {
		if (c.mainMenuChoice == 3)
		    c.highScores ();
	    }
	}
	while (c.mainMenuChoice != 4);
	c.goodbye ();
    }
}


