package io.github.fabiantauriello.covidtracker.presenter

import android.content.Context
import android.view.View
import io.github.fabiantauriello.covidtracker.model.DataManager
import java.lang.ref.WeakReference

// TODO consider putting all presenters into one file
// I've used a weak reference because typically the concrete view is an activity
// or fragment class and we need those view objects to be correctly garbage collected
// when they are destroyed (e.g. when user rotates the device).
abstract class BasePresenter<V> {
    private var view: WeakReference<V>? = null

    fun setView(view: V) {
        this.view = WeakReference(view)
    }

    fun getView(): V? = view?.get()
}