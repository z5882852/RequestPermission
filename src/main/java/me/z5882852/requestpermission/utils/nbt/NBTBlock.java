package me.z5882852.requestpermission.utils.nbt;


import net.minecraft.server.v1_12_R1.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;


public class NBTBlock {
    public NBTBlock() {

    }

    public static String getBlockNBT(Block block) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        net.minecraft.server.v1_12_R1.World nmsWorld = craftWorld.getHandle();
        BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
        TileEntity tileEntity = nmsWorld.getTileEntity(blockPosition);
        if (tileEntity != null) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.save(nbtTagCompound);
            return nbtTagCompound.toString();
        }
        return null;
    }

    public static String getBlockTargetNBTString(Block block, String targetKey) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        net.minecraft.server.v1_12_R1.World nmsWorld = craftWorld.getHandle();
        BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
        TileEntity tileEntity = nmsWorld.getTileEntity(blockPosition);
        if (tileEntity != null) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.save(nbtTagCompound);
            return nbtTagCompound.getString(targetKey);
        }
        return null;
    }

    public static int getBlockTargetNBTInt(Block block, String targetKey) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        net.minecraft.server.v1_12_R1.World nmsWorld = craftWorld.getHandle();
        BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
        TileEntity tileEntity = nmsWorld.getTileEntity(blockPosition);
        if (tileEntity != null) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.save(nbtTagCompound);
            return nbtTagCompound.getInt(targetKey);
        }
        return -1;
    }
}
