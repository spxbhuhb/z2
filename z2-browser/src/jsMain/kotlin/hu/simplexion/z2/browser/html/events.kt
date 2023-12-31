package hu.simplexion.z2.browser.html

import org.w3c.dom.DragEvent
import org.w3c.dom.events.Event
import org.w3c.dom.events.InputEvent
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

fun Z2.onBlur(handler: (event: Event) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("blur", handler) }

fun Z2.onChange(handler: (event: Event) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("change", handler) }

fun Z2.onClick(handler: (event: Event) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("click", handler) }

fun Z2.onDblClick(handler: (event: Event) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("dblclick", handler) }

fun Z2.onDragover(handler: (event: DragEvent) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("dragover", { handler(it as DragEvent) }) }

fun Z2.onDrop(handler: (event: DragEvent) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("drop", { handler(it as DragEvent) }) }

fun Z2.onFocus(handler: (event: Event) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("focus", handler) }

fun Z2.onFocusOut(handler: (event: Event) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("focusout", handler) }

fun Z2.onInput(handler: (event: InputEvent) -> Unit): Z2 =
    this.apply {
        // FIXME what to do with non-input event input callbacks, happens with Chrome autofill
        htmlElement.addEventListener("input", {
            if (it is InputEvent) handler(it) else console.log(it)
        })
    }

fun Z2.onKeyDown(handler: (event: KeyboardEvent) -> Unit): Z2 =
    this.apply {
        // FIXME what to do with non-keyboard event keydown callbacks, happens with Chrome autofill
        htmlElement.addEventListener("keydown", {
            if (it is KeyboardEvent) handler(it) else console.log(it)
        })
    }

fun Z2.onMouseDown(handler: (event: MouseEvent) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("mousedown", { handler(it as MouseEvent) }) }

fun Z2.onMouseEnter(handler: (event: MouseEvent) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("mouseenter", { handler(it as MouseEvent) }) }

fun Z2.onMouseLeave(handler: (event: MouseEvent) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("mouseleave", { handler(it as MouseEvent) }) }

fun Z2.onScroll(handler: (event: Event) -> Unit): Z2 =
    this.apply { htmlElement.addEventListener("scroll", handler) }
