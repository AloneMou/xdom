package com.bom.core.utils.xdom;

import com.bom.core.utils.xdom.dom.XElements;
import com.bom.core.utils.xdom.dom.XPathEvaluator;
import com.bom.core.utils.xdom.dom.XTokenQueue;
import com.bom.core.utils.xdom.dom.Xsoup;
import com.bom.core.utils.xdom.w3c.DocumentAdaptor;
import com.bom.core.utils.xdom.xevaluator.XPathParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;

public class XDomAdaptorHandler {

    private static Logger logger = LoggerFactory.getLogger(XDomAdaptorHandler.class);

    private static XDomAdaptorHandler xDomAdaptorHandler = null;

    public static XDomAdaptorHandler getInstance() {
        if (xDomAdaptorHandler != null) {
            return xDomAdaptorHandler;
        }
        synchronized (XDomAdaptorHandler.class) {
            if (xDomAdaptorHandler == null) {
                xDomAdaptorHandler = new XDomAdaptorHandler();
            }
        }
        return xDomAdaptorHandler;
    }

    public Object parseDocumentAdaptor(String expression, String content) throws Exception {
        Document document = new DocumentAdaptor(Jsoup.parse(content));
        XPathExpression xPathExpression = XPathFactory.newInstance().newXPath().compile(expression);
        return xPathExpression.evaluate(document);
    }

    public Object parseStringValue(String expression, String content) throws XPathExpressionException {
        Document document = Xsoup.convertDocument(Jsoup.parse(content));
        XPathExpression xPathExpression = newXPathExpression(expression);
        return xPathExpression.evaluate(document);
    }

    public Object parseNodeValue(String expression, String content) throws XPathExpressionException {
        Document document = Xsoup.convertDocument(Jsoup.parse(content));
        XPathExpression xPathExpression = newXPathExpression(expression);
        Object evaluate = xPathExpression.evaluate(document, XPathConstants.NODE);
        if (evaluate == null) {
            return null;
        }
        Node node = (Node) evaluate;
        return node.getNodeValue();
    }

    public List<String> parseNodeListValue(String expression, String content) throws XPathExpressionException {
        Document document = Xsoup.convertDocument(Jsoup.parse(content));
        XPathExpression xPathExpression = newXPathExpression(expression);
        Object evaluate = xPathExpression.evaluate(document, XPathConstants.NODESET);
        if (evaluate == null) {
            return null;
        }
        NodeList nodeList = (NodeList) evaluate;
        List<String> nodeStrings = new ArrayList<String>(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            nodeStrings.add(nodeList.item(i).getNodeValue());
        }
        return nodeStrings;
    }

    private XPathExpression newXPathExpression(String expression) throws XPathExpressionException {
        XPathExpression xPathExpression;
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath target = xPathfactory.newXPath();
        xPathExpression = target.compile(expression);
        return xPathExpression;
    }

    public Object compileNodeValue (String expression, String content) {
        org.jsoup.nodes.Document document = Jsoup.parse(content);
        return Xsoup.compile(expression).evaluate(document).get();
    }

    public List<String> compileNodeListValue (String expression, String content) {
        org.jsoup.nodes.Document document = Jsoup.parse(content);
        XPathEvaluator xPathEvaluator = Xsoup.compile(expression);
        return xPathEvaluator.evaluate(document).list();
    }
    private String html = "<html><body><div id='test'>aaa<div><a href=\"https://github.com\">github.com</a></div></div></body></html>";

    private String htmlClass = "<html><body><div class='a b c'><div><a href=\"https://github.com\">github.com</a></div></div><div>b</div></body></html>";

    public Object selectNode(String expression, String content) {
        org.jsoup.nodes.Document document = Jsoup.parse(content);
        return Xsoup.select(document, expression).get();
    }

    public Object selectNodeAttribute(String expression, String content) {
        org.jsoup.nodes.Document document = Jsoup.parse(content);
        XPathEvaluator xPathEvaluator = XPathParser.parse(expression);
        XElements select = xPathEvaluator.evaluate(document);
        return select.get();
    }

    public Object selectNodeElementAttribute(String expression, String tagName, String content) {
        Element element = Jsoup.parse(content).getElementsByTag(tagName).get(0);
        return Xsoup.select(element, expression).get();
    }

