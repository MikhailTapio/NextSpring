package committee.nova.ns.common.util

import committee.nova.ns.common.config.CommonConfig._
import net.minecraft.block.{Block, IGrowable}
import net.minecraft.entity.item.EntityItem
import net.minecraft.util.BlockPos
import net.minecraft.world.World

object Utilities {
  def catalyze(entity: EntityItem): Boolean = {
    val world = entity.worldObj
    val plantPos = entity.getPosition.add(0, 0.2, 0)
    val blockIn = world.getBlockState(plantPos).getBlock
    if (influencedByBiome && !willTryCatalyze(world, plantPos)) return false
    if (tryCatalyze(world, blockIn, plantPos)) return true
    val dirtPos = plantPos.down()
    val dirt = world.getBlockState(dirtPos).getBlock
    tryCatalyze(world, dirt, dirtPos)
  }

  def tryCatalyze(world: World, block: Block, pos: BlockPos): Boolean = {
    if (!block.isInstanceOf[IGrowable]) return false
    val plant = block.asInstanceOf[IGrowable]
    try {
      if (!plant.canGrow(world, pos, world.getBlockState(pos), world.isRemote)) return false
      if (respectVanillaCriteria && !plant.canUseBonemeal(world, world.rand, pos, world.getBlockState(pos))) return false
      plant.grow(world, world.rand, pos, world.getBlockState(pos))
    } catch {
      case _: Exception => return false
    }
    true
  }

  def willTryCatalyze(world: World, pos: BlockPos): Boolean = {
    val biome = world.getBiomeGenForCoords(pos)
    val r = world.rand
    val temperature = biome.temperature
    val humidity = biome.rainfall
    possibilityMultiplier * (temperature * Math.abs(temperature) * (r.nextFloat() - 0.3F) + humidity * Math.abs(humidity) * (r.nextFloat() - 0.3F)) > 0.25F
  }
}
