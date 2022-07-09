package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore,playerTwoScore,playerStatus;
    private Button[] buttons=new Button[9];
    private ImageButton resetGame;

    private int PlayerOneScoreCount,PlayerTwoScoreCount,roundCount;
    boolean activePlayer;


//p1==>0
//p2==>1
//empty==>2

    int [] gameState={2,2,2,2,2,2,2,2,2};

    int[][] winnigPosition={
            {0,1,2},{3,4,5},{6,7,8},//rows
            {0,3,6},{1,4,7},{2,5,8},//columns
            {0,4,8},{2,4,6}//cross;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the variables

        playerOneScore=(TextView)findViewById(R.id.playerOneScore);
        playerTwoScore=(TextView)findViewById(R.id.playerTwoScore);
        playerStatus=(TextView)findViewById(R.id.playerStatus);
        resetGame=(ImageButton)findViewById(R.id.resetGame);

        for(int i=0;i<buttons.length;i++){
            String buttonID="btn"+i;
            int resourceId=getResources().getIdentifier(buttonID,"id",getPackageName());

            //this will turn string in something like that..

            buttons[i]=(Button)findViewById(resourceId);
            buttons[i].setOnClickListener(this);


        }
        //some more variables for initialization
        roundCount=0;
        PlayerOneScoreCount=0;
        PlayerTwoScoreCount=0;
        activePlayer=true;

    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID=v.getResources().getResourceEntryName(v.getId());//as an example for btn   2
        int gameStatePointer=Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));//0,1,2,3,4

        if (activePlayer){//p1
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#53BAFE"));
            gameState[gameStatePointer]=0;
        }else{
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#F16363"));
            gameState[gameStatePointer]=1;
        }
        roundCount++;

        if (checkWinner()){
            if (activePlayer){
                PlayerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                PlayerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two won!", Toast.LENGTH_SHORT).show();
                playAgain();

            }
        }else if (roundCount==9){
            //means there is no winner
            playAgain();
            Toast.makeText(this, "No Winner", Toast.LENGTH_SHORT).show();

        }else{
            //we shift player
            activePlayer=!activePlayer;//p1=!p2
        }
        if (PlayerOneScoreCount>PlayerTwoScoreCount){
            playerStatus.setText("Player one is winning!");
        }else if (PlayerTwoScoreCount>PlayerOneScoreCount){
            playerStatus.setText("Player Two is winning!");
        }else{
            playerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                PlayerOneScoreCount=0;
                PlayerTwoScoreCount=0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    //check for winner
    public boolean checkWinner(){

        boolean winnerResult=false;

        for (int[]winnigPosition:winnigPosition){//X===={0,4,8}===={0,4,8)
            if (gameState[winnigPosition[0]]==gameState[winnigPosition[1]]&&
                    gameState[winnigPosition[1]]==gameState[winnigPosition[2]]&&
                    gameState[winnigPosition[0]]!=2){
                winnerResult=true;

            }

        }
        return winnerResult;//PLayer one is winner.two is winner
    }
    //function for updating player score

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(PlayerOneScoreCount));
        playerTwoScore.setText(Integer.toString(PlayerTwoScoreCount));
    }

    public void playAgain(){
        roundCount=0;
        activePlayer=true;

        for (int i=0;i<buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}