package cn.fdsd.bmk.ast.visitor;

import cn.fdsd.bmk.ast.Node;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 14:22
 */
public abstract class NodeAdaptingVisitHandler<N extends Node, A extends NodeAdaptingVisitor<N>> implements Visitor {
    protected final Class<? extends N> myClass;
    protected final A myAdapter;

    public NodeAdaptingVisitHandler(Class<? extends N> aClass, A adapter) {
        myClass = aClass;
        myAdapter = adapter;
    }

    public Class<? extends N> getNodeType() {
        return myClass;
    }

    public A getNodeAdapter() {
        return myAdapter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeAdaptingVisitHandler<?, ?> other = (NodeAdaptingVisitHandler<?, ?>) o;

        if (myClass != other.myClass) return false;
        return myAdapter == other.myAdapter;
    }

    @Override
    public int hashCode() {
        int result = myClass.hashCode();
        result = 31 * result + myAdapter.hashCode();
        return result;
    }
}
