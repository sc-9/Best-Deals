import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VirtualRealityList")

public class VirtualRealityList extends HttpServlet {

	/* vrs Page Displays all the vrs and their Information in vr Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the vrs type whether it is electronicArts or activision or takeTwoInteractive */

		String name = null;
		String CategoryName = request.getParameter("name");
		HashMap<String, VR> hm = new HashMap<String, VR>();

		// hm.putAll(SaxParserDataStore.vrs);
		// 	name = "";
		if(CategoryName==null)
		{
			hm.putAll(SaxParserDataStore.vrs);
			name = "";
		}
		else
		{
			// for(Map.Entry<String,Smartwatch> entry : SaxParserDataStore.smartwatchs.entrySet())
			// 	{
			// 	if(entry.getValue().getName().equalsIgnoreCase("Moto"))
			// 	 {
			// 		 hm.put(entry.getValue().getId(),entry.getValue());
			// 	 }
			// 	}
		  if(CategoryName.equalsIgnoreCase("VR1"))
		  {
			for(Map.Entry<String,VR> entry : SaxParserDataStore.vrs.entrySet())
				{
				if(entry.getValue().getName().equalsIgnoreCase("VR1"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				name="Sony VR";
			}
		}
		// if(CategoryName==null)
		// {
		// 	hm.putAll(SaxParserDataStore.vrs);
		// 	name = "";
		// }
		//else
	//	{
			// for(Map.Entry<String,vr> entry : SaxParserDataStore.vr.entrySet())
			// 	{
			// 	if(entry.getValue().getRetailer().equals("ElectronicArts"))
			// 	 {
			// 		 hm.put(entry.getValue().getId(),entry.getValue());
			// 	 }
			//	}
		  // if(CategoryName.equals("electronicArts"))
		  // {
			// for(Map.Entry<String,vr> entry : SaxParserDataStore.vrs.entrySet())
			// 	{
			// 	if(entry.getValue().getRetailer().equals("ElectronicArts"))
			// 	 {
			// 		 hm.put(entry.getValue().getId(),entry.getValue());
			// 	 }
			// 	}
			// name = "ElectronicArts";
		  // }
		  // else if(CategoryName.equals("activision"))
		  // {
			// for(Map.Entry<String,vr> entry : SaxParserDataStore.vrs.entrySet())
			// 	{
			// 	if(entry.getValue().getRetailer().equals("Activision"))
			// 	 {
			// 		 hm.put(entry.getValue().getId(),entry.getValue());
			// 	 }
			// 	}
			// name = "Activision";
		  // }
		  // else if(CategoryName.equals("takeTwoInteractive"))
		  // {
			// for(Map.Entry<String,vr> entry : SaxParserDataStore.vrs.entrySet())
			// 	{
			// 	if(entry.getValue().getRetailer().equals("TakeTwoInteractive"))
			// 	 {
			// 		 hm.put(entry.getValue().getId(),entry.getValue());
			// 	 }
			// 	}
			// name = "TakeTwoInteractive";
		  // }
	//	}

		/* Header, Left Navigation Bar are Printed.

		All the vrs and vrs information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Virtual Reality!</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, VR> entry : hm.entrySet()){
			VR vr = entry.getValue();
			if(i%3==1)
			pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+vr.getName()+"</h3>");
			pw.print("<strong>"+ "$" + vr.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/vr/"+vr.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='vr'>"+
				//	"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			// pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
			// 		"<input type='hidden' name='type' value='vrs'>"+
			// 		//"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
			// 		"<input type='hidden' name='access' value=''>"+
			// 	    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			// pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
			// 		"<input type='hidden' name='type' value='vrs'>"+
			// 		//"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
			// 		"<input type='hidden' name='access' value=''>"+
			// 	    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size)
			pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");

	}

}
