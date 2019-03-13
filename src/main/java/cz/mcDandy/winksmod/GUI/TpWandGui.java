package cz.mcDandy.winksmod.GUI;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TpWandGui extends GuiScreen {

	// int x, y, z;
	EntityPlayer entity;
	GuiTextField Xpos;
	GuiTextField Ypos;
	GuiTextField Zpos;
	private int guiLeft;
	private int guiTop;
	private int xSize;
	private int ySize;

	public TpWandGui(World world, EntityPlayer entity) {
		super();
		// this.x = entity.posX;
		// this.y = entity.posY;
		// this.z = entity.posZ;
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("tpwandgui.png");

	/*
	 * @Override public void drawScreen(int mouseX, int mouseY, float partialTicks)
	 * { this.drawDefaultBackground(); super.drawScreen(mouseX, mouseY,
	 * partialTicks); // this.renderHoveredToolTip(mouseX, mouseY); }
	 */
	@Override
	public void drawScreen(int par2, int par3, float par1) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		zLevel = 100.0F;

		Xpos.drawTextBox();
		Ypos.drawTextBox();
		Zpos.drawTextBox();
		this.fontRenderer.drawString("X:", 18, 20, -16777216);
		this.fontRenderer.drawString("Y:", 19, 51, -16777216);
		this.fontRenderer.drawString("Z:", 19, 75, -16777216);

	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		try {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			Xpos.mouseClicked(mouseX - guiLeft, mouseY - guiTop, mouseButton);
			Ypos.mouseClicked(mouseX - guiLeft, mouseY - guiTop, mouseButton);
			Zpos.mouseClicked(mouseX - guiLeft, mouseY - guiTop, mouseButton);
		} catch (Exception ignored) {
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		Xpos.updateCursorCounter();
		Ypos.updateCursorCounter();
		Zpos.updateCursorCounter();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		try {
			super.keyTyped(typedChar, keyCode);
			Xpos.textboxKeyTyped(typedChar, keyCode);
			Ypos.textboxKeyTyped(typedChar, keyCode);
			Zpos.textboxKeyTyped(typedChar, keyCode);
		} catch (Exception ignored) {
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		// Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - 176) / 2;
		this.guiTop = (this.height - 166) / 2;
		// Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.guiLeft + 47, this.guiTop + 126, 86, 20, "Teleport"));
		Xpos = new GuiTextField(0, this.fontRenderer, this.guiLeft + 38, this.guiTop + 19, 120, 20);

		Xpos.setMaxStringLength(32767);
		Xpos.setFocused(true);
		Xpos.setText("");
		Ypos = new GuiTextField(1, this.fontRenderer, this.guiLeft + 38, this.guiTop + 45, 120, 20);

		Ypos.setMaxStringLength(32767);
		Ypos.setFocused(true);
		Ypos.setText("");
		Zpos = new GuiTextField(2, this.fontRenderer, this.guiLeft + 39, this.guiTop + 70, 120, 20);

		Zpos.setMaxStringLength(32767);
		Zpos.setFocused(true);
		Zpos.setText("");

	}

	@Override
	protected void actionPerformed(GuiButton button) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		World world = server.getWorld(entity.dimension);
		if (button.id == 0) {

			if (entity instanceof EntityLivingBase)

				entity.setPositionAndUpdate(Integer.parseInt(Xpos.getText().trim()),
						Integer.parseInt(Ypos.getText().trim()), Integer.parseInt(Zpos.getText().trim()));

		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}