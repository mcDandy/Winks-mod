

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ActionResult;
import net.minecraft.server.MinecraftServer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import net.minecraft.init.Items;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Blocks;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;

import java.util.Random;

public class SolariaWand {

	public SolariaWand() {
	}

	public static Item block;
	public static Object instance;

	public void load(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.register(block, 0, new ModelResourceLocation("testenvironmentmod:solariaWand", "inventory"));
		}

	}

	@SideOnly(Side.CLIENT)
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityArrowCustom.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(),
				new ItemStack(Blocks.GOLD_BLOCK).getItem(), Minecraft.getMinecraft().getRenderItem()));
	}

	public void generateNether(World world, Random random, int chunkX, int chunkZ) {
	}

	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
	}

	public int addFuel(ItemStack fuel) {
		return 0;
	}

	public void serverLoad(FMLServerStartingEvent event) {
	}

	public void preInit(FMLPreInitializationEvent event) {
		int entityID = MathHelper.getRandomUUID().hashCode();
		EntityRegistry.registerModEntity(new ResourceLocation("testenvironmentmod:entitybulletsolariaWand"), EntityArrowCustom.class,
				"entitybulletsolariaWand", entityID, instance, 64, 1, true);
	}

	static {
		block = (new ItemgGUN());
	}

	static class ItemgGUN extends Item {

		public ItemgGUN() {
			super();
			setMaxDamage(100);
			maxStackSize = 1;
			setFull3D();
			setUnlocalizedName("solariaWand");
			setRegistryName("solariaWand");
			GameRegistry.register(this);
			setCreativeTab(CreativeTabs.COMBAT);
		}

		@Override
		public int getMaxItemUseDuration(ItemStack par1ItemStack) {
			return 72000;
		}

		@Override
		public ActionResult<ItemStack> onItemRightClick(World par2World, final EntityPlayer par3EntityPlayer, EnumHand hand) {

			par3EntityPlayer.setActiveHand(hand);

			ItemStack par1ItemStack = par3EntityPlayer.getHeldItem(hand);

			boolean flag = par3EntityPlayer.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;

			if (flag || par3EntityPlayer.inventory.hasItemStack(new ItemStack(Items.GOLD_NUGGET))) {
				float f = 1.0F;

				EntityArrowCustom entityarrow = new EntityArrowCustom(par2World, par3EntityPlayer);

				entityarrow.setThrowableHeading(par3EntityPlayer.getLookVec().xCoord, par3EntityPlayer.getLookVec().yCoord,
						par3EntityPlayer.getLookVec().zCoord, f * 2.0F, 0);
				entityarrow.setIsCritical(true);
				entityarrow.setDamage(5.0);
				entityarrow.setKnockbackStrength(5);

				if (true) {
					entityarrow.setFire(100);
				}

				par1ItemStack.damageItem(1, par3EntityPlayer);
				int i = (int) par3EntityPlayer.posX;
				int j = (int) par3EntityPlayer.posY;
				int k = (int) par3EntityPlayer.posZ;
				par2World.playSound((EntityPlayer) null, (double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D,
						(net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY
								.getObject(new ResourceLocation(("entity.arrow.shoot"))), SoundCategory.NEUTRAL, 1.0F, 1.0F
								/ (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				if (flag) {
					entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
				} else {
					par3EntityPlayer.inventory.clearMatchingItems(new ItemStack(Items.GOLD_NUGGET).getItem(), -1, 1, null);
				}

				if (!par2World.isRemote) {
					par2World.spawnEntity(entityarrow);
				}
				World world = par2World;
				EntityPlayer entity = par3EntityPlayer;

				if (true) {
					System.out.println("used");
				}

			}

			return new ActionResult(EnumActionResult.SUCCESS, par1ItemStack);
		}

		@Override
		public EnumAction getItemUseAction(ItemStack par1ItemStack) {
			return EnumAction.BOW;
		}

	}

	public static class EntityArrowCustom extends EntityTippedArrow {
		public EntityArrowCustom(World a) {
			super(a);
		}

		public EntityArrowCustom(World worldIn, double x, double y, double z) {
			super(worldIn, x, y, z);
		}

		public EntityArrowCustom(World worldIn, EntityLivingBase shooter) {
			super(worldIn, shooter);
		}

		@Override
		public void onCollideWithPlayer(EntityPlayer entity) {
			super.onCollideWithPlayer(entity);
			int i = MathHelper.floor(this.getEntityBoundingBox().minX + 0.001D);
			int j = MathHelper.floor(this.getEntityBoundingBox().minY + 0.001D);
			int k = MathHelper.floor(this.getEntityBoundingBox().minZ + 0.001D);
			World world = this.world;

			if (true) {
				System.out.println("player hit");
			}

		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			int i = MathHelper.floor(this.getEntityBoundingBox().minX + 0.001D);
			int j = MathHelper.floor(this.getEntityBoundingBox().minY + 0.001D);
			int k = MathHelper.floor(this.getEntityBoundingBox().minZ + 0.001D);
			World world = this.world;
			Entity entity = (Entity) shootingEntity;

			if (true) {
				if (entity instanceof EntityPlayerMP) {
					MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance();
					if (minecraftserver != null)
						minecraftserver
								.getCommandManager()
								.executeCommand(
										(EntityPlayerMP) entity,
										"summon fireworks_rocket ~ ~0 ~ {LifeTime:0,FireworksItem:{id:fireworks,Count:1,tag:{Fireworks:{Explosions:[{Type:0,Colors:[16711680],FadeColors:[16768256]}]}}}}");
				}
			}

			if (getBlock(this.world, i, j, k) != Blocks.AIR || getBlock(this.world, i, j - 1, k) != Blocks.AIR
					|| getBlock(this.world, i, j + 1, k) != Blocks.AIR || getBlock(this.world, i + 1, j, k) != Blocks.AIR
					|| getBlock(this.world, i - 1, j, k) != Blocks.AIR || getBlock(this.world, i, j, k + 1) != Blocks.AIR
					|| getBlock(this.world, i, j, k - 1) != Blocks.AIR) {

				if (true) {
					world.createExplosion((Entity) null, i, j, k, 3, true);
				}

				this.kill();
			}

		}

		public Block getBlock(World par1World, int i, int j, int k) {
			return par1World.getBlockState(new BlockPos(i, j, k)).getBlock();
		}

	}

}
