import java.util.ArrayList;
import java.util.List;

public class MipsInstructions {

    // Listas para armazenar as instruções de destino e de origem
    private List<String> destinationInstructions;
    private List<String> sourceInstructions;
    private List<String> advanceExec;

    public MipsInstructions() {
        destinationInstructions = new ArrayList<>();
        sourceInstructions = new ArrayList<>();
        advanceExec = new ArrayList<>();

        destinationInstructions.add("lb");
        destinationInstructions.add("lh");
        destinationInstructions.add("lwl");
        destinationInstructions.add("lw");
        destinationInstructions.add("lbu");
        destinationInstructions.add("lhu");
        destinationInstructions.add("lwr");
        destinationInstructions.add("add");
        destinationInstructions.add("addu");
        destinationInstructions.add("sub");
        destinationInstructions.add("subu");
        destinationInstructions.add("and");
        destinationInstructions.add("or");
        destinationInstructions.add("xor");
        destinationInstructions.add("nor");
        destinationInstructions.add("slt");
        destinationInstructions.add("sltu");
        destinationInstructions.add("addi");
        destinationInstructions.add("addiu");
        destinationInstructions.add("slti");
        destinationInstructions.add("sltiu");
        destinationInstructions.add("andi");
        destinationInstructions.add("ori");
        destinationInstructions.add("xori");
        destinationInstructions.add("lui");
        destinationInstructions.add("sll");
        destinationInstructions.add("srl");
        destinationInstructions.add("sra");
        destinationInstructions.add("sllv");
        destinationInstructions.add("srlv");
        destinationInstructions.add("srav");
        destinationInstructions.add("mfhi");
        destinationInstructions.add("mflo");
        destinationInstructions.add("jalr");

        sourceInstructions.add("sw");
        sourceInstructions.add("sb");
        sourceInstructions.add("sh");
        sourceInstructions.add("swl");
        sourceInstructions.add("swr");
        sourceInstructions.add("mthi");
        sourceInstructions.add("mtlo");
        sourceInstructions.add("mult");
        sourceInstructions.add("multu");
        sourceInstructions.add("div");
        sourceInstructions.add("divu");
        sourceInstructions.add("jr");
        sourceInstructions.add("bltz");
        sourceInstructions.add("bgez");
        sourceInstructions.add("bltzal");
        sourceInstructions.add("bgezal");
        sourceInstructions.add("j");
        sourceInstructions.add("jal");
    }

    public boolean isDest(String instru) {
        if (destinationInstructions.contains(instru)) {
            return true;
        }
        return false;

    }

    public boolean isExec(String instru) {
        if (advanceExec.contains(instru)) {
            return true;
        }
        return false;
    }
}
