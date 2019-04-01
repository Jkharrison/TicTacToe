package com.example.tictactow;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3]; // 2D Array for each of the buttons within our game.
    private boolean playerOneTurn = true;
    private int roundCount = 0; // Initially 0
    private int playerOnePoints = 0;
    private int playerTwoPoints = 0;
    private TextView textViewPlayerOne;
    private TextView textViewPlayerTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerOne = findViewById(R.id.text_view_p1);
        textViewPlayerTwo = findViewById(R.id.text_view_p2);
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                String buttonId = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resID); // Way to assign buttons to array
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")) {
            return;
        }

        if(playerOneTurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if(checkForWin()) {
            if(playerOneTurn) {
                playerOneWins();
            } else {
                playerTwoWins();
            }
        } else if(roundCount == 9) {
            draw();
        } else {
            playerOneTurn =!playerOneTurn;
        }
    }

    private boolean checkForWin() {
        // Check Rows/Columns/Diagonals
        String[][] field = new String[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        //TODO: Start checking
        for(int i = 0; i < 3; i++) {
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                // Case for the 3 Columns
                return true;
            }
        }
        for(int i = 0; i < 3; i++) {
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                // Case for the 3 Rows
                return true;
            }
        }
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            // Case for the Negative Diagonal
            return true;
        }
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            // Case for the Positive Diagonal
            return true;
        }
        return false;
    }
    private void playerOneWins() {
        playerOnePoints++;
        Toast.makeText(this, "Player One wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void playerTwoWins() {
        playerTwoPoints++;
        Toast.makeText(this, "Player Two wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText() {
        textViewPlayerOne.setText("Player 1: " + playerOnePoints);
        textViewPlayerTwo.setText("Player 2: " + playerTwoPoints);
    }
    private void resetBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        playerOneTurn = true;
    }
    private void resetGame() {
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerOnePoints", playerOnePoints);
        outState.putInt("playerTwoPoints", playerTwoPoints);
        outState.putBoolean("playerOneTurn", playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }
}
