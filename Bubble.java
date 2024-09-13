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

               // insertAdvance(um, quant, resposta)
               // insertBubble(um, quant, resposta);
               insertAdvance(um, quant, resposta);
          }

          resposta.add(pipeline.get(tamanho));
          reordering(resposta);
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
               } else {
                    resposta.add(instru);
               }

          }
     }

     public static void reordering(ArrayList<Instruction> resposta) {

          for (int i = 0; i < resposta.size(); i++) {
               Instruction base = resposta.get(i);

               if (base.getInstru() != "nop") {
                    if (!dependence(i, resposta, 15)) {
                         for (int j = 0; j < resposta.size(); j++) {

                              if (resposta.get(j).getInstru() == "nop") {

                                   Instruction comparada = resposta.get(j);

                                   if (comparada.getInstru().equals("nop")) {
                                        if (!dependence2(j, resposta, 2,i)) {
                                             resposta.remove(base);
                                             resposta.set(j, base);
                                             break;
                                        }

                                   }

                              }

                         }
                    }
               }

          }

     }

     private static boolean dependence(int indexNum, ArrayList<Instruction> resposta, int quant) {
          Instruction principal = resposta.get(indexNum);

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
                    if (principal.getRegis2().matches("^\\d+$")) {
                         if (principal.getRegis1().equals(a.getRegis1())
                                   || principal.getRegis3().equals(a.getRegis1())) {
                              return true;
                         }
                    } else {
                         if (principal.getRegis2().equals(a.getRegis1())
                                   || principal.getRegis3().equals(a.getRegis1())) {
                              return true;
                         }
                    }
               } else {

                    if (a.getRegis2().matches("^\\d+$")) {
                         if (principal.getRegis1().equals(a.getRegis1())
                                   || principal.getRegis1().equals(a.getRegis3())) {
                              return true;
                         }
                    } else {
                         if (principal.getRegis1().equals(a.getRegis2())
                                   || principal.getRegis1().equals(a.getRegis3())) {
                              return true;
                         }
                    }
               }

          }
          return false;
     }

     private static boolean dependence2(int indexNum, ArrayList<Instruction> resposta, int quant, int indexNum2) {
          Instruction principal = resposta.get(indexNum2);

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
                    if (principal.getRegis2().matches("^\\d+$")) {
                         if (principal.getRegis1().equals(a.getRegis1())
                                   || principal.getRegis3().equals(a.getRegis1())) {
                              return true;
                         }
                    } else {
                         if (principal.getRegis2().equals(a.getRegis1())
                                   || principal.getRegis3().equals(a.getRegis1())) {
                              return true;
                         }
                    }
               } else {

                    if (a.getRegis2().matches("^\\d+$")) {
                         if (principal.getRegis1().equals(a.getRegis1())
                                   || principal.getRegis1().equals(a.getRegis3())) {
                              return true;
                         }
                    } else {
                         if (principal.getRegis1().equals(a.getRegis2())
                                   || principal.getRegis1().equals(a.getRegis3())) {
                              return true;
                         }
                    }
               }

          }
          return false;
     }

     private static int quantNops(Instruction um, Instruction dois, Instruction tres) {

          if (um.getInstru() != "nop") {
               if (dois.getInstru() != "nop") {
                    if (dois.getRegis2().matches("^\\d+$")) {
                         if (um.getRegis1().equals(dois.getRegis1()) || um.getRegis1().equals(dois.getRegis3())) {
                              return 2;
                         }
                    } else {
                         if (um.getRegis1().equals(dois.getRegis2()) || um.getRegis1().equals(dois.getRegis3())) {
                              return 2;
                         }
                    }
               }

               if (tres != null) {
                    if (tres.getInstru() != "nop") {
                         if (tres.getRegis2().matches("^\\d+$")) {
                              if (um.getRegis1().equals(tres.getRegis1()) || um.getRegis1().equals(tres.getRegis3())) {
                                   return 1;
                              }
                         } else {
                              if (um.getRegis1().equals(dois.getRegis2()) || um.getRegis1().equals(tres.getRegis3())) {
                                   return 1;
                              }
                         }
                    }

               }
          }

          return 0;
     }

}
