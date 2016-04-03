Chaser
======

This project is a group project exercise proposed by **Academia de Código** at ​Bootcamp #2​ to get used to a stronger and more complex library, LibGDX. For the first time in the course there were no guidelines. That opened a door for creativity.We came up with an idea to make an Sheep Herding Trials game, where the user controls a Working Sheepdog to heard sheeps to complete a course.
This is still a work in progress.

Creators
--------

 * [Itamar Lourenço](https://github.com/italou)
 * [Joana Falcão](https://github.com/joanaferrofalcao)
 * [Milena Baeza](https://github.com/milebza)
 * [Nuno Monteiro](https://github.com/Nuno1123)


Gameplay
--------

User controls the dog with Keyboards:
 * **W** - move forward
 * **A** - turn left
 * **D** - turn right
 * **SPACE** - run forward
 
To achieve a more realistic movement logic, every sheep has 3 invisible rings (see PlayState.Class. You can set these rings visible for debugging purposes). 

Sheeps have 3 states of movement that can change accordingly to dog's position, type of movement and/or direction. They can either move quietly, move at half-speed or move hopelessly. 

If the dog is inside the second ring, the sheep runs hopelessly, improves speed and chooses a direction to try to get away from dog.

If the dog is outside of the second ring but inside the third ring, the dog is running, the sheep will run hopelessly.

If the dog is walking but in sheep's vision area, sheeps run hopelessly. If the dog is walking and outside sheep's vision area, which means the dog is walking from behind the sheep, the sheep moves at half speed.If the dog is out of the third ring, regardless of his type of movement or direction, the sheep will ignore the dog a move quietly and towards the flock.The smaller ring is a kind of detector for sheeps to know if they are to far apart the flock.


Most Notable TODO's
-------------------

  * Improve Dog and Sheeps moving animations
  * Activate collision detectors between all objects  
  * Create random or different difficulty type of courses, course validation and Scores
  * Highscores
  * Multiplayer options
 
 
Design
------  
  
  * Original Concept by [Nuno Monteiro](https://github.com/Nuno1123)
  * Original Programming Structure by [Milena Baeza](https://github.com/milebza) and [Joana Falcão](https://github.com/joanaferrofalcao)
  * Graphics Design/Animation Design by [Nuno Monteiro](https://github.com/Nuno1123)
  * Kinetics Mathematics and Code Refactoring by [Joana Falcão](https://github.com/joanaferrofalcao) and [Itamar Lourenço](https://github.com/italou)
  * Overall Game Logic by [Itamar Lourenço](https://github.com/italou), [Joana Falcão](https://github.com/joanaferrofalcao), [Milena Baeza](https://github.com/milebza) and [Nuno Monteiro]((https://github.com/Nuno1123)
