public class Instruction {
     private String instru;
     private String regisDest;
     private String regisRead1;
     private String regisRead2;

     public Instruction(String instru,String regisDest, String regisRead2, String regisRead1) {
          this.instru = instru;
          this.regisDest = regisDest;
          this.regisRead1 = regisRead1;
          this.regisRead2 = regisRead2;
     }

     public String getInstru() {
          return instru;
     }

     public String getRegisDest() {
          return regisDest;
     }

     public String getRegisRead1() {
          return regisRead1;
     }

     public String getRegisRead2() {
          return regisRead2;
     }

     public String getAllValues(){
          return instru + " " + regisDest + " " + regisRead1 + " " + regisRead2;
     }

}
