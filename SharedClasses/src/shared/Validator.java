package shared;


import Generated.GameDescriptor;

import java.util.List;

public class Validator {
    private GameInfo m_GameInfo;

    public Validator(GameInfo i_GameInfo){
        m_GameInfo = i_GameInfo;
    }

    public void checkBoardSize(int i_Size) throws Exception {
        if(i_Size > 50 || i_Size < 5) {
            throw(new Exception("Invalid board size\n"));
        }
    }

    public void checkValidSquaresLocation(List<GameDescriptor.Board.Structure.Squares.Square> squares) throws Exception {
        boolean validInput = true;
        String exceptionMessage = "";

        for(GameDescriptor.Board.Structure.Squares.Square s: squares){
            if(s.getRow().intValue() > m_GameInfo.getBoardSize() || s.getRow().intValue() < 1) {
                validInput = false;
                exceptionMessage = "Invalid file. Square row out of bounds\n";
            }
            else if(s.getColumn().intValue() > m_GameInfo.getBoardSize() || s.getColumn().intValue() < 1) {
                validInput = false;
                exceptionMessage = "Invalid file. Square column out of bounds\n";
            }

            for(GameDescriptor.Board.Structure.Squares.Square currSquare: squares) {
                if(s.getColumn().intValue() == currSquare.getColumn().intValue()
                        && s.getRow().intValue() == currSquare.getRow().intValue() && s != currSquare){
                    validInput = false;
                    exceptionMessage = "Invalid file. Two values assigned to same square\n";
                    break;
                }
            }
        }

        if(!validInput) {
            throw(new Exception(exceptionMessage));
        }
    }

    public void checkRangeForRandomBoard() throws Exception {
        if(m_GameInfo.getRangeFrom() > m_GameInfo.getRangeTo()){
            throw(new Exception("Invalid file. Illegal range of numbers: FROM is bigger than TO\n"));
        }

        if(m_GameInfo.getRangeTo() - m_GameInfo.getRangeFrom() + 1 > (m_GameInfo.getBoardSize() * m_GameInfo.getBoardSize()) - 1){
            throw(new Exception("Invalid file. Range of numbers is bigger than size of board\n"));
        }
    }

    public void checkValidMarkerLocation(GameDescriptor.Board.Structure.Squares.Marker marker) throws Exception {
        boolean validInput = true;
        String exceptionMessage = "";

        if(marker.getRow().intValue() > m_GameInfo.getBoardSize() || marker.getRow().intValue() < 1) {
            validInput = false;
            exceptionMessage = "Invalid file. Marker row out of bounds\n";
        }
        else if(marker.getColumn().intValue() > m_GameInfo.getBoardSize() || marker.getColumn().intValue() < 1) {
            validInput = false;
            exceptionMessage = "Invalid file. Marker column out of bounds\n";
        }

        if(!validInput) {
            throw(new Exception(exceptionMessage));
        }
    }
}