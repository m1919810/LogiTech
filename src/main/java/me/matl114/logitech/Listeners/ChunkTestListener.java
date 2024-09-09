package me.matl114.logitech.Listeners;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.Utils.Debug;
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
