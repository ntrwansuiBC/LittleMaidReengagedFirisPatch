package net.blacklab.lmr.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

/**
 * LittleMaidReengaged設定
 *
 */
public class LMRConfig {
	
	// @MLProp(info="Relative spawn weight. The lower the less common. 10=pigs. 0=off")
	public static int cfg_spawnWeight = 5;
	// @MLProp(info="Maximum spawn count in the World.")
	public static int cfg_spawnLimit = 20;
	// @MLProp(info="Minimum spawn group count.")
	public static int cfg_minGroupSize = 1;
	// @MLProp(info="Maximum spawn group count.")
	public static int cfg_maxGroupSize = 3;
	// @MLProp(info="It will despawn, if it lets things go. ")
	public static boolean cfg_canDespawn = false;

	// @MLProp(info="Print Debug Massages.")
	public static boolean cfg_PrintDebugMessage = false;
	// @MLProp(info="Print Death Massages.")
	public static boolean cfg_DeathMessage = true;
	// @MLProp(info="Spawn Anywhere.")
	public static boolean cfg_Dominant = false;
	// アルファブレンド
	public static boolean cfg_isModelAlphaBlend = false;
	
	// 野生テクスチャ
	public static boolean cfg_isFixedWildMaid = false;

	public static final float cfg_voiceRate = 0.2f;
	
	/** メイドの土産 */
	public static boolean cfg_isResurrection = true;
	
	/** 砂糖アイテムID */
	public static List<String> cfg_sugar_item_ids = null;

	/** ケーキアイテムID */
	public static List<String> cfg_cake_item_ids = null;
	
	/** 原木ブロックID */
	public static List<String> cfg_lj_log_block_ids = null;

	/** 葉ブロックID */
	public static List<String> cfg_lj_leaf_block_ids = null;

	/** 苗アイテムID */
	public static List<String> cfg_lj_sapling_item_ids = null;
	
	/** デフォルトスポーン有効化設定 */
	public static boolean cfg_spawn_default_enable = true;
	
	/** カスタムスポーン バイオームID or バイオーム名 */
	public static List<String> cfg_spawn_biomes = null;
	
	/** メイドミルク */
	public static boolean cfg_custom_maid_milk = false;
	
	/** 騎乗高さ調整 */
	public static float cfg_custom_riding_height_adjustment = 0.0F;
	
	/** 矢（弾丸）アイテムID */
	public static List<String> cfg_ac_arrow_item_ids = null;
	
	/**
	 * Config初期化
	 */
	public static void init(File configFile) {
		
		// Config
		Configuration cfg = new Configuration(configFile);
		cfg.load();

		cfg_canDespawn = cfg.getBoolean("canDespawn", "General", false,
				"Set whether non-contracted maids can despawn.");
		
		cfg_DeathMessage = cfg.getBoolean("deathMessage", "General", true,
				"Set whether prints death message of maids.");
		
		cfg_Dominant = cfg.getBoolean("Dominant", "Advanced", false,
				"Recommended to keep 'false'. If true, non-vanilla check is used for maid spawning.");
		
		cfg_maxGroupSize = cfg.getInt("maxGroupSize", "Advanced", 3, 1, 20,
				"Settings for maid spawning. Recommended to keep default.");
		
		cfg_minGroupSize = cfg.getInt("minGroupSize", "Advanced", 1, 1, 20,
				"Settings for maid spawning. Recommended to keep default.");
		
		cfg_spawnLimit = cfg.getInt("spawnLimit", "Advanced", 20, 1, 30,
				"Settings for maid spawning. Recommended to keep default.");
		
		cfg_spawnWeight = cfg.getInt("spawnWeight", "Advanced", 5, 1, 9,
				"Settings for maid spawning. Recommended to keep default.");
		
		cfg_PrintDebugMessage = cfg.getBoolean("PrintDebugMessage", "Advanced", false,
				"Print debug logs. Recommended to keep default.");
		
		cfg_isModelAlphaBlend = cfg.getBoolean("isModelAlphaBlend", "Advanced", true,
				"If your graphics SHOULD be too powerless to draw alpha-blend textures, turn this 'false'.");
		
		cfg_isFixedWildMaid = cfg.getBoolean("isFixedWildMaid", "General", false,
				"If 'true', only default-texture maid spawns. You can still change their textures after employing.");

		//メイドの土産設定
		cfg_isResurrection = cfg.getBoolean("isResurrection", "General", true,
				"If 'true', Drops a resurrection item when a maid dies.");
		
		//指定IDを砂糖として認識する
		String[] sugarItemIds = new String[] {"minecraft:sugar"};
		cfg_sugar_item_ids = Arrays.asList(cfg.getStringList("Sugar", "Custom", sugarItemIds, "Set the item ID to be treated the same as maid sugar."));

		//指定IDを砂糖として認識する
		String[] cakeItemIds = new String[] {"minecraft:cake"};
		cfg_cake_item_ids = Arrays.asList(cfg.getStringList("Cake", "Custom", cakeItemIds, "Set the item ID to be treated the same as maid cake."));
		
		//木こり設定
		initLumberjack(cfg);
		
		//メイドスポーン設定
		initSpawnBiome(cfg);
		
		//メイドミルクの設定
		cfg_custom_maid_milk = cfg.getBoolean("MaidMilk", "Custom", false,
				"Enable maid milk.");
		
		//騎乗メイドの高さ設定
		cfg_custom_riding_height_adjustment = cfg.getFloat("RidingHeightAdjustment", "Custom", 0.0F, -2.0F, 2.0F, 
				"Riding mode height adjustment.Standard setting of PFLM is -0.5.");
		
		//アーチャー設定
		initArcher(cfg);
		
		//試験機能
		initTest(cfg);
		
		cfg.save();
	}
	
