<!-- LEAGUES level1 level2 level3 -->
<div id="statement_back" class="statement_back" style="display:none"></div>
<div class="statement-body">
  <!-- LEAGUE ALERT -->
  <!-- BEGIN level1 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bois 2 :</b> Contrôlez <b>2 Drones-Relais</b> pour récolter l'énergie des Pulsars et la transmettre à votre Vaisseau-Mère. Si vous omettez <action>LINK</action>, vos drones se connectent automatiquement vers votre Vaisseau-Mère lorsqu'ils sont à portée !</p>
  </div>
  <!-- END -->
  <!-- BEGIN level2 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bois 1 :</b> Vous contrôlez désormais <b>3 Drones-Relais</b> et débloquez <action>OVERCHARGE targetId</action> (double la bande passante du faisceau de <const>6</const> à <const>12</const> unités d'énergie/tour, recharge de <const>2</const> tours) !</p>
  </div>
  <!-- END -->
  <!-- BEGIN level3 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bronze+ :</b> L'<b>Interférence Quantique des Faisceaux</b> est activée ! Si deux faisceaux lasers adverses se croisent géométriquement, ils s'annulent mutuellement (<const>0</const> flux) sauf si l'un des deux utilise <action>OVERCHARGE</action> !</p>
  </div>
  <!-- END -->

  <!-- GOAL -->
  <div class="statement-section statement-goal">
    <h1><span class="icon icon-goal">&nbsp;</span><span>Objectif</span></h1>
    <div class="statement-goal-content">
      Acheminez plus d'énergie quantique depuis les <b>Pulsars</b> vers votre <b>Vaisseau-Mère</b> que votre adversaire en <const>100</const> tours.
    </div>
  </div>

  <!-- RULES -->
  <div class="statement-section statement-rules">
    <h1><span class="icon icon-rules">&nbsp;</span><span>Règles</span></h1>
    <div class="statement-rules-content">
      <p>La partie se déroule dans un secteur spatial de <const>1600</const>&times;<const>900</const> contenant deux <b>Vaisseaux-Mères</b> et <const>5</const> <b>Pulsars</b> :</p>
      <ul>
        <li><b>Déplacement des Drones (<action>MOVE x y</action>) :</b> Un drone se déplace jusqu'à <const>120</const> unités par tour vers (<var>x</var>, <var>y</var>).</li>
        <li><b>Faisceaux Relais Laser (<action>LINK targetId</action>) :</b> Chaque drone peut projeter un faisceau laser jusqu'à <const>480</const> unités vers son Vaisseau-Mère (<var>targetId</var> = <const>-1</const>) ou vers un autre drone allié (<var>targetId</var> = <var>droneId</var>). Sa capacité standard est de <const>6</const> énergies/tour.</li>
        <!-- BEGIN level2 level3 -->
        <li><b>Surcharge (<action>OVERCHARGE targetId</action>) :</b> Requiert <var>cooldown</var> = <const>0</const>. Le drone maintient sa position actuelle, se lie à <var>targetId</var> et double la capacité de son faisceau sortant à <const>12</const> énergies/tour pendant ce tour (passe <var>cooldown</var> à <const>2</const>).</li>
        <!-- END -->
        <!-- BEGIN level3 -->
        <li><b>Interférence Géométrique des Faisceaux :</b> Si un segment de faisceau actif d'un joueur coupe géométriquement un segment de faisceau actif de l'adversaire :
          <ul>
            <li><b>Standard vs Standard (ou Surchargé vs Surchargé) :</b> Les deux faisceaux sont court-circuités (capacité effective <const>0</const>).</li>
            <li><b>Surchargé vs Standard :</b> Le faisceau standard adverse est coupé (<const>0</const>), tandis que le faisceau surchargé conserve une capacité de <const>6</const> !</li>
          </ul>
        </li>
        <!-- END -->
        <li><b>Récolte &amp; Flot d'Énergie :</b> Tout drone situé à moins de <const>200</const> unités d'un Pulsar et relié à son Vaisseau-Mère par une chaîne de relais valide transfère l'énergie du Pulsar vers le Vaisseau-Mère, limité par la capacité résiduelle minimale (goulot d'étranglement) de sa chaîne. Les Pulsars régénèrent <const>3</const> à <const>5</const> énergies à la fin de chaque tour.</li>
      </ul>

      <!-- VICTORY CONDITIONS -->
      <div class="statement-victory-conditions">
        <div class="icon victory"></div>
        <div class="blk">
          <div class="title">Conditions de Victoire</div>
          <div class="text">
            <ul style="padding-bottom:0">
              <li>Vous avez acheminé plus d'énergie totale vers votre Vaisseau-Mère que votre adversaire à la fin de la partie.</li>
            </ul>
          </div>
        </div>
      </div>

      <!-- DEFEAT CONDITIONS -->
      <div class="statement-lose-conditions">
        <div class="icon lose"></div>
        <div class="blk">
          <div class="title">Conditions de Défaite</div>
          <div class="text">
            <ul style="padding-bottom:0">
              <li>Vous avez acheminé moins d'énergie totale vers votre Vaisseau-Mère que votre adversaire à la fin de la partie.</li>
              <li>Votre programme ne répond pas dans le temps imparti ou affiche une commande non reconnue.</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- PROTOCOL -->
  <div class="statement-section statement-protocol">
    <h1><span class="icon icon-protocol">&nbsp;</span><span>Entrées &amp; Sorties du Jeu</span></h1>
    <div class="blk">
      <div class="title">Entrées d'Initialisation</div>
      <div class="text">
        <p><span class="statement-lineno">Ligne 1 :</span> 3 entiers séparés par des espaces <var>width</var>, <var>height</var> et <var>leagueLevel</var>, la largeur et la hauteur du secteur spatial (<const>1600</const>&times;<const>900</const>) et le niveau de la ligue (<const>1</const>, <const>2</const> ou <const>3</const>).</p>
        <p><span class="statement-lineno">Ligne 2 :</span> 4 entiers séparés par des espaces <var>myBaseX</var>, <var>myBaseY</var>, <var>oppBaseX</var> et <var>oppBaseY</var>, les coordonnées de votre Vaisseau-Mère et de celui de votre adversaire.</p>
      </div>
    </div>
    <div class="blk">
      <div class="title">Entrées pour un Tour de Jeu</div>
      <div class="text">
        <p><span class="statement-lineno">Ligne 1 :</span> un entier <var>pulsarCount</var>, le nombre de Pulsars dans le secteur.</p>
        <p><span class="statement-lineno">Les <var>pulsarCount</var> lignes suivantes :</span> 4 entiers séparés par des espaces <var>pulsarId</var>, <var>px</var>, <var>py</var> et <var>energy</var>, l'identifiant unique, les coordonnées et l'énergie actuellement disponible du Pulsar.</p>
        <p><span class="statement-lineno">Ligne suivante :</span> un entier <var>droneCount</var>, le nombre total de drones-relais.</p>
        <p><span class="statement-lineno">Les <var>droneCount</var> lignes suivantes :</span> 6 entiers séparés par des espaces <var>droneId</var>, <var>owner</var>, <var>x</var>, <var>y</var>, <var>linkTarget</var> et <var>cooldown</var> :</p>
        <ul>
          <li><var>droneId</var> : identifiant unique du drone.</li>
          <li><var>owner</var> : <const>0</const> si vous possédez ce drone (vos drones sont toujours listés en premier), <const>1</const> s'il appartient à l'adversaire.</li>
          <li><var>x</var>, <var>y</var> : coordonnées actuelles du drone.</li>
          <li><var>linkTarget</var> : cible actuelle du faisceau (<const>-2</const> pour aucune, <const>-1</const> pour le Vaisseau-Mère, ou un <var>droneId</var> allié).</li>
          <!-- BEGIN level1 -->
          <li><var>cooldown</var> : toujours <const>0</const> dans cette ligue.</li>
          <!-- END -->
          <!-- BEGIN level2 level3 -->
          <li><var>cooldown</var> : nombre de tours restants avant que <action>OVERCHARGE</action> soit à nouveau disponible.</li>
          <!-- END -->
        </ul>
      </div>
    </div>
    <div class="blk">
      <div class="title">Sortie pour un Tour de Jeu</div>
      <div class="text">
        <p><span class="statement-lineno">Une ligne par drone que vous possédez</span> (dans l'ordre exact où ils apparaissent en entrée) :</p>
        <ul>
          <li><action>MOVE x y</action> où <var>x</var> et <var>y</var> sont des coordonnées entières, éventuellement suivi de <action>LINK targetId</action> où <var>targetId</var> vaut <const>-1</const> pour votre Vaisseau-Mère ou le <var>droneId</var> d'un drone allié</li>
          <!-- BEGIN level2 level3 -->
          <li>ou <action>OVERCHARGE targetId</action> où <var>targetId</var> vaut <const>-1</const> ou le <var>droneId</var> d'un drone allié (maintient la position et double la capacité du faisceau à <const>12</const>)</li>
          <!-- END -->
        </ul>
        <p>Vous pouvez ajouter <action>MSG texte</action> en fin de ligne. Exemple : <action>MOVE 500 450 LINK -1</action></p>
      </div>
    </div>
    <div class="blk">
      <div class="title">Contraintes</div>
      <div class="text">
        <var>width</var> = <const>1600</const>, <var>height</var> = <const>900</const><br>
        <var>pulsarCount</var> = <const>5</const><br>
        <const>4</const> &le; <var>droneCount</var> &le; <const>6</const><br>
        Temps de réponse pour le premier tour &le; <const>1000</const> ms<br>
        Temps de réponse par tour &le; <const>50</const> ms<br>
        Nombre maximum de tours : <const>100</const>
      </div>
    </div>
  </div>

  <!-- CODE SOURCE -->
  <div class="statement-section statement-examples">
    <h1><span class="icon icon-example">&nbsp;</span><span>Code Source</span></h1>
    <div class="statement-examples-text">
      Le code source complet de l'arbitre (Referee), du viewer et des bots de référence est disponible sur GitHub :
      <a href="https://github.com/codingame-team/codingame-quantum-relay" target="_blank" rel="noopener noreferrer">https://github.com/codingame-team/codingame-quantum-relay</a>
    </div>
  </div>
</div>
