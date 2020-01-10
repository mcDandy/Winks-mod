package cz.mcDandy.winksmod.Dimensions.Biomes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ModBiomes {
    public static Biome OMEGA_BIOME = register(2684,"winksmod:omega",new OmegaBiome());
    public static Biome[] BIOMES = new Biome[]
           {
OMEGA_BIOME
           };
    private static Biome register(int id, String key, Biome p_222369_2_) {
        Registry.register(Registry.BIOME, id, key, p_222369_2_);
        if (p_222369_2_.isMutation()) {
            Biome.MUTATION_TO_BASE_ID_MAP.put(p_222369_2_, Registry.BIOME.getId(Registry.BIOME.getOrDefault(new ResourceLocation(p_222369_2_.getParent()))));
        }

        return p_222369_2_;
    }

}
