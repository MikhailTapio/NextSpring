package committee.nova.ns.common.util

import committee.nova.ns.common.config.CommonConfig._
import net.minecraft.block.{Block, IGrowable}
import net.minecraft.entity.item.EntityItem
import net.minecraft.world.World

object Utilities {
  def catalyze(entity: EntityItem): Boolean = {
    val world = entity.worldObj
    val x = Math.floor(entity.posX).intValue()
    val y = Math.floor(entity.posY + 0.2).intValue()
    val z = Math.floor(entity.posZ).intValue()
    val blockIn = world.getBlock(x, y, z)
    if (influencedByBiome && !willTryCatalyze(world, x, z)) return false
    if (tryCatalyze(world, blockIn, x, y, z)) return true
    val dirt = world.getBlock(x, y - 1, z)
    tryCatalyze(world, dirt, x, y - 1, z)
  }

  def tryCatalyze(world: World, block: Block, x: Int, y: Int, z: Int): Boolean = {
    if (!block.isInstanceOf[IGrowable]) return false
    val plant = block.asInstanceOf[IGrowable]
    try {
      if (!plant.func_149851_a(world, x, y, z, world.isRemote)) return false
      if (!plant.func_149852_a(world, world.rand, x, y, z)) return false
      plant.func_149853_b(world, world.rand, x, y, z)
    } catch {
      case _: Exception => return false
    }
    true
  }

  def willTryCatalyze(world: World, x: Int, z: Int): Boolean = {
    val biome = world.getBiomeGenForCoords(x, z)
    val r = world.rand
    val temperature = biome.temperature
    val humidity = biome.rainfall
    possibilityMultiplier * (temperature * Math.abs(temperature) * (r.nextFloat() - 0.3F) + humidity * Math.abs(humidity) * (r.nextFloat() - 0.3F)) > 0.25F
  }
}
