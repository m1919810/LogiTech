package me.matl114.logitech.utils.Algorithms;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import java.util.ArrayList;

public class PairList<T, W> extends ArrayList<Pair<T, W>> {
    public void put(T key, W value) {
        this.add(new Pair<>(key, value));
    }
}
