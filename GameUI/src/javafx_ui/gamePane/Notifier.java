package javafx_ui.gamePane;

import sharedStructures.PlayerData;


public class Notifier {


    public String showExceptionThrown(String message) {
        return message;
    }

    public String notifyGameDone() {
        return "Game finished!";
    }

    public String notifyPlayerExitGame(){
        return "BYE";
    }

    public String fileWasLoadedSuccessfully() {
        return "File was loaded successfully";
    }

    public void notifyLoadNewGame() {
        System.out.println("Load new file to begin new game");
    }

    public String announceWinner(int i_Max, PlayerData i_Winner) {
        return "The winner is the " + i_Winner.getName() + " player with " + i_Max + " points!";
    }

    public String notifyInvalidSquareChoice() {
       return "Square can't be chosen because it's color is not as the current player's color or it's value is not a number";
    }

    public String announceTie(int i_Points) {
        return "It's a tie! Both players with " + i_Points + " points\n";
    }
}
