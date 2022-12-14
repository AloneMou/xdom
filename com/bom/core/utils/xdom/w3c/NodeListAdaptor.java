package com.bom.core.utils.xdom.w3c;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class NodeListAdaptor implements NodeList {

    private List<? extends org.jsoup.nodes.Node> nodes;

    public NodeListAdaptor(List<? extends org.jsoup.nodes.Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public Node item(int index) {
        return NodeAdaptors.getNode(nodes.get(index));
    }

    @Override
    public int getLength() {
        return nodes.size();
    }
}
