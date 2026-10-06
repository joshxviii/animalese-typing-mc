package joshxviii.animalese.neoforge;

import joshxviii.animalese.AnimaleseMc;
import net.neoforged.fml.common.Mod;

@Mod(AnimaleseMc.MOD_ID)
public final class AnimaleseMcForge {
    public AnimaleseMcForge() {
        AnimaleseMc.INSTANCE.init();
    }
}
