package com.codingame.game;

import com.codingame.gameengine.module.entities.Circle;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;

public class Pulsar {
    private final int id;
    private final int x;
    private final int y;
    private int energy;
    private final int maxEnergy;
    private final int regenRate;

    private Group group;
    private Sprite sprite;
    private Circle rangeCircle;
    private Text energyText;

    public Pulsar(int id, int x, int y, int energy, int maxEnergy, int regenRate) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.regenRate = regenRate;
    }

    public void regenerate() {
        this.energy = Math.min(maxEnergy, this.energy + regenRate);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, energy);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getRegenRate() {
        return regenRate;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Circle getRangeCircle() {
        return rangeCircle;
    }

    public void setRangeCircle(Circle rangeCircle) {
        this.rangeCircle = rangeCircle;
    }

    public Text getEnergyText() {
        return energyText;
    }

    public void setEnergyText(Text energyText) {
        this.energyText = energyText;
    }
}
