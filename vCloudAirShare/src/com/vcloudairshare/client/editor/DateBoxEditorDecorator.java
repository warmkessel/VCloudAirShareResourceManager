package com.vcloudairshare.client.editor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * A simple decorator to display leaf widgets with an error message.
 * <p>
 * <h3>Use in UiBinder Templates</h3>
 * <p>
 * The decorator may have exactly one ValueBoxBase added though an <code>&lt;e:valuebox></code>
 * child tag.
 * <p>
 * For example:
 * 
 * <pre>
 * &#64;UiField
 * ValueBoxEditorDecorator<String> name;
 * </pre>
 * 
 * <pre>
 * &lt;e:ValueBoxEditorDecorator ui:field='name'>
 *   &lt;e:valuebox>
 *     &lt;g:DateBox />
 *   &lt;/e:valuebox>
 * &lt;/e:ValueBoxEditorDecorator>
 * </pre>
 * 
 * @param <T> the type of data being edited
 */
public class DateBoxEditorDecorator<T> extends Composite implements HasEditorErrors<T>,
    IsEditor<LeafValueEditor<Date>> {
		
	@UiTemplate("DateBoxEditorDecorator.ui.xml")
	interface Binder extends UiBinder<Widget, DateBoxEditorDecorator<?>> {}
	
	@UiField Element fieldName;
	@UiField Element errorLabel;
	@UiField SimplePanel contents;
  @UiField Element requiredLabel;

  interface Style extends CssResource {
    String requiredField();
  }
  @UiField Style style;
	private LeafValueEditor<Date> editor;
	private DateBox widget;

	private String path = "";

	@UiConstructor
	public DateBoxEditorDecorator() {
		initWidget(((Binder) GWT.create(Binder.class)).createAndBindUi(this));
	}
	
	public DateBoxEditorDecorator(DateBox widget, LeafValueEditor<Date> editor) {
		this();
		contents.add(widget);
		this.editor = editor;
    this.widget = widget;
	}
	
	public Date getValue() {
		return asEditor().getValue();
	}
	
	public void setValue(Date value) {
		asEditor().setValue(value);
	}
	
	public LeafValueEditor<Date> asEditor() {
		return editor;
	}
	
	public void setEditor(LeafValueEditor<Date> editor) {
		this.editor = editor;
	}
	
	/**
	* Adds a widget to the north edge of the dock.
	* 
	* @param widget the widget to be added
	*/
	@UiChild(limit = 1, tagname = "datebox")
	public void setDateBox(DateBox datebox) {
		contents.add(datebox);
		setEditor(datebox.asEditor());
		this.widget = datebox;
	}
	
	/**
	* Overloaded version for IsWidget.
	* 
	* @see #addNorth(Widget)
	*/
	public void setDateBox(IsWidget widget) {
		this.setDateBox(widget.asWidget());
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setRequired(boolean required) {
	  if(required) {
      requiredLabel.removeClassName("hidden");
      fieldName.addClassName(style.requiredField());
    }
//	  if(required) {
//			Element abbrElement = Document.get().createElement("abbr");
//			abbrElement.setInnerText("*");
//			fieldName.getParentElement().appendChild(abbrElement);
//			fieldName.getStyle().setFontWeight(FontWeight.BOLD);
//			fieldName.getStyle().setColor("black");
//		}
	}
	
	public void setFieldName(String name) {
		fieldName.setInnerText(name);
	}
	
	/**
	* The default implementation will display, but not consume, received errors whose
	* {@link EditorError#getEditor() getEditor()} method returns the Editor passed into
	* {@link #setEditor()}.
	*/
	public void showErrors(List<EditorError> errors) {
		if (errors.size() > 0) {
			errorLabel.setInnerText("");  
		    StringBuilder sb = new StringBuilder();
			Set<String> errorSet = new HashSet<String>();
			
			for (EditorError error : errors) {
				if(getPath().endsWith(error.getValue().toString())) {
				  if(!errorSet.contains(error.getMessage())){
            errorSet.add(error.getMessage());
            sb.append("\n").append(error.getMessage());
          }
				  error.setConsumed(true);
				}
			}
			if (sb.length() > 0) {
				errorLabel.setInnerText(sb.substring(1));
				errorLabel.getStyle().setDisplay(Display.BLOCK);
				
				return;
			}
		}
		
		errorLabel.setInnerText("");
		errorLabel.getStyle().setDisplay(Display.NONE);
	}
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler){
    return widget.addValueChangeHandler(handler);
	}  
}