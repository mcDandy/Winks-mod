package cz.mcDandy.winksmod.Capabilities;

public interface IAccessableTransformations {
    int getRawData();
    void setRawData(int amount);
    boolean[] getEnabledTransformations();
    void setTransformation(int id,boolean enabled);
}
