import java.util.ArrayList;

public class Bubble {
     public static ArrayList<Instruction> insertBubble(ArrayList<Instruction> pipeline) {
          ArrayList<Instruction> resposta = new ArrayList<>();
          int tamanho = pipeline.size()-1;
          for(int i = 0; i < tamanho;i++){
               Instruction um = pipeline.get(i);
               Instruction dois = pipeline.get(i+1);
               Instruction tres = null;

               if(i == pipeline.size()-2){
                    tres = null;
               }else{
                    tres = pipeline.get(i+2);
               }


               boolean igualA2 = false;
               boolean igualA3 = false;

               if(dois.getRegis2().matches(".*\\d+.*")){ // verifica se tem numero, se tiver ele compara onde tem registrador
                    if(um.getRegis1().equals(dois.getRegis1()) || um.getRegis1().equals(dois.getRegis3())){ // verirfica todos os registradores da 2 intrucao
                         igualA2 = true;
                    }
               }else{ // compara os registradores
                    if(um.getRegis1().equals(dois.getRegis2()) || um.getRegis1().equals(dois.getRegis3())){ // verirfica todos os registradores da 2 intrucao
                         igualA2 = true;
                    }
               }

               if(tres != null){
                    if(tres.getRegis2().matches(".*\\d+.*")){ // verifica se tem numero, se tiver ele compara onde tem registrador
                         if(um.getRegis1().equals(tres.getRegis1()) || um.getRegis1().equals(tres.getRegis3())){ // verirfica todos os registradores da 2 intrucao
                              igualA3 = true;
                         }
                    }else{ // compara os registradores
                         if(um.getRegis1().equals(dois.getRegis2()) || um.getRegis1().equals(tres.getRegis3())){ // verirfica todos os registradores da 2 intrucao
                              igualA3 = true;
                         }
                    }
               }
               
               if(igualA2){
                    resposta.add(um); // insere na lista da resposta
                    resposta.add(new Instruction("nop", "nop", "nop", "nop"));
                    resposta.add(new Instruction("nop", "nop", "nop", "nop"));
                    // coloca 2 nop
                    continue;
               }
               if(igualA3){
                    resposta.add(um); // insere na lista da resposta
                    resposta.add(new Instruction("nop", "nop", "nop", "nop"));
                    // coloca 1 nop
               }
          }
               resposta.add(pipeline.get(tamanho));
               return resposta;
     }
}

// add $t1, $s2, $s1
// lw $s2, 0($t1)

// add $s1, $s2, $s3
// sw $s1, 200($s4)

// lb $s1, 200($s2)
// add $s2, $s1, $s3

// add $s1, $s2, $s3
// add $s1, $s2, $s3