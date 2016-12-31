package logic;

import shared.GameInfo;
import sharedStructures.eColor;
import sharedStructures.ePlayerType;

import java.util.Random;

public class ComputerPlayer extends Player {

    public ComputerPlayer(int i_ID, String i_Name, ePlayerType i_Type, eColor i_Color){
        super(i_ID, i_Name, i_Type, i_Color);
    }

    @Override
    public void playTurn(Board i_Board, GameInfo i_GameInfo , Square i_ChosenSquare) {

        Random r = new Random();
        boolean hasNumberInColor = false;

        i_ChosenSquare = null;

        int randomMove = r.nextInt(i_Board.getBoardSize());

        if (i_GameInfo.getGameType().equals(Game.eGameType.Basic.name())){

            if(getPlayerType().name().equals(BasicGame.ePlayerOrientation.COLUMN_PLAYER.name())) {
                while(i_Board.getSquareInPos(randomMove, i_GameInfo.getMarkerCol()).getSquareSymbol().equals("") ||
                        i_Board.getSquareInPos(randomMove, i_GameInfo.getMarkerCol()).getSquareSymbol().equals("@")){
                    randomMove = r.nextInt(i_Board.getBoardSize());
                }

                i_ChosenSquare = i_Board.getSquareInPos(randomMove, i_GameInfo.getMarkerCol());
            }
            else {
                while(i_Board.getSquareInPos(i_GameInfo.getMarkerRow(), randomMove).getSquareSymbol().equals("") ||
                        i_Board.getSquareInPos(i_GameInfo.getMarkerRow(), randomMove).getSquareSymbol().equals("@")){
                    randomMove = r.nextInt(i_Board.getBoardSize());
                }

                i_ChosenSquare = i_Board.getSquareInPos(i_GameInfo.getMarkerRow(), randomMove);
            }

        }
        else {
            //checking color in col
            for(int row = 0; row < i_Board.getBoardSize(); row++){
                if(i_Board.getSquareInPos(row, i_GameInfo.getMarkerCol()).getColor().name().equals(getPlayerColor().name())){
                    hasNumberInColor = true;
                    break;
                }
            }
            //finding the relevant square in col
            while( hasNumberInColor && (illegalSquareToChoose (i_Board, randomMove, i_GameInfo.getMarkerCol()) )) {
                randomMove = r.nextInt(i_Board.getBoardSize());
            }
            //saving chosen square
            if(hasNumberInColor){
                i_ChosenSquare = i_Board.getSquareInPos(randomMove, i_GameInfo.getMarkerCol());
            }

            //couldn't find color in col
            if (!hasNumberInColor){
                //checking color in row
                for(int col = 0; col < i_Board.getBoardSize(); col++){
                    if(i_Board.getSquareInPos(i_GameInfo.getMarkerRow(), col).getColor().name().equals(getPlayerColor().name())){
                        hasNumberInColor = true;
                        break;
                    }
                }
                //finding the relevant square in row
                while( hasNumberInColor && (illegalSquareToChoose (i_Board, i_GameInfo.getMarkerRow(), randomMove ))) {
                    randomMove = r.nextInt(i_Board.getBoardSize());
                }

                //saving chosen square
                if(hasNumberInColor){
                    i_ChosenSquare = i_Board.getSquareInPos(i_GameInfo.getMarkerRow(), randomMove);
                }
            }
        }

        super.playTurn(i_Board, i_GameInfo , i_ChosenSquare);
    }


    private boolean illegalSquareToChoose( Board i_Board, int i_Row, int i_Col ){

        return i_Board.getSquareInPos(i_Row, i_Col).getSquareSymbol().equals("") ||
                i_Board.getSquareInPos(i_Row, i_Col).getSquareSymbol().equals("@") ||
                !i_Board.getSquareInPos(i_Row, i_Col).getColor().name().equals(getPlayerColor().name());

    }
}


