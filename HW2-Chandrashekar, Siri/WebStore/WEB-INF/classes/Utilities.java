import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/Utilities")

/*
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.

*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session;
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument,
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

		public void printHtml(String file) {
	        String result = HtmlToString(file);
	        //to print the right navigation in header of username cart and logout etc
	        if (file.equals("Header.html")) {
	            result = result + "<div id='menu' style='float: right;'><ul>";
	            if (session.getAttribute("username") != null) {
	                String username = session.getAttribute("username").toString();
	                username = Character.toUpperCase(username.charAt(0)) + username.substring(1);

	                String userType = session.getAttribute("userType").toString();
	                switch (userType) {
	                    case "Customer":
	                        result = result + "<li><a><span class='glyphicon'>Hello, " + username + "</span></a></li>"
	                                + "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
	                                + "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
	                        break;
	                    case "StoreManager":
	                        result = result + "<li><a href='StoreManagerHome'><span class='glyphicon'>ViewProduct</span></a></li>"
																	+"<li><a href='DataVisualization'><span class='glyphicon'>Data Visualization</span></a></li>"
																	+"<li><a href='DataAnalytics'><span class='glyphicon'>DataAnalytics</span></a></li>"
	                                + "<li><a><span class='glyphicon'>Hello, " + username + "</span></a></li>"
	                                + "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
	                        break;
	                    case "Salesman":
	                        result = result + "<li><a href='SalesmanHome'><span class='glyphicon'>ViewOrder</span></a></li>"
	                                + "<li><a><span class='glyphicon'>Hello, " + username + "</span></a></li>"
	                                + "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
	                        break;
	                }
	            } else
	                result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>" + "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
	            result = result + "<li><a href='Cart'><span class='glyphicon'>Cart(" + CartCount() + ")</span></a></li></ul></div></div><div id='page'>";
	            pw.print(result);
	        } else
	            pw.print(result);
	    }


	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		}
		catch (Exception e) {
		}
		return result;
	}

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}

	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/

	public String username() {
        if (session.getAttribute("username") != null)
            return session.getAttribute("username").toString();
        return null;
    }

    /*  usertype Function returns the usertype from the session variable.*/
    public String usertype() {
        if (session.getAttribute("usertype") != null)
            return session.getAttribute("usertype").toString();
        return null;
    }

    /*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
    public User getUser() {
        String usertype = usertype();
        HashMap<String, User> hm = new HashMap<String, User>();
        hm = MySqlDataStoreUtilities.selectUser();

        return hm.get(username());
    }

	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>();
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\WebStore\\PaymentDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{

			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
					 size=size + 1;

			}
			return size;
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}

	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

/*	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		if(type.equals("laptop")){
			Laptop laptop;
			laptop = SaxParserDataStore.laptops.get(name);
			OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage());
			orderItems.add(orderitem);
		}
		if(type.equals("phone")){
			Phone phone;
			phone = SaxParserDataStore.phones.get(name);
			OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage());
			orderItems.add(orderitem);
		}
		if(type.equals("speaker")){
			Speaker speaker;
			speaker = SaxParserDataStore.speakers.get(name);
			OrderItem orderitem = new OrderItem(speaker.getName(), speaker.getPrice(), speaker.getImage());
			orderItems.add(orderitem);
		}
		if(type.equals("headphones")){
			Headphones headphones;
			headphones = SaxParserDataStore.headphoness.get(name);
			OrderItem orderitem = new OrderItem(headphones.getName(), headphones.getPrice(), headphones.getImage());
			orderItems.add(orderitem);
		}
		if(type.equals("fitness")){
			Fitness fitness;
			fitness = SaxParserDataStore.fitnesss.get(name);
			OrderItem orderitem = new OrderItem(fitness.getName(), fitness.getPrice(), fitness.getImage());
			orderItems.add(orderitem);
		}

		if(type.equals("vr")){
			VR vr;
			vr = SaxParserDataStore.vrs.get(name);
			OrderItem orderitem = new OrderItem(vr.getName(), vr.getPrice(), vr.getImage());
			orderItems.add(orderitem);
		}

		if(type.equals("smartwatch")){
			Smartwatch smartwatch;
			smartwatch = SaxParserDataStore.smartwatchs.get(name);
			OrderItem orderitem = new OrderItem(smartwatch.getName(), smartwatch.getPrice(), smartwatch.getImage());
			orderItems.add(orderitem);
		}

		if(type.equals("pettracker")){
			Pettracker pettracker;
			pettracker = SaxParserDataStore.pettrackers.get(name);
			OrderItem orderitem = new OrderItem(pettracker.getName(), pettracker.getPrice(), pettracker.getImage());
			orderItems.add(orderitem);
		}

		if(type.equals("accessory")){
			Accessory accessory;
			accessory = SaxParserDataStore.accessorys.get(name);
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage());
			orderItems.add(orderitem);
		}

	}*/

	public void storeProduct(String name,String type,String maker, String acc){
	if(!OrdersHashMap.orders.containsKey(username())){
		ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
		OrdersHashMap.orders.put(username(), arr);
	}
	ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
	HashMap<String,Laptop> alllaptops = new HashMap<String,Laptop> ();
		HashMap<String,Phone> allphones = new HashMap<String,Phone> ();
		HashMap<String,Speaker> allspeakers = new HashMap<String,Speaker> ();
		HashMap<String,Headphones> allheadphones = new HashMap<String,Headphones> ();
		HashMap<String,VR> allvrs = new HashMap<String,VR> ();
		HashMap<String,Smartwatch> allsmartwatchs = new HashMap<String,Smartwatch> ();
		HashMap<String,Fitness> allfitness = new HashMap<String,Fitness> ();
		HashMap<String,Pettracker> allpettrackers = new HashMap<String,Pettracker> ();
		HashMap<String,Accessory> allaccessories=new HashMap<String,Accessory>();
		//Laptops
	if(type.equals("laptop")){
		Laptop laptop;
		try{
			alllaptops = MySqlDataStoreUtilities.getlaptop();
			}
			catch(Exception e){

			}
			laptop = alllaptops.get(name);
			OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage());
			orderItems.add(orderitem);
	}
	//Phones
	if(type.equals("phone")){
	  Phone phone = null;
	  try{
	  allphones = MySqlDataStoreUtilities.getphone();
	  }
	  catch(Exception e){

	  }
	  phone = allphones.get(name);
	  OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage());
	  orderItems.add(orderitem);
	}
	//Speakers
	if(type.equals("speaker")){
	  Speaker speaker = null;
	  try{
	  allspeakers = MySqlDataStoreUtilities.getspeaker();
	  }
	  catch(Exception e){

	  }
	  speaker = allspeakers.get(name);
	  OrderItem orderitem = new OrderItem(speaker.getName(), speaker.getPrice(), speaker.getImage());
	  orderItems.add(orderitem);
	}

	//VR
	if(type.equals("vr")){
	  VR vr = null;
	  try{
	  allvrs = MySqlDataStoreUtilities.getvr();
	  }
	  catch(Exception e){

	  }
	  vr = allvrs.get(name);
	  OrderItem orderitem = new OrderItem(vr.getName(), vr.getPrice(), vr.getImage());
	  orderItems.add(orderitem);
	}


	//fitness
	if(type.equals("fitness")){
	  Fitness fitness = null;
	  try{
	  allfitness = MySqlDataStoreUtilities.getfitness();
	  }
	  catch(Exception e){

	  }
	  fitness = allfitness.get(name);
	  OrderItem orderitem = new OrderItem(fitness.getName(), fitness.getPrice(), fitness.getImage());
	  orderItems.add(orderitem);
	}

	//smartWatch
	if(type.equals("smartwatch")){
	  Smartwatch smartwatch = null;
	  try{
	  allsmartwatchs = MySqlDataStoreUtilities.getsmartwatch();
	  }
	  catch(Exception e){

	  }
	  smartwatch = allsmartwatchs.get(name);
	  OrderItem orderitem = new OrderItem(smartwatch.getName(), smartwatch.getPrice(), smartwatch.getImage());
	  orderItems.add(orderitem);
	}

	//HeadphonesList
	if(type.equals("headphones")){
	  Headphones headphones = null;
	  try{
	  allheadphones = MySqlDataStoreUtilities.getheadphones();
	  }
	  catch(Exception e){

	  }
	  headphones = allheadphones.get(name);
	  OrderItem orderitem = new OrderItem(headphones.getName(), headphones.getPrice(), headphones.getImage());
	  orderItems.add(orderitem);
	}

	//Pettracker
	if(type.equals("pettracker")){
	  Pettracker pettracker = null;
	  try{
	  allpettrackers = MySqlDataStoreUtilities.getpettracker();
	  }
	  catch(Exception e){

	  }
	  pettracker = allpettrackers.get(name);
	  OrderItem orderitem = new OrderItem(pettracker.getName(), pettracker.getPrice(), pettracker.getImage());
	  orderItems.add(orderitem);
	}


	//Accessory
	if(type.equals("accessories")){
		try{
		allaccessories = MySqlDataStoreUtilities.getAccessories();
		}
		catch(Exception e){

		}
		Accessory accessory = allaccessories.get(name);
		OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage());
		orderItems.add(orderitem);
	}

}

