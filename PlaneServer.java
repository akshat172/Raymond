import java.io.*;
import java.net.*;
import java.util.Scanner;


/**
 * Created by marci on 4/05/2018.
 */
//Planeserver class processes incoming messages and respond accordingly
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

    //Constructor
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


    //The plane waits for a message to it to co0me through, and then executes the appropriate action
    @Override
    public void run() {
       try {
           boolean waiting=true;
           ServerSocket sSocket = new ServerSocket(serverPort, 100, InetAddress.getByName(plane.getIP(this.id)));
           //ServerSocket sSocket = new ServerSocket(serverPort, 100, InetAddress.getByName("h"+Integer.toString(this.id)));
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
               processMessage(mesg);
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
    //Processing the incoming message
    public void processMessage(Message o) throws IOException{
        //plane.printMessage("Mesg; "+o.forwarded);
        //Forwarding a request
        if(o.isRequest() && !plane.hasToken){
            //plane.printMessage("Request forwarded");
            //forward request to holder;
            o.forward(this.id);
            //plane.printMessage("Mesgafter; "+o.forwarded);
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
        //Sending the token to a requester
        if(o.isRequest() && plane.hasToken  ){
            //plane.printMessage("Token requested");
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
        //Forwarding the token to it's requestor
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
        //Receiving a token
        if(o.isToken() && plane.asked ){
            if (o.forwarded.isEmpty()) {
                plane.printMessage("Token accepted");
                //this.holder = o.dequeue();
                //plane.holder = this.holder;
                plane.asked = false;
                plane.hasToken = true;
                enterRunway();
            }
            //If the token was not sent in reply to it's request
            else{
                plane.printMessage("Token forwarded");
                this.holder=o.dequeue();
                plane.holder=this.holder;
                sendMessage(o,this.holder);
            }
        }

    }
    //Sending a message to the requested plane
    public void sendMessage(Message o,int to){
        try {
            plane.printMessage("Sending to "+Integer.toString(to));
            Socket mySocket = new Socket();
            //InetSocketAddress targetAddress = new InetSocketAddress(InetAddress.getByName("h"+Integer.toString(to)), 1000+to);
            InetSocketAddress targetAddress = new InetSocketAddress(InetAddress.getByName(plane.getIP(to)), 1000+to);
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
        //plane.printMessage("Enter a number to enter runway");
        //int n = reader.nextInt(); // Scans the next token of the input as an int.
        //Create graphic
        this.GUI=new ThreadAnimation();
        this.GUI.toFront();
        this.GUI.setLocationRelativeTo(null);
        this.GUI.animate();
        plane.printMessage("Enter a number to exit runway");
        int n = reader.nextInt();

         this.exitRunway();
        //String[] args=new String[1];
        //GUI.main(args);
    }
    //Leaving critical section
    public void exitRunway(){

        plane.landed=true;
        plane.using=false;
    }


    //Deprecated
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
        InetSocketAddress targetAddress=new InetSocketAddress(InetAddress.getByName("h"+Integer.toString(this.id)),serverPort);
        //  InetAddress LHAddress = mySocket.getLocalAddress();
        //  int myPort = mySocket.getLocalPort();
        // System.out.println("\nLocal Host:" + LHAddress + "\nLocal port:" + myPort);
        mySocket.connect(targetAddress);
        InputStream stream=mySocket.getInputStream();
        stream.read(buffer,0,128);
        System.out.println(new String(buffer));
        mySocket.close();
    }


}
