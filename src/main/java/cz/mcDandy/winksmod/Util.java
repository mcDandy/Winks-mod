package cz.mcDandy.winksmod;

public class Util {
    /**
     * Forge really likes annotation magic. This makes static analysis tools shut up.
     */
    @SuppressWarnings("ConstantConditions")
    public static <T> T Null() {
        return null;
    }
}