/* public void storeProduct(String name, String type, String maker, String acc) {
        if (!OrdersHashMap.orders.containsKey(username())) {
            ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
            OrdersHashMap.orders.put(username(), arr);
        }

        ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());

        if (type.equals("fitness")) {
            Fitness fitness = null;
            fitness = SaxParserDataStore.fitnesss.get(name);
            OrderItem orderitem = new OrderItem(fitness.getName(), fitness.getPrice(), fitness.getImage());
            orderItems.add(orderitem);
        }

        if (type.equals("smartwatch")) {
            Smartwatch smartwatch = null;
            smartwatch = SaxParserDataStore.smartwatchs.get(name);
            OrderItem orderitem = new OrderItem(smartwatch.getName(), smartwatch.getPrice(), smartwatch.getImage());
            orderItems.add(orderitem);
        }

        if (type.equals("headphones")) {
            Headphones headphones = null;
            headphones = SaxParserDataStore.headphoness.get(name);
            OrderItem orderitem = new OrderItem(headphones.getName(), headphones.getPrice(), headphones.getImage());
            orderItems.add(orderitem);
        }

        if (type.equals("vr")) {
            VR virtualReality = null;
            virtualReality = SaxParserDataStore.vrs.get(name);
            OrderItem orderitem = new OrderItem(virtualReality.getName(), virtualReality.getPrice(), virtualReality.getImage());
            orderItems.add(orderitem);
        }

        if (type.equals("pettracker")) {
            Pettracker petTracker = null;
            petTracker = SaxParserDataStore.pettrackers.get(name);
            OrderItem orderitem = new OrderItem(petTracker.getName(), petTracker.getPrice(), petTracker.getImage());
            orderItems.add(orderitem);
        }

        if (type.equals("phone")) {
            Phone phone = null;
            phone = SaxParserDataStore.phones.get(name);
            // OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage(), phone.getRetailer());
            OrderItem orderitem = new OrderItem(phone.getId(), phone.getPrice(), phone.getImage());
            orderItems.add(orderitem);
        }
        if (type.equals("laptop")) {
            Laptop laptop = null;
            laptop = SaxParserDataStore.laptops.get(name);
            OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage());
            orderItems.add(orderitem);
        }
        if (type.equals("speaker")) {
            Speaker speaker = SaxParserDataStore.speakers.get(name);
            OrderItem orderitem = new OrderItem(speaker.getName(), speaker.getPrice(), speaker.getImage());
            orderItems.add(orderitem);
        }

        if (type.equals("accessories")) {
            Accessory accessory = null;
            accessory = SaxParserDataStore.accessorys.get(name);
            OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage());
            orderItems.add(orderitem);
        }
    }*/

