package me.matl114.logitech.listeners;

import me.matl114.logitech.utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkTestListener implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
       // throw new NotImplementedException("nm");
          Debug.logger("CHUNK LOAD: " + event.getChunk());
    }
}
