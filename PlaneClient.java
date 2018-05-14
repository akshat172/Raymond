package Raymond;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * Created by marci on 6/05/2018.
 */
public class PlaneClient extends Thread {
    int holder;
    boolean using;
    boolean asked;
    int id;
    Plane plane;

    PlaneClient(int h, Plane p,int i){
        this.holder=h;
        this.asked=false;
        this.using=true;
        this.plane=p;
        this.id=i;

    }

    public static void main(String[] args) throws IOException {

        //mySocket.close();

        //Send request

        //Leave critical section

    }
    public void run(){
        try {

           // int serverPort = Integer.parseInt(args[0]);
            int serverPort = this.holder;
            byte[] buffer = new byte[128];
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            boolean r=true;

            //reader.close();
            plane.printMessage("Client Init");
            //while(r) {
            //  InetAddress LHAddress = mySocket.getLocalAddress();
            //  int myPort = mySocket.getLocalPort();
            // System.out.println("\nLocal Host:" + LHAddress + "\nLocal port:" + myPort);
            //InetSocketAddress targetAddress=new InetSocketAddress(InetAddress.getByName("localhost"),Integer.parseInt(args[1]));

            plane.printMessage("Started");
            //Send request for CS
            while(r) {
                Thread.sleep(4000);
                //if(!plane.landed && !plane.asked) {
                if( !plane.asked) {

                        //Check critcal section
                        //if has token and cs empty
                        //elif has token and cs not empty
                        //error
                        //elif no token
                        if(this.holder== plane.holder && this.holder!=this.id ) {
                            Socket mySocket = new Socket();
                            plane.printMessage("Check");
                            InetSocketAddress targetAddress = new InetSocketAddress(InetAddress.getByName("localhost"), 1000+this.holder);
                            mySocket.connect(targetAddress);
                            DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                            Message m = new Message(true, false, "localhost", this.id);
                            objectOutputStream.writeObject(m);
                            objectOutputStream.flush();
                            plane.asked = true;
                            mySocket.close();
                        }

                    }
                }

            //mySocket.close();
//            InputStream stream = mySocket.getInputStream();
//            stream.read(buffer, 0, 128);
//            System.out.println(new String(buffer));
//
//            String userInput;
//            while ((userInput = stdIn.readLine()) != null) {
//                out.write(userInput.getBytes());
//                out.flush();
//            }

           // }
        }
        catch (IOException e){
            plane.printMessage("In client");
            plane.printMessage(e.toString());

        }
        catch (InterruptedException e){

        }
    }

    public void makeRequest(){

    }
    public void sendRequest(){

    }
    public void enterRunway(){

    }
    public void exitRunway(){
        //process next message in queue

    }
    public void startup(){

    }
}
