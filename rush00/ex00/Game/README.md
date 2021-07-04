<h1>build and run:</h1>

<h3>first you need to build ChaseLogic</h3>
in <i>ChaseLogic</i> dir: <code>mvn clean install</code>

<h3>then build the program in <i>Game</i> dir</h3>
<code>mvn clean compile exec:java</code>

<h1>usage:</h1>
press keys for movement
<ul>
<li>UP W</li>
<li>DOWN S</li>
<li>LEFT A</li>
<li>RIGHT D</li>
<li>AUTO LOSE 9</li>
</ul>

<h1>Configuration</h1>

<p>in <i>application-production.properties</i></p>

enemy.char=X<br>
player. char=o<br>
wall. char=#<br>
goal. char=O<br>
empty.char=<br>
enemy.color=RED<br>
player. color=GREEN<br>
wall. color=MAGENTA<br>
goal. color=BLUE<br>
empty.color=YELLOW<br>
