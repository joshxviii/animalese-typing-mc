package joshxviii.animalese.fabric

import joshxviii.animalese.AnimaleseMc
import net.fabricmc.api.ModInitializer

class AnimaleseMcFabric: ModInitializer {
    override fun onInitialize() {
        AnimaleseMc.init()
    }
}