//Made with Blockbench
//Paste this code into your mod.

public class  extends ModelBase{
private final ModelRenderer Head;
private final ModelRenderer Body1;
private final ModelRenderer Body2;
private final ModelRenderer Body3;
private final ModelRenderer Body4;
private final ModelRenderer Body5;
private final ModelRenderer Body6;
private final ModelRenderer Body7;
private final ModelRenderer Body8;
private final ModelRenderer Body9;
private final ModelRenderer Body10;

public (){
        textureWidth=16;
        textureHeight=16;

        Head=new ModelRenderer(this);
        Head.setRotationPoint(0.0F,17.0F,0.0F);
        Head.cubeList.add(new ModelBox(Head,0,0,-8.0F,-9.0F,-8.0F,16,16,16,0.0F,false));

        Body1=new ModelRenderer(this);
        Body1.setRotationPoint(0.0F,-1.0F,-8.0F);
        Head.addChild(Body1);
        Body1.cubeList.add(new ModelBox(Body1,0,0,-7.0F,-7.0F,-14.0F,14,14,14,0.0F,false));

        Body2=new ModelRenderer(this);
        Body2.setRotationPoint(0.0F,0.0F,-14.0F);
        Body1.addChild(Body2);
        Body2.cubeList.add(new ModelBox(Body2,-40,0,-7.0F,-7.0F,-14.0F,14,14,14,0.0F,false));

        Body3=new ModelRenderer(this);
        Body3.setRotationPoint(0.0F,0.0F,-15.0F);
        Body2.addChild(Body3);
        Body3.cubeList.add(new ModelBox(Body3,0,0,-7.0F,-7.0F,-13.0F,14,14,14,0.0F,false));

        Body4=new ModelRenderer(this);
        Body4.setRotationPoint(0.0F,0.0F,-15.0F);
        Body3.addChild(Body4);
        Body4.cubeList.add(new ModelBox(Body4,-40,0,-7.0F,-7.0F,-12.0F,14,14,14,0.0F,false));

        Body5=new ModelRenderer(this);
        Body5.setRotationPoint(0.0F,0.0F,-12.0F);
        Body4.addChild(Body5);
        Body5.cubeList.add(new ModelBox(Body5,0,0,-7.0F,-7.0F,-14.0F,14,14,14,0.0F,false));

        Body6=new ModelRenderer(this);
        Body6.setRotationPoint(0.0F,0.0F,-14.0F);
        Body5.addChild(Body6);
        Body6.cubeList.add(new ModelBox(Body6,-40,0,-7.0F,-7.0F,-14.0F,14,14,14,0.0F,false));

        Body7=new ModelRenderer(this);
        Body7.setRotationPoint(0.0F,0.0F,-14.0F);
        Body6.addChild(Body7);
        Body7.cubeList.add(new ModelBox(Body7,0,0,-7.0F,-7.0F,-14.0F,14,14,14,0.0F,false));

        Body8=new ModelRenderer(this);
        Body8.setRotationPoint(0.0F,1.0F,-14.0F);
        Body7.addChild(Body8);
        Body8.cubeList.add(new ModelBox(Body8,-40,0,-7.0F,-8.0F,-14.0F,14,14,14,0.0F,false));

        Body9=new ModelRenderer(this);
        Body9.setRotationPoint(0.0F,-1.0F,-14.0F);
        Body8.addChild(Body9);
        Body9.cubeList.add(new ModelBox(Body9,0,0,-7.0F,-7.0F,-14.0F,14,14,14,0.0F,false));

        Body10=new ModelRenderer(this);
        Body10.setRotationPoint(0.0F,1.0F,-14.0F);
        Body9.addChild(Body10);
        Body10.cubeList.add(new ModelBox(Body10,-40,0,-7.0F,-8.0F,-14.0F,14,14,14,0.0F,false));
        }

@Override
public void render(Entity entity,float f,float f1,float f2,float f3,float f4,float f5){
        Head.render(f5);
        }
public void setRotationAngle(ModelRenderer modelRenderer,float x,float y,float z){
        modelRenderer.rotateAngleX=x;
        modelRenderer.rotateAngleY=y;
        modelRenderer.rotateAngleZ=z;
        }
        }