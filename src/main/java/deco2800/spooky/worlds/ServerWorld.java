package deco2800.spooky.worlds;

import java.util.concurrent.CopyOnWriteArrayList;
import deco2800.spooky.entities.*;

/**
 * Server world acts as the husk of an actual world, controlled by a server connection.
 *
 * Clients will not call onTick()
 */
public class ServerWorld extends AbstractWorld {

    public ServerWorld() {
        tiles = new CopyOnWriteArrayList<Tile>();
    }

    /**
     * Do not tick entities in this onTick method, entities will be ticked by the server.
     */
    @Override
    public void onTick(long i) {

    }

	@Override
	protected void generateWorld() {
		/* Auto generated stub */
	}
}
