package org.sfnelson.blog.client.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.WriteOperation;
import org.sfnelson.blog.client.events.EntrySelectionEvent;
import org.sfnelson.blog.client.places.ManagerPlace;
import org.sfnelson.blog.client.request.EntryProxy;
import org.sfnelson.blog.client.request.PostProxy;
import org.sfnelson.blog.client.request.EntryRequest;
import org.sfnelson.blog.client.request.RequestFactory;
import org.sfnelson.blog.client.views.EntryView;
import org.sfnelson.blog.server.domain.Post;

import java.util.Date;

/**
 * Author: Stephen Nelson <stephen@sfnelson.org>
 * Date: 28/10/11
 */
public class PostEditor extends EntityEditor<PostProxy, EntryView> implements EntryView.Presenter {

	interface Driver extends RequestFactoryEditorDriver<PostProxy, PostEditor> {};

	Editor<String> title() {
		return getView().getTitleEditor();
	}

	Editor<Date> posted() {
		return getView().getPostedEditor();
	}

	Editor<String> content() {
		return getView().getContentEditor();
	}

	private final Driver driver = GWT.create(Driver.class);

	private final EventBus eventBus;
	private final PlaceController pc;
	private final Provider<EntryRequest> request;

	private EntryRequest edit;
	private boolean created;

	@Inject
	PostEditor(EntryView view, RequestFactory rf, Provider<EntryRequest> request, EventBus eventBus, PlaceController pc) {
		super(view);
		view.setPresenter(this);
		driver.initialize(rf, this);
		this.request = request;
		this.eventBus = eventBus;
		this.pc = pc;
	}

	@Override
	public void init(PostProxy entry) {
		created = false;
		edit = null;
		setValue(entry);
		driver.display(entry);
		getView().view();
	}

	public PostProxy create() {
		created = true;
		edit = request.get();
		PostProxy value = this.edit.create(PostProxy.class);
		setValue(value);
		edit.create(value);
		value.setTitle(DateTimeFormat.getFormat("EEEE").format(new Date()));
		value.setPosted(new Date());
		value.setContent("<p>Content</p>");
		driver.edit(value, edit);
		getView().edit();
		requestSelect();
		return value;
	}

	public boolean doSelect() {
		getView().select();
		return true;
	}

	public boolean doDeselect() {
		if (edit != null) {
			if (driver.isDirty()) return false;
			requestCancel();
		}
		getView().deselect();
		return true;
	}

	@Override
	public void requestSelect() {
		eventBus.fireEvent(new EntrySelectionEvent(getValue(), true));
	}

	@Override
	public void requestEdit() {
		if (edit != null) return;
		edit = request.get();
		edit.update(getValue());
		driver.edit(getValue(), edit);
		getView().edit();
	}

	@Override
	public void requestSubmit() {
		if (edit == null) return;
		driver.flush().fire();
		edit = null;
		getView().view();
		if (created) {
			created = false;
			pc.goTo(ManagerPlace.PLACE);
		}
	}

	@Override
	public void requestCancel() {
		if (edit == null) return;
		reset();
		driver.display(getValue());
		edit = null;
		getView().view();
		if (created) {
			eventBus.fireEventFromSource(
					new EntityProxyChange<PostProxy>(getValue(), WriteOperation.DELETE),
					PostProxy.class);
			pc.goTo(ManagerPlace.PLACE);
		}
	}

	@Override
	public void requestDelete() {
		requestCancel();
		request.get().delete(getValue()).fire();
	}
}
