package project;

import java.util.Map;
import java.util.TreeMap;

public class InstrMap {
	public static Map<String, Integer> toCode = new TreeMap<>();
	public static Map<Integer, String> toMnemonic = new TreeMap<>();

	static {
		toCode.put("NOP", 0);
		toCode.put("LODI", 1);
		toCode.put("LOD", 2);
		toCode.put("LODN", 3);

		toCode.put("STO", 4);
		toCode.put("STON", 5);
		toCode.put("JMPR", 6);
		toCode.put("JUMP", 7);

		toCode.put("JUMPI", 8);
		toCode.put("JMPZR", 9);
		toCode.put("JMPZ", 10);
		toCode.put("JMPZI", 11);

		toCode.put("ADDI", 12);
		toCode.put("ADD", 13);
		toCode.put("ADDN", 14);
		toCode.put("SUBI", 15);

		toCode.put("SUB", 16);
		toCode.put("SUBN", 17);
		toCode.put("MULI", 18);
		toCode.put("MUL", 19);

		toCode.put("MULN", 20);
		toCode.put("DIVI", 21);
		toCode.put("DIV", 22);
		toCode.put("DIVN", 23);

		toCode.put("ANDI", 24);
		toCode.put("AND", 25);
		toCode.put("NOT", 26);

		toCode.put("CMPL", 27); // can use decimals instead of hex: 0x1B = 27, 0X1C = 28, etc.
		toCode.put("CMPZ", 28);
		toCode.put("HALT", 31);

		for (String s : toCode.keySet()) {
			toMnemonic.put(toCode.get(s), s);
		}
	}
}