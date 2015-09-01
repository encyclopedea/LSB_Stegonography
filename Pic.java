import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Pic{
    private BufferedImage orig;
    private BufferedImage edited;
    private Integer[][] origPix,editedPix;
    
    public Pic(String fileName,boolean b){
        try {
            orig = ImageIO.read(new File(fileName));
            edited=ImageIO.read(new File(fileName));
            
            origPix=new Integer[orig.getHeight()][orig.getWidth()];
            editedPix=new Integer[orig.getHeight()][orig.getWidth()];
            
            for(int r=0;r<origPix.length;r++){
                for(int c=0;c<origPix[0].length;c++){
                    origPix[r][c]=new Integer(orig.getRGB(c,r));
                    editedPix[r][c]=new Integer(edited.getRGB(c,r));
                    if(b){
                        if(origPix[r][c].intValue()==-16777216){
                            origPix[r][c]=-16777215;
                            orig.setRGB(c,r,-16777215);
                            editedPix[r][c]=-16777215;
                            edited.setRGB(c,r,-16777215);
                        }
                        if(origPix[r][c].intValue()==16777216){
                            origPix[r][c]=16777215;
                            orig.setRGB(c,r,16777215);
                            editedPix[r][c]=16777215;
                            edited.setRGB(c,r,16777215);
                        }
                        if(origPix[r][c].intValue()==0){
                            origPix[r][c]=1;
                            orig.setRGB(c,r,1);
                            editedPix[r][c]=1;
                            edited.setRGB(c,r,1);
                        }
                        if(origPix[r][c].intValue()==-1){
                            origPix[r][c]=-2;
                            orig.setRGB(c,r,-2);
                            editedPix[r][c]=-2;
                            edited.setRGB(c,r,-2);
                        }
                    }
                }
            }
        
        } catch (IOException e) {
           throw new NullPointerException();
        }
    }
    
    public Integer[][] getOrigPix(){
        return origPix;
    }
    
    public Integer[][] getEditedPix(){
        
        return editedPix;
    }
    
    public void editPix(int r, int c,int newValue){
        editedPix[r][c]=new Integer(newValue);
        edited.setRGB(c,r,newValue);
    }
    
    public BufferedImage getOrig(){
        return orig;
    }
    
    public BufferedImage getEdited(){
        return edited;
    }
    
    public int getWidth(){
        return orig.getHeight();
    }
    
    public int getHeight(){
        return orig.getWidth();
    }
    
    public int getMaxLength(){
        int length=0,numSections=0;
        int currentColor=origPix[0][0].intValue();
        
        for(int r=0;r<origPix.length;r++){
            for(int c=1;c<origPix[0].length;c++){
                if(currentColor==origPix[r][c].intValue()){
                    length++;
                }
                else{
                    currentColor=origPix[r][c].intValue();
                    numSections++;
                }
            }
            
        }
        
        
        return (length-numSections*2-3)/8;
    }
    
    public void saveEdited(String name){
        
        try {
                ImageIO.write(edited, "png", new File(name+".png"));
        } catch (IOException e) {
        }
    }
    
    public void reset(){
        editedPix=origPix;
    }
}
