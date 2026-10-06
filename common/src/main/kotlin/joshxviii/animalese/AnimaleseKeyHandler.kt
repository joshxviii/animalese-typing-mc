package joshxviii.animalese

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.sounds.SoundEvents

object AnimaleseKeyHandler {

    fun keyPress(handle: Long, action: Int, event: KeyEvent) {

        val minecraft = Minecraft.getInstance()
        val screen = minecraft.screen

        if (action != 0) {
            val hasNoEditboxFocused = screen == null || screen.focused !is EditBox || !((screen.focused as EditBox).canConsumeInput())

            if (!hasNoEditboxFocused) {
                val e = event
                minecraft.soundManager.play(
                    SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_BANJO, 1.0f)
                )

            }

        }
    }

}