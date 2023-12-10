package com.company;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
        // Что бы прочитать XML файл, нужно создать сборщик ( ◡‿◡ *)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        // Создадим будущие элементы
        Element bookstore = document.createElement("bookstore");

        Element book1  = document.createElement("book");
        Element title1   = document.createElement("title");
        Text text1 = document.createTextNode("Everyday Italian");

        Element book2 = document.createElement("book");
        Element title2   = document.createElement("title");
        Text text2 = document.createTextNode("Everyday Italian");

        Element book3  = document.createElement("book");
        Element title3   = document.createElement("title");
        Text text3 = document.createTextNode("Everyday Italian");

        // укажем их связи
        document.appendChild(bookstore);
        bookstore.appendChild(book1);
        bookstore.appendChild(book2);
        bookstore.appendChild(book3);
        book1.setAttribute("category", "COOKING");
        book2.setAttribute("category", "CHILDREN");
        book3.setAttribute("category", "WEB");
        book1.appendChild(title1);
        book2.appendChild(title2);
        book3.appendChild(title3);
        title1.appendChild(text1);
        title2.appendChild(text2);
        title3.appendChild(text3);

        // --------------- 1 вариант
        // трансформируем в XML файл (ﾉ´ヮ`)ﾉ*: ･ﾟ
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes"); // это что бы на разных строчках
        t.transform(new DOMSource(document), new StreamResult(new FileOutputStream("text.xml")));

        // --------------- 2 вариант
        DOMImplementation impl = document.getImplementation();
        DOMImplementationLS impLS = (DOMImplementationLS)impl.getFeature("LS", "3.0");
        // Формат для записи
        LSSerializer ser = impLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);

        // вывод в консоль строку
        //String str = ser.writeToString(document);
       // System.out.println(str);

        //запись в файл
        LSOutput out = impLS.createLSOutput();
        out.setEncoding("UTF-8");
        out.setByteStream(Files.newOutputStream(Paths.get("text2.xml")));
        ser.write(document, out);

    }
}
