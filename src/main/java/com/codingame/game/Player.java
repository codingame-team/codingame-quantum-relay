package com.codingame.game;

import java.util.ArrayList;
import java.util.List;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.module.entities.Text;

public class Player extends AbstractMultiplayerPlayer {
    private int baseX;
    private int baseY;
    private final List<Drone> drones = new ArrayList<>();
    private Text scoreText;
    private Text flowText;
    private int lastTurnFlow;

    @Override
    public int getExpectedOutputLines() {
        return Math.max(1, drones.size());
    }

    public int getBaseX() {
        return baseX;
    }

    public void setBasePosition(int x, int y) {
        this.baseX = x;
        this.baseY = y;
    }

    public int getBaseY() {
        return baseY;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void addDrone(Drone d) {
        drones.add(d);
    }

    public Text getScoreText() {
        return scoreText;
    }

    public void setScoreText(Text scoreText) {
        this.scoreText = scoreText;
    }

    public Text getFlowText() {
        return flowText;
    }

    public void setFlowText(Text flowText) {
        this.flowText = flowText;
    }

    public int getLastTurnFlow() {
        return lastTurnFlow;
    }

    public void setLastTurnFlow(int lastTurnFlow) {
        this.lastTurnFlow = lastTurnFlow;
    }
}
