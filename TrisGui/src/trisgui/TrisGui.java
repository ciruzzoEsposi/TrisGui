
package trisgui;

public class TrisGui {
    
    static void print (String s){
        System.out.println (s);
    }
    
    public static void main(String[] args) {
        IntroFrame  introF = new IntroFrame ("Tris", 400, 400);
        introF.setVisible(true);
        introF.setLocationRelativeTo(null);
    }
}
