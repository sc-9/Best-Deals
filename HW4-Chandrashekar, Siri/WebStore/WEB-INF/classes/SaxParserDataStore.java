
/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API.
SAX was the first widely adopted API for XML in Java, and is a �de facto� standard.
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java.

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import  java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure.
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    Laptop laptop;
    Phone phone;
    Speaker speaker;
    VR vr;
    Smartwatch smartwatch;
    Fitness fitness;
    Pettracker pettracker;
    Headphones headphones;
    Accessory accessory;
    static HashMap<String,Laptop> laptops;
    static HashMap<String,Phone> phones;
    static HashMap<String,Speaker> speakers;
    static HashMap<String,VR> vrs;
    static HashMap<String,Pettracker> pettrackers;
    static HashMap<String,Fitness> fitnesss;
    static HashMap<String,Headphones> headphoness;
    static HashMap<String,Smartwatch> smartwatchs;
    static HashMap<String,Accessory> accessorys;


    String consoleXmlFileName;
//	HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
    this.consoleXmlFileName = consoleXmlFileName;
    laptops = new HashMap<String, Laptop>();
	phones=new  HashMap<String, Phone>();
	speakers=new HashMap<String, Speaker>();
	fitnesss=new HashMap<String, Fitness>();
  smartwatchs = new HashMap<String,Smartwatch>();
  headphoness = new HashMap<String,Headphones>();
  pettrackers = new HashMap<String,Pettracker>();
  vrs = new HashMap<String,VR>();
  accessorys = new HashMap<String,Accessory>();
	//accessoryHashMap=new HashMap<String,String>();
	parseDocument();

    }

    // String TOMCAT_HOME = System.getProperty("catalina.home");
    // String xml = "E:\\IIT\\Semester 3\\Enterprise Web Application\\csj\\apache-tomcat-7.0.34\\webapps\\WebStore\\WEB-INF\\product.xml";
     // try {
     //    File inputFile = new File(xml);
     //    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
     //    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
     //    Document doc = dBuilder.parse(inputFile);
     //    doc.getDocumentElement().normalize();
     //    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
     //    NodeList nList = doc.getElementsByTagName("laptop");
     //    System.out.println("----------------------------");
     //
     //    for (int temp = 0; temp < nList.getLength(); temp++) {
     //       Node nNode = nList.item(temp);
     //       System.out.println("\nCurrent Element :" + nNode.getNodeName());
     //
     //       if (nNode.getNodeType() == Node.ELEMENT_NODE) {
     //          Element eElement = (Element) nNode;
     //          System.out.println("Student roll no : "
     //             + eElement.getAttribute("id"));
     //          System.out.println("First Name : "
     //             + eElement
     //             .getElementsByTagName("name")
     //             .item(0)
     //             .getTextContent());
     //          System.out.println("Last Name : "
     //             + eElement
     //             .getElementsByTagName("price")
     //             .item(0)
     //             .getTextContent());
     //          System.out.println("Nick Name : "
     //             + eElement
     //             .getElementsByTagName("discount")
     //             .item(0)
     //             .getTextContent());
     //          System.out.println("Marks : "
     //             + eElement
     //             .getElementsByTagName("marks")
     //             .item(0)
     //             .getTextContent());
     //       }
     //    }
     // } catch (Exception e) {
     //    e.printStackTrace();
     // }

   //parse the xml using sax parser to get the data
    private void parseDocument()
	{
    //     SAXParserFactory factory = SAXParserFactory.newInstance();
    //     try
		// {
	  //   SAXParser parser = factory.newSAXParser();
	  //   parser.parse(consoleXmlFileName, this);
    //     } catch (ParserConfigurationException e) {
    //         System.out.println("ParserConfig error");
    //     } catch (SAXException e) {
    //         System.out.println("SAXException : xml not well formed");
    //     } catch (IOException e) {
    //         System.out.println("IO error");
    //     }
    try {
       File inputFile = new File(consoleXmlFileName);
       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       Document doc = dBuilder.parse(inputFile);
       doc.getDocumentElement().normalize();
       System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
       NodeList nList_laptop = doc.getElementsByTagName("laptop");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_laptop.getLength(); temp++) {
          Node nNode = nList_laptop.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             laptop = new Laptop();
             laptop.setId(eElement.getAttribute("id"));
             laptop.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             laptop.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             laptop.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             laptop.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             laptop.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          laptops.put(laptop.getId(),laptop);
       }

       //phones

       NodeList nList_phone = doc.getElementsByTagName("phone");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_phone.getLength(); temp++) {
          Node nNode = nList_phone.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             phone = new Phone();
             phone.setId(eElement.getAttribute("id"));
             phone.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             phone.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             phone.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             phone.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             phone.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          phones.put(phone.getId(),phone);
       }

       //speakers
       NodeList nList_speaker = doc.getElementsByTagName("speaker");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_speaker.getLength(); temp++) {
          Node nNode = nList_speaker.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             speaker = new Speaker();
             speaker.setId(eElement.getAttribute("id"));
             speaker.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             speaker.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             speaker.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             speaker.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             speaker.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          speakers.put(speaker.getId(),speaker);
       }

       //VR
       NodeList nList_vr = doc.getElementsByTagName("vr");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_vr.getLength(); temp++) {
          Node nNode = nList_vr.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             vr = new VR();
             vr.setId(eElement.getAttribute("id"));
             vr.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             vr.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             vr.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             vr.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             vr.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          vrs.put(vr.getId(),vr);
       }

       //HeadphonesList
       NodeList nList_headphones = doc.getElementsByTagName("headphone");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_headphones.getLength(); temp++) {
          Node nNode = nList_headphones.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             headphones = new Headphones();
             headphones.setId(eElement.getAttribute("id"));
             headphones.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             headphones.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             headphones.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             headphones.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             headphones.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          headphoness.put(headphones.getId(),headphones);
       }

       //Fintess
       NodeList nList_fitness = doc.getElementsByTagName("fitness");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_fitness.getLength(); temp++) {
          Node nNode = nList_fitness.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             fitness = new Fitness();
             fitness.setId(eElement.getAttribute("id"));
             fitness.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             fitness.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             fitness.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             fitness.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             fitness.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          fitnesss.put(fitness.getId(),fitness);
       }

       //SmartwatchList
       NodeList nList_smartwatch = doc.getElementsByTagName("watch");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_smartwatch.getLength(); temp++) {
          Node nNode = nList_smartwatch.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             smartwatch = new Smartwatch();
             smartwatch.setId(eElement.getAttribute("id"));
             smartwatch.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             smartwatch.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             smartwatch.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             smartwatch.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             smartwatch.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          smartwatchs.put(smartwatch.getId(),smartwatch);
       }

       //PettrackerList
       NodeList nList_pettracker = doc.getElementsByTagName("pettracker");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_pettracker.getLength(); temp++) {
          Node nNode = nList_pettracker.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             pettracker = new Pettracker();
             pettracker.setId(eElement.getAttribute("id"));
             pettracker.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             pettracker.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             pettracker.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             pettracker.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             pettracker.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          pettrackers.put(pettracker.getId(),pettracker);
       }
       //Accessory ListOrderPayment
       NodeList nList_accessory = doc.getElementsByTagName("Accessory");
       System.out.println("----------------------------");

       for (int temp = 0; temp < nList_accessory.getLength(); temp++) {
          Node nNode = nList_accessory.item(temp);
          System.out.println("\nCurrent Element :" + nNode.getNodeName());

          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
             Element eElement = (Element) nNode;
             accessory = new Accessory();
             accessory.setId(eElement.getAttribute("id"));
             accessory.setImage(eElement.getElementsByTagName("image").item(0).getTextContent());
             accessory.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
             accessory.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
             accessory.setDiscount(Double.parseDouble(eElement.getElementsByTagName("discount").item(0).getTextContent()));
             accessory.setCondition(eElement.getElementsByTagName("condition").item(0).getTextContent());
          }
          accessorys.put(accessory.getId(),accessory);
       }



    } catch (Exception e) {
       e.printStackTrace();
    }



	}



