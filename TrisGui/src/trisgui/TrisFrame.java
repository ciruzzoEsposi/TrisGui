
package trisgui;

import javax.swing.JFrame;

class TrisFrame extends JFrame{
    private final int w;
    private final int h; //final is a const.
    
    
    public TrisFrame (String title, int w, int h){
        super (title);
        this.w = w;
        this.h = h;
        setVisible (true);
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        setSize (w,h);
        setLocationRelativeTo(null);
        setResizable (false);
    }
}
