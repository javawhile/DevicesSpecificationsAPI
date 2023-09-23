package org.devices.specifications.api;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	public static void main(String[] args) throws Exception  {
		Connection connect = Jsoup.connect("https://www.devicespecifications.com/en/model/70a23503");
		connect.userAgent("Mozilla/5.0");
		
		Document doc = connect.get();
        String title = doc.title();
        System.out.println("title is: " + title);
        
        Elements elementsByClass = doc.getElementsByClass("model-information-table");
        System.out.println(elementsByClass.size());
        elementsByClass.forEach( d -> System.out.println(d.toString()));
        
        Element elementModelInformation = elementsByClass.get(0);
        Elements elementsByTag = elementModelInformation.getElementsByTag("tr");
        System.out.println(elementsByTag.size());
        for(Element el: elementsByTag) {
        	System.out.println(el.getElementsByTag("td").get(1).toString());
        }
        
	}
	
}
