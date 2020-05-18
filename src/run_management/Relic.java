public class Relic {
    //attributes
    private String name;
    private int relicCost;
    private String relicDescription;
    private RelicEffect effect;
    private String image;

    //constructors
    public Relic(String name, int relicCost, String relicDescription, String image){
        this.name = name;
        this.relicCost = relicCost;
        this.relicDescription = relicDescription;
        this.image = image;
    }
    //methods
    protected void setEffect( RelicEffect effect) {this.effect = effect;}
    public RelicEffect getEffect() {return effect;}

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setRelicDescription(){
        this.relicDescription = relicDescription;
    }
    public String getRelicDescription(){
        return relicDescription;
    }

    public void setImage(String image){this.image = image;}
    public String getImage(){return getClass().getName() + ".png";}

    public void setCost(int cost){relicCost = cost;}
    public int getCost(){return relicCost;}
    public String toString(){return getRelicDescription();}
}

