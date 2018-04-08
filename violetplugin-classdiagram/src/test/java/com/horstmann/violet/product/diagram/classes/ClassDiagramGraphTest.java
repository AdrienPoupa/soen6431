package com.horstmann.violet.product.diagram.classes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.classes.edge.AggregationEdge;
import com.horstmann.violet.product.diagram.classes.edge.AssociationEdge;
import com.horstmann.violet.product.diagram.classes.edge.CompositionEdge;
import com.horstmann.violet.product.diagram.classes.node.ClassNode;

public class ClassDiagramGraphTest {

	ClassNode startNode, endNode;
	IEdge edge1, edge2;
	ClassDiagramGraph classDiagram = new ClassDiagramGraph();

	@Before
	public void setUp() {
		startNode = new ClassNode();
		endNode = new ClassNode();
		edge1 = new AggregationEdge();
		edge2 = new AggregationEdge();
	}

	@Test
	public void testIsBidirectionalRelationAllowedFalse1() {
		classDiagram.connect(edge1, startNode, null, endNode, null, null);
		classDiagram.connect(edge2, endNode, null, startNode, null, null);
		Assert.assertFalse(classDiagram.isBidirectionalRelationAllowed(startNode, endNode));
	}
	
	@Test
	public void testIsBidirectionalRelationAllowedFalse2() {
		edge2 = new CompositionEdge();
		classDiagram.connect(edge1, startNode, null, endNode, null, null);
		classDiagram.connect(edge2, endNode, null, startNode, null, null);
		Assert.assertFalse(classDiagram.isBidirectionalRelationAllowed(startNode, endNode));
	}

	@Test
	public void test_isBidirectionalRelationAllowedTrue() {
		classDiagram.connect(edge1, startNode, null, endNode, null, null);
		Assert.assertTrue(classDiagram.isBidirectionalRelationAllowed(startNode, endNode));
	}
	
	@Test
	public void test_isBidirectionalRelationExistTrue() {
		classDiagram.connect(edge1, startNode, null, endNode, null, null);
		classDiagram.connect(edge2, endNode, null, startNode, null, null);
		Assert.assertTrue(classDiagram.isBidirectionalRelationExist());
	}
	
	@Test
	public void test_isBidirectionalRelationExistFalse1() {
		classDiagram.connect(edge1, startNode, null, endNode, null, null);
		Assert.assertFalse(classDiagram.isBidirectionalRelationExist());
	}
	
	@Test
	public void test_isBidirectionalRelationExistFalse2() {
		classDiagram.connect(edge1, startNode, null, startNode, null, null);
		Assert.assertFalse(classDiagram.isBidirectionalRelationExist());
	}
	
	@Test
	public void test_isBidirectionalRelationExistFalse3() {
		edge1 = new AssociationEdge();
		classDiagram.connect(edge1, startNode, null, endNode, null, null);
		Assert.assertFalse(classDiagram.isBidirectionalRelationExist());
	}

	@Test
	public void testConnectStartEqualsToEnd() {
		classDiagram.connect(edge2, startNode, null, startNode, null, null);
		Assert.assertEquals(0, startNode.getCboCount());
		Assert.assertEquals(0, endNode.getCboCount());
	}

	@Test
	public void testConnectStartNotEqualsToEnd() {
		classDiagram.connect(edge2, startNode, null, endNode, null, null);
		Assert.assertEquals(1, startNode.getCboCount());
		Assert.assertEquals(1, endNode.getCboCount());
	}

	@Test
	public void testRemoveStartEqualsToEnd() {
		classDiagram.connect(edge1, startNode, null, startNode, null, null);
		classDiagram.removeEdge(edge1);
		Assert.assertEquals(0, startNode.getCboCount());
		Assert.assertEquals(0, endNode.getCboCount());
	}

	@Test
	public void testRemoveStartNotEqualsToEnd() {
		classDiagram.connect(edge1, startNode, null, endNode, null, null);
		classDiagram.removeEdge(edge1);
		Assert.assertEquals(0, startNode.getCboCount());
		Assert.assertEquals(0, endNode.getCboCount());
	}
}
