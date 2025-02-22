// SPDX-FileCopyrightText: 2015 - 2024 Rime community
//
// SPDX-License-Identifier: GPL-3.0-or-later

package com.osfans.trime.util

import java.util.WeakHashMap

class WeakHashSet<T> : MutableSet<T> {
    private val core = WeakHashMap<T, PlaceHolder>()

    private object PlaceHolder

    val view = object : Set<T> by core.keys {}
    override val size get() = core.size

    override fun iterator(): MutableIterator<T> = core.keys.iterator()

    override fun add(element: T) = core.put(element, PlaceHolder) == null

    override fun addAll(elements: Collection<T>) = elements.all(::add)

    override fun remove(element: T) = core.remove(element) != null

    override fun removeAll(elements: Collection<T>) = elements.all(::remove)

    override fun clear() = core.clear()

    override fun retainAll(elements: Collection<T>) = removeAll(core.keys.filter { it !in elements })

    override operator fun contains(element: T) = core.containsKey(element)

    override fun containsAll(elements: Collection<T>) = elements.all(::contains)

    override fun isEmpty() = core.isEmpty()
}
