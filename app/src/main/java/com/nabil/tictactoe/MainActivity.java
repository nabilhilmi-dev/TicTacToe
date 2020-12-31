package com.nabil.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView Player1Score, Player2Score, playerstatus;
    private Button [] buttons = new Button[9];
    private Button ResetGame;

    private int Player1ScoreCount, Player2ScoreCount, rountCount;

    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] WinningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //rows
            {0,3,6}, {1,4,7}, {2,5,8},//columns
            {0,4,8}, {2,4,6} //cross

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Player1Score =  (TextView) findViewById(R.id.Player1Score);
        Player2Score = (TextView) findViewById(R.id.Player2Score);
        playerstatus = (TextView) findViewById(R.id.Playerstatus);

        ResetGame = (Button) findViewById(R.id.ResetGame);

        for (int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        Player1ScoreCount = 0;
        Player2ScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if (activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FFFFFF"));
            gameState[gameStatePointer] = 0;
        }else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;

        }

        rountCount++;

        if (checkWinner()){
            if (activePlayer){
                Player1ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player 1 Won", Toast.LENGTH_SHORT).show();

            }else {
                Player2ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player 2  Won", Toast.LENGTH_SHORT).show();
                playAgain();

            }

        }else if (rountCount == 9){
            playAgain();
            Toast.makeText(this, "No Winner", Toast.LENGTH_SHORT).show();
        }else {
            activePlayer = !activePlayer;
        }

        if (Player1ScoreCount > Player2ScoreCount){
            playerstatus.setText("Player 1 Won");
        }else if (Player2ScoreCount > Player1ScoreCount){
            playerstatus.setText("Player 2 Won");
        }else {
            playerstatus.setText("");
        }

        ResetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                Player1ScoreCount = 0;
                Player2ScoreCount = 0;
                playerstatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for (int [] WinningPositions : WinningPositions){
            if (gameState[WinningPositions[0]] == gameState[WinningPositions[1]]
                    && gameState[WinningPositions[1]] == gameState[WinningPositions[2]] && gameState[WinningPositions[0]] !=2){

                winnerResult = true;



            }
        }

        return winnerResult;
    }

    public void updatePlayerScore(){
        Player1Score.setText(Integer.toString(Player1ScoreCount));
        Player2Score.setText(Integer.toString(Player2ScoreCount));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}