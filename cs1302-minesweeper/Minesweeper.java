
package cs1302.p1;

import java.io.File;
import java.util.Scanner;
import java.util.Random;

/**
 * This class represents a Minesweeper game.
 *
 * @author HARLI BOTT <hjb15032@uga.edu>
 */
public class Minesweeper {
   private int roundsCompleted;
   private String [][] grid;
   private String user;
   private int row;
   private int col;
   private int mines;  
   private int score;
   private boolean [][] mineField;
   private int markedMines;
   private String [][] revealGrid;
   public String fileName = "";   
    /**
     * Constructs an object instance of the {@link Minesweeper} class using the
     * information provided in <code>seedFile</code>. Documentation about the 
     * format of seed files can be found in the project's <code>README.md</code>
  game.fileName = args[0];     * file.
     *
     * @param seedFile the seed file used to construct the game
     * @see            <a href="https://github.com/mepcotterell-cs1302/cs1302-minesweeper-alpha/blob/master/README.md#seed-files">README.md#seed-files</a>
     */
    public Minesweeper(File seedFile) {

	try{
	    Scanner fileReader = new Scanner(seedFile);
	    int r = 0;
	    int c = 0;
	    this.fileName = seedFile.getName();
	    if (fileReader.hasNextInt()){
		r = fileReader.nextInt();
	    }
	    if (fileReader.hasNextInt()){
		c = fileReader.nextInt();
	    }
	    if (r <= 10 && r > 0 && c <= 10 && c > 0){
		this.row = r;
		this.col = c;
		
	    } // checking to make sure it is a valid row/col before setting it to "this"
	    if (fileReader.hasNextInt()){
		this.mines = fileReader.nextInt();
		if(this.mines > this.row * this.col){
		    error(5);
		} // checking to make sure num of mines !> the board total
	    }
	    
	    int cor1 = 0;
	    int cor2 = 0;

	    this.grid = new String[this.row][this.col];
	    this.revealGrid = new String[this.row][this.col];
	    this.mineField = new boolean[this.row][this.col];

	    for (int i = 0; i < mines; i ++){
		if (fileReader.hasNextInt()){ // checking for next int for row
		    cor1 = fileReader.nextInt();
		    if(fileReader.hasNextInt()){ // checking for next int for col
			cor2 = fileReader.nextInt();
			if(cor1 >= 0 && cor1 < this.row){ // checking the bounds for row
			    if(cor2 >= 0 && cor2 < this.col){ // checking the bounds for col
				this.mineField[cor1][cor2] = true;
			    }else{
				error(3);
			    }
			}else{
			    error(3);
			}
		    }else{
			error(3);
		    }
		}else{
		    error(3);
		}
	    }
	}catch(Exception a){
	    error(3);
	}
    } // Minesweeper

    /**                                                                                                                       
     * Constructs an object instance of the {@link Minesweeper} class using the                                               
     * <code>rows</code> and <code>cols</code> values as the game grid's number                                               
     * of rows and columns respectively. Additionally, One quarter (rounded up)                                               
     * of the squares in the grid will will be assigned mines, randomly.                                                      
     *                                                                                                                        
     * @param rows the number of rows in the game grid                                                                        
     * @param cols the number of cols in the game grid                                                                        
     */
    public Minesweeper(int rows, int cols) {
	this.grid = new String [rows][cols];
	this.mineField = new boolean [rows][cols];
	this.revealGrid = new String [rows][cols]; // will be used in the implementation of "no fog"
	this.row = rows;
	this.col = cols;

    } // Minesweeper     

    public void board(){
	for (int i = 0; i < grid.length; i ++){
	    System.out.print(i + " ");
	    System.out.print("|");
	    for (int j = 0; j < grid[i].length; j ++){
		System.out.print(grid[i][j]);
		System.out.print("|");
	    }
	    System.out.println();
	}
	System.out.print("    ");
	for (int i = 0; i < grid.length; i ++){
	    System.out.print(i + "   ");
	}
    } // Board method

