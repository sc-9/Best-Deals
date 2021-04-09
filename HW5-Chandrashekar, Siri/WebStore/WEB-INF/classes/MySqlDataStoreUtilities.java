import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MySqlDataStoreUtilities {
    static Connection conn = null;

    public static void getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledatabase?useUnicode=true&characterEncoding=utf8", "root", "root");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static boolean deleteOrder(int orderId) {
        try {

            getConnection();
            String deleteOrderQuery = "Delete from orders where OrderId=?";
            PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
            pst.setInt(1, orderId);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void insertOrder(int orderId, String userName, String orderName, double orderPrice, String userAddress, String creditCardNo) {
        try {


            Date current_date = new Date();

            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            getConnection();

            String insertIntoCustomerOrderQuery = "insert into orders (orderID, userName, orderName, orderPrice, userAddress, creditCardNo, orderTime) VALUES (?,?,?,?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
            //set the parameter for each column and execute the prepared statement
            pst.setInt(1, orderId);
            pst.setString(2, userName);
            pst.setString(3, orderName);
            pst.setDouble(4, orderPrice);
            pst.setString(5, userAddress);
            pst.setString(6, creditCardNo);
            pst.setString(7, SimpleDateFormat.format(current_date.getTime()));
            pst.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder() {

        HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();

        try {

            getConnection();
            //select the table
            String selectOrderQuery = "select * from orders";
            PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
            ResultSet rs = pst.executeQuery();
            ArrayList<OrderPayment> orderList = new ArrayList<OrderPayment>();
            while (rs.next()) {
                if (!orderPayments.containsKey(rs.getInt("OrderId"))) {
                    ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
                    orderPayments.put(rs.getInt("orderId"), arr);
                }
                ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));
                System.out.println("data is" + rs.getInt("OrderId") + orderPayments.get(rs.getInt("OrderId")));

                //add to orderpayment hashmap
                OrderPayment order = new OrderPayment(rs.getInt("OrderId"), rs.getString("userName"), rs.getString("orderName"), rs.getDouble("orderPrice"), rs.getString("userAddress"), rs.getString("creditCardNo"));
                listOrderPayment.add(order);

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return orderPayments;
    }


    public static boolean insertUser(String username, String password, String rePassword, String userType) {
        try {

            getConnection();
            String insertIntoCustomerRegisterQuery = "INSERT INTO user(username,password,repassword,usertype) "
                    + "VALUES (?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, rePassword);
            pst.setString(4, userType);
            pst.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static HashMap<String, User> selectUser() {
        HashMap<String, User> hm = new HashMap<String, User>();
        try {
            getConnection();
            Statement stmt = conn.createStatement();
            String selectCustomerQuery = "select * from user";
            ResultSet rs = stmt.executeQuery(selectCustomerQuery);
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("usertype"));
                hm.put(rs.getString("username"), user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hm;
    }

/************************************************************************/
    //Products
    public static void Insertproducts()
{
	try{

		getConnection();
		String insertProductQurey = "INSERT INTO  productdetails(ProductType,Id,productName,productPrice,productImage,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?);";
    //Accessories
		for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessorys.entrySet())
		{
			String name = "accessories";
	        Accessory acc = entry.getValue();

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,acc.getId());
			pst.setString(3,acc.getName());
			pst.setDouble(4,acc.getPrice());
			pst.setString(5,acc.getImage());
			pst.setString(6,acc.getCondition());
			pst.setDouble(7,acc.getDiscount());

			pst.executeUpdate();


		}
    //Laptop
    for(Map.Entry<String,Laptop> entry : SaxParserDataStore.laptops.entrySet())
    {
          Laptop con = entry.getValue();
      String name = "laptop";



      PreparedStatement pst = conn.prepareStatement(insertProductQurey);
      pst.setString(1,name);
      pst.setString(2,con.getId());
      pst.setString(3,con.getName());
      pst.setDouble(4,con.getPrice());
      pst.setString(5,con.getImage());
    //\\  pst.setString(6,con.getRetailer());
      pst.setString(6,con.getCondition());
      pst.setDouble(7,con.getDiscount());

      pst.executeUpdate();
      // try{
      // HashMap<String,String> acc = con.getAccessories();
      // String insertAccessoryQurey = "INSERT INTO  Product_accessories(productName,accessoriesName)" +
      // "VALUES (?,?);";
      // for(Map.Entry<String,String> accentry : acc.entrySet())
      // {
      //   PreparedStatement pstacc = conn.prepareStatement(insertAccessoryQurey);
      //   pstacc.setString(1,con.getId());
      //   pstacc.setString(2,accentry.getValue());
      //   pstacc.executeUpdate();
      // }
      // }catch(Exception et){
      //   et.printStackTrace();
      // }
    }
    //Phones
		for(Map.Entry<String,Phone> entry : SaxParserDataStore.phones.entrySet())
		{
			String name = "phone";
	        Phone phone = entry.getValue();

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,phone.getId());
			pst.setString(3,phone.getName());
			pst.setDouble(4,phone.getPrice());
			pst.setString(5,phone.getImage());
		//	pst.setString(6,game.getRetailer());
			pst.setString(6,phone.getCondition());
			pst.setDouble(7,phone.getDiscount());

			pst.executeUpdate();


		}
    //Speakers
		for(Map.Entry<String,Speaker> entry : SaxParserDataStore.speakers.entrySet())
		{
			String name = "speaker";
	        Speaker speaker = entry.getValue();

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,speaker.getId());
			pst.setString(3,speaker.getName());
			pst.setDouble(4,speaker.getPrice());
			pst.setString(5,speaker.getImage());
			//pst.setString(6,speaker.getRetailer());
			pst.setString(6,speaker.getCondition());
			pst.setDouble(7,speaker.getDiscount());

			pst.executeUpdate();


		}

    //Fitness Watches
    for(Map.Entry<String,Fitness> entry : SaxParserDataStore.fitnesss.entrySet())
    {
      String name = "fitness";
          Fitness fitness = entry.getValue();

      PreparedStatement pst = conn.prepareStatement(insertProductQurey);
      pst.setString(1,name);
      pst.setString(2,fitness.getId());
      pst.setString(3,fitness.getName());
      pst.setDouble(4,fitness.getPrice());
      pst.setString(5,fitness.getImage());
    //  pst.setString(6,fitness.getRetailer());
      pst.setString(6,fitness.getCondition());
      pst.setDouble(7,fitness.getDiscount());

      pst.executeUpdate();


    }

    //Smart Watches
    for(Map.Entry<String,Smartwatch> entry : SaxParserDataStore.smartwatchs.entrySet())
    {
      String name = "smartwatch";
          Smartwatch smartwatch = entry.getValue();

      PreparedStatement pst = conn.prepareStatement(insertProductQurey);
      pst.setString(1,name);
      pst.setString(2,smartwatch.getId());
      pst.setString(3,smartwatch.getName());
      pst.setDouble(4,smartwatch.getPrice());
      pst.setString(5,smartwatch.getImage());
  //    pst.setString(6,smartwatch.getRetailer());
      pst.setString(6,smartwatch.getCondition());
      pst.setDouble(7,smartwatch.getDiscount());

      pst.executeUpdate();


    }

    //Virtual Reality
    for(Map.Entry<String,VR> entry : SaxParserDataStore.vrs.entrySet())
    {
      String name = "vr";
          VR vr = entry.getValue();

      PreparedStatement pst = conn.prepareStatement(insertProductQurey);
      pst.setString(1,name);
      pst.setString(2,vr.getId());
      pst.setString(3,vr.getName());
      pst.setDouble(4,vr.getPrice());
      pst.setString(5,vr.getImage());
  //    pst.setString(6,vr.getRetailer());
      pst.setString(6,vr.getCondition());
      pst.setDouble(7,vr.getDiscount());

      pst.executeUpdate();


    }

    //Pet Tracker
    for(Map.Entry<String,Pettracker> entry : SaxParserDataStore.pettrackers.entrySet())
    {
      String name = "pettracker";
          Pettracker pettracker = entry.getValue();

      PreparedStatement pst = conn.prepareStatement(insertProductQurey);
      pst.setString(1,name);
      pst.setString(2,pettracker.getId());
      pst.setString(3,pettracker.getName());
      pst.setDouble(4,pettracker.getPrice());
      pst.setString(5,pettracker.getImage());
    //  pst.setString(6,pettracker.getRetailer());
      pst.setString(6,pettracker.getCondition());
      pst.setDouble(7,pettracker.getDiscount());

      pst.executeUpdate();


    }

    //Headphones

    for(Map.Entry<String,Headphones> entry : SaxParserDataStore.headphoness.entrySet())
    {
      String name = "headphones";
          Headphones headphones = entry.getValue();

      PreparedStatement pst = conn.prepareStatement(insertProductQurey);
      pst.setString(1,name);
      pst.setString(2,headphones.getId());
      pst.setString(3,headphones.getName());
      pst.setDouble(4,headphones.getPrice());
      pst.setString(5,headphones.getImage());
    //  pst.setString(6,headphones.getRetailer());
      pst.setString(6,headphones.getCondition());
      pst.setDouble(7,headphones.getDiscount());

      pst.executeUpdate();


    }



	}catch(Exception e)
	{
  		e.printStackTrace();
	}
}


//Laptops
public static HashMap<String,Laptop> getlaptop()
{
	HashMap<String,Laptop> hm=new HashMap<String,Laptop>();
	try
	{
		getConnection();

		String selectlaptop="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectlaptop);
		pst.setString(1,"laptop");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Laptop laptop = new Laptop(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), laptop);
				laptop.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}


public static HashMap<String,Phone> getphone()
{
	HashMap<String,Phone> hm=new HashMap<String,Phone>();
	try
	{
		getConnection();

		String selectphone="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectphone);
		pst.setString(1,"phone");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Phone phone = new Phone(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), phone);
				phone.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}

//Speakers
public static HashMap<String,Speaker> getspeaker()
{
	HashMap<String,Speaker> hm=new HashMap<String,Speaker>();
	try
	{
		getConnection();

		String selectspeaker="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectspeaker);
		pst.setString(1,"speaker");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Speaker speaker = new Speaker(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), speaker);
				speaker.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}

//Fitness Watches

public static HashMap<String,Fitness> getfitness()
{
	HashMap<String,Fitness> hm=new HashMap<String,Fitness>();
	try
	{
		getConnection();

		String selectfitness="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectfitness);
		pst.setString(1,"fitness");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Fitness fitness = new Fitness(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), fitness);
				fitness.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}

//Smart Watches

public static HashMap<String,Smartwatch> getsmartwatch()
{
	HashMap<String,Smartwatch> hm=new HashMap<String,Smartwatch>();
	try
	{
		getConnection();

		String selectsmartwatch="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectsmartwatch);
		pst.setString(1,"smartwatch");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Smartwatch smartwatch = new Smartwatch(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), smartwatch);
				smartwatch.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}

//Pet Tracker

public static HashMap<String,Pettracker> getpettracker()
{
	HashMap<String,Pettracker> hm=new HashMap<String,Pettracker>();
	try
	{
		getConnection();

		String selectpettracker="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectpettracker);
		pst.setString(1,"pettracker");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Pettracker pettracker = new Pettracker(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), pettracker);
				pettracker.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}




//HeadphonesList
public static HashMap<String,Headphones> getheadphones()
{
	HashMap<String,Headphones> hm=new HashMap<String,Headphones>();
	try
	{
		getConnection();

		String selectheadphones="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectheadphones);
		pst.setString(1,"headphones");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Headphones headphones = new Headphones(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), headphones);
				headphones.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}


//Virtual Reality
public static HashMap<String,VR> getvr()
{
	HashMap<String,VR> hm=new HashMap<String,VR>();
	try
	{
		getConnection();

		String selectvr="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectvr);
		pst.setString(1,"vr");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	VR vr = new VR(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), vr);
				vr.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}





//Accessories
public static HashMap<String,Accessory> getAccessories()
{
	HashMap<String,Accessory> hm=new HashMap<String,Accessory>();
	try
	{
		getConnection();

		String selectAcc="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		pst.setString(1,"accessory");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	Accessory acc = new Accessory(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), acc);
				acc.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}

public static String addproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productCondition,double productDiscount,String prod)
{
	String msg = "Product is added successfully";
	try{

		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?);";

			String name = producttype;

			PreparedStatement pst = conn.prepareStatement(addProductQurey);
			pst.setString(1,name);
			pst.setString(2,productId);
			pst.setString(3,productName);
			pst.setDouble(4,productPrice);
			pst.setString(5,productImage);
		//	pst.setString(6,productManufacturer);
			pst.setString(6,productCondition);
			pst.setDouble(7,productDiscount);

			pst.executeUpdate();
			// try{
			// 	if (!prod.isEmpty())
			// 	{
			// 		String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
			// 		"VALUES (?,?);";
			// 		PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
			// 		pst1.setString(1,prod);
			// 		pst1.setString(2,productId);
			// 		pst1.executeUpdate();
      //
			// 	}
			// }catch(Exception e)
			// {
			// 	msg = "Erro while adding the product";
			// 	e.printStackTrace();
      //
			// }



	}
	catch(Exception e)
	{
		msg = "Erro while adding the product";
		e.printStackTrace();

	}
	return msg;
}
public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productCondition,double productDiscount)
{
    String msg = "Product is updated successfully";
	try{

		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productCondition=?,productDiscount=? where Id =?;" ;



			PreparedStatement pst = conn.prepareStatement(updateProductQurey);

			pst.setString(1,productName);
			pst.setDouble(2,productPrice);
			pst.setString(3,productImage);
			//pst.setString(4,productManufacturer);
			pst.setString(4,productCondition);
			pst.setDouble(5,productDiscount);
			pst.setString(6,productId);
			pst.executeUpdate();



	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();

	}
 return msg;
}
public static String deleteproducts(String productId)
{   String msg = "Product is deleted successfully";
	try
	{

		getConnection();
		String deleteproductsQuery ="Delete from Productdetails where Id=?";
		PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
		pst.setString(1,productId);

		pst.executeUpdate();
	}
	catch(Exception e)
	{
			msg = "Proudct cannot be deleted";
	}
	return msg;
}

public static HashMap<String, Product> selectOnSale() {
        HashMap<String, Product> hm = new HashMap<String, Product>();
        try {
            getConnection();

            String selectAcc = "select * from productdetails where productCondition = 'new'";
            PreparedStatement pst = conn.prepareStatement(selectAcc);
          //  pst.setString(1, "1");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getString("productName"), rs.getDouble("productPrice"), Integer.parseInt(rs.getString("inventory")));
                hm.put(rs.getString("Id"), product);
                product.setId(rs.getString("Id"));
            }
        } catch (Exception e) {
        }
        return hm;
    }

    public static HashMap<String, Product> selectRebate() {
        HashMap<String, Product> hm = new HashMap<String, Product>();
        try {
            getConnection();

            String selectAcc = "select * from Productdetails where productDiscount > 0";
            PreparedStatement pst = conn.prepareStatement(selectAcc);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getString("productName"), rs.getDouble("productPrice"), Double.parseDouble(rs.getString("productDiscount")));
                hm.put(rs.getString("Id"), product);
                product.setId(rs.getString("Id"));
            }
        } catch (Exception e) {
        }
        return hm;
    }


    public static HashMap<String, OrderPayment> selectSaleAmount() {
        HashMap<String, OrderPayment> hm = new HashMap<String, OrderPayment>();
        try {
            getConnection();

            String selectAcc = "select DISTINCT(temp.orderName),temp.saleAmount,orders.orderPrice from orders, (select orderName, count(orderName) as saleAmount from orders group by orderName) as temp where orders.orderName = temp.orderName";
            PreparedStatement pst = conn.prepareStatement(selectAcc);
            ResultSet rs = pst.executeQuery();

            int i = 0;
            while (rs.next()) {
                OrderPayment orderPayment = new OrderPayment(rs.getString("orderName"), rs.getDouble("orderPrice"), rs.getInt("saleAmount"));
                i++;
                hm.put(String.valueOf(i), orderPayment);
                //orderPayment.setOrderId(Integer.parseInt(rs.getString("Id")));
            }
        } catch (Exception e) {
        }
        return hm;
    }
    public static HashMap<String, Product> selectInventory() {
      HashMap<String, Product> hm = new HashMap<String, Product>();
      try {
          getConnection();

          String selectAcc = "select * from productdetails";
          PreparedStatement pst = conn.prepareStatement(selectAcc);
          ResultSet rs = pst.executeQuery();

          while (rs.next()) {
              Product product = new Product(rs.getString("productName"), rs.getDouble("productPrice"), Integer.parseInt(rs.getString("inventory")));
              hm.put(rs.getString("Id"), product);
              product.setId(rs.getString("Id"));
          }
      } catch (Exception e) {

      }
      return hm;
  }

  public static HashMap<String, OrderPayment> selectDailyTransaction() {
          HashMap<String, OrderPayment> hm = new HashMap<String, OrderPayment>();
          try {
              getConnection();

              String selectAcc = "SELECT count(orderTime) as soldAmount, orderTime from orders group by orderTime";
              PreparedStatement pst = conn.prepareStatement(selectAcc);
              ResultSet rs = pst.executeQuery();

              int i = 0;
              while (rs.next()) {
                  OrderPayment orderPayment = new OrderPayment(rs.getInt("soldAmount"), rs.getDate("orderTime"));
                  i++;
                  hm.put(String.valueOf(i), orderPayment);
                  //orderPayment.setId(rs.getString("Id"));
              }
          } catch (Exception e) {
          }
          return hm;
      }

      public static ArrayList<OrderPayment> selectDailyTransactionForChart() {
          ArrayList<OrderPayment> orderPaymentArrayList = new ArrayList<OrderPayment>();
          try {
              getConnection();

              String selectAcc = "SELECT count(orderTime) as soldAmount, orderTime from orders group by orderTime";
              PreparedStatement pst = conn.prepareStatement(selectAcc);
              ResultSet rs = pst.executeQuery();

              while (rs.next()) {
                  OrderPayment orderPayment = new OrderPayment(rs.getInt("soldAmount"), rs.getDate("orderTime"));
                  orderPaymentArrayList.add(orderPayment);
              }
          } catch (Exception e) {
          }
          return orderPaymentArrayList;
      }
      public static HashMap<String,Product> getData()
  	{
  		HashMap<String,Product> hm=new HashMap<String,Product>();
  		try
  		{
  			getConnection();
  			Statement stmt=conn.createStatement();
  			String selectCustomerQuery="select * from  productdetails";
  			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
  			while(rs.next())
  			{	Product p = new Product(rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productCondition"),rs.getString("ProductType"),rs.getDouble("productDiscount"));
  				hm.put(rs.getString("Id"), p);
  			}
  		}
  		catch(Exception e)
  		{
  		e.printStackTrace();
  		}
  		return hm;
  	}

    public static HashMap<String,Console> getConsoles()
    {
    	HashMap<String,Console> hm=new HashMap<String,Console>();
    	try
    	{
    		getConnection();

    		String selectConsole="select * from  Productdetails where ProductType=?";
    		PreparedStatement pst = conn.prepareStatement(selectConsole);
    		pst.setString(1,"consoles");
    		ResultSet rs = pst.executeQuery();

    		while(rs.next())
    		{	Console con = new Console(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("retailer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
    				hm.put(rs.getString("Id"), con);
    				con.setId(rs.getString("Id"));

    				try
    				{
    				String selectaccessory = "Select * from Product_accessories where productName=?";
    				PreparedStatement pstacc = conn.prepareStatement(selectaccessory);
    				pstacc.setString(1,rs.getString("Id"));
    				ResultSet rsacc = pstacc.executeQuery();

    				HashMap<String,String> acchashmap = new HashMap<String,String>();
    				while(rsacc.next())
    				{
    					if (rsacc.getString("accessoriesName") != null){

    					 acchashmap.put(rsacc.getString("accessoriesName"),rsacc.getString("accessoriesName"));
    					 con.setAccessories(acchashmap);
    					}

    				}
    				}catch(Exception e)
    				{
    						e.printStackTrace();
    				}
    		}
    	}
    	catch(Exception e)
    	{
    	}
    	return hm;
    }


}
