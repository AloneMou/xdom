package com.bom.core.utils.xdom.xevaluator;

import com.bom.core.utils.xdom.dom.XElement;
import com.bom.core.utils.xdom.dom.XElements;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DefaultXElements extends ArrayList<XElement> implements XElements {

    private Elements elements;

    private ElementOperator elementOperator;

    public DefaultXElements(Elements elements, ElementOperator elementOperator) {
        this.elements = elements;
        this.elementOperator = elementOperator;
        initList();
    }

    private void initList() {
        for (Element element : elements) {
            this.add(new DefaultXElement(element, elementOperator));
        }
    }

    @Override
    public String get() {
        if (size() < 1) {
            return null;
        } else {
            return get(0).get();
        }
    }

    @Override
    public List<String> list() {
        List<String> resultStrings = new ArrayList<String>();
        for (XElement xElement : this) {
            String text = xElement.get();
            if (text != null) {
                resultStrings.add(text);
            }
        }
        return resultStrings;
    }

    @Override
    public String toString() {
        return get();
    }

    @Override
    public Elements getElements() {
        return elements;
    }

}