    public void initBoard(){
	for(int i = 0; i < grid.length; i ++){
	    for (int j = 0; j < grid[i].length; j++){
		this.grid[i][j] = "   ";
	    }
	} // initalizes Grid

	for(int i = 0; i < revealGrid.length; i ++){
	    for(int j = 0; j < revealGrid[i].length; j ++){
		this.revealGrid[i][j] = "   ";
	    } // initializes reveal grid for nofog method
	}
    } //initializes board 
    public void mineBoard(){

	Random r = new Random();
	this.mines =(int) Math.ceil(this.row*this.col*0.25);

	for (int i = 0; i < mines; i ++){
	   this.mineField[r.nextInt(this.row)][r.nextInt(this.col)] = true;
	}
    }// creates the mine field

    public void startMessage(){
	System.out.println();
	System.out.println("        _");
	System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __");
	System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
	System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |");
	System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
	System.out.println("\t\t\t     ALPHA EDITION |_| v2017.f");
	System.out.println();
    }// Start Message of the Game

    public void endMessage(){
	System.out.println("  Oh no... You revealed a mine!");
	System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __");
	System.out.println(" / _` |/ _` | '_ ` _ \\ / _ \\  / _ \\ \\ / / _ \\ '__|");
	System.out.println("| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |   ");
	System.out.println(" \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|");
	System.out.println(" |___/");
	System.out.println();
	System.exit(0);
    } // End message of the Game

