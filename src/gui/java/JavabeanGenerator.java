/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.java;

import database.DatabaseConnection;
import factory.NetworkFactory;
import java.io.File;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.scorpion.Base.JavaBeanToolbar;

/**
 *
 * @author Ahmed Ghanem
 */
public class JavabeanGenerator {

    private File beanFile;
    private PrintWriter beanFileWriter;
    private JFileChooser baenFilePath;
    private JComboBox databaseTables;
    private JLabel loadingLabel;
    private JTextArea javaTextArea;
    private DatabaseConnection connection;
    private Statement stmt;
    private ResultSet result;
    private Vector tablesName;
    private Vector tableAttributes;
    private Vector tableAttributesTypes;
    private NetworkFactory network;
    private int attibutesCount = 0;

    public JavabeanGenerator() {
        connection = new DatabaseConnection(JavaBeanToolbar.jTextField1.getText().trim());
        tableAttributes = new Vector();
        tableAttributesTypes = new Vector();

        try {
            stmt = connection.getConnection().createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void guiCompontsReference(JComboBox jcbx, JLabel jlbl, JTextArea jTxtArea) {
        databaseTables = jcbx;
        loadingLabel = jlbl;
        javaTextArea = jTxtArea;
    }

    public String setFirstLetterC(String element) {
        return element.substring(0, 1).toUpperCase() + element.substring(1).toLowerCase();
    }

    public void setTableName() {
        tablesName = new Vector();
        try {
            tablesName = connection.getTablesNames();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        databaseTables.setModel(new javax.swing.DefaultComboBoxModel(tablesName));

    }

    public void generateFinalJavaBean() {
        javaTextArea.setText(initClass());
        initAttributes();
        javaTextArea.append(initConstructor());
        initMethods();
        javaTextArea.append("\n}");
    }

    public String initClass() {
        String init = null;
        try {
            init = "/**\n*\n* @author " + InetAddress.getLocalHost().getHostName()
                    + "\n*/\n" + "class " + setFirstLetterC((String) databaseTables.getSelectedItem())
                    + " implements java.io.Serializable {";
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "error during getting computer name");
        }
        return init;
    }

    public void initAttributes() {
        tableAttributes.clear();
        tableAttributesTypes.clear();
        String init = null;
        try {
            result = stmt.executeQuery("select * from " + databaseTables.getSelectedItem());
            attibutesCount = result.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error getting data",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        for (int i = 1; i <= attibutesCount; i++) {
            try {
                tableAttributes.addElement(result.getMetaData().getColumnName(i));
                tableAttributesTypes.addElement(result.getMetaData().getColumnClassName(i));
                javaTextArea.append("\nprivate " + tableAttributesTypes.get(i - 1)
                        + " " + ((String) tableAttributes.get(i - 1)).toLowerCase() + ";");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String initConstructor() {
        return "\n\n/** No-arg constructor (takes no arguments). */"
                + "\npublic " + setFirstLetterC((String) databaseTables.getSelectedItem())
                + "(){\n}";
    }

    public void initMethods() {
        for (int i = 1; i <= attibutesCount; i++) {
            javaTextArea.append(getMethods(i, (String) tableAttributes.get(i - 1)));
            javaTextArea.append(setMethods(i, (String) tableAttributes.get(i - 1)));
        }
    }

    public String getMethods(int index, String element) {
        return "\n/**\n * @return " + element.toLowerCase()
                + "\n */"
                + "\n public " + tableAttributesTypes.get(index - 1)
                + " get" + setFirstLetterC(element + "() {")
                + "\n     return " + (element).toLowerCase() + ";"
                + "\n }\n";
    }

    public String setMethods(int index, String element) {
        return "\n/**\n * @param " + element.toLowerCase() + " variable " + element.toLowerCase()
                + " to set"
                + "\n */"
                + "\n public void"
                + " set" + setFirstLetterC(element) + "(" + tableAttributesTypes.get(index - 1) + " " + element.toLowerCase() + ") {"
                + "\n     this." + element.toLowerCase() + " = " + element.toLowerCase() + ";"
                + "\n }\n";
    }
}
