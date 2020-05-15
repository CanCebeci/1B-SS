public abstract class Card {
    private String cardName;
    private String cardType;
    private int energy;
    private String description;

    public Card(String cardName, String cardType, int energy,  String description){
        this.cardName = cardName;
        this.cardType = cardType;
        this.energy = energy;
        this.description = description;
    }

    public String getImage (){ return cardName + ".png"; }
    public String getName(){
        return cardName;
    };
    public void setName(String newCardName){
        this.cardName = newCardName;
    }
    public String getCardType(){
        return cardType;
    }
    public void setCardType(String newCardType){
        this.cardType = newCardType;
    }
    public void setEnergy(int newEnergy ){
        this.energy = energy;
    }
    public int getEnergy(){
        return energy;
    }
    abstract public void affect( Enemy target); // target = null if the card is not targeted.

    // added for test purposes to use in CombatManager.
    // implementers of RunManager can comment this out and write their own toString() if they need to
    public String toString() {
        return  "|-----------|\n" +
                "| "+ energy + "        |\n" +
                "|           |\n" +
                "| " + cardName + " |\n" +
                "| " + description + " |\n" +
                "|           |\n" +
                "|-----------|\n";
    }
}
