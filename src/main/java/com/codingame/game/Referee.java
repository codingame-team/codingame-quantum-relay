package com.codingame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.Circle;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Line;
import com.codingame.gameengine.module.entities.Rectangle;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.codingame.gameengine.module.toggle.ToggleModule;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.google.inject.Inject;

public class Referee extends AbstractReferee {
    public static final int SECTOR_WIDTH = 1600;
    public static final int SECTOR_HEIGHT = 900;
    public static final int VIEW_OFFSET_X = 160;
    public static final int VIEW_OFFSET_Y = 140;

    public static final int MAX_TURNS = 100;
    public static final int DRONE_SPEED = 120;
    public static final int HARVEST_RANGE = 200;
    public static final int MAX_LINK_RANGE = 480;
    public static final int BASE_BANDWIDTH = 6;
    public static final int OVERCHARGE_BANDWIDTH = 12;
    public static final int OVERCHARGE_COOLDOWN = 2;

    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private TooltipModule tooltipModule;
    @Inject private EndScreenModule endScreenModule;
    @Inject private ToggleModule toggleModule;

    private final List<Pulsar> pulsars = new ArrayList<>();
    private final List<Drone> allDrones = new ArrayList<>();
    private final Map<Integer, Drone> droneById = new HashMap<>();
    private Text turnText;
    private int leagueLevel;

    private int toViewX(int x) {
        return VIEW_OFFSET_X + x;
    }

    private int toViewY(int y) {
        return VIEW_OFFSET_Y + y;
    }

    @Override
    public void init() {
        Random rng = gameManager.getRandom();
        leagueLevel = Math.max(1, gameManager.getLeagueLevel());

        gameManager.setMaxTurns(MAX_TURNS);
        gameManager.setFirstTurnMaxTime(1000);
        gameManager.setTurnMaxTime(50);
        gameManager.setFrameDuration(550);

        Player p0 = gameManager.getPlayer(0);
        Player p1 = gameManager.getPlayer(1);
        p0.setBasePosition(140, 450);
        p1.setBasePosition(SECTOR_WIDTH - 140, 450);

        generatePulsars(rng);
        spawnDrones(p0, p1);
        initGraphics();

        // Send initialization inputs (always from each player's perspective: myBase first, oppBase second)
        for (Player player : gameManager.getActivePlayers()) {
            Player opp = gameManager.getPlayer(1 - player.getIndex());
            player.sendInputLine(String.format("%d %d %d", SECTOR_WIDTH, SECTOR_HEIGHT, leagueLevel));
            player.sendInputLine(String.format("%d %d %d %d",
                player.getBaseX(), player.getBaseY(), opp.getBaseX(), opp.getBaseY()));
        }
    }

    private void generatePulsars(Random rng) {
        pulsars.clear();
        int pid = 0;
        // Central high-capacity Megapulsar
        pulsars.add(new Pulsar(pid++, SECTOR_WIDTH / 2, SECTOR_HEIGHT / 2, 110, 130, 5));

        // Inner contested pair of Pulsars near the center midline
        int x1 = 650 + rng.nextInt(90);
        int y1 = 190 + rng.nextInt(110);
        pulsars.add(new Pulsar(pid++, x1, y1, 80, 95, 4));
        pulsars.add(new Pulsar(pid++, SECTOR_WIDTH - x1, SECTOR_HEIGHT - y1, 80, 95, 4));

        // Outer accessible pair of Pulsars
        int x2 = 460 + rng.nextInt(110);
        int y2 = 590 + rng.nextInt(120);
        pulsars.add(new Pulsar(pid++, x2, y2, 60, 75, 3));
        pulsars.add(new Pulsar(pid++, SECTOR_WIDTH - x2, SECTOR_HEIGHT - y2, 60, 75, 3));
    }

    private void spawnDrones(Player p0, Player p1) {
        int dronesPerPlayer = (leagueLevel == 1) ? 2 : 3;
        int[][] offsets = {
            {110, -130},
            {110, 130},
            {160, 0}
        };
        int idCounter = 0;
        for (int i = 0; i < dronesPerPlayer; i++) {
            int x0 = p0.getBaseX() + offsets[i][0];
            int y0 = p0.getBaseY() + offsets[i][1];
            Drone d0 = new Drone(idCounter++, p0, x0, y0);
            p0.addDrone(d0);
            allDrones.add(d0);
            droneById.put(d0.getId(), d0);
        }
        for (int i = 0; i < dronesPerPlayer; i++) {
            int x1 = p1.getBaseX() - offsets[i][0];
            int y1 = p1.getBaseY() - offsets[i][1];
            Drone d1 = new Drone(idCounter++, p1, x1, y1);
            p1.addDrone(d1);
            allDrones.add(d1);
            droneById.put(d1.getId(), d1);
        }
    }