    public void winMessage(){
	this.score = (this.row * this.col) - this.mines - this.roundsCompleted;

	System.out.println("░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ \"So Doge\"");
	System.out.println("░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░");
	System.out.println("░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ \"Such Score\"");
	System.out.println("░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
	System.out.println("░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ \"Much Minesweeping\"");
	System.out.println("░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
	System.out.println("░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ \"Wow\"");
	System.out.println("░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
	System.out.println("░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
	System.out.println("░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
	System.out.println("▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
	System.out.println("▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
	System.out.println("▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
	System.out.println("░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
	System.out.println("░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
	System.out.println("░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
	System.out.println("░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!");
	System.out.println("░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!");
	System.out.println("░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE: " + score);
	System.out.println();
	System.exit(0);
    }// win Message of the game

    public void helpMenu(){
	System.out.println("Commands Availible...");
	System.out.println("- Reveal: r/reveal row col");
	System.out.println("-   Mark: m/mark   row col");
	System.out.println("-  Guess: g/guess  row col");
	System.out.println("-   Help: h/help");
	System.out.println("-   Quit: q/quit");
	System.out.println();
    }// Help Menu for the Game

    public void quit(){
	System.out.println("ლ(ಠ_ಠლ)");
	System.out.println("Y U NO PLAY MORE?");
	System.out.println("Bye!");
	System.out.println();
	System.exit(0);
    }// quit Menu for the Game

    public void reveal(int x, int y){
	this.roundsCompleted ++;
	if(this.grid[x][y].equals(" F ")){
	    this.markedMines--;
	}
	if (this.mineField[x][y]){
	    endMessage();
	}else{
	    String s = getNumAdjMines(x,y); 
	    this.grid[x][y] = s;
	}    
    }

    public void mark(int x, int y){
	this.roundsCompleted ++;
	this.markedMines ++;

	if(this.grid[x][y] == " F "){
	    this.markedMines--;
	}
	
	this.grid[x][y] = " F ";
    }

    public void guess(int x, int y){
	this.roundsCompleted ++;
	if(this.grid[x][y].equals(" F ")){
	    this.markedMines --;
	}
	this.grid[x][y] = " ? ";
    }
    public void noFog(){
	this.roundsCompleted ++;
	System.out.println("Rounds Completed: " + roundsCompleted);
	System.out.println();

	for(int i = 0; i < mineField.length; i++){
	    for(int j = 0; j < mineField[i].length; j ++){
		if(this.mineField[i][j]){
		    if(this.grid[i][j].equals(" F ")){
			this.revealGrid[i][j] = "<F>";
		    }else if(this.grid[i][j].equals(" ? ")){
			this.revealGrid[i][j] = "<?>";
		    }else{
		    this.revealGrid[i][j] = "< >";
		    }
	        }
	    }
	}
	for (int i = 0; i < revealGrid.length; i ++){
	    System.out.print(i + " ");
	    System.out.print("|");
	    for (int j = 0; j < revealGrid[i].length; j ++){
		System.out.print(revealGrid[i][j]);
		System.out.print("|");
	    }
	    System.out.println();
	}
	System.out.print("    ");
	for (int i = 0; i < revealGrid.length; i ++){
	    System.out.print(i + "   ");
	}
    }
    public String getNumAdjMines(int row, int col){
	int n = 0;
	String s = " ";
	int i = row;
	int j = col;
	//checking to make sure row/col is on the inside of grid
	if((row > 0 && row < this.row-1) && (col > 0 && col < this.col-1)){
	    if(mineField[i+0][j-1]){
	       n++;
	    } //directly left
	    if(mineField[i+1][j-1]){
		n++;
	    } //bottom left corner
	    if(mineField[i+1][j+0]){
		n++;
	    }//directly below
	    if(mineField[i+1][j+1]){
		n++;
	    }//bottom right corner
	    if(mineField[i+0][j+1]){
		n++;
	    }//directly right		
	    if(mineField[i-1][j+1]){
		n++;
	    }//top right corner
	    if(mineField[i-1][j+0]){
		n++;
	    }//directly above
	    if(mineField[i-1][j-1]){
		n++;
	    }//top left corner
	}else if(row == 0 && col == 0){ //top left corner
	    if(mineField[i+0][j+1]){
		n++;
	    }//directly right
	    if(mineField[i+1][j+0]){
		n++;
	    }//directly below
	    if(mineField[i+1][j+1]){
		n++;
	    }//corner	 
	}else if(row == this.row-1 && col == 0){// bottom left corner
	    if(mineField[i+0][j+1]){
		n++;
	    }//directly right
	    if(mineField[i-1][j+0]){
		n++;
	    }//directly above
	    if(mineField[i-1][j+1]){
		n++;
	    }//corner
	}else if(row == 0 && col == this.col-1){// top right corner
	    if(mineField[i+1][j+0]){
		n++;
	    }//directly below
	    if(mineField[i+0][j-1]){
		n++;
	    }//directly left
	    if(mineField[i+1][j-1]){
		n++;
	    }//corner
	}else if(row == this.row-1 && col == this.col-1){// bottom right corner
	    if(mineField[i+0][j-1]){
		n++;
	    }// directly left
	    if(mineField[i-1][j+0]){
		n++;
	    }//directly above
	    if(mineField[i-1][j-1]){
		n++;
	    }//corner
	}else if((row > 0 && row < this.row-1) && (col == 0)){//rows on the outter left side of grid
	    if(mineField[i+1][j+0]){
		n++;
	    }//directly below
	    if(mineField[i+1][j+1]){
		n++;
	    }//bottom corner
	    if(mineField[i+0][j+1]){
		n++;
	    }//directly right
	    if(mineField[i-1][j+1]){
		n++;
	    }//top corner
	    if(mineField[i-1][j+0]){
		n++;
	    }//directly above
	}else if((col > 0 && col < this.col-1) && (row == this.row-1)){//columns on the outer bottom of grid
	    if(mineField[i+0][j+1]){
		n++;
	    }//directly right
	    if(mineField[i-1][j+1]){
		n++;
	    }//top right corner
	    if(mineField[i-1][j+0]){
		n++;
	    }//directly above
	    if(mineField[i-1][j-1]){
		n++;
	    }//top left corner
	    if(mineField[i+0][j-1]){
		n++;
	    }//directly left
	}else if((row > 0 && row < this.row-1) && (col == this.col-1)){//rows on the outter right of the grid
	    if(mineField[i-1][j+0]){
		n++;
	    }//directly above
	    if(mineField[i-1][j-1]){
		n++;
	    }//top corner
	    if(mineField[i+0][j-1]){
		n++;
	    }//directly left
	    if(mineField[i+1][j-1]){
		n++;
	    }//bottom corner
	    if(mineField[i+1][j+0]){
		n++;
	    }//directly below
	}else if((col > 0 && col < this.col-1) && (row == 0)){//cols on the top of the grid
	    if(mineField[i+0][j-1]){
		n++;
	    }//directly left
	    if(mineField[i+1][j-1]){
		n++;
	    }//left corner
	    if(mineField[i+1][j+0]){
		n++;
	    }//directly below
	    if(mineField[i+1][j+1]){
		n++;
	    }//right corner
	    if(mineField[i+0][j+1]){
		n++;
	    }//directly right
	}
			 
	s += Integer.toString(n);
	s+= " ";

	return s;
    }
    public void error(int x){
	if (x == 1){
	    System.out.println("ಠ_ಠ says, \"Command not recognized\"");
	}else if (x == 2){
	    System.out.println();
	    System.out.println("ಠ_ಠ says, \"Cannot create a mine field with that many rows and/or columns!\"");
	    System.out.println();
	    System.exit(0);
	}else if (x==3){
	    System.out.println();
	    System.out.println("ಠ_ಠ says, \"Cannot create a game with " + this.fileName + ", because it is not formatted correctly.\"");
	    System.out.println();
	    System.exit(0);
	}else if (x==4){
	    System.out.println("ಠ_ಠ says, \"The number of rows/cols is out of bounds");
	}else if (x == 0){
	    System.out.println();
	    System.out.println("ಠ_ಠ says, \"Error! Your input can not be empty. I will now exit...\"");
	    System.out.println();
	    System.exit(0);
	}else{
	    System.out.println("ಠ_ಠ Error!");
	}
	System.out.println();
    }// error Method created to handle error messages
   
    public void isInBounds(String user){
	System.out.println();
	if (user.equals("h") || user.equals("help")){ // error checking for help menu
	    helpMenu();
	}else if (user.equals("q") || user.equals("quit")){ // error checking for quit menu
	    quit();
	}else if (user.charAt(0) == 'm' && (user.length() == 6 || user.length() == 3)){ // error checking for mark
	    if (user.length() == 6){ 
		try{
		    int row = Integer.parseInt(user.substring(4,5));
		    int col = Integer.parseInt(user.substring(5,6));
		    mark(row,col);
		}catch(Exception a){
		    error(4);
		}
	    }else if (user.length() == 3){
		try{
		    int row = Integer.parseInt(user.substring(1,2));
		    int col = Integer.parseInt(user.substring(2,3));
		    mark(row,col);
		}catch(Exception a){
		    error(4);
		}
	    }
	}else if (user.charAt(0) == 'r' && (user.length() == 8 || user.length() == 3)){
	    if(user.length() == 8){
		try{
		    int row = Integer.parseInt(user.substring(6,7));
		    int col = Integer.parseInt(user.substring(7,8));
		    reveal(row,col);
		}catch(Exception a){
		    error(4);
		}
	    }else if (user.length() == 3){
		try{
		    int row = Integer.parseInt(user.substring(1,2));
		    int col = Integer.parseInt(user.substring(2,3));
		    reveal(row,col);
		}catch(Exception a){
		    error(4);
		}
	    }
	}else if (user.charAt(0) == 'g' && (user.length() == 7 || user.length() == 3)){ // error checking for guess
	    if (user.length() == 7){
		try{
		    int row = Integer.parseInt(user.substring(5,6));
		    int col = Integer.parseInt(user.substring(6,7));
		    guess(row,col);
		}catch (Exception a){
		    error(4);
		}
	    }else if (user.length() == 3){
		try{
		    int row = Integer.parseInt(user.substring(1,2));
		    int col = Integer.parseInt(user.substring(2,3));
		    guess(row,col);
		}catch(Exception a){
		    error(4);
		}
	    }
	}else if(user.equals("nofog")){ // error checking for nofog command
	    noFog();
	
	}else{ // else for if command not recognized
	    error(1);
	}	
    }
	public void run(){
	Scanner input = new Scanner(System.in);
	boolean game;

	this.roundsCompleted = 0;
	startMessage(); // calls the start message

	if(fileName.equals("")){
	    mineBoard(); // creates the mine board at the start of the game if not a seed file
	}
	game = true;
	initBoard(); // initalizes board with  " " at the start of the game

	while (game){ // game loop until game is won or lost
	System.out.println("Rounds Completed: " + roundsCompleted);
	System.out.println();

	board(); // calls the game board to print out

	System.out.println();
	System.out.println();
	System.out.print("minesweeper-alpha$ ");
	try{ // checks to make sure the user's input is not empty
		this.user=input.nextLine();
		this.user = user.trim().toLowerCase();
		this.user = user.replaceAll(" ", "");

		if(user.equals("")){
		    error(0);
		}
	}catch(Exception e){
	    error(0);
	}
	 
	isInBounds(user); // checks to make sure the user's input is valid

	if(user.equals("nofog")){ // prints out a different board for nofog, then resumes with hidden mines
	    System.out.println();
	    System.out.println();
	    System.out.print("minesweeper-alpha$ ");
	    try{
		this.user = input.nextLine();
		this.user = user.trim().toLowerCase();
		this.user = user.replaceAll(" ", "");

		if(user.equals("")){
		    error(0);
		}
	    }catch(Exception e){
		error(0);
	    }
	    isInBounds(user);

	}// end nofog

	if(mines == markedMines){
	    for(int i = 0; i < grid.length; i ++){
		for(int j = 0; j < grid.length; j ++){
		    if(this.grid[i][j].equals(" F ") && this.mineField[i][j]){
		    winMessage();
		    game = false;
		    }//end if mines marked[][] = minefield[][]
		}//end j for loop
	    }//end i for loop
	  
	}// if when mines marked = number of mines in game

	} //end game while loop

    } // run


    /**
     * The entry point into the program. This main method does implement some
     * logic for handling command line arguments. If two integers are provided
     * as arguments, then a Minesweeper game is created and started with a 
     * grid size corresponding to the integers provided and with 10% (rounded
     * up) of the squares containing mines, placed randomly. If a single word 
     * string is provided as an argument then it is treated as a seed file and 
     * a Minesweeper game is created and started using the information contained
     * in the seed file. If none of the above applies, then a usage statement
     * is displayed and the program exits gracefully. 
     *
     * @param args the shell arguments provided to the program
     */
    public static void main(String[] args) {

	/*
	  The following switch statement has been designed in such a way that if
	  errors occur within the first two cases, the default case still gets
	  executed. This was accomplished by special placement of the break
	  statements.
	*/

	Minesweeper game = null;

	switch (args.length) {

        // random game
	case 2: 

	    int rows, cols;

	    // try to parse the arguments and create a game
	    try {
		rows = Integer.parseInt(args[0]);
		cols = Integer.parseInt(args[1]);
		game = new Minesweeper(rows, cols);
		if (rows > 10 || cols > 10){
		    game.error(2);
		    System.out.println();
		}
		break;
	    } catch (NumberFormatException nfe) { 
		System.out.println();
		game.error(2);
	
	    }

	// seed file game
	case 1: 

	    String filename = args[0];
	    File file = new File(filename);
	   
	    if (file.isFile()) {
		game = new Minesweeper(file);
		break;
	    } // if
    
        // display usage statement
	default:

	    System.out.println("Usage: java Minesweeper [FILE]");
	    System.out.println("Usage: java Minesweeper [ROWS] [COLS]");
	    System.exit(0);
	} // switch
	// if all is good, then run the game
	game.run();

    } // main


} // Minesweeper
