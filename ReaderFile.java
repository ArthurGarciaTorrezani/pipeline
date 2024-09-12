import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReaderFile {
     public static void Reader() {
          String caminhoDoArquivo = "teste.txt";
          ArrayList<Instruction> pipeline = new ArrayList<Instruction>();
          MipsInstructions mips = new MipsInstructions();
          try {
               File arq = new File(caminhoDoArquivo);

               if (!arq.exists()) {
                    System.out.println("O arquivo não existe!");
                    return;
               }

               Scanner scanner = new Scanner(arq);

               Instruction instruction;

               while (scanner.hasNextLine()) {

                    String line = scanner.nextLine();

                    StringTokenizer regis = new StringTokenizer(line, ",$() ");

                    String[] temp = new String[4];
                    int cont = 0;
                    while (regis.hasMoreTokens()) {
                         temp[cont] = regis.nextToken();
                         cont++;
                    }

                    if (mips.isDest(temp[0])) { // formata para cada caso, esse  é para o primeiro registrador como destino
                         instruction = new Instruction(temp[0], temp[1], temp[3], temp[2]);
                    } else { // esse  é para o primeiro registrador como origem
                         instruction = new Instruction(temp[0], temp[3], temp[1], temp[2]); // intru reg numero reg
                    }

                    pipeline.add(instruction);
               }
              
               System.out.println("========================================");
               for (Instruction instru :  Bubble.implement(pipeline)) {
                    System.out.println(instru.getAllValues());
               }
               
               scanner.close();
          } catch (FileNotFoundException e) {
               System.out.println("Erro ao ler o arquivo: " + e.getMessage());
          }
     }
}
