import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StoreManagerHome")

public class StoreManagerHome extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		//pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Login</a></h2>"
			//	+ "<div class='entry'>"
			//	+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<form method='get' action='ProductModify'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<input type='submit' class='btn-danger btnbuy' name='button' value='Addproduct'style = 'font-size:25px'></input>"
				+ "</td></tr><tr><td></td></tr><tr><td>"
				+ "<input type='submit' class='btn-danger btnbuy' name='button' value='Updateproduct' style = 'font-size:25px'></input>"
				+ "</td></tr><tr><td></td></tr><tr><td>"
				+ "<input type='submit' class='btn-danger btnbuy' name='button' value='Deleteproduct' style = 'font-size:25px'></input>"
				+ "</td></tr><tr><td></td></tr>"
				//+ "<tr><td><input type='submit' class='btnbuy' name='button' value='Trending' style = 'font-size:50px'></input>"
			//	+ "</td></tr></table>"
				+ "</table></form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}


// // import org.apache.commons.fileupload.FileItem;
// // import org.apache.commons.fileupload.FileUploadException;
// // import org.apache.commons.fileupload.disk.DiskFileItemFactory;
// // import org.apache.commons.fileupload.servlet.ServletFileUpload;
// // import org.apache.commons.io.IOUtils;
//
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.*;
// import java.util.*;
//
// @WebServlet("/StoreManagerHome")
// public class StoreManagerHome extends HttpServlet {
//
//     private String error_msg;
//
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//         PrintWriter pw = response.getWriter();
//         displayStoreManagerHome(request, response, pw, "");
//     }
//
//     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//         response.setContentType("text/html");
//         PrintWriter pw = response.getWriter();
//         Utilities utility = new Utilities(request, pw);
//
//         //Add New product
//         Map<String, Object> map = new HashMap<String, Object>();
//
//         //DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
//         //ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
//         String catalog;
//         // try {
//         //     List<FileItem> parseRequest = servletFileUpload.parseRequest(request);
//         //     for (FileItem fileItem : parseRequest) {
//         //         boolean formField = fileItem.isFormField();
//         //         if (formField) {
//         //
//         //             String fieldName = fileItem.getFieldName();
//         //             String fieldValue = fileItem.getString();
//         //             map.put(fieldName, fieldValue);
//         //         } else {
//         //
//         //
//         //             catalog = String.valueOf(map.get("productCatalog"));
//         //             String realPath = utility.getRealPath(catalog);
//         //
//         //             String fileName = fileItem.getName();
//         //           //  String path = this.getServletContext().getRealPath(realPath);
//         //             InputStream inputStream = fileItem.getInputStream();
//         //           //  OutputStream outputStream = new FileOutputStream(path + "/" + fileName);
//         //             //IOUtils.copy(inputStream, outputStream);
//         //             inputStream.close();
//         //             outputStream.close();
//         //             //fileItem.delete();
//         //
//         //             map.put("image", fileName);
//         //         }
//         //     }
//         //
//         //     if (utility.storeNewProduct(map)) {
//         //
//         //         error_msg = "Completed!";
//         //         displayStoreManagerHome(request, response, pw, "newProduct");
//         //     } else {
//         //
//         //         error_msg = "Cannot add new product!";
//         //         displayStoreManagerHome(request, response, pw, "newProduct");
//         //     }
//         //
//         // } catch (FileUploadException e) {
//         //     e.printStackTrace();
//         // }
//     }
//
//     public boolean storeNewProduct(Map<String, Object> map) {
//         String id = String.valueOf(map.get("id"));
//         String name = String.valueOf(map.get("name"));
//         double price = Double.parseDouble(String.valueOf(map.get("price")));
//         String image = String.valueOf(map.get("image"));
//         //String retailer = String.valueOf(map.get("manufacturer"));
//         String condition = String.valueOf(map.get("condition"));
//         double discount = Double.parseDouble(String.valueOf(map.get("discount")));
//         String catalog = String.valueOf(map.get("productCatalog"));
//
//         switch (catalog) {
//             case "fitness":
//                 Fitness fitness = new Fitness();
//                 fitness.setId(id);
//                 fitness.setName(name);
//                 fitness.setPrice(price);
//                 fitness.setImage(image);
//                 //fitness.setRetailer(retailer);
//                 fitness.setCondition(condition);
//                 fitness.setDiscount(discount);
//                 SaxParserDataStore.fitnesss.put(id, fitness);
//                 return true;
//             case "smartwatch":
//                 Smartwatch smartwatch = new Smartwatch();
//                 smartwatch.setId(id);
//                 smartwatch.setName(name);
//                 smartwatch.setPrice(price);
//                 smartwatch.setImage(image);
//                 //smartwatch.setRetailer(retailer);
//                 smartwatch.setCondition(condition);
//                 smartwatch.setDiscount(discount);
//                 SaxParserDataStore.smartwatchs.put(id, smartwatch);
//                 return true;
//             case "vr":
//                 VR vr = new VR();
//                 vr.setId(id);
//                 vr.setName(name);
//                 vr.setPrice(price);
//                 vr.setImage(image);
//               //  vr.setRetailer(retailer);
//                 vr.setCondition(condition);
//                 vr.setDiscount(discount);
//                 SaxParserDataStore.vrs.put(id, vr);
//                 return true;
//             case "pettracker":
//                 Pettracker petTracker = new Pettracker();
//                 petTracker.setId(id);
//                 petTracker.setName(name);
//                 petTracker.setPrice(price);
//                 petTracker.setImage(image);
//                 //petTracker.setRetailer(retailer);
//                 petTracker.setCondition(condition);
//                 petTracker.setDiscount(discount);
//                 SaxParserDataStore.pettrackers.put(id, petTracker);
//                 return true;
//             case "headphone":
//                 Headphones headphone = new Headphones();
//                 headphone.setId(id);
//                 headphone.setName(name);
//                 headphone.setPrice(price);
//                 headphone.setImage(image);
//                 //headphone.setRetailer(retailer);
//                 headphone.setCondition(condition);
//                 headphone.setDiscount(discount);
//                 SaxParserDataStore.headphoness.put(id, headphone);
//                 return true;
//             case "phone":
//                 Phone phone = new Phone();
//                 phone.setId(id);
//                 phone.setName(name);
//                 phone.setPrice(price);
//                 phone.setImage(image);
//                 //phone.setRetailer(retailer);
//                 phone.setCondition(condition);
//                 phone.setDiscount(discount);
//                 SaxParserDataStore.phones.put(id, phone);
//                 return true;
//             case "laptop":
//                 Laptop laptop = new Laptop();
//                 laptop.setId(id);
//                 laptop.setName(name);
//                 laptop.setPrice(price);
//                 laptop.setImage(image);
//                 //laptop.setRetailer(retailer);
//                 laptop.setCondition(condition);
//                 laptop.setDiscount(discount);
//                 SaxParserDataStore.laptops.put(id, laptop);
//                 return true;
//             case "speaker":
//                 Speaker speaker = new Speaker();
//                 speaker.setId(id);
//                 speaker.setName(name);
//                 speaker.setPrice(price);
//                 speaker.setImage(image);
//               //  speaker.setRetailer(retailer);
//                 speaker.setCondition(condition);
//                 speaker.setDiscount(discount);
//                 SaxParserDataStore.speakers.put(id, speaker);
//                 return true;
//
//         }
//         return false;
//     }
//
//     public boolean removeProduct(String productId, String catalog) {
//         switch (catalog) {
//             case "fitness":
//                 SaxParserDataStore.fitnesss.remove(productId);
//                 return true;
//             case "watch":
//
//                 SaxParserDataStore.smartwatchs.remove(productId);
//                 return true;
//             case "vr":
//
//                 SaxParserDataStore.vrs.remove(productId);
//                 return true;
//             case "pettracker":
//
//                 SaxParserDataStore.pettrackers.remove(productId);
//                 return true;
//             case "headphone":
//
//                 SaxParserDataStore.headphoness.remove(productId);
//                 return true;
//             case "phone":
//
//                 SaxParserDataStore.phones.remove(productId);
//                 return true;
//             case "laptop":
//
//                 SaxParserDataStore.laptops.remove(productId);
//                 return true;
//             case "speaker":
//
//                 SaxParserDataStore.speakers.remove(productId);
//                 return true;
//
//         }
//         return false;
//     }
//
//     public boolean updateProduct(String id, String name, String price, String manufacturer, String condition, String discount, String image, String catalog) {
//         switch (catalog) {
//             case "fitness":
//                 Fitness fitness = new Fitness();
//                 fitness.setId(id);
//                 fitness.setName(name);
//                 fitness.setPrice(Double.parseDouble(price));
//               //  fitness.setRetailer(manufacturer);
//                 fitness.setCondition(condition);
//                 fitness.setDiscount(Double.parseDouble(discount));
//                 fitness.setImage(image);
//                 SaxParserDataStore.fitnesss.remove(id);
//                 SaxParserDataStore.fitnesss.put(id, fitness);
//
//                 return true;
//             case "watch":
//
//                 Smartwatch smartwatch = new Smartwatch();
//                 smartwatch.setId(id);
//                 smartwatch.setName(name);
//                 smartwatch.setPrice(Double.parseDouble(price));
//               //  smartwatch.setRetailer(manufacturer);
//                 smartwatch.setCondition(condition);
//                 smartwatch.setDiscount(Double.parseDouble(discount));
//                 smartwatch.setImage(image);
//                 SaxParserDataStore.smartwatchs.remove(id);
//                 SaxParserDataStore.smartwatchs.put(id, smartwatch);
//                 return true;
//             case "vr":
//
//                 VR vr = new VR();
//                 vr.setId(id);
//                 vr.setName(name);
//                 vr.setPrice(Double.parseDouble(price));
//               //  vr.setRetailer(manufacturer);
//                 vr.setCondition(condition);
//                 vr.setDiscount(Double.parseDouble(discount));
//                 vr.setImage(image);
//                 SaxParserDataStore.vrs.remove(id);
//                 SaxParserDataStore.vrs.put(id, vr);
//                 return true;
//             case "pettracker":
//
//                 Pettracker petTracker = new Pettracker();
//                 petTracker.setId(id);
//                 petTracker.setName(name);
//                 petTracker.setPrice(Double.parseDouble(price));
//               //  petTracker.setRetailer(manufacturer);
//                 petTracker.setCondition(condition);
//                 petTracker.setDiscount(Double.parseDouble(discount));
//                 petTracker.setImage(image);
//                 SaxParserDataStore.pettrackers.remove(id);
//                 SaxParserDataStore.pettrackers.put(id, petTracker);
//                 return true;
//             case "headphone":
//
//                 Headphones headphone = new Headphones();
//                 headphone.setId(id);
//                 headphone.setName(name);
//                 headphone.setPrice(Double.parseDouble(price));
//               //  headphone.setRetailer(manufacturer);
//                 headphone.setCondition(condition);
//                 headphone.setDiscount(Double.parseDouble(discount));
//                 headphone.setImage(image);
//                 SaxParserDataStore.headphoness.remove(id);
//                 SaxParserDataStore.headphoness.put(id, headphone);
//                 return true;
//             case "phone":
//
//                 Phone phone = new Phone();
//                 phone.setId(id);
//                 phone.setName(name);
//                 phone.setPrice(Double.parseDouble(price));
//               //  phone.setRetailer(manufacturer);
//                 phone.setCondition(condition);
//                 phone.setDiscount(Double.parseDouble(discount));
//                 phone.setImage(image);
//                 SaxParserDataStore.phones.remove(id);
//                 SaxParserDataStore.phones.put(id, phone);
//                 return true;
//             case "laptop":
//
//                 Laptop laptop = new Laptop();
//                 laptop.setId(id);
//                 laptop.setName(name);
//                 laptop.setPrice(Double.parseDouble(price));
//                 //laptop.setRetailer(manufacturer);
//                 laptop.setCondition(condition);
//                 laptop.setDiscount(Double.parseDouble(discount));
//                 laptop.setImage(image);
//                 SaxParserDataStore.laptops.remove(id);
//                 SaxParserDataStore.laptops.put(id, laptop);
//                 return true;
//             case "speaker":
//
//                 Speaker speaker = new Speaker();
//                 speaker.setId(id);
//                 speaker.setName(name);
//                 speaker.setPrice(Double.parseDouble(price));
//               //  speaker.setRetailer(manufacturer);
//                 speaker.setCondition(condition);
//                 speaker.setDiscount(Double.parseDouble(discount));
//                 speaker.setImage(image);
//                 SaxParserDataStore.speakers.remove(id);
//                 SaxParserDataStore.speakers.put(id, speaker);
//                 return true;
//
//         }
//         return false;
//     }
//
//
//     private void displayStoreManagerHome(HttpServletRequest request,
//                                          HttpServletResponse response, PrintWriter pw, String flag)
//     {
//
//         Utilities utility = new Utilities(request, pw);
//         utility.printHtml("Header.html");
//         utility.printHtml("LeftNavigationBar.html");
//
//         pw.print("<div id='content'>");
//         pw.print("<div class='post'>");
//         pw.print("<h3 class='title'>");
//         pw.print("Create New product");
//         pw.print("</h3>");
//         pw.print("<div class='entry'>");
//
//         if (flag.equals("newProduct"))
//             pw.print("<h4 style='color:red'>" + error_msg + "</h4>");
// //////////////////////////////////////
//             pw.print("<form action='CreateProduct' method='post'");
//             pw.print("<table class='gridtable'><tr><td>");
//
//             pw.print("Product ID:</td><td>");
//
//             pw.print("<input type='text' name='productId' value=''>");
//              pw.print("</td></tr><br>");
//             // pw.print("<input type='hidden' name='catalog' value='" + catalog + "'>");
//             // pw.print("<input type='hidden' name='image' value='" + image + "'>");
//
//             pw.print("<tr><td>Product Name</td><td><input type='text' name='productName' value='' class='input' required>");
//             pw.print("</td></tr>");
//
//             pw.print("<tr><td>Product Catalog</td><td><select id='catalog' name='productCatalog' class='input'>" +
//                   "<option value='fitness' name='fitness'selected>Fitness watch</option>" +
//                   "<option value='smartwatch' name='smartwatch'>Smart watch</option>" +
//                   "<option value='headphones' name='headphones'>Headphone</option>" +
//                   "<option value='vr' name='vr'>Virtual reality</option>" +
//                   "<option value='pettracker' name='pettracker'>Pet tracker</option>" +
//                   "<option value='phone' name='phone'>Phone</option>" +
//                   "<option value='laptop' name'laptop'>Laptop</option>" +
//                   "<option value='speaker' name='speaker'>Speaker</option>" +
//                   "<option value='speaker' name='accessory'>Accessory</option>"
//                   );
//             pw.print("</select></td></tr>");
//
//
//             pw.print("<tr><td>Price</td><td><input type='text' name='price' value='' class='input' required>");
//             pw.print("</td></tr>");
//             //pw.print("<h4>Manufacturer</h4></td><td><input type='text' name='manufacturer' value='" + manufacturer + "' class='input' required></input>");
//             //pw.print("</td></tr><tr><td>");
//
//             pw.print("<tr><td>Condition</td><td><select name='condition' class='input'>" +
//                   "<option value='New' selected>New</option>" +
//                   "<option value='Used'>Used</option>" +
//                   "<option value='Refurbished'>Refurbished</option></select>");
//             pw.print("</td></tr>");
//
//             pw.print("<tr><td>Image</td><td><img id=\"preview\" /><br/><input type='file' name='image' class='input' required>");
//             pw.print("</td></tr><tr><td>");
//
//             pw.print("Discount</td><td><input type='text' name='discount' value='' class='input' required>");
//             pw.print("</td></tr>");
//
//             pw.print("<tr><td><input type='submit' class='btnbuy' value='Create' style='float: left;height: 20px margin: 20px; margin-right: 10px;'>");
//             pw.print("</td></tr><tr><td></td><tr>");
//             pw.print("</table>");
//             pw.print("</form></div></div></div>");
//
//
//         ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//
//
//         pw.print("<div class='post'>");
//         // pw.print("<form method='post' action='RemoveUpdateProduct'>");
//         pw.print("<h2 class='title meta'>");
//         pw.print("<a style='font-size: 24px;'>View Products</a></h2>");
//         pw.print("<div class='entry'>");
//         pw.print("<table class='gridtable'>");
//
//         if (flag.equals("RemoveUpdateProduct"))
//             pw.print("<h4 style='color:red'>" + error_msg + "</h4>");
//
//
//
// //        pw.print("<div align='left' style='float:left'>");
// //        pw.print("<input type='submit' name='Product' value='Update Product' class='btnbuy'>");
// //        pw.print("</div>");
// //        pw.print("<div align='right'>");
// //        pw.print("<input type='submit' name='Product' value='Remove Product' class='btnbuy'>");
// //        pw.print("</div>");
// //        pw.print("<br>");
//
//
//
//         pw.print("<tr>");
//         pw.print("<td>Product Name</td>");
//         pw.print("<td>Price</td>");
//       //  pw.print("<td>Manufacturer</td>");
//         pw.print("<td>Condition</td>");
//         pw.print("<td>Discount</td>");
//         pw.print("<td>Catalog</td>");
//         pw.print("</tr>");
//
//
//         for (Map.Entry<String, Fitness> entry : SaxParserDataStore.fitnesss.entrySet()) {
//             Fitness fitness = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
// //            pw.print("<td><input type='radio' name='productId' value='" + fitness.getId() + "'></td>");
//
//             pw.print("<td>" + fitness.getName() + "</td>" +
//                     "<td>" + fitness.getPrice() + "</td>" +
//                     // "<td>" + fitness.getRetailer() + "</td>" +
//                     "<td>" + fitness.getCondition() + "</td>" +
//                     "<td>" + fitness.getDiscount() + "</td>" +
//                     "<td>Fitness Watch</td>");
//
//             pw.print("<input type='hidden' name='productId' value='" + fitness.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + fitness.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + fitness.getPrice() + "'>");
//             // pw.print("<input type='hidden' name='manufacturer' value='" + fitness.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + fitness.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + fitness.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='fitness'>");
//             pw.print("<input type='hidden' name='image' value='" + fitness.getImage() + "'>");
//             pw.print("</tr>");
//
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//             //pw.print("<br>");
//         }
//
//
//         for (Map.Entry<String, Smartwatch> entry : SaxParserDataStore.smartwatchs.entrySet()) {
//             Smartwatch smartwatch = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
//             //pw.print("<td><input type='radio' name='productId' value='" + smartwatch.getId() + "'></td>");
//
//             pw.print("<td>" + smartwatch.getName() + "</td>" +
//                     "<td>" + smartwatch.getPrice() + "</td>" +
//                     //"<td>" + smartwatch.getRetailer() + "</td>" +
//                     "<td>" + smartwatch.getCondition() + "</td>" +
//                     "<td>" + smartwatch.getDiscount() + "</td>" +
//                     "<td>Smart Watch</td>");
//             pw.print("<input type='hidden' name='productId' value='" + smartwatch.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + smartwatch.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + smartwatch.getPrice() + "'>");
//         //    pw.print("<input type='hidden' name='manufacturer' value='" + smartwatch.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + smartwatch.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + smartwatch.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='smartwatch'>");
//             pw.print("<input type='hidden' name='image' value='" + smartwatch.getImage() + "'>");
//             pw.print("</tr>");
//
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//         }
//
//
//         for (Map.Entry<String, Headphones> entry : SaxParserDataStore.headphoness.entrySet()) {
//             Headphones headphone = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
//             //pw.print("<td><input type='radio' name='productId' value='" + headphone.getId() + "'></td>");
//
//             pw.print("<td>" + headphone.getName() + "</td>" +
//                     "<td>" + headphone.getPrice() + "</td>" +
//                 //     "<td>" + headphone.getRetailer() + "</td>" +
//                     "<td>" + headphone.getCondition() + "</td>" +
//                     "<td>" + headphone.getDiscount() + "</td>" +
//                     "<td>Headphones</td>");
//             pw.print("<input type='hidden' name='productId' value='" + headphone.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + headphone.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + headphone.getPrice() + "'>");
//           //  pw.print("<input type='hidden' name='manufacturer' value='" + headphone.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + headphone.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + headphone.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='headphones'>");
//             pw.print("<input type='hidden' name='image' value='" + headphone.getImage() + "'>");
//             pw.print("</tr>");
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//         }
//
//
//         for (Map.Entry<String, VR> entry : SaxParserDataStore.vrs.entrySet()) {
//             VR vr = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
//             // pw.print("<td><input type='radio' name='productId' value='" + vr.getId() + "'></td>");
//             pw.print("<td>" + vr.getName() + "</td>" +
//                     "<td>" + vr.getPrice() + "</td>" +
//                   //  "<td>" + vr.getRetailer() + "</td>" +
//                     "<td>" + vr.getCondition() + "</td>" +
//                     "<td>" + vr.getDiscount() + "</td>" +
//                     "<td>vr</td>");
//             pw.print("<input type='hidden' name='productId' value='" + vr.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + vr.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + vr.getPrice() + "'>");
//           //  pw.print("<input type='hidden' name='manufacturer' value='" + vr.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + vr.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + vr.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='vr'>");
//             pw.print("<input type='hidden' name='image' value='" + vr.getImage() + "'>");
//             pw.print("</tr>");
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//         }
//
//          //Pet Tracker
//         for (Map.Entry<String, Pettracker> entry : SaxParserDataStore.pettrackers.entrySet()) {
//             Pettracker pettracker = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
//             // pw.print("<td><input type='radio' name='productId' value='" + petTracker.getId() + "'></td>");
//
//             pw.print("<td>" + pettracker.getName() + "</td>" +
//                     "<td>" + pettracker.getPrice() + "</td>" +
//               //      "<td>" + petTracker.getRetailer() + "</td>" +
//                     "<td>" + pettracker.getCondition() + "</td>" +
//                     "<td>" + pettracker.getDiscount() + "</td>" +
//                     "<td>Pet Tracker</td>");
//             pw.print("<input type='hidden' name='productId' value='" + pettracker.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + pettracker.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + pettracker.getPrice() + "'>");
//           //  pw.print("<input type='hidden' name='manufacturer' value='" + petTracker.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + pettracker.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + pettracker.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='pettracker'>");
//             pw.print("<input type='hidden' name='image' value='" + pettracker.getImage() + "'>");
//             pw.print("</tr>");
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//         }
//
//          //Phone
//         for (Map.Entry<String, Phone> entry : SaxParserDataStore.phones.entrySet()) {
//             Phone phone = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
//             // pw.print("<td><input type='radio' name='productId' value='" + phone.getId() + "'></td>");
//
//             pw.print("<td>" + phone.getName() + "</td>" +
//                     "<td>" + phone.getPrice() + "</td>" +
//                   //  "<td>" + phone.getRetailer() + "</td>" +
//                     "<td>" + phone.getCondition() + "</td>" +
//                     "<td>" + phone.getDiscount() + "</td>" +
//                     "<td>Phone</td>");
//             pw.print("<input type='hidden' name='productId' value='" + phone.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + phone.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + phone.getPrice() + "'>");
//           //  pw.print("<input type='hidden' name='manufacturer' value='" + phone.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + phone.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + phone.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='phone'>");
//             pw.print("<input type='hidden' name='image' value='" + phone.getImage() + "'>");
//             pw.print("</tr>");
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//         }
//
//          //Laptop
//         for (Map.Entry<String, Laptop> entry : SaxParserDataStore.laptops.entrySet()) {
//             Laptop laptop = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
//             //pw.print("<td><input type='radio' name='productId' value='" + laptop.getId() + "'></td>");
//
//             pw.print("<td>" + laptop.getName() + "</td>" +
//                     "<td>" + laptop.getPrice() + "</td>" +
//                   //  "<td>" + laptop.getRetailer() + "</td>" +
//                     "<td>" + laptop.getCondition() + "</td>" +
//                     "<td>" + laptop.getDiscount() + "</td>" +
//                     "<td>Laptop</td>");
//             pw.print("<input type='hidden' name='productId' value='" + laptop.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + laptop.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + laptop.getPrice() + "'>");
//             // pw.print("<input type='hidden' name='manufacturer' value='" + laptop.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + laptop.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + laptop.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='laptop'>");
//             pw.print("<input type='hidden' name='image' value='" + laptop.getImage() + "'>");
//             pw.print("</tr>");
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//         }
//
//          //Voice Assistant
//         for (Map.Entry<String, Speaker> entry : SaxParserDataStore.speakers.entrySet()) {
//             Speaker Speaker = entry.getValue();
//             pw.print("<form method='post' action='RemoveUpdateProduct'>");
//             pw.print("<tr>");
//             // pw.print("<td><input type='radio' name='productId' value='" + Speaker.getId() + "'></td>");
//
//             pw.print("<td>" + Speaker.getName() + "</td>" +
//                     "<td>" + Speaker.getPrice() + "</td>" +
//                   //  "<td>" + Speaker.getRetailer() + "</td>" +
//                     "<td>" + Speaker.getCondition() + "</td>" +
//                     "<td>" + Speaker.getDiscount() + "</td>" +
//                     "<td>Speakers</td>");
//             pw.print("<input type='hidden' name='productId' value='" + Speaker.getId() + "'>");
//             pw.print("<input type='hidden' name='productName' value='" + Speaker.getName() + "'>");
//             pw.print("<input type='hidden' name='price' value='" + Speaker.getPrice() + "'>");
//             //pw.print("<input type='hidden' name='manufacturer' value='" + Speaker.getRetailer() + "'>");
//             pw.print("<input type='hidden' name='condition' value='" + Speaker.getCondition() + "'>");
//             pw.print("<input type='hidden' name='discount' value='" + Speaker.getDiscount() + "'>");
//             pw.print("<input type='hidden' name='productCatalog' value='speaker'>");
//             pw.print("<input type='hidden' name='image' value='" + Speaker.getImage() + "'>");
//             pw.print("</tr>");
//             pw.print("<tr>");
//             pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//             pw.print("<td>");
//             pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//             pw.print("</tr>");
//             pw.print("</form>");
//         }
//
//           //Accessory
//           for (Map.Entry<String, Accessory> entry : SaxParserDataStore.accessorys.entrySet()) {
//               Accessory accessory = entry.getValue();
//               pw.print("<form method='post' action='RemoveUpdateProduct'>");
//               pw.print("<tr>");
//               // pw.print("<td><input type='radio' name='productId' value='" + accessory.getId() + "'></td>");
//
//               pw.print("<td>" + accessory.getName() + "</td>" +
//                       "<td>" + accessory.getPrice() + "</td>" +
//                     //  "<td>" + accessory.getRetailer() + "</td>" +
//                       "<td>" + accessory.getCondition() + "</td>" +
//                       "<td>" + accessory.getDiscount() + "</td>" +
//                       "<td>Accessory</td>");
//               pw.print("<input type='hidden' name='productId' value='" + accessory.getId() + "'>");
//               pw.print("<input type='hidden' name='productName' value='" + accessory.getName() + "'>");
//               pw.print("<input type='hidden' name='price' value='" + accessory.getPrice() + "'>");
//               //pw.print("<input type='hidden' name='manufacturer' value='" + accessory.getRetailer() + "'>");
//               pw.print("<input type='hidden' name='condition' value='" + accessory.getCondition() + "'>");
//               pw.print("<input type='hidden' name='discount' value='" + accessory.getDiscount() + "'>");
//               pw.print("<input type='hidden' name='productCatalog' value='accessory'>");
//               pw.print("<input type='hidden' name='image' value='" + accessory.getImage() + "'>");
//               pw.print("</tr>");
//               pw.print("<tr>");
//               pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
//               pw.print("<td>");
//               pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//               pw.print("</tr>");
//               pw.print("</form>");
//           }
//         // for (Map.Entry<String, Accessory> entry : SaxParserDataStore.accessories.entrySet()) {
//         //     Accessory accessory = entry.getValue();
//         //     pw.print("<form method='post' action='RemoveUpdateProduct'>");
//         //     if (accessory.getName() == null || accessory.getName().isEmpty()) {
//         //         continue;
//         //     }
//         //     pw.print("<tr>");
//         //     // pw.print("<td><input type='radio' name='productId' value='" + accessory.getId() + "'></td>");
//         //
//         //     pw.print("<td>" + accessory.getName() + "</td>" +
//         //             "<td>" + accessory.getPrice() + "</td>" +
//         //             "<td>" + accessory.getRetailer() + "</td>" +
//         //             "<td>" + accessory.getCondition() + "</td>" +
//         //             "<td>" + accessory.getDiscount() + "</td>" +
//         //             "<td>Accessory</td>");
//         //     pw.print("<input type='hidden' name='productId' value='" + accessory.getId() + "'>");
//         //     pw.print("<input type='hidden' name='productName' value='" + accessory.getName() + "'>");
//         //     pw.print("<input type='hidden' name='price' value='" + accessory.getPrice() + "'>");
//         //     pw.print("<input type='hidden' name='manufacturer' value='" + accessory.getRetailer() + "'>");
//         //     pw.print("<input type='hidden' name='condition' value='" + accessory.getCondition() + "'>");
//         //     pw.print("<input type='hidden' name='discount' value='" + accessory.getDiscount() + "'>");
//         //     pw.print("<input type='hidden' name='catalog' value='Accessory'>");
//         //     pw.print("<input type='hidden' name='image' value='" + accessory.getImage() + "'>");
//         //     pw.print("</tr>");
//         //     pw.print("<tr>");
//         //     //pw.print("<td></td>");
//         //     pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div>");
//         //     pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
//         //     pw.print("</tr>");
//         //     pw.print("</form>");
//         // }
//
//         pw.print("</table>");
//         pw.print("</div></div></div>");
//         utility.printHtml("Footer.html");
//
//     }
// }
