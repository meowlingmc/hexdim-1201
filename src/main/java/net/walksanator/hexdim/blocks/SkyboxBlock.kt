package net.walksanator.hexdim.blocks

import at.petrak.hexcasting.ktxt.markHurt
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.walksanator.hexdim.patterns.dim.OpBanish

class SkyboxBlock(settings: Settings) : Block(settings) {
    override fun onBreak(world: World, pos: BlockPos?, state: BlockState?, player: PlayerEntity) {
        super.onBreak(world, pos, state, player)
        if (world.isClient) {return} //teleporting won't do anything on client
        if (player.isCreative) {return} //you can build with these in creative (and give yourself a staff to escape)
        OpBanish.banish(world.server!!.getWorld(World.OVERWORLD)!!,player) // LET THEM BE FREE
        player.health = (if (player.health > 5) {player.health-4} else {1}).toFloat()
        player.markHurt()
        player.addStatusEffect(
            StatusEffectInstance(StatusEffects.NAUSEA,600,3,false,true)
        )
        world.setBlockState(pos,state) //we put the block back where it came from (help me)
    }
}