    public static void main(String[] args) throws Exception {
        String content1 = "<html><body><div id='test'>aaa<div><a href=\"https://github.com\">github.com</a></div></div></body></html>";
        String content2 = "<html><div><a href='https://github.com'>github.com</a></div><table><tr><td>a</td><td>b</td></tr></table></html>";
        String content3 = "<html><body><div class='a b c'><div><a href=\"https://github.com\">github.com</a></div></div><div>b</div></body></html>";
        String content4 = "<html><body><div id='test'>aaa<div><a href=\"https://github.com\">github.com</a></div></div></body></html>";
        String content5 = "<html><div id='test2'>aa<a href='https://github.com'>github.com</a></div>";
        String content6 = "<div><svg>1</svg><svg>2</svg></div>";
        String content7 = "<a href='https://github.com'>github.com</a>";
        String content8 = "<html><body><div id='test'>aaa<div><a href=\"https://github.com\">github.com</a></div></div></body></html>";
        String content9 = "<html><body><div class='a b c'><div><a href=\"https://github.com\">github.com</a></div></div><div>b</div></body></html>";
        String content10 = "<div><svg>1</svg><svg>2</svg></div>";
        String content11 = "<html><div id='test2'>aa<a href='https://github.com'>github.com</a></div>";
        String content12 = "<html><div id='test2'>/list/12345<a href='https://github.com'>github.com</a></div>";
        String content13 = "<html><div><a href='https://github.com'>github.com</a></div><table><tr><td>a</td><td>b</td></tr></table></html>";
        String content14 = "<html><div><a href='https://github.com'>github.com</a></div><table><tr><td>a</td><td>b</td></tr></table></html>";

        System.out.println(XDomAdaptorHandler.getInstance().parseDocumentAdaptor("//div/a/@href", content1));
        
        System.out.println(XDomAdaptorHandler.getInstance().parseStringValue("//div/a/@href", content2));
        System.out.println(XDomAdaptorHandler.getInstance().parseStringValue("//a/@href", content3));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//a[@href]", content4));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//a[@id]", content4));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div[@id='test']", content4));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div[@id=\"test\"]", content4));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//body/div[1]", content3));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//body/div[2]", content3));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div/svg[1]", content6));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div/svg[2]", content6));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//*[@id='te' or @id='test']", content4));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//*[@id='te' and @id='test']", content4));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//*[@id='te' and @id='test']", content4));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//*[(@id='te' or @id='test') and @id='test']", content4));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//*[@id='te' or (@id='test' and @id='id')]", content4));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div[contains(@id,'te')]", content4));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div[@id='test'] | //div[@id='test2']", content4));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div[@id='test'] | //div[@id='test2']", content5));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeValue("//div[@id='test'] | //div[@id='test2']", content4 + content5));

        System.out.println(XDomAdaptorHandler.getInstance().parseNodeListValue("//div[@class='a b c']", content3).get(0));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeListValue("//div[@class='b']", content3));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeListValue("//div[@class='d']", content3));
        
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeListValue("//tr/td", content2).get(0));
        System.out.println(XDomAdaptorHandler.getInstance().parseNodeListValue("//tr/td", content2).get(1));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("/html/body/div/div/a", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("/html//div/div/a", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("/html/div/div/a", content8));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//a[@id]", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@id=test]", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@id='test']", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@id=\"test\"]", content8));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@class=a]", content9));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@class=d]", content9));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//body/div[1]", content9));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//body/div[2]", content9));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div/svg[1]/text()", content10));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div/svg[2]/text()", content10));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//a/@href", content9));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//a/text()", content9));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@class=a]/html()", content9));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@href]/@href", content9));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@class=a]/html()", content9));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@*]/html()", content9));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id~=te]/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id$=st]/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id*=es]/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id~='tes[t]+']/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id~=te]/allText()", content8));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id=te or @id=test]/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id=test or @id=te]/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id=te and @id=test]/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[(@id=te or @id=test) and @id=test]/text()", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//*[@id=te or (@id=test and @id=id)]/text()", content8));

        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@id='test']/text() | //div[@id='test2']/text()", content11));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@id='test']/text() | //div[@id='test2']/text()", content8 + content11));
        System.out.println(XDomAdaptorHandler.getInstance().selectNode("//div[@id='test2']/regex(\"/list/(\\d+)\",1)", content12));

        System.out.println(XDomAdaptorHandler.getInstance().selectNodeAttribute("//*[@id~=te]/regex('gi\\w+ub')", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNodeAttribute("//a/regex('@href','.*gi\\w+ub.*')", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNodeAttribute("//a/regex('@href','.*(gi\\w+ub).*',1", content8));

        System.out.println(XDomAdaptorHandler.getInstance().selectNodeAttribute("//div[contains(@id,'te')]", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNodeAttribute("//div[@id='test'] | //div[@id='test2']", content8));

        System.out.println(XDomAdaptorHandler.getInstance().selectNodeElementAttribute("@href", "a", content7));

        System.out.println(XDomAdaptorHandler.getInstance().compileNodeValue("//a/@href", content13));
        System.out.println(XDomAdaptorHandler.getInstance().compileNodeValue("//tr/td/text()", content14));

        System.out.println(XDomAdaptorHandler.getInstance().selectNodeAttribute("//a[@href]", content8));
        System.out.println(XDomAdaptorHandler.getInstance().selectNodeAttribute("//div[@id='test']/text() | //div[@id='test2']/text()", content8));

        List<String> list = XTokenQueue.parseFuncionParams("a,b,c");
        list = XTokenQueue.parseFuncionParams("'a,b',c");
        list = XTokenQueue.parseFuncionParams("'a,\\'b',c");
        list = XTokenQueue.parseFuncionParams("@a,1,c");
        System.out.println(list);

        XTokenQueue xTokenQueue = new XTokenQueue("\"aaaaa\"");
        String chomp = xTokenQueue.chompBalancedQuotes();
        System.out.println(chomp);

        xTokenQueue = new XTokenQueue("\"aaaaa\"aabb");
        chomp = xTokenQueue.chompBalancedQuotes();
        System.out.println(chomp);

        xTokenQueue = new XTokenQueue("a\"aaaaa\"aabb");
        chomp = xTokenQueue.chompBalancedQuotes();
        System.out.println(chomp);

        XTokenQueue xTokenQueue2 = new XTokenQueue("(\")\")");
        String chomp2 = xTokenQueue2.chompBalancedNotInQuotes('(',')');
        System.out.println(chomp);

        xTokenQueue2 = new XTokenQueue("(\"')\")");
        chomp2 = xTokenQueue2.chompBalancedNotInQuotes('(',')');
        System.out.println(chomp);

        xTokenQueue2 = new XTokenQueue("(''')')");
        chomp2 = xTokenQueue2.chompBalancedNotInQuotes('(',')');
        System.out.println(chomp);
    }
}
