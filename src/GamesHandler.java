public class GamesHandler {
    public static volatile boolean game1Turn = false;
    public static volatile boolean game2Turn = false;

    public static volatile boolean game1Won=false;
    public static volatile boolean game2Won=false;
    public static volatile String gameCode;

    public static void setWin(int playernum, boolean value){
        if(playernum==1)
            game1Won=value;
        else
            game2Won=value;
    }
    public static void setTurn(int playernum, boolean value)
    {
        if(playernum==1)
            game1Turn=value;
        else{
            game2Turn=value;
        }
    }

    public static boolean getWin(int playerNum)
    {
        if(playerNum==1)
            return game1Won;
        else
            return game2Won;
    }

    public static boolean getTurn(int playerNum)
    {
        if(playerNum==1)
            return game1Turn;
        else
            return game2Turn;
    }
    public static String winMessage(int playerNum)
    {
        if(game1Won==false&&game2Won==false)
            return "";
        else if(game1Won==true&&game2Won==true)
            return"Tie Game, no winners\n";
        else if(game1Won==true&&game2Won==false){
            if(playerNum==1)
                return "Congratulations, you ousted the other player and answered first!\n";
            else
                return "Tough Luck, the other player was faster\n";
        }
        else{
            if(playerNum==2)
                return "Congratulations, you ousted the other player and answered first!\n";
            else
                return "Tough Luck, the other player was faster\n";
        }
    }
}
