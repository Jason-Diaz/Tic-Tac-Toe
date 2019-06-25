package edu.ncc.tictactoe;
/**
 * Jason Diaz
 * Assignment #1
 */
import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    // constant for the key-value pair used in the Intent
    public final static String NAME1 = "userName1";
    public final static String NAME2 = "userName2";
    public final static String GAMEBOARD = "Board";
    public final static String CURRENTCHAR = "char";
    private String[] gameBoard = new String[9];
    private String currentChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * this method will be called when the start game button is clicked
     * it retrieves the two names from the EditText boxes, creates an
     * Intent, puts the names into the Intent and starts the 2nd activity
     * @param v the button that has been clicked
     */
    public void onClick(View v)
    {
        // retrieves the names typed into the edit text boxes
        String name1 = ((EditText)findViewById(R.id.player1_name)).getText().toString();
        String name2 = ((EditText)findViewById(R.id.player2_name)).getText().toString();

        // specifies the second activity in the intent
        Intent intent = new Intent(this, TicTacToeActivity.class);

        // adds the two names to the intent
        intent.putExtra(NAME1, name1);
        intent.putExtra(NAME2, name2);
        intent.putExtra(GAMEBOARD, gameBoard);
        intent.putExtra(CURRENTCHAR, currentChar);
        // starts the second activity
        startActivity(intent);
    }
}
