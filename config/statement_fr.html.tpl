<!-- LEAGUES level1 level2 level3 -->
<div id="statement_back" class="statement_back" style="display:none"></div>
<div class="statement-body">
  <!-- LEAGUE ALERT -->
  <!-- BEGIN level1 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bois 3 :</b> Contrôlez <b>2 Drones Relais</b> pour récolter l'énergie des Pulsars et la transmettre à votre Vaisseau-Mère. Si vous omettez <action>LINK</action>, les drones se connectent automatiquement à votre Vaisseau-Mère s'ils sont à portée !</p>
  </div>
  <!-- END -->
  <!-- BEGIN level2 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bois 2 :</b> Vous contrôlez désormais <b>3 Drones Relais</b> et débloquez l'action <action>OVERCHARGE</action> (double la capacité du faisceau de <const>6</const> à <const>12</const> énergie/tour, recharge <const>2</const> tours) !</p>
  </div>
  <!-- END -->
  <!-- BEGIN level3 -->
  <div style="color: #7cc576; background-color: rgba(124, 197, 118,.1); padding: 20px; margin-bottom: 15px; text-align: left">
    <p style="margin-bottom: 0"><b>Ligue Bois 1 :</b> Les <b>Interférences de Faisceaux Quantiques</b> sont actives ! Si deux faisceaux adverses se croisent géométriquement, les deux sont court-circuités (<const>0</const> débit) sauf si l'un est en <action>OVERCHARGE</action> !</p>
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
      <p>La partie se déroule dans un secteur spatial de <const>1600</const>&times;<const>900</const> à symétrie centrale contenant deux <b>Vaisseaux-Mères</b> et <const>5</const> <b>Pulsars</b>. Les coordonnées suivent la convention standard : <var>x</var> croît vers la droite, <var>y</var> croît vers le bas et <const>(0, 0)</const> correspond au coin supérieur gauche.</p>

      <h3 style="font-size: 16px; font-weight: 700; padding-top: 10px">Ordre de Résolution d'un Tour</h3>
      <ol>
        <li><b>Déplacement des Drones (<action>MOVE</action> <var>x</var> <var>y</var>) :</b> Chaque drone se déplace jusqu'à <const>120</const> unités par tour vers (<var>x</var>, <var>y</var>), dans les limites du secteur.</li>
        <li><b>Liaisons Relais Laser (<action>LINK</action> <var>targetId</var>) :</b> Après déplacement, chaque drone projette un faisceau laser jusqu'à <const>480</const> unités vers son Vaisseau-Mère (<var>targetId</var> = <const>-1</const>) ou un autre drone allié (<var>targetId</var> = <var>droneId</var>). Débit par défaut : <const>6</const> énergie/tour.</li>
        <!-- BEGIN level2 level3 -->
        <li><b>Surcharge (<action>OVERCHARGE</action> <var>targetId</var>) :</b> Requiert <var>cooldown</var> = <const>0</const>. Le drone maintient sa position, se relie à <var>targetId</var> et porte la capacité de son faisceau à <const>12</const> énergie/tour pour ce tour (passe <var>cooldown</var> à <const>2</const>).</li>
        <!-- END -->
        <!-- BEGIN level3 -->
        <li><b>Interférence de Faisceaux Quantiques :</b> Si un faisceau actif croise géométriquement un faisceau adverse actif :
          <ul>
            <li><b>Normal contre Normal (ou Surchargé contre Surchargé) :</b> Les deux faisceaux sont court-circuités (<const>0</const> débit).</li>
            <li><b>Surchargé contre Normal :</b> Le faisceau normal est coupé (<const>0</const> débit) tandis que le faisceau Surchargé conserve <const>6</const> de débit !</li>
          </ul>
        </li>
        <!-- END -->
        <li><b>Récolte &amp; Flux d'Énergie :</b> Tout drone situé à moins de <const>200</const> unités d'un Pulsar disposant d'un chemin relais acyclique valide vers son Vaisseau-Mère y achemine l'énergie du Pulsar, limitée par la bande passante minimale restante du chemin. Les Pulsars régénèrent <const>3</const> à <const>5</const> d'énergie à la fin de chaque tour.</li>
      </ol>
      <!-- BEGIN level1 -->
      <p><b>Actions inexécutables :</b> Les commandes syntaxiquement valides mais inexécutables (par exemple relier une cible hors portée, inexistante, soi-même ou adverse) sont simplement ignorées (le lien ne se forme pas) et n'entraînent pas la défaite.</p>
      <!-- END -->
      <!-- BEGIN level2 level3 -->
      <p><b>Actions inexécutables :</b> Les commandes syntaxiquement valides mais inexécutables (par exemple relier une cible hors portée, inexistante, soi-même ou adverse, ou utiliser <action>OVERCHARGE</action> avec <var>cooldown</var> &gt; <const>0</const>) sont simplement ignorées et n'entraînent pas la défaite.</p>
      <!-- END -->

      <!-- VICTORY CONDITIONS -->
      <div class="statement-victory-conditions">
        <div class="icon victory"></div>
        <div class="blk">
          <div class="title">Conditions de Victoire</div>
          <div class="text">
            <ul style="padding-bottom:0">
              <li>Vous avez acheminé plus d'énergie totale vers votre Vaisseau-Mère que votre adversaire au terme des <const>100</const> tours.</li>
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
              <li>Vous avez acheminé moins d'énergie totale que votre adversaire à la fin de la partie.</li>
              <li>Votre programme ne répond pas dans le temps imparti ou affiche une commande mal formée / non reconnue.</li>
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
        <p><span class="statement-lineno">Ligne 1 :</span> 3 entiers séparés par des espaces <var>width</var>, <var>height</var> et <var>leagueLevel</var>, la largeur et la hauteur du secteur spatial (<const>1600</const>&times;<const>900</const>) ainsi que la division Bois actuelle (<const>3</const> pour Bois 3, <const>2</const> pour Bois 2, <const>1</const> pour Bois 1).</p>
        <p><span class="statement-lineno">Ligne 2 :</span> 4 entiers séparés par des espaces <var>myBaseX</var>, <var>myBaseY</var>, <var>oppBaseX</var> et <var>oppBaseY</var>, les coordonnées de votre Vaisseau-Mère et de celui de votre adversaire.</p>
      </div>
    </div>
    <div class="blk">
      <div class="title">Entrées pour un Tour de Jeu</div>
      <div class="text">
        <p><span class="statement-lineno">Ligne 1 :</span> un entier <var>turn</var>, le numéro du tour courant (<const>1</const> à <const>100</const>).</p>
        <p><span class="statement-lineno">Ligne suivante :</span> un entier <var>pulsarCount</var>, le nombre de Pulsars dans le secteur.</p>
        <p><span class="statement-lineno">Les <var>pulsarCount</var> lignes suivantes :</span> 4 entiers séparés par des espaces <var>pulsarId</var>, <var>px</var>, <var>py</var> et <var>energy</var>, l'identifiant, les coordonnées et l'énergie disponible de chaque Pulsar.</p>
        <p><span class="statement-lineno">Ligne suivante :</span> un entier <var>droneCount</var>, le nombre total de Drones Relais.</p>
        <p><span class="statement-lineno">Les <var>droneCount</var> lignes suivantes :</span> 6 entiers séparés par des espaces <var>droneId</var>, <var>owner</var>, <var>x</var>, <var>y</var>, <var>linkTarget</var> et <var>cooldown</var> :</p>
        <ul>
          <li><var>droneId</var> : identifiant unique du drone.</li>
          <li><var>owner</var> : <const>0</const> si le drone vous appartient (vos drones sont toujours listés en premier), <const>1</const> s'il appartient à votre adversaire.</li>
          <li><var>x</var>, <var>y</var> : coordonnées actuelles du drone.</li>
          <li><var>linkTarget</var> : cible relais actuelle (<const>-2</const> = aucune, <const>-1</const> = Vaisseau-Mère, ou un <var>droneId</var> allié).</li>
          <!-- BEGIN level1 -->
          <li><var>cooldown</var> : vaut toujours <const>0</const> en Bois 3, les actions spéciales n'étant pas disponibles dans cette division.</li>
          <!-- END -->
          <!-- BEGIN level2 level3 -->
          <li><var>cooldown</var> : nombre de tours restants avant de pouvoir réutiliser <action>OVERCHARGE</action>.</li>
          <!-- END -->
        </ul>
      </div>
    </div>
    <div class="blk">
      <div class="title">Sortie pour un Tour de Jeu</div>
      <div class="text">
        <p><span class="statement-lineno">Une ligne par drone que vous possédez</span> (dans l'ordre exact où ils apparaissent en entrée), contenant l'une des commandes suivantes :</p>
        <ul>
          <li><action>MOVE</action> <var>x</var> <var>y</var> pour déplacer le drone vers les coordonnées entières (<var>x</var>, <var>y</var>).</li>
          <!-- BEGIN level2 level3 -->
          <li><action>OVERCHARGE</action> <var>targetId</var> pour maintenir la position et projeter un faisceau surchargé vers <var>targetId</var>.</li>
          <!-- END -->
        </ul>
        <p>Une commande <action>MOVE</action> peut être suivie d'une liaison relais :</p>
        <ul>
          <li><action>LINK</action> <var>targetId</var> pour projeter un faisceau laser vers votre Vaisseau-Mère (<const>-1</const>) ou un drone allié (<var>droneId</var>).</li>
        </ul>
        <!-- BEGIN level1 -->
        <p>Exemple : <action>MOVE 500 450 LINK -1</action></p>
        <!-- END -->
        <!-- BEGIN level2 level3 -->
        <p>Exemples : <action>MOVE 500 450 LINK -1</action> ou <action>OVERCHARGE -1</action></p>
        <!-- END -->
        <p>Vous pouvez facultativement ajouter <action>MSG</action> <var>text</var> en fin de ligne pour afficher un message dans le viewer.</p>
      </div>
    </div>
    <div class="blk">
      <div class="title">Contraintes</div>
      <div class="text">
        <var>width</var> = <const>1600</const>, <var>height</var> = <const>900</const><br>
        <var>pulsarCount</var> = <const>5</const><br>
        <!-- BEGIN level1 -->
        <var>droneCount</var> = <const>4</const><br>
        <!-- END -->
        <!-- BEGIN level2 level3 -->
        <var>droneCount</var> = <const>6</const><br>
        <!-- END -->
        Temps de réponse pour le premier tour &le; <const>1000</const> ms<br>
        Temps de réponse par tour &le; <const>50</const> ms<br>
        Nombre maximum de tours : <const>100</const>
      </div>
    </div>
  </div>

  <!-- CODE SOURCE & CRÉDITS -->
  <div class="statement-section statement-examples">
    <h1><span class="icon icon-example">&nbsp;</span><span>Code Source &amp; Crédits</span></h1>
    <div class="statement-examples-text">
      <p>Le code source complet de l'arbitre (Referee), du viewer et des bots de référence est disponible sur GitHub :
        <a href="https://github.com/codingame-team/codingame-quantum-relay" target="_blank" rel="noopener noreferrer">https://github.com/codingame-team/codingame-quantum-relay</a>
      </p>
      <p><b>Crédits &amp; Inspiration :</b> Conception originale inspirée de l'optimisation de flots dans les réseaux et des jeux de stratégie spatiale à relais d'énergie. Développé avec le SDK officiel CodinGame Game Engine.</p>
    </div>
  </div>
</div>
