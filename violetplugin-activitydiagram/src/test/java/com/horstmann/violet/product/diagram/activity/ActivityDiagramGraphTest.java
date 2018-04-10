package com.horstmann.violet.product.diagram.activity;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.horstmann.violet.product.diagram.activity.node.ActivityNode;

public class ActivityDiagramGraphTest {

	@Test
	public void testisBidirectionalRelationAllowed() {
		ActivityDiagramGraph graph= new ActivityDiagramGraph();
		Assert.assertTrue(graph.isBidirectionalRelationAllowed(new ActivityNode(), new ActivityNode()));
	}
	
	@Test
	public void testisRecursiveRelationAllowed() {
		ActivityDiagramGraph graph= new ActivityDiagramGraph();
		Assert.assertTrue(graph.isRecursiveRelationAllowed(new ActivityNode(), new ActivityNode()));
	}

}
