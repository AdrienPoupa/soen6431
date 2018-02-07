package com.horstmann.violet.product.diagram.classes.edge;

import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.userpreferences.Options;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.classes.ClassDiagramConstant;
import com.horstmann.violet.product.diagram.property.ArrowheadChoiceList;
import com.horstmann.violet.product.diagram.property.LineStyleChoiceList;
import com.horstmann.violet.product.diagram.common.edge.LabeledLineEdge;

import javax.swing.*;
import java.awt.geom.Point2D;

/**
 * TODO javadoc
 * This ...
 *
 * @author Adrian Bobrowski <adrian071993@gmail.com>
 * @date 23.02.2016
 */
public class CompositionEdge extends LabeledLineEdge
{
    public CompositionEdge()
    {
        super();
        setStartArrowhead(ArrowheadChoiceList.NONE);
        setEndArrowhead(ArrowheadChoiceList.DIAMOND_BLACK);
        setLineStyle(LineStyleChoiceList.SOLID);
    }

    protected CompositionEdge(CompositionEdge cloned)
    {
        super(cloned);
    }

    @Override
    protected void beforeReconstruction()
    {
        super.beforeReconstruction();
        setStartArrowhead(ArrowheadChoiceList.NONE);
        setEndArrowhead(ArrowheadChoiceList.DIAMOND_BLACK);
        setLineStyle(LineStyleChoiceList.SOLID);
    }

    @Override
    protected CompositionEdge copy() throws CloneNotSupportedException
    {
        return new CompositionEdge(this);
    }

    @Override
    public String getToolTip()
    {
        return ClassDiagramConstant.CLASS_DIAGRAM_RESOURCE.getString("tooltip.composition_edge");
    }

    @Override
    public boolean isOperationAllowed(IGraph graph, Point2D startPoint, Point2D endPoint)
    {
        INode startNode = graph.findNode(startPoint);
        INode endNode = graph.findNode(endPoint);
        for (IEdge e: graph.getAllEdges()) {
            if(e instanceof CompositionEdge){
                if(!Options.allowBidirectional) {
                    if (e.getEndNode() == startNode && e.getStartNode() == endNode) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
