package deco2800.spooky.managers;

import deco2800.spooky.Tickable;

public abstract class TickableManager implements Tickable, AbstractManager {

	 public abstract void onTick(long i);
}
