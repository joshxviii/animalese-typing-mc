package joshxviii.animalese

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvent
import org.lwjgl.glfw.GLFW

object AnimaleseSounds {
    private val typingSounds = ('a'..'z').associate { letter ->
        GLFW.GLFW_KEY_A + (letter - 'a') to "typing.$letter"
    } + ('0'..'9').associate { digit ->
        val keyCode = if (digit == '0') GLFW.GLFW_KEY_0 else GLFW.GLFW_KEY_1 + (digit - '1')
        keyCode to "typing.$digit"
    }

    private val shiftedNumberSounds = listOf(
        "typing.gwah",
        "effects.at",
        "effects.pound",
        "effects.dollar",
        "effects.percent",
        "effects.caret",
        "effects.ampersand",
        "effects.asterisk",
        "effects.parenthesis_open",
        "effects.parenthesis_closed"
    ).mapIndexed { index, soundName ->
        val keyCode = if (index == 9) GLFW.GLFW_KEY_0 else GLFW.GLFW_KEY_1 + index
        keyCode to soundName
    }.toMap()

    private val punctuationSounds = mapOf(
        GLFW.GLFW_KEY_GRAVE_ACCENT to ("effects.default" to "effects.tilde"),
        GLFW.GLFW_KEY_MINUS to ("effects.default" to "effects.default"),
        GLFW.GLFW_KEY_EQUAL to ("effects.default" to "effects.default"),
        GLFW.GLFW_KEY_LEFT_BRACKET to ("effects.bracket_open" to "effects.brace_open"),
        GLFW.GLFW_KEY_RIGHT_BRACKET to ("effects.bracket_closed" to "effects.brace_closed"),
        GLFW.GLFW_KEY_BACKSLASH to ("effects.slash_back" to "effects.default"),
        GLFW.GLFW_KEY_SEMICOLON to ("effects.default" to "effects.default"),
        GLFW.GLFW_KEY_APOSTROPHE to ("effects.default" to "effects.default"),
        GLFW.GLFW_KEY_COMMA to ("effects.default" to "effects.default"),
        GLFW.GLFW_KEY_PERIOD to ("effects.default" to "effects.default"),
        GLFW.GLFW_KEY_SLASH to ("effects.slash_forward" to "typing.deska"),
        GLFW.GLFW_KEY_ESCAPE to ("effects.enter" to "effects.enter"),
        GLFW.GLFW_KEY_TAB to ("effects.tab" to "effects.tab"),
        GLFW.GLFW_KEY_ENTER to ("effects.enter" to "effects.enter"),
        GLFW.GLFW_KEY_BACKSPACE to ("effects.backspace" to "effects.at"),
        GLFW.GLFW_KEY_LEFT to ("effects.arrow_left" to "effects.arrow_left"),
        GLFW.GLFW_KEY_UP to ("effects.arrow_up" to "effects.arrow_up"),
        GLFW.GLFW_KEY_RIGHT to ("effects.arrow_right" to "effects.arrow_right"),
        GLFW.GLFW_KEY_DOWN to ("effects.arrow_down" to "effects.arrow_down")
    )

    private val soundNames = (
        typingSounds.values +
            shiftedNumberSounds.values +
            punctuationSounds.values.flatMap { listOf(it.first, it.second) } +
            listOf("typing.gwah", "typing.deska")
        ).toSet()
    private val soundRegistry = DeferredRegister.create<SoundEvent>(AnimaleseMc.MOD_ID, Registries.SOUND_EVENT)
    private val sounds: Map<String, RegistrySupplier<SoundEvent>> = soundNames.associateWith { soundName ->
        soundRegistry.register(soundName) {
            val id = animaleseResource(soundName)
            SoundEvent.createVariableRangeEvent(id)
        }
    }

    fun register() {
        AnimaleseMc.LOGGER.info("Registering Animalese sound events")
        soundRegistry.register()
    }

    fun forKeyCode(keyCode: Int, shiftDown: Boolean): SoundEvent? {
        val name = when {
            shiftDown && keyCode in shiftedNumberSounds -> shiftedNumberSounds[keyCode]
            keyCode in typingSounds -> typingSounds[keyCode]
            keyCode in punctuationSounds -> {
                val sounds = punctuationSounds.getValue(keyCode)
                if (shiftDown) sounds.second else sounds.first
            }
            else -> null
        } ?: return null

        return sounds[name]?.get()
    }
}
