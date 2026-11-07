import java.util.ArrayList;
import java.util.Scanner;

/**
 * Core controller managing game flow, initialization, and the combat loop.
 */
public class GameManager {
    private Player player;
    private Opponent opponent;
    private Environment environment;
    private Scanner scanner;

    public GameManager() {
        this.player = new Player();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initiates the game sequence.
     */
    public void start() {
        System.out.println("=== WARRIOR GAME: PHASE 1 ===\n");
        initializeGameEntities();
        runBattleLoop();
        this.scanner.close(); // Standard practice to prevent resource leaks
    }

    private void initializeGameEntities() {
        Armor[] availableArmors = {
            new Armor("Light", 20, -5),   //[cite: 1]
            new Armor("Medium", 30, -15), //[cite: 1]
            new Armor("Heavy", 40, -25)   //[cite: 1]
        };
        
        Weapon[] availableWeapons = {
            new Weapon("Dagger", 20, 0),     //[cite: 2]
            new Weapon("Sword", 30, -10),    //[cite: 2]
            new Weapon("Battle Axe", 40, -20)//[cite: 2]
        };
        
        Environment[] availableEnvironments = {
            new Environment("Arena"),
            new Environment("Swamp"),
            new Environment("Colosseum")
        };

        ArrayList<Opponent> availableOpponents = new ArrayList<>();
        availableOpponents.add(new Opponent("Thief", 150, 20, 20, 40));   //[cite: 2]
        availableOpponents.add(new Opponent("Viking", 250, 30, 30, 30));  //[cite: 2]
        availableOpponents.add(new Opponent("Minotaur", 350, 40, 40, 20));//[cite: 2]

        System.out.println("Select Armor (1. Light, 2. Medium, 3. Heavy):");
        int armorChoice = this.scanner.nextInt();
        this.player.equipArmor(availableArmors[armorChoice - 1]);

        System.out.println("Select Weapon (1. Dagger, 2. Sword, 3. Battle Axe):");
        int weaponChoice = this.scanner.nextInt();
        this.player.equipWeapon(availableWeapons[weaponChoice - 1]);

        System.out.println("Select Opponent (1. Thief, 2. Viking, 3. Minotaur):");
        int opponentChoice = this.scanner.nextInt();
        this.opponent = availableOpponents.get(opponentChoice - 1);

        System.out.println("Select Environment (1. Arena, 2. Swamp, 3. Colosseum):");
        int environmentChoice = this.scanner.nextInt();
        this.environment = availableEnvironments[environmentChoice - 1];
    }

    private void runBattleLoop() {
        int turnCounter = 1;
        System.out.println("\n--- BATTLE START ---");

        while (this.player.getHitPoints() > 0 && this.opponent.getHitPoints() > 0) {
            System.out.println("\n=== TURN " + turnCounter + " ===");
            System.out.println("Player HP: " + this.player.getHitPoints() + " | " + this.opponent.getName() + " HP: " + this.opponent.getHitPoints());

            processEnvironmentEffects();
            if (this.player.getHitPoints() <= 0) {
                break;
            }

            System.out.print("Commands: 1. Attack  2. Defend  3. Charge\nChoose: ");
            int command = this.scanner.nextInt();

            this.player.setDefending(false);
            this.opponent.setDefending(false);

            if (command == 2) {
                this.player.setDefending(true);
                System.out.println("Player prepares to defend!");
            } else if (command == 3) {
                if (this.player.isChargeActive()) {
                    System.out.println("Cannot stack charge! You wasted your turn."); //[cite: 2]
                } else {
                    this.player.setCharging(true);
                    System.out.println("Player charges! Next attack damage is tripled."); //[cite: 2]
                }
            }

            boolean isPlayerFirst = this.player.isDefending() || (this.player.getSpeed() >= this.opponent.getSpeed()); //[cite: 2]

            if (isPlayerFirst) {
                executeCombatAction(this.player, this.opponent, command);
                if (this.opponent.getHitPoints() > 0) {
                    executeCombatAction(this.opponent, this.player, 1);
                }
            } else {
                executeCombatAction(this.opponent, this.player, 1);
                if (this.player.getHitPoints() > 0) {
                    executeCombatAction(this.player, this.opponent, command);
                }
            }
            turnCounter++;
        }

        if (this.player.getHitPoints() > 0) {
            System.out.println("\nYOU WIN!");
        } else {
            System.out.println("\nYOU HAVE BEEN KO'd! GAME OVER.");
        }
    }

    private void processEnvironmentEffects() {
        if (this.environment.getName().equals("Swamp")) {
            this.player.setHitPoints(this.player.getHitPoints() - 1); //[cite: 2]
            this.opponent.setAttack(this.opponent.getAttack() + 1);   //[cite: 2]
            System.out.println("[Swamp] Player takes 1 damage. Opponent gains 1 Attack.");
        } else if (this.environment.getName().equals("Colosseum")) {
            this.player.setAttack(this.player.getAttack() + 1);       //[cite: 2]
            
            if (this.opponent.getDefense() > 0) {
                this.opponent.setDefense(this.opponent.getDefense() - 1); //[cite: 2]
            }
            System.out.println("[Colosseum] Player gains 1 Attack. Opponent loses 1 Defense.");
        }
    }

    private void executeCombatAction(Character attacker, Character defender, int command) {
        if (attacker instanceof Player) {
            Player p = (Player) attacker;
            if (p.isDefending() || p.isCharging()) {
                if (p.isCharging()) {
                    p.setChargeActive(true); 
                    p.setCharging(false);    
                }
                return;
            }
        }

        int damageToDeal = attacker.getAttack();
        
        if (attacker instanceof Player) {
            Player p = (Player) attacker;
            if (p.isChargeActive()) {
                damageToDeal *= 3; //[cite: 2]
                p.setChargeActive(false); 
            }
        }

        int damageTaken = defender.takeDamage(damageToDeal);
        System.out.println(attacker.getName() + " attacks! Deals " + damageTaken + " damage to " + defender.getName() + ".");
    }
}