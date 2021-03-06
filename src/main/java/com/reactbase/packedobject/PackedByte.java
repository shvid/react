package com.reactbase.packedobject;

import java.nio.ByteBuffer;

public final class PackedByte extends PackedObject {

	final byte defaultValue;

	public PackedByte(long offset) {
		this(offset, TypeDefaults.BYTE);
	}

	public PackedByte(long offset, byte defaultValue) {
		super(offset);
		this.defaultValue = defaultValue;
	}
	
	public void format(Object address, long ptr) {
		setByte(address, ptr, defaultValue);
	}

	public byte getByte(Object address, long ptr) {
		if (address instanceof byte[]) {
			return getByteA((byte[]) address, ptr);
		}
		else if (address instanceof Long) {
			return getByteL((Long) address, ptr);
		}
		else if (address instanceof ByteBuffer) {
			return getByteB((ByteBuffer) address, ptr);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public byte getByteA(byte[] blob, long ptr) {
		return UnsafeUtil.UNSAFE.getByte(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset);
	}
	
	public byte getByteL(long address, long ptr) {
		return UnsafeUtil.UNSAFE.getByte(address + offset + ptr);
	}
	
	public byte getByteB(ByteBuffer bb, long ptr) {
		return bb.get((int)(offset + ptr));
	}

	public void setByte(Object address, long ptr, byte value) {
		if (address instanceof byte[]) {
			setByteA((byte[]) address, ptr, value);
		}
		else if (address instanceof Long) {
			setByteL((Long) address, ptr, value);
		}
		else if (address instanceof ByteBuffer) {
			setByteB((ByteBuffer) address, ptr, value);
		}
		else {
			throw new IllegalArgumentException("unknown object " + address);
		}
	}

	public void setByteA(byte[] blob, long ptr, byte value) {
		UnsafeUtil.UNSAFE.putByte(blob, offset + ptr + UnsafeUtil.byteArrayBaseOffset, value);
	}	
	
	public void setByteL(long address, long ptr, byte value) {
		UnsafeUtil.UNSAFE.putByte(address + offset + ptr, value);
	}
	
	public void setByteB(ByteBuffer bb, long ptr, byte value) {
		bb.put((int)(offset + ptr), value);
	}
	
	public int getTypeId() {
		return TypeRegistry.BYTE_ID;
	}
	
	public int sizeOf() {
		return TypeSizes.BYTE.sizeOf();
	}
	
}