//delete product
public void removeItemFromCart(String itemName) {
			ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
			int index = 0;


			for (OrderItem oi : orderItems) {
					if (oi.getName().equals(itemName)) {
							break;
					} else index++;
			}
			orderItems.remove(index);
	}

			//OrdersHashMap.orders.values(name);


			public boolean removeProduct(String productId, String catalog) {
			        switch (catalog) {
			            case "fitness":
			                SaxParserDataStore.fitnesss.remove(productId);
			                return true;


			            case "smartwatch":

			                SaxParserDataStore.smartwatchs.remove(productId);
			                return true;

			            case "vr":

			                SaxParserDataStore.vrs.remove(productId);
			                return true;

			            case "pettracker":

			                SaxParserDataStore.pettrackers.remove(productId);
			                return true;

			            case "headphones":

			                SaxParserDataStore.headphoness.remove(productId);
			                return true;

			            case "phone":

			                SaxParserDataStore.phones.remove(productId);
			                return true;

			            case "laptop":

			                SaxParserDataStore.laptops.remove(productId);
			                return true;

			            case "speaker":

			                SaxParserDataStore.speakers.remove(productId);
			                return true;
									case "accessory":

			                SaxParserDataStore.accessorys.remove(productId);
			                return true;

			        }
			        return false;
			    }



	// store the payment details for orders
	// public void storePayment(int orderId,
	// 	String orderName,double orderPrice,String userAddress,String creditCardNo){
	// 	HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
	// 	String TOMCAT_HOME = System.getProperty("catalina.home");
	// 		// get the payment details file
	// 		try
	// 		{
	// 			FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\WebStore\\PaymentDetails.txt"));
	// 			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	// 			orderPayments = (HashMap)objectInputStream.readObject();
	// 		}
	// 		catch(Exception e)
	// 		{
	//
	// 		}
	// 		if(orderPayments==null)
	// 		{
	// 			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
	// 		}
	// 		// if there exist order id already add it into same list for order id or create a new record with order id
	//
	// 		if(!orderPayments.containsKey(orderId)){
	// 			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
	// 			orderPayments.put(orderId, arr);
	// 		}
	// 	ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
	// 	OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
	// 	listOrderPayment.add(orderpayment);
	//
	// 		// add order details into file
	//
	// 		try
	// 		{
	// 			FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME+"\\webapps\\WebStore\\PaymentDetails.txt"));
	// 			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
  //     	objectOutputStream.writeObject(orderPayments);
	// 			objectOutputStream.flush();
	// 			objectOutputStream.close();
	// 			fileOutputStream.close();
	// 		}
	// 		catch(Exception e)
	// 		{
	// 			System.out.println("inside exception file not written properly");
	// 		}
	// }

	public void storePayment(int orderId,
													 String orderName, double orderPrice, String userAddress, String creditCardNo) {

			String username = (String) session.getAttribute("username");

			MySqlDataStoreUtilities.insertOrder(orderId, username, orderName, orderPrice, userAddress, creditCardNo);
	}

	public boolean removeOldOrder(int orderId, String orderName, String customerName) {
			return MySqlDataStoreUtilities.deleteOrder(orderId);
	}

	// public void storeNewOrder(int orderId, String orderName, String customerName, double orderPrice, String userAddress, String creditCardNo) {
  //       HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
  //       String TOMCAT_HOME = System.getProperty("catalina.home");
  //       // get the payment details file
  //       try {
  //           FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "\\webapps\\WebStore\\PaymentDetails.txt"));
  //           ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
  //           orderPayments = (HashMap) objectInputStream.readObject();
  //       } catch (Exception ignored) {
	//
  //       }
  //       if (orderPayments == null) {
  //           orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
  //       }
  //       // if there exist order id already add it into same list for order id or create a new record with order id
	//
  //       if (!orderPayments.containsKey(orderId)) {
  //           ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
  //           orderPayments.put(orderId, arr);
  //       }
  //       ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
	//
  //       OrderPayment orderpayment = new OrderPayment(orderId, customerName, orderName, orderPrice, userAddress, creditCardNo);
  //       listOrderPayment.add(orderpayment);
	//
  //       // add order details into file
  //       updateOrderFile(orderPayments);
	//
  //   }

	public void storeNewOrder(int orderId, String orderName, String customerName, double orderPrice, String userAddress, String creditCardNo) {
        MySqlDataStoreUtilities.insertOrder(orderId, customerName, orderName, orderPrice, userAddress, creditCardNo);

    }

		//Create Products
		public boolean createProduct(String id, String name, String price, String condition, String discount, String image, String type) {
        switch (type) {
            case "fitness":
                Fitness fitness = new Fitness();
                fitness.setId(id);
                fitness.setName(name);
                fitness.setPrice(Double.parseDouble(price));
              //  fitness.setRetailer(manufacturer);
                fitness.setCondition(condition);
                fitness.setDiscount(Double.parseDouble(discount));
                fitness.setImage(image);
                SaxParserDataStore.fitnesss.remove(id);
                SaxParserDataStore.fitnesss.put(id, fitness);

                return true;
            case "smartwatch":

                Smartwatch smartWatch = new Smartwatch();
                smartWatch.setId(id);
                smartWatch.setName(name);
                smartWatch.setPrice(Double.parseDouble(price));
                //smartWatch.setRetailer(manufacturer);
                smartWatch.setCondition(condition);
                smartWatch.setDiscount(Double.parseDouble(discount));
                smartWatch.setImage(image);
                SaxParserDataStore.smartwatchs.remove(id);
                SaxParserDataStore.smartwatchs.put(id, smartWatch);
                return true;
            case "vr":

                VR vr = new VR();
                vr.setId(id);
                vr.setName(name);
                vr.setPrice(Double.parseDouble(price));
              //  vr.setRetailer(manufacturer);
                vr.setCondition(condition);
                vr.setDiscount(Double.parseDouble(discount));
                vr.setImage(image);
                SaxParserDataStore.vrs.remove(id);
                SaxParserDataStore.vrs.put(id, vr);
                return true;
            case "pettracker":

                Pettracker petTracker = new Pettracker();
                petTracker.setId(id);
                petTracker.setName(name);
                petTracker.setPrice(Double.parseDouble(price));
              //  petTracker.setRetailer(manufacturer);
                petTracker.setCondition(condition);
                petTracker.setDiscount(Double.parseDouble(discount));
                petTracker.setImage(image);
                SaxParserDataStore.pettrackers.remove(id);
                SaxParserDataStore.pettrackers.put(id, petTracker);
                return true;
            case "headphones":

                Headphones headphone = new Headphones();
                headphone.setId(id);
                headphone.setName(name);
                headphone.setPrice(Double.parseDouble(price));
              //  headphone.setRetailer(manufacturer);
                headphone.setCondition(condition);
                headphone.setDiscount(Double.parseDouble(discount));
                headphone.setImage(image);
                SaxParserDataStore.headphoness.remove(id);
                SaxParserDataStore.headphoness.put(id, headphone);
                return true;
            case "phone":

                Phone phone = new Phone();
                phone.setId(id);
                phone.setName(name);
                phone.setPrice(Double.parseDouble(price));
              //  phone.setRetailer(manufacturer);
                phone.setCondition(condition);
                phone.setDiscount(Double.parseDouble(discount));
                phone.setImage(image);
                SaxParserDataStore.phones.remove(id);
                SaxParserDataStore.phones.put(id, phone);
                return true;
            case "laptop":

                Laptop laptop = new Laptop();
                laptop.setId(id);
                laptop.setName(name);
                laptop.setPrice(Double.parseDouble(price));
              //  laptop.setRetailer(manufacturer);
                laptop.setCondition(condition);
                laptop.setDiscount(Double.parseDouble(discount));
                laptop.setImage(image);
                SaxParserDataStore.laptops.remove(id);
                SaxParserDataStore.laptops.put(id, laptop);
                return true;
            case "speaker":

                Speaker speaker = new Speaker();
                speaker.setId(id);
                speaker.setName(name);
                speaker.setPrice(Double.parseDouble(price));
                //speaker.setRetailer(manufacturer);
                speaker.setCondition(condition);
                speaker.setDiscount(Double.parseDouble(discount));
                speaker.setImage(image);
                SaxParserDataStore.speakers.remove(id);
                SaxParserDataStore.speakers.put(id, speaker);
                return true;

						case "accessory":

						    Accessory accessory = new Accessory();
						    accessory.setId(id);
						    accessory.setName(name);
						    accessory.setPrice(Double.parseDouble(price));
						    //accessory.setRetailer(manufacturer);
						    accessory.setCondition(condition);
						    accessory.setDiscount(Double.parseDouble(discount));
						    accessory.setImage(image);
						    SaxParserDataStore.accessorys.remove(id);
						    SaxParserDataStore.accessorys.put(id, accessory);
						    return true;

        }
        return false;
    }
