import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Marcel on 6/05/2018.
 */
//Message class, transfers information between nodes
public class Message implements Serializable {

    boolean request;
    boolean token;
    String fromHost;
    int type;
    int fromPort;
    int fromID;
    ArrayList<Integer> forwarded;

    //getters, setters, and mutators
    public ArrayList<Integer> getForwarded() {
        return forwarded;
    }

    public void setForwarded(ArrayList<Integer> forwarded) {
        this.forwarded = forwarded;
    }


    public int getFromID() {
        return fromID;
    }

    public void setFromID(int fromID) {
        this.fromID = fromID;
    }



    public void setRequest(boolean request) {
        this.request = request;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public void setFromHost(String fromHost) {
        this.fromHost = fromHost;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }

    public boolean isRequest() {
        return request;
    }

    public boolean isToken() {
        return token;
    }

    public String getFromHost() {
        return fromHost;
    }

    public int getType() {
        return type;
    }

    public int getFromPort() {
        return fromPort;
    }

    public void forward(int i){
        this.forwarded.add(i);
    }

    //Constructor
    Message(boolean r, boolean t, String h, int f){
       // this.type=t;
        this.fromPort=f;
        this.request=r;
        this.token=t;
        this.fromHost=h;
        this.forwarded=new ArrayList<Integer>();
        this.forwarded.add(f);
    }

    public String toString(){
        return Integer.toString(this.fromPort)+fromHost;
    }

  //Gets latest recipient of message
    public int dequeue(){
        int index=this.forwarded.size()-1;
        int i=this.forwarded.get(index);
        this.forwarded.remove(index);
        return i;

    }
}
