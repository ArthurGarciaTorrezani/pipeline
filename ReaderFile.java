import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReaderFile {
     public static void Reader() {
          String caminhoDoArquivo = "teste.txt";
          ArrayList<Instruction> pipeline = new ArrayList<Instruction>();

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
                         String teste = regis.nextToken().toLowerCase();
                         temp[cont] = teste;
                         cont++;
                    }

                    for (int i = 0; i < 4; i++) {
                         if (temp[i] == null) {
                              temp[i] = "void";
                         }
                    }
                    if (temp[0].equals("nop")) {
                         instruction = new Instruction("nop", "nop", "nop", "nop");
                    } else {
                         instruction = new Instruction(temp[0], temp[1], temp[3], temp[2]);
                    }

                    pipeline.add(instruction);
               }

               System.out.println("========================================");
               for (Instruction instru : Bubble.implement(pipeline)) {
                    System.out.println(instru.getAllValues());
               }

               scanner.close();
          } catch (FileNotFoundException e) {
               System.out.println("Erro ao ler o arquivo: " + e.getMessage());
          }
     }
}
