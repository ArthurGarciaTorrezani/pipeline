import java.util.ArrayList;

public class Bubble {
     public static ArrayList<Instruction> implement(ArrayList<Instruction> pipeline) {

          ArrayList<Instruction> resposta = new ArrayList<>();
          ArrayList<Instruction> nops = new ArrayList<>();

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
               Instruction[] instructionsNops = createNops(pipeline, i);
               insertBubble(um, quant, resposta, instructionsNops, nops);
          }

          resposta.add(pipeline.get(tamanho));
          reordering(resposta, nops);
          return resposta;
     }

     public static void insertBubble(Instruction instru, int quant, ArrayList<Instruction> resposta,
               Instruction[] instructionsNops, ArrayList<Instruction> nops) {

          if (quant == 0) {
               resposta.add(instru);
          }

          if (quant == 2) {
               resposta.add(instru);
               resposta.add(instructionsNops[0]);
               resposta.add(instructionsNops[1]);
               nops.add(instructionsNops[0]);
               nops.add(instructionsNops[1]);

          }

          if (quant == 1) {
               resposta.add(instru);
               resposta.add(instructionsNops[2]);
               nops.add(instructionsNops[2]);
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

     public static void reordering(ArrayList<Instruction> resposta, ArrayList<Instruction> nops) {
          for (int i = 0; i < resposta.size(); i++) {
               Instruction resp = resposta.get(i);
               if (resp.getInstru() == null) {
                    continue;
               }
               for (int j = 0; j < nops.size(); j++) {
                    Instruction nop = nops.get(j);

                    if (nop.getInstru() != null) {
                         continue;
                    }

                    if (nop.getRegis1().contains(resp.getRegis1())) {
                         continue;
                    }
                    if (nop.getRegis3().contains(resp.getRegis2())) {
                         continue;
                    }
                    if (nop.getRegis3().contains(resp.getRegis3())) {
                         continue;
                    }

                    resposta.remove(resp);
                    nops.set(j, resp);
                    i--;
                    break;
               }
          }

          for (int i = 0; i < resposta.size(); i++) {
               Instruction resp = resposta.get(i);
               if (resp.getInstru() != null) { // verifica se é nop
                    continue;
               }
               // se for nop faz isso
               for (int j = 0; j < nops.size(); j++) {
                    Instruction nop = nops.get(j);
                    if (nop.getInstru() != null) {
                         System.out.println("VALORES: "+nop.getAllValues());
                         resposta.set(i, nop);
                         nops.remove(nop);
                         break;
                    }

               }

          }

         
     }

     private static int quantNops(Instruction um, Instruction dois, Instruction tres) {

          if (dois.getRegis2().matches("^\\d+$")) {
               if (um.getRegis1().equals(dois.getRegis1()) || um.getRegis1().equals(dois.getRegis3())) {
                    return 2;
               }
          } else {
               if (um.getRegis1().equals(dois.getRegis2()) || um.getRegis1().equals(dois.getRegis3())) {
                    return 2;
               }
          }

          if (tres != null) {
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

          return 0;
     }

     private static Instruction[] createNops(ArrayList<Instruction> pipeline, int numIndex) {

          Instruction nop1 = new Instruction(null, null, null, null);
          Instruction nop2 = new Instruction(null, null, null, null);
          Instruction nop3 = new Instruction(null, null, null, null);

          Instruction antPrincipal = new Instruction("void", "void", "void", "void");
          Instruction posPosPrincipal = new Instruction("void", "void", "void", "void");

          if (numIndex != 0) {
               antPrincipal = pipeline.get(numIndex - 1);
          }

          if (numIndex < pipeline.size() - 2) {
               posPosPrincipal = pipeline.get(numIndex + 2);
          }

          Instruction principal = pipeline.get(numIndex);
          Instruction posPrincipal = pipeline.get(numIndex + 1);

          String destNop1 = "";
          String destNop2 = "";
          String destNop3 = "";

          String origemNop1 = "";
          String origemNop2 = "";
          String origemNop3 = "";

          // origem
          origemNop1 += "/" + principal.getRegis1() + "/" + antPrincipal.getRegis1();
          origemNop2 += "/" + principal.getRegis1();
          origemNop3 += "/" + principal.getRegis1() + "/" + antPrincipal.getRegis1();
          ///////////////
          // destino
          if (posPrincipal.getRegis2().matches("^\\d+$")) {
               destNop1 += "/" + posPrincipal.getRegis1() + "/" + posPrincipal.getRegis3();
               destNop2 += "/" + posPrincipal.getRegis1() + "/" + posPrincipal.getRegis3();
               destNop3 += "/" + posPrincipal.getRegis1() + "/" + posPrincipal.getRegis3();
          } else {
               destNop1 += "/" + posPrincipal.getRegis2() + "/" + posPrincipal.getRegis3();
               destNop2 += "/" + posPrincipal.getRegis2() + "/" + posPrincipal.getRegis3();
               destNop3 += "/" + posPrincipal.getRegis2() + "/" + posPrincipal.getRegis3();
          }

          if (posPosPrincipal != null) {
               if (posPosPrincipal.getRegis2().matches("^\\d+$")) {
                    destNop2 += "/" + posPosPrincipal.getRegis1() + "/" + posPosPrincipal.getRegis3();
                    destNop3 += "/" + posPosPrincipal.getRegis1() + "/" + posPosPrincipal.getRegis3();
               } else {
                    destNop2 += "/" + posPosPrincipal.getRegis2() + "/" + posPosPrincipal.getRegis3();
                    destNop3 += "/" + posPosPrincipal.getRegis2() + "/" + posPosPrincipal.getRegis3();
               }
          }

          nop1.setRegis1(destNop1);
          nop1.setRegis3(origemNop1);

          nop2.setRegis1(destNop2);
          nop2.setRegis3(origemNop2);

          nop3.setRegis1(destNop3);
          nop3.setRegis3(origemNop3);

          Instruction[] nops = { nop1, nop2, nop3 };
          return nops;
     }
}

// 1 1
// 1 2
// 1 2
// 1
// 1
