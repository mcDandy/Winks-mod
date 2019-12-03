package cz.mcDandy.winksmod.Register;


import com.google.common.base.Supplier;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
	private final Supplier<ItemStack> iconSupplier;
	public ModItemGroup(final String name, final Supplier<ItemStack> iconSupplier) {
		super(name);
		this.iconSupplier = iconSupplier;
	}

	@Override
	public ItemStack createIcon() {
		return iconSupplier.get();
	}

}

	};
	
}