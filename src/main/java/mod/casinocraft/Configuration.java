package mod.casinocraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.minecraft.world.level.biome.Biomes.*;

@SuppressWarnings("unused")
public class Configuration {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	public static final ConfigCasino CASINO = new ConfigCasino(BUILDER, "casino");
	
	public static class ConfigCasino {
		public static ForgeConfigSpec.BooleanValue config_creative_token;
		public static ForgeConfigSpec.BooleanValue config_creative_reward;
		public static ForgeConfigSpec.BooleanValue config_creative_oponly;
		public static ForgeConfigSpec.BooleanValue config_animated_cards;
		public static ForgeConfigSpec.IntValue     config_timeout;
		
		ConfigCasino(ForgeConfigSpec.Builder builder, String id){
			builder.push("CASINO CONFIG");
			config_creative_token   = builder.comment("Allows Betting Inventory to be set to INFINITE").define("config_creative_token", true);
			config_creative_reward  = builder.comment("Allows Reward Inventory to be set to INFINITE").define("config_creative_reward", true);
			config_creative_oponly  = builder.comment("Only Operators on a Server or Singleplayer with Cheats enabled can set INFINITE").define("config_creative_oponly", false);
			config_animated_cards   = builder.comment("Face Cards do a one frame animation sometimes").define("config_animated_cards", true);
			config_timeout          = builder.comment("How long until a player receives a timeout").defineInRange("config_timeout", 300, 1, 10000);
			builder.pop();
		}
	}
	
	public static final ForgeConfigSpec spec = BUILDER.build();
	
}