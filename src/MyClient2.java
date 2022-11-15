import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient2 {
public static void main(String[] args) {
try{	
Socket s=new Socket("localhost",6666);

DataOutputStream dout=new DataOutputStream(s.getOutputStream());
    DataInputStream dis=new DataInputStream(s.getInputStream());

    Scanner scan = new Scanner(System.in);


    boolean gameState=true;
    boolean inputNeeded;
    while(gameState){
        //inputNeeded=dis.readBoolean();
        System.out.println((String)dis.readUTF());
        dout.writeUTF(scan.nextLine());

    }

dout.flush();

dout.close();
s.close();

}catch(Exception e){System.out.println(e);}
}
}
