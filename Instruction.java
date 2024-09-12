public class Instruction {
     private String instru;
     private String regis1;
     private String regis2;
     private String regis3;

     public Instruction(String instru, String regis1, String regis3, String regis2) {
          this.instru = instru;
          this.regis1 = regis1;
          this.regis2 = regis2;
          this.regis3 = regis3;
     }

     public void setRegis1(String regis1){
          this.regis1 = regis1;
     }

     public void setRegis3(String regis3){
          this.regis3 = regis3;
     }

     public String getInstru() {
          return instru;
     }

     public String getRegis1() {
          return regis1;
     }

     public String getRegis2() {
          return regis2;
     }

     public String getRegis3() {
          return regis3;
     }

     public String getAllValues() {
          return instru + " " + regis1 + " " + regis2 + " " + regis3;
     }
}
