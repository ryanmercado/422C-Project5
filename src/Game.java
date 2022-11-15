import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * Ryan Mercado
 * rdm3649
 */
public class Game implements Runnable {

    private static boolean testing;
    private DataInputStream dis;
    private DataOutputStream dout;
    private String code;
    private int playerNum;



    public Game(){
        testing = false;
    }
    public Game(String args, DataInputStream in, DataOutputStream out, String secret, int num){
        dis = in;
        dout = out;
        code=secret;
        playerNum=num;
        if(args.equals("1"))
            testing=true;
        else
            testing=false;
    }

    public static String printHistory(Guess guesses[],int num, String secret){
        int feedback[];
        String result ="";
        result=result+ "\nGuess History:\n";
        //if(testing)
        //    System.out.println(" (Secret Code: "+secret+")");
        //else{
        //    System.out.println();
        //}
        for(int i=0;i<num;i++)
        {
            feedback = guesses[i].getFeedback();
            result=result+guesses[i].printString();
            result=result+"\t\t"+ feedback[0] + "B_" + feedback[1] + "W\n";
        }
        return result;
    }

    public void run() {
        String currOutput="";
        //SecretCodeGenerator secret = SecretCodeGenerator.getInstance();
try {
    boolean gameState = true;
    String input;

    Guess guesses[] = new Guess[GameConfiguration.guessNumber];

    //dout.writeBoolean(true);
    dout.writeUTF("Welcome to Mastermind.  Here are the rules.\n\nThis is a text version of the classic board game Mastermind.\n\nThe  computer  will  think  of  a  secret  code.  The  code  consists  of "  + GameConfiguration.pegNumber + "\ncolored  pegs.  The  pegs  MUST  be  one  of " + GameConfiguration.colors.length +  " colors:  blue,  green,\norange, purple, red, or yellow. A color may appear more than once in\nthe  code.  You  try  to  guess  what  colored  pegs  are  in  the  code  and\nwhat  order  they  are  in.  After  you  make  a  valid  guess  the  result\n(feedback) will be displayed.\n\nThe  result  consists  of  a  black  peg  for  each  peg  you  have  guessed\nexactly correct (color and position) in your guess.  For each peg in\nthe guess that is the correct color, but is out of position, you get\na  white  peg.  For  each  peg,  which  is  fully  incorrect,  you  get  no\nfeedback.\n\nOnly the first letter of the color is displayed. B for Blue, R for\nRed, and so forth. When entering guesses you only need to enter the\nfirst character of each color as a capital letter.\n\nYou  have " + GameConfiguration.guessNumber +" guesses  to  figure  out  the  secret  code  or  you  lose  the\ngame.  Are you ready to play? (Y/N):");


    gameLoop:
    while (gameState) {
        input = dis.readUTF();

        if (!(input.equals("Y")))
            return;

        //String code = secret.getNewSecretCode();

        //String code ="RBYR";
        char[] secretCode = Guess.stringToArray(code);
        currOutput=currOutput+ "\n\nGenerating secret code\n";// ...(for this example the secret code is " + code + ")\n";

//        if (testing)
//            System.out.println("(for this example the secret code is " + code + ")");
//        else
//            System.out.println();
//        System.out.println();

        boolean validGuess;
        int[] feedback;
        int count;

        for (int i = 0; i < GameConfiguration.guessNumber; i++) {
            validGuess = false;
            if(GamesHandler.game1Won==true||GamesHandler.game2Won==true){
                dout.writeUTF(currOutput);
                currOutput="";
                break;
            }
            while(!GamesHandler.getTurn(playerNum)){
                //System.out.print(playerNum+" "+GamesHandler.getTurn(playerNum));
            }
            currOutput=currOutput+ "\nYou have " + (GameConfiguration.guessNumber - i) + " guesses left. \n" +
                    "What is your next guess? \n" +
                    "Type in the characters for your guess and press enter. \n" +
                    "Enter guess:";
            dout.writeUTF(currOutput);
            currOutput="";
            //dout.writeBoolean(true);


            do {
                input = dis.readUTF();
                if (input.equals("history") || input.equals("HISTORY") || input.equals("History")) {
                    currOutput=currOutput+ printHistory(guesses, i, code);
                    currOutput=currOutput+"\nYou have " + (GameConfiguration.guessNumber - i) + " guesses left. \n" + "What is your next guess? \n" +
                            "Type in the characters for your guess and press enter. \n" +
                            "Enter guess:";
                    dout.writeUTF(currOutput);
                    currOutput="";
                    continue;
                }
                if (i != GameConfiguration.guessNumber - 1)
                    currOutput=currOutput+ "\n" + input;
                if (input.length() != GameConfiguration.pegNumber || !Guess.checkLowercase(input)) {
                    currOutput=currOutput+ " -> INVALID GUESS\n";
                    currOutput=currOutput+"What is your next guess? \n" +
                            "Type in the characters for your guess and press enter. \n" +
                            "Enter guess:";
                    dout.writeUTF(currOutput);
                    currOutput="";
                    continue;
                }
                validGuess = true;

                for (int j = 0; j < input.length(); j++) {
                    count = 0;
                    for (int k = 0; k < GameConfiguration.colors.length; k++) {
                        if (!(Character.toString(Character.toUpperCase(input.charAt(j))).equals(GameConfiguration.colors[k]))) {
                            count++;
                        }
                    }
                    if (count == GameConfiguration.colors.length) {
                        validGuess = false;
                        break;
                    }
                }
                if (!validGuess) {
                    currOutput=currOutput+ " -> INVALID GUESS\n";
                    currOutput=currOutput+ "What is your next guess? \n" +
                            "Type in the characters for your guess and press enter. \n" +
                            "Enter guess:";
                    dout.writeUTF(currOutput);
                    currOutput="";
                }
            } while (!validGuess);
            guesses[i] = new Guess(input);
            feedback = guesses[i].checkCorrect(secretCode);//feedback[0] is black peg, [1] is white peg, [2] is empty peg
            if (feedback[0] == GameConfiguration.pegNumber) {
                currOutput=currOutput+ " -> Result: " + feedback[0] + "B_" + feedback[1] + "W - Correct Guess!\n\n";

                GamesHandler.setWin(playerNum,true);
                GamesHandler.setTurn(playerNum,false);
                while(!(GamesHandler.game1Turn==false&&GamesHandler.game2Turn==false)){}
                currOutput = currOutput+GamesHandler.winMessage(playerNum);
                currOutput=currOutput+ "Are you ready for another game (Y/N):";

                dout.writeUTF(currOutput);
                currOutput="";
                break gameLoop;
                //continue gameLoop;
            }
            if (i != GameConfiguration.guessNumber - 1) {
                currOutput = currOutput + " -> Result: " + feedback[0] + "B_" + feedback[1] + "W\n";
                GamesHandler.setTurn(playerNum,false);
                currOutput=currOutput+GamesHandler.winMessage(playerNum);
            }

        }
        currOutput=currOutput+ "\nSorry, you are out of guesses. You lose, boo-hoo.\nAre you ready for another game (Y/N):";
        dout.writeUTF(currOutput);
        currOutput="";
    }
}
        catch(Exception e){
            System.out.println(e);

        }

    }
}
