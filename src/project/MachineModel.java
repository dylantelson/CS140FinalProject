package project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel {
	public Map<Integer, Instruction> INSTRUCTIONS = new TreeMap<>();
	private CPU cpu = new CPU();
	private Memory memory = new Memory();
	private HaltCallback callback;
	private boolean withGUI;
	
	private class CPU {
		private int accumulator, instructionPointer, memoryBase;
		
		public void incrementIP(int val) { instructionPointer += val; }
		public void incrementIP() { instructionPointer += 1; }
	}
	
	public MachineModel() {
		this(false, null);
	}
	
	public MachineModel(boolean wg, HaltCallback hcb) {
		withGUI = wg;
		callback = hcb;
		
		//INSTRUCTION_MAP entry for "NOP"
        INSTRUCTIONS.put(0x0, arg -> {
        	cpu.incrementIP();
		});
        
        //INSTRUCTION_MAP entry for "LODI"
        INSTRUCTIONS.put(0x1, arg -> {
        	cpu.accumulator = arg;
        	cpu.incrementIP();
		});
        
        //INSTRUCTION_MAP entry for "LOD"
        INSTRUCTIONS.put(0x2, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	cpu.accumulator = arg1;
        	cpu.incrementIP();
		});
        
        //INSTRUCTION_MAP entry for "LODN"
        INSTRUCTIONS.put(0x3, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator = arg2;
        	cpu.incrementIP();
		});
		
        //INSTRUCTION_MAP entry for "STO"
        INSTRUCTIONS.put(0x4, arg -> {
        	memory.setData(cpu.memoryBase + arg, cpu.accumulator);
        	cpu.incrementIP();
		});
        
        //INSTRUCTION_MAP entry for "STON"
        INSTRUCTIONS.put(0x5, arg -> {
        	int val = memory.getData(cpu.memoryBase + arg);
        	memory.setData(cpu.memoryBase + val, cpu.accumulator);
        	cpu.incrementIP();
		});
        
        //INSTRUCTION_MAP entry for "JMPR"
        INSTRUCTIONS.put(0x6, arg -> {
        	cpu.instructionPointer += arg;
		});
        
        //INSTRUCTION_MAP entry for "JUMP"
        INSTRUCTIONS.put(0x7, arg -> {
        	cpu.instructionPointer += memory.getData(cpu.memoryBase + arg);
		});
        
      //INSTRUCTION_MAP entry for "JUMPI"
        INSTRUCTIONS.put(0x8, arg -> {
        	cpu.instructionPointer = arg;
		});
        
      //INSTRUCTION_MAP entry for "JMPZR"
        INSTRUCTIONS.put(0x9, arg -> {
        	if(cpu.accumulator == 0) cpu.instructionPointer += arg;
		});
        
      //INSTRUCTION_MAP entry for "JMPZ"
        INSTRUCTIONS.put(0xA, arg -> {
        	if(cpu.accumulator == 0)	cpu.instructionPointer += memory.getData(cpu.memoryBase + arg);
		});
        
      //INSTRUCTION_MAP entry for "JMPZI"
        INSTRUCTIONS.put(0xB, arg -> {
        	if(cpu.accumulator == 0)	cpu.instructionPointer = arg;
		});
        
        //
		//INSTRUCTION_MAP entry for "ADDI"
        INSTRUCTIONS.put(0xC, arg -> {
            cpu.accumulator += arg;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "ADD"
        INSTRUCTIONS.put(0xD, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            cpu.accumulator += arg1;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "ADDN"
        INSTRUCTIONS.put(0xE, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator += arg2;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "SUBI"
        INSTRUCTIONS.put(0xF, arg -> {
            cpu.accumulator -= arg;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "SUB"
        INSTRUCTIONS.put(0x10, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            cpu.accumulator -= arg1;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "SUBN"
        INSTRUCTIONS.put(0x11, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator -= arg2;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "MULI"
        INSTRUCTIONS.put(0x12, arg -> {
            cpu.accumulator *= arg;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "MUL"
        INSTRUCTIONS.put(0x13, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            cpu.accumulator *= arg1;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "MULN"
        INSTRUCTIONS.put(0x14, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator *= arg2;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "DIVI"
        INSTRUCTIONS.put(0x15, arg -> {
        	if(arg == 0) throw new DivideByZeroException();
            cpu.accumulator /= arg;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "DIV"
        INSTRUCTIONS.put(0x16, arg -> {
        	if(arg == 0) throw new DivideByZeroException();
            int arg1 = memory.getData(cpu.memoryBase+arg);
            cpu.accumulator /= arg1;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "DIVN"
        INSTRUCTIONS.put(0x17, arg -> {
        	if(arg == 0) throw new DivideByZeroException();
            int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator /= arg2;
            cpu.incrementIP(1);
        });
        
        //INSTRUCTION_MAP entry for "ANDI"
        INSTRUCTIONS.put(0x18, arg -> {
        	if(cpu.accumulator != 0 && arg != 0) {
        		cpu.accumulator = 1;
        	} else cpu.accumulator = 0;
        	cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "AND"
        INSTRUCTIONS.put(0x19, arg -> {
        	if(cpu.accumulator != 0 && memory.getData(cpu.memoryBase+arg) != 0) {
        		cpu.accumulator = 1;
        	} else cpu.accumulator = 0;
        	cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "NOT"
        INSTRUCTIONS.put(0x1A, arg -> {
        	if(cpu.accumulator == 0) {
        		cpu.accumulator = 1;
        	} else cpu.accumulator = 0;
        	cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "CMPL"
        INSTRUCTIONS.put(0x1B, arg -> {
        if(memory.getData(cpu.memoryBase+arg) < 0) {
        		cpu.accumulator = 1;
        } else cpu.accumulator = 0;
        	cpu.incrementIP(1);
        });

      //INSTRUCTION_MAP entry for "CMPZ"
        INSTRUCTIONS.put(0x1C, arg -> {
        if(memory.getData(cpu.memoryBase+arg) == 0) {
        		cpu.accumulator = 1;
        } else cpu.accumulator = 0;
        	cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "HALT"
        INSTRUCTIONS.put(0x1F, arg -> {
        callback.halt();
        });
	}

	public int getData(int index) {
		return memory.getData(index);
	}

	public void setData(int index, int value) {
		memory.setData(index, value);
	}
	
	public void setAccumulator(int accum) {
		cpu.accumulator = accum;
	}
	
	public int getAccumulator() {
		return cpu.accumulator;
	}
	
	public void setInstructionPointer(int ip) {
		cpu.instructionPointer = ip;
	}
	
	public int getInstuctionPointer() {
		return cpu.instructionPointer;
	}
	
	public void setMemoryBase(int mb) {
		cpu.memoryBase = mb;
	}
	
	public int getMemoryBase() {
		return cpu.memoryBase;
	}
	
	public Instruction get(int index) {
		return INSTRUCTIONS.get(index);
	}
	
}
