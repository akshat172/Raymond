import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by marci on 6/05/2018.
 */
public class Node {
    private int port;
    private String host;
    private String[] neighbours;
    private int id;

    Node(int id, int port, String host) {
        this.host = host;
        this.port = port;
        //this.neighbours=n;
        this.id = id;

    }

    public void setNeighbours(String[] n) {
        this.neighbours = n;
    }

    //Initialises the planes and sets up
    public static void main(String[] args) throws IOException {
        int id = Integer.parseInt(args[0]);
        ArrayList<PlaneClient> clients=new ArrayList<PlaneClient>();
        int holder=0;
       // String hold="Holder_config";
        String hold=args[2];
        String lineh = null;
        FileReader fileReaderhold = new FileReader(hold);
        BufferedReader bufferedReaderh = new BufferedReader(fileReaderhold);
        while ((lineh = bufferedReaderh.readLine()) != null) {
            String[] l = lineh.split(",");
            if (Integer.parseInt(l[0]) == id) {
                holder=Integer.parseInt(l[1]);
            }
        }
        Plane p = new Plane(holder);
        if (id==Integer.parseInt(args[1])){
            p.hasToken=true;
        }
        //String ipconfig="ipconfig";
        String ipconfig=args[3];
        FileReader ipreader=new FileReader(ipconfig);
        BufferedReader bufferedip = new BufferedReader(ipreader);
        String lineip;
        while ((lineip= bufferedip.readLine()) != null) {
            String[] l = lineip.split(",");
            p.addIP(Integer.parseInt(l[0]),l[1]);
        }
        PlaneServer sPlane = new PlaneServer(id, p);
        //String fileName = "configConnections";
        String fileName=args[4];
        String line = null;
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            String[] l = line.split(",");
            if (Integer.parseInt(l[0]) == id) {
                PlaneClient cPlane = new PlaneClient(Integer.parseInt(l[1]), p,id);
                clients.add(cPlane);
            }
        }
        sPlane.start();
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        p.printMessage("Enter a number to start the clients");
        int n = reader.nextInt(); // Scans the next token of the input as an int.
        for (PlaneClient client: clients) {
            client.start();
        }

        }

            //System.out.println("Server started");
            //cPlane.start();
            //for (int i=0;i<;)






    //Deprecated
    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String[] getNeighbours() {
        return neighbours;
    }

    public int getId() {
        return this.id;
    }
}
