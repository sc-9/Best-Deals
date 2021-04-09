import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProductCrud")

public class ProductCrud extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			String action = request.getParameter("button");
			System.out.print(action);
			String msg = "good";
			String producttype= "",productId="",productName="",productImage="",productManufacturer="",productCondition="",prod = "";
			double productPrice=0.0,productDiscount = 0.0;
			HashMap<String,Laptop> alllaptops = new HashMap<String,Laptop> ();
			HashMap<String,Phone> allphones = new HashMap<String,Phone> ();
			HashMap<String,Speaker> allspeakers = new HashMap<String,Speaker> ();
      HashMap<String,Fitness> allfitness = new HashMap<String,Fitness> ();
      HashMap<String,Smartwatch> allsmartwatchs = new HashMap<String,Smartwatch> ();
      HashMap<String,VR> allvrs = new HashMap<String,VR> ();
      HashMap<String,Headphones> allheadphones = new HashMap<String,Headphones> ();
      HashMap<String,Pettracker> allpettrackers = new HashMap<String,Pettracker> ();
			HashMap<String,Accessory> allaccessories=new HashMap<String,Accessory>();
			if (action.equals("add") || action.equals("update"))
			{
				 producttype = request.getParameter("producttype");
				 productId   = request.getParameter("productId");
				 productName = request.getParameter("productName");
				 productPrice = Double.parseDouble(request.getParameter("productPrice"));
				 productImage = request.getParameter("productImage");
				// productManufacturer = request.getParameter("productManufacturer");
				 productCondition = request.getParameter("productCondition");
				 productDiscount = Double.parseDouble(request.getParameter("productDiscount"));

			}
			else{
				productId   = request.getParameter("productId");
			}
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");

			if(action.equals("add"))
			{
			  	if(producttype.equals("laptop")){
				  alllaptops = MySqlDataStoreUtilities.getlaptop();
				  if(alllaptops.containsKey(productId)){
					  msg = "Product already available";
					  System.out.print(productId);
				  }

			  }else if(producttype.equals("phone"))
			  {
				  allphones = MySqlDataStoreUtilities.getphone();
				  if(allphones.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }else if (producttype.equals("speaker"))
			  {
				  allspeakers = MySqlDataStoreUtilities.getspeaker();
				  if(allspeakers.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }else if (producttype.equals("accessories"))
			  {

				allaccessories = MySqlDataStoreUtilities.getAccessories();
				if(allaccessories.containsKey(productId)){
					msg = "Product already available";
				}else if (producttype.equals("fitness"))
			  {
				  allfitness = MySqlDataStoreUtilities.getfitness();
				  if(allfitness.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }
        else if (producttype.equals("smartwatch"))
			  {
				  allsmartwatchs = MySqlDataStoreUtilities.getsmartwatch();
				  if(allsmartwatchs.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }
        else if (producttype.equals("pettracker"))
			  {
				  allpettrackers = MySqlDataStoreUtilities.getpettracker();
				  if(allpettrackers.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }
        else if (producttype.equals("headphones"))
			  {
				  allheadphones = MySqlDataStoreUtilities.getheadphones();
				  if(allheadphones.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }
        else if (producttype.equals("vr"))
			  {
				  allvrs = MySqlDataStoreUtilities.getvr();
				  if(allvrs.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }
			else{
				msg = "The product related to accessories is not available";
			}


						}
            else{
							msg = "Please add the prodcut name";
						}


			  if (msg.equals("good"))
			  {
				  try
				  {
						msg = MySqlDataStoreUtilities.addproducts(producttype,productId,productName,productPrice,productImage,productCondition,productDiscount,prod);
						if(producttype.equals("laptop"))
						{
							Laptop laptop = new Laptop();
							SaxParserDataStore SaxParserDataStore = new SaxParserDataStore();
							laptop.setId(productId);
							laptop.setName(productName);
							laptop.setPrice((productPrice));
						//  laptop.setRetailer(manufacturer);
							laptop.setCondition(productCondition);
							laptop.setDiscount((productDiscount));
							laptop.setImage(productImage);
							SaxParserDataStore.laptops.remove(productId);
							SaxParserDataStore.laptops.put(productId, laptop);
					}
					if(producttype.equals("phone"))
					{
						Phone phone = new Phone();
						SaxParserDataStore SaxParserDataStore = new SaxParserDataStore();
						phone.setId(productId);
						phone.setName(productName);
						phone.setPrice((productPrice));
					//  laptop.setRetailer(manufacturer);
						phone.setCondition(productCondition);
						phone.setDiscount((productDiscount));
						phone.setImage(productImage);
						SaxParserDataStore.phones.remove(productId);
						SaxParserDataStore.phones.put(productId, phone);
				}
				if(producttype.equals("speaker"))
				{
					Speaker speaker = new Speaker();
					SaxParserDataStore SaxParserDataStore = new SaxParserDataStore();
					speaker.setId(productId);
					speaker.setName(productName);
					speaker.setPrice((productPrice));
				//  laptop.setRetailer(manufacturer);
					speaker.setCondition(productCondition);
					speaker.setDiscount((productDiscount));
					speaker.setImage(productImage);
					SaxParserDataStore.speakers.remove(productId);
					SaxParserDataStore.speakers.put(productId, speaker);
				}
				  }
				  catch(Exception e)
				  {
					msg = "Product cannot be inserted";
				  }
				  msg = "Product has been successfully added";
			  }
			}else if(action.equals("update"))
			{

			  if(producttype.equals("laptop")){
				  alllaptops = MySqlDataStoreUtilities.getlaptop();
				  if(!alllaptops.containsKey(productId)){
					  msg = "Product not available";
				  }

			  }else if(producttype.equals("phone"))
			  {
				  allphones = MySqlDataStoreUtilities.getphone();
				  if(!allphones.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }else if (producttype.equals("speaker"))
			  {
				  allspeakers = MySqlDataStoreUtilities.getspeaker();
				  if(!allspeakers.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }else if (producttype.equals("accessories"))
			  {
				  allaccessories = MySqlDataStoreUtilities.getAccessories();
				  if(!allaccessories.containsKey(productId)){
					  msg = "Product not available";
				}
			  }
        else if (producttype.equals("fitness"))
			  {
				  allfitness = MySqlDataStoreUtilities.getfitness();
				  if(!allfitness.containsKey(productId)){
					  msg = "Product not available";
				}
			  }
        else if (producttype.equals("smartwatch"))
			  {
				  allsmartwatchs = MySqlDataStoreUtilities.getsmartwatch();
				  if(!allsmartwatchs.containsKey(productId)){
					  msg = "Product not available";
				}
			  }
        else if (producttype.equals("vr"))
			  {
				  allvrs = MySqlDataStoreUtilities.getvr();
				  if(!allvrs.containsKey(productId)){
					  msg = "Product not available";
				}
			  }
        else if (producttype.equals("pettracker"))
			  {
				  allpettrackers = MySqlDataStoreUtilities.getpettracker();
				  if(!allpettrackers.containsKey(productId)){
					  msg = "Product not available";
				}
			  }
        else if (producttype.equals("headphones"))
			  {
				  allheadphones = MySqlDataStoreUtilities.getheadphones();
				  if(!allheadphones.containsKey(productId)){
					  msg = "Product not available";
				}
			  }
			  if (msg.equals("good"))
			  {

				  try
				  {
					msg = MySqlDataStoreUtilities.updateproducts(producttype,productId,productName,productPrice,productImage,productCondition,productDiscount);
					if(producttype.equals("laptop"))
					{
						Laptop laptop = new Laptop();
						SaxParserDataStore SaxParserDataStore = new SaxParserDataStore();
						laptop.setId(productId);
						laptop.setName(productName);
						laptop.setPrice((productPrice));
					//  laptop.setRetailer(manufacturer);
						laptop.setCondition(productCondition);
						laptop.setDiscount((productDiscount));
						laptop.setImage(productImage);
						SaxParserDataStore.laptops.remove(productId);
						SaxParserDataStore.laptops.put(productId, laptop);
				}
				if(producttype.equals("phone"))
				{
					Phone phone = new Phone();
					SaxParserDataStore SaxParserDataStore = new SaxParserDataStore();
					phone.setId(productId);
					phone.setName(productName);
					phone.setPrice((productPrice));
				//  laptop.setRetailer(manufacturer);
					phone.setCondition(productCondition);
					phone.setDiscount((productDiscount));
					phone.setImage(productImage);
					SaxParserDataStore.phones.remove(productId);
					SaxParserDataStore.phones.put(productId, phone);
			}
				  }
				  catch(Exception e)
				  {
					msg = "Product cannot be updated";
				  }
				  msg = "Product has been successfully updated";
			  }
			}else if(action.equals("delete"))
			{
				  msg = "bad";
				  alllaptops = MySqlDataStoreUtilities.getlaptop();
				  if(alllaptops.containsKey(productId)){
					  msg = "good";
						Laptop laptop = new Laptop();
						SaxParserDataStore SaxParserDataStore = new SaxParserDataStore();
						laptop.setId(productId);
						SaxParserDataStore.laptops.remove(productId);
					  System.out.print("Laptop");
				  }


				  allphones = MySqlDataStoreUtilities.getphone();
				  if(allphones.containsKey(productId)){
					  msg = "good";
						Phone phone = new Phone();
						SaxParserDataStore SaxParserDataStore = new SaxParserDataStore();
						phone.setId(productId);
						SaxParserDataStore.phones.remove(productId);
				  }

				  allspeakers = MySqlDataStoreUtilities.getspeaker();
				  if(allspeakers.containsKey(productId)){
					  msg = "good";
				  }

				  allaccessories = MySqlDataStoreUtilities.getAccessories();
				  if(allaccessories.containsKey(productId)){
					  msg = "good";
				}

        allfitness = MySqlDataStoreUtilities.getfitness();
        if(allfitness.containsKey(productId)){
          msg = "good";
        }

        allsmartwatchs = MySqlDataStoreUtilities.getsmartwatch();
        if(allsmartwatchs.containsKey(productId)){
          msg = "good";
        }

        allvrs = MySqlDataStoreUtilities.getvr();
        if(allvrs.containsKey(productId)){
          msg = "good";
        }

        allheadphones = MySqlDataStoreUtilities.getheadphones();
        if(allheadphones.containsKey(productId)){
          msg = "good";
        }

        allpettrackers = MySqlDataStoreUtilities.getpettracker();
        if(allpettrackers.containsKey(productId)){
          msg = "good";
        }

				  if (msg.equals("good"))
				  {

					  try
					  {
							System.out.print("delete the prodcut");
							 msg = MySqlDataStoreUtilities.deleteproducts(productId);
							 if(producttype.equals("laptop"))
			 				{

							}
							if(producttype.equals("phone"))
							{

						  }
						}
					  catch(Exception e)
					  {
						msg = "Product cannot be deleted";
					  }
					   msg = "Product has been successfully deleted";
				  }else{
					  msg = "Product not available";
				  }
			}

			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h4 style='color:blue'>"+msg+"</h4>");
			pw.print("</div></div></div>");
			utility.printHtml("Footer.html");

	}
}
