package us.fusu.io;

public class IO {
	
	private static Object[] linearInput;
	private static int linearInputIndex;
	
	public static <V> void loadInput(V[] argInput) {
		linearInput = argInput;
		linearInputIndex = 0;
	}
	
	public static int readInt() {
		return (Integer) linearInput[linearInputIndex++];
	}
	
	public static boolean hasMore() {
		return linearInput == null ? false : linearInputIndex < linearInput.length;
	}

	public static void write(String argStr) {
		System.out.print(argStr);
	}
	
	public static void writeLn(String argStr) {
		System.out.println(argStr);
	}
	
	public static void writeLn(Object argObject) {
		System.out.println(argObject);
	}
	
	public static void writeLn() {
		System.out.println();
	}
}