////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document.
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////

	// when xml start element is parsed store the id into respective hashmap for console,games etc
  //   @Override
  //   public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {
  //
  //   if (elementName.equalsIgnoreCase("laptop"))
	// 	{
	// 		currentElement="laptop";
	// 		laptop = new Laptop();
  //     laptop.setId(attributes.getValue("id"));
  //     laptop.setImage(attributes.getValue("image"));
  //     laptop.setName(attributes.getValue("name"));
  //     laptop.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     laptop.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     laptop.setCondition(attributes.getValue("condition"));
	// 	}
  //
  //   if (elementName.equalsIgnoreCase("phone"))
  //   {
  //     currentElement="phone";
  //     phone = new Phone();
  //     phone.setId(attributes.getValue("id"));
  //     phone.setImage(attributes.getValue("image"));
  //     phone.setName(attributes.getValue("name"));
  //     phone.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     phone.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     phone.setCondition(attributes.getValue("condition"));
  //   }
  //
  //   if (elementName.equalsIgnoreCase("vr"))
  //   {
  //     currentElement="vr";
  //     vr = new VR();
  //     vr.setId(attributes.getValue("id"));
  //     vr.setImage(attributes.getValue("image"));
  //     vr.setName(attributes.getValue("name"));
  //     vr.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     vr.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     vr.setCondition(attributes.getValue("condition"));
  //   }
  //   if (elementName.equalsIgnoreCase("speaker"))
  //   {
  //     currentElement="speaker";
  //     speaker = new Speaker();
  //     speaker.setId(attributes.getValue("id"));
  //     speaker.setImage(attributes.getValue("image"));
  //     speaker.setName(attributes.getValue("name"));
  //     speaker.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     speaker.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     speaker.setCondition(attributes.getValue("condition"));
  //   }
  //
  //   if (elementName.equalsIgnoreCase("smartwatch"))
  //   {
  //     currentElement="smartwatch";
  //     smartwatch = new Smartwatch();
  //     smartwatch.setId(attributes.getValue("id"));
  //     smartwatch.setImage(attributes.getValue("image"));
  //     smartwatch.setName(attributes.getValue("name"));
  //     smartwatch.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     smartwatch.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     smartwatch.setCondition(attributes.getValue("condition"));
  //   }
  //   if (elementName.equalsIgnoreCase("fitness"))
  //   {
  //     currentElement="fitness";
  //     fitness = new Fitness();
  //     fitness.setId(attributes.getValue("id"));
  //     fitness.setImage(attributes.getValue("image"));
  //     fitness.setName(attributes.getValue("name"));
  //     fitness.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     fitness.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     fitness.setCondition(attributes.getValue("condition"));
  //   }
  //   if (elementName.equalsIgnoreCase("headphones"))
  //   {
  //     currentElement="headphones";
  //     headphones = new Headphones();
  //     headphones.setId(attributes.getValue("id"));
  //     headphones.setImage(attributes.getValue("image"));
  //     headphones.setName(attributes.getValue("name"));
  //     headphones.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     headphones.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     headphones.setCondition(attributes.getValue("condition"));
  //   }
  //
  //   if (elementName.equalsIgnoreCase("pettracker"))
  //   {
  //     currentElement="pettracker";
  //     pettracker = new Pettracker();
  //     pettracker.setId(attributes.getValue("id"));
  //     pettracker.setImage(attributes.getValue("image"));
  //     pettracker.setName(attributes.getValue("name"));
  //     pettracker.setPrice(Double.parseDouble(attributes.getValue("price")));
  //     pettracker.setDiscount(Double.parseDouble(attributes.getValue("discount")));
  //     pettracker.setCondition(attributes.getValue("condition"));
  //   }
  //   //     if (elementName.equalsIgnoreCase("tablet"))
	// 	// {
	// 	// 	currentElement="tablet";
	// 	// 	tablet = new Tablet();
  //   //         tablet.setId(attributes.getValue("id"));
  //   //     }
  //   //     if (elementName.equalsIgnoreCase("game"))
	// 	// {
	// 	// 	currentElement="game";
	// 	// 	game= new Game();
  //   //         game.setId(attributes.getValue("id"));
  //   //     }
  //   //     if (elementName.equals("accessory") &&  !currentElement.equals("console"))
	// 	// {
	// 	// 	currentElement="accessory";
	// 	// 	accessory=new Accessory();
	// 	// 	accessory.setId(attributes.getValue("id"));
	//   //   }
  //   //
  //
  //   }
	// // when xml end element is parsed store the data into respective hashmap for console,games etc respectively
  //   @Override
  //   public void endElement(String str1, String str2, String element) throws SAXException {
  //
  //       if (element.equals("laptop")) {
	// 		laptops.put(laptop.getId(),laptop);
	// 		return;
  //       }
  //
  //       if (element.equals("phone")) {
	// 		phones.put(phone.getId(),phone);
	// 		return;
  //       }
  //       if (element.equals("speaker")) {
	// 		speakers.put(speaker.getId(),speaker);
	// 		return;
  //       }
  //       if (element.equals("vr")) {
	// 		vrs.put(vr.getId(),vr);
	// 		return;
  //       }
  //       if (element.equals("smartwatch")) {
	// 		smartwatchs.put(smartwatch.getId(),smartwatch);
	// 		return;
  //       }
  //       if (element.equals("fitness")) {
	// 		fitnesss.put(fitness.getId(),fitness);
	// 		return;
  //       }
  //       if (element.equals("headphones")) {
	// 		headphoness.put(headphones.getId(),headphones);
	// 		return;
  //       }
  //       if (element.equals("pettracker")) {
	// 		pettrackers.put(pettracker.getId(),pettracker);
	// 		return;
  //       }
  //     //   if (element.equals("accessory") && currentElement.equals("accessory")) {
	// 		// accessories.put(accessory.getId(),accessory);
	// 		// return;
  //     //   }
	// 	// if (element.equals("accessory") && currentElement.equals("console"))
	// 	// {
	// 		accessoryHashMap.put(elementValueRead,elementValueRead);
	// //	}
  //   //   	if (element.equalsIgnoreCase("accessories") && currentElement.equals("console")) {
	// 	// 	console.setAccessories(accessoryHashMap);
	// 	 	accessoryHashMap=new HashMap<String,String>();
	// 	// 	return;
	// 	// }
  //   //System.out.println("element"+elementValueRead);
  //       if (element.equalsIgnoreCase("image")) {
	// 	    if(currentElement.equals("laptop"))
	// 			laptop.setImage(elementValueRead);
  //       	if(currentElement.equals("phone"))
	// 			phone.setImage(elementValueRead);
  //           if(currentElement.equals("speaker"))
	// 			speaker.setImage(elementValueRead);
  //           if(currentElement.equals("vr"))
	// 			vr.setImage(elementValueRead);
  //       if(currentElement.equals("headphones"))
  //         headphones.setImage(elementValueRead);
  //       if(currentElement.equals("pettracker"))
  //       pettracker.setImage(elementValueRead);
  //       if(currentElement.equals("smartwatch"))
  //       smartwatch.setImage(elementValueRead);
  //       if(currentElement.equals("fitness"))
  //       fitness.setImage(elementValueRead);
	// 		 return;
  //       }
  //   //
  //   //
	// 	if (element.equalsIgnoreCase("discount")) {
  //           if(currentElement.equals("laptop"))
  //           laptop.setDiscount(Double.parseDouble(elementValueRead));
  //             if(currentElement.equals("phone"))
  //           phone.setDiscount(Double.parseDouble(elementValueRead));
  //               if(currentElement.equals("speaker"))
  //           speaker.setDiscount(Double.parseDouble(elementValueRead));
  //               if(currentElement.equals("vr"))
  //           vr.setDiscount(Double.parseDouble(elementValueRead));
  //           if(currentElement.equals("headphones"))
  //             headphones.setDiscount(Double.parseDouble(elementValueRead));
  //           if(currentElement.equals("pettracker"))
  //           pettracker.setDiscount(Double.parseDouble(elementValueRead));
  //           if(currentElement.equals("smartwatch"))
  //           smartwatch.setDiscount(Double.parseDouble(elementValueRead));
  //           if(currentElement.equals("fitness"))
  //           fitness.setDiscount(Double.parseDouble(elementValueRead));
	// 		return;
	//     }
  //   //
  //   //
	// 	if (element.equalsIgnoreCase("condition")) {
  //     if(currentElement.equals("laptop"))
  //     laptop.setCondition(elementValueRead);
  //       if(currentElement.equals("phone"))
  //     phone.setCondition(elementValueRead);
  //         if(currentElement.equals("speaker"))
  //     speaker.setCondition(elementValueRead);
  //         if(currentElement.equals("vr"))
  //     vr.setCondition(elementValueRead);
  //     if(currentElement.equals("headphones"))
  //       headphones.setCondition(elementValueRead);
  //     if(currentElement.equals("pettracker"))
  //     pettracker.setCondition(elementValueRead);
  //     if(currentElement.equals("smartwatch"))
  //     smartwatch.setCondition(elementValueRead);
  //     if(currentElement.equals("fitness"))
  //     fitness.setCondition(elementValueRead);
	// 		return;
	// 	}
  //   //
	// 	// if (element.equalsIgnoreCase("manufacturer")) {
  //   //         if(currentElement.equals("console"))
	// 	// 		console.setRetailer(elementValueRead);
  //   //     	if(currentElement.equals("game"))
	// 	// 		game.setRetailer(elementValueRead);
  //   //         if(currentElement.equals("tablet"))
	// 	// 		tablet.setRetailer(elementValueRead);
  //   //         if(currentElement.equals("accessory"))
	// 	// 		accessory.setRetailer(elementValueRead);
	// 	// 	return;
	// 	// }
  //   //
  //       if (element.equalsIgnoreCase("name")) {
  //         if(currentElement.equals("laptop"))
  //         laptop.setName(elementValueRead);
  //           if(currentElement.equals("phone"))
  //         phone.setName(elementValueRead);
  //             if(currentElement.equals("speaker"))
  //         speaker.setName(elementValueRead);
  //             if(currentElement.equals("vr"))
  //         vr.setName(elementValueRead);
  //         if(currentElement.equals("headphones"))
  //           headphones.setName(elementValueRead);
  //         if(currentElement.equals("pettracker"))
  //         pettracker.setName(elementValueRead);
  //         if(currentElement.equals("smartwatch"))
  //         smartwatch.setName(elementValueRead);
  //         if(currentElement.equals("fitness"))
  //         fitness.setName(elementValueRead);
	// 		return;
	//     }
  //   //
  //       if(element.equalsIgnoreCase("price")){
  //         if(currentElement.equals("laptop"))
  //         laptop.setPrice(Double.parseDouble(elementValueRead));
  //           if(currentElement.equals("phone"))
  //         phone.setPrice(Double.parseDouble(elementValueRead));
  //             if(currentElement.equals("speaker"))
  //         speaker.setPrice(Double.parseDouble(elementValueRead));
  //             if(currentElement.equals("vr"))
  //         vr.setPrice(Double.parseDouble(elementValueRead));
  //         if(currentElement.equals("headphones"))
  //           headphones.setPrice(Double.parseDouble(elementValueRead));
  //         if(currentElement.equals("pettracker"))
  //         pettracker.setPrice(Double.parseDouble(elementValueRead));
  //         if(currentElement.equals("smartwatch"))
  //         smartwatch.setPrice(Double.parseDouble(elementValueRead));
  //         if(currentElement.equals("fitness"))
  //         fitness.setPrice(Double.parseDouble(elementValueRead));
	// 		return;
  //       }
  //
	// }
	// //get each element in xml tag
  //   @Override
  //   public void characters(char[] content, int begin, int end) throws SAXException {
  //       elementValueRead = new String(content, begin, end);
  //   }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////

//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\WebStore\\ProductCatalog.xml");
    }
}
