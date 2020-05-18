public class ThunderclapPlus extends Card {
    // --- constants ---
    private static final int ENERGY_COST = 1;
    private static final int DAMAGE = 7;
    private static final int VULNERABLE_APPLIED = 2;
    private static final String DESCRIPTION = "Deals " + DAMAGE + " damage and apply " + VULNERABLE_APPLIED + " to all enemies.";
    private static final boolean TARGET_REQUIREMENT = false;
    private static int COST = 200;
    // constructors
    public ThunderclapPlus() {
        super("Thunderclap+", "Attack", ENERGY_COST, DESCRIPTION, TARGET_REQUIREMENT,COST);
    }

    public void affect(Enemy target) {
        for (Enemy e: CombatManager.getInstance().getEnemies()) {
            CombatManager.getInstance().getPlayer().dealDamage(DAMAGE, e);
            target.addStatusEffect( new Vulnerable(VULNERABLE_APPLIED));
        }
    }
    public Card upgradedVersion() {
        return null;
    }
}
