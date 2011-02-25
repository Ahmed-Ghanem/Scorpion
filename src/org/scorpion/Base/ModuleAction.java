/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scorpion.Base;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.util.actions.Presenter;

public final class ModuleAction implements Presenter.Toolbar, ActionListener {
    private Component panelToolbar = new JavaBeanToolbar();
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }

    @Override
    public Component getToolbarPresenter() {
        return panelToolbar;
    }
}
