package me.z5882852.requestpermission.utils;

import me.z5882852.requestpermission.utils.json.Json;
import org.bukkit.inventory.ItemStack;

public class Item {
    public static String getItemId(ItemStack itemStack) {
        return itemStack.getType().toString() + ":" + String.valueOf(itemStack.getData().getData());
    }

    public static String getItemId(String itemData) {
        ItemStack itemStack = getItemStack(itemData);
        return itemStack.getType().toString() + ":" + String.valueOf(itemStack.getData().getData());
    }

    public static String getItemData(ItemStack itemStack) {
        return  Json.mapToJsonString(itemStack.serialize());
    }

    public static ItemStack getItemStack(String itemData) {
        return ItemStack.deserialize(Json.jsonStringToMap(itemData));
    }


}
