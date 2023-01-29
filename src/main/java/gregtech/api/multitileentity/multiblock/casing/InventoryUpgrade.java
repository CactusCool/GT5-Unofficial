package gregtech.api.multitileentity.multiblock.casing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.GT_Values.NBT;
import gregtech.api.multitileentity.interfaces.IMultiBlockController;
import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryUpgrade extends AdvancedCasing {
    public UUID mInventoryID;
    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    private String mInventoryName = "inventory";
    private int mInventorySize = 16;

    @Override
    protected void customWork(IMultiBlockController aTarget) {
        aTarget.registerInventory(mInventoryID.toString(), mInventorySize, INPUT);
        aTarget.registerInventory(mInventoryID.toString(), mInventorySize, OUTPUT);
    }

    @Override
    public String getTileEntityName() {
        return "gt.multitileentity.multiblock.inventory";
    }

    @Override
    public void readMultiTileNBT(NBTTagCompound aNBT) {
        super.readMultiTileNBT(aNBT);
        if (aNBT.hasKey(NBT.UPGRADE_INVENTORY_UUID)) {
            mInventoryID = UUID.fromString(aNBT.getString(NBT.UPGRADE_INVENTORY_UUID));
        } else {
            mInventoryID = UUID.randomUUID();
        }
        mInventorySize = aNBT.getInteger(NBT.UPGRADE_INVENTORY_SIZE);
        mInventoryName = "inventory" + GT_Values.VN[mTier];
    }

    @Override
    public void writeMultiTileNBT(NBTTagCompound aNBT) {
        super.writeMultiTileNBT(aNBT);
        aNBT.setString(NBT.UPGRADE_INVENTORY_UUID, mInventoryID.toString());
    }

    @Override
    protected void onBaseTEDestroyed() {
        super.onBaseTEDestroyed();
        final IMultiBlockController controller = getTarget(false);
        if (controller != null) {
            controller.unregisterInventory(mInventoryID.toString(), INPUT);
            controller.unregisterInventory(mInventoryID.toString(), OUTPUT);
        }
    }
}