//Store Review
		public String storeReview(String productname, String producttype, String productmaker, String reviewrating,
	                              String reviewdate, String reviewtext, String reatilerpin, String price, String city, String userAge, String userGender, String userOccupation) {
	        String message = MongoDBDataStoreUtilities.insertReview(productname, username(), producttype, productmaker, reviewrating, reviewdate, reviewtext, reatilerpin, price, city, userAge, userGender, userOccupation);
	        if (!message.equals("Successful")) {
	            return "UnSuccessful";
	        } else {
	            HashMap<String, ArrayList<Review>> reviews = new HashMap<String, ArrayList<Review>>();
	            try {
	                reviews = MongoDBDataStoreUtilities.selectReview();
	            } catch (Exception e) {

	            }
	            if (reviews == null) {
	                reviews = new HashMap<String, ArrayList<Review>>();
	            }
	            // if there exist product review already add it into same list for productname or create a new record with product name

	            if (!reviews.containsKey(productname)) {
	                ArrayList<Review> arr = new ArrayList<Review>();
	                reviews.put(productname, arr);
	            }
	            ArrayList<Review> listReview = reviews.get(productname);
	            Review review = new Review(productname, username(), producttype, productmaker, reviewrating, reviewdate, reviewtext, reatilerpin, price, city, userAge, userGender, userOccupation);
	            listReview.add(review);

	            // add Reviews into database

	            return "Successful";
	        }
	    }

		///////////////////////////////////////////////////////////////////////////////////////////////

		public boolean updateProduct(String id, String name, String price, String condition, String discount, String image, String type) {
        switch (type) {
            case "fitness":
                Fitness fitness = new Fitness();
                fitness.setId(id);
                fitness.setName(name);
                fitness.setPrice(Double.parseDouble(price));
              //  fitness.setRetailer(manufacturer);
                fitness.setCondition(condition);
                fitness.setDiscount(Double.parseDouble(discount));
                fitness.setImage(image);
                SaxParserDataStore.fitnesss.remove(id);
                SaxParserDataStore.fitnesss.put(id, fitness);

                return true;
            case "smartwatch":

                Smartwatch smartWatch = new Smartwatch();
                smartWatch.setId(id);
                smartWatch.setName(name);
                smartWatch.setPrice(Double.parseDouble(price));
                //smartWatch.setRetailer(manufacturer);
                smartWatch.setCondition(condition);
                smartWatch.setDiscount(Double.parseDouble(discount));
                smartWatch.setImage(image);
                SaxParserDataStore.smartwatchs.remove(id);
                SaxParserDataStore.smartwatchs.put(id, smartWatch);
                return true;
            case "vr":

                VR vr = new VR();
                vr.setId(id);
                vr.setName(name);
                vr.setPrice(Double.parseDouble(price));
              //  vr.setRetailer(manufacturer);
                vr.setCondition(condition);
                vr.setDiscount(Double.parseDouble(discount));
                vr.setImage(image);
                SaxParserDataStore.vrs.remove(id);
                SaxParserDataStore.vrs.put(id, vr);
                return true;
            case "pettracker":

                Pettracker petTracker = new Pettracker();
                petTracker.setId(id);
                petTracker.setName(name);
                petTracker.setPrice(Double.parseDouble(price));
              //  petTracker.setRetailer(manufacturer);
                petTracker.setCondition(condition);
                petTracker.setDiscount(Double.parseDouble(discount));
                petTracker.setImage(image);
                SaxParserDataStore.pettrackers.remove(id);
                SaxParserDataStore.pettrackers.put(id, petTracker);
                return true;
            case "headphones":

                Headphones headphone = new Headphones();
                headphone.setId(id);
                headphone.setName(name);
                headphone.setPrice(Double.parseDouble(price));
              //  headphone.setRetailer(manufacturer);
                headphone.setCondition(condition);
                headphone.setDiscount(Double.parseDouble(discount));
                headphone.setImage(image);
                SaxParserDataStore.headphoness.remove(id);
                SaxParserDataStore.headphoness.put(id, headphone);
                return true;
            case "phone":

                Phone phone = new Phone();
                phone.setId(id);
                phone.setName(name);
                phone.setPrice(Double.parseDouble(price));
              //  phone.setRetailer(manufacturer);
                phone.setCondition(condition);
                phone.setDiscount(Double.parseDouble(discount));
                phone.setImage(image);
                SaxParserDataStore.phones.remove(id);
                SaxParserDataStore.phones.put(id, phone);
                return true;
            case "laptop":

                Laptop laptop = new Laptop();
                laptop.setId(id);
                laptop.setName(name);
                laptop.setPrice(Double.parseDouble(price));
              //  laptop.setRetailer(manufacturer);
                laptop.setCondition(condition);
                laptop.setDiscount(Double.parseDouble(discount));
                laptop.setImage(image);
                SaxParserDataStore.laptops.remove(id);
                SaxParserDataStore.laptops.put(id, laptop);
                return true;
            case "speaker":

                Speaker speaker = new Speaker();
                speaker.setId(id);
                speaker.setName(name);
                speaker.setPrice(Double.parseDouble(price));
                //speaker.setRetailer(manufacturer);
                speaker.setCondition(condition);
                speaker.setDiscount(Double.parseDouble(discount));
                speaker.setImage(image);
                SaxParserDataStore.speakers.remove(id);
                SaxParserDataStore.speakers.put(id, speaker);
                return true;

						case "accessory":

						    Accessory accessory = new Accessory();
						    accessory.setId(id);
						    accessory.setName(name);
						    accessory.setPrice(Double.parseDouble(price));
						    //accessory.setRetailer(manufacturer);
						    accessory.setCondition(condition);
						    accessory.setDiscount(Double.parseDouble(discount));
						    accessory.setImage(image);
						    SaxParserDataStore.accessorys.remove(id);
						    SaxParserDataStore.accessorys.put(id, accessory);
						    return true;

        }
        return false;
    }

		public boolean isContainsStr(String string) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(string);
        return m.matches();
    }

		public boolean isItemExist(String itemCatalog, String itemName) {

        HashMap<String, Object> hm = new HashMap<String, Object>();

        switch (itemCatalog) {
            case "fitness":
                hm.putAll(SaxParserDataStore.fitnesss);
                break;
            case "watch":
                hm.putAll(SaxParserDataStore.smartwatchs);
                break;
            case "vr":
                hm.putAll(SaxParserDataStore.vrs);
                break;
            case "pettracker":
                hm.putAll(SaxParserDataStore.pettrackers);
                break;
            case "headphone":
                hm.putAll(SaxParserDataStore.headphoness);
                break;
            case "phone":
                hm.putAll(SaxParserDataStore.phones);
                break;
            case "laptop":
                hm.putAll(SaxParserDataStore.laptops);
                break;
            case "speaker":
                hm.putAll(SaxParserDataStore.speakers);
                break;
          case "accessory":
              hm.putAll(SaxParserDataStore.accessorys);
              break;
        }
        return true;
    }


