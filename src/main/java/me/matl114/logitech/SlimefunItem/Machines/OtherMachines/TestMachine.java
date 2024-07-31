package me.matl114.logitech.SlimefunItem.Machines.OtherMachines;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.matl114.logitech.SlimefunItem.AddGroups;
import me.matl114.logitech.Utils.AddUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public class TestMachine extends SlimefunItem implements EnergyNetProvider {
    public static int ENERGY_THRESHOLD=1000;
    public static int BUFFER=11451419;

    public TestMachine(){
        super(AddGroups.author,new SlimefunItemStack("TESTER_CLASS",new CustomItemStack(Material.BEDROCK,"泌阳得测试类"),"BYD测试类","BYD测试IDEA"), RecipeType.NULL, AddUtils.NULL_RECIPE);

    }
    public EnergyNetComponentType getEnergyComponentType(){

            return EnergyNetComponentType.GENERATOR;



    }
    public int getCapacity(){
        return this.BUFFER;
    }
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data){
        return  AddUtils.random(this.ENERGY_THRESHOLD)-500;
    }

}
