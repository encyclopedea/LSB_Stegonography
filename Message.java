import java.util.ArrayList;
public class Message{
    private String text;
    private Integer[] msg;
    
    public Message(String str){
        text=str;
        msg=new Integer[text.length()];
        convertToNum();
    }
    
    public Message(ArrayList<Integer> m){
        msg=new Integer[m.size()];
        for(int i=0;i<m.size();i++){
            msg[i]=m.get(i);
        }
        convertToText();
    }
    
    private void convertToNum(){
        for(int i=0;i<text.length();i++){
            msg[i]=new Integer((int)text.charAt(i));
        }
    }
    
    private void convertToText(){
        text="";
        for(int i=0;i<msg.length;i++){
            text=text+(char)msg[i].intValue();
        }
    }
    
    public String getMessage(){
        return text;
    }
    
    public int getLength(){
        return text.length();
    }
    
    public Integer[] getNumArray(){
        //System.out.println(Integer.toBinaryString(msg[0]));
        return msg;
    }
}