// 		public void removeOldOrder(int orderId, String orderName, String customerName) {
//         String TOMCAT_HOME = System.getProperty("catalina.home");
//         HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
//         ArrayList<OrderPayment> ListOrderPayment = new ArrayList<OrderPayment>();
//         //get the order from file
//         try {
//             FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "\\webapps\\WebStore\\PaymentDetails.txt"));
//             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//             orderPayments = (HashMap) objectInputStream.readObject();
//         } catch (Exception e) {
//
//         }
//         //get the exact order with same ordername and add it into cancel list to remove it later
//         for (OrderPayment oi : orderPayments.get(orderId)) {
//             if (oi.getOrderName().equals(orderName) && oi.getUserName().equals(customerName)) {
//                 ListOrderPayment.add(oi);
//                 //pw.print("<h4 style='color:red'>Your Order is Cancelled</h4>");
// //                        response.sendRedirect("SalesmanHome");
// //                        return;
//             }
//         }
//         //remove all the orders from hashmap that exist in cancel list
//         orderPayments.get(orderId).removeAll(ListOrderPayment);
//         if (orderPayments.get(orderId).size() == 0) {
//             orderPayments.remove(orderId);
//         }
//
//         //save the updated hashmap with removed order to the file
//         updateOrderFile(orderPayments);
//     }



		public boolean updateOrderFile(HashMap<Integer, ArrayList<OrderPayment>> orderPayments) {
        String TOMCAT_HOME = System.getProperty("catalina.home");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME + "\\webapps\\WebStore\\PaymentDetails.txt"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(orderPayments);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {

        }
        return true;
    }

		// public void updateOrder(int orderId, String customerName,
    //                         String orderName, double orderPrice, String userAddress, String creditCardNo) {
    //     HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
    //     String TOMCAT_HOME = System.getProperty("catalina.home");
    //     // get the payment details file
    //     try {
    //         FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "\\webapps\\WebStore\\PaymentDetails.txt"));
    //         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    //         orderPayments = (HashMap) objectInputStream.readObject();
    //     } catch (Exception ignored) {
		//
    //     }
    //     if (orderPayments == null) {
    //         orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
    //     }
    //     // if there exist order id already add it into same list for order id or create a new record with order id
		//
    //     if (!orderPayments.containsKey(orderId)) {
    //         ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
    //         orderPayments.put(orderId, arr);
    //     }
    //     ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
		//
    //     OrderPayment orderpayment = new OrderPayment(orderId, customerName, orderName, orderPrice, userAddress, creditCardNo);
    //     listOrderPayment.add(orderpayment);
		//
    //     // add order details into file
    //     updateOrderFile(orderPayments);
    // }

		public void updateOrder(int orderId, String customerName,
                            String orderName, double orderPrice, String userAddress, String creditCardNo) {
        MySqlDataStoreUtilities.deleteOrder(orderId);
        MySqlDataStoreUtilities.insertOrder(orderId, orderName, customerName, orderPrice, userAddress, creditCardNo);
    }


	/* getConsoles Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Laptop> getlaptop(){
			HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
			hm.putAll(SaxParserDataStore.laptops);
			return hm;
	}

	/* getphones Functions returns the  Hashmap with all phones in the store.*/

	public HashMap<String, Phone> getphone(){
			HashMap<String, Phone> hm = new HashMap<String, Phone>();
			hm.putAll(SaxParserDataStore.phones);
			return hm;
	}

	/* getspeakers Functions returns the Hashmap with all speaker in the store.*/

	public HashMap<String, Speaker> getspeaker(){
			HashMap<String, Speaker> hm = new HashMap<String, Speaker>();
			hm.putAll(SaxParserDataStore.speakers);
			return hm;
	}

	public HashMap<String, Headphones> getheadphones(){
			HashMap<String, Headphones> hm = new HashMap<String, Headphones>();
			hm.putAll(SaxParserDataStore.headphoness);
			return hm;
	}

	public HashMap<String, Pettracker> getpettracker(){
			HashMap<String, Pettracker> hm = new HashMap<String, Pettracker>();
			hm.putAll(SaxParserDataStore.pettrackers);
			return hm;
	}

	public HashMap<String, Fitness> getfitness(){
			HashMap<String, Fitness> hm = new HashMap<String, Fitness>();
			hm.putAll(SaxParserDataStore.fitnesss);
			return hm;
	}

	public HashMap<String, Smartwatch> getsmartwatch(){
			HashMap<String, Smartwatch> hm = new HashMap<String, Smartwatch>();
			hm.putAll(SaxParserDataStore.smartwatchs);
			return hm;
	}

	public HashMap<String, VR> getvr(){
			HashMap<String, VR> hm = new HashMap<String, VR>();
			hm.putAll(SaxParserDataStore.vrs);
			return hm;
	}

	public HashMap<String, Accessory> getaccessory(){
			HashMap<String, Accessory> hm = new HashMap<String, Accessory>();
			hm.putAll(SaxParserDataStore.accessorys);
			return hm;
	}
	/* getProducts Functions returns the Arraylist of consoles in the store.*/

	public ArrayList<String> getProductslaptop(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Laptop> entry : getlaptop().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of phones in the store.*/

	public ArrayList<String> getProductsphone(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Phone> entry : getphone().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of speakers in the store.*/

	public ArrayList<String> getProductsspeaker(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Speaker> entry : getspeaker().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsfitness(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Fitness> entry : getfitness().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductssmartwatch(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Smartwatch> entry : getsmartwatch().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsvr(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String,VR> entry : getvr().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductspettracker(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Pettracker> entry : getpettracker().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsheadphones(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Headphones> entry : getheadphones().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsaccessory(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Accessory> entry : getaccessory().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}



}
