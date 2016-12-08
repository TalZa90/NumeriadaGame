package Logic;

import Shared.GameInfo;

import java.sql.Time;

/**
 * Created by talza on 20/11/2016.
 */
public abstract class Game {

    private Player [] m_Players;
    Board m_Board;
    GameInfo m_GameInfo;
    protected  Player m_CurrentPlayer;
    private int m_NumOfMoves;
    private Time m_Timer;
    private eBoardStructure m_BoardStructure;
    private eGameType m_GameType;

    public Board getBoard() {
        return m_Board;
    }

    public enum eGameType {
        Basic, Advanced
    }

    public enum eBoardStructure {
        Explicit, Random
    }


    public Game(GameInfo i_GameInfo){
        m_GameInfo = i_GameInfo;
        m_Board = new Board(m_GameInfo.GetBoardSize());
        m_Players = new Player[m_GameInfo.getNumOfPlayers()];
        for(int i = 0; i < m_Players.length; i++ ){
            m_Players[i] = new Player();
            if(i % 2 == 0){
                m_Players[i].setPlayerType(Player.ePlayerType.ROW_PLAYER);
            }
            else{
                m_Players[i].setPlayerType(Player.ePlayerType.COLUMN_PLAYER);
            }
        }
        m_CurrentPlayer = m_Players[0];
        m_NumOfMoves = 0;

        if(m_GameInfo.getGameType().equals(eGameType.Basic.toString())) {
            m_GameType = eGameType.Basic;
        }
        else{
            m_GameType = eGameType.Advanced;
        }

        if(m_GameInfo.getBoardStructure().equals(eBoardStructure.Explicit.toString())) {
            m_BoardStructure = eBoardStructure.Explicit;
        }
        else{
            m_BoardStructure = eBoardStructure.Random;
        }

        //board exists, values need to be initiated (if explicit from m_board in gameinfo, if random, then randomly)
        initBoard(m_BoardStructure);
    }

    public void MakeMove(int i_Move) {
        playTurn(m_CurrentPlayer, i_Move);
        m_NumOfMoves++;
        m_CurrentPlayer = m_Players[m_NumOfMoves % m_Players.length];

        checkIfGameDone(m_Board.getMark());
    }

    private boolean checkIfGameDone(Marker i_Mark) {
        int markRow = i_Mark.GetRow();
        int markCol = i_Mark.GetColumn();
        boolean gameDone = true;

        for(int i = 0; i < m_Board.getBoardSize(); i++){
            //check if row empty of numbers
            if(m_Board.getSquareInPos(markRow,i).toString() != m_Board.getMark().GetSquareSymbol() &&
                    m_Board.getSquareInPos(markRow,i).toString() != ""){
                gameDone = false;
            }
            //check if col empty of numbers
            if(m_Board.getSquareInPos(i,markCol).toString() != m_Board.getMark().GetSquareSymbol() &&
                    m_Board.getSquareInPos(i, markCol).toString() != ""){
                gameDone = false;
            }
        }

        return gameDone;
    }


    public abstract void GetBoardToPrint();


    public void GetGameStatus() {
        GetBoardToPrint();
        m_GameInfo.setCurrPlayer(m_CurrentPlayer.GetEPlayerTypeAsString());
    }

    //TODO: CHANGE: PLAYER NEEDS TO HOLD SCORE AND PLAYRT TYPE(COL / ROW)
    public void GetGameStatistics() {

        m_GameInfo.setNumOfMoves(m_NumOfMoves);
        m_GameInfo.setElapsedTime(m_Timer);

        for (Player p: m_Players){
            m_GameInfo.setRowPlayerScore(p.getPlayerScore());
        }
    }


    public void getCurrMarkerPosition() {
        m_GameInfo.setMarkerCol(m_Board.GetMarkerCol());
        m_GameInfo.setMarkerRow(m_Board.GetMarkerRow());
    }

    public void playTurn(Player i_Player, int i_Move){
        Square squareToChange = new Square();
        Marker markerToChange = new Marker();

        if(i_Player.getPlayerType() == Player.ePlayerType.COLUMN_PLAYER) {
            squareToChange = (Square)m_Board.getSquareInPos(i_Move, m_GameInfo.getMarkerCol());
            markerToChange = (Marker)m_Board.getSquareInPos(i_Move, m_GameInfo.getMarkerCol());
        }
        else {
            squareToChange = (Square)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),i_Move);
            markerToChange = (Marker)m_Board.getSquareInPos( m_GameInfo.getMarkerRow(),i_Move);
        }

        m_CurrentPlayer.addToPlayerScore(Integer.parseInt(squareToChange.GetSquareSymbol()));

        m_Board.SetSquare(i_Player, squareToChange);
        m_Board.SetMarker(i_Player, markerToChange);


    }
}

/*
* update points
* timer
* move random
* */