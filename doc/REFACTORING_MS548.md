## Refactoring of Simulation Methods

Originally, each of our simulations had to implement a generateMap method, which specified how to fill in the grid after the cells specified in the XML file were added. 
However, as I thought about this, I realized that in almost every class, this method simply came down to setting a specific type of cell based on each simulation. 
Since we already had to have an overridden createCell method, which makes a cell based on a given state and coordinate, I decided to move the logic of creating the default cells into the createCell method. 
This createCell method would be passed in a default cell state, specified in the XML file for that simulation, inside of the generateMap method. 
This consolidated a lot of duplicated code from the Simulation classes into the generateMap method in Simulation (the abstract class). 
Additionally, it makes more sense to have createCell, which is supposed to be able to return the proper cell given solely a state and coordinate, do ALL of the logic for creating cells. 
The changes can be seen in [this](https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team05/commit/151f14e3d9cd3079207f86ede49e8c3a27bc0b32) commit.