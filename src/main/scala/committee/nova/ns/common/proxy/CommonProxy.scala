package committee.nova.ns.common.proxy

import committee.nova.ns.common.config.CommonConfig
import committee.nova.ns.common.event.NextSpringEvent
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.common.MinecraftForge

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent): Unit = new CommonConfig(event)

  def init(event: FMLInitializationEvent): Unit = MinecraftForge.EVENT_BUS.register(new NextSpringEvent)
}
