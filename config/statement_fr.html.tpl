<!-- LEAGUES level1 level2 level3 -->
<div id="statement_back" class="statement_back" style="display:none"></div>
<div class="statement-body">
  <!-- LEAGUE ALERT -->
  <!-- BEGIN level1 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bois 2 :</b> Contrôlez <b>2 Drones-Relais</b> pour récolter l'énergie des Pulsars et la transmettre à votre Vaisseau-Mère. Si vous omettez <code>LINK</code>, vos drones se connectent automatiquement vers votre Vaisseau-Mère lorsqu'ils sont à portée !</p>
  </div>
  <!-- END -->
  <!-- BEGIN level2 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bois 1 :</b> Vous contrôlez désormais <b>3 Drones-Relais</b> et débloquez <code>OVERCHARGE targetId</code> (double la bande passante du faisceau de <const>6</const> à <const>12</const> unités d'énergie/tour, recharge de 2 tours) !</p>
  </div>
  <!-- END -->
  <!-- BEGIN level3 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bronze+ :</b> L'<b>Interférence Quantique des Faisceaux</b> est activée ! Si deux faisceaux lasers adverses se croisent géométriquement, ils s'annulent mutuellement (<const>0</const> flux) sauf si l'un des deux utilise <code>OVERCHARGE</code> !</p>
  </div>
  <!-- END -->

  <!-- GOAL -->
  <div class="statement-section statement-goal">
    <h1><span class="icon icon-goal">&nbsp;</span><span>Objectif</span></h1>
    <div class="statement-goal-content">
      Acheminez plus d'énergie quantique depuis les <b>Pulsars</b> vers votre <b>Vaisseau-Mère</b> que votre adversaire en <b>100 tours</b>.
    </div>
  </div>

  <!-- RULES -->
  <div class="statement-section statement-rules">
    <h1><span class="icon icon-rules">&nbsp;</span><span>Règles</span></h1>
    <div class="statement-rules-content">
      <p>La partie se déroule dans un secteur spatial de <b>1600&times;900</b> contenant deux <b>Vaisseaux-Mères</b> et <b>5 Pulsars</b> :</p>
      <ul>
        <li><b>Déplacement des Drones (<action>MOVE x y</action>) :</b> Un drone se déplace jusqu'à <const>120</const> unités par tour vers <var>(x, y)</var>.</li>
        <li><b>Faisceaux Relais Laser (<action>LINK targetId</action>) :</b> Chaque drone peut projeter un faisceau laser jusqu'à <const>480</const> unités vers son Vaisseau-Mère (<var>targetId = -1</var>) ou vers un autre drone allié (<var>targetId = droneId</var>). Sa capacité standard est de <const>6</const> énergies/tour.</li>
        <!-- BEGIN level2 level3 -->
        <li><b>Surcharge (<action>OVERCHARGE targetId</action>) :</b> Requiert <var>cooldown == 0</var>. Le drone maintient sa position actuelle, se lie à <var>targetId</var> et double la capacité de son faisceau sortant à <const>12</const> énergies/tour pendant ce tour (passe <var>cooldown = 2</var>).</li>
        <!-- END -->
        <!-- BEGIN level3 -->
        <li><b>Interférence Géométrique des Faisceaux :</b> Si le segment actif <var>[A, B]</var> d'un joueur coupe géométriquement le segment actif <var>[C, D]</var> de l'adversaire :
          <ul>
            <li><b>Standard vs Standard (ou Surchargé vs Surchargé) :</b> Les deux faisceaux sont court-circuités (capacité effective <const>0</const>).</li>
            <li><b>Surchargé vs Standard :</b> Le faisceau standard adverse est coupé (<const>0</const>), tandis que le faisceau surchargé conserve une capacité de <const>6</const> !</li>
          </ul>
        </li>
        <!-- END -->
        <li><b>Récolte &amp; Flot d'Énergie :</b> Tout drone situé à moins de <const>200</const> unités d'un Pulsar et relié à son Vaisseau-Mère par une chaîne de relais valide transfère l'énergie du Pulsar vers le Vaisseau-Mère, limité par la capacité résiduelle minimale (goulot d'étranglement) de sa chaîne. Les Pulsars régénèrent <const>+3</const> à <const>+4</const> énergies à la fin de chaque tour.</li>
      </ul>
    </div>
  </div>

  <!-- PROTOCOL -->
  <div class="statement-section statement-protocol">
    <h1><span class="icon icon-protocol">&nbsp;</span><span>Entrées &amp; Sorties du Jeu</span></h1>
    <div class="statement-protocol-content">
      <div class="blk">
        <div class="title">Entrées d'Initialisation</div>
        <div class="text">
          Ligne 1 : <var>width</var> <var>height</var> <var>leagueLevel</var> (<const>1600 900</const> et le niveau de la ligue).<br>
          Ligne 2 : <var>myBaseX</var> <var>myBaseY</var> <var>oppBaseX</var> <var>oppBaseY</var>.
        </div>
      </div>
      <div class="blk">
        <div class="title">Entrées pour un Tour de Jeu</div>
        <div class="text">
          <ul>
            <li>Ligne 1 : <var>pulsarCount</var>, le nombre de Pulsars.</li>
            <li>Les <var>pulsarCount</var> lignes suivantes : <var>pulsarId</var> <var>px</var> <var>py</var> <var>energy</var>.</li>
            <li>Ligne suivante : <var>droneCount</var>, le nombre total de drones.</li>
            <li>Les <var>droneCount</var> lignes suivantes : <var>droneId</var> <var>owner</var> <var>x</var> <var>y</var> <var>linkTarget</var> <var>cooldown</var> (<var>owner</var> vaut toujours <const>0</const> pour vos drones, listés en premier, et <const>1</const> pour ceux de l'adversaire).</li>
          </ul>
        </div>
      </div>
      <div class="blk">
        <div class="title">Sortie pour un Tour de Jeu</div>
        <div class="text">
          Affichez <b>une ligne par drone que vous possédez</b> (dans l'ordre exact où ils apparaissent en entrée) :
          <ul>
            <li><action>MOVE x y</action> (éventuellement suivi de <action>LINK targetId</action>, où <const>-1</const> désigne votre Vaisseau-Mère et <var>droneId</var> un drone allié)</li>
            <!-- BEGIN level2 level3 -->
            <li>ou <action>OVERCHARGE targetId</action> (maintient la position et double la capacité du faisceau à <const>12</const>)</li>
            <!-- END -->
          </ul>
        </div>
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