	/**
	 * 木こり用設定
	 */
	public static void initLumberjack(Configuration cfg) {
		
		//指定IDを原木として認識する
		String[] logBlockIds = new String[] {"minecraft:log"};
		cfg_lj_log_block_ids = Arrays.asList(
				cfg.getStringList("Log", "Lumberjack", logBlockIds, "Set the block ID to be processed in the same way as the log.")
		);
		
		//指定IDを葉ブロックとして認識する
		String[] leafBlockIds = new String[] {"minecraft:leaves"};
		cfg_lj_leaf_block_ids = Arrays.asList(
				cfg.getStringList("Leaf", "Lumberjack", leafBlockIds, "Set the block ID to be processed in the same way as the leaf.")
		);
		
		//指定IDを苗木として認識する
		String[] saplingItemIds = new String[] {"minecraft:sapling"};
		cfg_lj_sapling_item_ids = Arrays.asList(
				cfg.getStringList("Sapling", "Lumberjack", saplingItemIds, "Set the item ID to be processed in the same way as the sapling.")
		);
	}
	
	/**
	 * カスタムスポーン設定
	 */
	public static void initSpawnBiome(Configuration cfg) {
		
		//カスタムスポーン有効設定
		cfg_spawn_default_enable = cfg.getBoolean("SpawnDefaultEnable", "Custom", true,
				"Enable the default spawn for Maid.");
		
		//スポーンバイオーム設定
		String[] biomeList = new String[] {
				"Plains",
				"minecraft:plains"};
		cfg_spawn_biomes = Arrays.asList(
				cfg.getStringList("SpawnBiomeList", "Custom", biomeList, 
						"Setting for Maid custom spawn Biome Name or Biome Id.")
		);
		
	}
	
	
	/**
	 * アーチャー用設定
	 */
	public static void initArcher(Configuration cfg) {
		
		//指定IDを矢として認識する
		String[] arrowItemIds = new String[] {"minecraft:arrow"};
		cfg_ac_arrow_item_ids = Arrays.asList(
				cfg.getStringList("Arrow", "Archer", arrowItemIds, "Set the item ID to be processed in the same way as the arrow or bullet.")
		);
		
	}
	
	
	/** 試験機能 ******************************/
	/** 水上歩行術 */
	public static boolean cfg_test_water_walking  = true;
	
	/**
	 * 試験的に実装した機能の設定
	 */
	public static void initTest(Configuration cfg) {
		
		//水上歩行術
		cfg_test_water_walking = cfg.getBoolean("MaidWaterWalking", "Test", false,
				"Enable Maid's water walking technique.");
		
	}
	
	/****************************************/
	
}
