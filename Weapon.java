public class Weapon {
    private String name;
    private int attack;
    private int speedPenalty;

    public Weapon(String name, int attack, int speedPenalty) {
        this.name = name;
        this.attack = attack;
        this.speedPenalty = speedPenalty;
    }

    public String getName() { 
        return this.name; 
    }
    public int getAttack() { 
        return this.attack; 
    }
    public int getSpeedPenalty() { 
        return this.speedPenalty; 
    }
}