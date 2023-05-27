package me.z5882852.requestpermission.utils.nbt;


import net.minecraft.server.v1_12_R1.*;
import org.bukkit.inventory.ItemStack;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;


public class NBTItem {
    public NBTItem() {

    }

    public static String getItemNBT(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        if (nmsItemStack.hasTag()) {
            return nmsItemStack.getTag().toString();
        }
        return null;
    }

    public static ItemStack setItemNBT(ItemStack itemStack, String nbtString) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound;
        try {
            nbtTagCompound = MojangsonParser.parse(nbtString);
        } catch (MojangsonParseException e) {
            e.printStackTrace();
            return itemStack;
        }
        nmsItemStack.setTag(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static ItemStack setItemNBT(ItemStack itemStack, String key, String value) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }
        nbtTagCompound.setString(key, value);
        nmsItemStack.setTag(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static ItemStack setItemNBT(ItemStack itemStack, String key, int value) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }
        nbtTagCompound.setInt(key, value);
        nmsItemStack.setTag(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }
}
