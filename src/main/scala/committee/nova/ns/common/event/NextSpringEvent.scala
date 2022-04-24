package committee.nova.ns.common.event

import committee.nova.ns.common.config.CommonConfig
import committee.nova.ns.common.util.Utilities.catalyze
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Items
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.item.ItemExpireEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class NextSpringEvent {
  @SubscribeEvent
  def onItemDropped(event: EntityJoinWorldEvent): Unit = {
    if (event.isCanceled) return
    if (!event.getEntity.isInstanceOf[EntityItem]) return
    val itemEntity = event.getEntity.asInstanceOf[EntityItem]
    val stack = itemEntity.getEntityItem
    if (stack == null) return
    if (stack.getItem != Items.ROTTEN_FLESH) return
    itemEntity.lifespan = CommonConfig.refreshInterval
  }

  @SubscribeEvent
  def onItemExpire(event: ItemExpireEvent): Unit = {
    if (event.isCanceled) return
    val itemEntity = event.getEntityItem
    val stack = itemEntity.getEntityItem
    if (stack.getItem != Items.ROTTEN_FLESH) return
    if (itemEntity.ticksExisted >= CommonConfig.expireTime) return
    if (!catalyze(itemEntity)) {
      event.setExtraLife(CommonConfig.refreshInterval)
      event.setCanceled(true)
      return
    }
    val newStack = stack.copy()
    newStack.stackSize -= 1
    if (newStack.stackSize <= 0) return
    itemEntity.setEntityItemStack(newStack)
    event.setExtraLife(CommonConfig.refreshInterval)
    event.setCanceled(true)
  }
}
