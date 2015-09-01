import java.util.ArrayList;
/**
 * This class controls the placing of text into the pixels and the extraction of text from the pixels.
 * 
 */
public class Stego{
    public static void textIntoPic(Pic p,Message m){
        Integer[][] pixels=p.getOrigPix();
        Integer[] msg=m.getNumArray();
        //p.reset();
        
        
        boolean ok=false;
        boolean min=false;
        boolean last=false;
        int bit=0;
        int character=0;
        String str="";
        
        for(int r=0;r<pixels.length;r++){
            for(int c=0;c<pixels[0].length;c++){
                if(character>msg.length-1){
                    p.editPix(r,c,0);
                    p.getEditedPix();
                    return;
                }//if the message is ended
                  
                if(ok==false){//if we are not in the middle of a section
                    if(c<pixels[0].length-3&&r>pixels.length-1){//if there is no more space for the message
                        //do nothing
                    }
                    
                    
                    //if the next 2 pixels are the same, set ok to true and this pixel to 0
                    else if(c<pixels[0].length-2){//if not at the end of the row
                        if(pixels[r][c].intValue()==pixels[r][c+1].intValue()){//test next pixel
                            if(pixels[r][c].intValue()==pixels[r][c+2].intValue()){//test pixel after next
                                p.editPix(r,c,0);
                                ok=true;
                                min=true;//get ready to denote the minimum
                                //this will work because i can modify even pixels that are not the same value to be null
                            }
                        }
                    }
                    
                    else if(r>pixels.length-2){//do nothing if this is at the end of the row and cannot go down any further
                    }
                    
                    else if(c==pixels[0].length-2){//if there is 1 more space left
                        if(pixels[r][c].intValue()==pixels[r][c+1].intValue()){//test next pixel in row
                            if(pixels[r][c].intValue()==pixels[r+1][0].intValue()){//check pixel at next row, 0
                                p.editPix(r,c,0);
                                ok=true;
                                min=true;
                            }
                        }
                    }
                    
                    else if(c==pixels[0].length-1){//if at the end of the row
                        if(pixels[r][c].intValue()==pixels[r+1][0].intValue()){//check pixel at next row, 0
                            if(pixels[r][c].intValue()==pixels[r+1][1].intValue()){//check pixel at next row, 1
                                p.editPix(r,c,0);
                                ok=true;
                                min=true;
                            }
                        }
                    }
                }
                
                
                else{//if ok==true, or rather, if we are in the middle of a section
                    if(last){//if we already modified the last same color pixel, end this
                        p.editPix(r,c,0);
                        ok=false;
                        last=false;
                    }
                    
                    //test next pixel to see if this section has to end
                    else if(c<pixels[0].length-1){//if there is at least 1 more space in this row
                        if(pixels[r][c].intValue()!=pixels[r][c+1].intValue()){//test next pixel
                            last=true;
                        }
                    }
                    
                    else if(r==pixels.length-1){//if at the end of the image
                        p.editPix(r,c,0);
                        ok=false;
                    }
                    
                    else{//if we need to check next row-only option left
                        if(pixels[r][c].intValue()!=pixels[r+1][0].intValue()){
                            last=true;
                        }
                    }
                    
                    if(min){//if we are on the minimum, leave it as is and set min to false
                        min=false;
                    }
                    
                    else if(ok==true){//if the section continues, we can begin putting text bytecode into the pixels
                        str=Integer.toBinaryString(msg[character].intValue());//get the bytecode of the character we are on 
                        while(str.length()<8){//make sure the string is the right length
                            str="0"+str;
                        }
                        str=str.substring(bit,bit+1);//now find the bit we are on
                        //after this, modify the original pixel according to the bit we find
                        if(str.equals("0"));//leave it alone
                        if(str.equals("1")){
                            p.editPix(r,c,pixels[r][c].intValue()+1);
                        }
                        
                        //at this point, increment the progress of the message
                        if(bit<7)
                          bit++;
                        else{
                            bit=0;
                            character++;
                        }
                    }
                }
            }
        }
    }
    
    //jesse will add the picToText method, it takes an empty message and a pic, then returns a full message
    //the most difficult part will be determining the base value- we dont want to iterate through everything beforehand, and if the message is all 0s or all 1s, we can't tell the difference
    public static Message picIntoText(Pic p)
    {
        boolean found = false;
        Integer[][] pixels=p.getOrigPix();
        int section;
        String message = "";
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int r=0;r<pixels.length;r++){
                for(int c=0;c<pixels[0].length;c++){
                        
                    if(!found && pixels[r][c].intValue() == 0)//-16777216)
                    {
                        found = true;
                        numbers.add(0);
                    }
                
                    else if(found==true){
                        if(pixels[r][c].intValue() == 0){//-16777216){
                            found = false;
                            numbers.add(0);
                        }
                    
                        else 
                        {
                            numbers.add(pixels[r][c].intValue());
                        }
                    }
                }
        }
        
        
        found=false;
        int min;
        for(int i=0;i<numbers.size()-1;i++)
        {
            if(numbers.get(i).intValue()==0 && found == false)
            {
                found = true;
                numbers.remove(i);
                min = numbers.get(i);
                numbers.remove(i);
                
                /*for(int k = i+1; k<numbers.size(); k++)//iterates through to find the smallest in the list
                {
                    if(numbers.get(k) == 0)
                    {
                        found = false;
                        break;
                    }
                    else
                    {
                        if(numbers.get(k)<min)
                        {
                            min = numbers.get(k);
                        }
                    }
                }*/

                for(int k = i; k<numbers.size(); k++)
                {
                    if(numbers.get(k) == 0)
                    {
                        numbers.remove(k);
                        found = false;
                        i=k-1;
                        break;
                    }
                    else
                    {
                        numbers.set(k,numbers.get(k)-min);
                    }
                }
            }
        }
        
        numbers = convertToBaseTen(numbers);
        
        Message m=new Message(numbers);
        return m;
    }
    
    private static ArrayList<Integer> convertToBaseTen(ArrayList<Integer> list)
    {
        ArrayList<Integer> convertedList = new ArrayList<Integer>();
        
        for(int i = 0; i < list.size()-7; i+=8)
        {
            int number = 0;
            for(int k = 7; k>=0; k--)
            {
                number += list.get(i+7-k)*Math.pow(2,k);
                //System.out.print(list.get(i+7-k));//debug
            }
            convertedList.add(number);
            //System.out.println("->"+number+"->"+(char)number);//debug
        }
        return convertedList;
    }
}
