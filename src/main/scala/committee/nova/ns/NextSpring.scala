package committee.nova.ns

import committee.nova.ns.NextSpring.proxy
import committee.nova.ns.common.proxy.CommonProxy
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}

object NextSpring {
  final val MODID = "nextspring"
  @SidedProxy(serverSide = "committee.nova.ns.common.proxy.CommonProxy")
  val proxy: CommonProxy = new CommonProxy
  @Mod.Instance(NextSpring.MODID)
  var instance: NextSpring = _
}

@Mod(modid = NextSpring.MODID, useMetadata = true, acceptedMinecraftVersions = "[1.7,)")
class NextSpring {
  NextSpring.instance = this

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = proxy.preInit(event)

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = proxy.init(event)
}