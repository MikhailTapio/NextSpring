package committee.nova.ns.common.config

import committee.nova.ns.common.config.CommonConfig._
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.config.Configuration
import org.apache.logging.log4j.Logger

object CommonConfig {
  var refreshInterval: Int = _
  var expireTime: Int = _
  var influencedByBiome: Boolean = _
  var possibilityMultiplier: Float = _
  private var config: Configuration = _
  private var logger: Logger = _
}

class CommonConfig(event: FMLPreInitializationEvent) {
  logger = event.getModLog
  config = new Configuration(event.getSuggestedConfigurationFile)
  config.load()
  refreshInterval = config.getInt("refresh_interval", Configuration.CATEGORY_GENERAL, 100, 20, 6000, "Refresh interval for rotten flesh catalysis. A smaller value will make it faster for a plant to be tried to catalyze if possible. Default is 100 ticks(5 sec).")
  expireTime = config.getInt("expireTime", Configuration.CATEGORY_GENERAL, 6000, 20, 36000, "Expire time of a rotten flesh itemEntity. If smaller than the refreshInterval, the itemEntity will disappear when it refreshes for the first time.")
  influencedByBiome = config.getBoolean("biome_influence", Configuration.CATEGORY_GENERAL, true, "Should the possibility of catalysis be influenced by biome properties (temperature & rainfall)?")
  possibilityMultiplier = config.getFloat("possibility_multiplier", Configuration.CATEGORY_GENERAL, 1F, Float.MinValue, Float.MaxValue / 2, "The possibility multiplier. The greater the value is, the more the possibility the plant is catalyzed.")
  config.save()
}
