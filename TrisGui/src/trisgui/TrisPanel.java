
package trisgui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;


class TrisPanel extends JPanel implements MouseListener { 
    IntroFrame inf;
    
    //player names and scores
    private final String p1;
    private final String p2;
    private int s1;
    private int s2;
    
    
    private int w,h; 
    
    //game states
    private boolean state = false;
    
    
    //clickable pads
    private final Position [] pos;
    
    
    //matrix rappr. of field
    private int [] matrixX;
    private int [] matrixC;
    
    
    
    public TrisPanel (IntroFrame f){
        matrixX = new int [9];
        matrixC = new int [9];
        s1 = 0;
        s2 = 0;
        inf = f;
        p1 = inf.p1;
        p2 = inf.p2;
        setBackground (new Color (255,255,255));
        setMinimumSize(new Dimension (700,500));
        this.addMouseListener(this);
        pos = new Position [9];
        for (int i = 0; i<pos.length; i++)
            pos[i] = new Position (0,0,50,50);
    }
    
    public static void SOP (String s){
        System.out.println(s);
    }
    
    private void drawCross (Graphics g,Color c , int x, int y, int r){
        BasicStroke bs = new BasicStroke (5);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(bs);
        g.setColor(c);
        g.drawLine(x-r, y-r, x+r, y+r);
        g.drawLine(x+r, y-r, x-r, y+r);
    }
    
    private void drawCircle (Graphics g,Color c , int x, int y, int r){
        BasicStroke bs = new BasicStroke (5);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(bs);
        g.setColor(c);
        g.drawOval(x-r/2, y-r/2, r, r);
    }
    
    private void drawField (Graphics g, int w, int h){
        g.drawLine(w/2-50, h/2-150, w/2-50, h/2+150);
        g.drawLine(w/2+50, h/2-150, w/2+50, h/2+150);
        g.drawLine(w/2-150, h/2-50, w/2+150, h/2-50);
        g.drawLine(w/2-150, h/2+50, w/2+150, h/2+50);
    }
    
    private void printNick (Graphics g, int w, int h){
        Font f = new Font ("Taohma", Font.PLAIN, 20);
        g.setFont(f);
        g.drawString(p1,w/7, 100);
        g.drawString(s1+"",w/7+20, 150);
        g.drawString(p2,w-w/5, 100);
        g.drawString(s2+"",w-w/5+20, 150);
    }
    
    private void initPos (int w, int h){
        for (int i = 0; i<3; i++){
            pos[i] = new Position (w/2-150, h/2-150 + 100*i,100,100);
        }
        for (int i = 0; i<3; i++){
            pos[(i+3)] = new Position (w/2-50, h/2-150 + 100*i,100,100);
        }
        for (int i = 0; i<3; i++){
            pos[(i+6)] = new Position (w/2+50, h/2-150 + 100*i,100,100);
        }
    }
    
    private int calcDiag (int pos,int[] a){
        int s = 0;
        switch (pos){
            case 1:
                s = a[0]+a[4]+a[8];
                break;
            case 2: 
                s = a[2]+a[4]+a[6];
                break;
            default:
                SOP ("this diagonal doesn't exist");
                break;
        }
        return s;
    }
    
    private int sumLine (int id, int [] a){
        int s = 0;
        switch (id){
            case 1:
                s = a[0]+a[3]+a[6];
                break;
            case 2: 
                s = a[1]+a[4]+a[7];
                break;
            case 3:
                s = a[2]+a[5]+a[8];
                break;
            case 4:
                s = a[0]+a[1]+a[2];
                break;
            case 5:
                s = a[3]+a[4]+a[5];
                break;
            case 6:
                s = a[6]+a[7]+a[8];
                break;
            default:
                SOP ("this line doesn't exist");
                break;
        }
        return s;
    }
    
    private boolean victory (){
        if (calcDiag (1, matrixX) == 3){
            SOP ("winX");
            s1++;
            state = false;
            return true;
        }
        if (calcDiag (2,matrixX) == 3){
            SOP ("WinC");
            s1++;
            state = false;
            return true;
        }
        if (calcDiag (1, matrixC) == 3){
            SOP ("winX");
            s2++;
            state = false;
            return true;
        }
        if (calcDiag (2,matrixC) == 3){
            SOP ("WinC");
            s2++;
            state = false;
            return true;
        }
        
        for (int i = 0; i<7; i++){
            if (sumLine (i, matrixX) == 3){
                s1++;
                state = false;
                return true;
            }
            if (sumLine(i, matrixC) == 3){
                s2++;
                state = false;
                return true;
            }
        }
        return false;
    }
    
    private int arraySum (int [] v){
        int s = 0;
        for (int i = 0; i<v.length; i++)
            s+= v[i];
        return s;
    }
   
    private void eraseAll (){
        this.repaint ();
    }
    
    @Override
    public void paintComponent (Graphics g){
        w = getWidth();
        h = getHeight();
        super.paintComponent(g);
        printNick(g, w, h);
        
        drawField (g, w, h);
        initPos (w,h);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int nClick = 0;
        Graphics g1 = this.getGraphics();
        for (int i = 0; i<pos.length; i++){
            if (pos[i].over(e.getX(), e.getY())){
                SOP ("Clicked:"+ i);
                if (!pos[i].clicked)
                    if (state){
                        drawCross (g1, Color.blue, pos[i].centerX(), pos[i].centerY(), 20);
                        matrixX[i] = 1;
                        nClick++;
                    } else {
                        drawCircle (g1, Color.red, pos[i].centerX(), pos[i].centerY(), 50);
                        matrixC[i] = 1;
                    }
                if (nClick<2){
                    pos[i].clicked = true;  
                    state = !state;
                }
            }
            if (victory () || arraySum (matrixX) + arraySum (matrixC) == 9){
                try {
                    Thread.sleep(1000);
                } catch (Exception ex){}
                eraseAll();
                matrixX = new int [9];
                matrixC = new int [9];
            } 
        }
    }
    @Override
    public void mousePressed(MouseEvent e) { 
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}