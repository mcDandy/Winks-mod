package cz.mcDandy.winksmod.GUI;

import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import cz.mcDandy.winksmod.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
	GuiButton tp;

	public TpWandGui(World world, EntityPlayer playerIn) {
		super();
		// this.x = entity.posX;
		// this.y = entity.posY;
		// this.z = entity.posZ;
		this.entity = playerIn;
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
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		Xpos.drawTextBox();
		Ypos.drawTextBox();
		Zpos.drawTextBox();
		this.buttonList.get(0).drawButton(mc, mouseX, mouseY, partialTicks);
		this.fontRenderer.drawString("X:", k + 19, l + 25, -16777216);
		this.fontRenderer.drawString("Y:", k + 19, l + 50, -16777216);
		this.fontRenderer.drawString("Z:", k + 19, l + 75, -16777216);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		try {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			Xpos.mouseClicked(mouseX, mouseY, mouseButton);
			Ypos.mouseClicked(mouseX, mouseY, mouseButton);
			Zpos.mouseClicked(mouseX, mouseY, mouseButton);
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
		this.buttonList.add(new GuiButton(0, 47, 126, 86, 20, "Teleport"));

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
		if (button.id == tp.id) {
			double X = Double.parseDouble(Xpos.getText());
			double Y = Double.parseDouble(Ypos.getText());
			double Z = Double.parseDouble(Zpos.getText());
			Main.logger.log(Level.INFO, "Cliend clicked on Teleport button.");
			Main.NETWORK.sendTo(new MessageTp(X, Y, Z), (EntityPlayerMP) entity);
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