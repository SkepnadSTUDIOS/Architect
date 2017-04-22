package li.cil.architect.common.init;

import li.cil.architect.common.ProxyCommon;
import li.cil.architect.common.config.Constants;
import li.cil.architect.common.item.ItemBlueprint;
import li.cil.architect.common.item.ItemProviderFluid;
import li.cil.architect.common.item.ItemProviderItem;
import li.cil.architect.common.item.ItemSketch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.function.Predicate;

/**
 * Manages setup, registration and lookup of items.
 */
public final class Items {
    public static Item sketch;
    public static Item blueprint;
    public static Item providerItem;
    public static Item providerFluid;

    // --------------------------------------------------------------------- //

    public static boolean isSketch(final ItemStack stack) {
        return isItem(stack, sketch);
    }

    public static boolean isBlueprint(final ItemStack stack) {
        return isItem(stack, blueprint);
    }

    public static boolean isItemProvider(final ItemStack stack) {
        return isItem(stack, providerItem);
    }

    public static boolean isFluidProvider(final ItemStack stack) {
        return isItem(stack, providerFluid);
    }

    public static boolean isProvider(final ItemStack stack) {
        return isItemProvider(stack) || isFluidProvider(stack);
    }

    public static ItemStack getHeldItem(final EntityPlayer player, final Predicate<ItemStack> filter) {
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (filter.test(stack)) {
            return stack;
        }
        stack = player.getHeldItem(EnumHand.OFF_HAND);
        if (filter.test(stack)) {
            return stack;
        }
        return ItemStack.EMPTY;
    }

    // --------------------------------------------------------------------- //

    public static void register(final ProxyCommon proxy) {
        sketch = proxy.registerItem(Constants.NAME_ITEM_SKETCH, ItemSketch::new);
        blueprint = proxy.registerItem(Constants.NAME_ITEM_BLUEPRINT, ItemBlueprint::new);
        providerItem = proxy.registerItem(Constants.NAME_ITEM_PROVIDER_ITEM, ItemProviderItem::new);
        providerFluid = proxy.registerItem(Constants.NAME_ITEM_PROVIDER_FLUID, ItemProviderFluid::new);
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(sketch, 1),
                "LEL",
                "PPS",
                "LLL",
                'E', "enderpearl",
                'P', "paper",
                'L', "leather",
                'S', "string"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(providerItem, 1),
                "IEI",
                "QHQ",
                "ITI",
                'I', "ingotIron",
                'E', "enderpearl",
                'Q', "gemQuartz",
                'T', Blocks.TRAPDOOR,
                'H', Blocks.HOPPER));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(providerFluid, 1),
                "IEI",
                "QPQ",
                "ITI",
                'I', "ingotIron",
                'E', "enderpearl",
                'Q', "gemQuartz",
                'T', Blocks.TRAPDOOR,
                'P', Blocks.PISTON));
    }

    // --------------------------------------------------------------------- //

    private static boolean isItem(final ItemStack stack, final Item item) {
        return !stack.isEmpty() && stack.getItem() == item;
    }

    // --------------------------------------------------------------------- //

    private Items() {
    }
}
