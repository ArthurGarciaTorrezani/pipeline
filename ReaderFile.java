import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReaderFile {
    
    public static void Reader(String arquivo) throws IOException{ // Leitura do arquivo inserido na "principal".
        BufferedReader bf = new BufferedReader(new FileReader(arquivo));
        String linha;
        String conteudo = "";
        ArrayList<Instruction> pipeline = new ArrayList<Instruction>();
      
        Instruction instruction;
        while((linha = bf.readLine()) != null){
          StringTokenizer regis = new StringTokenizer(linha, ",$() ");

                    String[] temp = new String[4];
                    int cont = 0;
                    while (regis.hasMoreTokens()) {
                         temp[cont] = regis.nextToken().toLowerCase();
                         cont++;
                    }
                    
                    for(int i = 0;i<4;i++){
                         if(temp[i] == null){
                              temp[i] = " ";
                         }
                    }
                    if (temp[0].equals("nop")) {
                         instruction = new Instruction("nop", "nop", "nop", "nop");
                    } else {
                         instruction = new Instruction(temp[0], temp[1], temp[3], temp[2]);
                    }

                    pipeline.add(instruction);
               }
              
           
               for (Instruction instru :  Bubble.implement(pipeline, arquivo)) {
                    System.out.println(instru.getAllValues());
                    conteudo += instru.getAllValues();
                    conteudo += "\n";
               }
               bf.close();
               criaArquivo(conteudo, arquivo);

    }

    public static void openDir() {
        Path dir = Paths.get("arquivos");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    Path fileName = file.getFileName();
                    String fileNameStr = file.getFileName().toString();
                    System.out.println("========================================");
                    System.out.printf("Open file: %s\n", fileNameStr);

                    Reader("arquivos/" + fileName);
                }
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.printf("Error opening directory: %s\n", e);
        }
    }

    public static void criaArquivo(String conteudo, String titulo) throws IOException{ // Criação do arquivo de resultado.

        titulo = titulo.split(".txt")[0];
        titulo += "-RESULTADO.txt";
        BufferedWriter out = new BufferedWriter(new FileWriter(titulo));
        out.write(conteudo);
        out.flush();
        out.close();

    }


}
