public class Character {
    protected String name;
    protected int hitPoints;
    protected int attack;
    protected int defense;
    protected int speed;
    protected boolean isDefending;

    /**
     * Initializes a character with default base stats.
     * 
     * @param name The name of the character.
     */
    public Character(String name) {
        this.name = name;
        this.hitPoints = 100; //[cite: 1]
        this.attack = 1;      //[cite: 1]
        this.defense = 1;     //[cite: 1]
        this.speed = 50;      //[cite: 1]
        this.isDefending = false;
    }

    /**
     * Initializes a character with specific stats.
     * 
     * @param name The name of the character.
     * @param hitPoints The maximum hit points.
     * @param attack The base attack value.
     * @param defense The base defense value.
     * @param speed The base speed value.
     */
    public Character(String name, int hitPoints, int attack, int defense, int speed) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.isDefending = false;
    }

    /**
     * Calculates and applies damage taken by subtracting defense from incoming damage.
     * 
     * @param damage The raw incoming damage.
     * @return The final damage dealt after calculations.
     */
    public int takeDamage(int damage) {
        int finalDamage = damage - this.defense;
        
        if (finalDamage < 1) {
            finalDamage = 1; 
        }
        
        if (this.isDefending) {
            finalDamage = finalDamage / 2; //[cite: 2]
        }
        
        this.hitPoints -= finalDamage;
        if (this.hitPoints < 0) {
            this.hitPoints = 0;
        }
        
        return finalDamage;
    }

    public String getName() { 
        return this.name; 
    }
    public int getHitPoints() { 
        return this.hitPoints; 
    }
    public int getAttack() { 
        return this.attack; 
    }
    public int getDefense() { 
        return this.defense; 
    }
    public int getSpeed() { 
        return this.speed; 
    }
    public boolean isDefending() { 
        return this.isDefending; 
    }

    public void setHitPoints(int hitPoints) { 
        this.hitPoints = hitPoints; 
    }
    public void setAttack(int attack) { 
        this.attack = attack; 
    }
    public void setDefense(int defense) { 
        this.defense = defense; 
    }
    public void setDefending(boolean defending) { 
        this.isDefending = defending; 
    }
}