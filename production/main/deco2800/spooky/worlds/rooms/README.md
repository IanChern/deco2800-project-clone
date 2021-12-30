This file explains how to serialize a room.

Rooms are layered in such a way that every room as a centre tile which all other tiles are connected around.
The first line of any ".room" file must be the number of layers in the room.

The second line *must* specify the number of entities the room will have.
Each successive line afterwards contains the tile number where the entity will appear, followed by a single colon (":"), the number of entities
that have a chance of spawning in the tile, another colon (":") and then a path to each entity surrounded by parantheses ("()"), with each
entity having a probabilty to spawn, followed by a comma (",") and the name of the entity.
Different entities may be serialized in different ways. 
An "x" may be used in the probability to represent even distributed probability across all entities with "x".

If any tile number exceeds the maximum number of tiles in the room (specified by the number of layers) the read will fail.
If the probability of the entities sum to greater than 1 the room will become invalid.

See "example.room" for an example file.
