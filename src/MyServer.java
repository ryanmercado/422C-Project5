import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

public class MyServer {
public static void main(String[] args){
try{
    ServerSocket ss1=new ServerSocket(6666);
    Socket s1=ss1.accept();//establishes connection
    Socket s2 = ss1.accept();

    DataInputStream dis1=new DataInputStream(s1.getInputStream());
    DataOutputStream dout1=new DataOutputStream(s1.getOutputStream());

    DataInputStream dis2=new DataInputStream(s2.getInputStream());
    DataOutputStream dout2=new DataOutputStream(s2.getOutputStream());
    boolean gameState=true;
    String code = SecretCodeGenerator.getInstance().getNewSecretCode();

    Runnable r1 = new Game("0", dis1, dout1,code,1);
    Thread t1 = new Thread(r1);
    Runnable r2 = new Game("0", dis2, dout2,code,2);
    Thread t2 = new Thread(r2);
//    Runnable loop = new HandlerLoop();
//    Thread loopThread = new Thread(loop);
    t1.start();
    t2.start();
    //loopThread.start();
    System.out.println("The Secret code is "+code);
    while(gameState){
        //System.out.println("");
        if(GamesHandler.game2Turn==false&&GamesHandler.game1Turn==false){
            if(GamesHandler.game1Won==true||GamesHandler.game2Won==true)
                break;
            GamesHandler.game1Turn=true;
            GamesHandler.game2Turn=true;
        }
    }


//String	str=(String)dis.readUTF();
//System.out.println("message= "+str);

ss1.close();


}catch(Exception e){System.out.println(e);}
}
}
