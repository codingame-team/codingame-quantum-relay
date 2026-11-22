<!-- LEAGUES level1 level2 level3 -->
<div id="statement_back" class="statement_back" style="display:none"></div>
<div class="statement-body">
  <!-- LEAGUE ALERT -->
  <!-- BEGIN level1 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Wood 2 League:</b> Command <b>2 Relay Drones</b> to harvest Pulsar energy and beam it back to your Mothership. If you omit <action>LINK</action>, drones automatically link toward your Mothership when within range!</p>
  </div>
  <!-- END -->
  <!-- BEGIN level2 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Wood 1 League:</b> You now command <b>3 Relay Drones</b> and unlock <action>OVERCHARGE targetId</action> (doubles beam capacity from <const>6</const> to <const>12</const> energy/turn, cooldown <const>2</const> turns)!</p>
  </div>
  <!-- END -->
  <!-- BEGIN level3 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Bronze+ League:</b> <b>Quantum Beam Interference</b> is now active! If two opposing laser beams geometrically cross each other, both are shorted out (<const>0</const> flow) unless one is <action>OVERCHARGE</action>d!</p>
  </div>
  <!-- END -->

  <!-- GOAL -->
  <div class="statement-section statement-goal">
    <h1><span class="icon icon-goal">&nbsp;</span><span>The Goal</span></h1>
    <div class="statement-goal-content">
      Route more quantum energy from deep-space <b>Pulsars</b> to your <b>Mothership</b> than your opponent over <const>100</const> turns.
    </div>
  </div>

  <!-- RULES -->
  <div class="statement-section statement-rules">
    <h1><span class="icon icon-rules">&nbsp;</span><span>Rules</span></h1>
    <div class="statement-rules-content">
      <p>The game takes place in a <const>1600</const>&times;<const>900</const> space sector containing two <b>Motherships</b> and <const>5</const> <b>Pulsars</b>:</p>
      <ul>
        <li><b>Drone Movement (<action>MOVE x y</action>):</b> A drone moves up to <const>120</const> units per turn toward (<var>x</var>, <var>y</var>).</li>
        <li><b>Laser Relay Links (<action>LINK targetId</action>):</b> Each drone can project one outgoing laser beam up to <const>480</const> units to either its Mothership (<var>targetId</var> = <const>-1</const>) or another friendly drone (<var>targetId</var> = <var>droneId</var>). Default bandwidth is <const>6</const> energy/turn.</li>
        <!-- BEGIN level2 level3 -->
        <li><b>Overcharge (<action>OVERCHARGE targetId</action>):</b> Requires <var>cooldown</var> = <const>0</const>. The drone holds its current position, links to <var>targetId</var>, and boosts its outgoing beam capacity to <const>12</const> energy/turn for this turn (sets <var>cooldown</var> to <const>2</const>).</li>
        <!-- END -->
        <!-- BEGIN level3 -->
        <li><b>Quantum Beam Interference:</b> If a player's active beam segment geometrically intersects an opponent's active beam segment:
          <ul>
            <li><b>Normal vs Normal (or Overcharged vs Overcharged):</b> Both beams short out (<const>0</const> effective bandwidth).</li>
            <li><b>Overcharged vs Normal:</b> The normal beam is severed (<const>0</const> bandwidth), while the Overcharged beam retains <const>6</const> bandwidth!</li>
          </ul>
        </li>
        <!-- END -->
        <li><b>Energy Harvesting &amp; Flow:</b> Any drone within <const>200</const> units of a Pulsar that has a valid relay path to its Mothership routes energy from that Pulsar to the Mothership, constrained by the minimum remaining bandwidth along its relay path. Pulsars regenerate <const>3</const> to <const>5</const> energy at the end of each turn.</li>
      </ul>

      <!-- VICTORY CONDITIONS -->
      <div class="statement-victory-conditions">
        <div class="icon victory"></div>
        <div class="blk">
          <div class="title">Victory Conditions</div>
          <div class="text">
            <ul style="padding-bottom:0">
              <li>You have routed more total energy to your Mothership than your opponent at the end of the game.</li>
            </ul>
          </div>
        </div>
      </div>

      <!-- DEFEAT CONDITIONS -->
      <div class="statement-lose-conditions">
        <div class="icon lose"></div>
        <div class="blk">
          <div class="title">Defeat Conditions</div>
          <div class="text">
            <ul style="padding-bottom:0">
              <li>You have routed less total energy to your Mothership than your opponent at the end of the game.</li>
              <li>Your program does not respond within the allotted time or outputs an unrecognized command.</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- PROTOCOL -->
  <div class="statement-section statement-protocol">
    <h1><span class="icon icon-protocol">&nbsp;</span><span>Game Input &amp; Output</span></h1>
    <div class="blk">
      <div class="title">Initialization Input</div>
      <div class="text">
        <p><span class="statement-lineno">Line 1:</span> 3 space-separated integers <var>width</var>, <var>height</var> and <var>leagueLevel</var>, the width and height of the space sector (<const>1600</const>&times;<const>900</const>) and the current league level (<const>1</const>, <const>2</const> or <const>3</const>).</p>
        <p><span class="statement-lineno">Line 2:</span> 4 space-separated integers <var>myBaseX</var>, <var>myBaseY</var>, <var>oppBaseX</var> and <var>oppBaseY</var>, the coordinates of your Mothership and of your opponent's Mothership.</p>
      </div>
    </div>
    <div class="blk">
      <div class="title">Input for One Game Turn</div>
      <div class="text">
        <p><span class="statement-lineno">Line 1:</span> an integer <var>pulsarCount</var>, the number of Pulsars in the sector.</p>
        <p><span class="statement-lineno">Next <var>pulsarCount</var> lines:</span> 4 space-separated integers <var>pulsarId</var>, <var>px</var>, <var>py</var> and <var>energy</var>, the unique identifier, coordinates, and currently stored energy of each Pulsar.</p>
        <p><span class="statement-lineno">Next line:</span> an integer <var>droneCount</var>, the total number of Relay Drones.</p>
        <p><span class="statement-lineno">Next <var>droneCount</var> lines:</span> 6 space-separated integers <var>droneId</var>, <var>owner</var>, <var>x</var>, <var>y</var>, <var>linkTarget</var> and <var>cooldown</var>:</p>
        <ul>
          <li><var>droneId</var>: unique identifier of the drone.</li>
          <li><var>owner</var>: <const>0</const> if you own this drone (your drones are always listed first), <const>1</const> if owned by your opponent.</li>
          <li><var>x</var>, <var>y</var>: current coordinates of the drone.</li>
          <li><var>linkTarget</var>: current relay target (<const>-2</const> for none, <const>-1</const> for Mothership, or a friendly <var>droneId</var>).</li>
          <!-- BEGIN level1 -->
          <li><var>cooldown</var>: always <const>0</const> in this league.</li>
          <!-- END -->
          <!-- BEGIN level2 level3 -->
          <li><var>cooldown</var>: turns remaining before <action>OVERCHARGE</action> is available again.</li>
          <!-- END -->
        </ul>
      </div>
    </div>
    <div class="blk">
      <div class="title">Output for One Game Turn</div>
      <div class="text">
        <p><span class="statement-lineno">One line per owned drone</span> (in the exact order they appear in the input), containing one of the following commands:</p>
        <ul>
          <li><action>MOVE x y</action> where <var>x</var> and <var>y</var> are integer target coordinates, optionally followed by <action>LINK targetId</action> where <var>targetId</var> is <const>-1</const> for your Mothership or a friendly <var>droneId</var></li>
          <!-- BEGIN level2 level3 -->
          <li>or <action>OVERCHARGE targetId</action> where <var>targetId</var> is <const>-1</const> or a friendly <var>droneId</var> (holds position and doubles beam capacity to <const>12</const>)</li>
          <!-- END -->
        </ul>
        <p>You may optionally append <action>MSG text</action> at the end of a line. Example: <action>MOVE 500 450 LINK -1</action></p>
      </div>
    </div>
    <div class="blk">
      <div class="title">Constraints</div>
      <div class="text">
        <var>width</var> = <const>1600</const>, <var>height</var> = <const>900</const><br>
        <var>pulsarCount</var> = <const>5</const><br>
        <const>4</const> &le; <var>droneCount</var> &le; <const>6</const><br>
        Response time for first turn &le; <const>1000</const> ms<br>
        Response time for one turn &le; <const>50</const> ms<br>
        Maximum number of turns: <const>100</const>
      </div>
    </div>
  </div>

  <!-- SOURCE CODE -->
  <div class="statement-section statement-examples">
    <h1><span class="icon icon-example">&nbsp;</span><span>Source Code</span></h1>
    <div class="statement-examples-text">
      The full referee, viewer, and starter bots source code is available on GitHub:
      <a href="https://github.com/codingame-team/codingame-quantum-relay" target="_blank" rel="noopener noreferrer">https://github.com/codingame-team/codingame-quantum-relay</a>
    </div>
  </div>
</div>
