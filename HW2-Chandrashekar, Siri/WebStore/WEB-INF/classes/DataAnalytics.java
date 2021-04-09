import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import javax.servlet.http.HttpSession;
@WebServlet("/DataAnalytics")

public class DataAnalytics extends HttpServlet {
	static DBCollection myReviews;
	/* Trending Page Displays all the Consoles and their Information in Game Speed*/

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);


		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to View Reviews");
			response.sendRedirect("Login");
			return;
		}


		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Data Analytics on Review</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table id='bestseller'>");
		pw.print("<form method='post' action='FindReviews'>");

     		pw.print("<table id='bestseller'>");
			pw.print("<tr>");
			pw.print("<td> <input type='checkbox' name='queryCheckBox' value='productName'> Select </td>");
                                pw.print("<td> Product Name: </td>");
                                pw.print("<td>");
                                       pw.print("<select name='productName'>");
				       pw.print("<option value='ALL_PRODUCTS'>All Products</option>");
	                  pw.print("<option value='Asus'>Asus</option>");
	                  pw.print("<option value='HP'>HP</option>");
	                  pw.print("<option value='Dell'>Dell Pro</option>");
	                  pw.print(" <option value='Dell'>Dell Q4</option>");
	                  pw.print("<option value='Acer'>Acer</option>");

				            pw.print("<option value='Macbook air'>Macbook air</option>");
										pw.print("<option value='Samsung s8'>Samsung s8</option>");
										pw.print("<option value='Samsun s9'>Samsung s9</option>");
										pw.print("<option value='Nexus'>Nexus 5</option>");
										pw.print("<option value='Iphone 6'>Iphone 6</option>");
										pw.print("<option value='Iphone 7'>Iphone 7</option>");
										pw.print("<option value='Iphone X'>Iphone X</option>");
										pw.print("<option value='Sony Xperia'>Sony Xperia</option>");
										pw.print("<option value='Moto g1'>Moto g1</option>");
										pw.print("<option value='Moto'>Moto</option>");
										pw.print("<option value='Evolve'>Evolve</option>");
										pw.print("<option value='Sony c3'>Sony c3</option>");
										pw.print("<option value='LG'>LG</option>");
										pw.print("<option value='Fitbit aqua'>Fitbit aqua</option>");
										pw.print("<option value='Fitbit versa'>Fitbit versa</option>");
										pw.print("<option value='Fitbit small'>Fitbit small</option>");
										pw.print("<option value='JBL'>JBL</option>");
										pw.print("<option value='Sony'>Sony</option>");
										pw.print("<option value='Sanheisser'>Sanheisser</option>");
										pw.print("<option value='SkullCandy'>SkullCandy</option>");
										pw.print("<option value='phillips'>phillips</option>");
										pw.print("<option value='Sony1'>Sony1</option>");
										pw.print("<option value='JB'>JB</option>");
										pw.print("<option value='Samsung Dog Tracker'>Samsung Dog Tracker</option>");
										pw.print("<option value='Sony Dog Tracker'>Sony Dog Tracker</option>");
										pw.print("<option value='Cat Tracker'>Cat Tracker</option>");
										pw.print("<option value='Google Home Mini'>Google Home Mini</option>");
										pw.print("<option value='Alexa'>Alexa</option>");
										pw.print("<option value='Echo'>Echo</option>");
										pw.print("<option value='Google Home max'>Google Home max</option>");
										pw.print("<option value='VR1'>VR1</option>");
										pw.print("<option value='VR2'>VR2</option>");
										pw.print("<option value='Mouse'>Mouse</option>");
										pw.print("<option value='PS4 Controller'>PS4 Controller</option>");
										pw.print("<option value='case'>case</option>");

                                pw.print("</td>");
			pw.print("<tr>");
			     pw.print("<td> <input type='checkbox' name='queryCheckBox' value='productPrice'> Select </td>");
                                pw.print("<td> Product Price: </td>");
                              pw.print(" <td>");
                                  pw.print("  <input type='number' name='productPrice' value = '0' size=10  /> </td>");
						pw.print("<td>");
					pw.print("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
					pw.print("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
					pw.print("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
					pw.print("</td></tr>");


			pw.print("<tr><td> <input type='checkbox' name='queryCheckBox' value='reviewRating'> Select </td>");
                               pw.print(" <td> Review Rating: </td>");
                               pw.print(" <td>");
                                   pw.print(" <select name='reviewRating'>");
                                       pw.print(" <option value='1' selected>1</option>");
                                       pw.print(" <option value='2'>2</option>");
                                       pw.print(" <option value='3'>3</option>");
                                     pw.print("   <option value='4'>4</option>");
                                      pw.print("  <option value='5'>5</option>");
                                pw.print("</td>");
				pw.print("<td>");
				pw.print("<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>");
				pw.print("<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than");
			pw.print("</td></tr>");

			pw.print("<tr>");
								pw.print("<td> <input type='checkbox' name='queryCheckBox' value='retailerCity'> Select </td>");
                                pw.print("<td> Retailer City: </td>");
                                pw.print("<td>");
                                pw.print("<input type='text' name='retailerCity' /> </td>");

                            pw.print("</tr>");

							 pw.print("<tr>");
								pw.print("<td> <input type='checkbox' name='queryCheckBox' value='retailerpin'> Select </td>");
                               pw.print(" <td> Retailer Zip code: </td>");
                               pw.print(" <td>");
                                    pw.print("<input type='text' name='retailerpin' /> </td>");
					        pw.print("</tr>");
				pw.print("<tr><td>");
					pw.print("<input type='checkbox' name='extraSettings' value='GROUP_BY'> Group By");
								pw.print("</td>");
								pw.print("<td>");
								pw.print("<select name='groupByDropdown'>");
                                pw.print("<option value='GROUP_BY_CITY' selected>City</option>");
                                pw.print("<option value='GROUP_BY_PRODUCT'>Product Name</option>");
                                pw.print("</td><td>");
								pw.print("<input type='radio' name='dataGroupBy' value='Count' checked> Count <br>");
								pw.print("<input type='radio' name='dataGroupBy' value='Detail'> Detail <br>");
								pw.print("</td></tr>");



						pw.print("<tr>");
                                pw.print("<td colspan = '4'> <input type='submit' value='Find Data' class='btnbuy' /> </td>");
                            pw.print("</tr>");


		pw.print("</table>");
		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");




	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
