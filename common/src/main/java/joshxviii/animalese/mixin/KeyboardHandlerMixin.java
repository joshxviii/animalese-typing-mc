package joshxviii.animalese.mixin;

import joshxviii.animalese.AnimaleseKeyHandler;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(method = "keyPress", at = @At("HEAD"))
    private void keyPress(long handle, int action, KeyEvent event, CallbackInfo ci) {
        AnimaleseKeyHandler.INSTANCE.keyPress(handle, action, event);
    }

}
