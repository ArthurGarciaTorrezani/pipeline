import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReaderFile {
     public static void Reader() {
          String caminhoDoArquivo = "teste.txt";

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

                    instruction = new Instruction(temp[0], temp[1], temp[2], temp[3]);

                    System.out.println(instruction.getAllValues());
               }

               scanner.close();
          } catch (FileNotFoundException e) {
               System.out.println("Erro ao ler o arquivo: " + e.getMessage());
          }
     }
}
