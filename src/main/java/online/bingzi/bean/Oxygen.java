package online.bingzi.bean;

import online.bingzi.util.ToolsKt;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 仓库物品存储
 */
public class Oxygen implements Serializable {
    /**
     * 玩家名称
     */
    private String username;
    /**
     * 物品集合
     */
    private ArrayList<ItemStack> itemStacks;
    /**
     * 已解锁的页数
     */
    private int count;

    public Oxygen() {
    }

    public Oxygen(String username) {
        this();
        this.username = username;
    }

    public Oxygen(String username, ArrayList<ItemStack> itemStacks) {
        this(username);
        this.itemStacks = itemStacks;
    }

    @SuppressWarnings("unchecked")
    public Oxygen(String username, String itemStacks) {
        this(username);
        this.itemStacks = (ArrayList<ItemStack>) ToolsKt.fromSerialization(itemStacks);
    }

    public Oxygen(String username, ArrayList<ItemStack> itemStacks, int count) {
        this(username, itemStacks);
        this.count = count;
    }

    public Oxygen(String username, String itemStacks, int count) {
        this(username, itemStacks);
        this.count = count;
    }

    /**
     * 已解锁页数+1
     */
    public void autoAddCount() {
        this.count++;
    }

    /**
     * 增加已解锁的页数
     */
    public void addCount(int count) {
        this.count += count;
    }

    /**
     * 获取解锁的页数
     *
     * @return 页数
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置已解锁的页数
     *
     * @param count 已解锁页数
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 一共存储了多少物品
     *
     * @return 个数
     */
    public Integer getItemStackCount() {
        return this.itemStacks.size();
    }

    /**
     * 获取玩名
     *
     * @return 玩家名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置玩家名
     *
     * @param username 玩家名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 增加物品(列表)
     *
     * @param stacks 物品列表
     */
    public void addItemStacks(ArrayList<ItemStack> stacks) {
        this.itemStacks.addAll(stacks);
    }

    /**
     * 增加物品
     *
     * @param stack 物品
     */
    public void addItemStack(ItemStack stack) {
        this.itemStacks.add(stack);
    }

    /**
     * 删除物品
     *
     * @param stack 物品
     * @return 是否成功
     */
    public Boolean deleteItemStack(ItemStack stack) {
        if (this.itemStacks.contains(stack)) {
            this.itemStacks.remove(stack);
            // 成功删除
            return true;
        } else {
            // 删除失败
            return false;
        }
    }

    /**
     * 删除物品(列表)
     *
     * @param itemStacks 物品列表
     * @return 是否成功
     */
    public Boolean deleteItemStacks(ArrayList<ItemStack> itemStacks) {
        if (this.itemStacks.containsAll(itemStacks)) {
            this.itemStacks.removeAll(itemStacks);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取物品列表
     *
     * @return 物品列表
     */
    public ArrayList<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    /**
     * 设置物品列表
     *
     * @param itemStacks 物品列表
     */
    public void setItemStacks(ArrayList<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    /**
     * 获取仓库页数
     *
     * @return 已用页数
     */
    public Double getPage() {
        return (itemStacks.size() + 1) / 21.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oxygen oxygen = (Oxygen) o;
        return getUsername().equals(oxygen.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return String.format("玩家 %s 仓库持有 %s 个物品且已解锁 %s 页。", username, itemStacks.size(), count);
    }
}
