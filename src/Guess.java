import java.util.ArrayList;
import java.util.List;

/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * Ryan Mercado
 * rdm3649
 */
public class Guess {

    private char pegs[]=new char[GameConfiguration.pegNumber];
    private int feedback[] = new int[3];  //feedback[0] is black peg, [1] is white peg, [2] is empty peg
    public Guess(){
        for(int i=0;i<GameConfiguration.pegNumber;i++)
            pegs[i]=' ';
    }
    public Guess(String guess){
        for(int i=0; i<GameConfiguration.pegNumber;i++)
            pegs[i]=(guess.charAt(i));
    }

    public char[] getPegs() {
        return pegs;
    }

    public int[] getFeedback(){
        return feedback;
    }
    //public static boolean anyIndexMatch(List<Character> guess, List<Character> secret){

    //}
    public static boolean checkLowercase(String guess){
        char[] arr = stringToArray(guess);
        for(char c : arr){
            if(Character.isLowerCase(c))
                return false;
        }
        return true;
    }
    public int[] checkCorrect(char[] secretGuess){
        //feedback[0] is black peg, [1] is white peg, [2] is empty peg
        List<Character> dynamicGuess =new ArrayList<Character>();
        List<Character> dynamicSecret = new ArrayList<>();
        for(char c: secretGuess)
            dynamicSecret.add(c);
        for (char c: pegs)
            dynamicGuess.add(c);

        for(int i=0;i<dynamicGuess.size();i++)
        {
            if(dynamicSecret.indexOf(dynamicGuess.get(i))==-1)
            {
                feedback[2]++;
            }
            else if(dynamicSecret.get(i)==dynamicGuess.get(i)){//Only looks at first instance inside guess, needs a method that checks for any
                feedback[0]++;
                dynamicSecret.set(i, ' ');
                dynamicGuess.set(i,' ');
            }
        }
        for(int i=0;i<dynamicGuess.size();i++)
        {
            if(!(dynamicGuess.get(i).equals(' '))&&!(dynamicSecret.indexOf(dynamicGuess.get(i))==-1)) {
                feedback[1]++;
                dynamicSecret.set(dynamicSecret.indexOf(dynamicGuess.get(i)), ' ');

            }
        }



//        for(int i=0;i<secretGuess.length;i++)
//        {
//            if(secretGuess[i]==pegs[i])
//            {
//                feedback[0]++;
//                dynamicSecret.remove(i);
//            }
//            else
//            {
//                nonBlacks.add(secretGuess[i]);
//            }
//        }
//        for(int c=0;c<nonBlacks.size();c++)
//            for(int d=0;d<dynamicSecret.size();d++){
//                if(nonBlacks.get(c).equals(dynamicSecret.get(d))) {
//                    feedback[1]++;
//                    dynamicSecret.remove(d);
//                    nonBlacks.remove(c);
//                }
//            }
        feedback[2]=GameConfiguration.pegNumber-(feedback[0]+feedback[1]);
        if(feedback[2]<0)
            System.out.println("Uge problem with checkCorreckt");
        return feedback;
    }

    public static char[] stringToArray(String guess){
        if(guess.length()!=GameConfiguration.pegNumber)
            System.out.println("Incorrect Guess Length");
        char guessArr[] = new char[GameConfiguration.pegNumber];
        for(int i=0;i<GameConfiguration.pegNumber;i++)
            guessArr[i]=guess.charAt(i);
        return guessArr;
    }

    public String printString() {
        String result="";
        for(int i=0;i<pegs.length;i++){
            result = result+pegs[i];
        }
        return result;
    }
}

