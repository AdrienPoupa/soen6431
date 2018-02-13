package com.horstmann.violet.application.menu;

import java.util.Collection;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.classes.ClassDiagramGraph;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.editorpart.IEditorPart;

/**
 * This class contains menu items for constraints to be applied class diagram
 * 1. Enable/disable bidirectional relationship constraint for aggregation and composition relationship
 * 2. Check bidirectional aggregation/composition relationships exists in class diagram 
 * 3. Check recursive aggregation/composition relationships exist in class diagram
 */
@ResourceBundleBean(resourceReference = MenuFactory.class)
public class ConstraintsMenu extends JMenu {

	/**
	 * Default constructor
	 *
	 * @param mainFrame
	 */
	@ResourceBundleBean(key = "constraints")
	public ConstraintsMenu(MainFrame mainFrame) {
		ResourceBundleInjector.getInjector().inject(this);
		BeanInjector.getInjector().inject(this);
		this.mainFrame = mainFrame;
		createMenu();
	}

	/**
	 * Initialize the menu
	 */
	private void createMenu() {
		initCheckBidirectionalRelationMenu();
		initEnableRecursiveRelation();
		initEnableBidirectionalRelationCheckbox();
		initCboMenuItem();
		this.add(this.checkBidirectionalRelation);
		this.add(this.enableRecursiveRelationconstraint);
		this.add(this.bidirectionalRelationCheckBox);
		this.add(this.cboMenuItem);

	}
	
	private void initCboMenuItem() {
		cboMenuItem.setSelected(true);
		cboMenuItem.addItemListener(e -> {

			if (mainFrame.getWorkspaceList().size() == 0)
				return;

			IWorkspace activeWorkspace = mainFrame.getActiveWorkspace();
			IEditorPart activeEditor = activeWorkspace.getEditorPart();
			IGraph activeGraph = activeEditor.getGraph();
			Collection<INode> nodes = activeGraph.getAllNodes();

			if (cboMenuItem.isSelected()) {
				for (INode node : nodes) {
					node.enableCbo();
				}
			}
			if (!cboMenuItem.isSelected()) {
				for (INode node : nodes) {
					node.disableCbo();
				}
			}

			activeEditor.getSwingComponent().repaint();
		});
	}

	/**
	 * Init menu item for enabling bidirectional relationships
	 */
	private void initEnableBidirectionalRelationCheckbox() {
		this.bidirectionalRelationCheckBox.setSelected(true);
		this.bidirectionalRelationCheckBox.addItemListener(e -> {
			IWorkspace workspace = mainFrame.getActiveWorkspace();
			if (workspace != null && workspace.getGraphFile() != null
					&& workspace.getGraphFile().getGraph() != null) {
				IGraph graph = workspace.getGraphFile().getGraph();
				if(bidirectionalRelationCheckBox.isSelected()){
					graph.setBirectionalRelationConstraint(true);
				}
				else{
					graph.setBirectionalRelationConstraint(false);
				}
			}

		});
		
	}

	/**
	 * Init menu item for checking bidirectional relationship constraint
	 */
	private void initCheckBidirectionalRelationMenu() {
		this.checkBidirectionalRelation.addActionListener(e -> {
			IWorkspace workspace = mainFrame.getActiveWorkspace();
			if (workspace != null && workspace.getGraphFile() != null
					&& workspace.getGraphFile().getGraph() != null) {
				IGraph graph = workspace.getGraphFile().getGraph();
				if (graph instanceof ClassDiagramGraph) {
					ClassDiagramGraph classGraph = (ClassDiagramGraph) graph;
					if (classGraph.isBidirectionalRelationExist()){
						dialogFactory.showWarningDialog(bidirectionalFound);
					}
					else {
						dialogFactory.showInformationDialog(bidirectionalNotFound);
					}
				}
			}
		});
	}
	/**
	 * Init enable recursive relationship constraint
	 */
	private void initEnableRecursiveRelation() {
		this.enableRecursiveRelationconstraint.addActionListener(e -> {
			boolean recursionFoundInLoop = false;
			IWorkspace workspace = mainFrame.getActiveWorkspace();
			if (workspace != null && workspace.getGraphFile() != null
					&& workspace.getGraphFile().getGraph() != null) {
				Collection<IEdge> edgesList = workspace.getGraphFile().getGraph().getAllEdges();
				for (IEdge edge : edgesList) {
					if (edge.getStartNode() == edge.getEndNode() && !recursionFoundInLoop) {
						recursionFoundInLoop = true;
						dialogFactory.showWarningDialog(recursionFound);
					}
				}
				if (!recursionFoundInLoop) {
					dialogFactory.showInformationDialog(recursionNotFound);
				}
			}
		});
	}

	/**
	 * Application main frame
	 */
	private MainFrame mainFrame;

	@ResourceBundleBean(key = "constraints.bidirectional.check")
	private JMenuItem checkBidirectionalRelation;

	@ResourceBundleBean(key = "constraints.recursive")
	private JMenuItem enableRecursiveRelationconstraint;
	
	@ResourceBundleBean(key = "constraints.bidirectional.enable")
	private JCheckBoxMenuItem bidirectionalRelationCheckBox;

	@ResourceBundleBean(key = "constraints.cbo")
	private JCheckBoxMenuItem cboMenuItem;

	@ResourceBundleBean(key = "constraints.recursion.found")
	private String recursionFound;

	@ResourceBundleBean(key = "constraints.recursion.notfound")
	private String recursionNotFound;

	@ResourceBundleBean(key = "constraints.bidirectional.found")
	private String bidirectionalFound;

	@ResourceBundleBean(key = "constraints.bidirectional.notfound")
	private String bidirectionalNotFound;

	/**
	 * DialogBox handler
	 */
	@InjectedBean
	private DialogFactory dialogFactory;
	
  
}
