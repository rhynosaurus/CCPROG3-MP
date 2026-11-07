public class Armor {
    private String name;
    private int defense;
    private int speedPenalty;

    public Armor(String name, int defense, int speedPenalty) {
        this.name = name;
        this.defense = defense;
        this.speedPenalty = speedPenalty;
    }

    public String getName() { 
        return this.name; 
    }
    public int getDefense() { 
        return this.defense; 
    }
    public int getSpeedPenalty() { 
        return this.speedPenalty; 
    }
}