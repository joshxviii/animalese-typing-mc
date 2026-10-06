package joshxviii.animalese

import joshxviii.animalese.AnimaleseMc.LOGGER
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import org.lwjgl.glfw.GLFW
import kotlin.random.Random

object AnimaleseKeyHandler {

    fun keyPress(handle: Long, action: Int, event: KeyEvent) {
        if (action != GLFW.GLFW_PRESS) return

        val screen = Minecraft.getInstance().screen ?: return
        val canType = when (screen) {
            is AbstractSignEditScreen -> true
            else -> (screen.focused as? EditBox)?.canConsumeInput() == true
        }
        if (!canType) return

        val modifiers = event.modifiers
        val disallowedModifiers = GLFW.GLFW_MOD_CONTROL or GLFW.GLFW_MOD_ALT or GLFW.GLFW_MOD_SUPER
        if (modifiers and disallowedModifiers != 0) return

        val shiftDown = modifiers and GLFW.GLFW_MOD_SHIFT != 0
        val sound = AnimaleseSounds.forKeyCode(event.key, shiftDown) ?: return
        val capsBoost = if (shiftDown && event.key in GLFW.GLFW_KEY_A..GLFW.GLFW_KEY_Z) 0.2f else 0f
        val pitch = 0.92f + Random.nextFloat() * 0.16f + capsBoost

        Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(sound, pitch, 0.225f + capsBoost))
        LOGGER.debug("Playing typing sound for keycode {}", event.key)
    }
}