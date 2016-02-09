# Game of life #

This is a raw implementation of [Conway's game of life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

- - -

## Index ##

* [Building the project](#building-the-project)
* [Running the application](#running-the-application)
* [Configuring the application](#configuring-the-application)
* [Test scenarios](#test-scenarios)
* [Implementations demystified](#implementations-demystified)
  * [Constrained world](#constrained-world)
  * [Endless world](#endless-world)
  
- - -

## Building the project ##

In order to build this project, you will require JDK 1.8 and maven 3.x

To build the project, simply make `mvn clean package` and maven will place a jar file on the target directory named `game-of-life.jar`.

- - -

## Running the application ##

To run the application you can either run the jar file

`java -jar target/game-of-life.jar [OPTION]..`

or you can use maven

`mvn exec:java`

- - -

## Configuring the application ##

The application can be configured in several different ways. You can create a properties file on the same place as the jar file called `application.properties` or you can pass the commands via command line.

The following table describe the options available and their defaults

|         Option         |     default     | description |
|:-----------------------|:---------------:|:------------|
| max-generations        | 2               | Maximum number of generations to be generated. |
| endless                | false           | Flag indicating to never stop generating new generations. |      
| pattern                | NOUS_OSCILLATOR | Pattern to be used. Patterns available: NOUS_OSCILLATOR, BORDER_BLINKER, BLINKER, VOLDIAG, BIG_HORIZONTAL_LINE, BLANK, ROW_OF_BLINKERS. |
| mode                   | ENDLESS         | Mode to be used. Can be ENDLESS or CONSTRAINED indicating what implementation should be used, respectively GameOfLifeEndless or GameOfLifeConstrained. |
| alive-character        | '#'             | Character to be used when printing live cells. |
| dead-character         | ' '             | Character to be used when printing dead cells. |
| input-pattern          | BINARY          | How is the input pattern defined. Currently can only be BINARY. |
| pattern-file           | undefined       | Used to indicate the file name of the file that contains the initial pattern to be used. |
| ms-between-generations | 500             | Number of milliseconds to wait between each generation. |

When using the properties file, each option in it's own line and should be in the format `option=value`

Example file:

```
max-generations=2
pattern=BIG_HORIZONTAL_LINE
alive-character=#
```

When using the command line arguments, options should be placed after jar file specification.

Example: `java -jar game-of-life.jar max-generations 2 pattern BIG_HORIZONTAL_LINE alive-character #`

Note: When configuring the options described above directly in the command line it's possible to either use the option name or the option name with the `--` prefix. For instance, you can use either `pattern BIG_HORIZONTAL_LINE` or `--pattern BIG_HORIZONTAL LINE`.

- - -

## Test scenarios ##

The project has unit tests primarily regarding game of life patterns.

The following patterns are tested:

### Blinker ###

The blinker is the smallest and most common oscillator. It will continuously change from 3 vertically aligned cells to 3 horizontally aligned cells.

```
...     .O.     ...
OOO --> .O. --> OOO
...     .O.     ...
```

### Border Blinker ###

The exact same thing as a [blinker](#blinker) but placed in a border. Starting in horizontal position and placed on the top most row, this will cause the next generation to not be entirely visible.

```
OOO
...
...
```

The border blinker gives different results depending on the implementation being used. For the GameOfLifeEndless implementation, it will oscillate, on the GameOfLifeConstrained implementation will lead to death of cells. This was intended. 

The (GameOfLifeEndless)[#endless-world] mimics a world in which there is no restriction on how far the cells can grow. 

The (GameOfLifeConstrained)[#constrained-world] mimics a world where there are boundaries to which the cells won't be able to grow to.

```
GameOfLifeEndless
OOO     .O.     OOO
... --> .O. --> ...
...     ...     ...
GameOfLifeConstrained
OOO     .O.     ...
... --> .O. --> ...
...     ...     ...
```

### Row of Blinkers ###

It's a normal blinker but there are 15 of then in a row.

```
...........................................................
OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO
...........................................................
                           |
                           v
.O...O...O...O...O...O...O...O...O...O...O...O...O...O...O.
.O...O...O...O...O...O...O...O...O...O...O...O...O...O...O.
.O...O...O...O...O...O...O...O...O...O...O...O...O...O...O.
                           |
                           v
...........................................................
OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO.OOO
...........................................................
```

### Nous Oscillator (a.k.a Toad) ###

The pattern provided in the exercise statement. An oscillator with a period of 2 generations. Commonly known as toad.

```
..........      ..........      ..........
..........      ..........      ..........
..........      ..........      ..........
..........      .....O....      ..........
....OOO...      ....O..O..      ....OOO...
.....OOO.. --> ....O..O..  --> .....OOO..
..........      ......O...      ..........
..........      ..........      ..........
..........      ..........      ..........
..........      ..........      ..........
``` 

### Doomed Life ###

A pattern doomed to have no cells after a few generations, in this case, the 4th generation will have no cells.

```
O....     .....     .....     .....     .....
.O...     .O...     .....     .....     .....
..O.. --> ..O.. --> ..O.. --> ..... --> .....
...O.     ...O.     .....     .....     .....
....O     .....     .....     .....     .....
```

### Boat ###

A still life. A still life is a pattern that does not change from one generation to the next, and thus is a parent of itself.

```
.....     .....
.OO..     .OO..
.O.O. --> .O.O.
..O..     ..O..
.....     .....
```

### Pre Block ###

A pattern that precedes the block. The block is a still life just like the boat. 

```
....     ....     ....
..O. --> .OO. --> .OO.
.OO.     .OO.     .OO.
....     ....     ....
```

- - -

## Implementations Demystified ##

Two implementations are available, a constrained and an endless.

### Constrained World ###

The constrained implementation mimics a world with boundaries, imposing restrictions on how far the cells can expand. The world size is determined by the initial pattern size and uses a bidimensional array behind the scenes. 

To calculate the next generation, a new array with the same dimensions as the current generation is created and for each current cell, check it's neighbours and, based on conway's game of life rules, determine the next cell state.

### Endless World ###

The endless implementation mimics a world without any boundaries on how far the cells can grow.

To represent the world, a map of coordinates and cell states is used. A coordinate is an object with an x and y values. A cell state is an enumeration of the possible states a cell can have (ALIVE and DEAD). The map only keeps track of the living cells.

To calculate the next generation, first a new world is created.
Afterwards a map with all the neighboring cells of the live cells that are dead is created.

For every live cell (all the cells contained in the current world) determine if it will continue alive and if it will, add it to the new generation map.
For every dead cell, determine if enough neighboring live cells (cells in the current world) exist and if so, add the current dead cell to the new generation map.

To retrieve the next state in a bidimensional array, a conversion between the two representations is also made. This implementation will use a pattern strategy to define how this conversion should be made. Currently only one exists, where the initial bidimensional array provided as a seed determines the array dimension of the output. But future strategies could be implemented, allowing the output to be resized according to the current world. the border blinker for instance would make the first next generation to be a 4x3 matrix instead of a 3x3.

- - -
