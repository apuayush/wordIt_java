package wordit;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/game"})
public class game extends HttpServlet {
    BufferedReader br ;
    String[] qlist;
    void readNames() throws FileNotFoundException, IOException{
        Random r = new Random();
        br = new BufferedReader( new FileReader( new File( "/home/apurvnit/Projects/java_test_programs/test/data/movies.csv" ) ));
        for(int i=0;i<r.nextInt(7000);i++)
            br.readLine();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        readNames();
        qlist=get();
        String[] jlist = perform(qlist);
   
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>WordIT</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Enter correct name of the movies jumbled </h1>");
            out.println("<form action='/test/game' method='POST'");
            for(int i=0;i<10;i++){
                System.out.println(qlist[i]);
                out.println("<p>" + jlist[i]+"- <input type='text' name='answers"+i+"'/></p>");
            }
            out.println("<p><input type='submit'/></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int score = 0;
        for(int i=0;i<10;i++){
            String p = request.getParameter("answers"+i).replaceAll("(^ )|( $)", "");
            if(p.compareTo(qlist[i].replaceAll("(^ )|( $)", ""))==0){
                System.out.print("inside");
                score++;
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<p>Here is your score<p>" + score);
            out.println("<form action='/test/game' method='GET'><p><input type='submit' value='Play again'/></p></form>");
        }
    }
    
    String[] findTenNames() throws FileNotFoundException, IOException{
        String[] list ;
        String v = "\'\".-_():;";
        list = new String[10];
        label:
        for(int i=0;i<10;i++){
            String s = br.readLine();
            String k = s.substring(s.indexOf(',')+1,s.indexOf('(')).toLowerCase();
            for(int j=0;j<k.length();j++){
                if(v.indexOf(k.charAt(j))!=-1){
                    i-=1;
                    continue label;
                }
            list[i] = k;
                    
            }
        }
        return list;
            
    }
    
    String[] get()throws IOException{
         String[] list = findTenNames();
         return list;
    }
//    for jumbling
    String[] perform(String[] list) throws IOException{ 
        String[] jumb = new String[10];
        for(int i=0;i<10;i++){
            jumb[i] = randomizeEach(list[i]);
            System.out.println(jumb[i]);
            }
        
        return jumb;
    }
    
    String randomizeEach(String s){
        String p[] = s.replaceAll("\\s+"," ").split(" ");
        String l="";
        for(int k=0;k<p.length;k++){
            l = l+choose(p[k])+" ";
        }
        return l;
    }
    
    String choose(String s){
        if(s.length()==0)
            return "";
        Random r = new Random();
         int p = r.nextInt(s.length());
        return s.charAt(p)+choose(s.substring(0,p)+s.substring(p+1));
    }
   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
