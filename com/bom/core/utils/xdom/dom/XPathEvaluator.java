package com.bom.core.utils.xdom.dom;

import org.jsoup.nodes.Element;

public interface XPathEvaluator {

    XElements evaluate(Element element);

    boolean hasAttribute();

}