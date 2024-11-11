<h1>Minesweeper</h1>
<p>This is a Minesweeper clone coded completely in Java. It plays exactly like the real Minesweeper, but incase you have never played, left-clicks reveal tiles, right-clicks place flags on tiles 
  (making them unclickable), and right-clicking again on a flagged tile will remove the flag. The objective is click on tiles that dont contain mines/bombs. Clicking on a mine will reveal all other mines and 
  trigger a game over. You can place flags on tiles you think mines might be under. Clicking on a tile that does not have a mine will either reveal a number (which is how many mines are currently surrounding 
  that tile), or, if there are no mines nearby, it will reveal all the tiles in proximity that also do not touch any mines</p>
<br/>
<a href = "https://imgur.com/a/P3NYFgy">Image of the game being played</a> 
<br/>
<a href = "https://imgur.com/a/a71sfo5">Image of the game over</a>
<br/>
<br/>
<h1>How It's Made:</h1>
<p>Main Tech Used: Java, JFrame library</p>
<br/>
<p>I used two classes, App.java and Minesweeper.java</p>
<br/>
<p>App.java only runs the program, but Minesweeper.java holds all the actual code for the game. To build it, I utilized multiple ArrayLists, one to store and cycle through the tiles, one to record the positions 
  of the mines, one to record flags, and one to check if a mine has been hit. Other than that, basic helper methods to assist in checking for the events just listed.</p>
<br/>
<h1>Lessons Learned:</h1>
<p>This is only my second time using JFrame, so it served for good practice, going over how to display different shapes, colours and strings.</p>
