package net.undergroundantics.grapple;

import static java.lang.Math.sqrt;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.player.PlayerFishEvent;
import static org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH;
import static org.bukkit.event.player.PlayerFishEvent.State.IN_GROUND;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Grapple implements Listener {
    
    private Boolean grappleCheck(ItemStack item) {
        return item.getItemMeta().hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        Location hookLoc = event.getHook().getLocation();
        double HookX = hookLoc.getX();
        double HookY = hookLoc.getY();
        double HookZ = hookLoc.getZ();
        PlayerFishEvent.State bobberState = event.getState();
        if (bobberState == IN_GROUND) {
            if (grappleCheck(player.getInventory().getItemInMainHand())) {
                player.getInventory().getItemInMainHand().setDurability((short) (player.getInventory().getItemInMainHand().getDurability() - 1));
                player.getWorld().playSound(hookLoc, Sound.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 0.5f, 1.0f);
                player.getWorld().spawnParticle(Particle.CRIT, HookX, HookY, HookZ, 4, 0.1, 0.05, 0.1);
                double d0 = HookX - player.getLocation().getX();
                double d1 = HookY - player.getLocation().getY();
                double d2 = HookZ - player.getLocation().getZ();
                double d3 = (double) sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                Vector v = new Vector(d0 * 0.1D, d1 * 0.1D + (double) sqrt(d3) * 0.08D, d2 * 0.1D);
                player.setVelocity(v);
            }
        } //stops the player from using a grapple as a fishing rod
        else if (bobberState == CAUGHT_FISH && grappleCheck(player.getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }
    }
    //when a player attempts to enchant a grapple no valid enchants will come up
    @EventHandler
    public void onEnchantPrpare(PrepareItemEnchantEvent e) {
        if (grappleCheck(e.getItem())) {
            e.setCancelled(true);
        }
    }
}
