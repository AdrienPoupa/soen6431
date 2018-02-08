package com.horstmann.violet.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.classes.edge.AggregationEdge;
import com.horstmann.violet.product.diagram.classes.edge.CompositionEdge;
import com.horstmann.violet.workspace.IWorkspace;

@ResourceBundleBean(resourceReference = MenuFactory.class)
public class NewContraintsMenu extends JMenu {

	/**
	 * Default constructor
	 *
	 * @param mainFrame
	 */
	@ResourceBundleBean(key = "constraints")
	public NewContraintsMenu(MainFrame mainFrame) {
		ResourceBundleInjector.getInjector().inject(this);
		BeanInjector.getInjector().inject(this);
		this.mainFrame = mainFrame;
		createMenu();
	}

	/**
	 * Initialize the menu
	 */
	private void createMenu() {
		initEnableBidirectionalRelation();
		initEnableRecursiveRelation();
		this.add(this.enableBidirectionalRelationContraint);
		this.add(this.enableRecursiveRelationContraint);

	}

	/**
	 * Init enable bidirectional relationship contraint
	 */
	private void initEnableBidirectionalRelation() {
		this.enableBidirectionalRelationContraint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean bflag = false;
				StringBuffer message = new StringBuffer();
				IWorkspace workspace = mainFrame.getActiveWorkspace();
				if (workspace != null) {
					IGraphFile graphFile = workspace.getGraphFile();
					if (graphFile != null) {
						IGraph graph = graphFile.getGraph();
						if (graph != null) {
							Collection<IEdge> edgesList = graph.getAllEdges();
							//Map<String, String> classPairNames = new HashMap<>();
							for (IEdge edge : edgesList) {
								if (edge.getStartNode() != edge.getEndNode() && bflag != true) {
									if (edge instanceof AggregationEdge || edge instanceof CompositionEdge) {
										INode startNode = edge.getStartNode();
										INode endNode = edge.getEndNode();
										checkClassPairExists(graph, startNode, endNode);
										bflag = true;
									}
								}
							}
						}

					}
				}

			}

		});
	}

	/**
	 * Init enable recursive relationship contraint
	 */
	private void initEnableRecursiveRelation() {
		this.enableRecursiveRelationContraint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean bflag = false;
				StringBuffer message = new StringBuffer();
				IWorkspace workspace = mainFrame.getActiveWorkspace();
				if (workspace != null) {
					IGraphFile graphFile = workspace.getGraphFile();
					if (graphFile != null) {
						IGraph graph = graphFile.getGraph();
						if (graph != null) {
							Collection<IEdge> edgesList = graph.getAllEdges();
							//Map<String, String> classPairNames = new HashMap<>();
							for (IEdge edge : edgesList) {

								if (edge.getStartNode() == edge.getEndNode()) {
									dialogFactory.showErrorDialog("Recussion");

								}
							}


						}
					}

				}
			}
		});
	}
	private void checkClassPairExists(IGraph graph, INode startNode, INode endNode) {
		for (IEdge ie : graph.getAllEdges()) {
			if (ie instanceof AggregationEdge || ie instanceof CompositionEdge) {
				INode sn = ie.getStartNode();
				INode en = ie.getEndNode();
				if (sn.getId().equals(endNode.getId())
						&& en.getId().equals(startNode.getId()))
				{
					dialogFactory.showErrorDialog("Bidirectional Aggregation/Composition Relationship Exists");
				}
			}
		}
	}
	
	/**
	 * Application main frame
	 */
	private MainFrame mainFrame;

	@ResourceBundleBean(key = "constraints.bidirectional")
	private JMenuItem enableBidirectionalRelationContraint;

	@ResourceBundleBean(key = "constraints.recursive")
	private JMenuItem enableRecursiveRelationContraint;

	/**
	 * DialogBox handler
	 */
	@InjectedBean
	private DialogFactory dialogFactory;

}
