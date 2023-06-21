package Memory_Value;

public abstract class MemoryValue {
	
	protected final int value;
	protected final boolean[] bitArray;
	
	protected MemoryValue(int memoryValue) {
		value = memoryValue;
		bitArray = new boolean[8];
		for(int i = 0; i < 8; i++) {
			bitArray[i] = getBit(i);
		}
	}
	
	@Override
	public int hashCode() {
		return value;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final MemoryValue other = (MemoryValue) obj;

        if (this.value != other.value) {
            return false;
        }

        return true;
    }
	
	@Override
	public String toString() {
		return value + "";
	}
	
	private boolean getBit(int bitIndex) {
		return ((value >> bitIndex) & 1) == 1;
	}
	
	public boolean[] getBitArray() {
		boolean[] returnArray = new boolean[8];
		for(int i = 0; i < 8; i++) {
			returnArray[i] = bitArray[i];
		}
		return returnArray;
	}
	
	public int getValue() {
		return value;
	}
	
	protected static int combineValues(int firstValue, int secondValue) {
		return (firstValue + secondValue) % 0x100;
	}	

}
