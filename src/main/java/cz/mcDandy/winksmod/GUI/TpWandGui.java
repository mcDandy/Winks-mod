package cz.mcDandy.winksmod.GUI;

<<<<<<< HEAD
import org.lwjgl.input.Keyboard;
=======
import java.util.logging.Level;

>>>>>>> master
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

	double x, y, z;
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
<<<<<<< HEAD
		this.x = entity.posX;
		this.y = entity.posY;
		this.z = entity.posZ;
	  	this.entity = entity;
=======
		// this.x = entity.posX;
		// this.y = entity.posY;
		// this.z = entity.posZ;
		this.entity = playerIn;
>>>>>>> master
		this.xSize = 176;
		this.ySize = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation(Main.MODID+":solwand.png");
	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - 176) / 2;
		this.guiTop = (this.height - 166) / 2;
		// Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.guiLeft + 47, this.guiTop + 126, 86, 20, "Teleport"));
		
		Xpos = new GuiTextField(0, this.fontRenderer, guiLeft+38, guiTop+19, 120, 20);
		Xpos.setMaxStringLength(9);
		Xpos.setFocused(true);
		Xpos.setText("");
		
		
		Ypos = new GuiTextField(1, this.fontRenderer, guiLeft+38, guiTop+45, 120, 20);
		Ypos.setMaxStringLength(9);
		Ypos.setFocused(true);
		Ypos.setText("");
		
		Zpos = new GuiTextField(2, this.fontRenderer, guiLeft+39, guiTop+70, 120, 20);
		Zpos.setMaxStringLength(9);
		Zpos.setFocused(true);
		Zpos.setText("");

<<<<<<< HEAD
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
=======
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
>>>>>>> master
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawDefaultBackground();
	//	this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
<<<<<<< HEAD
	//	this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		zLevel = 100.0F;

		Xpos.drawTextBox();
		Ypos.drawTextBox();
		Zpos.drawTextBox();
		this.fontRenderer.drawString("X:", guiLeft+19, guiTop+20, -16777216);
		this.fontRenderer.drawString("Y:", guiLeft+19, guiTop+51, -16777216);
		this.fontRenderer.drawString("Z:", guiLeft+19, guiTop+75, -16777216);
		 super.drawScreen(mouseX, mouseY, partialTicks);
=======
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		Xpos.drawTextBox();
		Ypos.drawTextBox();
		Zpos.drawTextBox();
		this.buttonList.get(0).drawButton(mc, mouseX, mouseY, partialTicks);
		this.fontRenderer.drawString("X:", k + 19, l + 25, -16777216);
		this.fontRenderer.drawString("Y:", k + 19, l + 50, -16777216);
		this.fontRenderer.drawString("Z:", k + 19, l + 75, -16777216);
>>>>>>> master
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		try {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			Xpos.mouseClicked(mouseX, mouseY, mouseButton);
			Ypos.mouseClicked(mouseX, mouseY, mouseButton);
			Zpos.mouseClicked(mouseX, mouseY, mouseButton);
		} catch (Exception ignored) {
			Main.logger.log(Level.WARNING, ignored.toString());
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
			if(Character.isDigit(typedChar)||typedChar=='\b'||keyCode==127||keyCode==37||keyCode==39)
			{
				Xpos.textboxKeyTyped(typedChar, keyCode);
				Ypos.textboxKeyTyped(typedChar, keyCode);
				Zpos.textboxKeyTyped(typedChar, keyCode);
			}
		} catch (Exception ignored) {
			Main.logger.log(Level.SEVERE, ignored.toString());
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		 Keyboard.enableRepeatEvents(false);
	}

<<<<<<< HEAD

=======
	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - 176) / 2;
		this.guiTop = (this.height - 166) / 2;
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
>>>>>>> master

	@Override
	protected void actionPerformed(GuiButton button) {
		// Main.logger.log(Level.INFO, "Cliend clicked on button: " + button.id);
		if (button.id == this.buttonList.get(0).id) {
			double X = Double.parseDouble(Xpos.getText());
			double Y = Double.parseDouble(Ypos.getText());
			double Z = Double.parseDouble(Zpos.getText());
			Main.logger.log(Level.INFO, "Cliend clicked on Teleport button.");
			Main.NETWORK.sendToServer(new MessageTp(X, Y, Z));
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}