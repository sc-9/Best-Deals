import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;



@WebServlet("/CreateOrder")
public class CreateOrder extends HttpServlet {
    private String error_msg;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String customerName = request.getParameter("username");
        String itemName = request.getParameter("itemName");
        String creditCardNo = request.getParameter("creditCardNo");
        String customerAddress = request.getParameter("customerAddress");


        HashMap<String, User> hm = new HashMap<String, User>();
        String TOMCAT_HOME = System.getProperty("catalina.home");



        try {
            FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "\\webapps\\WebStore\\UserDetails.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            hm = (HashMap) objectInputStream.readObject();
        } catch (Exception e) {

        }


        if (!hm.containsKey(customerName))
            error_msg = "Customer doesn't exist.";
        else {

          // User user = new User(customerName, password, "customer");
          //            hm.put(username, user);
          //            FileOutputStream fileOutputStream = new FileOutputStream(TOMCAT_HOME + "\\webapps\\WebStore\\UserDetails.txt");
          //            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
          //            objectOutputStream.writeObject(hm);
          //            objectOutputStream.flush();
          //            objectOutputStream.close();
          //            fileOutputStream.close();
          //            HttpSession session = request.getSession(true);
          //            session.setAttribute("login_msg", "The customer account created successfully.");
          //
          //            error_msg = "The customer account has been created.";
          //            displaySalesmanHome(request, response, pw, true);
          //            return;


        }


        Utilities utility = new Utilities(request, pw);
        String name = request.getParameter("orderName");
        utility.removeItemFromCart(name);


        response.sendRedirect("Cart");
        return;
    }

}
