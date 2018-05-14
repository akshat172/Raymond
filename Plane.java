package Raymond;

import java.io.IOException;

/**
 * Created by marci on 6/05/2018.
 */
public class Plane {
    boolean landed=false;
    boolean using=false;
    boolean asked=false;
    boolean hasToken=false;
    int holder;

    Plane(int h){
        this.hasToken=false;
        this.landed=false;
        this.asked=false;
        this.holder=h;
    }
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
    }

