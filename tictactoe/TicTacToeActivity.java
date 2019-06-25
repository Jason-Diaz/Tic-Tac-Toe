package edu.ncc.tictactoe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeActivity extends Activity implements ActionBar.OnNavigationListener {
    // constant for the key-value pair used in the Intent
    public final static String NAME1 = "userName1";
    public final static String NAME2 = "userName2";
    public final static String LETTERS = "gameBoard";
    public final static String GAMEBOARD = "Board";
    public final static String CURRENTCHAR = "char";
    // keeps track of the current character
    private String currentChar;
    private String name1 = "";
    private String name2 = "";
    private String[] gameBoard = new String[9];
    private int count;

    // constants for X and O
    public final static String X = "   X   ";
    public final static String O = "   O   ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // create the intent and retrieve the users' names
        Intent intent = getIntent();

        name1 = intent.getStringExtra(NAME1);
        name2 = intent.getStringExtra(NAME2);
        currentChar = intent.getStringExtra(CURRENTCHAR);
        gameBoard = intent.getStringArrayExtra(GAMEBOARD);

        // set the text color to blue & red
        ((TextView)findViewById(R.id.player1_info)).setTextColor(Color.BLUE);
        ((TextView)findViewById(R.id.player2_info)).setTextColor(Color.RED);


        // display the players' names
        //((TextView)findViewById(R.id.player1_info)).setText("Player 1:" + name1);
        ((TextView)findViewById(R.id.player1_info)).setText(getString(R.string.player1_label) + name1);
        ((TextView)findViewById(R.id.player2_info)).setText(getString(R.string.player2_label) + name2);

        // set current character to X as player 1 is X
        currentChar = O;
        count = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tic_tac_toe, menu);

        // creat a spinner adapter object
        SpinnerAdapter colorSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_dropdown_item);

        // get a handle on the action bar
        ActionBar actionBar = getActionBar();

        // set th navigation mode
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // set the callback
        actionBar.setListNavigationCallbacks(colorSpinnerAdapter, this);

        // set the title of the action bar
        actionBar.setTitle("Tic Tac Toe");

        return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else
        {
            if (id == R.id.action_reset) {
                Toast toast = Toast.makeText(this, "RESET CLICKED",
                        Toast.LENGTH_SHORT);

                clearBoard();
                toast.show();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearBoard(){
        int idButton = R.id.button;
        for (int i=0; i<9; i++)
        {
            ((Button)findViewById(idButton)).setText(R.string.blank);
            ((Button)findViewById(idButton)).setClickable(true);
            idButton++;
            gameBoard[i] = null;
        }

        count = 0;
        currentChar = O;
    }

    /**
     * will be called before the activity is destroyed
     * save the text on each of the buttons into the
     * Bundle
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        String[] strs = new String[9];

        // save the string on each button into an array
        int id = R.id.button;
        for (int i=0; i<strs.length; i++)
        {
            strs[i] = ((Button)findViewById(id)).getText().toString();
            id++;
        }

        // put the array into the bundle
        outState.putStringArray(GAMEBOARD, gameBoard);
        outState.putStringArray(LETTERS, strs);
        outState.putString(CURRENTCHAR, currentChar);

        // call the super class onSaveInstanceState
        super.onSaveInstanceState(outState);
    }

    /**
     * retrieve the Strings, set the appropriate color,
     * set the appropriate text, disable if button has
     * been clicked (ie. the text is X or O)
     * @param inState
     */
    @Override
    public void onRestoreInstanceState(Bundle inState)
    {
        // call onRestoreInstanceState in the super class
        super.onRestoreInstanceState(inState);

        // retrieve the strings from the Bundle
        String[] strs = inState.getStringArray(LETTERS);
        currentChar = inState.getString(CURRENTCHAR);

        int id = R.id.button;
        for (int i=0; i<strs.length; i++)
        {
            // if it is an X
            if (strs[i].equals(X))
            {
                // set the color to BLUE and disable
                ((Button)findViewById(id)).setTextColor(Color.BLUE);
                ((Button)findViewById(id)).setClickable(false);
            }
            else if (strs[i].equals(O)) {
                // display the string
                ((Button)findViewById(id)).setTextColor(Color.RED);
                ((Button)findViewById(id)).setClickable(false);
            }
            ((Button) findViewById(id)).setText(strs[i]);
            // move to the next button
            id++;
        }
    }

    /**
     * onClick method - will be called when the user clicks any button
     * on the tic tac toe board - should show X or O and disable the button
     * @param v
     */
    public void onClick(View v) {
        // display the current character in the right color
        // on the appropriate button
        if (currentChar.equals(O)) {
            ((Button) v).setTextColor(Color.BLUE);
            currentChar = X;
        } else {
            ((Button) v).setTextColor(Color.RED);
            currentChar = O;
        }
        // use the view sent to this method, cast it to a
        // button, then set its text to the current character
        ((Button) v).setText(currentChar);

        // disable the button so it can't be clicked
        ((Button) v).setClickable(false);

        int id = R.id.button;
        int pos = -1;
        boolean winner = false;



        for (int i = 0; i < gameBoard.length; i++) {
            if (v.getId() == id) {
                    gameBoard[i] = ((Button) v).getText().toString();
                }
               else if(gameBoard[i] == null){
                    gameBoard[i] = String.valueOf(i+23);
               }
                id++;
            }
        count = 0;
        for(int i=0;i<gameBoard.length;i++) {
            if (gameBoard[i].equals(X) || (gameBoard[i].equals(O))) {
                count++;
            }
        }


        if (count >= 5) {

            for (int i = 0; i < gameBoard.length; i = i+3) {

                if ((gameBoard[i].equals(gameBoard[i + 1])) && (gameBoard[i + 1].equals(gameBoard[i + 2]))) {
                    winner = true;
                    pos = i;
                    i = gameBoard.length;
                }
            }
            for (int i = 0; i < 3; i++) {

                if ((gameBoard[i].equals(gameBoard[i + 3])) && (gameBoard[i + 3].equals(gameBoard[i + 6]))) {
                    winner = true;
                    pos = i;
                    i = gameBoard.length;
                }
            }

            if ((gameBoard[0].equals(gameBoard[4])) && (gameBoard[4].equals(gameBoard[8]))) {
                winner = true;
                pos = 0;
            }
            if ((gameBoard[2].equals(gameBoard[4])) && (gameBoard[4].equals(gameBoard[6]))) {
                winner = true;
                pos = 2;
            }

            if (winner == true) {
                Toast toast;
                if (gameBoard[pos].equals(X)) {
                    toast = Toast.makeText(this, name1 + " won!",
                            Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(this, name2 + " won!",
                            Toast.LENGTH_SHORT);
                }
                toast.show();
                clearBoard();
            }
        }
        if(count == 9){
            Toast toast = Toast.makeText(this,"It's a Draw",
                    Toast.LENGTH_SHORT);
            toast.show();
            clearBoard();
        }
    }

    public boolean onNavigationItemSelected (int itemPos, long itemId)
    {
        return true;
    }
}
