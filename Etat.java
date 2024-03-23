import java.util.List;

public class Etat {
    List<SacADos> sacs;
    int g; // represente le cout : le cout d'un etat 
    int f;

    public Etat(List<SacADos> sacs){
        this.sacs=sacs;
 
     
    }

    public int  getG(){
        return this.g;
    }
    public int  getF(){
        return this.f;
    }

    public  List<SacADos>  getSacs(){
        return this.sacs;
    }

    public void  setG(int g){
        this.g=g;
    }

    public void  setF(int f){
        this.f=f;
    }

    public  void setSacs(List<SacADos> s){
        this.sacs=s;
    }
}
