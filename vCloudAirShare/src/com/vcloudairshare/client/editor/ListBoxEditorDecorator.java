package com.vcloudairshare.client.editor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.editor.ui.client.adapters.ValueBoxEditor;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

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
 *     &lt;g:TextBox />
 *   &lt;/e:valuebox>
 * &lt;/e:ValueBoxEditorDecorator>
 * </pre>
 * 
 * @param <T> the type of data being edited
 */
public class ListBoxEditorDecorator<T> extends Composite implements HasEditorErrors<T>,
    IsEditor<TakesValueEditor<T>>, HasValueChangeHandlers<T> {
  
  @UiTemplate("ListBoxEditorDecorator.ui.xml")
  interface Binder extends UiBinder<Widget, ListBoxEditorDecorator<?>> {
    Binder BINDER = GWT.create(Binder.class);
  }

  @UiField Element fieldName;
  @UiField Element errorLabel;
  @UiField SimplePanel contents;
  @UiField Element requiredLabel;

  interface Style extends CssResource {
    String requiredField();
  }
  @UiField Style style;
  
  private TakesValueEditor<T> editor;
  private String path = "";
  private ValueListBox<T> widget = null;

  @UiConstructor
  public ListBoxEditorDecorator() {
    initWidget(Binder.BINDER.createAndBindUi(this));
  }

  public ListBoxEditorDecorator(ValueBoxBase<T> widget, ValueBoxEditor<T> editor) {
    this();
    contents.add(widget);
    this.editor = editor;
  }

  public T getValue() {
    return asEditor().getValue();
  }

  public void setValue(T value) {
    asEditor().setValue(value);
  }

  public TakesValueEditor<T> asEditor() {
    return editor;
  }

  public void setEditor(TakesValueEditor<T> editor) {
    this.editor = editor;
  }

  /**
   * Set the widget that the EditorPanel will display. This method will automatically call
   * {@link #setEditor}.
   */
  @UiChild(limit = 1, tagname = "valuebox")
  public void setValueBox(ValueListBox<T> widget) {
    this.widget = widget;
    contents.add(widget);
    setEditor(widget.asEditor());
  }
  @Ignore
  public ValueListBox<T> getValueBox(){
    return widget;
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
    showErrors(errors, null);
  }
  public void showErrors(List<EditorError> errors, DisclosurePanel parent) {

    if (errors.size() > 0) {
  	  errorLabel.setInnerText("");  
  	  StringBuilder sb = new StringBuilder();
      Set<String> errorSet = new HashSet<String>();
      
      for (EditorError error : errors) {
         
        if(getPath().endsWith(error.getValue().toString())){
          if(!errorSet.contains(error.getMessage())){
            errorSet.add(error.getMessage());
            sb.append("\n").append(error.getMessage());
            if(null != parent){
              parent.setOpen(true);
            }
          }
          error.setConsumed(true);
        }
      }

      if (sb.length() > 0) {
        errorLabel.setInnerText(sb.substring(1));
        errorLabel.getStyle().setDisplay(Display.INLINE);
        return;
      }

    }
    errorLabel.setInnerText("");
    errorLabel.getStyle().setDisplay(Display.NONE);
  }
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler){
    return getValueBox().addValueChangeHandler(handler);
  }
}