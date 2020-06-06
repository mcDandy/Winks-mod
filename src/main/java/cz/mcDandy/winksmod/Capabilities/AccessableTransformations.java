package cz.mcDandy.winksmod.Capabilities;

public class AccessableTransformations implements IAccessableTransformations {
    private int amount = 0;

    @Override
    public int getRawData() {
        return this.amount;
    }

    @Override
    public void setRawData(int amount) {
        this.amount = amount;
    }
    @Override
    public boolean[] getEnabledTransformations() {
        boolean[] enabled = new boolean[32];
        int a = getRawData();
        for(int i =0;i<32;i++)
        {
            enabled[i]=a%2==1;
            a=a>>1;
        }
        return enabled;
    }
    @Override
    public void setTransformation(int id,boolean enabled) {
        boolean[] transformations = getEnabledTransformations();
        transformations[id]=enabled;
        int a = 0;
        for(int i = 0; i<31;i++)
        {
            if(transformations[i]=true)
            {
                a++;
            }
            a=a<<1;
        }
        if(transformations[31]=true)
        {
            a++;
        }
    }

}