    private void initGraphics() {
        // Space background
        graphicEntityModule.createSprite()
            .setImage("background.jpg")
            .setBaseWidth(1920)
            .setBaseHeight(1080)
            .setZIndex(-20);

        // Sector border frame
        graphicEntityModule.createRectangle()
            .setX(VIEW_OFFSET_X)
            .setY(VIEW_OFFSET_Y)
            .setWidth(SECTOR_WIDTH)
            .setHeight(SECTOR_HEIGHT)
            .setFillColor(0x070b16)
            .setAlpha(0.65)
            .setLineWidth(4)
            .setLineColor(0x2e446e)
            .setZIndex(-15);

        // Header HUD
        graphicEntityModule.createText("QUANTUM RELAY")
            .setX(960)
            .setY(40)
            .setAnchor(0.5)
            .setFontFamily("Lato")
            .setFontSize(44)
            .setFillColor(0xffffff)
            .setStrokeThickness(4)
            .setStrokeColor(0x000000)
            .setZIndex(30);

        turnText = graphicEntityModule.createText("TURN 0 / " + MAX_TURNS)
            .setX(960)
            .setY(92)
            .setAnchor(0.5)
            .setFontFamily("Lato")
            .setFontSize(36)
            .setFillColor(0xb8d8ff)
            .setZIndex(30);

        // Player HUDs & Motherships
        for (Player p : gameManager.getPlayers()) {
            int idx = p.getIndex();
            int hudX = idx == 0 ? 340 : 1580;

            graphicEntityModule.createRectangle()
                .setX(hudX - 240)
                .setY(16)
                .setWidth(480)
                .setHeight(104)
                .setFillColor(0x111a2e)
                .setAlpha(0.88)
                .setLineWidth(3)
                .setLineColor(p.getColorToken())
                .setZIndex(25);

            graphicEntityModule.createSprite()
                .setImage(p.getAvatarToken())
                .setX(hudX - 185)
                .setY(68)
                .setAnchor(0.5)
                .setBaseWidth(76)
                .setBaseHeight(76)
                .setZIndex(26);

            graphicEntityModule.createText(p.getNicknameToken())
                .setX(hudX - 125)
                .setY(46)
                .setAnchorX(0)
                .setAnchorY(0.5)
                .setFontFamily("Lato")
                .setFontSize(36)
                .setFillColor(p.getColorToken())
                .setZIndex(26);

            Text scoreTxt = graphicEntityModule.createText("0 ⚡")
                .setX(hudX - 125)
                .setY(90)
                .setAnchorX(0)
                .setAnchorY(0.5)
                .setFontFamily("Lato")
                .setFontSize(38)
                .setFillColor(0xffffff)
                .setZIndex(26);
            p.setScoreText(scoreTxt);

            Text flowTxt = graphicEntityModule.createText("+0/t")
                .setX(hudX + 135)
                .setY(90)
                .setAnchorX(0.5)
                .setAnchorY(0.5)
                .setFontFamily("Lato")
                .setFontSize(36)
                .setFillColor(0x7ee787)
                .setZIndex(26);
            p.setFlowText(flowTxt);

            // Mothership sprite on sector map
            String baseImg = idx == 0 ? "base_p0.png" : "base_p1.png";
            Sprite baseSprite = graphicEntityModule.createSprite()
                .setImage(baseImg)
                .setX(toViewX(p.getBaseX()))
                .setY(toViewY(p.getBaseY()))
                .setAnchor(0.5)
                .setBaseWidth(84)
                .setBaseHeight(84)
                .setZIndex(6);

            Circle baseLinkRange = graphicEntityModule.createCircle()
                .setX(toViewX(p.getBaseX()))
                .setY(toViewY(p.getBaseY()))
                .setRadius(MAX_LINK_RANGE)
                .setFillAlpha(0.0)
                .setLineWidth(2)
                .setLineColor(p.getColorToken())
                .setLineAlpha(0.28)
                .setZIndex(-5);
            toggleModule.displayOnToggleState(baseLinkRange, "debugRanges", true);

            tooltipModule.setTooltipText(baseSprite,
                String.format("Mothership (Player %d)\nID: -1\nPos: (%d, %d)",
                    idx, p.getBaseX(), p.getBaseY()));
        }

        // Pulsars
        for (Pulsar pulsar : pulsars) {
            int vx = toViewX(pulsar.getX());
            int vy = toViewY(pulsar.getY());

            Circle rangeCircle = graphicEntityModule.createCircle()
                .setX(vx)
                .setY(vy)
                .setRadius(HARVEST_RANGE)
                .setFillColor(0x2ea043)
                .setFillAlpha(0.07)
                .setLineWidth(2)
                .setLineColor(0x3fb950)
                .setLineAlpha(0.42)
                .setZIndex(-4);
            toggleModule.displayOnToggleState(rangeCircle, "debugRanges", true);

            Sprite ps = graphicEntityModule.createSprite()
                .setImage("pulsar.png")
                .setAnchor(0.5)
                .setBaseWidth(66)
                .setBaseHeight(66);

            Text eTxt = graphicEntityModule.createText(String.valueOf(pulsar.getEnergy()))
                .setY(-48)
                .setAnchor(0.5)
                .setFontFamily("Lato")
                .setFontSize(36)
                .setFillColor(0x7ee787)
                .setStrokeThickness(3)
                .setStrokeColor(0x000000);

            Group pg = graphicEntityModule.createGroup(ps, eTxt)
                .setX(vx)
                .setY(vy)
                .setZIndex(5);

            pulsar.setRangeCircle(rangeCircle);
            pulsar.setSprite(ps);
            pulsar.setEnergyText(eTxt);
            pulsar.setGroup(pg);
            updatePulsarTooltip(pulsar);
        }

        // Drones & Relay Beams
        for (Drone d : allDrones) {
            int color = d.getOwner().getColorToken();
            int vx = toViewX(d.getX());
            int vy = toViewY(d.getY());

            Line beam = graphicEntityModule.createLine()
                .setX(vx)
                .setY(vy)
                .setX2(vx)
                .setY2(vy)
                .setLineWidth(6)
                .setLineColor(color)
                .setAlpha(0.85)
                .setVisible(false)
                .setZIndex(3);
            d.setBeamLine(beam);

            Line harvest = graphicEntityModule.createLine()
                .setX(vx)
                .setY(vy)
                .setX2(vx)
                .setY2(vy)
                .setLineWidth(4)
                .setLineColor(0x7ee787)
                .setAlpha(0.80)
                .setVisible(false)
                .setZIndex(2);
            d.setHarvestLine(harvest);

            Sprite interf = graphicEntityModule.createSprite()
                .setImage("interference.png")
                .setAnchor(0.5)
                .setBaseWidth(58)
                .setBaseHeight(58)
                .setVisible(false)
                .setZIndex(18);
            d.setInterferenceSprite(interf);

            Sprite ov = graphicEntityModule.createSprite()
                .setImage("overcharge.png")
                .setAnchor(0.5)
                .setBaseWidth(74)
                .setBaseHeight(74)
                .setVisible(false);

            String droneImg = d.getOwner().getIndex() == 0 ? "drone_p0.png" : "drone_p1.png";
            Sprite ds = graphicEntityModule.createSprite()
                .setImage(droneImg)
                .setAnchor(0.5)
                .setBaseWidth(52)
                .setBaseHeight(52);

            Text lbl = graphicEntityModule.createText(String.valueOf(d.getId()))
                .setY(-36)
                .setAnchor(0.5)
                .setFontFamily("Lato")
                .setFontSize(36)
                .setFillColor(0xffffff)
                .setStrokeThickness(3)
                .setStrokeColor(0x000000);
            toggleModule.displayOnToggleState(lbl, "debugRanges", true);

            Group dg = graphicEntityModule.createGroup(ov, ds, lbl)
                .setX(vx)
                .setY(vy)
                .setZIndex(10);

            d.setOverchargeSprite(ov);
            d.setSprite(ds);
            d.setLabel(lbl);
            d.setGroup(dg);
            updateDroneTooltip(d);
        }
    }

