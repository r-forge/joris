package org.rosuda.mvc.swing;

import java.io.Serializable;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.rosuda.ui.core.mvc.impl.AbstractHasValue;

public class JListHasSelectionModel extends AbstractHasValue<ListSelectionModel> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7798935210387295230L;

    private final ListSelectionModel delegate;
   
    public JListHasSelectionModel(final JList list) {
	this.delegate = list.getSelectionModel();
    }

    @Override
    public ListSelectionModel getValue() {
	return delegate;
    }
}
