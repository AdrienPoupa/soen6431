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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.injection.bean.ManiocFramework;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.userpreferences.Options;
import com.horstmann.violet.framework.userpreferences.UserPreferencesService;


/**
 * Edit menu
 *
 * @author SOEN
 *
 */
@ResourceBundleBean(resourceReference = MenuFactory.class)
public class OptionsMenu extends JMenu
{

    /**
     * Default constructor
     *
     * @param mainFrame where is attached this menu
     */
    @ResourceBundleBean(key = "options")
    public OptionsMenu(final MainFrame mainFrame)
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
        disableBiDirection.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                if(disableBiDirection.getText().toLowerCase().contains("disable")){
                    Options.allowBidirectional = false;
                    disableBiDirection.setText("Enable Bi-Directional Aggregation and Composition");
                }
                else
                {
                    Options.allowBidirectional = true;
                    disableBiDirection.setText("Disable Bi-Directional Aggregation and Composition");
                }

            }
        });
        this.add(disableBiDirection);

        disableRecursion.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                if(disableRecursion.getText().toLowerCase().contains("disable")){
                    Options.allowRecursive = false;
                    disableRecursion.setText("Enable Recursion in Aggregation and Composition");
                }
                else
                {
                    Options.allowBidirectional = true;
                    disableRecursion.setText("Disable Recursion in Aggregation and Composition");
                }

            }
        });
        this.add(disableRecursion);
    }

    /** Application frame */
    private MainFrame mainFrame;

    @ResourceBundleBean(key = "options.enable.recursion")
    private JMenuItem disableRecursion;

    @ResourceBundleBean(key = "options.enable.bidirection")
    private JMenuItem disableBiDirection;
}