    private void updatePulsarTooltip(Pulsar p) {
        tooltipModule.setTooltipText(p.getGroup(),
            String.format("Pulsar %d\nPos: (%d, %d)\nEnergy: %d / %d (+%d/turn)",
                p.getId(), p.getX(), p.getY(), p.getEnergy(), p.getMaxEnergy(), p.getRegenRate()));
    }

    private void updateDroneTooltip(Drone d) {
        tooltipModule.setTooltipText(d.getGroup(),
            String.format("Drone %d (Player %d)\nPos: (%d, %d)\nLink: %d | Cap: %d\nCooldown: %d",
                d.getId(), d.getOwner().getIndex(), d.getX(), d.getY(),
                d.getLinkTarget(), d.getEffectiveCapacity(), d.getCooldown()));
    }

    @Override
    public void gameTurn(int turn) {
        turnText.setText("TURN " + turn + " / " + MAX_TURNS);

        for (Drone d : allDrones) {
            d.setCooldown(d.getCooldown() - 1);
            d.resetTurnCommand();
        }

        // 1. Send inputs to each active player (Owner is ALWAYS 0 for self, 1 for opponent)
        for (Player player : gameManager.getActivePlayers()) {
            sendTurnInput(player);
            player.execute();
        }

        // 2. Read and parse outputs
        for (Player player : gameManager.getActivePlayers()) {
            try {
                List<String> outputs = player.getOutputs();
                parsePlayerOutputs(player, outputs);
            } catch (TimeoutException e) {
                disqualifyPlayer(player, "timed out!");
            }
        }

        if (gameManager.getActivePlayers().size() < 2) {
            gameManager.endGame();
            return;
        }

        // 3. Resolve Drone Movement
        resolveMovement();

        // 4. Resolve Relay Links, Overcharges, and Geometric Beam Interference
        resolveLinksAndInterference();

        // 5. Resolve Quantum Energy Flow from Pulsars through Relay Trees to Motherships
        resolveEnergyFlow();

        // 6. Regenerate Pulsars, Refresh Visuals & Check Early Victory
        for (Pulsar p : pulsars) {
            p.regenerate();
        }
        refreshGraphics();
        checkEarlyEnd(turn);
    }

