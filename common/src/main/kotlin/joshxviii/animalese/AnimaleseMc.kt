package joshxviii.animalese

import net.minecraft.resources.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object AnimaleseMc {

    //TODO add screen and profile saving for custom animalese

    const val MOD_ID: String = "animalese_mc"
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        AnimaleseSounds.register()
    }
}

fun animaleseResource(name: String): Identifier {
    return Identifier.fromNamespaceAndPath(AnimaleseMc.MOD_ID,name)
}