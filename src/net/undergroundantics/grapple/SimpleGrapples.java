package net.undergroundantics.grapple;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import static org.bukkit.inventory.ItemFlag.HIDE_POTION_EFFECTS;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleGrapples extends JavaPlugin{

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new Grapple(), this);
        this.registerGrappleRecipe();
    }
        public void registerGrappleRecipe()
        {
        ItemStack grapple = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta meta = grapple.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Grapple");
        meta.addItemFlags(HIDE_POTION_EFFECTS);
        grapple.setItemMeta(meta);
        
        ShapedRecipe grappleRecipe = new ShapedRecipe(new NamespacedKey(this, "Grapple"), grapple);
        grappleRecipe.shape("  s", " sl", "s i");
        grappleRecipe.setIngredient('l', Material.LEASH);
        grappleRecipe.setIngredient('i', Material.IRON_INGOT);
        grappleRecipe.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(grappleRecipe);
    }
} 