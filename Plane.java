import java.util.HashMap;

/**
 * Created by marci on 6/05/2018.
 */
public class Plane {
    boolean landed=false;
    boolean using=false;
    boolean asked=false;
    boolean hasToken=false;
    int holder;
    HashMap<Integer,String> ipconfig;


    Plane(int h){
        this.hasToken=false;
        this.landed=false;
        this.asked=false;
        this.holder=h;
        this.ipconfig = new HashMap<Integer, String>();
    }
    //Prints a message to the output
    public void printMessage(String m){
        System.out.println(m);
    }
//    public updateHolder(int h){
////        pass;
////    }
    public  synchronized void enterRunway(){
        this.using=true;
    }
    public  synchronized void exitRunway(){
        this.using=false;
    }

    public void addIP(int i,String j){
        this.ipconfig.put(i,j);
    }
    //gets the IP address of a plane
    public String getIP(int i){
        return this.ipconfig.get(i);
    }
    }


