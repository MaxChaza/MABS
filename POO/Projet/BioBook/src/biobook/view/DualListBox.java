package biobook.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;


public class DualListBox extends JPanel {

    private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
    private static final String ADD_BUTTON_LABEL = ">>";
    private static final String REMOVE_BUTTON_LABEL = "<<";
    private JLabel sourceLabel;
    private JList sourceList;
    private SortedListModel sourceListModel;
    private JList destList;
    private SortedListModel destListModel;
    private JLabel destLabel;
    private JButton addButton;
    private JButton removeButton;
    private JScrollPane scrollListeDest;
    private Object[] lastAddSource;
    private Object[] lastAddDest;
    
    public DualListBox(String labelSource, String labelDest) {
//        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new GridBagLayout());

        sourceLabel = new JLabel(labelSource);
        sourceListModel = new SortedListModel();
        sourceList = new JList(sourceListModel);
//        sourceList.setOpaque(true);
        
                
//        sourceList.clearSelection();
        add(sourceLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        add(new JScrollPane(sourceList), new GridBagConstraints(0, 1, 1, 5, .5,
                1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                EMPTY_INSETS, 0, 0));
        
        // Si on click sur modifier on affiche ce qui vient
        addButton = new JButton(ADD_BUTTON_LABEL);
        add(addButton, new GridBagConstraints(1, 2, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        addButton.setVisible(false);
        addButton.addActionListener(new AddListener());

        removeButton = new JButton(REMOVE_BUTTON_LABEL);
        add(removeButton, new GridBagConstraints(1, 4, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                0, 5, 0, 5), 0, 0));
        removeButton.setVisible(false);
        removeButton.addActionListener(new RemoveListener());

        destLabel = new JLabel(labelDest);
        destListModel = new SortedListModel();
        destList = new JList(destListModel);
        add(destLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        destLabel.setVisible(false);
        scrollListeDest = new JScrollPane(destList);
        add(scrollListeDest, new GridBagConstraints(2, 1, 1, 5, .5,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                EMPTY_INSETS, 0, 0));
        scrollListeDest.setVisible(false);
         
    }

    public JList getSourceList() {
        return sourceList;
    }

    public void setSourceList(JList sourceList) {
        this.sourceList = sourceList;
    }

    public JList getDestList() {
        return destList;
    }

    public void setDestList(JList destList) {
        this.destList = destList;
    }

    
    public String getSourceChoicesTitle() {
        return sourceLabel.getText();
    }

    public void setSourceChoicesTitle(String newValue) {
        sourceLabel.setText(newValue);
    }

    public String getDestinationChoicesTitle() {
        return destLabel.getText();
    }

    public void setDestinationChoicesTitle(String newValue) {
        destLabel.setText(newValue);
    }

    public void clearSourceListModel() {
        sourceListModel.clear();
    }

    public void clearDestinationListModel() {
        destListModel.clear();
    }

    public void addSourceElements(ListModel newValue) {
        fillListModel(sourceListModel, newValue);
    }

    public void setSourceElements(ListModel newValue) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    public void addDestinationElements(ListModel newValue) {
        fillListModel(destListModel, newValue);
    }
    
    public void reset(){
        if(lastAddSource!=null) {
            setSourceElements(lastAddSource);
        }
        if(lastAddDest!=null) {
            setDestinationElements(lastAddDest);
        }
    }

    private void fillListModel(SortedListModel model, ListModel newValues) {
        int size = newValues.getSize();
        for (int i = 0; i < size; i++) {
            model.add(newValues.getElementAt(i));
        }
    }

    public void addSourceElements(Object newValue[]) {
        fillListModel(sourceListModel, newValue);
    }

    public void addFirstSourceElements(Object newValue[]) {
        lastAddSource = newValue;
        fillListModel(sourceListModel, newValue);
    }
    
    public void setSourceElements(Object newValue[]) {
        clearSourceListModel();
        addSourceElements(newValue);
    }
    
    public void setDestinationElements(Object newValue[]) {
        clearDestinationListModel();
        addDestinationElements(newValue);
    }

    public void addDestinationElements(Object newValue[]) {
        fillListModel(destListModel, newValue);
    }
    
    public void addFirstDestinationElements(Object newValue[]) {
        lastAddDest = newValue;
        fillListModel(destListModel, newValue);
    }

    private void fillListModel(SortedListModel model, Object newValues[]) {
        model.addAll(newValues);
    }

    public Iterator sourceIterator() {
        return sourceListModel.iterator();
    }

    public Iterator destinationIterator() {
        return destListModel.iterator();
    }

    public void setSourceCellRenderer(ListCellRenderer newValue) {
        sourceList.setCellRenderer(newValue);
    }

    public ListCellRenderer getSourceCellRenderer() {
        return sourceList.getCellRenderer();
    }

    public void setDestinationCellRenderer(ListCellRenderer newValue) {
        destList.setCellRenderer(newValue);
    }

    public ListCellRenderer getDestinationCellRenderer() {
        return destList.getCellRenderer();
    }

    public void setVisibleRowCount(int newValue) {
        sourceList.setVisibleRowCount(newValue);
        destList.setVisibleRowCount(newValue);
    }

    public int getVisibleRowCount() {
        return sourceList.getVisibleRowCount();
    }

    public void setSelectionBackground(Color newValue) {
        sourceList.setSelectionBackground(newValue);
        destList.setSelectionBackground(newValue);
    }

    public Color getSelectionBackground() {
        return sourceList.getSelectionBackground();
    }

    public void setSelectionForeground(Color newValue) {
        sourceList.setSelectionForeground(newValue);
        destList.setSelectionForeground(newValue);
    }

    public Color getSelectionForeground() {
        return sourceList.getSelectionForeground();
    }

    public void clearSourceSelected() {
        Object selected[] = sourceList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            sourceListModel.removeElement(selected[i]);
        }
        sourceList.getSelectionModel().clearSelection();
    }

    private void clearDestinationSelected() {
        Object selected[] = destList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            destListModel.removeElement(selected[i]);
        }
        destList.getSelectionModel().clearSelection();
    }

    public void clickModif()
    {
        addButton.setVisible(true);
        removeButton.setVisible(true);
        destLabel.setVisible(true);
        scrollListeDest.setVisible(true);
    }

    public void clickAnnuler() {
        addButton.setVisible(false);
        removeButton.setVisible(false);
        destLabel.setVisible(false);
        scrollListeDest.setVisible(false);
        reset();
    }
    
    private class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object selected[] = sourceList.getSelectedValues();
            addDestinationElements(selected);
            clearSourceSelected();
        }
    }

    private class RemoveListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object selected[] = destList.getSelectedValues();
            addSourceElements(selected);
            clearDestinationSelected();
        }
    }
}
class SortedListModel extends AbstractListModel {

    SortedSet model;

    public SortedListModel() {
        model = new TreeSet();
    }

    public int getSize() {
        return model.size();
    }

    public Object getElementAt(int index) {
        return model.toArray()[index];
    }

    public void add(Object element) {
        if (model.add(element)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(Object elements[]) {
        Collection c = Arrays.asList(elements);
        model.addAll(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean contains(Object element) {
        return model.contains(element);
    }

    public Object firstElement() {
        return model.first();
    }

    public Iterator iterator() {
        return model.iterator();
    }

    public Object lastElement() {
        return model.last();
    }

    public boolean removeElement(Object element) {
        boolean removed = model.remove(element);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        return removed;
    }
}