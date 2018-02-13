package com.horstmann.violet.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;

import javax.swing.JCheckBoxMenuItem;
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
import com.horstmann.violet.product.diagram.classes.ClassDiagramGraph;
import com.horstmann.violet.workspace.IWorkspace;

/**
 * This class contains menu items for contraints to be applied class diagram
 * 1. Enable/ disable bidirectional relationship contraint for aggregation and composition relationship
 * 2. Check bidirectional aggregation/composition relationships exists in class diagram 
 * 3. Check recursive aggregation/composition relationshhips exist in class diagram
 * @author amanp
 *
 */
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
		initCheckBidirectionalRelationMenu();
		initEnableRecursiveRelation();
		initEnableBidirectionalRelationCheckbox();
		this.add(this.checkBidirectionalRelation);
		this.add(this.enableRecursiveRelationContraint);
		this.add(this.bidirectionalRelationCheckBox);

	}

	private void initEnableBidirectionalRelationCheckbox() {
		this.bidirectionalRelationCheckBox.setSelected(true);
		this.bidirectionalRelationCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
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
				
			}
		});
		
	}

	/**
	 * Init menu item for checking bidirectional relationship constraint
	 */
	private void initCheckBidirectionalRelationMenu() {
		this.checkBidirectionalRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IWorkspace workspace = mainFrame.getActiveWorkspace();
				if (workspace != null && workspace.getGraphFile() != null
						&& workspace.getGraphFile().getGraph() != null) {
					IGraph graph = workspace.getGraphFile().getGraph();
					if(graph instanceof ClassDiagramGraph){
						ClassDiagramGraph classGraph = (ClassDiagramGraph) graph;
						if(classGraph.isBidirectionalRelationExist()){
							dialogFactory.showErrorDialog("Bidirectional aggregation/composition relationship exists");
						}
						else{
							dialogFactory.showInformationDialog("No Bidirectional aggregation/composition relationship exists");
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
				boolean rflag = false;
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

								if (edge.getStartNode() == edge.getEndNode() && rflag != true) {
									rflag = true;
									dialogFactory.showErrorDialog("Recussion");

								}
							}


						}
					}

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
	private JMenuItem enableRecursiveRelationContraint;
	
	@ResourceBundleBean(key = "constraints.bidirectional.enable")
	private JCheckBoxMenuItem bidirectionalRelationCheckBox;

	/**
	 * DialogBox handler
	 */
	@InjectedBean
	private DialogFactory dialogFactory;
	
  
}
