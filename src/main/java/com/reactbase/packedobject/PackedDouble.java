package com.reactbase.packedobject;

import java.nio.ByteBuffer;

public final class PackedDouble extends PackedObject {

	final double defaultValue;
	
	public PackedDouble(long offset) {
		this(offset, TypeDefaults.DOUBLE);
	}
	
	public PackedDouble(long offset, double defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(Object address, long ptr) {
		setDouble(address, ptr, defaultValue);
	}

	public double getDouble(Object address, long ptr) {
		if (address instanceof byte[]) {
			return getDoubleA((byte[]) address, ptr);
		}
		else if (address instanceof Long) {
			return getDoubleL((Long) address, ptr);
		}
		else if (address instanceof ByteBuffer) {
			return getDoubleB((ByteBuffer) address, ptr);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public double getDoubleA(byte[] blob, long ptr) {
		double value = UnsafeUtil.UNSAFE.getDouble(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset);
		return isLittleEndian ? value : Swapper.swapDouble(value);
	}
	
	public double getDoubleL(long address, long ptr) {
		double value = UnsafeUtil.UNSAFE.getDouble(address + offset + ptr);
		return isLittleEndian ? value : Swapper.swapDouble(value);
	}

	public double getDoubleB(ByteBuffer bb, long ptr) {
		return bb.getDouble((int)(offset + ptr));
	}
	
	public void setDouble(Object address, long ptr, double value) {
		if (address instanceof byte[]) {
			setDoubleA((byte[]) address, ptr, value);
		}
		else if (address instanceof Long) {
			setDoubleL((Long) address, ptr, value);
		}
		else if (address instanceof ByteBuffer) {
			setDoubleB((ByteBuffer) address, ptr, value);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}
		
	public void setDoubleA(byte[] blob, long ptr, double value) {
		value = isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeUtil.UNSAFE.putDouble(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset, value);
	}
	
	public void setDoubleL(long address, long ptr, double value) {
		value = isLittleEndian ? value : Swapper.swapDouble(value);
		UnsafeUtil.UNSAFE.putDouble(address + offset + ptr, value);
	}
	
	public void setDoubleB(ByteBuffer bb, long ptr, double value) {
		bb.putDouble((int)(offset + ptr), value);
	}	
	
	public int getTypeId() {
		return TypeRegistry.DOUBLE_ID;
	}
	
	public int sizeOf() {
		return TypeSizes.DOUBLE.sizeOf();
	}
	
}
