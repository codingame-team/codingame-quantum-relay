package com.codingame.game;

import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Line;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;

public class Drone {
    public static final int LINK_NONE = -2;
    public static final int LINK_BASE = -1;

    private final int id;
    private final Player owner;
    private int x;
    private int y;
    private int linkTarget = LINK_NONE;
    private int cooldown = 0;

    // Turn command state
    private int moveTargetX;
    private int moveTargetY;
    private boolean explicitLink = false;
    private int requestedLinkTarget = LINK_NONE;
    private boolean overchargedThisTurn = false;
    private boolean interferedThisTurn = false;
    private int effectiveCapacity = 0;
    private String message = "";

    // Graphics
    private Group group;
    private Sprite sprite;
    private Sprite overchargeSprite;
    private Text label;
    private Line beamLine;
    private Line harvestLine;
    private Sprite interferenceSprite;

    public Drone(int id, Player owner, int x, int y) {
        this.id = id;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.moveTargetX = x;
        this.moveTargetY = y;
    }

    public void resetTurnCommand() {
        this.moveTargetX = this.x;
        this.moveTargetY = this.y;
        this.explicitLink = false;
        this.requestedLinkTarget = LINK_NONE;
        this.overchargedThisTurn = false;
        this.interferedThisTurn = false;
        this.effectiveCapacity = 0;
        this.message = "";
    }

    public int getId() {
        return id;
    }

    public Player getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(int linkTarget) {
        this.linkTarget = linkTarget;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = Math.max(0, cooldown);
    }

    public int getMoveTargetX() {
        return moveTargetX;
    }

    public int getMoveTargetY() {
        return moveTargetY;
    }

    public void setMoveTarget(int tx, int ty) {
        this.moveTargetX = tx;
        this.moveTargetY = ty;
    }

    public boolean hasExplicitLink() {
        return explicitLink;
    }

    public int getRequestedLinkTarget() {
        return requestedLinkTarget;
    }

    public void setRequestedLink(int targetId) {
        this.explicitLink = true;
        this.requestedLinkTarget = targetId;
    }

    public boolean isOverchargedThisTurn() {
        return overchargedThisTurn;
    }

    public void setOverchargedThisTurn(boolean overchargedThisTurn) {
        this.overchargedThisTurn = overchargedThisTurn;
    }

    public boolean isInterferedThisTurn() {
        return interferedThisTurn;
    }

    public void setInterferedThisTurn(boolean interferedThisTurn) {
        this.interferedThisTurn = interferedThisTurn;
    }

    public int getEffectiveCapacity() {
        return effectiveCapacity;
    }

    public void setEffectiveCapacity(int effectiveCapacity) {
        this.effectiveCapacity = effectiveCapacity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (message != null && message.length() > 18) {
            this.message = message.substring(0, 18);
        } else {
            this.message = message == null ? "" : message;
        }
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

    public Sprite getOverchargeSprite() {
        return overchargeSprite;
    }

    public void setOverchargeSprite(Sprite overchargeSprite) {
        this.overchargeSprite = overchargeSprite;
    }

    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
    }

    public Line getBeamLine() {
        return beamLine;
    }

    public void setBeamLine(Line beamLine) {
        this.beamLine = beamLine;
    }

    public Line getHarvestLine() {
        return harvestLine;
    }

    public void setHarvestLine(Line harvestLine) {
        this.harvestLine = harvestLine;
    }

    public Sprite getInterferenceSprite() {
        return interferenceSprite;
    }

    public void setInterferenceSprite(Sprite interferenceSprite) {
        this.interferenceSprite = interferenceSprite;
    }
}
