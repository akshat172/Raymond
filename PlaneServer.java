package Raymond;

import java.io.*;
import java.net.*;
import java.util.EventObject;
import java.util.Scanner;


/**
 * Created by marci on 4/05/2018.
 */
public class PlaneServer extends Thread{
    //private PlaneServer parent;
    //private Socket parentSocket;
    //private Tower tower;
    ServerSocket sSocket;
    private int serverPort;
    private int id;
    private int host;
    private int port;
    int holder;
    boolean using;
    boolean asked;
    Plane plane;
    ThreadAnimation GUI;

   // Raymond.PlaneServer(Raymond.PlaneServer p, Socket s, Raymond.Tower t, int id){
     //   this.parent=p;
     //   this.parentSocket=s;
    //    this.tower=t;
    //    this.id=id;
   // }
   PlaneServer(int s,Plane p) {
       this.id=s;
       this.serverPort=1000+s;
       this.plane=p;
       this.holder=p.holder;
   }
    public static void main(String[] args) throws IOException {
        //int serverPort=Integer.parseInt(args[0]);

        //InetSocketAddress myAddress=new InetSocketAddress("localhost",Integer.parseInt(args[0]));
        //System.out.println(InetAddress.getByName("localhost"));
        //Socket mySocket=new Socket(InetAddress.getByName("localhost"),Integer.parseInt(args[0]));


        boolean r=true;
       // while (r) {
        //    int i=0;
       // }
    }

    public void receiveMessage(){

    }
    public void receiveRequest(){

    }
    public void receivePrivillege(){

    }
    public void communicatewithServer(int serverPort) throws UnknownHostException, IOException{
        Socket mySocket=new Socket();
        byte[] buffer=new byte[128];
        //InetSocketAddress targetAddress=new InetSocketAddress(InetAddress.getByName("localhost"),Integer.parseInt(args[1]));
        InetSocketAddress targetAddress=new InetSocketAddress(InetAddress.getByName("localhost"),serverPort);
        //  InetAddress LHAddress = mySocket.getLocalAddress();
        //  int myPort = mySocket.getLocalPort();
        // System.out.println("\nLocal Host:" + LHAddress + "\nLocal port:" + myPort);
        mySocket.connect(targetAddress);
        InputStream stream=mySocket.getInputStream();
        stream.read(buffer,0,128);
        System.out.println(new String(buffer));
        mySocket.close();
    }
    @Override
    public void run() {
       try {
           boolean waiting=true;
           ServerSocket sSocket = new ServerSocket(serverPort, 100, InetAddress.getByName("localhost"));
           plane.printMessage("Server Init");
           System.out.println(sSocket.getLocalSocketAddress().toString());
           if(plane.hasToken){
               this.enterRunway();
           }
           while(waiting) {
               Socket s = sSocket.accept();
               System.out.println(s.getRemoteSocketAddress().toString());
               System.out.println(s.getLocalSocketAddress().toString());
               OutputStream out = s.getOutputStream();
               InputStream in = s.getInputStream();
               DataInputStream is = new DataInputStream(s.getInputStream());
               BufferedInputStream input = new BufferedInputStream(is);
               ObjectInputStream objectInputStream = new ObjectInputStream(input);
               Object object = objectInputStream.readObject();
               Message mesg = Message.class.cast(object);
               String testMessage = "Received";
               out.write(testMessage.getBytes(), 0, testMessage.length());
               //processMess
               s.close();
               processMessage(mesg);//Seperate thread
               System.out.println(object.toString());
             //  boolean r = true;
               int inputCount = 0;
             //  byte inputLine[] = new byte[256];
              // while ((inputCount = input.read(inputLine)) != -1) {
              //     System.out.write(inputLine);
              // }
               //while(r){
               //     int i=0;
               // }
               //s.close();
           }
       }
       catch (IOException e){
           plane.printMessage("In Server");
           plane.printMessage(e.toString());
           e.printStackTrace();
       }
       catch (ClassNotFoundException e){
           System.out.println(e);
       }
    }
    public void processMessage(Message o) throws IOException{
        plane.printMessage("Mesg; "+o.forwarded);
        if(o.isRequest() && !plane.hasToken){
            plane.printMessage("Request forwarded");
            //forward request to holder;
            o.forward(this.id);
            plane.printMessage("Mesgafter; "+o.forwarded);
            sendMessage(o,this.holder);
//            Socket mySocket = new Socket();
//            InetSocketAddress targetAddress = new InetSocketAddress(InetAddress.getByName("localhost"), 1000+holder);
//            mySocket.connect(targetAddress);
//            DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
//            o.forward(1000+id);
//            //Message m=new Message(true,false,"localhost",this.id);
//            objectOutputStream.writeObject(o);
//            objectOutputStream.flush();
//            mySocket.close();
            //Forward request
        }
        if(o.isRequest() && plane.hasToken  ){
            plane.printMessage("Token requested");
            //Check if using


            //Send privillege to from
            Message tokenMessage=new Message(false,true,"localhost",this.id);
            tokenMessage.setForwarded(o.forwarded);
            this.holder=o.dequeue();
            plane.holder=this.holder;
            plane.hasToken=false;
            //this.GUI.dispatchEvent(new WindowEvent(this.GUI, WindowEvent.WINDOW_CLOSING));
            this.GUI.close();
            sendMessage(tokenMessage,this.holder);
            //Update holder to from/
        }
        if(o.isToken() && !plane.asked){
            plane.printMessage("Token forwarded");
            this.holder=o.dequeue();
            plane.holder=this.holder;
            sendMessage(o,this.holder);
            //Forward request
            //Send message to from
            //Update holder
            //this.holder=o.getFromID();
        }
        if(o.isToken() && plane.asked ){
            if (o.forwarded.isEmpty()) {
                plane.printMessage("Token accepted");
                //this.holder = o.dequeue();
                //plane.holder = this.holder;
                plane.asked = false;
                plane.hasToken = true;
                enterRunway();
            }
            else{
                plane.printMessage("Token forwarded");
                this.holder=o.dequeue();
                plane.holder=this.holder;
                sendMessage(o,this.holder);
            }
            //Enter CS
        }

    }
    public void sendMessage(Message o,int to){
        try {
            plane.printMessage("Sending to "+Integer.toString(to));
            Socket mySocket = new Socket();
            InetSocketAddress targetAddress = new InetSocketAddress(InetAddress.getByName("localhost"), 1000+to);
            mySocket.connect(targetAddress);
            DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
           // o.forward(this.id);
            //Message m=new Message(true,false,"localhost",this.id);
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            mySocket.close();
        }
        catch (UnknownHostException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
    public void enterRunway(){
        plane.printMessage("Entered runway");
        plane.using=true;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        plane.printMessage("Enter a number to enter runway");
        int n = reader.nextInt(); // Scans the next token of the input as an int.
        //Create graphic
        this.GUI=new ThreadAnimation();
        this.GUI.animate();
        plane.printMessage("Enter a number to exit runway");
         n = reader.nextInt();
         this.exitRunway();
        //String[] args=new String[1];
        //GUI.main(args);
    }
    public void exitRunway(){

        plane.landed=true;
        plane.using=false;
    }
    //Get request

    //Get privilege




}
