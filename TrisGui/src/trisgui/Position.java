package trisgui;


class Position {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    public boolean clicked;
    
    Position (int x, int y, int w, int h){
       this.x = x;
       this.y = y;
       this.width = w;
       this.height= h;
       clicked = false;
    }
    public boolean over (int x, int y){
        return x >= this.x && y >= this.y && x <= this.x + width && y <= this.y+height;
    }
    public int centerX(){
        return x+width/2;
    }
    public int centerY(){
        return y+height/2;
    }
}
