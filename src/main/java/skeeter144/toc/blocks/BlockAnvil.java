package skeeter144.toc.blocks;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.sounds.Sounds;

public class BlockAnvil extends BlockTileEntity<TileEntityAnvil> {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
	protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
	protected static final Logger LOGGER = LogManager.getLogger();

	final ArrayList<Item> ingots = new ArrayList<Item>();

	public BlockAnvil(String name) {
		super(Properties.create(Material.ANVIL).hardnessAndResistance(1000, 1000), name);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
		ingots.add(TOCItems.ingot_bronze);
		ingots.add(TOCItems.ingot_iron);
		ingots.add(TOCItems.ingot_steel);
		ingots.add(TOCItems.ingot_gold);
		ingots.add(TOCItems.ingot_mithril);
		ingots.add(TOCItems.ingot_adamantite);
		ingots.add(TOCItems.ingot_runite);
		ingots.add(TOCItems.ingot_dragonstone);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		boolean isServer = !worldIn.isRemote;

		if (ingots.contains(player.getHeldItem(hand).getItem())) {
			TileEntityAnvil anvil = (TileEntityAnvil) worldIn.getTileEntity(pos);
			if (isServer) {
				if (anvil.anvilOwner != null) {
					if (!anvil.anvilOwner.equals(player.getUniqueID())) {
						player.sendMessage(new StringTextComponent("Someone else is using that anvil! Find an empty one"));
						return false;
					}
				}
			}

			Item item = player.getHeldItem(hand).getItem();
			if (anvil.addedIngots == 0) {
				anvil.ingot = player.getHeldItem(hand).getItem();
				anvil.sendUpdates();
			}

			if (item.equals(anvil.ingot) && anvil.addedIngots < 6) {
				++anvil.addedIngots;
				anvil.anvilOwner = player.getUniqueID();
				anvil.sendUpdates();
				if (isServer) {
					player.getHeldItem(hand).shrink(1);
					worldIn.playSound(null, pos, Sounds.ingot_place, SoundCategory.MASTER, 1, 1);
				}
			} else {
				if (isServer) {
					if (!item.equals(anvil.ingot)) {
						player.sendMessage(new StringTextComponent("You already have "
								+ new ItemStack(anvil.ingot).getDisplayName()
								+ "s on the anvil. Either take them off by right clicking with an empty hand, or craft those ingots first."));
					} else {
						player.sendMessage(new StringTextComponent("You already have 6 ingots on the anvil!"));
					}
				}
			}
		}
		return true;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn) {
		boolean isServer = !worldIn.isRemote;

		TileEntityAnvil anvil = (TileEntityAnvil) worldIn.getTileEntity(pos);
		if (playerIn.getCooledAttackStrength(1) < 1)
			return;

		boolean holdingHammer = playerIn.getHeldItemMainhand().getItem().equals(TOCItems.blacksmith_hammer);

		if (anvil == null) {
			System.out.println("null anvil client!");
			return;
		}

		if (holdingHammer && (anvil.anvilOwner == null || anvil.anvilOwner.equals(playerIn.getUniqueID()))) {
			if (!isServer)
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1,
						TOCMain.rand.nextFloat() / 5 + 1);
			else {
				Network.INSTANCE.sendToAll(new SpawnParticlesPKT(ParticleSystem.ANVIL_STRUCK_PARTICLE_SYSTEM, pos));
				worldIn.playSound(null, pos, Sounds.anvil_strike, SoundCategory.MASTER, 1,
						TOCMain.rand.nextFloat() / 5 + 1);

				if (anvil.anvilOwner.equals(playerIn.getUniqueID()))
					anvil.hammerStruck(playerIn);
			}

			if (!holdingHammer) {
				if (!isServer)
					return;

				if (anvil.addedIngots > 0)
					playerIn.addItemStackToInventory(new ItemStack(anvil.ingot));

				if (--anvil.addedIngots == 0) {
					anvil.ingot = null;
					anvil.anvilOwner = null;
				}
				anvil.sendUpdates();
			}
		}
	}

	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().rotateY());
	}

	public boolean isFullCube(BlockState state) {
		return false;
	}

	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().with(FACING, Direction.byIndex(meta & 3));
	}

	public int getMetaFromState(BlockState state) {
		return ((Direction) state.get(FACING)).getHorizontalIndex();
	}

	public BlockState withRotation(BlockState state, Rotation rot) {
		return state.getBlock() != this ? state : state.with(FACING, rot.rotate((Direction) state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public Class<TileEntityAnvil> getTileEntityClass() {
		return TileEntityAnvil.class;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityAnvil();
	}
}
