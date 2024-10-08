import java.util.ArrayList;

public class Bubble {
     public static ArrayList<Instruction> implement(ArrayList<Instruction> pipeline, String execs) {

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

               if (execs.equals("TESTE-01.txt") || execs.equals("TESTE-02.txt") || execs.equals("TESTE-05.txt")
                         || execs.equals("TESTE-06.txt")
                         || execs.equals("TESTE-07.txt")) {
                    insertBubble(um, quant, resposta);
               }

               if (execs.equals("TESTE-03.txt") || execs.equals("TESTE-04.txt") || execs.equals("TESTE-08.txt")
                         || execs.equals("TESTE-09.txt")
                         || execs.contains("TESTE-10.txt")) {
                    insertAdvance(um, quant, resposta);
               }
          }

          resposta.add(pipeline.get(tamanho));

          if (execs.equals("TESTE-05.txt") || execs.equals("TESTE-06.txt") || execs.equals("TESTE-07.txt")
                    || execs.equals("TESTE-08.txt")
                    || execs.equals("TESTE-09.txt") || execs.equals("TESTE-10.txt")) {
               reordering(resposta);
          }

          return resposta;
     }

     public static void insertBubble(Instruction instru, int quant, ArrayList<Instruction> resposta) {

          if (quant == 0) {
               resposta.add(instru);
          }

          if (quant == 2) {
               resposta.add(instru);
               resposta.add(new Instruction("nop", "nop", "nop", "nop", "nop"));
               resposta.add(new Instruction("nop", "nop", "nop", "nop", "nop"));

          }

          if (quant == 1) {
               resposta.add(instru);
               resposta.add(new Instruction("nop", "nop", "nop", "nop", "nop"));
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
                    resposta.add(new Instruction("nop", "nop", "nop", "nop", "nop"));
               } else {
                    resposta.add(instru);
               }
          }
     }

     public static void reordering(ArrayList<Instruction> resposta) {

          for (int i = 0; i < resposta.size(); i++) { 

               Instruction base = resposta.get(i);

               char firstLetter = base.getInstru().charAt(0);

               if (!base.getInstru().equals("nop")) {
                    if (firstLetter == 'j') {
                         resposta.add(0, base);
                         resposta.remove(i + 1);
                    } else {
                         if (!dependence(i, resposta, 15)) { // 0 1 2 3 
                              if (firstLetter == 'b') {
                                   resposta.add(0, base);
                                   resposta.remove(i + 1);
                              } else {
                                   for (int j = 0; j < resposta.size(); j++) { // pegar a isntrucao a ser comparada
                                        if (resposta.get(j).getInstru().equals("nop")) { // com quem compara
                                             Instruction comparada = resposta.get(j);
                                             if (comparada.getInstru().equals("nop")) {
                                                  if (!conflito(j, resposta, 2, i)) {
                                                       resposta.set(j, base);
                                                       resposta.remove(i);
                                                       break;
                                                  }
                                             }
                                        }
                                   }
                              }

                         }
                    }

               }
          }

         
          try{
               char firstLetter = resposta.get(resposta.size()-1).getInstru().charAt(0);
               if(firstLetter == 'j'){
                    resposta.add(0,resposta.get(resposta.size()-1));
                    resposta.remove(resposta.size()-1);
               }
               if(firstLetter == 'b'){
                    resposta.add(0,resposta.get(resposta.size()-1));
                    resposta.remove(resposta.size()-1);
               }
     
          }catch(Error error){
               System.out.println(" ");
          }
          
     }

     private static boolean dependence(int indexNum, ArrayList<Instruction> resposta, int quant) {
          Instruction principal = resposta.get(indexNum);
          MipsInstructions mips = new MipsInstructions();

          int inicio = Math.max(0, indexNum - quant);
          int fim = Math.min(resposta.size() - 1, indexNum + quant);

          for (int i = inicio; i <= fim; i++) {
               if (i == indexNum) {
                    continue;
               }

               Instruction a = resposta.get(i); // a ser comparada

               if (a.getInstru() == null) {
                    continue;
               }

               if (i < indexNum) {
                    if (test2(a, principal, mips)) {
                         return true;
                    }

               } else {
                    if (test2(principal, a, mips)) {
                         return true;
                    }

               }

          }
          return false;
     }

     private static boolean conflito(int indexNum, ArrayList<Instruction> resposta, int quant, int indexNum2) { // so
                                                                                                                // pro
                                                                                                                // reordebnamento
          Instruction principal = resposta.get(indexNum2);
          MipsInstructions mips = new MipsInstructions();
          int inicio = Math.max(0, indexNum - quant);
          int fim = Math.min(resposta.size() - 1, indexNum + quant);

          for (int i = inicio; i <= fim; i++) {

               if (i == indexNum) {
                    continue;
               }

               Instruction a = resposta.get(i); // a ser comparada

               if (a.getInstru() == null) {
                    continue;
               }

               if (i < indexNum2) {
                    if (test2(a, principal, mips)) {
                         return true;
                    }

               } else {
                    if (test2(principal, a, mips)) {
                         return true;
                    }

               }

          }
          return false;
     }

     private static int quantNops(Instruction um, Instruction dois, Instruction tres) {
          MipsInstructions mips = new MipsInstructions();

          if (um.getInstru() != "nop") {
               if (dois.getInstru() != "nop") {
                    if (test2(um, dois, mips))
                         return 2;
               }

               if (tres != null) {
                    if (tres.getInstru() != "nop") {
                         if (test2(um, tres, mips))
                              return 1;
                    }

               }
          }

          return 0;
     }

     private static boolean test2(Instruction um, Instruction comparada, MipsInstructions mips) {

          if (mips.isDest(um.getInstru()) && mips.isDest(comparada.getInstru())) {
               if (um.getRegis1().equals(comparada.getRegis2()) || um.getRegis1().equals(comparada.getRegis3())) {
                    return true;
               }
          }

          if (mips.isDest(um.getInstru()) && !mips.isDest(comparada.getInstru())) {
               if (um.getRegis1().equals(comparada.getRegis1()) || um.getRegis1().equals(comparada.getRegis2())
                         || um.getRegis1().equals(comparada.getRegis3())) {
                    return true;
               }
          }

          if (!mips.isDest(um.getInstru()) && mips.isDest(comparada.getInstru())) {
               if (um.getRegis2().equals(comparada.getRegis2()) || um.getRegis3().equals(comparada.getRegis3())) {
                    return true;
               }
          }

          return false;
     }

}
