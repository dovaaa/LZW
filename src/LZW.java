import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


class Tag{
    int index;


    Tag(int idx){
        index=idx;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }

}
public class LZW {
    Reader R;
    PrintWriter W;
    int dictSize = 16;
    ArrayList<String> dict=new ArrayList<>();
    ArrayList<Tag> tags=new ArrayList<>();
    public LZW(){}
    public LZW(int dictSize){
       this.dictSize=dictSize;
    }
    public void fillDict(){
        for (int i = 0; i < 128; i++) {
            String ch="";
            ch+=(char) i;
            dict.add(ch);
        }
    }
    public void compress(String In) throws IOException {
        R = new BufferedReader(new FileReader(In));
        W=new PrintWriter(new BufferedWriter(new FileWriter(In+"Compressed.txt")));
        fillDict();

        String p= "";
        p=String.valueOf((char)R.read());
        int c;
        while ((c=R.read())!=-1){
            String seq=p+(char)c;
            if(dict.contains(seq)){
                p+=(char)c;
                continue;
            }else{
                Tag tag= new Tag(dict.lastIndexOf(p));
                tags.add(tag);
            }

            dict.add(p+(char)c);
            p=String.valueOf((char)c);
        }
        Tag tag= new Tag(dict.indexOf(p));
        tags.add(tag);


        for (int i = 0; i < tags.size(); i++) {
            W.println(tags.get(i));
        }

        W.flush();W.close();
        R.close();
    }
    public void decompress(String In) throws IOException{
        File file = new File(In+"Compressed.txt");
        Scanner reader = new Scanner(file);
        W=new PrintWriter(new BufferedWriter(new FileWriter(In+"Decompressed.txt")));
        fillDict();

        String old= reader.nextLine();
        W.print((char)Integer.parseInt(old));

        String C="",S="",New="";
        while (reader.hasNextLine()){
            New = reader.nextLine();

            if(dict.size()<=Integer.parseInt(New)){

                S=dict.get(Integer.parseInt(old));
                S+=C;
            }else{
                S=dict.get(Integer.parseInt(New));
            }
            W.print(S);
            C=String.valueOf(S.charAt(0));
            String adder= dict.get(Integer.parseInt(old));
            dict.add(adder+C);
            old=New;
        }

        W.flush(); W.close();
        reader.close();
    }



    public static void main(String[] args) throws IOException {
//        LZW lzw=new LZW();
//        lzw.decompress("E:\\programming\\LZW\\src\\input");
        String path = "E:\\programming\\LZW\\src\\input";
        System.out.println("1.Compress\n2.Decompress");
        Scanner in =new Scanner(System.in);
        int option = in.nextInt();
        LZW lzw = new LZW();
        switch (option){
            case 1:
                lzw.compress(path);
                break;
            case 2:
                lzw.decompress(path);
                break;
            default:
                break;
        }

    }
}