    private void sendTurnInput(Player player) {
        int myIdx = player.getIndex();
        player.sendInputLine(String.valueOf(pulsars.size()));
        for (Pulsar p : pulsars) {
            player.sendInputLine(String.format("%d %d %d %d",
                p.getId(), p.getX(), p.getY(), p.getEnergy()));
        }

        List<Drone> ordered = new ArrayList<>();
        for (Drone d : allDrones) {
            if (d.getOwner().getIndex() == myIdx) ordered.add(d);
        }
        for (Drone d : allDrones) {
            if (d.getOwner().getIndex() != myIdx) ordered.add(d);
        }

        player.sendInputLine(String.valueOf(ordered.size()));
        for (Drone d : ordered) {
            int relOwner = (d.getOwner().getIndex() == myIdx) ? 0 : 1;
            player.sendInputLine(String.format("%d %d %d %d %d %d",
                d.getId(), relOwner, d.getX(), d.getY(), d.getLinkTarget(), d.getCooldown()));
        }
    }

    private void parsePlayerOutputs(Player player, List<Drone> myDrones, List<String> outputs) {
        for (int i = 0; i < myDrones.size() && i < outputs.size(); i++) {
            Drone drone = myDrones.get(i);
            String line = outputs.get(i).trim();
            if (line.isEmpty()) {
                disqualifyPlayer(player, "sent an empty command line!");
                return;
            }
            String[] tokens = line.split("\\s+");
            int cursor = 0;

            try {
                String cmd = tokens[cursor++].toUpperCase();
                if ("MOVE".equals(cmd)) {
                    if (cursor + 1 >= tokens.length) {
                        disqualifyPlayer(player, "invalid MOVE syntax: " + line);
                        return;
                    }
                    int tx = Integer.parseInt(tokens[cursor++]);
                    int ty = Integer.parseInt(tokens[cursor++]);
                    tx = Math.max(40, Math.min(SECTOR_WIDTH - 40, tx));
                    ty = Math.max(40, Math.min(SECTOR_HEIGHT - 40, ty));
                    drone.setMoveTarget(tx, ty);
                } else if ("OVERCHARGE".equals(cmd)) {
                    if (cursor >= tokens.length) {
                        disqualifyPlayer(player, "OVERCHARGE requires a targetId: " + line);
                        return;
                    }
                    int targetId = Integer.parseInt(tokens[cursor++]);
                    drone.setRequestedLink(targetId);
                    if (leagueLevel >= 2) {
                        if (drone.getCooldown() == 0) {
                            drone.setOverchargedThisTurn(true);
                            drone.setCooldown(OVERCHARGE_COOLDOWN);
                        } else {
                            gameManager.addToGameSummary(player.getNicknameToken()
                                + ": Drone " + drone.getId() + " OVERCHARGE on cooldown.");
                        }
                    } else {
                        gameManager.addToGameSummary(player.getNicknameToken()
                            + ": OVERCHARGE is not unlocked in this league.");
                    }
                } else if ("WAIT".equals(cmd)) {
                    drone.setMoveTarget(drone.getX(), drone.getY());
                } else {
                    disqualifyPlayer(player, "unrecognized command '" + cmd + "'");
                    return;
                }

                // Optional secondary action: LINK <targetId> or MSG <text>
                while (cursor < tokens.length) {
                    String sub = tokens[cursor++].toUpperCase();
                    if ("LINK".equals(sub)) {
                        if (cursor >= tokens.length) {
                            disqualifyPlayer(player, "invalid LINK syntax: " + line);
                            return;
                        }
                        int targetId = Integer.parseInt(tokens[cursor++]);
                        drone.setRequestedLink(targetId);
                    } else if ("MSG".equals(sub)) {
                        StringBuilder sb = new StringBuilder();
                        while (cursor < tokens.length) {
                            if (sb.length() > 0) sb.append(' ');
                            sb.append(tokens[cursor++]);
                        }
                        drone.setMessage(sb.toString());
                    } else {
                        disqualifyPlayer(player, "unrecognized token '" + sub + "'");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                disqualifyPlayer(player, "expected integer parameter in '" + line + "'");
                return;
            }
        }
    }

    private void parsePlayerOutputs(Player player, List<String> outputs) {
        parsePlayerOutputs(player, player.getDrones(), outputs);
    }

    private void disqualifyPlayer(Player player, String reason) {
        player.deactivate(player.getNicknameToken() + " " + reason);
        player.setScore(-1);
        gameManager.addTooltip(player, player.getNicknameToken() + " disqualified: " + reason);
        gameManager.addToGameSummary(GameManager.formatErrorMessage(player.getNicknameToken() + " " + reason));
    }

    private void resolveMovement() {
        for (Drone d : allDrones) {
            if (d.isOverchargedThisTurn()) {
                // Overcharging drone holds position to stabilize high-energy beam
                continue;
            }
            int tx = d.getMoveTargetX();
            int ty = d.getMoveTargetY();
            double dist = Geometry.dist(d.getX(), d.getY(), tx, ty);
            if (dist > 1e-3) {
                double angle = Math.atan2(ty - d.getY(), tx - d.getX());
                d.getSprite().setRotation(angle);
                if (dist <= DRONE_SPEED) {
                    d.setX(tx);
                    d.setY(ty);
                } else {
                    double ratio = DRONE_SPEED / dist;
                    int nx = (int) Math.round(d.getX() + (tx - d.getX()) * ratio);
                    int ny = (int) Math.round(d.getY() + (ty - d.getY()) * ratio);
                    d.setX(nx);
                    d.setY(ny);
                }
                d.getGroup().setX(toViewX(d.getX()), Curve.EASE_IN_AND_OUT)
                            .setY(toViewY(d.getY()), Curve.EASE_IN_AND_OUT);
            }
        }
    }

    private int[] getLinkEndpoint(Drone d, int targetId) {
        Player owner = d.getOwner();
        if (targetId == Drone.LINK_BASE) {
            return new int[]{owner.getBaseX(), owner.getBaseY()};
        }
        Drone target = droneById.get(targetId);
        if (target != null && target.getOwner() == owner && target.getId() != d.getId()) {
            return new int[]{target.getX(), target.getY()};
        }
        return null;
    }

    private void resolveLinksAndInterference() {
        // 1. Validate or auto-assign link targets
        for (Drone d : allDrones) {
            Player owner = d.getOwner();
            int chosenTarget = Drone.LINK_NONE;

            if (d.hasExplicitLink()) {
                int req = d.getRequestedLinkTarget();
                int[] ep = getLinkEndpoint(d, req);
                if (ep != null && Geometry.dist(d.getX(), d.getY(), ep[0], ep[1]) <= MAX_LINK_RANGE) {
                    chosenTarget = req;
                } else if (req != Drone.LINK_NONE) {
                    gameManager.addToGameSummary(owner.getNicknameToken()
                        + ": Drone " + d.getId() + " LINK target " + req + " out of range or invalid.");
                }
            } else {
                // Automatic intelligent relay link toward Mothership (noob-friendly default!)
                double distToBase = Geometry.dist(d.getX(), d.getY(), owner.getBaseX(), owner.getBaseY());
                if (distToBase <= MAX_LINK_RANGE) {
                    chosenTarget = Drone.LINK_BASE;
                } else {
                    double bestPeerDistToBase = distToBase;
                    for (Drone peer : owner.getDrones()) {
                        if (peer == d) continue;
                        double dToPeer = Geometry.dist(d.getX(), d.getY(), peer.getX(), peer.getY());
                        double peerToBase = Geometry.dist(peer.getX(), peer.getY(), owner.getBaseX(), owner.getBaseY());
                        if (dToPeer <= MAX_LINK_RANGE && peerToBase < bestPeerDistToBase) {
                            bestPeerDistToBase = peerToBase;
                            chosenTarget = peer.getId();
                        }
                    }
                }
            }

            d.setLinkTarget(chosenTarget);
            if (chosenTarget == Drone.LINK_NONE) {
                d.setEffectiveCapacity(0);
            } else {
                d.setEffectiveCapacity(d.isOverchargedThisTurn() ? OVERCHARGE_BANDWIDTH : BASE_BANDWIDTH);
            }
            d.getInterferenceSprite().setVisible(false);
        }

        // 2. Check Geometric Segment Intersection between opposing players' beams in League 3+
        if (leagueLevel >= 3) {
            List<Drone> p0Drones = gameManager.getPlayer(0).getDrones();
            List<Drone> p1Drones = gameManager.getPlayer(1).getDrones();

            for (Drone d0 : p0Drones) {
                int[] ep0 = getLinkEndpoint(d0, d0.getLinkTarget());
                if (ep0 == null) continue;

                for (Drone d1 : p1Drones) {
                    int[] ep1 = getLinkEndpoint(d1, d1.getLinkTarget());
                    if (ep1 == null) continue;

                    if (Geometry.segmentsIntersect(
                        d0.getX(), d0.getY(), ep0[0], ep0[1],
                        d1.getX(), d1.getY(), ep1[0], ep1[1]
                    )) {
                        d0.setInterferedThisTurn(true);
                        d1.setInterferedThisTurn(true);

                        int[] crossPt = Geometry.intersectionPoint(
                            d0.getX(), d0.getY(), ep0[0], ep0[1],
                            d1.getX(), d1.getY(), ep1[0], ep1[1]
                        );
                        d0.getInterferenceSprite()
                            .setX(toViewX(crossPt[0]))
                            .setY(toViewY(crossPt[1]))
                            .setVisible(true);

                        // Apply quantum interference bandwidth penalty
                        if (d0.isOverchargedThisTurn() && !d1.isOverchargedThisTurn()) {
                            d0.setEffectiveCapacity( Math.min(d0.getEffectiveCapacity(), BASE_BANDWIDTH));
                            d1.setEffectiveCapacity(0);
                        } else if (d1.isOverchargedThisTurn() && !d0.isOverchargedThisTurn()) {
                            d1.setEffectiveCapacity(Math.min(d1.getEffectiveCapacity(), BASE_BANDWIDTH));
                            d0.setEffectiveCapacity(0);
                        } else {
                            d0.setEffectiveCapacity(0);
                            d1.setEffectiveCapacity(0);
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds the cycle-free path from Drone d to its Mothership (-1) along active links.
     * Returns null if not connected to Mothership.
     */
    private List<Drone> getPathToMothership(Drone start) {
        List<Drone> path = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Drone curr = start;
        while (curr != null) {
            if (visited.contains(curr.getId()) || curr.getEffectiveCapacity() <= 0) {
                return null;
            }
            visited.add(curr.getId());
            path.add(curr);
            int target = curr.getLinkTarget();
            if (target == Drone.LINK_BASE) {
                return path;
            } else if (target >= 0) {
                curr = droneById.get(target);
            } else {
                return null;
            }
        }
        return null;
    }

    private void resolveEnergyFlow() {
        Map<Drone, Integer> remainingCap = new HashMap<>();
        for (Drone d : allDrones) {
            remainingCap.put(d, d.getEffectiveCapacity());
            d.getHarvestLine().setVisible(false);
        }

        for (Player p : gameManager.getPlayers()) {
            p.setLastTurnFlow(0);
        }

        // Route energy from each Pulsar through connected harvesting drones
        for (Pulsar pulsar : pulsars) {
            if (pulsar.getEnergy() <= 0) continue;

            for (Drone d : allDrones) {
                if (pulsar.getEnergy() <= 0) break;
                double dist = Geometry.dist(d.getX(), d.getY(), pulsar.getX(), pulsar.getY());
                if (dist > HARVEST_RANGE) continue;

                List<Drone> path = getPathToMothership(d);
                if (path == null) continue;

                int bottleneck = pulsar.getEnergy();
                for (Drone hop : path) {
                    bottleneck = Math.min(bottleneck, remainingCap.get(hop));
                }

                if (bottleneck > 0) {
                    pulsar.setEnergy(pulsar.getEnergy() - bottleneck);
                    for (Drone hop : path) {
                        remainingCap.put(hop, remainingCap.get(hop) - bottleneck);
                    }
                    Player owner = d.getOwner();
                    owner.setScore(owner.getScore() + bottleneck);
                    owner.setLastTurnFlow(owner.getLastTurnFlow() + bottleneck);

                    d.getHarvestLine()
                        .setX(toViewX(d.getX()))
                        .setY(toViewY(d.getY()))
                        .setX2(toViewX(pulsar.getX()))
                        .setY2(toViewY(pulsar.getY()))
                        .setVisible(true);
                }
            }
        }
    }

    private void refreshGraphics() {
        for (Player p : gameManager.getPlayers()) {
            p.getScoreText().setText(p.getScore() + " ⚡");
            p.getFlowText().setText("+" + p.getLastTurnFlow() + "/t");
        }
        for (Pulsar pulsar : pulsars) {
            pulsar.getEnergyText().setText(String.valueOf(pulsar.getEnergy()));
            updatePulsarTooltip(pulsar);
        }
        for (Drone d : allDrones) {
            d.getOverchargeSprite().setVisible(d.isOverchargedThisTurn());
            int[] ep = getLinkEndpoint(d, d.getLinkTarget());
            Line beam = d.getBeamLine();
            if (ep != null) {
                beam.setX(toViewX(d.getX()))
                    .setY(toViewY(d.getY()))
                    .setX2(toViewX(ep[0]))
                    .setY2(toViewY(ep[1]))
                    .setLineWidth(d.isOverchargedThisTurn() ? 10 : 6)
                    .setAlpha(d.getEffectiveCapacity() == 0 ? 0.30 : 0.90)
                    .setVisible(true);
            } else {
                beam.setVisible(false);
            }
            updateDroneTooltip(d);
        }
    }

    private void checkEarlyEnd(int turn) {
        Player p0 = gameManager.getPlayer(0);
        Player p1 = gameManager.getPlayer(1);
        int turnsLeft = MAX_TURNS - turn;
        int maxFlowPerTurn = p0.getDrones().size() * OVERCHARGE_BANDWIDTH;
        if (Math.abs(p0.getScore() - p1.getScore()) > turnsLeft * maxFlowPerTurn) {
            gameManager.addToGameSummary(GameManager.formatSuccessMessage(
                "Energy lead is mathematically insurmountable!"));
            gameManager.endGame();
        }
    }

    @Override
    public void onEnd() {
        int[] scores = gameManager.getPlayers().stream().mapToInt(Player::getScore).toArray();
        String[] texts = gameManager.getPlayers().stream()
            .map(p -> p.getScore() + " Energy Routed")
            .toArray(String[]::new);
        endScreenModule.setScores(scores, texts);
    }
}
