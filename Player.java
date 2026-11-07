public class Player extends Character {
    private Armor equippedArmor;
    private Weapon equippedWeapon;
    private boolean isCharging;
    private boolean isChargeActive;

    public Player() {
        super("Warrior", 100, 1, 1, 50); //[cite: 1]
        this.isCharging = false;
        this.isChargeActive = false;
    }

    /**
     * Equips armor and updates the player's defense and speed.
     * 
     * @param armor The armor to equip.
     */
    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
        this.defense += armor.getDefense();
        this.speed += armor.getSpeedPenalty();
    }

    /**
     * Equips a weapon and updates the player's attack and speed.
     * 
     * @param weapon The weapon to equip.
     */
    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
        this.attack += weapon.getAttack();
        this.speed += weapon.getSpeedPenalty();
    }

    public boolean isCharging() { 
        return this.isCharging; 
    }
    public void setCharging(boolean charging) {
         this.isCharging = charging; 
    }
    
    public boolean isChargeActive() { 
        return this.isChargeActive; 
    }
    public void setChargeActive(boolean chargeActive) { 
        this.isChargeActive = chargeActive; 
    }
}