package com.horstmann.violet.product.diagram.classes.node;

import com.horstmann.violet.framework.graphics.content.TextContent;
import com.horstmann.violet.framework.graphics.content.VerticalLayout;
import com.horstmann.violet.product.diagram.property.text.LineText;
import com.horstmann.violet.product.diagram.property.text.SingleLineText;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Rectangle2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the ClassNode class
 * @author VioletUML authors
 * @author Adrien Poupa
 */
public class ClassNodeTest {

    @Test
    public void testEmptyClassNodeBounds() {
        ClassNode node = new ClassNode();

        assertBounds(getEmptyClassNodeBounds(), node.getBounds());
    }

    @Test
    public void testClassNodeWithNameBounds() {
        ClassNode node = new ClassNode();
        node.setName(createMultiLineString("TestClass"));
        assertBounds(getClassNodeWithNameBounds(), node.getBounds());
    }

    @Test
    public void testFullClassNodeBounds() {
        ClassNode node = new ClassNode();
        node.setName(createMultiLineString("TestClass"));
        node.setAttributes(createMultiLineString("attributes"));
        node.setMethods(createMultiLineString("methods"));

        assertBounds(getFullClassNodeBounds(), node.getBounds());
    }

    @Test
    public void testClassNodeWithAttributesBounds() {
        ClassNode node = new ClassNode();
        node.setAttributes(createMultiLineString("attributes"));
        assertBounds(getClassNodeWithAttributesBounds(), node.getBounds());
    }

    @Test
    public void testClassNodeWithNameAndMethodsBounds() {
        ClassNode node = new ClassNode();
        node.setName(createMultiLineString("TestClass"));
        node.setMethods(createMultiLineString("methods"));

        assertBounds(getClassNodeWithNameAndMethodsBounds(), node.getBounds());
    }

    @Test
    public void testClassNodeWithMethodsBounds() {
        ClassNode node = new ClassNode();
        node.setMethods(createMultiLineString("methods"));

        assertBounds(getClassNodeWithMethodsBounds(), node.getBounds());
    }

