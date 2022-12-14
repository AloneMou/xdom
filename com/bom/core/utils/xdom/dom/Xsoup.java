package com.bom.core.utils.xdom.dom;

import com.bom.core.utils.xdom.w3c.NodeAdaptors;
import com.bom.core.utils.xdom.xevaluator.XPathParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Xsoup {

    /*-------------     XEvaluator         --------------- */

    public static XElements select(Element element, String xpathStr) {
        return XPathParser.parse(xpathStr).evaluate(element);
    }

    public static XElements select(String html, String xpathStr) {
        return XPathParser.parse(xpathStr).evaluate(Jsoup.parse(html));
    }

    public static XPathEvaluator compile(String xpathStr) {
        return XPathParser.parse(xpathStr);
    }

    /*-------------     W3cAdaptor         --------------- */

    public static org.w3c.dom.Element convertElement(Element element) {
        return (org.w3c.dom.Element) NodeAdaptors.getElement(element);
    }

    public static org.w3c.dom.Document convertDocument(Document document) {
        return NodeAdaptors.getDocument(document);
    }

}
