import java.util.ArrayList;

public class Bubble {
     public static ArrayList<Instruction> implement(ArrayList<Instruction> pipeline) {

          ArrayList<Instruction> resposta = new ArrayList<>();

          int tamanho = pipeline.size() - 1; // 2

          for (int i = 0; i < tamanho; i++) {
               Instruction um = pipeline.get(i); // lw $t0, 1200($t1)
               Instruction dois = pipeline.get(i + 1); // add $t0, $s2, $t0
               Instruction tres = null;

               if (i == pipeline.size() - 2) {
                    tres = null;
               } else {
                    tres = pipeline.get(i + 2); // sw $t0, 1200($t1)
               }

               int quant = quantNops(um, dois, tres);

               
               insertAdvance(um, quant, resposta);
               //insertBubble(um, quant, resposta);
          }
          resposta.add(pipeline.get(tamanho));
          return resposta;
     }

     public static void insertBubble(Instruction instru, int quant, ArrayList<Instruction> resposta) {

          if (quant == 0) {
               resposta.add(instru);
          }

          if (quant == 2) {
               resposta.add(instru);
               resposta.add(new Instruction("nop", "nop", "nop", "nop"));
               resposta.add(new Instruction("nop", "nop", "nop", "nop"));

          }

          if (quant == 1) {
               resposta.add(instru);
               resposta.add(new Instruction("nop", "nop", "nop", "nop"));

          }

     }

     public static void insertAdvance(Instruction instru, int quant, ArrayList<Instruction> resposta) {

          MipsInstructions advance = new MipsInstructions();

          if (quant == 0) {
               resposta.add(instru);

          }

          if (quant == 1) {
               resposta.add(instru);
          }

          if (quant == 2) {
               if (advance.isMem(instru.getInstru())) {
                    resposta.add(instru);
                    resposta.add(new Instruction("nop", "nop", "nop", "nop"));
               }else{
                    resposta.add(instru);
               }
               

          }
     }

     private static int quantNops(Instruction um, Instruction dois, Instruction tres) {
          if (dois.getRegis2().matches(".*\\d+.*")) {
               if (um.getRegis1().equals(dois.getRegis1()) || um.getRegis1().equals(dois.getRegis3())) {
                    return 2;
               }
          } else {
               if (um.getRegis1().equals(dois.getRegis2()) || um.getRegis1().equals(dois.getRegis3())) {
                    return 2;
               }
          }

          if (tres != null) {
               if (tres.getRegis2().matches(".*\\d+.*")) {
                    if (um.getRegis1().equals(tres.getRegis1()) || um.getRegis1().equals(tres.getRegis3())) {
                         return 1;
                    }
               } else {
                    if (um.getRegis1().equals(dois.getRegis2()) || um.getRegis1().equals(tres.getRegis3())) {
                         return 1;
                    }
               }
          }

          return 0;
     }
}