    private Rectangle2D getClassNodeWithAttributesBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 152.0, 109.0);
    }

    private Rectangle2D getClassNodeWithNameBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 152.0, 90.0);
    }

    private Rectangle2D getClassNodeWithMethodsBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 152.0, 109.0);
    }

    private Rectangle2D getClassNodeWithNameAndMethodsBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 152.0, 109.0);
    }

    private Rectangle2D getFullClassNodeBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 152.0, 128.0);
    }

    private Rectangle2D getEmptyClassNodeBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 152.0, 90.0);
    }

    private void assertBounds(Rectangle2D expected, Rectangle2D actual) {
        assertEquals(expected.getX(), actual.getX(), 0.0);
        assertEquals(expected.getY(), actual.getY(), 0.0);
        assertEquals(expected.getHeight(), actual.getHeight(), 0.0);
        assertEquals(expected.getWidth(), actual.getWidth(), 0.0);
    }

    private SingleLineText createMultiLineString(String text) {
        SingleLineText result = new SingleLineText();
        result.setText(text);
        return result;
    }

    /**
     * Test the ClassNode constructor, make sure the default node is conform to what we expect
     */
    @Test
    public void testClassNodeConstructor() {
        ClassNode node = new ClassNode();
        assertEquals(LineText.CENTER, node.getCbo().getAlignment());
    }

    /**
     * Test the ClassNode copy constructor, make sure the new node is conform to what we expect
     */
    @Test
    public void testClassNodeCopyConstructor() {
        try {
            ClassNode node1 = new ClassNode();
            ClassNode node2 = new ClassNode(node1);
            assertEquals(node1.getCbo().getAlignment(), node2.getCbo().getAlignment());
            assertEquals(node1.getCbo().getBounds(), node2.getCbo().getBounds());
            assertEquals(node1.getCbo().getTextColor(), node2.getCbo().getTextColor());
            assertEquals(node1.getCbo().getClass(), node2.getCbo().getClass());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the createContentStructure function
     */
    @Test
    public void testCreateContentStructure() {
        ClassNode node = new ClassNode();
        node.createContentStructure();
        assertEquals(152, node.getContent().getWidth(), 0);
        assertEquals(90, node.getContent().getHeight(), 0);
        assertEquals(4, node.getVerticalGroupContent().getContents().size());
    }

    /**
     * Update CBO, make sure the output is CBO count: 0
     */
    @Test
    public void testUpdateCbo() {
        ClassNode node = new ClassNode();
        node.updateCbo();
        assertEquals("CBO count: 0", node.getCbo().toString());
    }

    /**
     * Test the enableCbo function, make sure the VerticalGroupContent array has been updated and contains cboContent
     */
    @Test
    public void testEnableCbo() {
        ClassNode node = new ClassNode();
        TextContent cboContent = new TextContent(node.getCbo());
        node.setCboContent(cboContent);
        node.enableCbo();
        assertTrue(node.getVerticalGroupContent().getContents().contains(cboContent));
    }

    /**
     * Test the disableCbo function, make sure the VerticalGroupContent does not contain the cboContent anymore
     */
    @Test
    public void testDisableCbo() {
        ClassNode node = new ClassNode();
        TextContent cboContent = new TextContent(node.getCbo());
        node.setCboContent(cboContent);
        node.disableCbo();
        assertFalse(node.getVerticalGroupContent().getContents().contains(cboContent));
    }

    /**
     * Test for getVerticalGroupContent
     */
    @Test
    public void testGetVerticalGroupContent() {
        ClassNode node = new ClassNode();
        node.createContentStructure();
        VerticalLayout verticalGroupContent = node.getVerticalGroupContent();
        node.setVerticalGroupContent(verticalGroupContent);
        assertEquals(verticalGroupContent, node.getVerticalGroupContent());
    }

    /**
     * Test for setVerticalGroupContent
     */
    @Test
    public void testSetVerticalGroupContent() {
        ClassNode node = new ClassNode();
        VerticalLayout verticalGroupContent = new VerticalLayout();
        node.setVerticalGroupContent(verticalGroupContent);
        assertEquals(verticalGroupContent, node.getVerticalGroupContent());
    }

    /**
     * Test for getCboContent
     */
    @Test
    public void testGetCboContent() {
        ClassNode node = new ClassNode();
        node.createContentStructure();
        TextContent cboContent = node.getCboContent();
        node.setCboContent(cboContent);
        assertEquals(cboContent, node.getCboContent());
    }

    /**
     * Test for setCboContent
     */
    @Test
    public void testSetCboContent() {
        ClassNode node = new ClassNode();
        node.createContentStructure();
        TextContent cboContent = new TextContent(new SingleLineText());
        node.setCboContent(cboContent);
        assertEquals(cboContent, node.getCboContent());
    }

    /**
     * Make sure cbo count is equals to 0 at instantiation
     */
    @Test
    public void testCboCounIs0AtInstantiation() {
        ClassNode classNode = new ClassNode();
        Assert.assertEquals(0, classNode.getCboCount());
    }

    /**
     * Make sure cbo count is equals to 0 at instantiation using copy constructor
     */
    @Test
    public void testCboCountIs0AtInstantiationFromNode() {
        try {
            ClassNode ClassNode1 = new ClassNode();
            ClassNode ClassNode2 = new ClassNode(ClassNode1);
            Assert.assertEquals(0, ClassNode2.getCboCount());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for incrementCboCount, make sure cboCount increased to 1
     */
    @Test
    public void testIncrementCboCount() {
        ClassNode classNode = new ClassNode();
        classNode.incrementCboCount();
        Assert.assertEquals(1, classNode.getCboCount());
    }

    /**
     * Test for decrementCboCount
     */
    @Test
    public void testDecrementCboCountIfCboCountGreaterThan1() {
        ClassNode classNode = new ClassNode();
        classNode.incrementCboCount(); // cbo count = 1
        classNode.incrementCboCount(); // cbo count = 2
        classNode.decrementCboCount(); // cbo count = 1
        Assert.assertEquals(1, classNode.getCboCount());
    }

    /**
     * Test for decrementCboCount when initial value is at 0
     */
    @Test
    public void testDecrementCboCountIfCboCountLessThan1() {
        ClassNode classNode = new ClassNode();
        classNode.decrementCboCount(); // cbo count = 0 and not -1
        Assert.assertEquals(0, classNode.getCboCount());
    }

    /**
     * Test for getCboCount
     */
    @Test
    public void testGetCboCount() {
        ClassNode classNode = new ClassNode();
        Assert.assertEquals(0, classNode.getCboCount());
    }

    /**
     * Test for setCboCount
     */
    @Test
    public void testSetCboCount() {
        ClassNode classNode = new ClassNode();
        classNode.setCboCount(2);
        Assert.assertEquals(2, classNode.getCboCount());
    }

    /**
     * Test for getCbo
     */
    @Test
    public void testGetCbo() {
        ClassNode classNode = new ClassNode();
        SingleLineText cbo = new SingleLineText();
        classNode.setCbo(cbo);
        Assert.assertEquals(cbo, classNode.getCbo());
    }

    /**
     * Test for setCbo
     */
    @Test
    public void testSetCbo() {
        ClassNode classNode = new ClassNode();
        SingleLineText cbo = new SingleLineText();
        classNode.setCbo(cbo);
        Assert.assertEquals(cbo, classNode.getCbo());
    }
}
