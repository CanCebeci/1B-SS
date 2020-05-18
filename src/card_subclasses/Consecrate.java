public class Consecrate extends Card {
    // --- constants ---
    private static final int ENERGY_COST = 0;
    private static final int DAMAGE = 5;
    private static final String DESCRIPTION = "Deals " + DAMAGE + " damage to all enemies.";
    private static final boolean TARGET_REQUIREMENT = false;
    private static int COST = 200;
    // constructors
    public Consecrate() {
        super("Consecrate", "Attack", ENERGY_COST, DESCRIPTION, TARGET_REQUIREMENT,COST);
    }

    public void affect(Enemy target) {
        for (Enemy e: CombatManager.getInstance().getEnemies()) {
            CombatManager.getInstance().getPlayer().dealDamage(DAMAGE, e);
        }
    }
    public Card upgradedVersion() {
        return new ConsecratePlus();
    }
}