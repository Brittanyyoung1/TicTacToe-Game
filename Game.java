package edu.fvtc.tictactoegame;

import androidx.annotation.NonNull;

import java.util.Date;

public class Game {

    private int id;
    private String name;

    private String player1;
    private String player2;

    private String winner;

    private Date lastUpdateDate;

    private String nextTurn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int connectionId;
    private String gameState;
    private boolean completed;

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1( String player1) {
        this.player1 = player1;
    }
    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2( String player2) {
        this.player2 = player2;
    }
    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
    public String getNextTurn() {
        return nextTurn;
    }

    public void setNextTurn(String nextTurn) {
        this.nextTurn = nextTurn;
    }

    public Date getLastUpdateDate(){return lastUpdateDate;}
    public void setLastUpdateDate(Date lastUpdateDate){this.lastUpdateDate = lastUpdateDate;}
    public String getGameState(){return gameState;}
    public void setGameState(String gameState){this.gameState = gameState;}
    public boolean getCompleted(){return completed;}
    public void setCompleted(boolean completed){this.completed = completed;}

    @NonNull
    @Override
    public String toString() {
        int id = this.id;
        int connectionId = this.connectionId;
        String name="";
        String player1="";
        String player2="";
        String winner="";
        String nextTurn="";


        return String.format("%d|%s|%s|%s|%s\n", id, name,player1,player2,winner,nextTurn);
    }
    public Game(int id,
                int connectionId,
                String name,
                String player1,
                String player2,
                String winner,
                String nextTurn,
                String lastUpdateDate,
                String gameState,
                String completed) {
        this.id = id;
        this.connectionId = connectionId;
        this.name= name;
        this.player1=player1;
        this.player2=player2;
        this.winner=winner;
        this.nextTurn=nextTurn;
        this.lastUpdateDate = lastUpdateDate;
        this.gameState = gameState;
        this.completed=completed;
    }
}
