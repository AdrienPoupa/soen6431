/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.application.menu;

import javax.swing.*;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.editorpart.IEditorPart;

import java.util.Collection;

/**
 * Help menu
 *
 * @author Alexandre de Pellegrin
 *
 */
@ResourceBundleBean(resourceReference = MenuFactory.class)
public class NewFeatures extends JMenu
{

    /**
     * Default constructor
     *
     * @param mainFrame where this menu is attached
     */
    @ResourceBundleBean(key = "newf")
    public NewFeatures(MainFrame mainFrame)
    {
        ResourceBundleInjector.getInjector().inject(this);
        this.mainFrame = mainFrame;
        this.createMenu();
    }

    /**
     * Initializes menu
     */
    private void createMenu()
    {
        // CBO item
        cboMenuItem.setSelected(true);
        cboMenuItem.addItemListener(e -> {

            if (mainFrame.getWorkspaceList().size() == 0) return;

            IWorkspace activeWorkspace = mainFrame.getActiveWorkspace();
            IEditorPart activeEditor = activeWorkspace.getEditorPart();
            IGraph activeGraph = activeEditor.getGraph();
            Collection<INode> nodes = activeGraph.getAllNodes();

            if (cboMenuItem.isSelected()) {
                for(INode node: nodes) {
                    node.enableCbo();
                }
            }
            if (!cboMenuItem.isSelected()) {
                for(INode node: nodes) {
                    node.disableCbo();
                }
            }

            activeEditor.getSwingComponent().repaint();
        });
        this.add(cboMenuItem);

//        // REC item
//        recMenuItem.setSelected(true);
//        recMenuItem.addItemListener(e -> {
//
//            if (mainFrame.getWorkspaceList().size() == 0) return;
//
//            IWorkspace activeWorkspace = mainFrame.getActiveWorkspace();
//            IEditorPart activeEditor = activeWorkspace.getEditorPart();
//            IGraph activeGraph = activeEditor.getGraph();
//            Collection<INode> nodes = activeGraph.getAllNodes();
//
//            if (recMenuItem.isSelected()) {
//                for(INode node: nodes) {
//                    node.enableRec();
//                }
//            }
//            if (!recMenuItem.isSelected()) {
//                for(INode node: nodes) {
//                    node.disableRec();
//                }
//            }
//
//            activeEditor.getSwingComponent().repaint();
//        });
//        this.add(recMenuItem);
    }


    /**
     * Main app frame where this menu is attached to
     */
    private MainFrame mainFrame;

    @ResourceBundleBean(key = "newf.cbo")
    private JCheckBoxMenuItem cboMenuItem;
//    private JCheckBoxMenuItem recMenuItem;
}
